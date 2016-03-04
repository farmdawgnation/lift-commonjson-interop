package me.frmr.liftweb

import net.liftweb.json
import scala.json.ast.fast
import scala.scalajs.js

object ScalaToLiftASTConversions {
  def toLiftAST(input: fast.JValue): json.JValue = input match {
    case fast.JTrue =>
      json.JBool(true)

    case fast.JFalse =>
      json.JBool(false)

    case fast.JNull =>
      json.JNull

    case fast.JString(str) =>
      json.JString(str)

    case fast.JNumber(numberString) =>
      try {
        json.JInt(numberString.toInt)
      } catch {
        case e: Exception =>
          json.JDouble(numberString.toDouble)
      }

    case fast.JArray(members) =>
      json.JArray(members.toList.map(toLiftAST))

    case fast.JObject(fields) =>
      json.JObject(convertFields(fields))
  }

  private def convertFields(input: Array[fast.JField]): List[json.JField] = {
    val resultingFields = for {
      scalafield <- input
      scalavalue = toLiftAST(scalafield.value)
    } yield {
      json.JField(scalafield.field, scalavalue)
    }

    resultingFields.toList
  }
}
