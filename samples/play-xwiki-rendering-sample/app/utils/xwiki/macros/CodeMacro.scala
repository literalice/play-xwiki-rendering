package utils.xwiki.macros

import scala.collection.JavaConversions._
import org.xwiki.rendering.transformation.MacroTransformationContext
import org.apache.commons.lang3.{StringUtils, StringEscapeUtils}
import org.xwiki.rendering.block.{Block, ParagraphBlock, RawBlock}
import org.xwiki.rendering.syntax.Syntax
import reflect.BeanProperty
import com.monochromeroad.play.xwiki.rendering.DefaultXWikiMacro

/**
 * @author Masatoshi Hayashi
 */
class CodeMacro extends DefaultXWikiMacro[CodeMacroParameters]("code", "Code Macros") {

  def supportsInlineMode() = true

  def exec(parameters: CodeMacroParameters, content: String, context: MacroTransformationContext): List[Block] = {
    if (StringUtils.isBlank(content)) {
      List.empty[Block]
    } else {
      val codeText =
        "<pre class=\"" + StringEscapeUtils.escapeHtml4(parameters.mode) + "\">" +
        StringEscapeUtils.escapeHtml4(content) +
        "</pre>"
      val codeBlock = new RawBlock(codeText, Syntax.XHTML_1_0)
      if (context.isInline) {
        List[Block](codeBlock)
      } else {
        List[Block](
          new ParagraphBlock(List[Block](codeBlock)))
      }
    }
  }
}

class CodeMacroParameters {
  @BeanProperty var mode = ""
}

