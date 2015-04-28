
val m = Map(1 -> "a", 2 -> "b")

m(1)
m.apply(2)

val x = m.get(1)
val y = m.get(3)

def meth(p: Option[String]): Option[String] = p match {
  case Some(blah) => Some(s"Hello $blah")
  case None       => None
}

meth(x)
meth(y)


