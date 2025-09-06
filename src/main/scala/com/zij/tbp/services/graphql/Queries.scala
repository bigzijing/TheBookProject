package com.zij.tbp.services.graphql

import com.zij.tbp.core.Librarian
import com.zij.tbp.services.graphql.models.{ BookSearchResult, RetrieveBookArgs }
import zio.ZIO

case class Queries(
  retrieveBooks: RetrieveBookArgs => ZIO[Librarian, Throwable, BookSearchResult],
  searchByTitle: String => ZIO[Librarian, Throwable, BookSearchResult],
  searchByIsbn: String => ZIO[Librarian, Throwable, BookSearchResult]
)
