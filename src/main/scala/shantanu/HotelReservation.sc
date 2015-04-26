import shantanu._


val lwRateList=Set(RateCard(Regular,WeekDay,110),RateCard(Rewards,WeekDay,80),RateCard(Regular,WeekEnd,90),RateCard(Rewards,WeekEnd,80))
val lwStrat=PricingStrategy(lwRateList)

val lwHotel =Hotel("LakeWood", 3, lwStrat)

val bwRateList=Set(RateCard(Regular,WeekDay,160),RateCard(Rewards,WeekDay,110),RateCard(Regular,WeekEnd,60),RateCard(Rewards,WeekEnd,50))
val bwStrat=PricingStrategy(bwRateList)

val bwHotel =Hotel("BridgeWood", 4, bwStrat)

val rwRateList=Set(RateCard(Regular,WeekDay,220),RateCard(Rewards,WeekDay,100),RateCard(Regular,WeekEnd,150),RateCard(Rewards,WeekEnd,40))
val rwStrat=PricingStrategy(rwRateList)

val rwHotel =Hotel("RiverWood", 5, rwStrat)







val myApp= RoomResApp(Set(lwHotel,bwHotel, rwHotel))

myApp.getCheapPrice(Regular,List(WeekDay,WeekDay, WeekEnd))
myApp.getCheapPrice(Rewards,List(WeekEnd, WeekEnd))
myApp.getCheapPrice(Regular,List(WeekEnd, WeekDay,WeekDay))
myApp.getCheapPrice(Rewards,List(WeekDay))