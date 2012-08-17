package com.monochromeroad.play.xwiki.rendering

import play.api.Play.current
import org.xwiki.component.embed.EmbeddableComponentManager
import org.xwiki.component.descriptor.DefaultComponentDescriptor
import org.xwiki.rendering.`macro`.Macro
import org.xwiki.rendering.parser.Parser
import org.xwiki.properties.BeanManager

/**
 * @author Masatoshi Hayashi
 */
class XWikiComponentManager(classLoader: ClassLoader) {

  val componentManager = new EmbeddableComponentManager()
  componentManager.initialize(classLoader)

  def getInstance[T](p: String)(implicit c: ClassManifest[T]) = componentManager.getInstance(c.erasure, p): T

  def getInstance[T](implicit c: ClassManifest[T]) = componentManager.getInstance(c.erasure): T

  def getParser(syntaxName: String): Parser = {
    getInstance[Parser](syntaxName)
  }

  def registerMacro(macroClass: Class[_<:XWikiMacro[_]]) {
    val macroConstructor = macroClass.getConstructor(classOf[BeanManager])
    val macroInstance = macroConstructor.newInstance(getInstance[BeanManager])
    macroInstance.initialize()
    registerMacro(macroInstance.macroName, macroInstance)
  }

  def registerMacro(name: String, macroInstance :Macro[_]) {
    val macroDescriptor = new DefaultComponentDescriptor[Macro[_]]()
    macroDescriptor.setImplementation(macroInstance.getClass)
    macroDescriptor.setRoleType(classOf[Macro[_]])
    macroDescriptor.setRoleHint(name)
    componentManager.registerComponent(macroDescriptor, macroInstance)
  }

}

object DefaultXWikiComponentManager extends XWikiComponentManager(current.classloader) {

  def registerDefaultMacro(macroClass: Class[_<:DefaultXWikiMacro[_]]) {
    val macroInstance = macroClass.newInstance()
    macroInstance.initialize()
    registerMacro(macroInstance.macroName, macroInstance)
  }

}

