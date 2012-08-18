package com.monochromeroad.play.xwiki.rendering

import play.api._

/**
 * @author Masatoshi Hayashi
 */

class DefaultXWikiRenderingPlugin(app: Application) extends Plugin {

  override def onStart() {
    app.configuration.getString("xwiki.rendering.default.macros") match {
      case Some(macroConfig) =>
        val macros = macroConfig.split(",")
        val macroClasses = macros.map(loadMacroClass).collect { case Some(mc) => mc }
        macroClasses.map(DefaultXWikiComponentManager.registerDefaultMacro)
      case None => // does nothing
    }
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
