package com.monochromeroad.play.xwiki.rendering.plugin

import play.api._
import com.monochromeroad.play.xwiki.rendering._
import macros.XWikiMacroManager
import scala.Some

/**
 * Play Plugin to configure XWiki default renderers
 *
 * @author Masatoshi Hayashi
 */
class DefaultXWikiRenderingPlugin(app: Application) extends Plugin {

  val name = "XWiki Default Renderer"

  override def onStart() {
    Logger.info("Configuring XWiki default renderers ...")

    val macroManager = new XWikiMacroManager(DefaultXWikiComponentManager)

    val pluginConfiguration = DefaultXWikiRenderingPluginConfiguration

    pluginConfiguration.macroList.map({macroName =>
      loadMacroClass(macroName).map(macroManager.reloadMacro(_))
      Logger.info("Registered a macro: " + macroName)
    })

    Logger.info("XWiki default renderers configuration done.\n" + pluginConfiguration.rendererConfiguration)
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

}

