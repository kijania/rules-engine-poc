package alert.engine.domain.alerts

import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, Json}

case class AlertRule(alert: String, rule: Json)

object AlertRule {
  implicit val decoder: Decoder[AlertRule] = deriveDecoder[AlertRule]
}