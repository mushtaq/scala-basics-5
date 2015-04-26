import scala.collection.mutable

case object TypeOfDay {
  val WeekDay = "WeekDay"
  val WeekEnd = "WeekEnd"
}

case object TypeOfCustomer {
  val Guest = "Guest"
  val Rewards = "Rewards"
}

case class Rate(typeOfDay: String,
                typeOfCustomer: String,
                cost: Int)

case class ResultSet(hotelName: String,
                      starRating: Int,
                      cost: Int)

case class Hotel(hotelName: String, starRating: Int, rates: List[Rate]) {
  def GetRateForDay(dayType: String, custType: String): Int = {
    rates.filter(p => p.typeOfCustomer == custType && p.typeOfDay == dayType).head.cost
  }

  def GetRateForDays(days: List[String], custType: String): Int = {
    days.foldLeft(0)((acc, elm) => GetRateForDay(elm, custType))
  }
}

case object RateCalc {
  def CalculateRate(hotels: List[Hotel], custType: String, days: List[String]): String = {
    val calcs = hotels
      .map(x => ResultSet(x.hotelName, x.starRating, x.GetRateForDays(days, custType)))
      .sortBy(x => (x.cost, - x.starRating))
      .head
      .hotelName
  }
}

// ===== Defining values here ======
// LakeWood
val lkRateList = mutable.MutableList.empty[Rate]
lkRateList += Rate(TypeOfDay.WeekDay, TypeOfCustomer.Guest, 110)
lkRateList += Rate(TypeOfDay.WeekDay, TypeOfCustomer.Rewards, 80)
lkRateList += Rate(TypeOfDay.WeekEnd, TypeOfCustomer.Guest, 90)
lkRateList += Rate(TypeOfDay.WeekEnd, TypeOfCustomer.Rewards, 80)
val hotelLakewood = Hotel("LakeWood", 3, lkRateList.toList)

// BridgeWood
val brRateList = mutable.MutableList.empty[Rate]
brRateList += Rate(TypeOfDay.WeekDay, TypeOfCustomer.Guest, 160)
brRateList += Rate(TypeOfDay.WeekDay, TypeOfCustomer.Rewards, 110)
brRateList += Rate(TypeOfDay.WeekEnd, TypeOfCustomer.Guest, 60)
brRateList += Rate(TypeOfDay.WeekEnd, TypeOfCustomer.Rewards, 50)
val hotelBridgeWood = Hotel("BridgeWood", 4, brRateList.toList)

// RidgeWood
val rdRateList = mutable.MutableList.empty[Rate]
rdRateList += Rate(TypeOfDay.WeekDay, TypeOfCustomer.Guest, 220)
rdRateList += Rate(TypeOfDay.WeekDay, TypeOfCustomer.Rewards, 100)
rdRateList += Rate(TypeOfDay.WeekEnd, TypeOfCustomer.Guest, 150)
rdRateList += Rate(TypeOfDay.WeekEnd, TypeOfCustomer.Rewards, 40)
val hotelRidgewood = Hotel("RidgeWood", 5, rdRateList.toList)
// ===== End of Defining values ======

// All Hotels
val allHotels = List(hotelLakewood, hotelBridgeWood, hotelRidgewood)
println("==================")
RateCalc.CalculateRate(allHotels, TypeOfCustomer.Guest, List(TypeOfDay.WeekDay, TypeOfDay.WeekDay, TypeOfDay.WeekDay))
RateCalc.CalculateRate(allHotels, TypeOfCustomer.Guest, List(TypeOfDay.WeekDay, TypeOfDay.WeekEnd, TypeOfDay.WeekEnd))
RateCalc.CalculateRate(allHotels, TypeOfCustomer.Rewards, List(TypeOfDay.WeekDay, TypeOfDay.WeekDay, TypeOfDay.WeekEnd))