case class Address(line1: String, pin: Int) extends Proxy {
  def self = line1
}
case class Person(name: String, address: Address)
val p = Person("asdasd", Address("line1", 23))
val p2 = p.copy(address = p.address.copy(pin = 34))
p == p2
trait Customer
case object Regular extends Customer
case object Rewards extends Customer
trait Day
case object Weekday extends Day
case object Weekend extends Day
case object X
X

