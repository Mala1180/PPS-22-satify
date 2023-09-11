package satify.update.solver.dpll.utils

import satify.model.cnf.CNF
import satify.model.dpll.{Decision, PartialModel}
import satify.update.solver.dpll.Dpll.dpll
import satify.update.solver.dpll.utils.PartialModelUtils.*

object DpllUtils:

  def extractSolutions(cnf: CNF): Set[PartialModel] =
    val s =
      for pmSet <- extractSolutionsFromDT(dpll(cnf))
      yield explodeSolutions(pmSet)
    s.flatten
