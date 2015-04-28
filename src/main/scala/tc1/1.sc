import tc1.Monoid

def sum[T](xs: List[T])(monoid: Monoid[T]): T =
  xs.foldLeft(monoid.zero)(monoid.combine)


sum(List(1, 2))(Monoid.intM)
sum(List("a", "b"))(Monoid.strM)
sum(List(Some(1), None, Some(2)))(
  Monoid.optionM(Monoid.intM)
)
sum(List(Some("a"), None, Some("b")))(
  Monoid.optionM(Monoid.strM)
)

sum(List((1, "a"), (2, "b")))(
  Monoid.pairM(Monoid.intM, Monoid.strM)
)

val tuples = List((Option(1), Option("a")), (Option(2), Option("b")))

sum(tuples)(
  Monoid.pairM(
    Monoid.optionM(Monoid.intM),
    Monoid.optionM(Monoid.strM)
  )
)

