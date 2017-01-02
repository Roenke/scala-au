package controllers

import javax.inject.Inject

import dao.UserDAO
import play.api.data.Forms.mapping
import play.api.data.{Form, Forms}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, AnyContent, Controller}

import scala.concurrent.Future


class Registration @Inject()(userDAO: UserDAO) extends Controller {

  def signIn: Action[AnyContent] = Action async { implicit request =>
    signInForm.bindFromRequest.value match {
      case Some(x) => userDAO.getByLogin(x.login).map {
        case Some(y) => if (x.password.equals(y.password)) {
          Ok.withSession("user-login" -> x.login)
        } else {
          Forbidden
        }
        case None => Forbidden
      }
      case None => Future.successful(NoContent)
    }
  }

  def signUp: Action[AnyContent] = Action async { implicit request =>
    signUpForm.bindFromRequest.value match {
      case Some(x) => userDAO.newUser(x.name, x.login, x.password).map { _ => Ok.withSession("user-login" -> x.login) }
      case None => Future.successful(NoContent)
    }
  }

  val signInForm = Form(
    mapping(
      "login" -> Forms.text,
      "password" -> Forms.text
    )(OldUser.apply)(OldUser.unapply)
  )

  val signUpForm = Form(
    mapping(
      "name" -> Forms.text,
      "login" -> Forms.text,
      "password" -> Forms.text
    )(NewUser.apply)(NewUser.unapply)
  )

  case class NewUser(name: String, login: String, password: String)

  case class OldUser(login: String, password: String)

}
