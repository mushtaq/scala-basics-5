trait Customer
case object Regular extends Customer
case object Reward extends Customer

trait Day
case object Weekday extends Day
case object Weekend extends Day


class Hotel( val name: String
  , val rating: Int
  , rateCalcStrategy : (Customer,Day) => Int )
{
  def costPerDay(customer: Customer, day: Day): Int = rateCalcStrategy(customer,day)
}

object Hotels {
  type T = (Customer, Day)
  private val tupleToInt: Map[T, Int] = Map(
    (Regular, Weekday) -> 110,
    (Reward, Weekday) -> 80,
    (Regular, Weekend) -> 90,
    (Reward, Weekend) -> 80
  )

  tupleToInt: T => Int

  val value = Array(
    new Hotel("Lakewood"
      , 3
      ,tupleToInt
    )
    ,
    new Hotel("Bridgewood"
      , 4
      , (customer,day) => (customer,day) match{
        case (Regular, Weekday) => 160
        case (Reward, Weekday) => 110
        case (Regular, Weekend) => 60
        case (Reward, Weekend) => 50
      }
    )
    ,
    new Hotel("Ridgewood"
      , 5
      , (customer,day) => (customer,day) match{
        case (Regular, Weekday) => 220
        case (Reward, Weekday) => 100
        case (Regular, Weekend) => 150
        case (Reward, Weekend) => 40
      }
    )
  )
}

case class Deal(hotel: Hotel
  , customer: Customer
  , days : Array[Day])
  extends math.Ordered[Deal]
{
  val price = days.map(hotel.costPerDay(customer,_)).sum

  def compare(that : Deal) = {
    /*
    println(this.hotel.name, this.price)
    println(that.hotel.name,  that.price)
    println("---")
    */
    if ( this.price > that.price )
      -1
    else if ( this.price < that.price)
      1
    else
    if (this.hotel.rating < that.hotel.rating )
      -1
    else
      1
  }
}

class HotelAdviser(hotels : Array[Hotel]) {
  def getBestDeal(customer: Customer, days: Array[Day]) : Deal =
    hotels.map(Deal(_, customer, days)).max
}

val hotelAdviser = new HotelAdviser(Hotels.value)
val bestDeal = hotelAdviser.getBestDeal(Regular, Array(Weekday,Weekday,Weekday))
bestDeal.hotel.name
bestDeal.price
val bestDeal2 = hotelAdviser.getBestDeal(Regular, Array(Weekday,Weekend,Weekend))
bestDeal2.hotel.name
bestDeal2.price
val bestDeal3 = hotelAdviser.getBestDeal(Reward, Array(Weekday,Weekday,Weekend))
bestDeal3.hotel.name
bestDeal3.price

