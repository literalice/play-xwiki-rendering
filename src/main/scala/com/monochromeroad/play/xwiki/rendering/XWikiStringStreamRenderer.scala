package com.monochromeroad.play.xwiki.rendering

import java.io.Reader
import org.xwiki.rendering.syntax.Syntax
import org.xwiki.rendering.transformation.Transformation

/**
 * XWiki Rendering System -- streaming based, simple
 *
 * <br /><br />
 *
 * Default syntax: xwiki/2.1 output: xhtml/1.0
 *
 * @author Masatoshi Hayashi
 */
class XWikiStringStreamRenderer (componentManager: XWikiComponentManager,
                                 configuration: XWikiRendererConfiguration = new XWikiRendererConfiguration())
  extends XWikiRenderingSystem(componentManager, configuration) {

  /**
   * XWiki rendering in a stream
   *
   * @param source Input reader
   * @param input Input syntax
   * @param output Output syntax
   * @param transformations Transformations on a XDOM
   * @param proc A function processed on streaming
   */
  def render(source :Reader, input: Syntax, output: Syntax, transformations: Seq[Transformation], proc: String => Unit) {
    val listener = (_: Unit, n: String) => { proc(n) }
    val printer = new CallbackWikiPrinter[Unit](Unit, listener)
    render(source, input, output, transformations, printer)
  }

  /**
   * XWiki XHTML rendering in a stream.
   *
   * @param source Input stream
   * @param input Input syntax
   * @param proc A function processed on streaming
   */
  def render(source :Reader, input: Syntax, transformations: Seq[Transformation], proc: String => Unit) {
    render(source, input, configuration.defaultOutputSyntax, transformations, proc)
  }

  /**
   * XWiki XHTML rendering in a stream using default syntax.
   *
   * @param source Input stream
   * @param proc A function processed on streaming
   */
  def render(source :Reader, transformations: Seq[Transformation], proc: String => Unit) {
    render(source, configuration.defaultInputSyntax, configuration.defaultOutputSyntax, transformations, proc)
  }

  /**
   * XWiki rendering in a stream
   *
   * @param source Input reader
   * @param input Input syntax
   * @param output Output syntax
   * @param proc A function processed on streaming
   */
  def render(source :Reader, input: Syntax, output: Syntax, proc: String => Unit) {
    render(source, input, output, List.empty[Transformation], proc)
  }

  /**
   * XWiki XHTML rendering in a stream.
   *
   * @param source Input stream
   * @param input Input syntax
   * @param proc A function processed on streaming
   */
  def render(source :Reader, input: Syntax, proc: String => Unit) {
    render(source, input, configuration.defaultOutputSyntax, proc)
  }

  /**
   * XWiki XHTML rendering in a stream using default syntax.
   *
   * @param source Input stream
   * @param proc A function processed on streaming
   */
  def render(source :Reader, proc: String => Unit) {
    render(source, configuration.defaultInputSyntax, configuration.defaultOutputSyntax, proc)
  }
}
/**
 * Default XWiki streaming rendering system in the Play XWiki Plugin.
 *
 * @author Masatoshi Hayashi
 */
object DefaultXWikiStringStreamRenderer extends XWikiStringStreamRenderer(DefaultXWikiComponentManager)

