package satify.update.solver.dpll.utils

import satify.model.CNF
import satify.model.dpll.{Decision, PartialModel}
//import satify.update.solver.dpll.Dpll.dpll
import satify.update.solver.dpll.DpllOneSol.dpll
import satify.update.solver.dpll.utils.PartialModelUtils.*

object DpllUtils:

  def extractSolutions(cnf: CNF): Set[PartialModel] =
    val s =
      for pmSet <- extractSolutionsFromDT(dpll(cnf)._1)
      yield explodeSolutions(pmSet)
    s.flatten
