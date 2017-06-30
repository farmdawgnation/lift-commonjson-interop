package me.frmr.jsonutils

import net.liftweb.{json => liftjson}
import scalajson.ast.unsafe
import scalajson.ast

/**
 * Provides explicit conversion functions for converting between the standard and unsafe Scala
 * AST's. To use this in your code simply add an import to the top of your file.
 *
 * {{{
 * import me.frmr.jsonutils.JsonConverters._
 * }}}
 *
 * Then the functions provided will be available for all of your AST conversion needs..
 */
object JsonConverters {
  implicit class LiftJValueWithConverters(liftValue: liftjson.JValue) {
    def toStandardScalaAST: Option[ast.JValue] =
      toUnsafeScalaAST.map(_.toStandard)

    def toUnsafeScalaAST: Option[unsafe.JValue] =
      LiftToScalaASTConversions.toScalaAST(liftValue)
  }

  implicit class StandardASTWithConverters(scalaValue: ast.JValue) {
    def toLiftAST: liftjson.JValue =
      ScalaToLiftASTConversions.toLiftAST(scalaValue.toUnsafe)
  }

  implicit class UnsafeASTWithConverters(scalaValue: unsafe.JValue) {
    def toLiftAST: liftjson.JValue =
      ScalaToLiftASTConversions.toLiftAST(scalaValue)
  }
}
