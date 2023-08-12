package satify.DecisionTreeTest

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.should
import satify.model.{CNF, DecisionTree, PartialModel, TreeState, Variable}
import satify.model.DecisionTree.{Branch, *}
import satify.model.CNF.*
import satify.model.Constant.{False, True}
import satify.update.dpll.CNFSimplification.*
import satify.update.dpll.PartialModelUtils.*
import satify.update.dpll.DPLL.*

class DPLLTest extends AnyFlatSpec with Matchers:

  val varA: Variable = Variable("a")
  val varB: Variable = Variable("b")
  val varC: Variable = Variable("c")

  val cnf: CNF = And(Symbol(varA), Symbol(varB))

  "DPLL" should "accept a Decision and return a DecisionTree of type Branch" in {
    dpll(TreeState(extractModelFromCnf(cnf), cnf)).getClass shouldBe classOf[Branch]
  }

  "All solutions" should "be extractable from a PartialModel" in {
    val pm: PartialModel = Seq(Variable("a"), Variable("b"), Variable("c", Some(true)))
    explodeSolutions(pm) shouldBe
      Set(
        Seq(Variable("a", Some(true)), Variable("b", Some(true)), Variable("c", Some(true))),
        Seq(Variable("a", Some(false)), Variable("b", Some(true)), Variable("c", Some(true))),
        Seq(Variable("a", Some(true)), Variable("b", Some(false)), Variable("c", Some(true))),
        Seq(Variable("a", Some(false)), Variable("b", Some(false)), Variable("c", Some(true)))
      )
  }

  "Solutions" should "be extractable from a DecisionTree" in {
    extractSolutionsFromDT(dpll(TreeState(extractModelFromCnf(cnf), cnf))) shouldBe
      Set(Seq(Variable("a", Some(true)), Variable("b", Some(true))))
    val secCnf = And(Symbol(varA), Or(Symbol(varB), Symbol(varC)))
    val solutions =
      for pmSet <- extractSolutionsFromDT(dpll(TreeState(extractModelFromCnf(secCnf), secCnf)))
      yield explodeSolutions(pmSet)
    solutions.flatten shouldBe
      Set(
        Seq(Variable("a", Some(true)), Variable("b", Some(true)), Variable("c", Some(true))),
        Seq(Variable("a", Some(true)), Variable("b", Some(true)), Variable("c", Some(false))),
        Seq(Variable("a", Some(true)), Variable("b", Some(false)), Variable("c", Some(true)))
      )
  }

  "DPLL" should "return a specific DecisionTree for a specific CNF" in {
    dpll(TreeState(extractModelFromCnf(cnf), cnf)) shouldBe
      Branch(
        TreeState(extractModelFromCnf(cnf), cnf),
        Branch(
          TreeState(Seq(Variable("a", Some(true)), varB), Symbol(varB)),
          Branch(
            TreeState(Seq(Variable("a", Some(true)), Variable("b", Some(true))), Symbol(True)),
            Leaf,
            Leaf
          ),
          Branch(
            TreeState(Seq(Variable("a", Some(true)), Variable("b", Some(false))), Symbol(False)),
            Leaf,
            Leaf
          )
        ),
        Branch(
          TreeState(Seq(Variable("a", Some(false)), varB), Symbol(False)),
          Leaf,
          Leaf
        )
      )
  }
