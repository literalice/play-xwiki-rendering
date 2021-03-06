import sbt.Defaults._

scalaVersion := "2.9.1"

version := "1.0"

organization := "com.monochromeroad"

libraryDependencies += "play" % "play_2.9.1" % "2.0.3" % "provided"

libraryDependencies += "play" % "play-test_2.9.1" % "2.0.3" % "test"

libraryDependencies += "org.specs2" %% "specs2" % "1.12.1" % "test"

publishMavenStyle := true

publishArtifact in Test := false

testOptions in Test += Tests.Argument("junitxml")

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

