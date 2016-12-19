package me.frmr.jsonutils

import net.liftweb.json
import scala.json.ast.unsafe
import scala.util.control.NonFatal

/**
 * Implements the conversions from the Scala Common JSON's unsafe AST to Lift-Json's AST.
 */
object ScalaToLiftASTConversions {
  /**
   * Convert an unsafe AST to a Lift-Json AST.
   *
   * Since this conversion is from the unsafe AST, there is some amount of risk in invoking this
   * method since numbers in the unsafe AST are represented as strings. If those strings are
   * invalid numbers, you can expect to receive exceptions when you attempt to run this conversion.
   */
  def toLiftAST(input: unsafe.JValue): json.JValue = input match {
    case unsafe.JTrue =>
      json.JBool(true)

    case unsafe.JFalse =>
      json.JBool(false)

    case unsafe.JNull =>
      json.JNull

    case unsafe.JString(str) =>
      json.JString(str)

    case unsafe.JNumber(numberString) =>
      try {
        json.JInt(numberString.toInt)
      } catch {
        case NonFatal(e) =>
          json.JDouble(numberString.toDouble)
      }

    case unsafe.JArray(members) =>
      json.JArray(members.toList.map(toLiftAST))

    case unsafe.JObject(fields) =>
      json.JObject(convertFields(fields))
  }

  private def convertFields(input: Array[unsafe.JField]): List[json.JField] = {
    val resultingFields = for {
      scalafield <- input
      scalavalue = toLiftAST(scalafield.value)
    } yield {
      json.JField(scalafield.field, scalavalue)
    }

    resultingFields.toList
  }
}
