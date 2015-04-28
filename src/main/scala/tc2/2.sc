


def add(a: Int)(implicit b: Int) = a + b

add(10)(20)

implicit val x = 100

add(10)
