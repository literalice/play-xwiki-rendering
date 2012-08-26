package com.monochromeroad.play.xwiki.rendering

import play.api.Play.current
import org.xwiki.component.embed.EmbeddableComponentManager
import org.xwiki.component.descriptor.ComponentDescriptor
import org.xwiki.rendering.parser.Parser

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

  def registerComponent[T](componentDescriptor: ComponentDescriptor[T] , componentInstance: T) {
    componentManager.registerComponent(componentDescriptor, componentInstance)
  }

}

object DefaultXWikiComponentManager extends XWikiComponentManager(current.classloader) {}

