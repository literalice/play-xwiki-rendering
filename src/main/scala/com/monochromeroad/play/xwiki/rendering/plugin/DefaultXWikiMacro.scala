package com.monochromeroad.play.xwiki.rendering.plugin

import org.xwiki.properties.BeanManager
import com.monochromeroad.play.xwiki.rendering.macros.XWikiMacro

/**
 * XWiki macro takes some parameters (e.g. {{code for="java" /}}) to use in Play default component manager.
 *
 * @tparam P bean class that represents the macro's parameter.
 * @param name macro name ({{xxx}}macro content{{/xxx}}).
 * @param description description about the macro.
 *
 * @author Masatoshi Hayashi
 */
abstract class DefaultXWikiMacro[P](name: String, description: String)(implicit c: ClassManifest[P])
  extends XWikiMacro[P](name, description, DefaultXWikiComponentManager.getInstance[BeanManager]) { }
