package satify.update.solver.dpll.utils

import satify.model.cnf.CNF
import satify.model.Assignment
import satify.update.solver.dpll.Dpll.dpll
import satify.model.dpll.PartialAssignment.*

object DpllUtils:

  def extractSolutions(cnf: CNF): List[Assignment] =
    val s =
      for partialAssignment <- extractParAssignments(dpll(cnf))
      yield partialAssignment.toAssignments
    s.flatten
