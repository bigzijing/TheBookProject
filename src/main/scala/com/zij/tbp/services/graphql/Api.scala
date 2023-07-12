package com.zij.tbp.services.graphql

import caliban.schema.GenericSchema
import caliban.{GraphQL, RootResolver, graphQL}
import com.zij.tbp.core.Librarian

object Api extends GenericSchema[Librarian] {
  import Api.auto._

  private val queries   = Queries(args => Librarian.retrieveBooks(args))
  val api: GraphQL[Librarian] = graphQL(RootResolver(queries))
}
