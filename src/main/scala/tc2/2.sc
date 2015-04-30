


def add(a: Int)(implicit b: Int) = a + b

add(10)(20)

implicit val x = 99

add(10)

def identity[A](a: A) = a
def identity1[A](implicit a: A) = a

identity("asd")
identity(100)
identity1[Int]

implicitly[Int]

