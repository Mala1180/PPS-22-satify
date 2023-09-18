package satify.view.utils

import satify.model.problems.NurseScheduling

enum ProblemTitle(val title: String):
  case NQueens extends ProblemTitle("N-Queens")
  case GraphColoring extends ProblemTitle("Graph Coloring")
  case NurseScheduling extends ProblemTitle("Nurse Scheduling")

enum Placeholders(val description: String):
  case QueensNumbers extends Placeholders("N. queens")
  case GraphColoringNodes extends Placeholders("Nodes: n1, n2, n3, ...")
  case GraphColoringEdges extends Placeholders("Edges: n1-n2, n2-n3, ...")
  case GraphColoringColors extends Placeholders("N. colors")
  case NurseSchedulingNurses extends Placeholders("N. nurses")
  case NurseSchedulingDays extends Placeholders("Days")
  case NurseSchedulingShifts extends Placeholders("Shifts")
