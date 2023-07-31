package DecisionTreeTest

import model.Expression.{And, Not, Or, Symbol}
import model.{AssignedVariable, EmptyExpression, EmptyVariable, PartialExpression, PartialVariable}
import model.dpll.DpllExpressionUtils.*
import model.dpll.{PartialModel, VariableConstraint}
import org.scalatest.Assertions.assertThrows
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DpllExpressionUtils extends AnyFlatSpec with Matchers:

  val varA: EmptyVariable = EmptyVariable("a")
  val varB: EmptyVariable = EmptyVariable("b")
  val varC: EmptyVariable = EmptyVariable("c")

  val emptyExp: EmptyExpression = And(Or(Symbol(varA), Symbol(varB)), Symbol(varC))

  "Function mapExp" should "be able to map an EmptyExpression to a PartialExpression" in {
    mapEmptyExpToPar(emptyExp) should be equals
      And(Or(Symbol(PartialVariable("a", Option.empty)), Symbol(PartialVariable("b", Option.empty))),
        Symbol(PartialVariable("c", Option.empty)))
  }

  "Function mapExp" should "be able to map a PartialExpression to an AssignedExpression" in {
    val parExp: PartialExpression = And(Or(Symbol(PartialVariable("a", Option(true))),
      Symbol(PartialVariable("b", Option(false)))), Symbol(PartialVariable("c", Option(true))))
    mapParExpToAss(parExp) should be equals
      And(Or(Symbol(AssignedVariable("a", true)), Symbol(AssignedVariable("b", false))),
        Symbol(AssignedVariable("c", true)))
  }

  "A PartialModel" should "be extractable from a PartialExpression" in {
    val partialModel: PartialModel = Set(PartialVariable("a", Option.empty),
      PartialVariable("c", Option.empty), PartialVariable("c", Option.empty))
    partialModel should be equals extractModelFromExpression(mapEmptyExpToPar(emptyExp))
  }

  "A PartialExpression" should  "be mapped to another PartialExpression setting a variable" in {
    val partialExpression = mapEmptyExpToPar(emptyExp)
    updateParExp(partialExpression, VariableConstraint("b", false)) should be equals
      And(Or(Symbol(PartialVariable("a", Option.empty)), Symbol(PartialVariable("b", Option(false)))),
        Symbol(PartialVariable("c", Option.empty)))
  }

