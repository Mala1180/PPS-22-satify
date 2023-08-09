package satify.DecisionTreeTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.should
import satify.model.{CNF, DecisionTree, PartialModel, TreeState, Variable}
import satify.model.DecisionTree.*
import satify.model.CNF.*
import satify.update.dpll.CNFSimplification.*
import satify.update.dpll.DPLL.*

class DPLLTest extends AnyFlatSpec with Matchers:

  val varA: Variable = Variable("a")
  val varB: Variable = Variable("b")

  val cnf: CNF = And(Symbol(varA), Symbol(varB))

  "DPLL" should "accept a Decision and return a DecisionTree of type Branch" in {
    dpll(TreeState(extractModelFromCnf(cnf), cnf)).getClass shouldBe classOf[Branch]
  }

  "DPLL" should "explore all the possible assignments to the model" in {
    val emptyDec = TreeState(extractModelFromCnf(cnf), cnf)
    dpll(emptyDec) shouldBe
      Branch(emptyDec,
        Branch(
          TreeState(
            Seq(Variable("a", Some(true)), varB),
            And(Symbol(Variable("a", Some(true))), Symbol(varB))
          ),
          Branch(
            TreeState(
              Seq(Variable("a", Some(true)), Variable("b", Some(true))),
              And(Symbol(Variable("a", Some(true))), Symbol(Variable("b", Some(true))))
            ), Leaf, Leaf),
          Branch(
            TreeState(
              Seq(Variable("a", Some(true)), Variable("b", Some(false))),
              And(Symbol(Variable("a", Some(true))), Symbol(Variable("b", Some(false))))
            ), Leaf, Leaf)
        ),
        Branch(
          TreeState(
            Seq(Variable("a", Some(false)), varB),
            And(Symbol(Variable("a", Some(false))), Symbol(varB))
          ),
          Branch(
            TreeState(
              Seq(Variable("a", Some(false)), Variable("b", Some(true))),
              And(Symbol(Variable("a", Some(false))), Symbol(Variable("b", Some(true))))
            ), Leaf, Leaf),
          Branch(
            TreeState(
              Seq(Variable("a", Some(false)), Variable("b", Some(false))),
              And(Symbol(Variable("a", Some(false))), Symbol(Variable("b", Some(false))))
            ), Leaf, Leaf)
        )
      )

    "A PartialModel" should "be extractable from a CNF" in {
      val parModel: PartialModel = Seq(Variable("a"), Variable("b"), Variable("c"))
      parModel shouldBe extractModelFromCnf(cnf)
    }
  }