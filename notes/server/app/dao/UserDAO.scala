package dao

import javax.inject.Inject

import models.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.db.NamedDatabase
import slick.driver.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.Future

class UserDAO @Inject()(@NamedDatabase("notes") protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  private val users = TableQuery[TableDef]

  def all(): Future[Seq[User]] = db.run(users.result)

  def newUser(name: String, login: String, password: String): Future[Unit] =
    insert(User(0, name, login, password))

  private def insert(user: User): Future[Unit] = db.run(users += user).map { _ => () }

  def getByLogin(login: String): Future[Option[User]] = {
    db.run(users.filter(user => user.login === login).result.headOption)
  }

  private class TableDef(tag: Tag) extends Table[User](tag, "User") {

    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name: Rep[String] = column[String]("name")

    def login: Rep[String] = column[String]("login")

    def password: Rep[String] = column[String]("passw")

    override def * : ProvenShape[User] = (id, name, login, password) <>
      (User.tupled, User.unapply)
  }

}
