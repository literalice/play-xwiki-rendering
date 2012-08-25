package com.monochromeroad.play.xwiki.rendering

import org.xwiki.rendering.syntax.Syntax

/**
 * @author Masatoshi Hayashi
 */
class XWikiRendererConfiguration(
          val defaultInputSyntax: Syntax = Syntax.XWIKI_2_1,
          val defaultOutputSyntax: Syntax = Syntax.XHTML_1_0,
          val macrosEnabled: Boolean = true) {
}
