package me.frmr.jsonutils

import org.scalacheck.Gen

import net.liftweb.json._

object LiftASTGenerators {
  def jString = for {
    innerString <- Gen.alphaStr
  } yield {
    JString(innerString)
  }

  def jInt = for {
    innerInt <- Gen.Choose.chooseInt.choose(Integer.MIN_VALUE, Integer.MAX_VALUE)
  } yield {
    JInt(innerInt)
  }

  def jDouble = for {
    innerDouble <- Gen.Choose.chooseDouble.choose(java.lang.Double.MIN_VALUE, java.lang.Double.MAX_VALUE)
  } yield {
    JDouble(innerDouble)
  }

  def jBool = for {
    innerBool <- Gen.oneOf(false, true)
  } yield {
    JBool(innerBool)
  }

  val jNull = Gen.oneOf(JNull :: Nil)

  def jArray = for {
    innerValues <- Gen.containerOf[List, JValue](jValue)
  } yield {
    JArray(innerValues)
  }

  def jField = for {
    fieldName <- Gen.alphaStr
    fieldValue <- jValue
  } yield {
    JField(fieldName, fieldValue)
  }

  def jObject = for {
    fields <- Gen.containerOf[List, JField](jField)
  } yield {
    JObject(fields)
  }

  def jValue: Gen[JValue] = Gen.oneOf(jString, jInt, jDouble, jBool, jNull)

  def topJValue: Gen[JValue] = Gen.oneOf(jString, jInt, jDouble, jBool, jNull, jArray, jObject)
}
