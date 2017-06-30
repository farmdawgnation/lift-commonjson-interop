package me.frmr.jsonutils

import org.scalactic._
import org.scalatest._
import org.scalatest.prop._
import org.scalacheck.Gen

import scalajson.ast.unsafe

class ConversionsSpec extends WordSpec
                      with Matchers
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
            case (unsafe.JObject(inValues), unsafe.JObject(outValues)) =>
              inValues should equal(outValues)

            case (unsafe.JArray(inValues), unsafe.JArray(outValues)) =>
              inValues should equal(outValues)

            case _ =>
              inputValue should equal(scalaValue)
          }
        }
      }
    }
  }
}
