
1 :: 2 :: 3 :: Nil

33 +: 10 +: Seq(1, 34) :+ 99 :+ 78



val email = "mushtaq@thoughtworks.com"

email.split("@") match {
  case Array(user, domain) => s"Hello $user of $domain"
  case _                   => "error"
}


object @@ {
  def unapply(str: String): Option[(String, String)] =
    str.split("@") match {
      case Array(user, domain) => Some((user, domain))
      case _                   => None
    }
}

def sayHello(email: String) = email match {
  case "ashish" @@ domain => s"Hello ashish!!"
  case user @@ domain     => s"Hello $user of $domain"
  case _                  => "error"
}

sayHello(email)

