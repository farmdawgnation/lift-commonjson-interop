package me.frmr.liftweb

import net.liftweb.json
import scala.json.ast.fast
import scala.scalajs.js

object LiftToScalaASTConversions {
  def toScalaAST(input: json.JValue): Option[fast.JValue] = input match {
    case json.JString(string) =>
      Some(fast.JString(string))

    case json.JBool(true) =>
      Some(fast.JTrue)

    case json.JBool(false) =>
      Some(fast.JFalse)

    case json.JDouble(number) =>
      Some(fast.JNumber(number))

    case json.JInt(number) =>
      Some(fast.JNumber(number))

    case json.JNull =>
      Some(fast.JNull)

    case json.JArray(items) =>
      Some(fast.JArray(items.flatMap(toScalaAST).toArray))

    case json.JObject(fields) =>
      Some(fast.JObject(convertFields(fields)))

    case json.JNothing =>
      None
  }

  private def convertFields(input: List[json.JField]): Array[fast.JField] = {
    val resultingFields = for {
      liftfield <- input
      liftvalue <- toScalaAST(liftfield.value)
    } yield {
      fast.JField(liftfield.name, liftvalue)
    }

    resultingFields.toArray
  }
}
