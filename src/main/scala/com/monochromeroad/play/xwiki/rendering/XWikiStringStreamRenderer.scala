package com.monochromeroad.play.xwiki.rendering

import java.io.Reader
import org.xwiki.rendering.syntax.Syntax
import org.xwiki.rendering.transformation.Transformation
import org.xwiki.rendering.block.XDOM
import org.xwiki.rendering.parser.StreamParser

/**
 * XWiki Rendering System -- streaming based, simple
 *
 * <br /><br />
 *
 * Default syntax: xwiki/2.1 output: xhtml/1.0
 *
 * @author Masatoshi Hayashi
 */
class XWikiStringStreamRenderer (val componentManager: XWikiComponentManager) extends XWikiRenderingSystem {

  /**
   * XWiki rendering in a stream
   *
   * @param source Input reader
   * @param input Input syntax
   * @param output Output syntax
   * @param proc A function processed on streaming
   */
  def render(source :Reader, input: Syntax, output: Syntax, proc: String => Unit) {
    val streamParser = componentManager.getInstance[StreamParser](input.toIdString)
    val listener = (_: Unit, n: String) => { proc(n) }
    val printer = new CallbackWikiPrinter[Unit](Unit, listener)
    val renderer = createRenderer(componentManager, output, printer)
    streamParser.parse(source, renderer)
  }

  /**
   * XWiki XHTML rendering in a stream.
   *
   * @param source Input stream
   * @param input Input syntax
   * @param proc A function processed on streaming
   */
  def render(source :Reader, input: Syntax, proc: String => Unit) {
    render(source, input, defaultOutputSyntax, proc)
  }

  /**
   * XWiki XHTML rendering in a stream using default syntax.
   *
   * @param source Input stream
   * @param proc A function processed on streaming
   */
  def render(source :Reader, proc: String => Unit) {
    render(source, defaultInputSyntax, defaultOutputSyntax, proc)
  }

  /**
   * XWiki rendering in a stream with macros and some transformations.
   * It needs to create XDOM represented the document structure in memory.
   *
   * @param source Input stream
   * @param input Input syntax
   * @param output Output syntax
   * @param transformations Transformations on a XDOM
   * @param proc A function processed on streaming
   */
  def renderWithMacros(source :Reader, input: Syntax, output: Syntax, transformations: Seq[Transformation], proc: String => Unit) {
    val transformationsWithMacro = List.concat(
      transformations , List(getTransformationForMacro(componentManager)))
    renderWithTransformations(source, input, output, transformationsWithMacro, proc)
  }

  /**
   * XWiki XHTML rendering in a stream with macros and some transformations.
   * It needs to create XDOM represented the document structure in memory.
   *
   * @param source Input stream
   * @param input Input syntax
   * @param transformations Transformations on a XDOM
   * @param proc A function processed on streaming
   */
  def renderWithMacros(source :Reader, input: Syntax, transformations: Seq[Transformation], proc: String => Unit) {
    renderWithMacros(source, input, defaultOutputSyntax, transformations, proc)
  }

  /**
   * XWiki XHTML rendering in a stream with macros and some transformations using default syntax.
   * It needs to create XDOM represented the document structure in memory.
   *
   * @param source Input stream
   * @param transformations Transformations on a XDOM
   * @param proc A function processed on streaming
   */
  def renderWithMacros(source :Reader, transformations: Seq[Transformation], proc: String => Unit) {
    renderWithMacros(source, defaultInputSyntax, defaultOutputSyntax, transformations, proc)
  }

  /**
   * XWiki rendering in a stream with macros.
   * It needs to create XDOM represented the document structure in memory.
   *
   * @param source Input stream
   * @param input Input syntax
   * @param output Output syntax
   * @param proc A function processed on streaming
   */
  def renderWithMacros(source :Reader, input: Syntax, output: Syntax, proc: String => Unit) {
    renderWithMacros(source, input, output, List.empty[Transformation], proc)
  }

  /**
   * XWiki XHTML rendering in a stream with macros and some transformations.
   * It needs to create XDOM represented the document structure in memory.
   *
   * @param source Input stream
   * @param input Input syntax
   * @param proc A function processed on streaming
   */
  def renderWithMacros(source :Reader, input: Syntax, proc: String => Unit) {
    renderWithMacros(source, input, defaultOutputSyntax, proc)
  }

  /**
   * XWiki XHTML rendering in a stream with macros using default syntax.
   * It needs to create XDOM represented the document structure in memory.
   *
   * @param source Input stream
   * @param proc A function processed on streaming
   */
  def renderWithMacros(source :Reader, proc: String => Unit) {
    renderWithMacros(source, defaultInputSyntax, defaultOutputSyntax, proc)
  }

  /**
   * XWiki rendering in a stream with some transformations.
   * It needs to create XDOM represented the document structure in memory.
   *
   * @param source Input stream
   * @param input Input syntax
   * @param output Output syntax
   * @param transformations Transformations on a XDOM
   * @param proc A function processed on streaming
   */
  def renderWithTransformations(source :Reader, input: Syntax, output: Syntax, transformations: Seq[Transformation], proc: String => Unit) {
    val xdom = buildXDOM(componentManager, source, input)
    transform(componentManager, xdom, input, transformations)
    processStream(xdom, output, proc)
  }

  /**
   * XWiki XHTML rendering in a stream with some transformations.
   * It needs to create XDOM represented the document structure in memory.
   *
   * @param source Input stream
   * @param input Input syntax
   * @param transformations Transformations on a XDOM
   * @param proc A function processed on streaming
   */
  def renderWithTransformations(source :Reader, input: Syntax, transformations: Seq[Transformation], proc: String => Unit) {
    renderWithTransformations(source, input, defaultOutputSyntax, transformations, proc)
  }

  /**
   * XWiki XHTML rendering in a stream with some transformations using default syntax.
   * It needs to create XDOM represented the document structure in memory.
   *
   * @param source Input stream
   * @param transformations Transformations on a XDOM
   * @param proc A function processed on streaming
   */
  def renderWithTransformations(source :Reader, transformations: Seq[Transformation], proc: String => Unit) {
    renderWithTransformations(source, defaultInputSyntax, defaultOutputSyntax, transformations, proc)
  }

  private def processStream(xdom: XDOM, syntax: Syntax, proc: String => Unit) {
    val listener = (_: Unit, n: String) => { proc(n) }
    val printer = new CallbackWikiPrinter[Unit](Unit, listener)
    applyRenderer(componentManager, xdom, syntax, printer)
  }
}
/**
 * Default XWiki streaming rendering system in the Play XWiki Plugin.
 *
 * @author Masatoshi Hayashi
 */
object DefaultXWikiStringStreamRenderer extends XWikiStringStreamRenderer(DefaultXWikiComponentManager)

