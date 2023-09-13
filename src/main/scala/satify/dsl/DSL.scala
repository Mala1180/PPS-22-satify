package satify.dsl

import satify.model.expression.SymbolGeneration.{SymbolGenerator, encodingVarPrefix}

object DSL:
  export Conversions.given
  export Operators.*
  export Numbers.*
  export Encodings.*

  given SymbolGenerator with
    def prefix: String = encodingVarPrefix
