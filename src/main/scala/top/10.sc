

object A {
  val s = "asdasd"
  def square(x: Int) = x * x
}

A

A.square(100)
A.s

A: AnyRef
A: A.type

class X

val x = new X


x: x.type
x: X
x: AnyRef


val y = new X
y: X
y: AnyRef
