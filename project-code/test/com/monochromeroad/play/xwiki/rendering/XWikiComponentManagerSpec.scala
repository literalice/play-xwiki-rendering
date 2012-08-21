package com.monochromeroad.play.xwiki.rendering

import macros.RbMacro
import org.specs2.mutable._

import org.xwiki.rendering.syntax.Syntax

/**
 * @author Masatoshi Hayashi
 */

class XWikiComponentManagerSpec extends Specification {

  "Ruby tag" should {
    "contains ruby tag" in {
      convert("**TEST** {{rb read='read'}}Unreadable word{{/rb}}") must contain("<ruby>")
    }
  }

  private def convert(src: String): String = {
    val componentManager = new XWikiComponentManager(getClass.getClassLoader)
    componentManager.registerMacro(classOf[RbMacro])

    val xwikiRenderer = new XWikiRenderer(componentManager)
    xwikiRenderer.render(src, Syntax.XWIKI_2_1, Syntax.XHTML_1_0)
  }
}
