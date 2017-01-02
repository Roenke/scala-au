package notes

import org.scalajs.jquery.{JQueryAjaxSettings, JQueryXHR, jQuery => $}
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.Dictionary
import scala.scalajs.js.annotation.JSExport

@JSExport
class SignInJS {
  @JSExport
  def signin(): Unit = {
    val form = $("form.login-form")
    form.submit(false)
    $.ajax("/signin", js.Dynamic.literal(
      data = form.serialize(),
      success = (data: js.Any, textStatus: String, jqXHR: JQueryXHR) => {
        $("p.error").hide()
        dom.document.location.reload(true)
      },
      error = { (jqXHR: JQueryXHR, textStatus: String, errorThrow: String) =>{
        $("p.error").show()
      }
      },
      `type` = "POST"
    ).asInstanceOf[JQueryAjaxSettings])
  }

  @JSExport
  def signup(): Unit = {
    val form = $("form.register-form")
    form.submit(false)
    $.ajax("/signup", js.Dynamic.literal(
      data = form.serialize(),
      success = (data: js.Any, textStatus: String, jqXHR: JQueryXHR) =>
        dom.document.location.reload(true),
      error = { (jqXHR: JQueryXHR, textStatus: String, errorThrow: String) =>
        println(s"jqXHR=${jqXHR.status},text=$textStatus,err=$errorThrow")
      },
      `type` = "POST"
    ).asInstanceOf[JQueryAjaxSettings])
  }

  @JSExport
  def toggleForm(): Unit = {
    $("form").animate(Dictionary("height" -> "toggle", "opacity" -> "toggle"))
  }
}
