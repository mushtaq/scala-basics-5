
object Person {
  private val dd = 10
  def fromString(data: String): Person = data.split("-") match {
    case Array(name, age) => Person(name, age.toInt)
    case _ => throw new RuntimeException("failed")
  }
}

case class Person(name: String, age: Int) {

  Person.dd
  val goodName = s"hello good $name"

  def hello() = 3
}

Person: Person.type
Person("bad", 33): Person
Person.fromString("mushtaq-100")



val p = new Person("ashish", 33)
val p2 = new Person("ashish", 33)
p.copy(age = 345)

p.hashCode()
p2.hashCode()

p.name

p.age
p.goodName




p == p2
p eq p2
