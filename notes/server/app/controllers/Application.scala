package controllers

import java.sql.Timestamp
import javax.inject.Inject

import dao.{NoteDAO, UserDAO}
import models.{Note, NoteUtil, User}
import play.api.data.Forms.mapping
import play.api.data.{Form, Forms}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, AnyContent, Controller, Session}

import scala.concurrent.Future

class Application @Inject()(usersDAO: UserDAO, noteDAO: NoteDAO) extends Controller {

  def index: Action[AnyContent] = Action async { request =>
    val now = new Timestamp(System.currentTimeMillis());
    request.session.get("user-login") match {
      case Some(login) => usersDAO.getByLogin(login).flatMap {
        case Some(user) => noteDAO.getByUser(user.id).map { notes =>
          Ok(views.html.main("Notes pro")(views.html.notes.notes(user.name, user.login, notes)))
            .withSession(request.session)
        }
        case None => Future.successful(Ok(views.html.registration()))
      }
      case None => Future.successful(Ok(views.html.registration()))
    }
  }

  def logout: Action[AnyContent] = Action async { request =>
    Future.successful(Redirect(routes.Application.index()).withNewSession)
  }

  def save(): Action[AnyContent] = Action async { implicit request =>
    def doSaveNote(user: User, note: Note, noteInfo: NoteInfo): Future[Status] = {
      if (user.id != note.ownerId) {
        Future.successful(Unauthorized)
      } else {
        noteDAO.saveNote(note).map(_ => Ok)
      }
    }

    def doSave(login: String, noteInfo: NoteInfo): Future[Status] = {
      usersDAO.getByLogin(login).flatMap {
        case Some(user) => noteDAO.getById(noteInfo.id).flatMap {
          case Some(note) => doSaveNote(user, NoteUtil.modify(note, noteInfo.header, noteInfo.content), noteInfo)
          case None => doSaveNote(user, NoteUtil.create(user.id, noteInfo.header, noteInfo.content), noteInfo)
        }
        case None => Future.successful(Unauthorized)
      }
    }

    request.session.get("user-login") match {
      case Some(login) => noteForm.bindFromRequest().value match {
        case Some(noteInfo) => doSave(login, noteInfo)
        case None => Future.successful(NotAcceptable)
      }
      case None => Future.successful(Unauthorized)
    }
  }

  private case class NoteInfo(id: Int, header: String, content: String)

  private val noteForm = Form(
    mapping(
      "id" -> Forms.number,
      "header" -> Forms.text,
      "content" -> Forms.text
    )(NoteInfo.apply)(NoteInfo.unapply)
  )

}
