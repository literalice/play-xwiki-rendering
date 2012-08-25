import sbt._
import Keys._

object ApplicationBuild extends Build {

  val xwikiVersion = "4.1.3"

  lazy val root = Project (
    id = "play-xwiki-rendering",
    base = file ("."),
    settings = Project.defaultSettings ++ Seq (
      resolvers ++= getResolvers(),
      libraryDependencies ++= getXWikiDependencies(),
      publishTo <<= version(getPublishingRepository)
    )
  )

  def getResolvers() :Seq[Resolver] = {
    Seq(
      DefaultMavenRepository,
      "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
      "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases",
      "XWiki Repository" at "http://nexus.xwiki.org/nexus/content/groups/public"
    )
  }

  def getXWikiDependencies(): Seq[ModuleID] = {
    val xwikiGroup = "org.xwiki"
    val xwikiCommonsGroup = xwikiGroup + ".commons"
    val xwikiRenderingGroup = xwikiGroup + ".rendering"

    val xwikiMacros = Seq("xwiki21", "xhtml")
    val xwikiMacrosDependencies =
      xwikiMacros map((n) => xwikiRenderingGroup % ("xwiki-rendering-syntax-" + n) % xwikiVersion)

    val xwikiTransforms = Seq("macro")
    val xwikiTransformsDependencies =
      xwikiTransforms map(n => xwikiRenderingGroup % ("xwiki-rendering-transformation-" + n) % xwikiVersion)

    Seq(
      xwikiCommonsGroup % "xwiki-commons-component-default" % xwikiVersion
    ) ++ xwikiMacrosDependencies ++ xwikiTransformsDependencies
  }

  def getPublishingRepository(version: String): Option[Resolver] = {
    val publishToRemote = "true" == System.getProperty("publish.remote")
    if (publishToRemote) {
      val cloudbees = "https://repository-monochromeroad.forge.cloudbees.com/"
      if (version.trim.endsWith("SNAPSHOT")) {
        Some("snapshots" at cloudbees + "snapshot")
      } else {
        Some("releases" at cloudbees + "release")
      }
    } else {
      Some(Resolver.file("file",  new File(Path.userHome.absolutePath + "/.m2/repository")))
    }
  }

}

