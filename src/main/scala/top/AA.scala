package top

case class Meter(a: Int) extends AnyVal {

  def +(other: Meter) = Meter(a + other.a)

//  val state = a
}
