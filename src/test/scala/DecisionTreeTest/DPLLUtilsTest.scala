package DecisionTreeTest

import model.Expression.{And, Not, Or, Symbol, cast}
import model.{EmptyExpression, NamedVariable, PartialExpression, PartialVariable}
import model.dpll.DPLLUtils.*
import model.dpll.{VariableConstraint, PartialModel}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DPLLUtilsTest extends AnyFlatSpec with Matchers:

  val varA: NamedVariable = NamedVariable("a")
  val varB: NamedVariable = NamedVariable("b")
  val varC: NamedVariable = NamedVariable("c")

  val emptyExpression: EmptyExpression = And(Or(Symbol(varA), Symbol(varB)), Symbol(varC))

  // From object Expression
  "Function convert" should "be able to convert an EmptyExpression to a PartialExpression" in {
    cast(emptyExpression, classOf[PartialVariable]) should be equals
      And(Or(Symbol(PartialVariable("a", Option.empty)), Symbol(PartialVariable("b", Option.empty))),
        Symbol(PartialVariable("c", Option.empty)))
  }

  "A PartialModel" should "be extractable from a PartialExpression" in {
    val partialModel: PartialModel = Set(PartialVariable("a", Option.empty),
      PartialVariable("c", Option.empty), PartialVariable("c", Option.empty))
    partialModel should be equals extractModelFromExpression(cast(emptyExpression, classOf[PartialVariable]))
  }

  "A PartialExpression" should  "be mapped to another PartialExpression setting a variable" in {
    val partialExpression = cast(emptyExpression, classOf[PartialVariable])
    updateExpression(partialExpression, VariableConstraint("b", false)) should be equals
      And(Or(Symbol(PartialVariable("a", Option.empty)), Symbol(PartialVariable("b", Option(false)))),
        Symbol(PartialVariable("c", Option.empty)))
  }