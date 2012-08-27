package com.monochromeroad.play.xwiki

/**
 * =Play2 XWiki Rendering Module=
 *
 * ==Overview==
 * This Play2 module allows you to convert texts written in a certain wiki syntax to some formatted texts using [[http://rendering.xwiki.org/xwiki/bin/view/Main/WebHome XWiki Rendering Framework]].
 *
 * == Example ==
 * {{{
 * val componentManager = new XWikiComponentManager(getClass.getClassLoader)
 * val macroManager = new XWikiMacroManager(componentManager)
 * macroManager.registerMacro(classOf[CodeMacro])
 * macroManager.registerMacro(classOf[DateMacro])
 *
 * val renderer = new XWikiStringStreamRenderer(componentManager)
 *
 * val builder = new StringBuilder()
 * renderer.render(new StringReader("** Bold ** {{code type="java"}}class Macro{}{{/code}}"), builder.append(_))
 * val result  = builder.toString()
 * }}}
 *
 * @author Masatoshi Hayashi
 */
package object rendering {
}

