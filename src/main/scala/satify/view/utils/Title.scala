package satify.view.utils

/** Enumerates the titles of the components of the application.
  * @param title the title of the component
  */
enum Title(val title: String):
  case App extends Title("Satify SAT Solver")
  case Solve extends Title("Solve")
  case SolveAll extends Title("Solve All")
  case Convert extends Title("Convert to CNF")
  case Next extends Title("Next")
  case Show extends Title("Show")
  case Import extends Title("Import CNF")
  case Export extends Title("Export CNF")
  case Exported extends Title("Exported CNF")
  case Help extends Title("Help")
  case InputTab extends Title("Input")
  case ProblemTab extends Title("Problem")
  case ErrorDialog extends Title("Error")
  case ImportDialog extends Title("Import CNF from DIMACS")
  case ExportDialog extends Title("Export CNF from DIMACS")
  case SolutionDialog extends Title("Solution")
  case ConversionDialog extends Title("Converted formula")
  case ProblemDialog extends Title("Problem visualization")
  case LoadingLabel extends Title("Loading...")
