import tc2.Monoid

def sum[T: Monoid](xs: List[T]): T =
  xs.foldLeft(Monoid[T].zero)(Monoid[T].combine)


sum(List(1, 2))
sum(List("a", "b"))
sum(List(Some(1), None, Some(2)))
sum(List(Some("a"), None, Some("b")))
sum(List((1, "a"), (2, "b")))
sum(List((Option(1), Option("a")), (Option(2), Option("b"))))

{
  implicit val intM = new Monoid[Int] {
    def zero = 0
    def combine(a: Int, b: Int) = a + b + 100
  }

  sum(List((Option(1), Option("a")), (Option(2), Option("b"))))
}


sum(List((Option(1), Option("a")), (Option(2), Option("b"))))
