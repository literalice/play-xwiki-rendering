package com.monochromeroad.play.xwiki.rendering

import play.api._
import scala.Some

/**
 * Play Plugin to configure XWiki default renderers
 *
 * @author Masatoshi Hayashi
 */
class DefaultXWikiRenderingPlugin(app: Application) extends Plugin {

  private final val KEY_MACRO: String = "xwiki.rendering.default.macros"

  private final val KEY_MACRO_ENABLED: String = KEY_MACRO + ".enabled"

  val name = "XWiki Default Renderer"

  override def onStart() {
    Logger.info("Configuring XWiki default renderers ...")

    val macroManager = new XWikiMacroManager(DefaultXWikiComponentManager)

    val macroListKeys = app.configuration.keys filter(p => p.startsWith(KEY_MACRO) && !p.startsWith(KEY_MACRO_ENABLED))
    val macroList = macroListKeys.map(app.configuration.getString(_).get)

    macroList.map({macroName =>
      loadMacroClass(macroName).map(macroManager.registerMacro(_))
      Logger.info("Registered a macro: " + macroName)
    })

    val xwikiConfiguration = loadConfiguration(app.configuration)
    Seq(DefaultXWikiRenderer,
        DefaultXWikiStreamRenderer,
        DefaultXWikiStringStreamRenderer).map(_.configure(xwikiConfiguration))

    Logger.info("XWiki default renderers configuration done.\n" + xwikiConfiguration)
  }

  private def loadMacroClass(macroClassName: String): Option[Class[DefaultXWikiMacro[_]]] = {
    findMacroClass(macroClassName) match {
      case Some(mc) =>
        if (mc.isInstanceOf[Class[DefaultXWikiMacro[_]]]) {
          Some(mc.asInstanceOf[Class[DefaultXWikiMacro[_]]])
        } else {
          None
        }
      case None =>
        Logger.warn("The XWiki macro (" + macroClassName + ") not found.")
        None
    }
  }

  private def findMacroClass(macroClassName: String): Option[Class[_]] = {
    try {
      Some(Class.forName(macroClassName))
    } catch {
      case _: ClassNotFoundException =>
        Logger.warn("The XWiki macro (" + macroClassName + ") not found.")
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

