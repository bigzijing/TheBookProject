package com.zij.tbp.models

import reactivemongo.api.bson.BSONObjectID

import java.time.Instant

case class Book(
  _id: Option[BSONObjectID],
  createdDate: Option[Instant],
  title: String,
  isbn: String,
  author: String,
  publisher: String,
  category: List[String],
  goodreadsCategory: List[String],
  language: List[Language],
  coverType: CoverType,
  pages: Option[Int],
  condition: Option[Double],
  rating: Option[Double],
  goodreadsRating: Option[Double],
  location: Option[String],
  status: Option[String],
  read: Boolean,
  dateBought: Option[Instant],
  notes: Option[String]
)
