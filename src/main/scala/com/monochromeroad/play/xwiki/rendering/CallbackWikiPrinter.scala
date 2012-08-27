package com.monochromeroad.play.xwiki.rendering

import org.xwiki.rendering.renderer.printer.WikiPrinter

/**
 * Printer for processing a callback during parsing a text.
 *
 * @param init a initial value when starting to parse a text.
 * @param callback a callback called during parsing a text.
 * @tparam T the result value type
 * @author Masatoshi Hayashi
 */
class CallbackWikiPrinter[T](init: T, callback: (T,  String) => T) extends WikiPrinter {

  private var acc = init

  def print(p1: String) {
    acc = callback(acc, p1)
  }

  def println(p1: String) {
    acc = callback(acc, p1 + "\n")
  }

  def get: T = acc

}
