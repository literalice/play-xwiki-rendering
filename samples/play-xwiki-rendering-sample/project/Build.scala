import sbt._
import Keys._
import PlayProject._
 
object ApplicationBuild extends Build {
 
  val appName         = "Sample application integrated with XWiki Rendering"
  val appVersion      = "1.0"
 
  val appDependencies = Seq(
    "com.monochromeroad" %% "play-xwiki-rendering" % "1.0",
    "org.xwiki.rendering" % "xwiki-rendering-macro-comment" % "4.1.3"
  )
 
  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    resolvers += "Monochrmeroad CloudBees Repository" at "http://repository-monochromeroad.forge.cloudbees.com/release"
    // resolvers += "Local Maven Repository" at "file:///" + Path.userHome.absolutePath + "/.m2/repository"
  )
}
