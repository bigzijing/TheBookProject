package com.zij.tbp.services.graphql.models

import caliban.schema.ArgBuilder

case class RetrieveBookArgs(title: String)

object RetrieveBookArgs {
  implicit val argBuilderForRetrieveBookArgs: ArgBuilder[RetrieveBookArgs] = ArgBuilder.gen
}
