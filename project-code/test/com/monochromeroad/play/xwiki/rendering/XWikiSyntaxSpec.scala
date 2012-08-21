package com.monochromeroad.play.xwiki.rendering

import org.specs2.mutable._
import org.specs2.specification.Example

/**
 * XWiki Syntax Specification
 *
 * <a href="http://platform.xwiki.org/xwiki/bin/view/Main/XWikiSyntax">XWiki Syntaxes</a>
 *
 * @author Masatoshi Hayashi
 */
trait XWikiSyntaxSpec extends Specification {

  val cheatSheet = """
= Heading =

== level 2 ==

=== level 3 ===

==== level 4 ====

===== level 5 =====

====== level 6 ======

= Text Formatting =

**bold**

__underline__

//italic//

--strike--

## monospace ##

some ^^superscript^^
some ,,subscript,,

= Horizontal Line =

== Simple horizontal line ==

----

== Parametrized horizontal line ==

(% style="color:blue" %)
----

= Lists =

== Bulleted list ==

* item 1
** item 2
*** item 3
* item 4

== Numbered list ==

1. item 1
11. item 2
111. item 3
1. item 4

== Mixed list ==

1. item 1
1*. item 2
1*. item 3
1. item 4

== Styled list ==

(% style="list-style-type: square" %)
* item 1
* item 2

= Definition Lists =

== Standard definition ==

; term
: definition

== Nested definitions ==

; term 1
: definition 1
:; term 2
:: definition 2

== Parametrized definition ==

(% style="color:blue" %)
; term
: definition

= Line break =

Type1\\New line

OR

Type2
New line

= Links =

== Link to a page in the current Space ==

[[WebHome]]

== Link with a label ==

[[label>>WebHome]]

== Link with XWiki Syntax in the label ==

[[**bold label**>>WebHome]]

== Link to a page with the space specified ==

[[Main.WebHome]]

== Link to a subwiki ==

[[subwiki:Main.WebHome]]

== Link that opens in a new window ==

[[label>>WebHome||rel="_blank"]]

== Link to a URL directly in the text ==

This is a URL: http://xwiki.org

== Link to a URL ==

[[http://xwiki.org]]

== Link to a URL with a label ==

[[XWiki>>http://xwiki.org]]

== Link to an email address ==

[[john@smith.net>>mailto:john@smith.net]]

== Image Link ==

[[image:Space2.Page2@img.png>>Space1.Page1]]

== Image Link with image parameters ==

[[[[image:Space2.Page2@img.png||width="26" height="26"]]>>Space1.Page1]]

== Link to a Heading in a page ==

[[label>>Space.Page#HMyheading]]

= Tables =

== Standard table ==

|=Title 1|=Title 2
|Word 1|Word 2

or

!=Title 3!=Title 4
!!Word 3!!Word 4

== Parametrized table ==

(% style="background-color:red;align=center" %)
|=Title 1|=(% style="background-color:yellow" %)Title 2
|Word 1|Word 2

= Images =

== Image from attachment on current page ==

image:img.png

== Image from attachment on another page ==

image:Space.Page@img.png

== Image with parameters ==

[[image:img.png||width="25" height="25"]]

== Images located at URL ==

image:http://some/url/img.png

= Verbatim =

== Verbatim inline ==

Some verbatim {{{**[[not rendered]]**}}} content

== Verbatim block ==

{{{
multi line
**verbatim**
content
}}}

= Quotations =

> john said this
I said ok

== Nested quotes ==

> john said this
>> marie answered that
I said ok

= Groups =

|=Header 1|=Header 2|=Header 3
|Cell One|(((
= Embedded document =

Some embedded paragraph.

* list item one
* list item two
  ** sub-item 1
  ** sub-item 2
))) | Cell Three

Next paragraph in the top-level document

= Escapes =

This is not a ~[~[link~]~]

= Macros =

{{rb read="rb text"}}Text{{/rb}}

                   """.trim

  val sampleHTML = Map(
    "heading" -> Map(
      "level1" -> "<h1 id=\"HHeading\"><span>Heading</span></h1>",
      "level2" -> "<h2 id=\"Hlevel2\"><span>level 2</span></h2>",
      "level3"-> "<h3 id=\"Hlevel3\"><span>level 3</span></h3>",
      "level4"-> "<h4 id=\"Hlevel4\"><span>level 4</span></h4>",
      "level5"-> "<h5 id=\"Hlevel5\"><span>level 5</span></h5>",
      "level6"-> "<h6 id=\"Hlevel6\"><span>level 6</span></h6>"
    ),
    "format" -> Map(
      "strong" -> "<strong>bold</strong>",
      "underline" -> "<ins>underline</ins>",
      "italic" -> "<em>italic</em>",
      "strike" -> "<del>strike</del>",
      "monospace" -> "<tt>&nbsp;monospace&nbsp;</tt>",
      "superscript" -> "some <sup>superscript</sup>",
      "subscript" -> "some <sub>subscript</sub>"
    ),
    "horizontal line" -> Map(
      "standard" -> "<hr/>",
      "parameterized" -> "<hr style=\"color:blue\"/>"
    ),
    "list" -> Map(
      "bulleted" -> "<ul><li>item 1<ul><li>item 2<ul><li>item 3</li></ul></li></ul></li><li>item 4</li></ul>",
      "numbered" -> "<ol><li>item 1<ol><li>item 2<ol><li>item 3</li></ol></li></ol></li><li>item 4</li></ol>",
      "mixed" -> "<ol><li>item 1<ul><li>item 2</li><li>item 3</li></ul></li><li>item 4</li></ol>",
      "parameterized" -> "<ul style=\"list-style-type: square\"><li>item 1</li><li>item 2</li></ul>"
    ),
    "definition list" -> Map(
      "standard" -> "<dl><dt>term</dt><dd>definition</dd></dl>",
      "nested" -> "<dl><dt>term 1</dt><dd>definition 1<dl><dt>term 2</dt><dd>definition 2</dd></dl></dd></dl>",
      "parameterized" -> "<dl style=\"color:blue\"><dt>term</dt><dd>definition</dd></dl>"
    ),
    "line break" -> Map(
      "type1" -> "Type1<br/>New line",
      "type2" -> "Type2<br/>New line"
    ),
    "link" -> Map(
      "internal" -> "<span class=\"wikiexternallink\"><a href=\"WebHome\"><span class=\"wikigeneratedlinkcontent\">WebHome</span></a></span>",
      "labeled internal" -> "<span class=\"wikiexternallink\"><a href=\"WebHome\">label</a></span>",
      "rich labeled internal" -> "<span class=\"wikiexternallink\"><a href=\"WebHome\"><strong>bold label</strong></a></span>",
      "space" -> "<span class=\"wikiexternallink\"><a href=\"Main.WebHome\"><span class=\"wikigeneratedlinkcontent\">Main.WebHome</span></a></span>",
      "subwiki" -> "<span class=\"wikiexternallink\"><a href=\"subwiki:Main.WebHome\"><span class=\"wikigeneratedlinkcontent\">subwiki:Main.WebHome</span></a></span>",
      "new window" -> "<span class=\"wikiexternallink\"><a rel=\"_blank\" href=\"WebHome\">label</a></span>",
      "plain" -> "This is a URL: <span class=\"wikiexternallink\"><a class=\"wikimodel-freestanding\" href=\"http://xwiki.org\"><span class=\"wikigeneratedlinkcontent\">http://xwiki.org</span></a></span>",
      "url" -> "<span class=\"wikiexternallink\"><a href=\"http://xwiki.org\"><span class=\"wikigeneratedlinkcontent\">http://xwiki.org</span></a></span>",
      "labeled url" -> "<span class=\"wikiexternallink\"><a href=\"http://xwiki.org\">XWiki</a></span>",
      "email" -> "<span class=\"wikiexternallink\"><a href=\"mailto:john@smith.net\">john@smith.net</a></span>",
      "image" -> "<span class=\"wikiexternallink\"><a href=\"Space1.Page1\"><img src=\"Space2.Page2@img.png\" class=\"wikimodel-freestanding\" alt=\"Space2.Page2@img.png\"/></a></span>",
      "parameterized image" -> "<span class=\"wikiexternallink\"><a href=\"Space1.Page1\"><img src=\"Space2.Page2@img.png\" width=\"26\" height=\"26\" alt=\"Space2.Page2@img.png\"/></a></span>",
      "anchor" -> "<span class=\"wikiexternallink\"><a href=\"Space.Page#HMyheading\">label</a></span>"
    ),
    "table" -> Map(
      "standard1" -> "<table><tr><th scope=\"col\">Title 1</th><th scope=\"col\">Title 2</th></tr><tr><td>Word 1</td><td>Word 2</td></tr></table>",
      "standard2" -> "<table><tr><th scope=\"col\">Title 3</th><th scope=\"col\">Title 4</th></tr><tr><td>Word 3</td><td>Word 4</td></tr></table>",
      "parameterized" -> "<table style=\"background-color:red;align=center\"><tr><th scope=\"col\">Title 1</th><th style=\"background-color:yellow\" scope=\"col\">Title 2</th></tr><tr><td>Word 1</td><td>Word 2</td></tr></table>"
    )
  )

  def containXhtmlHeading(target: String) :Example = {
    "contain heading" in {
      val renderedHeading = sampleHTML("heading")
      target must contain(renderedHeading("level1"))
      target must contain(renderedHeading("level2"))
      target must contain(renderedHeading("level3"))
      target must contain(renderedHeading("level4"))
      target must contain(renderedHeading("level5"))
      target must contain(renderedHeading("level6"))
    }
  }

  def containXhtmlFormats(target: String) :Example = {
    "contain html format tag" in {
      val renderedFormat = sampleHTML("format")
      target must contain(renderedFormat("strong"))
      target must contain(renderedFormat("underline"))
      target must contain(renderedFormat("italic"))
      target must contain(renderedFormat("strike"))
      target must contain(renderedFormat("monospace"))
      target must contain(renderedFormat("superscript"))
      target must contain(renderedFormat("subscript"))
    }
  }

  def containXhtmlHorizontalLines(target: String) :Example = {
    "contain horizontal line" in {
      val renderedLine = sampleHTML("horizontal line")
      target must contain(renderedLine("standard"))
      target must contain(renderedLine("parameterized"))
    }
  }

  def containXhtmlLists(target: String) :Example = {
    "contain list" in {
      val renderedList = sampleHTML("list")
      target must contain(renderedList("bulleted"))
      target must contain(renderedList("numbered"))
      target must contain(renderedList("mixed"))
      target must contain(renderedList("parameterized"))
    }
  }

  def containXhtmlDefLists(target: String) :Example = {
    "contain definition list" in {
      val renderedDefList = sampleHTML("definition list")
      target must contain(renderedDefList("standard"))
      target must contain(renderedDefList("nested"))
      target must contain(renderedDefList("parameterized"))
    }
  }

  def containXhtmlLineBreaks(target: String) :Example = {
    "contain html line break" in {
      val renderedLineBreak = sampleHTML("line break")
      target must contain(renderedLineBreak("type1"))
      target must contain(renderedLineBreak("type2"))
    }
  }

  def containXhtmlLinks(target: String) :Example = {
    "contain links" in {
      val renderedLink = sampleHTML("link")
      target must contain(renderedLink("internal"))
      target must contain(renderedLink("labeled internal"))
      target must contain(renderedLink("rich labeled internal"))
      target must contain(renderedLink("space"))
      target must contain(renderedLink("subwiki"))
      target must contain(renderedLink("new window"))
      target must contain(renderedLink("plain"))
      target must contain(renderedLink("url"))
      target must contain(renderedLink("labeled url"))
      target must contain(renderedLink("email"))
      target must contain(renderedLink("image"))
      target must contain(renderedLink("parameterized image"))
      target must contain(renderedLink("anchor"))
    }
  }

  def containXhtmlTables(target: String) :Example = {
    "contain tables" in {
      val renderedTable = sampleHTML("table")
      target must contain(renderedTable("standard1"))
      target must contain(renderedTable("standard2"))
      target must contain(renderedTable("parameterized"))
    }
  }

}
