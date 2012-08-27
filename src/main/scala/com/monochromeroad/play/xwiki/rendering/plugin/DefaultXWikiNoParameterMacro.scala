package com.monochromeroad.play.xwiki.rendering.plugin

import org.xwiki.properties.BeanManager
import com.monochromeroad.play.xwiki.rendering.macros.XWikiNoParameterMacro

/**
 * XWiki no-parameter macro (e.g. {{date /}}) base to use in Play default component manager.
 *
 * @param name macro name ({{xxx}}macro content{{/xxx}}).
 * @param description description about the macro.
 */
abstract class DefaultXWikiNoParameterMacro(name: String, description: String)
  extends XWikiNoParameterMacro(name, description, DefaultXWikiComponentManager.getInstance[BeanManager]) { }

