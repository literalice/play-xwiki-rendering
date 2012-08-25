package com.monochromeroad.play.xwiki.rendering

import org.xwiki.rendering.syntax.Syntax
import java.io.{Reader, StringReader}
import org.xwiki.rendering.renderer.printer.DefaultWikiPrinter
import org.xwiki.rendering.transformation.Transformation

/**
 * XWiki Rendering System -- XDOM based
 *
 * <br /><br />
 *
 * It needs to create a XDOM represented the document structure in a memory.<br />
 * Default syntax: xwiki/2.1 output: xhtml/1.0
 *
 * @author Masatoshi Hayashi
 */
class XWikiRenderer(componentManager: XWikiComponentManager,
                    configuration: XWikiRendererConfiguration = new XWikiRendererConfiguration())
  extends XWikiRenderingSystem(componentManager, configuration) {

  /**
   * XWiki rendering
   *
   * @param source Input stream
   * @param input Input syntax
   * @param output Output syntax
   * @param transformations Transformations on a XDOM
   * @return Result
   */
  def render(source :Reader, input: Syntax, output: Syntax, transformations: Transformation*): String = {
    val printer = new DefaultWikiPrinter()
    renderOnXDOM(source, input, output, transformations, printer)
    printer.toString
  }

  /**
   * XWiki XHTML rendering
   *
   * @param source Input stream
   * @param input Input syntax
   * @param transformations Transformations on a XDOM
   * @return Result
   */
  def render(source :Reader, input: Syntax, transformations: Transformation*): String = {
    render(source, input, configuration.defaultOutputSyntax, transformations: _*)
  }

  /**
   * XWiki XHTML rendering in a stream using default syntax.
   *
   * @param source Input stream
   * @return Result
   */
  def render(source :Reader, transformations: Transformation*): String = {
    render(source, configuration.defaultInputSyntax, configuration.defaultOutputSyntax, transformations: _*)
  }

  /**
   * XWiki rendering
   *
   * @param source Input stream
   * @param input Input syntax
   * @param output Output syntax
   * @param transformations Transformations on a XDOM
   * @return Result
   */
  def render(source :String, input: Syntax, output: Syntax, transformations: Transformation*): String = {
    render(new StringReader(source), input, output, transformations: _*)
  }

  /**
   * XWiki XHTML rendering
   *
   * @param source Input stream
   * @param input Input syntax
   * @param transformations Transformations on a XDOM
   * @return Result
   */
  def render(source :String, input: Syntax, transformations: Transformation*): String = {
    render(source, input, configuration.defaultOutputSyntax, transformations: _*)
  }

  /**
   * XWiki XHTML rendering in a stream using default syntax.
   *
   * @param source Input stream
   * @return Result
   */
  def render(source :String, transformations: Transformation*): String = {
    render(source, configuration.defaultInputSyntax, configuration.defaultOutputSyntax, transformations: _*)
  }

}

/**
 * Default XWiki rendering system in the Play XWiki Plugin.
 *
 * @author Masatoshi Hayashi
 */
object DefaultXWikiRenderer extends XWikiRenderer(DefaultXWikiComponentManager)

