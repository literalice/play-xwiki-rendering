package com.monochromeroad.play.xwiki.rendering.macros

import org.xwiki.rendering.transformation.MacroTransformationContext
import java.util.Collections
import org.apache.commons.lang3.{StringUtils, StringEscapeUtils}
import org.xwiki.rendering.block.{Block, ParagraphBlock, RawBlock}
import org.xwiki.rendering.syntax.Syntax
import java.util
import reflect.BeanProperty
import com.monochromeroad.play.xwiki.rendering.{DefaultXWikiMacro, XWikiMacro}
import org.xwiki.properties.BeanManager

/**
 * @author Masatoshi Hayashi
 */
class RbMacro(beanManager: BeanManager) extends XWikiMacro[RbMacroParameters]("rb", "Text Ruby", beanManager) with RbMacroSpec {}

class DefaultRbMacro extends DefaultXWikiMacro[RbMacroParameters]("rb", "Text Ruby") with RbMacroSpec { }

trait RbMacroSpec {

  def supportsInlineMode() = true

  def execute(parameters: RbMacroParameters, content: String, context: MacroTransformationContext): util.List[Block] = {
    if (StringUtils.isBlank(content)) {
      Collections.emptyList[Block]()
    } else {
      val rubyText = "<ruby>" + StringEscapeUtils.escapeHtml4(content) +
        "<rp>(</rp><rt>" + StringEscapeUtils.escapeHtml4(parameters.read) + "</rt><rp>)</rp></ruby>"
      val rubyBlock = new RawBlock(rubyText, Syntax.XHTML_1_0)
      if (context.isInline) {
        Collections.singletonList[Block](rubyBlock)
      } else {
        Collections.singletonList[Block](
          new ParagraphBlock(Collections.singletonList[Block](rubyBlock)))
      }
    }
  }

}

class RbMacroParameters {
  @BeanProperty var read = ""
}

