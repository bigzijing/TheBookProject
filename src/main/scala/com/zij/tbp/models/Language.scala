package com.zij.tbp.models

import enumeratum._
import reactivemongo.api.bson.{ BSONDocumentReader, BSONDocumentWriter, Macros }
sealed trait Language extends EnumEntry

object Language extends Enum[Language] {

  val values = findValues
  case object English extends Language
  case object Chinese extends Language
  case object Russian extends Language
  case object German  extends Language
  case object French  extends Language
  case object Spanish extends Language

  implicit def languageWriter: BSONDocumentWriter[Language] = Macros.writer[Language]

  implicit def languageReader: BSONDocumentReader[Language] = Macros.reader[Language]

}
