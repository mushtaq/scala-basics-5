
class Person(val name: String, val age: Int)

object A {
  def apply(name: String, age: Int): Person = new Person(name, age)

  def unapply(p: Person): Option[(String, Int)] = Some((p.name, p.age))
}

val p = A("ashish", 33)
val q: Person = p


val A(nm, ag) = p
def m(p: Any) = p match {
  case A(n, a) => s"Hello $n of age $a"
  case _       => "error"
}


def n(p: Any): Any = p match {
  case 1 => 1
  case "asdasd" => "hello"
  case t @ (100, b: String) if b.length < 4 => s"Hello $b"
  case (a, b) => a
  case Seq(a, b) =>
  case Some(blah) =>
  case List(head, tail @ _*) =>
  case ::(A(n1, a1), tail) =>
  case head :: tail =>
  case head #:: tail =>
  case x =>
}

n(1)
n("asdasd")
n((100, "asdasd"))







