package com.monochromeroad.play.xwiki.rendering.plugin

import com.monochromeroad.play.xwiki.rendering.XWikiStreamRenderer
import com.monochromeroad.play.xwiki.rendering.plugin.{DefaultXWikiComponentManager => XCM, DefaultXWikiRenderingPluginConfiguration => CONF}

/**
 * Default XWiki streaming rendering system in the Play XWiki Plugin.
 *
 * @author Masatoshi Hayashi
 */
object DefaultXWikiStreamRenderer extends XWikiStreamRenderer(XCM, CONF.rendererConfiguration)

