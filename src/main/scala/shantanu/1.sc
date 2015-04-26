trait Customer
case object Regular extends Customer
case object Reward extends Customer

trait Day
case object Weekday extends Day
case object Weekend extends Day

trait Booking {
  val customer: Customer
  val day: Day
}

case class SimpleBooking(customer: Customer, day: Day) extends Booking
object BookingService {
  def instanceFor(customer: Customer, day: Day):Booking = {
    new SimpleBooking(customer,day)
  }
}

trait RateRule {
  def isApplicableTo(booking: Booking): Boolean
  def apply(booking: Booking): Double
}
case class WeekdayRateRule(rates: Map[Customer, Int]) extends RateRule {
  def isApplicableTo(booking: Booking) = booking.day == Weekday
  def apply(booking: Booking) = rates(booking.customer)
}
case class WeekendRateRule(rates: Map[Customer, Int]) extends RateRule {
  def isApplicableTo(booking: Booking) = booking.day == Weekend
  def apply(booking: Booking) = rates(booking.customer)
}

trait Hotel {
  def rating: Double
  def rate(booking: Booking): Double
}

abstract class AbstractHotel(rates:List[RateRule]) extends Hotel {
  def rate(booking: Booking): Double =
    rates
      .filter(_.isApplicableTo(booking))
      .map(_.apply(booking))
      .min
}

case class Lakewood(rates:List[RateRule]) extends AbstractHotel(rates) {
  override def rating: Double = 3
}
case class Bridgewood(rates:List[RateRule]) extends AbstractHotel(rates) {
  override def rating: Double = 4
}

case class Ridgewood(rates:List[RateRule]) extends AbstractHotel(rates) {
  override def rating: Double = 5
}

trait HotelProvider {
  def getAllHotels:List[Hotel]
}
case object HotelProvider extends HotelProvider{
  def getLakewood:Hotel = new Lakewood(List(
    WeekdayRateRule(Map(Regular->110, Reward->80)),
    WeekendRateRule(Map(Regular->90, Reward->80))))

  def getBridgewood:Hotel = new Bridgewood(List(
    WeekdayRateRule(Map(Regular->110, Reward->110)),
    WeekendRateRule(Map(Regular->60, Reward->50))))

  def getRidgewood:Hotel = new Ridgewood(List(
    WeekdayRateRule(Map(Regular->220, Reward->100)),
    WeekendRateRule(Map(Regular->150, Reward->40))))

  def getAllHotels:List[Hotel] = List(
    getLakewood,
    getBridgewood,
    getRidgewood
  )
}

class HotelService(val hotelProvider: HotelProvider) {
  def findHotelFor(booking: Booking):Hotel = {
    hotelProvider.getAllHotels
      .groupBy(_.rate(booking))
      .head._2
      .groupBy(_.rating)
      .head._2
      .head
  }
}

val hotelService = new HotelService(HotelProvider);
val simpleBooking = BookingService.instanceFor(Regular,Weekday)

hotelService.findHotelFor(simpleBooking)
