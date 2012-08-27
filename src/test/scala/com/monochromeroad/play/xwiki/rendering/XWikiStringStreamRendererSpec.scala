package com.monochromeroad.play.xwiki.rendering

import macros._
import java.io.StringReader

/**
 * @author Masatoshi Hayashi
 */
class XWikiStringStreamRendererSpec extends XWikiSyntaxSpec {

  val componentManager = new XWikiComponentManager(getClass.getClassLoader)
  val macroManager = new XWikiMacroManager(componentManager)
  macroManager.registerMacro(classOf[RbMacro])
  macroManager.registerMacro(classOf[DateMacro])

  val xwikiRendererWithoutMacros =
    new XWikiStringStreamRenderer(componentManager, new XWikiRendererConfiguration(macrosEnabled = false))

  val renderedHTMLBuilder = new StringBuilder()
  xwikiRendererWithoutMacros.render(new StringReader(cheatSheet), renderedHTMLBuilder.append(_))
  val renderedHTML = renderedHTMLBuilder.toString()

  "A html rendered without macros" should {
    containXhtmlHeading(renderedHTML)
    containXhtmlFormats(renderedHTML)
    containXhtmlHorizontalLines(renderedHTML)
    containXhtmlLists(renderedHTML)
    containXhtmlDefLists(renderedHTML)
    containXhtmlLineBreaks(renderedHTML)
    containXhtmlLinks(renderedHTML)
    containXhtmlTables(renderedHTML)
    notContainMacroXhtmls(renderedHTML)
  }

  val xwikiRenderer = new XWikiStringStreamRenderer(componentManager)

  val renderedMacroBuilder = new StringBuilder()
  xwikiRenderer.render(
    new StringReader(cheatSheet), renderedMacroBuilder.append(_))

  val renderedMacro = renderedMacroBuilder.toString()

  "A html rendered without macros" should {
    containXhtmlHeading(renderedMacro)
    containXhtmlFormats(renderedMacro)
    containXhtmlHorizontalLines(renderedMacro)
    containXhtmlLists(renderedMacro)
    containXhtmlDefLists(renderedMacro)
    containXhtmlLineBreaks(renderedMacro)
    containXhtmlLinks(renderedMacro)
    containXhtmlTables(renderedMacro)
    containMacroXhtmls(renderedMacro)
    containNoParameterMacroXhtmls(renderedMacro)
  }
}

