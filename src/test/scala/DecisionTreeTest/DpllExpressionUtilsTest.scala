package DecisionTreeTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.model.*
import satify.model.CNF.{And, Or, Symbol}
//import satify.model.dpll.DpllExpressionUtils.*
import satify.model.dpll.{Constraint, PartialModel}

/*class DpllExpressionUtilsTest extends AnyFlatSpec with Matchers:

  val varA: Variable = Variable("a")
  val varB: Variable = Variable("b")
  val varC: Variable = Variable("c")

  val emptyExp: CNF = And(Or(Symbol(varA), Symbol(varB)), Symbol(varC))

  "EmptyExpression" should "be able to map be mapped to a PartialExpression" in {
    mapEmptyExpToPar(emptyExp) should be equals
      And(
        Or(Symbol(Variable("a", Option.empty)), Symbol(Variable("b", Option.empty))),
        Symbol(Variable("c", Option.empty))
      )
  }

  "PartialExpression" should "be able to be mapped to an AssignedExpression" in {
    val parExp: PartialExpression = And(
      Or(Symbol(Variable("a", Option(true))), Symbol(Variable("b", Option(false)))),
      Symbol(Variable("c", Option(true)))
    )
    mapParExpToAss(parExp) should be equals
      And(
        Or(Symbol(Variable("a", true)), Symbol(Variable("b", false))),
        Symbol(Variable("c", true))
      )
  }

  "A PartialModel" should "be extractable from a PartialExpression" in {
    val partialModel: PartialModel =
      Seq(Variable("a", Option.empty), Variable("c", Option.empty), Variable("c", Option.empty))
    partialModel should be equals extractModelFromExp(mapEmptyExpToPar(emptyExp))
  }

  "A PartialExpression" should "be mapped to another PartialExpression setting a variable" in {
    val partialExpression = mapEmptyExpToPar(emptyExp)
    updateParExp(partialExpression, Constraint("b", false)) should be equals
      And(
        Or(Symbol(Variable("a", Option.empty)), Symbol(Variable("b", Option(false)))),
        Symbol(Variable("c", Option.empty))
      )
  }*/
