package com.monochromeroad.play.xwiki.rendering

import macros.RbMacro
import java.io.StringReader

/**
 * @author Masatoshi Hayashi
 */
class XWikiStringStreamRendererSpec extends XWikiSyntaxSpec {

  val componentManager = new XWikiComponentManager(getClass.getClassLoader)
  componentManager.registerMacro(classOf[RbMacro])

  val xwikiRenderer = new XWikiStringStreamRenderer(componentManager)

  val renderedHTMLBuilder = new StringBuilder()
  xwikiRenderer.render(new StringReader(cheatSheet),
    { (n: String) => renderedHTMLBuilder.append(n) }
  )
  val renderedHTML = renderedHTMLBuilder.toString()

  println("\n--------------- XWiki Cheat Sheet ---------------\n")
  println(renderedHTML)
  println("\n-------------------------------------------------\n")

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

  val renderedMacroBuilder = new StringBuilder()
  xwikiRenderer.renderWithMacros(
    new StringReader(cheatSheet),
    { (n: String) => renderedMacroBuilder.append(n) }
  )

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
  }
}

