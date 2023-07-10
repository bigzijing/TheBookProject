package com.zij.tbp.models

import enumeratum._
import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}
sealed trait CoverType extends EnumEntry {
  val GSheetsEntryName: String
}

object CoverType extends Enum[CoverType] {

  val values = findValues
  case object HardCover    extends CoverType {
    override val GSheetsEntryName: String = "H"
  }
  case object PaperBack    extends CoverType {
    override val GSheetsEntryName: String = "P"
  }
  case object LeatherBound extends CoverType {
    override val GSheetsEntryName: String = "L"
  }
  case object BoxSet       extends CoverType {
    override val GSheetsEntryName: String = "B"
  }

  def gsheetsToEnumRouter(gsheetsEntry: String): CoverType = gsheetsEntry match {
    case "H" => HardCover
    case "P" => PaperBack
    case "L" => LeatherBound
    case "B" => BoxSet
    case _ => throw new Exception("CoverType not known")
  }

  implicit def coverTypeWriter: BSONDocumentWriter[CoverType] = Macros.writer[CoverType]

  implicit def bookReader: BSONDocumentReader[CoverType] = Macros.reader[CoverType]

}
