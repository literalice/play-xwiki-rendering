package com.monochromeroad.play.xwiki.rendering

import macros.RbMacro

/**
 * @author Masatoshi Hayashi
 */
class XWikiRendererSpec extends XWikiSyntaxSpec {

  val componentManager = new XWikiComponentManager(getClass.getClassLoader)
  componentManager.registerMacro(classOf[RbMacro])

  val xwikiRenderer = new XWikiRenderer(componentManager)

  val renderedCheatSheet = xwikiRenderer.render(cheatSheet)
  System.out.println("--------------- XWiki Cheat Sheet ---------------\n")
  System.out.println(renderedCheatSheet)
  System.out.println("\n-------------------------------------------------\n")


  "The cheat cheet rendered as html" should {
    containXhtmlHeading(renderedCheatSheet)
    containXhtmlFormats(renderedCheatSheet)
    containXhtmlHorizontalLines(renderedCheatSheet)
    containXhtmlLists(renderedCheatSheet)
    containXhtmlDefLists(renderedCheatSheet)
    containXhtmlLineBreaks(renderedCheatSheet)
    containXhtmlLinks(renderedCheatSheet)
    containXhtmlTables(renderedCheatSheet)
  }
}
