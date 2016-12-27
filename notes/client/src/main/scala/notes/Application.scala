package notes

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLDivElement
import org.scalajs.jquery.{JQueryAjaxSettings, JQueryXHR, jQuery => $}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

object Application extends js.JSApp {
  private var currentNote = -1

  def main(): Unit = {
  }

  @JSExport
  def noteClicked(id: Int, div: HTMLDivElement): Unit = {
    hideSelection()

    currentNote = id
    val note = s"#note-$currentNote"
    $(s"#note-$currentNote").css("border", "solid")
    $(s"#note-$currentNote").css("border-width", "1px")
    $(s"#note-$currentNote").css("padding", "9px")

    val header = $(s"$note .header-preview").text()
    val content = $(s"$note .content-preview").text()

    setContent(header, content)
  }

  def setContent(header: String, content: String): Unit = {
    $("textarea.head-editor").`val`(header)
    $("textarea.content-editor").`val`(content)
  }

  @JSExport
  def hideSelection(): Unit = {
    if (currentNote != -1) {
      val oldDiv = $(s"#note-$currentNote")
      oldDiv.css("border", "hidden")
      oldDiv.css("padding", "10px")
    }
  }

  @JSExport
  def newNote(): Unit = {
    hideSelection()

    currentNote = -1
    setContent("", "")
  }

  @JSExport
  def saveCurrentNote(): Unit = {
    val header = $("textarea.head-editor").`val`()
    val content = $("textarea.content-editor").`val`()
    if (header.asInstanceOf[String].trim.nonEmpty || content.asInstanceOf[String].trim.nonEmpty)
      $.ajax("/save", js.Dynamic.literal(
        data = s"id=$currentNote&header=$header&content=$content",
        success = (data: js.Any, textStatus: String, jqXHR: JQueryXHR) => {
          dom.document.location.reload(true)
        },
        error = { (jqXHR: JQueryXHR, textStatus: String, errorThrow: String) => {
          $("p.error").show()
        }
        },
        `type` = "POST"
      ).asInstanceOf[JQueryAjaxSettings])
  }
}
