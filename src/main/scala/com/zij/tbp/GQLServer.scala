package com.zij.tbp

import caliban.GraphQL
import com.zij.tbp.core.Librarian
import zio.{ ZIO, ZIOAppDefault, ZLayer }
import zio.Console.{ printLine, readLine }
import caliban.quick._
import com.zij.tbp.services.graphql.Api

object GQLServer extends ZIOAppDefault {

  private val Port = 8080

  private val cliShutdownSignal =
    printLine(s"Server online at http://localhost:$Port/") *>
      printLine("Press RETURN to stop...") *>
      readLine.unit

  val run = (for {
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
