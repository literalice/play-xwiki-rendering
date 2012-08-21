package com.monochromeroad.play.xwiki.rendering

import org.xwiki.rendering.syntax.Syntax
import java.io.{Reader, StringReader}
import org.xwiki.rendering.renderer.printer.DefaultWikiPrinter
import org.xwiki.rendering.transformation.Transformation
import org.xwiki.rendering.block.XDOM

/**
 * XWiki rendering system
 *
 * @author Masatoshi Hayashi
 */
class XWikiRenderer(val componentManager: XWikiComponentManager) extends XWikiRenderingSystem {

  def render(source :Reader, input: Syntax, output: Syntax, transformations: Transformation*): String = {
    val xdom = buildXDOM(componentManager, source, input)
    transformWithMacro(componentManager, xdom, input, transformations)
    convertToString(xdom, output)
  }

  def render(source :Reader, transformations: Transformation*): String = {
    render(source, Syntax.XWIKI_2_1, Syntax.XHTML_1_0, transformations: _*)
  }

  def render(source :Reader, input: Syntax, transformations: Transformation*): String = {
    render(source, input, Syntax.XHTML_1_0, transformations: _*)
  }

  def render(source :String, input: Syntax, output: Syntax, transformations: Transformation*): String = {
    render(new StringReader(source), input, output, transformations: _*)
  }

  def render(source :String, input: Syntax, transformations: Transformation*): String = {
    render(source, input, Syntax.XHTML_1_0, transformations: _*)
  }

  def render(source :String, transformations: Transformation*): String = {
    render(source, Syntax.XWIKI_2_1, Syntax.XHTML_1_0, transformations: _*)
  }

  private def convertToString(xdom: XDOM, syntax: Syntax): String = {
    val printer = new DefaultWikiPrinter()
    applyRenderer(componentManager, xdom, syntax, printer)
    printer.toString
  }

}

/**
 * Default XWiki rendering system in the Play XWiki Plugin.
 *
 * @author Masatoshi Hayashi
 */
object DefaultXWikiRenderer extends XWikiRenderer(DefaultXWikiComponentManager)

