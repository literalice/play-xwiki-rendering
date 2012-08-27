package controllers

import play.api._
import play.api.mvc._
import java.io.StringReader
import play.api.libs.iteratee.Enumerator.Pushee
import com.monochromeroad.play.xwiki.rendering.plugin.{DefaultXWikiStringStreamRenderer => XCM}
import play.api.libs.iteratee.Enumerator

object Application extends Controller {
  
  def index = Action {
    val src = new StringReader("**TEST** {{code mode='scala'}}trait TestTrait{}{{/code}} \nCurrent date: {{date /}} {{comment}}This is a comment that would not be contained in the result{{/comment}}")
    // <p><strong>TEST</strong> <pre class="scala">trait TestTrait{}</pre> <br/>Current date: 2012-08-27</p>

    val onStart: Pushee[String] => Unit = { pushee =>
      val push: String => Unit = { p1 =>
        pushee.push(p1)
      }
      XCM.render(src, push)
      pushee.close()
    }

    val channel = Enumerator.pushee(onStart)
    Ok.stream(channel).as(JSON)
  }
  
}

