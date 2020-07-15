resolvers += Resolver.sonatypeRepo("releases")

resolvers += Resolver.sonatypeRepo("snapshots")

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.0")

val quillMacroVersion = "0.9.0"

addSbtPlugin("com.github.ajozwik" % "sbt-quill-crud-generic" % quillMacroVersion)

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")

addSbtPlugin("com.sksamuel.scapegoat" %% "sbt-scapegoat" % "1.1.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-license-report" % "1.2.0")
