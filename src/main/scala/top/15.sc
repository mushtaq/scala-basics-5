import library.Extensions.RichSeq
import models._
val rates = Seq(
  Rate(Booking(Regular, Weekday), 110),
  Rate(Booking(Rewards, Weekday), 80),
  Rate(Booking(Regular, Weekend), 90),
  Rate(Booking(Rewards, Weekend), 80)
)

rates.indexBy(rate => rate.booking) foreach println


val index = Seq("a", "b", "c").indexBy(_.hashCode)
index foreach println
index("a".hashCode)
