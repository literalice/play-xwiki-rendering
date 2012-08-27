package com.monochromeroad.play.xwiki.rendering

/**
 * XWiki Rendering macro support
 *
 * ==Overview==
 * This package has some stuff to integrate with XWiki macro.
 *
 * To create your own macros, extend [[com.monochromeroad.play.xwiki.rendering.macros.XWikiMacro]] or [[com.monochromeroad.play.xwiki.rendering.macros.XWikiNoParameterMacro]].
 * See also [[http://rendering.xwiki.org/xwiki/bin/view/Main/ExtendingMacro Writing a Macro - XWiki Rendering Framework]].
 *
 * @author Masatoshi Hayashi
 */
package object macros {
}

/**
 * XWiki Rendering Play2 default renderer plugin
 *
 * ==Overview==
 * This Play2 plugin provides some XWiki rendereres.
 *
 *   - '''XDOM based renderer''' DefaultXWikiRenderer.
 *   - '''Streaming based renderer''' DefaultXWikiStreamRenderer
 *   - '''Simplified streaming based renderer''' DefaultXWikiStringStreamRenderer
 *
 * And XWiki macro base for their default renderer are also provided.
 *
 *   - [[com.monochromeroad.play.xwiki.rendering.plugin.DefaultXWikiMacro]]
 *   - [[com.monochromeroad.play.xwiki.rendering.plugin.DefaultXWikiNoParameterMacro]]
 *
 * @author Masatoshi Hayashi
 */
package object plugin {
}

