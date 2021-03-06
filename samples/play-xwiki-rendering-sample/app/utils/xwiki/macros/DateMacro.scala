package utils.xwiki.macros

import com.monochromeroad.play.xwiki.rendering.plugin.DefaultXWikiNoParameterMacro
import org.xwiki.rendering.transformation.MacroTransformationContext
import org.xwiki.rendering.block.{Block, RawBlock}
import org.xwiki.rendering.syntax.Syntax
import java.util.Date

/**
 * @author Masatoshi Hayashi
 */
class DateMacro extends DefaultXWikiNoParameterMacro("date", "Date Macro") {

  def supportsInlineMode() = true

  def exec(content: String, context: MacroTransformationContext): List[Block] = {
    List(new RawBlock("%tF" format new Date, Syntax.XHTML_1_0))
  }
}


