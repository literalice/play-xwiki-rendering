package com.monochromeroad.play.xwiki.rendering

import org.xwiki.rendering.syntax.Syntax
import java.io.{Reader, StringReader}
import org.xwiki.rendering.renderer.printer.DefaultWikiPrinter
import org.xwiki.rendering.transformation.Transformation
import org.xwiki.rendering.block.XDOM

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
class XWikiRenderer(val componentManager: XWikiComponentManager) extends XWikiRenderingSystem {

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
    val xdom = buildXDOM(componentManager, source, input)
    transform(componentManager, xdom, input, transformations)
    convertToString(xdom, output)
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
    render(source, input, defaultOutputSyntax, transformations: _*)
  }

  /**
   * XWiki XHTML rendering in a stream using default syntax.
   *
   * @param source Input stream
   * @return Result
   */
  def render(source :Reader, transformations: Transformation*): String = {
    render(source, defaultInputSyntax, defaultOutputSyntax, transformations: _*)
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
    render(source, input, defaultOutputSyntax, transformations: _*)
  }

  /**
   * XWiki XHTML rendering in a stream using default syntax.
   *
   * @param source Input stream
   * @return Result
   */
  def render(source :String, transformations: Transformation*): String = {
    render(source, defaultInputSyntax, defaultOutputSyntax, transformations: _*)
  }

  /**
   * XWiki rendering with some macros
   *
   * @param source Input stream
   * @param input Input syntax
   * @param output Output syntax
   * @param transformations Transformations on a XDOM
   * @return Result
   */
  def renderWithMacros(source :Reader, input: Syntax, output: Syntax, transformations: Transformation*): String = {
    val transformationsWithMacro = List.concat(
      transformations , List(getTransformationForMacro(componentManager)))
    render(source, input, output, transformationsWithMacro:_*)
  }

  /**
   * XWiki XHTML rendering with some macros
   *
   * @param source Input stream
   * @param input Input syntax
   * @param transformations Transformations on a XDOM
   * @return Result
   */
  def renderWithMacros(source :Reader, input: Syntax, transformations: Transformation*): String = {
    renderWithMacros(source, input, defaultOutputSyntax, transformations: _*)
  }

  /**
   * XWiki XHTML rendering in a stream using default syntax.
   *
   * @param source Input stream
   * @return Result
   */
  def renderWithMacros(source :Reader, transformations: Transformation*): String = {
    renderWithMacros(source, defaultInputSyntax, defaultOutputSyntax, transformations: _*)
  }

  /**
   * XWiki rendering with Macros
   *
   * @param source Input stream
   * @param input Input syntax
   * @param output Output syntax
   * @param transformations Transformations on a XDOM
   * @return Result
   */
  def renderWithMacros(source :String, input: Syntax, output: Syntax, transformations: Transformation*): String = {
    renderWithMacros(new StringReader(source), input, output, transformations: _*)
  }

  /**
   * XWiki XHTML rendering
   *
   * @param source Input stream
   * @param input Input syntax
   * @param transformations Transformations on a XDOM
   * @return Result
   */
  def renderWithMacros(source :String, input: Syntax, transformations: Transformation*): String = {
    renderWithMacros(source, input, defaultOutputSyntax, transformations: _*)
  }

  /**
   * XWiki XHTML rendering in a stream using default syntax.
   *
   * @param source Input stream
   * @return Result
   */
  def renderWithMacros(source :String, transformations: Transformation*): String = {
    renderWithMacros(source, defaultInputSyntax, defaultOutputSyntax, transformations: _*)
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

