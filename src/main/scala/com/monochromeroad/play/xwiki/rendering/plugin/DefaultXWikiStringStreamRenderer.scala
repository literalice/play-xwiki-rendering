package com.monochromeroad.play.xwiki.rendering.plugin

import com.monochromeroad.play.xwiki.rendering.XWikiStringStreamRenderer

/**
 * Default XWiki streaming rendering system in the Play XWiki Plugin.
 *
 * @author Masatoshi Hayashi
 */
object DefaultXWikiStringStreamRenderer extends XWikiStringStreamRenderer(DefaultXWikiComponentManager)

