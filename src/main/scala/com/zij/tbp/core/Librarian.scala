package com.zij.tbp.core

import com.zij.tbp.services.graphql.models.{ BookOutput, RetrieveBookArgs }
import com.zij.tbp.services.mongo.models.BookDBO
import reactivemongo.api.bson.document
import reactivemongo.zio.{ CollectionName, Mongo }
import zio.{ ZIO, ZLayer }

trait Librarian {
  def retrieveBooks(args: RetrieveBookArgs): ZIO[Any, Throwable, List[BookOutput]]
  def searchByTitle(title: String): ZIO[Any, Throwable, List[BookOutput]]
  def searchByIsbn(isbn: String): ZIO[Any, Throwable, List[BookOutput]]
}

object Librarian {

  val live: ZLayer[Mongo, Nothing, LibrarianImpl] =
    ZLayer.fromFunction(LibrarianImpl(_))

  def retrieveBooks(args: RetrieveBookArgs): ZIO[Librarian, Throwable, List[BookOutput]] =
    ZIO.serviceWithZIO[Librarian](_.retrieveBooks(args)).orDie

  def searchByTitle(title: String): ZIO[Librarian, Throwable, List[BookOutput]] =
    ZIO.serviceWithZIO[Librarian](_.searchByTitle(title)).orDie

  def searchByIsbn(isbn: String): ZIO[Librarian, Throwable, List[BookOutput]] =
    ZIO.serviceWithZIO[Librarian](_.searchByIsbn(isbn)).orDie

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

    override def searchByTitle(title: String): ZIO[Any, Throwable, List[BookOutput]] = {

      val keywords    = splitToKeywords(title).sortBy(-_.length)
      val bigKeywords = filterOffSmallWords(keywords)

      booksColl {
        Mongo
          .find(document())
          .stream[BookDBO]()
          .map(_.toBook)
          .filter { book =>
            val bookKeywords = splitToKeywords(book.title)

            bigKeywords.intersect(filterOffSmallWords(bookKeywords)).nonEmpty &&
            (if (bigKeywords.nonEmpty)
               bigKeywords.intersect(filterOffSmallWords(bookKeywords)).size >= 2
             else false) ||
            keywords.intersect(bookKeywords).size >= (bookKeywords.size / 2.0)
          }
          .map(b => BookOutput.fromBook(b))
      }.runCollect.map(_.toList)
    }

    override def searchByIsbn(isbn: String): ZIO[Any, Throwable, List[BookOutput]] =
      booksColl {
        Mongo
          .find(document())
          .stream[BookDBO]()
          .map(_.toBook)
          .filter(b => b.isbn.trim == isbn.trim)
          .map(b => BookOutput.fromBook(b))
      }.runCollect.map(_.toList)

    private def splitToKeywords(string: String): List[String] =
      string.toLowerCase.split(" ").toList

    private def filterOffSmallWords(keywords: List[String]) =
      keywords.filter(_.length > 2)

  }
}
