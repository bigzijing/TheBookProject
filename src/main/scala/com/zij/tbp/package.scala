package com.zij

import reactivemongo.zio.Mongo
import zio.Console.{printLine, readLine}
import zio.{ZIO, ZLayer}

import java.io.IOException

package object tbp {

  def getMongoFromCredentials: ZLayer[Any, Throwable, Mongo] =
    ZLayer
      .fromZIO(
        for {
          username <- printLine("Enter URL encoded Mongo username") *> readLine
          password <- printLine("Enter URL encoded Mongo password") *> readLine
        } yield s"mongodb+srv://$username:$password@the-book-project-cluste.7xsahya.mongodb.net/the-book-project?retryWrites=true&w=majority"
      )
      .flatMap(s => Mongo.fromConnectionString(s.get))

  def getFilename: ZIO[Any, IOException, String] =
    for {
      filename <- printLine("Enter filename in resources folder") *> readLine
    } yield filename
}
