package common

case class PaymentEvent(
                         orderId: String,
                         status: String,
                         amount: Double,
                         timestamp: String
                       )