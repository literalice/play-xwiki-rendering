package com.monochromeroad.play.xwiki.rendering

import org.xwiki.rendering.syntax.Syntax
import java.io.Reader
import org.xwiki.rendering.transformation.Transformation
import org.xwiki.rendering.block.XDOM
import org.xwiki.rendering.parser.StreamParser

/**
 * XWiki rendering system in a stream
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
    render[T](source, input, Syntax.XHTML_1_0, iv, proc)
  }

  /**
   * XWiki XHTML rendering in a stream using xwiki/2.1 syntax.
   *
   * @param source Input stream
   * @param iv Initial value on output function
   * @param proc A function processed on streaming
   * @tparam T Result type
   * @return Result
   */
  def render[T](source :Reader, iv: T, proc: (T, String) => T): T = {
    render[T](source, Syntax.XWIKI_2_1, Syntax.XHTML_1_0, iv, proc)
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
    transformWithMacro(componentManager, xdom, input, transformationsWithMacro)
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
    renderWithMacros[T](source, input, Syntax.XHTML_1_0, transformations, iv, proc)
  }

  /**
   * XWiki XHTML rendering in a stream with macros and some transformations using xwiki/2.1 syntax.
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
    renderWithMacros[T](source, Syntax.XWIKI_2_1, Syntax.XHTML_1_0, transformations, iv, proc)
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
    renderWithMacros[T](source, input, Syntax.XHTML_1_0, iv, proc)
  }

  /**
   * XWiki XHTML rendering in a stream with macros using xwiki/2.1 syntax.
   * It needs to create XDOM represented the document structure in memory.
   *
   * @param source Input stream
   * @param iv Initial value on output function
   * @param proc A function processed on streaming
   * @tparam T Result type
   * @return Result
   */
  def renderWithMacros[T](source :Reader, iv: T, proc: (T, String) => T): T = {
    renderWithMacros[T](source, Syntax.XWIKI_2_1, Syntax.XHTML_1_0, iv, proc)
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
    transformWithMacro(componentManager, xdom, input, transformations)
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
    renderWithTransformations[T](source, input, Syntax.XHTML_1_0, transformations, iv, proc)
  }

  /**
   * XWiki XHTML rendering in a stream with some transformations using xwiki/2.1 syntax.
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
    renderWithTransformations[T](source, Syntax.XWIKI_2_1, Syntax.XHTML_1_0, transformations, iv, proc)
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

