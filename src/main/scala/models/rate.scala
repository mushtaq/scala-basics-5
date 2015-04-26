package models

sealed trait Day
case object Weekday extends Day
case object Weekend extends Day

sealed trait Customer
case object Regular extends Customer
case object Rewards extends Customer

case class Booking(customer: Customer, day: Day)

case class Rate(booking: Booking, price: Int)
