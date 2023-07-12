package com.zij.tbp.services.graphql

import com.zij.tbp.core.Librarian
import com.zij.tbp.services.graphql.models.{ BookOutput, RetrieveBookArgs }
import zio.ZIO

case class Queries(retrieveBooks: RetrieveBookArgs => ZIO[Librarian, Throwable, List[BookOutput]])

