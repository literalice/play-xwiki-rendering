package com.monochromeroad.play.xwiki.rendering

import play.api._
import scala.Some

/**
 * @author Masatoshi Hayashi
 */
class DefaultXWikiRenderingPlugin(app: Application) extends Plugin {

  private final val KEY_MACRO: String = "xwiki.rendering.default.macros"

  private final val KEY_MACRO_ENABLED: String = KEY_MACRO + ".enabled"

  override def onStart() {
    val macroListKeys = app.configuration.keys filter(p => p.startsWith(KEY_MACRO) && !p.startsWith(KEY_MACRO_ENABLED))
    val macroList = macroListKeys.map(app.configuration.getString(_).get)
    macroList.map({macroName =>
      loadMacroClass(macroName).map(DefaultXWikiComponentManager.registerDefaultMacro)
    })

    val xwikiConfiguration = loadConfiguration(app.configuration)
    Seq(DefaultXWikiRenderer,
        DefaultXWikiStreamRenderer,
        DefaultXWikiStringStreamRenderer).map(_.configure(xwikiConfiguration))
  }

  private def loadMacroClass(macroClassName: String): Option[Class[DefaultXWikiMacro[_]]] = {
    val mayMacroClass = Class.forName(macroClassName)
    if (mayMacroClass.isInstanceOf[Class[DefaultXWikiMacro[_]]]) {
      Some(mayMacroClass.asInstanceOf[Class[DefaultXWikiMacro[_]]])
    } else {
      None
    }
  }

  private def loadConfiguration(configuration: Configuration): XWikiRendererConfiguration = {
    app.configuration.getBoolean(KEY_MACRO_ENABLED) match {
      case Some(macrosEnabled) => new XWikiRendererConfiguration(macrosEnabled = macrosEnabled)
      case _ => new XWikiRendererConfiguration()
    }
  }

}

