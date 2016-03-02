package me.frmr.liftweb

import net.liftweb.json
import scala.json.ast.fast
import scala.scalajs.js

object LiftToScalaASTConversions {
  def toScalaAST(input: json.JValue): fast.JValue = input match {
    case json.JString(string) =>
      fast.JString(string)

    case json.JBool(true) =>
      fast.JTrue

    case json.JBool(false) =>
      fast.JFalse

    case json.JDouble(number) =>
      fast.JNumber(number)

    case json.JInt(number) =>
      fast.JNumber(number)

    case json.JNothing | json.JNull =>
      fast.JNull

    case json.JArray(items) =>
      fast.JArray(items.map(toScalaAST).toArray)

    case json.JObject(fields) =>
      fast.JObject(convertFields(fields))
  }

  private def convertFields(input: List[json.JField]): Array[fast.JField] = {
    val resultingFields = for (liftfield <- input) yield {
      fast.JField(liftfield.name, toScalaAST(liftfield.value))
    }

    resultingFields.toArray
  }
}
