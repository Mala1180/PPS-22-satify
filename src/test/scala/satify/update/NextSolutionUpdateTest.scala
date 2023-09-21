//package satify.update
//
//import org.scalatest.flatspec.AnyFlatSpec
//import org.scalatest.matchers.should.Matchers
//import satify.model.errors.Error.EmptySolution
//import satify.model.{Assignment, State}
//import satify.update.Message.*
//
//class NextSolutionUpdateTest extends AnyFlatSpec with Matchers:
//
//  import satify.update.Update.update
//
//  "the new State" should "contain a new assignment in the Solution" in {
//    val initialState: State = update(State(), Solve("a or b"))
//    val newState = update(initialState, NextSolution())
//    println(initialState)
//    initialState.solution.get.assignments.last should not be newState.solution.get.assignments.last
//  }
//
//  "the new State" should "contain the same assignments of previous one if no more assignments are found" in {
//    val initialState: State = update(State(), Solve("a and b"))
//    val newState = update(initialState, NextSolution())
//    newState.solution.get.assignments shouldBe initialState.solution.get.assignments
//  }
//
////  "update " should "throw an IllegalStateException if " in {
////    update(State(), NextSolution()) shouldBe State()
////  }
