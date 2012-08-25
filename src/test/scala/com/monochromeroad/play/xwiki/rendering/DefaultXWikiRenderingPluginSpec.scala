package com.monochromeroad.play.xwiki.rendering

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import java.io.StringReader

/**
 * Plugin for the default XWiki rendering system
 *
 * @author Masatoshi Hayashi
 */

class DefaultXWikiRenderingPluginSpec extends Specification {

  val testString = "**TEST** {{rb read='read'}}Unreadable word{{/rb}}"

  override def is = args(sequential = true) ^ super.is

  "The Default XWiki Rendering Plugin" should {
    "load default renderer" in {
      running(FakeApplication(
          additionalPlugins = Seq("com.monochromeroad.play.xwiki.rendering.DefaultXWikiRenderingPlugin"),
          additionalConfiguration = Map(
            "xwiki.rendering.default.macros.enabled" -> "true",
            "xwiki.rendering.default.macros.1" -> "com.monochromeroad.play.xwiki.rendering.macros.DefaultRbMacro"))) {
        DefaultXWikiRenderer.render(testString) must contain("<ruby>")
      }
    }

    "load default stream renderer" in {
      running(FakeApplication(
        additionalPlugins = Seq("com.monochromeroad.play.xwiki.rendering.DefaultXWikiRenderingPlugin"),
        additionalConfiguration = Map(
          "xwiki.rendering.default.macros.enabled" -> "false",
          "xwiki.rendering.default.macros.1" -> "com.monochromeroad.play.xwiki.rendering.macros.DefaultRbMacro"))) {
        val result = DefaultXWikiStreamRenderer.render[String](
          new StringReader(testString), "", {(acc: String, n:String) => acc + n})
        result must not contain("<ruby>")
      }
    }

    "load default string stream renderer" in {
      running(FakeApplication(
        additionalPlugins = Seq("com.monochromeroad.play.xwiki.rendering.DefaultXWikiRenderingPlugin"),
        additionalConfiguration = Map(
          "xwiki.rendering.default.macros.1" -> "com.monochromeroad.play.xwiki.rendering.macros.DefaultRbMacro"))) {
        var result = new StringBuilder()
        DefaultXWikiStringStreamRenderer.render(new StringReader(testString), { n => result.append(n)})
        result.toString() must contain("<ruby>")
      }
    }
  }

}
