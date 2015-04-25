class Person(age: Int, isFemale: Boolean) {
  def isWise =
    if(isFemale) age > 18 else age > 21
  def canMarry(canDrink: Int => Boolean) =
    isWise && canDrink(age)
  def getIQ(f: (Int, Boolean) => Int) =
    f(age, isFemale)
  def getIQ1(f: Function2[Int, Boolean, Int]) =
    f(age, isFemale)
}
val p = new Person(30, true)
val gujStrat = { age: Int =>
  age > 50
}
val gujStrat1: Int => Boolean = _ > 50

def gujStrat2(age: Int) = age > 50


p.canMarry(_ > 10)
p.canMarry(gujStrat1)
p.canMarry(age => gujStrat2(age))

val x = gujStrat1
val x2: Int => Boolean = gujStrat2
val x3 = gujStrat2 _


p.getIQ { (age, isFemale) =>
  if(isFemale) age + 100 else 10
}

