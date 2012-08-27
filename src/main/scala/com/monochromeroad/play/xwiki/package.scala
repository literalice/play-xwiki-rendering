package com.monochromeroad.play.xwiki

/**
 * =Play2 XWiki Rendering Module=
 *
 * ==Overview==
 * This Play2 module allows you to convert texts written in a certain wiki syntax to some formatted texts using [[http://rendering.xwiki.org/xwiki/bin/view/Main/WebHome XWiki Rendering Framework]].
 *
 * == Example ==
 * {{{
 * val result = DefaultXWikiRenderer.render("** Bold ** {{code type="java"}}class Macro{}{{/code}}")
 * // <b>Bold</b><pre class="java">class Macro{}</pre>
 * }}}
 *
 * @author Masatoshi Hayashi
 */
package object rendering {
}

