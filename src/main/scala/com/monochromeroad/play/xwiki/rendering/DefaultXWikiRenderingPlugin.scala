package com.monochromeroad.play.xwiki.rendering

import play.api._

/**
 * @author Masatoshi Hayashi
 */

class DefaultXWikiRenderingPlugin(app: Application) extends Plugin {

  override def onStart() {
    val macroListKeys = app.configuration.keys.filter((p) => p.startsWith("xwiki.rendering.default.macros"))
    val macroList = macroListKeys.map { n => app.configuration.getString(n).get }
    macroList.map({macroName =>
      loadMacroClass(macroName).map(DefaultXWikiComponentManager.registerDefaultMacro)
    })
  }

  private def loadMacroClass(macroClassName: String): Option[Class[DefaultXWikiMacro[_]]] = {
    val mayMacroClass = Class.forName(macroClassName)
    if (mayMacroClass.isInstanceOf[Class[DefaultXWikiMacro[_]]]) {
      Some(mayMacroClass.asInstanceOf[Class[DefaultXWikiMacro[_]]])
    } else {
      None
    }
  }

}
