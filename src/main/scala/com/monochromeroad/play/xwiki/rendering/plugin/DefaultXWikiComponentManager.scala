package com.monochromeroad.play.xwiki.rendering.plugin

import com.monochromeroad.play.xwiki.rendering.XWikiComponentManager

/**
 * XWiki Component Manager / Repository for Play current class loader
 *
 * @author Masatoshi Hayashi
 */
object DefaultXWikiComponentManager extends XWikiComponentManager(play.api.Play.current.classloader) {}

