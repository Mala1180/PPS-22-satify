package satify.update.solver.dpll.utils

import satify.model.cnf.CNF
import satify.model.Assignment
import satify.update.solver.dpll.Dpll.dpll
import satify.update.solver.dpll.utils.PartialModelUtils.*

object DpllUtils:

  def extractSolutions(cnf: CNF): List[Assignment] =
    val s =
      for pmSet <- extractParAssignments(dpll(cnf))
      yield explodeAssignments(pmSet)
    s.flatten
