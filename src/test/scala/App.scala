import alert.engine.domain.alerts.AlertRule
import alert.engine.domain.balances.FundsBalance
import alert.engine.logic.JsonLogicOps.ruleApplies
import io.circe.parser.decode
import org.scalatest.EitherValues._
import zio._

import java.util
import scala.io.Source
import scala.util.Try

object App extends ZIOAppDefault {
    def alertRules(currency: String, operator: String): Seq[AlertRule] =
      Try(decode[List[AlertRule]](Source.fromResource("alert-rules/config.json").mkString).value).getOrElse(Nil) ++
      Try(decode[List[AlertRule]](Source.fromResource(s"alert-rules/$currency/config.json").mkString).value).getOrElse(Nil) ++
      Try(decode[List[AlertRule]](Source.fromResource(s"alert-rules/$currency/$operator/config.json").mkString).value).getOrElse(Nil)
  val fundsBalance: FundsBalance = decode[FundsBalance](Source.fromResource("funds_balance.json").mkString).value
  val context: util.Map[String, Any] = fundsBalance.toJsonLogicData

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    val alerts = alertRules(fundsBalance.currency, fundsBalance.operator)
      .collect { case AlertRule(alert, rule) if ruleApplies(rule, context) => alert }
    Console.printLine(s"Alerts:\n${alerts.mkString("\n")}")
  }
}







