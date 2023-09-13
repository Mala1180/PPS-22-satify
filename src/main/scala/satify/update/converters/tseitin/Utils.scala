package satify.update.converters.tseitin

import satify.model.expression.Expression
import satify.model.expression.Expression.*
import satify.model.cnf.CNF.{And as CNFAnd, Not as CNFNot, Or as CNFOr, Symbol as CNFSymbol}
import satify.model.cnf.Literal
import satify.model.cnf.CNF

private[converters] object Utils:
  
  /** Zip the subexpressions found in the given expression with a Symbol.
    *
    * @param exp the expression.
    * @return a list of the subexpressions found in the given expression zipped with the Symbol.
    */
  def zipWithSymbol(exp: Expression): List[(Symbol, Expression)] =
    given SymbolGenerator with
      override val hasToReset = true
      override val prefix: String = tseitinVarPrefix 
    zipWith(exp)(summon[SymbolGenerator].generate)

  /** Method to check if an expression is in CNF form and can be converted to CNF form.
    *
    * @param expression The expression to check.
    * @return true if the expression could be converted to CNF form, false otherwise and in case of empty expression.
    */
  def isCNF(expression: Expression): Boolean =
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

  /** Method to check if an expression is in CNF form and can be converted to CNF form.
    *
    * @param expression The expression to check.
    * @return true if the expression could be converted to CNF form, false otherwise and in case of empty expression.
    */
  def convertToCNF(expression: Expression): CNF =
    def convL(exp: Expression): CNFOr | Literal = exp match
      case Or(l, r) => CNFOr(convL(l), convL(r))
      case Symbol(v) => CNFSymbol(v)
      case Not(Symbol(v)) => CNFNot(CNFSymbol(v))
      case _ => throw new Exception("Expression is not convertible to CNF form")

    def convR(exp: Expression): CNFAnd | CNFOr | Literal = exp match
      case And(l, r) => CNFAnd(convL(l), convR(r))
      case Or(l, r) => CNFOr(convL(l), convL(r))
      case Symbol(v) => CNFSymbol(v)
      case Not(Symbol(v)) => CNFNot(CNFSymbol(v))
      case _ => throw new Exception("Expression is not convertible to CNF form")

    expression match
      case Symbol(v) => CNFSymbol(v)
      case Not(Symbol(v)) => CNFNot(CNFSymbol(v))
      case And(l, r) => CNFAnd(convL(l), convR(r))
      case Or(l, r) => CNFOr(convL(l), convL(r))
      case _ => throw new Exception("Expression is not convertible to CNF form")
