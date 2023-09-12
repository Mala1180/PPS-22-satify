package satify.update


object MemoHelper:

  def memoize[I, O](f: I => O): I => O = new collection.mutable.HashMap[I, O]():
    override def apply(key: I) = getOrElseUpdate(key, f(key))


object MemoExamples extends App:

  import MemoHelper.*

  // factorial as a val ( it could NOT be a def )
  val factorial: Int => Int = memoize(x =>
    println(s"Calling factorial with input $x")
    if x == 0 then 1 else x * factorial(x - 1)
  )

  println(s"Result with 5 ${factorial(5)}")
  println(s"Result with 7 ${factorial(7)}")
  println(s"Result with 3 ${factorial(3)}")
