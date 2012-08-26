package com.monochromeroad.play.xwiki.rendering.macros

import org.xwiki.rendering.transformation.MacroTransformationContext
import org.xwiki.rendering.block.{Block, RawBlock}
import org.xwiki.rendering.syntax.Syntax
import com.monochromeroad.play.xwiki.rendering.{DefaultXWikiNoParameterMacro, XWikiNoParameterMacro}
import org.xwiki.properties.BeanManager
import java.util.Date

/**
 * @author Masatoshi Hayashi
 */
class DateMacro(beanManager: BeanManager) extends XWikiNoParameterMacro("date", "Shows the current date", beanManager) with DateMacroSpec {}

class DefaultDateMacro extends DefaultXWikiNoParameterMacro("date", "Shows the current date") with DateMacroSpec { }

trait DateMacroSpec {

  def supportsInlineMode() = true

  def exec(content: String, context: MacroTransformationContext): List[Block] = {
    List(new RawBlock("%tF" format new Date, Syntax.XHTML_1_0))
  }

}


