package com.monochromeroad.play.xwiki.rendering

import org.xwiki.rendering.syntax.Syntax
import java.io.Reader
import org.xwiki.rendering.transformation.Transformation
import org.xwiki.rendering.block.XDOM
import org.xwiki.rendering.parser.StreamParser

/**
 * XWiki Rendering System -- streaming based
 *
 * <br /><br />
 *
 * Default syntax: xwiki/2.1 output: xhtml/1.0
 *
 * @author Masatoshi Hayashi
 */
class XWikiStreamRenderer(val componentManager: XWikiComponentManager) extends XWikiRenderingSystem {

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
    val streamParser = componentManager.getInstance[StreamParser](input.toIdString)
    val printer = new CallbackWikiPrinter[T](iv, proc)
    val renderer = createRenderer(componentManager, output, printer)
    streamParser.parse(source, renderer)
    printer.get
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
    render[T](source, input, defaultOutputSyntax, iv, proc)
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
    render[T](source, defaultInputSyntax, defaultOutputSyntax, iv, proc)
  }

  /**
   * XWiki rendering in a stream with macros and some transformations.
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
  def renderWithMacros[T](source :Reader, input: Syntax, output: Syntax, transformations: Seq[Transformation], iv: T, proc: (T, String) => T): T = {
    val xdom = buildXDOM(componentManager, source, input)
    val transformationsWithMacro = List.concat(
      transformations , List(getTransformationForMacro(componentManager)))
    transform(componentManager, xdom, input, transformationsWithMacro)
    processStream(xdom, output, iv, proc)
  }

  /**
   * XWiki XHTML rendering in a stream with macros and some transformations.
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
  def renderWithMacros[T](source :Reader, input: Syntax, transformations: Seq[Transformation], iv: T, proc: (T, String) => T): T = {
    renderWithMacros[T](source, input, defaultOutputSyntax, transformations, iv, proc)
  }

  /**
   * XWiki XHTML rendering in a stream with macros and some transformations using default syntax.
   * It needs to create XDOM represented the document structure in memory.
   *
   * @param source Input stream
   * @param transformations Transformations on a XDOM
   * @param iv Initial value on output function
   * @param proc A function processed on streaming
   * @tparam T Result type
   * @return Result
   */
  def renderWithMacros[T](source :Reader, transformations: Seq[Transformation], iv: T, proc: (T, String) => T): T = {
    renderWithMacros[T](source, defaultInputSyntax, defaultOutputSyntax, transformations, iv, proc)
  }

  /**
   * XWiki rendering in a stream with macros.
   * It needs to create XDOM represented the document structure in memory.
   *
   * @param source Input stream
   * @param input Input syntax
   * @param output Output syntax
   * @param iv Initial value on output function
   * @param proc A function processed on streaming
   * @tparam T Result type
   * @return Result
   */
  def renderWithMacros[T](source :Reader, input: Syntax, output: Syntax, iv: T, proc: (T, String) => T): T = {
    renderWithMacros[T](source, input, output, List.empty[Transformation], iv, proc)
  }

  /**
   * XWiki XHTML rendering in a stream with macros and some transformations.
   * It needs to create XDOM represented the document structure in memory.
   *
   * @param source Input stream
   * @param input Input syntax
   * @param iv Initial value on output function
   * @param proc A function processed on streaming
   * @tparam T Result type
   * @return Result
   */
  def renderWithMacros[T](source :Reader, input: Syntax, iv: T, proc: (T, String) => T): T = {
    renderWithMacros[T](source, input, defaultOutputSyntax, iv, proc)
  }

  /**
   * XWiki XHTML rendering in a stream with macros using default syntax.
   * It needs to create XDOM represented the document structure in memory.
   *
   * @param source Input stream
   * @param iv Initial value on output function
   * @param proc A function processed on streaming
   * @tparam T Result type
   * @return Result
   */
  def renderWithMacros[T](source :Reader, iv: T, proc: (T, String) => T): T = {
    renderWithMacros[T](source, defaultInputSyntax, defaultOutputSyntax, iv, proc)
  }

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
  def renderWithTransformations[T](source :Reader, input: Syntax, output: Syntax, transformations: Seq[Transformation], iv: T, proc: (T, String) => T): T = {
    val xdom = buildXDOM(componentManager, source, input)
    transform(componentManager, xdom, input, transformations)
    processStream(xdom, output, iv, proc)
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
  def renderWithTransformations[T](source :Reader, input: Syntax, transformations: Seq[Transformation], iv: T, proc: (T, String) => T): T = {
    renderWithTransformations[T](source, input, defaultOutputSyntax, transformations, iv, proc)
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
  def renderWithTransformations[T](source :Reader, transformations: Seq[Transformation], iv: T, proc: (T, String) => T): T = {
    renderWithTransformations[T](source, defaultInputSyntax, defaultOutputSyntax, transformations, iv, proc)
  }

  private def processStream[T](xdom: XDOM, syntax: Syntax, init: T, callback: (T, String) => T): T = {
    val printer = new CallbackWikiPrinter[T](init, callback)
    applyRenderer(componentManager, xdom, syntax, printer)
    printer.get
  }
}

/**
 * Default XWiki streaming rendering system in the Play XWiki Plugin.
 *
 * @author Masatoshi Hayashi
 */
object DefaultXWikiStreamRenderer extends XWikiStreamRenderer(DefaultXWikiComponentManager)

