package com.monochromeroad.play.xwiki.rendering.plugin

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import java.io.StringReader
import java.util.Date

/**
 * Plugin for the default XWiki rendering system
 *
 * @author Masatoshi Hayashi
 */

class DefaultXWikiRenderingPluginSpec extends Specification {

  val testString = "**TEST** {{rb read='read'}}Unreadable word{{/rb}} Current {{date/}}"

  override def is = args(sequential = true) ^ super.is

  "The Default XWiki Rendering Plugin" should {
    "load default renderer" in {
      running(FakeApplication(
          additionalPlugins = Seq("com.monochromeroad.play.xwiki.rendering.plugin.DefaultXWikiRenderingPlugin"),
          additionalConfiguration = Map(
            "xwiki.rendering.default.macros.enabled" -> "true",
            "xwiki.rendering.default.macros.1" -> "com.monochromeroad.play.xwiki.rendering.plugin.RbMacro"))) {
        val result = DefaultXWikiRenderer.render(testString)
        result must contain("<ruby>")
        result must not contain("Current %tF" format new Date())
      }
    }

    "load default stream renderer" in {
      running(FakeApplication(
        additionalPlugins = Seq("com.monochromeroad.play.xwiki.rendering.plugin.DefaultXWikiRenderingPlugin"),
        additionalConfiguration = Map(
          "xwiki.rendering.default.macros.enabled" -> "false",
          "xwiki.rendering.default.macros.1" -> "com.monochromeroad.play.xwiki.rendering.plugin.RbMacro"))) {
        val result = DefaultXWikiStreamRenderer.render[String](
          new StringReader(testString), "", {(acc: String, n:String) => acc + n})
        result must not contain("<ruby>")
      }
    }

    "load default string stream renderer" in {
      running(FakeApplication(
        additionalPlugins = Seq("com.monochromeroad.play.xwiki.rendering.plugin.DefaultXWikiRenderingPlugin"),
        additionalConfiguration = Map(
          "xwiki.rendering.default.macros.1" -> "com.monochromeroad.play.xwiki.rendering.plugin.RbMacro",
          "xwiki.rendering.default.macros.2" -> "com.monochromeroad.play.xwiki.rendering.plugin.DateMacro"
        ))) {
        var result = new StringBuilder()
        DefaultXWikiStringStreamRenderer.render(new StringReader(testString), { n => result.append(n)})
        result.toString() must contain("<ruby>")
        result.toString() must contain("Current %tF" format new Date())
      }
    }
  }

}
