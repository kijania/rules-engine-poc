package alert.engine.domain.balances

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

import scala.jdk.CollectionConverters._

import java.util

case class FundsBalance(
                         personal: Double,
                         secondPersonal: Double,
                         savings: Double,
                         brokerage: Double,
                         currency: String,
                         operator: String
                       ) {
  def toJsonLogicData: util.Map[String, Any] =
    this.productElementNames.zip(this.productIterator).toMap.asJava
}

object FundsBalance {
  implicit val decoder: Decoder[FundsBalance] = deriveDecoder[FundsBalance]
}