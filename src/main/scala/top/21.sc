
List(9).map(x => x * x)
List.empty[Int].map(x => x * x)

Option(null)

Option(9).map(x => x * x)
Option.empty[Int].map(x => x * x)

Option(9).flatMap(x => Option(10).map(y => x * y))

for {
  x <- Option(9)
  if x > 3
  y <- Option(10)
  if y < 100
} yield x * y




