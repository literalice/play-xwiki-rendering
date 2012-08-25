import sbt._
import Keys._
import PlayProject._
 
object ApplicationBuild extends Build {
 
  val appName         = "Sample application integrated with XWiki Rendering"
  val appVersion      = "1.0"
 
  val appDependencies = Seq(
    "com.monochromeroad" %% "play-xwiki-rendering" % "0.1.0-SNAPSHOT"
  )
 
  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    resolvers += "Local Maven Repository" at "file:///" + Path.userHome.absolutePath + "/.m2/repository"
  )
}
