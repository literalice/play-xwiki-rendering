package com.monochromeroad.play.xwiki.rendering

import macros._

/**
 * @author Masatoshi Hayashi
 */
class XWikiRendererSpec extends XWikiSyntaxSpec {

  val componentManager = new XWikiComponentManager(getClass.getClassLoader)
  val macroManager = new XWikiMacroManager(componentManager)
  macroManager.registerMacro(classOf[RbMacro])
  macroManager.registerMacro(classOf[DateMacro])

  val xwikiRenderer = new XWikiRenderer(componentManager)

  val renderedCheatSheet = xwikiRenderer.render(cheatSheet)

  "A html rendered with macros" should {
    containXhtmlHeading(renderedCheatSheet)
    containXhtmlFormats(renderedCheatSheet)
    containXhtmlHorizontalLines(renderedCheatSheet)
    containXhtmlLists(renderedCheatSheet)
    containXhtmlDefLists(renderedCheatSheet)
    containXhtmlLineBreaks(renderedCheatSheet)
    containXhtmlLinks(renderedCheatSheet)
    containXhtmlTables(renderedCheatSheet)
    containMacroXhtmls(renderedCheatSheet)
    containNoParameterMacroXhtmls(renderedCheatSheet)
  }

  val xwikiRendererWithoutMacros =
    new XWikiRenderer(componentManager, new XWikiRendererConfiguration(macrosEnabled = false))
  val renderedWithoutMacros = xwikiRendererWithoutMacros.render(cheatSheet)

  "A html rendered without macros" should {
    containXhtmlHeading(renderedWithoutMacros)
    containXhtmlFormats(renderedWithoutMacros)
    containXhtmlHorizontalLines(renderedWithoutMacros)
    containXhtmlLists(renderedWithoutMacros)
    containXhtmlDefLists(renderedWithoutMacros)
    containXhtmlLineBreaks(renderedWithoutMacros)
    containXhtmlLinks(renderedWithoutMacros)
    containXhtmlTables(renderedWithoutMacros)
    notContainMacroXhtmls(renderedWithoutMacros)
  }

}
