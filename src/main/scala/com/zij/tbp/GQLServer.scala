package com.zij.tbp

import caliban.interop.tapir.{ HttpInterpreter, WebSocketInterpreter }
import caliban.ZHttpAdapter
import com.zij.tbp.core.Librarian
import com.zij.tbp.services.graphql.Api.api
import com.zij.tbp.services.graphql.models.RetrieveBookArgs
import reactivemongo.zio.Mongo
import zio.{ ZIOAppDefault, ZLayer }
import zio.Console.printLine
import zio.http._
import zio.stream.ZStream

object GQLServer extends ZIOAppDefault {

  import sttp.tapir.json.circe._

  val run = (for {
    books <- Librarian.retrieveBooks(RetrieveBookArgs("1Q84: The Complete Trilogy"))
    interpreter <- api.interpreter
    _           <- Server
                     .serve(
                       Http.collectHttp[Request] { case _ -> !! / "api" / "graphql" =>
                         ZHttpAdapter.makeHttpService(HttpInterpreter(interpreter))
                       }
                     ) <&> printLine("Server online at http://localhost:8080/")
  } yield ())
    .provide(
      getMongoFromCredentials,
      Librarian.live,
      ZLayer.succeed(Server.Config.default.port(8080)),
      Server.live
    )
}
