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
