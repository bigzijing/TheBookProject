package com.zij.tbp.services.graphql

import caliban.schema.GenericSchema
import caliban.{ GraphQL, RootResolver, graphQL }
import com.zij.tbp.core.Librarian
import com.zij.tbp.services.graphql.models.BookSearchResult

object Api extends GenericSchema[Librarian] {
  import Api.auto._

  private val queries = Queries(
    retrieveBooks = args => Librarian.retrieveBooks(args).map(ls => BookSearchResult(ls.size, ls)),
    searchByTitle = title => Librarian.searchByTitle(title).map(ls => BookSearchResult(ls.size, ls)),
    searchByIsbn = isbn => Librarian.searchByIsbn(isbn).map(ls => BookSearchResult(ls.size, ls))
  )

  val api: GraphQL[Librarian] = graphQL(RootResolver(queries))
}
