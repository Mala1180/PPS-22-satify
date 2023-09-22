package satify.model.errors

enum Error(val description: String):
  case InvalidInput() extends Error("Invalid input")
  case InvalidImport() extends Error("Import error, select a txt file containing a valid DIMACS CNF")
  case InvalidExport() extends Error("Export error, select a txt file to export the CNF")
  case EmptySolution() extends Error("Empty solution, no next assignment to show")
  case NoPreviousSolution() extends Error("Cannot find next solution without a previous one")
  case Unknown() extends Error("Unknown error occurred")
