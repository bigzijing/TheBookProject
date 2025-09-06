package com.zij.tbp

import caliban.GraphQL
import com.zij.tbp.core.Librarian
import com.zij.tbp.services.graphql.Api.api
import com.zij.tbp.services.graphql.models.RetrieveBookArgs
import reactivemongo.zio.Mongo
import zio.{ ZIO, ZIOAppDefault, ZLayer }
import zio.Console.{ printLine, readLine }
import zio.http._
import zio.stream.ZStream
import caliban.quick._
import com.zij.tbp.services.graphql.Api

object GQLServer extends ZIOAppDefault {

  import sttp.tapir.json.circe._

  private val Port = 8080

  private val cliShutdownSignal =
    printLine(s"Server online at http://localhost:$Port/") *>
      printLine("Press RETURN to stop...") *>
      readLine.unit

  val run = (for {
    books <- Librarian.retrieveBooks(RetrieveBookArgs("1Q84: The Complete Trilogy"))
    _     <- ZIO.serviceWithZIO[GraphQL[Librarian]] {
               _.runServer(
                 port = Port,
                 apiPath = "/api/graphql"
               )
             } race cliShutdownSignal
  } yield ())
    .provide(
      getMongoFromCredentials,
      ZLayer.succeed(Api.api),
      Librarian.live
    )
}
