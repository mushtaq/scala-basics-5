
object A {
  object B extends AnyRef
  lazy val B: B.type = new AnyRef{}
  val C = 100
}


A.B

