val quillMacroVersion = sys.props.getOrElse("plugin.version", "0.9.0")

resolvers += Resolver.sonatypeRepo("releases")

resolvers += Resolver.sonatypeRepo("snapshots")

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.0")

addSbtPlugin("com.github.ajozwik" % "sbt-quill-crud-generic" % quillMacroVersion)

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")

addSbtPlugin("com.sksamuel.scapegoat" %% "sbt-scapegoat" % "1.1.0")
