package alert.engine.logic

import io.circe.Json
import io.github.jamsesso.jsonlogic.JsonLogic

import java.util
import scala.jdk.FunctionConverters._

object JsonLogicOps {
  // TODO add error handling for other than one argument
  private val abs: Array[AnyRef] => Double = args => Math.abs(args.head.asInstanceOf[Double])
  val jsonLogic: JsonLogic =
    new JsonLogic()
      .addOperation("abs", abs.asInstanceOf[Array[AnyRef] => AnyRef].asJavaFunction)

  def ruleApplies(rule: Json, context: util.Map[String, Any]): Boolean = {
    jsonLogic.apply(rule.toString, context).asInstanceOf[Boolean]
  }
}
