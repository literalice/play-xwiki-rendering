package com.monochromeroad.play.xwiki.rendering

import macros.RbMacro

/**
 * @author Masatoshi Hayashi
 */
class XWikiRendererSpec extends XWikiSyntaxSpec {

  val componentManager = new XWikiComponentManager(getClass.getClassLoader)
  componentManager.registerMacro(classOf[RbMacro])

  val xwikiRenderer = new XWikiRenderer(componentManager)

  val renderedCheatSheet = xwikiRenderer.renderWithMacros(cheatSheet)
  System.out.println("--------------- XWiki Cheat Sheet ---------------\n")
  System.out.println(renderedCheatSheet)
  System.out.println("\n-------------------------------------------------\n")


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
  }

  val renderedWithoutMacros = xwikiRenderer.render(cheatSheet)
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
