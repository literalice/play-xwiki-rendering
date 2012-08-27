package com.monochromeroad.play.xwiki.rendering

import macros._
import java.io.StringReader

/**
 * @author Masatoshi Hayashi
 */
class XWikiStreamRendererSpec extends XWikiSyntaxSpec {

  val componentManager = new XWikiComponentManager(getClass.getClassLoader)
  val macroManager = new XWikiMacroManager(componentManager)
  macroManager.registerMacro(classOf[RbMacro])
  macroManager.registerMacro(classOf[DateMacro])

  val xwikiRendererWithoutMacros =
    new XWikiStreamRenderer(componentManager, new XWikiRendererConfiguration(macrosEnabled = false))

  val renderedHTML = xwikiRendererWithoutMacros.render[String](
    new StringReader(cheatSheet),
    "\n--------------- XWiki Cheat Sheet ---------------\n\n", (acc: String, n: String) => acc + n
  )

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

  val xwikiRenderer = new XWikiStreamRenderer(componentManager)

  val renderedMacros = xwikiRenderer.render[StringBuilder](
    new StringReader(cheatSheet),
    new StringBuilder(),
    { (acc: StringBuilder, n: String) => acc.append(n) }
  ).toString()

  "A html rendered with macros" should {
    containXhtmlHeading(renderedMacros)
    containXhtmlFormats(renderedMacros)
    containXhtmlHorizontalLines(renderedMacros)
    containXhtmlLists(renderedMacros)
    containXhtmlDefLists(renderedMacros)
    containXhtmlLineBreaks(renderedMacros)
    containXhtmlLinks(renderedMacros)
    containXhtmlTables(renderedMacros)
    containMacroXhtmls(renderedMacros)
    containNoParameterMacroXhtmls(renderedMacros)
  }
}

