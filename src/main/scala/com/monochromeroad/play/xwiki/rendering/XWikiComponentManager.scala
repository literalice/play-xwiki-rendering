package com.monochromeroad.play.xwiki.rendering

import org.xwiki.component.embed.EmbeddableComponentManager
import org.xwiki.component.descriptor.ComponentDescriptor
import org.xwiki.rendering.parser.Parser

/**
 * XWiki Component Manager / Repository
 *
 * @param classLoader classLoader where the component manager search component classes
 * @author Masatoshi Hayashi
 */
class XWikiComponentManager(classLoader: ClassLoader) {

  val componentManager = new EmbeddableComponentManager()
  componentManager.initialize(classLoader)

  /**
   * Returns a XWiki component from the class loader.
   *
   * @param roleHint hint string to search the component
   * @param roleType the component class
   * @tparam T the component class
   * @return XWiki component
   */
  def getInstance[T](roleHint: String)(implicit roleType: ClassManifest[T]) = componentManager.getInstance(roleType.erasure, roleHint): T

  /**
   * Returns a XWiki component from the class loader.
   *
   * @param roleType the component class
   * @tparam T the component class
   * @return XWiki component
   */
  def getInstance[T](implicit roleType: ClassManifest[T]) = componentManager.getInstance(roleType.erasure): T

  /**
   * Returns a XWiki parser component from the class loader.
   *
   * @param syntaxName syntax name for the parser
   * @return XWiki parser component
   */
  def getParser(syntaxName: String): Parser = {
    getInstance[Parser](syntaxName)
  }

  /**
   * Registers a XWiki component to the component manager.
   *
   * @param componentDescriptor descriptor of the component
   * @param componentInstance  instance of the component
   * @tparam T The component class
   */
  def registerComponent[T](componentDescriptor: ComponentDescriptor[T] , componentInstance: T) {
    componentManager.registerComponent(componentDescriptor, componentInstance)
  }

}

