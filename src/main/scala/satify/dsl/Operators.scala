package satify.dsl


import scala.annotation.targetName

object Operators:

  extension (exp: Expression)

    def and(exp2: Expression): Expression = And(exp, exp2)
    def or(exp2: Expression): Expression = Or(exp, exp2)
    def not: Expression = Not(exp)
    def xor(exp2: Expression): Expression = Or(And(exp, Not(exp2)), And(Not(exp), exp2))
    def implies(exp2: Expression): Expression = Or(Not(exp), exp2)
    def iff(exp2: Expression): Expression = And(implies(exp2), exp2.implies(exp))

    @targetName("andSymbol")
    def /\(exp2: Expression): Expression = and(exp2)
    @targetName("orSymbol")
    def \/(exp2: Expression): Expression = or(exp2)
    @targetName("notSymbol")
    def unary_! : Expression = not
    @targetName("xorSymbol")
    def ^(exp2: Expression): Expression = xor(exp2)
    @targetName("impliesSymbol")
    def ->(exp2: Expression): Expression = implies(exp2)
    @targetName("iffSymbol")
    def <->(exp2: Expression): Expression = iff(exp2)
