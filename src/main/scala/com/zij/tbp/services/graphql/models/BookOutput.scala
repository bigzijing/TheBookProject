package com.zij.tbp.services.graphql.models

import caliban.schema.Schema
import com.zij.tbp.models.Book
import io.scalaland.chimney.dsl._

import java.time.Instant

case class BookOutput(
  _id: Option[String],
  createdDate: Option[String],
  title: String,
  isbn: String,
  author: String,
  publisher: String,
  category: List[String],
  goodreadsCategory: List[String],
  language: List[String],
  coverType: String,
  pages: Option[Int],
  condition: Option[Double],
  rating: Option[Double],
  goodreadsRating: Option[Double],
  location: Option[String],
  status: Option[String],
  read: Boolean,
  dateBought: Option[String],
  notes: Option[String]
)

object BookOutput {

  implicit val bookSchema: Schema[Any, BookOutput] = Schema.gen
  def fromBook(book: Book): BookOutput             =
    book
      .into[BookOutput]
      .withFieldComputed(_._id, _._id.map(_.toString))
      .withFieldComputed(_.language, _.language.map(_.entryName))
      .withFieldComputed(_.coverType, _.coverType.entryName)
      .withFieldComputed(_.dateBought, _.dateBought.map(_.toString))
      .withFieldComputed(_.createdDate, _.createdDate.map(_.toString))
      .transform
}
