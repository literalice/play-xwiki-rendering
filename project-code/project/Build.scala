import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName         = "play-xwiki-rendering"
  val appVersion      = "1.0-SNAPSHOT"

  val xwikiVersion = "4.1.3"
  val xwikiGroup = "org.xwiki"
  val xwikiCommonsGroup = xwikiGroup + ".commons"
  val xwikiRenderingGroup = xwikiGroup + ".rendering"

  val xwikiMacros = Seq("xwiki21", "xhtml")
  val xwikiMacrosDependencies = xwikiMacros.map(
  { n => xwikiRenderingGroup % ("xwiki-rendering-syntax-" + n) % xwikiVersion })

  val xwikiTransforms = Seq("macro")
  val xwikiTransformsDependencies = xwikiTransforms.map(
  { n => xwikiRenderingGroup % ("xwiki-rendering-transformation-" + n) % xwikiVersion })

  val xwikiDependencies = Seq(
    xwikiCommonsGroup % "xwiki-commons-component-default" % xwikiVersion
  ) ++ xwikiMacrosDependencies ++ xwikiTransformsDependencies

  val appDependencies = xwikiDependencies

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    organization := "com.monochromeroad.play",
    resolvers += "XWiki Repository" at "http://nexus.xwiki.org/nexus/content/groups/public"
  )

}
