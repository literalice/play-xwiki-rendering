package utils.xwiki.macros

import org.xwiki.rendering.transformation.MacroTransformationContext
import java.util.Collections
import org.apache.commons.lang3.{StringUtils, StringEscapeUtils}
import org.xwiki.rendering.block.{Block, ParagraphBlock, RawBlock}
import org.xwiki.rendering.syntax.Syntax
import java.util
import reflect.BeanProperty
import com.monochromeroad.play.xwiki.rendering.DefaultXWikiMacro

/**
 * @author Masatoshi Hayashi
 */
class CodeMacro extends DefaultXWikiMacro[CodeMacroParameters]("code", "Code Macros") {

  def supportsInlineMode() = true

  def execute(parameters: CodeMacroParameters, content: String, context: MacroTransformationContext): util.List[Block] = {
    if (StringUtils.isBlank(content)) {
      Collections.emptyList[Block]()
    } else {
      val codeText =
        "<pre class=\"" + StringEscapeUtils.escapeHtml4(parameters.mode) + "\">" +
        StringEscapeUtils.escapeHtml4(content) +
        "</pre>"
      val codeBlock = new RawBlock(codeText, Syntax.XHTML_1_0)
      if (context.isInline) {
        Collections.singletonList[Block](codeBlock)
      } else {
        Collections.singletonList[Block](
          new ParagraphBlock(Collections.singletonList[Block](codeBlock)))
      }
    }
  }
}

class CodeMacroParameters {
  @BeanProperty var mode = ""
}

