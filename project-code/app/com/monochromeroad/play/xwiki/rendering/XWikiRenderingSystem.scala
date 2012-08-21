package com.monochromeroad.play.xwiki.rendering

import org.xwiki.rendering.renderer.{Renderer, PrintRendererFactory, BlockRenderer}
import org.xwiki.rendering.renderer.printer.WikiPrinter
import org.xwiki.rendering.syntax.Syntax
import org.xwiki.rendering.block.XDOM
import java.io.Reader
import org.xwiki.rendering.transformation.{TransformationContext, Transformation}

/**
 * @author Masatoshi Hayashi
 */

trait XWikiRenderingSystem {

  protected def applyRenderer(componentManager: XWikiComponentManager, xdom: XDOM, syntax: Syntax, printer: WikiPrinter) {
    val renderer = createRenderer(componentManager, syntax, printer)
    xdom.traverse(renderer)
  }

  protected final def createRenderer(componentManager: XWikiComponentManager, output: Syntax, printer: WikiPrinter): Renderer = {
    val rendererFactory = componentManager.getInstance[PrintRendererFactory](output.toIdString)
    rendererFactory.createRenderer(printer)
  }

  protected final def buildXDOM(componentManager: XWikiComponentManager, source :Reader, input: Syntax): XDOM = {
    val parser = componentManager.getParser(input.toIdString)
    parser.parse(source)
  }

  protected final def transformWithMacro(componentManager: XWikiComponentManager, xdom: XDOM, syntax: Syntax, transformations: Seq[Transformation]) {
    val txContext = new TransformationContext(xdom, syntax)
    transformations.map({ tx => tx.transform(xdom, txContext)})
  }

  protected final def getTransformationForMacro(componentManager: XWikiComponentManager): Transformation = {
    componentManager.getInstance[Transformation]("macro")
  }
}

