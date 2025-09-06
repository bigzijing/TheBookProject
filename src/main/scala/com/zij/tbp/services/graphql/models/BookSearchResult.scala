package com.zij.tbp.services.graphql.models

case class BookSearchResult(totalResults: Int, books: List[BookOutput])
