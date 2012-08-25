package com.monochromeroad.play.xwiki.rendering

import org.xwiki.rendering.syntax.Syntax
import java.io.Reader
import org.xwiki.rendering.transformation.Transformation

/**
 * XWiki Rendering System -- streaming based
 *
 * <br /><br />
 *
 * Default syntax: xwiki/2.1 output: xhtml/1.0
 *
 * @author Masatoshi Hayashi
 */
class XWikiStreamRenderer(componentManager: XWikiComponentManager,
                          configuration: XWikiRendererConfiguration = new XWikiRendererConfiguration())
  extends XWikiRenderingSystem(componentManager, configuration) {

  /**
   * XWiki rendering in a stream with some transformations.
   * It needs to create XDOM represented the document structure in memory.
   *
   * @param source Input stream
   * @param input Input syntax
   * @param output Output syntax
   * @param transformations Transformations on a XDOM
   * @param iv Initial value on output function
   * @param proc A function processed on streaming
   * @tparam T Result type
   * @return Result
   */
  def render[T](source :Reader, input: Syntax, output: Syntax, transformations: Seq[Transformation], iv: T, proc: (T, String) => T): T = {
    val printer = new CallbackWikiPrinter[T](iv, proc)
    render(source, input, output, transformations, printer)
    printer.get
  }

  /**
   * XWiki XHTML rendering in a stream with some transformations.
   * It needs to create XDOM represented the document structure in memory.
   *
   * @param source Input stream
   * @param input Input syntax
   * @param transformations Transformations on a XDOM
   * @param iv Initial value on output function
   * @param proc A function processed on streaming
   * @tparam T Result type
   * @return Result
   */
  def render[T](source :Reader, input: Syntax, transformations: Seq[Transformation], iv: T, proc: (T, String) => T): T = {
    render[T](source, input, configuration.defaultOutputSyntax, transformations, iv, proc)
  }

  /**
   * XWiki XHTML rendering in a stream with some transformations using default syntax.
   * It needs to create XDOM represented the document structure in memory.
   *
   * @param source Input stream
   * @param transformations Transformations on a XDOM
   * @param iv Initial value on output function
   * @param proc A function processed on streaming
   * @tparam T Result type
   * @return Result
   */
  def render[T](source :Reader, transformations: Seq[Transformation], iv: T, proc: (T, String) => T): T = {
    render[T](source, configuration.defaultInputSyntax, configuration.defaultOutputSyntax, transformations, iv, proc)
  }

  /**
   * XWiki rendering in a stream
   *
   * @param source Input stream
   * @param input Input syntax
   * @param output Output syntax
   * @param iv Initial value on output function
   * @param proc A function processed on streaming
   * @tparam T Result type
   * @return Result
   */
  def render[T](source :Reader, input: Syntax, output: Syntax, iv:T, proc: (T, String) => T): T = {
    render[T](source, input, output, List.empty[Transformation], iv, proc)
  }

  /**
   * XWiki XHTML rendering in a stream.
   *
   * @param source Input stream
   * @param input Input syntax
   * @param iv Initial value on output function
   * @param proc A function processed on streaming
   * @tparam T Result type
   * @return Result
   */
  def render[T](source :Reader, input: Syntax, iv: T, proc: (T, String) => T): T = {
    render[T](source, input, configuration.defaultOutputSyntax, iv, proc)
  }

  /**
   * XWiki XHTML rendering in a stream using default syntax.
   *
   * @param source Input stream
   * @param iv Initial value on output function
   * @param proc A function processed on streaming
   * @tparam T Result type
   * @return Result
   */
  def render[T](source :Reader, iv: T, proc: (T, String) => T): T = {
    render[T](source, configuration.defaultInputSyntax, configuration.defaultOutputSyntax, iv, proc)
  }

}

/**
 * Default XWiki streaming rendering system in the Play XWiki Plugin.
 *
 * @author Masatoshi Hayashi
 */
object DefaultXWikiStreamRenderer extends XWikiStreamRenderer(DefaultXWikiComponentManager)

