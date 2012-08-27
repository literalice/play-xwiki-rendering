package com.monochromeroad.play.xwiki.rendering.plugin

import com.monochromeroad.play.xwiki.rendering.macros.DateMacroSpec

/**
 * @author Masatoshi Hayashi
 */

class DateMacro extends DefaultXWikiNoParameterMacro("date", "Shows the current date") with DateMacroSpec { }

