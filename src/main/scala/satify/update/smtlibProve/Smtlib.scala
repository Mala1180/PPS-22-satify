package satify.update.smtlibProve

import smtlib.theories.Ints.{Add, LessThan, NumeralLit}
import smtlib.trees.Commands.*
import smtlib.trees.Terms.{QualifiedIdentifier, SSymbol, SimpleIdentifier, Term}

object Smtlib:

  @main def main(): Unit = {
    val x: Term = QualifiedIdentifier(SimpleIdentifier(SSymbol("x")))
    val y: Term = QualifiedIdentifier(SimpleIdentifier(SSymbol("y")))
    val formula = Assert(LessThan(NumeralLit(0), Add(x, y)))
    println(smtlib.printer.
    println(smtlib.printer.RecursivePrinter.toString(formula)) // (assert (< 0 (+ x y)))
  }
