package com.monochromeroad.play.xwiki.rendering

import org.xwiki.component.descriptor.DefaultComponentDescriptor
import org.xwiki.rendering.`macro`.Macro
import org.xwiki.properties.BeanManager

/**
 * @author Masatoshi Hayashi
 */
class XWikiMacroManager(componentManager: XWikiComponentManager) {

  def registerMacro(macroClass: Class[_<:XWikiMacro[_]]) {
    val macroInstance = getMacroInstance(macroClass)
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

  private def getMacroInstance[T<:XWikiMacro[_]](macroClass: Class[T]): T = {
    try {
      macroClass.newInstance()
    } catch {
      case _: InstantiationException =>
        try {
          val constructor = macroClass.getConstructor(classOf[BeanManager])
          constructor.newInstance(componentManager.getInstance[BeanManager])
        } catch {
          case e: NoSuchMethodException =>
            throw new IllegalArgumentException("The macro class has not a proper constructor.", e)
        }
    }
  }
}