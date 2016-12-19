package me.frmr.liftweb

import net.liftweb.json
import scala.json.ast.unsafe

object LiftToScalaASTConversions {
  def toScalaAST(input: json.JValue): Option[unsafe.JValue] = input match {
    case json.JString(string) =>
      Some(unsafe.JString(string))

    case json.JBool(true) =>
      Some(unsafe.JTrue)

    case json.JBool(false) =>
      Some(unsafe.JFalse)

    case json.JDouble(number) =>
      Some(unsafe.JNumber(number))

    case json.JInt(number) =>
      Some(unsafe.JNumber(number))

    case json.JNull =>
      Some(unsafe.JNull)

    case json.JArray(items) =>
      Some(unsafe.JArray(items.flatMap(toScalaAST).toArray))

    case json.JObject(fields) =>
      Some(unsafe.JObject(convertFields(fields)))

    case json.JNothing =>
      None
  }

  private def convertFields(input: List[json.JField]): Array[unsafe.JField] = {
    val resultingFields = for {
      liftfield <- input
      liftvalue <- toScalaAST(liftfield.value)
    } yield {
      unsafe.JField(liftfield.name, liftvalue)
    }

    resultingFields.toArray
  }
}
