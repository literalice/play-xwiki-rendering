package com.monochromeroad.play.xwiki.rendering

import java.util
import scala.collection.JavaConversions._
import org.xwiki.rendering.`macro`.AbstractMacro
import org.xwiki.properties.BeanManager
import org.xwiki.rendering.transformation.MacroTransformationContext
import org.xwiki.rendering.block.Block

/**
 * XWiki macro takes some parameters (e.g. {{code for="java" /}}) to use in Play.
 *
 * @tparam P bean class that represents the macro's parameter.
 * @param name macro name ({{xxx}}macro content{{/xxx}}).
 * @param description description about the macro.
 * @param macroBeanManager the bean manager for the macro.
 *
 * @author Masatoshi Hayashi
 */
abstract class XWikiMacro[P](name: String, description: String, macroBeanManager: BeanManager)(implicit c: ClassManifest[P])
  extends AbstractMacro[P](name, description, c.erasure) {

  this.beanManager = macroBeanManager

  def macroName: String = this.name

  /**
   * Macro Implementation
   *
   * @param parameters the bean instance created from the macro's parameter ({{ macro param="xxx"/}}
   * @param content the macro's content (){{macro}}xxx{{/macro}})
   * @param context the macro transformation context
   * @return XDOM Blocks
   */
  def execute(parameters: P, content: String, context: MacroTransformationContext): util.List[Block] =  exec(parameters, content, context)

  def exec(parameters: P, content: String, context: MacroTransformationContext): List[Block]
}

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
