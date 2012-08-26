package com.monochromeroad.play.xwiki.rendering

import org.xwiki.properties.BeanManager
import org.xwiki.rendering.transformation.MacroTransformationContext
import org.xwiki.rendering.block.Block

/**
 * XWiki no-parameter macro (e.g. {{date /}}) base to use in Grails.
 *
 * <p>You need to create a constructor that takes no arguments.</p>
 *
 * @author Masatoshi Hayashi
 */
abstract class XWikiNoParameterMacro(name: String, description: String, macroBeanManager: BeanManager) extends XWikiMacro[Any](name, description, macroBeanManager) {

  def exec(parameters: Any, content: String, context: MacroTransformationContext): List[Block] =  exec(content, context)

  def exec(content: String, context: MacroTransformationContext): List[Block]

}

abstract class DefaultXWikiNoParameterMacro(name: String, description: String)
  extends XWikiNoParameterMacro(name, description, DefaultXWikiComponentManager.getInstance[BeanManager]) { }

