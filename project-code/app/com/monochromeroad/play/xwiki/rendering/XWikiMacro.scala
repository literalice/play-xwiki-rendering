package com.monochromeroad.play.xwiki.rendering

import org.xwiki.rendering.`macro`.AbstractMacro
import org.xwiki.properties.BeanManager

/**
 * @author Masatoshi Hayashi
 */
abstract class XWikiMacro[P](name: String, description: String, macroBeanManager: BeanManager)(implicit c: ClassManifest[P])
  extends AbstractMacro[P](name, description, c.erasure) {

  this.beanManager = macroBeanManager

  def macroName: String = this.name

}

abstract class DefaultXWikiMacro[P](name: String, description: String)(implicit c: ClassManifest[P])
  extends XWikiMacro[P](name, description, DefaultXWikiComponentManager.getInstance[BeanManager]) { }
