package me.frmr.liftweb

import org.scalatest._
import org.scalatest.prop._
import org.scalacheck.Gen

class ConversionsSpec extends WordSpec with ShouldMatchers with GeneratorDrivenPropertyChecks {
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
  }
}
