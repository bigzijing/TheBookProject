package com.zij.tbp.services.mongo.models

import com.zij.tbp.models.{ Book, CoverType, Language }
import reactivemongo.api.bson.{ BSONDocumentReader, BSONDocumentWriter, BSONObjectID, Macros }
import io.scalaland.chimney.dsl._
import cats.implicits._
import com.zij.tbp.services.graphql.models.BookOutput

import java.time.Instant

case class BookDBO(
  _id: Option[BSONObjectID] = None,
  createdDate: Option[Instant] = None,
  title: String,
  isbn: String,
  author: String,
  publisher: String,
  category: List[String],
  goodreadsCategory: List[String],
  language: List[String],
  coverType: String,
  pages: Option[Int] = None,
  condition: Option[Double] = None,
  rating: Option[Double] = None,
  goodreadsRating: Option[Double] = None,
  location: Option[String] = None,
  status: Option[String] = None,
  read: Boolean = false,
  dateBought: Option[Instant] = None,
  notes: Option[String] = None
) {

  def toShortString     = s"$title by $author"
  def toFormattedString =
    s"""Title: $title
       |ISBN: $isbn
       |Author: $author
       |""".stripMargin

  def toBook: Book =
    this
      .into[Book]
      .withFieldComputed(_.language, _.language.map(Language.withNameInsensitive))
      .withFieldComputed(_.coverType, b => CoverType.withNameInsensitive(b.coverType))
      .transform
}

object BookDBO {

  def fromBook(book: Book)                             =
    book
      .into[BookDBO]
      .withFieldComputed(_.language, _.language.map(_.entryName))
      .withFieldComputed(_.coverType, _.coverType.entryName)
      .transform
  implicit def bookWriter: BSONDocumentWriter[BookDBO] = Macros.writer[BookDBO]
  implicit def bookReader: BSONDocumentReader[BookDBO] = Macros.reader[BookDBO]
}
