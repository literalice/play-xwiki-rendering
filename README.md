# XWiki Rendering Framework Integration for Play 2 #

## Introduction ##

This Play2 module allows you to convert texts using XWiki Rendering Framework.

### Example ###

```scala
val result = DefaultXWikiRenderer.render("** Bold ** {{code type="java"}}class Macro{}{{/code}}")
// <p><b>Bold</b><pre class="java">class Macro{}</pre></p>
```

See also [XWiki Rendering Framework](http://rendering.xwiki.org/xwiki/bin/view/Main/WebHome) for the details about the framework.

## Installing the module ##

To use the module, a repository and dependency should be added to your "project/Build.scala"

<dl>
    <dt>Repository</dt>
    <dd>http://repository-monochromeroad.forge.cloudbees.com/release</dd>
    <dt>Dependency</dt>
    <dd>"com.monochromeroad" %% "play-xwiki-rendering" % "1.0"</dd>
</dl>

```scala
object ApplicationBuild extends Build {

  val appName         = "Sample application integrated with XWiki Rendering"
  val appVersion      = "1.0"

  val appDependencies = Seq(
    "com.monochromeroad" %% "play-xwiki-rendering" % "1.0"
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    resolvers += "Monochrmeroad CloudBees Repository" at "http://repository-monochromeroad.forge.cloudbees.com/release"
  )
}
```

## Default XWiki Renderer ##

This moudle provides some XWiki rendereres as a Play plugin.

<dl>
    <dt>com.monochromeroad.play.xwiki.rendering.DefaultXWikiRenderer</dt>
    <dd>XDOM based renderer</dd>
    <dt>com.monochromeroad.play.xwiki.rendering.DefaultXWikiStreamRenderer</dt>
    <dd>Streaming based renderer</dd>
    <dt>com.monochromeroad.play.xwiki.rendering.DefaultXWikiStringStreamRenderer</dt>
    <dd>Simplified streaming based renderer</dd>
</dl>

### Installing the default renderers ###

Just add a entry to the "plugins.sbt" in the conf directory.

    10000:com.monochromeroad.play.xwiki.rendering.DefaultXWikiRenderingPlugin

### DefaultXWikiRenderer ###

```scala
import com.monochromeroad.play.xwiki.rendering.{DefaultXWikiRenderer => XCM}
object Application extends Controller {

  def index = Action {
    val htmlText = XCM.render("**TEST** //italic//")

    // ...
  }
}
```

### DefaultXWikiStreamRenderer ###

```scala
import java.io.StringReader
import com.monochromeroad.play.xwiki.rendering.{DefaultXWikiStreamRenderer => XCM}
object Application extends Controller {

  def index = Action {
    val sourceReader = new StringReader("**TEST** //italic//")
    val htmlText = XCM.render(sourceReader, "-- iv --", {acc, n => acc + n})

    // ...
  }
}
```

### DefaultXWikiStringStreamRenderer ###

```scala
import java.io.StringReader
import com.monochromeroad.play.xwiki.rendering.{DefaultXWikiStringStreamRenderer => XCM}
object Application extends Controller {

  def index = Action {
    val sourceReader = new StringReader("**TEST** //italic//")
    val result = new StringBuilder()
    XCM.render(sourceReader, {n => result.append(n)})

    // ...
  }
}
```

### Macros ###

You can use some XWiki macros.

To use XWiki macros, add a macro jar to your project or register a macro source code.

#### Adding a macro jar ####

First, add the macro jar you want to use in the project.

```scala
val appDependencies = Seq(
  "org.xwiki.rendering" % "xwiki-rendering-macro-comment" % "4.1.3"
)
```

and

```scala
import com.monochromeroad.play.xwiki.rendering.{DefaultXWikiRenderer => XCM}
object Application extends Controller {

  def index = Action {
    val htmlText = XCM.render("**TEST** {{comment}}This is a comment that would not be contained in the result{{/comment}}")
    // <p><b>TEST</b> </p>

    // ...
  }
}
```

#### Registering a macro code ####

If you want to create you own macro, the XWiki's site would be useful.

[Writing a Macro](http://rendering.xwiki.org/xwiki/bin/view/Main/ExtendingMacro)

First, write a macro code that extends DefaultXWikiMacro and has no parameter constructor.

and regster the macro class in the "conf/application.conf"

    xwiki.rendering.default.macros.1=utils.xwiki.macros.CodeMacro


## License ##

LGPL Version 2.1

