package com.monochromeroad.play.xwiki.rendering

import macros.RbMacro
import java.io.StringReader

/**
 * @author Masatoshi Hayashi
 */
class XWikiStreamRendererSpec extends XWikiSyntaxSpec {

  val componentManager = new XWikiComponentManager(getClass.getClassLoader)
  componentManager.registerMacro(classOf[RbMacro])

  val xwikiRenderer = new XWikiStreamRenderer(componentManager)

  val renderedHTML = xwikiRenderer.render[String](
    new StringReader(cheatSheet),
    "\n--------------- XWiki Cheat Sheet ---------------\n\n",
    { (acc: String, n: String) => acc + n }
  )

  System.out.println(renderedHTML)
  System.out.println("\n-------------------------------------------------\n")

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
}
