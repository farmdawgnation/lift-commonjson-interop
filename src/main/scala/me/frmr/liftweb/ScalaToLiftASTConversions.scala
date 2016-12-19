package me.frmr.liftweb

import net.liftweb.json
import scala.json.ast.unsafe

object ScalaToLiftASTConversions {
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
        case e: Exception =>
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
