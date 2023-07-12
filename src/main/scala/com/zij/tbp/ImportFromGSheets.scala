package com.zij.tbp

import com.github.tototoshi.csv.CSVReader
import com.zij.tbp.services.mongo.models.BookDBO
import zio.ZIOAppDefault
import reactivemongo.zio._
import zio.stream.ZStream
import cats.implicits._
import com.zij.tbp.models.{ CoverType, Language }
import com.zij.tbp.models.Book

import java.time.Instant
import scala.io.Source
import scala.util.Try

object ImportFromGSheets extends ZIOAppDefault {
  def stringToBoolean(s: String): Boolean = s match {
    case "1" => true
    case _   => false
  }

  def stringToOption(s: String): Option[String] = s match {
    case "" => none
    case s  => s.some
  }

  def getLanguages(s: String): List[Language] = s.trim.split(",").toList.flatMap(Language.withNameInsensitiveOption)

  def readFromCsv(fileName: String): ZStream[Any, Nothing, BookDBO] = ZStream
    .fromIterable(
      CSVReader
        .open(Source.fromResource(fileName))
        .toLazyList
        .drop(2)
    )
    .map { line =>
      BookDBO.fromBook(
        Book(
          _id = none,
          createdDate = Instant.now.some,
          title = line.head,
          isbn = line(10),
          author = line(1),
          publisher = line(2),
          category = List(line(3)),
          goodreadsCategory = List.empty,
          language = getLanguages(line(4)),
          coverType = CoverType.gsheetsToEnumRouter(line(5)),
          pages = stringToOption(line(6)).flatMap(s => Try(s.toInt).toOption),
          condition = stringToOption(line(7)).flatMap(s => Try(s.toDouble).toOption),
          rating = stringToOption(line(8)).flatMap(p => Try(p.toDouble).toOption),
          goodreadsRating = stringToOption(line(9)).flatMap(p => Try(p.toDouble).toOption),
          location = stringToOption(line(11)),
          status = stringToOption(line(12)),
          read = stringToBoolean(line(14)),
          dateBought = none,
          notes = stringToOption(line(13))
        )
      )
    }

  val run = (for {
    filename        <- getFilename
    booksCollection <- Mongo.collection(CollectionName("book"))
    _               <- booksCollection {
                         readFromCsv(filename).runForeach(Mongo.insert.one(_))
                       }
  } yield ())
    .provide(getMongoFromCredentials)
}
