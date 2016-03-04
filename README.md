# Interop between lift-json and the proposed Scala Json AST.

This package demonstrates the interoperability between lift-json's AST and the Scala JSON AST that
is being proposed for inclusion in the Scala language proper. The motivation of the new Scala
AST that has been proposed is to provide true interoperability between all libraries that talk in
JSON across the Scala language.

In the current ecosystem, you're not guaranteed compatibility between json4s, play-json, spay-json,
and any other library. This is particularly problematic as applications grow and evolve in
complexity because they could transitively pull in multiple JSON libraries. The result is that
when this happens engineers are having to manually deal with converting ASTs from one half of their
application to the other. Libraries like databinder-dispatch require users to think about what
JSON library they might be using. And ensuring the correctness of conversions between ASTs is
sometimes nontrivial and, if done in application code, likely not well documented.

This PoC project serves two purposes.

1. To serve as a gap analysis between lift-json's API and the proposed Scala JSON API.
2. To test and ensure that conversion between the two ASTs is actually possible.

If you find any issues or have concerns, please file an issue and let me know.
