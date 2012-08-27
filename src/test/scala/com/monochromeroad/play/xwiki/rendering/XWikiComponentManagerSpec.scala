package com.monochromeroad.play.xwiki.rendering

import macros.{XWikiMacroManager, RbMacro}
import org.specs2.mutable._

import org.xwiki.rendering.syntax.Syntax

/**
 * @author Masatoshi Hayashi
 */

class XWikiComponentManagerSpec extends Specification {

  val componentManager = new XWikiComponentManager(getClass.getClassLoader)

  "A component required to be registerd" should {
    new XWikiMacroManager(componentManager).registerMacro(classOf[RbMacro])
    "be activated" in {
      convert("**TEST** {{rb read='read'}}Unreadable word{{/rb}}") must contain("<ruby>")
    }
  }

  "A component required to be unregisterd" should {
    "not be activated" in {
      new XWikiMacroManager(componentManager).registerMacro(classOf[RbMacro])
      convert("**TEST** {{rb read='read'}}Unreadable word{{/rb}}") must contain("<ruby>")
      new XWikiMacroManager(componentManager).unregisterMacro(classOf[RbMacro])
      convert("**TEST** {{rb read='read'}}Unreadable word{{/rb}}") must not contain("<ruby>")
      new XWikiMacroManager(componentManager).reloadMacro(classOf[RbMacro])
      convert("**TEST** {{rb read='read'}}Unreadable word{{/rb}}") must contain("<ruby>")
    }
  }

  private def convert(src: String): String = {
    val xwikiRenderer = new XWikiRenderer(componentManager, new XWikiRendererConfiguration())
    xwikiRenderer.render(src, Syntax.XWIKI_2_1, Syntax.XHTML_1_0)
  }
}
