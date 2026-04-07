package common

case class OrderEvent(
                       orderId: String,
                       userId: String,
                       status: String,
                       timestamp: String
                     )