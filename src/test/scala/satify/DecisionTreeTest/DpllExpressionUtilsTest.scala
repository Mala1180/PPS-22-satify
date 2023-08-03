package satify.DecisionTreeTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.*
import satify.model.Expression.{And, Or, Symbol}
import satify.model.dpll.DpllExpressionUtils.*
import satify.model.dpll.{Constraint, PartialModel}

class DpllExpressionUtilsTest extends AnyFlatSpec with Matchers:

  val varA: EmptyVariable = EmptyVariable("a")
  val varB: EmptyVariable = EmptyVariable("b")
  val varC: EmptyVariable = EmptyVariable("c")

  val emptyExp: EmptyExpression = And(Or(Symbol(varA), Symbol(varB)), Symbol(varC))

  "EmptyExpression" should "be able to map be mapped to a PartialExpression" in {
    mapEmptyExpToPar(emptyExp) should be equals
      And(
        Or(Symbol(PartialVariable("a", Option.empty)), Symbol(PartialVariable("b", Option.empty))),
        Symbol(PartialVariable("c", Option.empty))
      )
  }

  "PartialExpression" should "be able to be mapped to an AssignedExpression" in {
    val parExp: PartialExpression = And(
      Or(Symbol(PartialVariable("a", Option(true))), Symbol(PartialVariable("b", Option(false)))),
      Symbol(PartialVariable("c", Option(true)))
    )
    mapParExpToAss(parExp) should be equals
      And(
        Or(Symbol(AssignedVariable("a", true)), Symbol(AssignedVariable("b", false))),
        Symbol(AssignedVariable("c", true))
      )
  }

  "A PartialModel" should "be extractable from a PartialExpression" in {
    val partialModel: PartialModel =
      Seq(PartialVariable("a", Option.empty), PartialVariable("c", Option.empty), PartialVariable("c", Option.empty))
    partialModel should be equals extractModelFromExp(mapEmptyExpToPar(emptyExp))
  }

  "A PartialExpression" should "be mapped to another PartialExpression setting a variable" in {
    val partialExpression = mapEmptyExpToPar(emptyExp)
    updateParExp(partialExpression, Constraint("b", false)) should be equals
      And(
        Or(Symbol(PartialVariable("a", Option.empty)), Symbol(PartialVariable("b", Option(false)))),
        Symbol(PartialVariable("c", Option.empty))
      )
  }
