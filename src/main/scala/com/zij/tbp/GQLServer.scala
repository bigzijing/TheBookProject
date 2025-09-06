package com.zij.tbp

import caliban.interop.tapir.{ HttpInterpreter, WebSocketInterpreter }
import caliban.ZHttpAdapter
import com.zij.tbp.core.Librarian
import com.zij.tbp.services.graphql.Api.api
import com.zij.tbp.services.graphql.models.RetrieveBookArgs
import reactivemongo.zio.Mongo
import zio.{ ZIOAppDefault, ZLayer }
import zio.Console.{ printLine, readLine }
import zio.http._
import zio.stream.ZStream

object GQLServer extends ZIOAppDefault {

  import sttp.tapir.json.circe._

  private val Port = 8080

  private val cliShutdownSignal =
    printLine(s"Server online at http://localhost:$Port/") *>
      printLine("Press RETURN to stop...") *>
      readLine.unit

  val run = (for {
    books       <- Librarian.retrieveBooks(RetrieveBookArgs("1Q84: The Complete Trilogy"))
    interpreter <- api.interpreter
    _           <- (Server
                     .serve(
                       Http.collectHttp[Request] { case _ -> !! / "api" / "graphql" =>
                         ZHttpAdapter.makeHttpService(HttpInterpreter(interpreter))
                       }
                     ) race cliShutdownSignal)
  } yield ())
    .provide(
      getMongoFromCredentials,
      Librarian.live,
      ZLayer.succeed(Server.Config.default.port(Port)),
      Server.live
    )
}
