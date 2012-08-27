package com.monochromeroad.play.xwiki.rendering.macros

import org.xwiki.properties.BeanManager
import org.xwiki.rendering.transformation.MacroTransformationContext
import org.xwiki.rendering.block.Block

/**
 * XWiki no-parameter macro (e.g. {{date /}}) base to use in Play.
 *
 * You need to create a constructor that takes no arguments.
 *
 * @param name macro name ({{xxx}}macro content{{/xxx}}).
 * @param description description about the macro.
 * @param macroBeanManager the bean manager for the macro.
 * @author Masatoshi Hayashi
 *
 */
abstract class XWikiNoParameterMacro(name: String, description: String, macroBeanManager: BeanManager) extends XWikiMacro[Any](name, description, macroBeanManager) {

  def exec(parameters: Any, content: String, context: MacroTransformationContext): List[Block] =  exec(content, context)

  def exec(content: String, context: MacroTransformationContext): List[Block]

}

