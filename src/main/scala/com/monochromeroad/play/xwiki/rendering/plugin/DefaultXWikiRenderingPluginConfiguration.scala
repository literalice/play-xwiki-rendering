package com.monochromeroad.play.xwiki.rendering.plugin

import com.monochromeroad.play.xwiki.rendering.XWikiRendererConfiguration

/**
 * @author Masatoshi Hayashi
 */
object DefaultXWikiRenderingPluginConfiguration {

  private final val KEY_MACRO: String = "xwiki.rendering.default.macros"

  private final val KEY_MACRO_ENABLED: String = KEY_MACRO + ".enabled"

  private val configuration = play.api.Play.current.configuration

  private val macroListKeys = configuration.keys filter(p => p.startsWith(KEY_MACRO) && !p.startsWith(KEY_MACRO_ENABLED))

  val macroList = macroListKeys.map(configuration.getString(_).get)

  val rendererConfiguration: XWikiRendererConfiguration = configuration.getBoolean(KEY_MACRO_ENABLED) match {
      case Some(macrosEnabled) => new XWikiRendererConfiguration(macrosEnabled = macrosEnabled)
      case _ => new XWikiRendererConfiguration()
    }

}

