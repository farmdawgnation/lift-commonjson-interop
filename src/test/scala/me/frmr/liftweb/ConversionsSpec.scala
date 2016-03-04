package me.frmr.liftweb

import org.scalactic._
import org.scalatest._
import org.scalatest.prop._
import org.scalacheck.Gen

import scala.json.ast.fast

class ConversionsSpec extends WordSpec
                      with ShouldMatchers
                      with GeneratorDrivenPropertyChecks
                      with PrettyMethods {
  "Conversions" when {
    "from Lift" should {
      "be reversable" in {
        forAll(LiftASTGenerators.topJValue) { inputValue =>
          val scalaValue = LiftToScalaASTConversions.toScalaAST(inputValue).get
          val reversedValue = ScalaToLiftASTConversions.toLiftAST(scalaValue)

          inputValue should equal(reversedValue)
        }
      }
    }

    "from Scala" should {
      "be reversable" in {
        forAll(ScalaASTGenerators.topJValue) { inputValue =>
          val liftValue = ScalaToLiftASTConversions.toLiftAST(inputValue)
          val scalaValue = LiftToScalaASTConversions.toScalaAST(liftValue).get

          // We have to provide special handling for arrays. It seems scalatest
          // is smart enough to do the Right Thing™ if the top type of your
          // should is actually an Array, but if you're comparing a case class
          // that contains an Array the bad things happen?
          (inputValue, scalaValue) match {
            case (fast.JObject(inValues), fast.JObject(outValues)) =>
              inValues should equal(outValues)

            case (fast.JArray(inValues), fast.JArray(outValues)) =>
              inValues should equal(outValues)

            case _ =>
              inputValue should equal(scalaValue)
          }
        }
      }
    }
  }
}
