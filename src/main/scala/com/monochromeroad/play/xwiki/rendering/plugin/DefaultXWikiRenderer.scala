package com.monochromeroad.play.xwiki.rendering.plugin

import com.monochromeroad.play.xwiki.rendering.XWikiRenderer
import com.monochromeroad.play.xwiki.rendering.plugin.{DefaultXWikiComponentManager => XCM, DefaultXWikiRenderingPluginConfiguration => CONF}

/**
 * Default XWiki rendering system in the Play XWiki Plugin.
 *
 * @author Masatoshi Hayashi
 */
object DefaultXWikiRenderer extends XWikiRenderer(XCM, CONF.rendererConfiguration)

