package com.zij.tbp.core

import com.zij.tbp.services.graphql.models.{BookOutput, RetrieveBookArgs}
import com.zij.tbp.services.mongo.models.BookDBO
import reactivemongo.api.bson.document
import reactivemongo.zio.{CollectionName, Mongo}
import zio.{ZIO, ZLayer}

trait Librarian {
  def retrieveBooks(args: RetrieveBookArgs): ZIO[Any, Throwable, List[BookOutput]]
}

object Librarian {

  val live: ZLayer[Mongo, Nothing, LibrarianImpl] =
    ZLayer.fromFunction(LibrarianImpl.apply(_))
  def retrieveBooks(args: RetrieveBookArgs): ZIO[Librarian, Throwable, List[BookOutput]] =
    ZIO.serviceWithZIO[Librarian](_.retrieveBooks(args)).orDie
  case class LibrarianImpl(mongo: Mongo) extends Librarian {

    private val booksColl = mongo.collection(CollectionName("book"))
    override def retrieveBooks(args: RetrieveBookArgs): ZIO[Any, Throwable, List[BookOutput]] =
      booksColl {
        Mongo
          .find(document("title" -> args.title))
          .stream[BookDBO]()
          .map(_.toBook)
          .map(b => BookOutput.fromBook(b))
      }.runCollect.map(_.toList)
  }
}
