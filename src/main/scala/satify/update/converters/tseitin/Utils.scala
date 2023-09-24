package satify.update.converters.tseitin

import satify.model.cnf.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.cnf.{CNF, Literal}
import satify.model.expression.Expression
import satify.model.expression.Expression.*
import satify.model.expression.SymbolGeneration.{SymbolGenerator, converterVarPrefix}

private[converters] object Utils:

  /** Zip the subexpressions found in the given expression with a Symbol.
    * @param exp the expression.
    * @return a list of the subexpressions found in the given expression zipped with the Symbol.
    */
  private[tseitin] def zipWithSymbol(exp: Expression): List[(Symbol, Expression)] =
    given SymbolGenerator with
      override def prefix: String = converterVarPrefix
    zipWith(exp)(summon[SymbolGenerator].generate)

  /** Method to check if an expression is in CNF form and can be converted to CNF form.
    * @param expression The expression to check.
    * @return true if the expression could be converted to CNF form, false otherwise and in case of empty expression.
    */
  private[tseitin] def isCNF(expression: Expression): Boolean =
    import Expression.*
    expression match
      case Symbol(_) => true
      case Not(Symbol(_)) => true
      case And(l, r) =>
        (l, r) match
          case (And(_, _), _) => false
          case _ => isCNF(l) && isCNF(r);
      case Or(l, r) =>
        (l, r) match
          case (And(_, _), _) | (_, And(_, _)) => false
          case _ => isCNF(l) && isCNF(r);
      case _ => false
