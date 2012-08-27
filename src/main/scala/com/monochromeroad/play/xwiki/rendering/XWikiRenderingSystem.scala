package com.monochromeroad.play.xwiki.rendering

import org.xwiki.rendering.renderer.{Renderer, PrintRendererFactory}
import org.xwiki.rendering.renderer.printer.WikiPrinter
import org.xwiki.rendering.syntax.Syntax
import org.xwiki.rendering.block.XDOM
import java.io.Reader
import org.xwiki.rendering.transformation.{TransformationContext, Transformation}
import org.xwiki.rendering.parser.StreamParser

/**
 * XWiki Rendering System
 *
 * It needs to create a XDOM that represents the whole document structure in a memory.
 *
 *   - '''Default Syntax''' xwiki/2.1
 *   - '''Output''' xhtml/1.0
 *
 * @param componentManager component manager used by the renderer
 * @author Masatoshi Hayashi
 */
abstract class XWikiRenderingSystem(val componentManager: XWikiComponentManager, private var conf: XWikiRendererConfiguration) {

  protected final val transformationForMacro: Transformation =  componentManager.getInstance[Transformation]("macro")

  protected final def configuration: XWikiRendererConfiguration = {
    this.conf
  }

  protected def render(source :Reader, input: Syntax, output: Syntax, transformations: Seq[Transformation], printer: WikiPrinter) {
    if (transformations.isEmpty && !configuration.macrosEnabled) {
      renderOnStream(source, input, output, printer)
    } else {
      renderOnXDOM(source, input, output, transformations, printer)
    }
  }

  protected def render(source :Reader, input: Syntax, output: Syntax, printer: WikiPrinter) {
    render(source, input, output, List.empty[Transformation], printer)
  }

  protected def renderOnXDOM(source :Reader, input: Syntax, output: Syntax, transformations: Seq[Transformation], printer: WikiPrinter) {
    val xdom = buildXDOM(source, input)
    transform(xdom, input, transformations)
    applyRenderer(xdom, output, printer)
  }

  protected def renderOnStream(source :Reader, input: Syntax, output: Syntax, printer: WikiPrinter) {
    val streamParser = componentManager.getInstance[StreamParser](input.toIdString)
    val renderer = createRenderer(output, printer)
    streamParser.parse(source, renderer)
  }

  protected def applyRenderer(xdom: XDOM, syntax: Syntax, printer: WikiPrinter) {
    val renderer = createRenderer(syntax, printer)
    xdom.traverse(renderer)
  }

  protected final def createRenderer(output: Syntax, printer: WikiPrinter): Renderer = {
    val rendererFactory = componentManager.getInstance[PrintRendererFactory](output.toIdString)
    rendererFactory.createRenderer(printer)
  }

  protected final def buildXDOM(source :Reader, input: Syntax): XDOM = {
    val parser = componentManager.getParser(input.toIdString)
    parser.parse(source)
  }

  protected final def transform(xdom: XDOM, syntax: Syntax, transformations: Seq[Transformation]) {
    val txContext = new TransformationContext(xdom, syntax)
    if (configuration.macrosEnabled) {
      transformationForMacro.transform(xdom, txContext)
    }
    transformations.map({ tx => tx.transform(xdom, txContext)})
  }

  def configure(conf: XWikiRendererConfiguration) {
    this.conf = conf
  }

}

