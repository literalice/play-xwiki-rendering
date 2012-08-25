import sbt.Defaults._

scalaVersion := "2.9.1"

version := "0.1.0-SNAPSHOT"

organization := "com.monochromeroad"

libraryDependencies += "play" %% "play" % "2.0.3" % "provided"

libraryDependencies += "play" %% "play-test" % "2.0.3" % "test"

libraryDependencies += "org.specs2" %% "specs2" % "1.12.1" % "test"

publishMavenStyle := true

credentials += {
  val credsFile = (Path.userHome / ".ivy2" / ".credentials" / "repository-monochromeroad.forge.cloudbees.com")
    (if (credsFile.exists) Credentials(credsFile)
     else Credentials(file("/private/monochromeroad/.credentials/repository-monochromeroad.forge.cloudbees.com")))
}

publishArtifact in Test := false

pomExtra := (
  <url>http://github.com/literalice/play-xwiki-rendering</url>
  <inceptionYear>2012</inceptionYear>
  <licenses>
    <license>
      <name>GNU Lesser General Public License (LGPL), version 2.1</name>
      <url>http://www.gnu.org/licenses/lgpl-2.1.txt</url>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:literalice/play-xwiki-rendering.git</url>
    <connection>scm:git:git@github.com:literalice/play-xwiki-rendering.git</connection>
  </scm>
  <developers>
    <developer>
      <name>Masatoshi Hayashi</name>
      <email>literalice@monochromeroad.com</email>
      <url>http://blog.monochromeroad.com/</url>
      <roles>
        <role>Author</role>
      </roles>
      <organization>LITERAL-ICE</organization>
    </developer>
  </developers>)

