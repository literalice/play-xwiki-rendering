# Play2 XWiki Rendering Module #

This Play2 module allows you to convert texts written in a certain wiki syntax to some formatted texts using XWiki Rendering Framework.

## Example ##

```scala
val result = DefaultXWikiRenderer.render("** Bold ** {{code type="java"}}class Macro{}{{/code}}")
// <p><b>Bold</b><pre class="java">class Macro{}</pre></p>
```

## Documents ##

<dl>
    <dt>Quick Start Guide</dt>
    <dd>http://literalice.github.com/play-xwiki-rendering/</dd>
    <dt>API Document</dt>
    <dd>http://literalice.github.com/play-xwiki-rendering/api/index.html#com.monochromeroad.play.xwiki.rendering.package</dd>
    <dt>Sample Application</dt>
    <dd>https://github.com/literalice/play-xwiki-rendering/tree/master/samples/play-xwiki-rendering-sample</dd>
    <dt>About XWiki Rendering Framework</dt>
    <dd>http://rendering.xwiki.org/xwiki/bin/view/Main/WebHome</dd>
</dl>

