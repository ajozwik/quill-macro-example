resolvers += Resolver.sonatypeRepo("releases")

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.0.3")

val quillMacroVersion = "0.8.5"

addSbtPlugin("com.github.ajozwik" % "sbt-quill-crud-generic" % quillMacroVersion)

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")

addSbtPlugin("com.sksamuel.scapegoat" %% "sbt-scapegoat" % "1.0.9")

addSbtPlugin("com.typesafe.sbt" % "sbt-license-report" % "1.2.0")
