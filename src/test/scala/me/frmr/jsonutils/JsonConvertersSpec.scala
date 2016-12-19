package me.frmr.jsonutils

import org.scalatest._
import me.frmr.jsonutils.JsonConverters._
import net.liftweb.{json => liftjson}
import scala.json.ast
import scala.json.ast.unsafe

class JsonConvertersSpec extends WordSpec with Matchers {
  "JsonConverters" should {
    "work on lift-json JValues" in new SpecContext {
      liftJsonRepr.toStandardScalaAST.get should equal(standardJsonRepr)
      liftJsonRepr.toUnsafeScalaAST.get should equal(unsafeJsonRepr)
    }

    "work on scala standard JValues" in new SpecContext {
      unsafeJsonRepr.toLiftAST should equal(liftJsonRepr)
    }

    "work on scala unsafe JValues" in new SpecContext {
      standardJsonRepr.toLiftAST should equal(liftJsonRepr)
    }
  }

  trait SpecContext {
    val liftJsonRepr = liftjson.JObject(
      liftjson.JField("name", liftjson.JString("Matt Farmer")) ::
      liftjson.JField("occupation", liftjson.JString("Magician")) ::
      liftjson.JField("age", liftjson.JInt(27)) ::
      Nil
    )

    val standardJsonRepr = ast.JObject(Map(
      "name" -> ast.JString("Matt Farmer"),
      "occupation" -> ast.JString("Magician"),
      "age" -> ast.JNumber("27")
    ))

    val unsafeJsonRepr = unsafe.JObject(Array(
      unsafe.JField("name", unsafe.JString("Matt Farmer")),
      unsafe.JField("occupation", unsafe.JString("Magician")),
      unsafe.JField("age", unsafe.JNumber("27"))
    ))
  }
}
