package com.monochromeroad.play.xwiki.rendering

import java.util
import scala.collection.JavaConversions._
import org.xwiki.rendering.`macro`.AbstractMacro
import org.xwiki.properties.BeanManager
import org.xwiki.rendering.transformation.MacroTransformationContext
import org.xwiki.rendering.block.Block

/**
 * XWiki macro takes some parameters (e.g. {{code for="java" /}}) to use in Grails.
 *
 * @author Masatoshi Hayashi
 */
abstract class XWikiMacro[P](name: String, description: String, macroBeanManager: BeanManager)(implicit c: ClassManifest[P])
  extends AbstractMacro[P](name, description, c.erasure) {

  this.beanManager = macroBeanManager

  def macroName: String = this.name

  def execute(parameters: P, content: String, context: MacroTransformationContext): util.List[Block] =  exec(parameters, content, context)

  def exec(parameters: P, content: String, context: MacroTransformationContext): List[Block]
}

abstract class DefaultXWikiMacro[P](name: String, description: String)(implicit c: ClassManifest[P])
  extends XWikiMacro[P](name, description, DefaultXWikiComponentManager.getInstance[BeanManager]) { }
