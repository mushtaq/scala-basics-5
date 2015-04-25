
def x1 = 100

Option(1).getOrElse(x1)
Option.empty[Int].getOrElse(x1)


def While(cond: () => Boolean, body: () => Unit): Unit = {
  if(cond()) {
    body()
    While(cond, body)
  }
}


var x = 0

While(() => x < 10, { () =>
  println(x)
  x = x + 1
})


