package com.monochromeroad.play.xwiki.rendering

import macros.RbMacro
import org.specs2.mutable._

import org.xwiki.rendering.syntax.Syntax
import java.io.StringReader
import org.xwiki.rendering.transformation.{TransformationContext, Transformation}
import org.xwiki.rendering.renderer.PrintRendererFactory
import org.xwiki.rendering.renderer.printer.DefaultWikiPrinter

/**
 * @author Masatoshi Hayashi
 */

class XWikiComponentManagerSpec extends Specification {

  "Ruby tag" should {
    "contains ruby tag" in {
      convert("**TEST** {{rb read='read'}}Unreadable word{{/rb}}") must contain("<ruby>")
    }
  }

  private def convert(src: String): String = {
    val componentManager = new XWikiComponentManager(getClass.getClassLoader)
    componentManager.registerMacro(classOf[RbMacro])

    val parser = componentManager.getParser(Syntax.XWIKI_2_1.toIdString)
    val xdom = parser.parse(new StringReader(src))

    // Execute the Macro Transformation to execute Macros.
    val transformation = componentManager.getInstance[Transformation]("macro")
    val txContext = new TransformationContext(xdom, parser.getSyntax)
    transformation.transform(xdom, txContext)

    // Convert input in XWiki Syntax 2.0 into XHTML. The result is stored in the printer.
    val result = new StringBuffer()
    val wikiPrinter = new DefaultWikiPrinter(result)
    val printRendererFactory = componentManager.getInstance[PrintRendererFactory](Syntax.XHTML_1_0.toIdString)
    val renderer = printRendererFactory.createRenderer(wikiPrinter)
    xdom.traverse(renderer)
    result.toString
  }
}
