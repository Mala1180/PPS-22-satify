package DecisionTreeTest

import model.Expression.{And, Not, Or, Symbol}
import model.{EmptyExpression, EmptyVariable, PartialExpression, PartialVariable}
import model.dpll.DpllExpressionUtils.*
import model.dpll.{VariableConstraint, PartialModel}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DPLLUtilsTest extends AnyFlatSpec with Matchers:

  val varA: EmptyVariable = EmptyVariable("a")
  val varB: EmptyVariable = EmptyVariable("b")
  val varC: EmptyVariable = EmptyVariable("c")

  val emptyExpression: EmptyExpression = And(Or(Symbol(varA), Symbol(varB)), Symbol(varC))

  // From object Expression
  "Function convert" should "be able to convert an EmptyExpression to a PartialExpression" in {
    mapEmptyExpToPar(emptyExpression) should be equals
      And(Or(Symbol(PartialVariable("a", Option.empty)), Symbol(PartialVariable("b", Option.empty))),
        Symbol(PartialVariable("c", Option.empty)))
  }

  "A PartialModel" should "be extractable from a PartialExpression" in {
    val partialModel: PartialModel = Set(PartialVariable("a", Option.empty),
      PartialVariable("c", Option.empty), PartialVariable("c", Option.empty))
    partialModel should be equals extractModelFromExpression(mapEmptyExpToPar(emptyExpression))
  }

  "A PartialExpression" should  "be mapped to another PartialExpression setting a variable" in {
    val partialExpression = mapEmptyExpToPar(emptyExpression)
    updateExpression(partialExpression, VariableConstraint("b", false)) should be equals
      And(Or(Symbol(PartialVariable("a", Option.empty)), Symbol(PartialVariable("b", Option(false)))),
        Symbol(PartialVariable("c", Option.empty)))
  }