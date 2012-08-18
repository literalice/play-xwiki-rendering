package com.monochromeroad.play.xwiki.rendering

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import org.xwiki.rendering.syntax.Syntax
import java.io.StringReader
import org.xwiki.rendering.transformation.{TransformationContext, Transformation}
import org.xwiki.rendering.renderer.printer.DefaultWikiPrinter
import org.xwiki.rendering.renderer.PrintRendererFactory

/**
 * @author Masatoshi Hayashi
 */

class DefaultXWikiRenderingPluginSpec extends Specification {

  "The Default XWiki Rendering Plugin" should {
    "load default component" in {
      running(FakeApplication(
          additionalPlugins = Seq("com.monochromeroad.play.xwiki.rendering.DefaultXWikiRenderingPlugin"),
          additionalConfiguration = Map("xwiki.rendering.default.macros.1" -> "com.monochromeroad.play.xwiki.rendering.macros.DefaultRbMacro"))) {
        convert("**TEST** {{rb read='read'}}Unreadable word{{/rb}}") must contain("<ruby>")
      }
    }
  }

  private def convert(src: String): String = {
    val parser = DefaultXWikiComponentManager.getParser(Syntax.XWIKI_2_1.toIdString)
    val xdom = parser.parse(new StringReader(src))

    // Execute the Macro Transformation to execute Macros.
    val transformation = DefaultXWikiComponentManager.getInstance[Transformation]("macro")
    val txContext = new TransformationContext(xdom, parser.getSyntax)
    transformation.transform(xdom, txContext)

    // Convert input in XWiki Syntax 2.0 into XHTML. The result is stored in the printer.
    val result = new StringBuffer()
    val wikiPrinter = new DefaultWikiPrinter(result)
    val printRendererFactory = DefaultXWikiComponentManager.getInstance[PrintRendererFactory](Syntax.XHTML_1_0.toIdString)
    val renderer = printRendererFactory.createRenderer(wikiPrinter)
    xdom.traverse(renderer)
    result.toString
  }
}
