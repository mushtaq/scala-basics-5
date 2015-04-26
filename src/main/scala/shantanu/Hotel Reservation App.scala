/**
 * Created by ashish on 26/4/15.
 */
package shantanu

trait Customer
case object Regular extends Customer
case object Rewards extends Customer

trait Day
case object WeekDay extends Day
case object WeekEnd extends Day

case class RateCard(customer:Customer,day: Day, price :Int)

case class PricingStrategy(rateList: Set[RateCard]){
  def apply(cusotmer:Customer, day: Day):Int={
    rateList.find(x=>x.customer==cusotmer&&x.day==day).head.price
  }
}
case class Hotel(name:String,rating:Int,pricingStrategy: PricingStrategy){
  def getPrice(customer:Customer,stayPeriod:List[Day]):Int={
    stayPeriod.foldLeft(0)((acc,el)=>acc+pricingStrategy(customer,el))
  }
}


case class RoomResApp(hotels:Set[Hotel]){
  def getCheapPrice(customer:Customer,stayPeriod:List[Day]):String={
    hotels
      .groupBy(_.getPrice(customer,stayPeriod))
      .toList
      .sortBy(x=>x._1)
      .head
      ._2
      .toList
      .sortBy(_.rating)
      .last
      .name
  }
}
