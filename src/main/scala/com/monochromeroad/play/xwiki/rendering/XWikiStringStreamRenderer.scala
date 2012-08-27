package com.monochromeroad.play.xwiki.rendering

import java.io.Reader
import org.xwiki.rendering.syntax.Syntax
import org.xwiki.rendering.transformation.Transformation

/**
 * XWiki Rendering System -- streaming based, simplified
 *
 * If you use some macros or transformations, it needs to create a XDOM that represents the whole document structure in a memory.
 *
 *   - '''Default Syntax''' xwiki/2.1
 *   - '''Output''' xhtml/1.0
 *
 * @param componentManager component manager used by the renderer
 * @param configuration renderer configuration
 * @author Masatoshi Hayashi
 */
class XWikiStringStreamRenderer (componentManager: XWikiComponentManager,
                                 configuration: XWikiRendererConfiguration = new XWikiRendererConfiguration())
  extends XWikiRenderingSystem(componentManager, configuration) {

  /**
   * XWiki rendering in a stream
   *
   * @param source input reader
   * @param input input syntax
   * @param output output syntax
   * @param transformations transformations on a XDOM
   * @param proc function processed on streaming
   */
  def render(source :Reader, input: Syntax, output: Syntax, transformations: Seq[Transformation], proc: String => Unit) {
    val listener = (_: Unit, n: String) => { proc(n) }
    val printer = new CallbackWikiPrinter[Unit](Unit, listener)
    render(source, input, output, transformations, printer)
  }

  /**
   * XWiki XHTML rendering in a stream.
   *
   * @param source input stream
   * @param input input syntax
   * @param proc function processed on streaming
   */
  def render(source :Reader, input: Syntax, transformations: Seq[Transformation], proc: String => Unit) {
    render(source, input, configuration.defaultOutputSyntax, transformations, proc)
  }

  /**
   * XWiki XHTML rendering in a stream using default syntax.
   *
   * @param source input stream
   * @param proc function processed on streaming
   */
  def render(source :Reader, transformations: Seq[Transformation], proc: String => Unit) {
    render(source, configuration.defaultInputSyntax, configuration.defaultOutputSyntax, transformations, proc)
  }

  /**
   * XWiki rendering in a stream
   *
   * @param source input reader
   * @param input input syntax
   * @param output output syntax
   * @param proc function processed on streaming
   */
  def render(source :Reader, input: Syntax, output: Syntax, proc: String => Unit) {
    render(source, input, output, List.empty[Transformation], proc)
  }

  /**
   * XWiki XHTML rendering in a stream.
   *
   * @param source input stream
   * @param input input syntax
   * @param proc function processed on streaming
   */
  def render(source :Reader, input: Syntax, proc: String => Unit) {
    render(source, input, configuration.defaultOutputSyntax, proc)
  }

  /**
   * XWiki XHTML rendering in a stream using default syntax.
   *
   * @param source input stream
   * @param proc function processed on streaming
   */
  def render(source :Reader, proc: String => Unit) {
    render(source, configuration.defaultInputSyntax, configuration.defaultOutputSyntax, proc)
  }
}

