package dao

import java.sql.Timestamp
import javax.inject.Inject

import models.Note
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.db.NamedDatabase
import slick.driver.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.Future

class NoteDAO @Inject()(@NamedDatabase("notes") protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  private val notes = TableQuery[TableDef]

  def saveNote(note: Note): Future[Unit] = {
    db.run(notes.insertOrUpdate(note)).map(_ => ())
  }

  def getById(noteId: Int): Future[Option[Note]] = {
    db.run(notes.filter(note => note.id === noteId).result.headOption)
  }

  def getByUser(userId: Int): Future[Seq[Note]] = db.run(notes
    .filter(note => note.user === userId && !note.isDeleted).sortBy(_.modificationDate.desc).result)

  def deleteById(id: Int): Future[Int] = {
    db.run(notes.filter(note => note.id === id).delete)
  }

  private class TableDef(tag: Tag) extends Table[Note](tag, "Note") {

    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def user: Rep[Int] = column[Int]("user")

    def creationDate: Rep[Timestamp] = column[Timestamp]("creation_date")

    def modificationDate: Rep[Timestamp] = column[Timestamp]("modification_date")

    def header: Rep[String] = column[String]("header")

    def text: Rep[String] = column[String]("text")

    def isDeleted: Rep[Boolean] = column[Boolean]("isDeleted")

    override def * : ProvenShape[Note] =
      (id, user, creationDate, modificationDate, header, text, isDeleted) <>
        (Note.tupled, Note.unapply)
  }

}
