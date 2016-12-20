# LiftJSON / Scala JSON AST Interop

[![Build Status](https://travis-ci.org/farmdawgnation/liftjson-scalajson-interop.svg?branch=master)](https://travis-ci.org/farmdawgnation/liftjson-scalajson-interop)

This package implements interoperability between lift-json's AST and the Scala JSON AST that
is being proposed for inclusion in the Scala language. The motivation of the new Scala
AST that has been proposed is to provide true interoperability between all libraries that talk in
JSON across the Scala language.

In the current ecosystem, you're not guaranteed compatibility between json4s, play-json, spay-json,
and any other library. This is particularly problematic as applications grow and evolve in
complexity because they could transitively pull in multiple JSON libraries. The result is that
when this happens engineers are having to manually deal with converting ASTs from one half of their
application to the other. Libraries like databinder-dispatch require users to think about what
JSON library they might be using. And ensuring the correctness of conversions between ASTs is
sometimes nontrivial and, if done in application code, likely not well documented.

## Using

To use this library in your application, simply include it along with your Lift and scala-json-ast
dependency.

```scala
libraryDependencies ++= Seq(
  "net.liftweb"       %% "lift-json"                  % "3.0.1",
  "org.mdedetrich"    %% "scala-json-ast"             % "1.0.0-M6",
  "me.frmr.jsonutils" %% "liftjson-scalajson-interop" % "0.1.0"
)
```

After this is included, you can start converting between the Standard and Unsafe AST and Lift's AST
using simple converter functions.

```scala
// Bring the methods into scope.
import me.frmr.jsonutils.JsonConverters._

// Convert Lift to Standard AST.
liftJsonObj.toStandardScalaAST        // => Option[scala.json.ast.JValue]

// Convert Lift to Unsafe AST.
liftJsonObj.toUnsafeScalaAST          // => Option[scala.json.ast.unsafe.JValue]

// Convert standard AST to Lift AST
standardObj.toLiftAST                 // => net.liftweb.json.JValue

// Convert unsafe AST to Lift AST
unsafeObj.toLiftAST                   // => net.liftweb.json.JValue
```

### On JNothing

You'll notice that converting the Lift AST to the Scala AST will produce an `Option`. This is
because Lift's AST includes the concept of a `JNothing` in its AST. This is effectively the same
as no value, and disappears during the conversion.

## About Me

My name is Matt Farmer. I'm a developer in Atlanta, GA, with passions for beer, coffee, and Scala.
You can find me [on Twitter](https://twitter.com/farmdawgnation) or at my
[blog](https://farmdawgnation.com).
