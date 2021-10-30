val quillMacroVersion = sys.props.get("plugin.version") match {
  case Some(pluginVersion) =>
    pluginVersion
  case _ =>
    "1.0.1"
}

resolvers += Resolver.sonatypeRepo("releases")

resolvers += Resolver.sonatypeRepo("snapshots")

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.3")

addSbtPlugin("com.github.ajozwik" % "sbt-quill-crud-generic" % quillMacroVersion)

addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.4.16")

addSbtPlugin("com.sksamuel.scapegoat" %% "sbt-scapegoat" % "1.1.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-license-report" % "1.2.0")
