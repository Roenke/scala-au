package models

import java.sql.Timestamp


case class Note(id: Int, ownerId: Int,
                creationDate: Timestamp, modificationDate: Timestamp,
                header: String, content: String, isDeleted: Boolean) {
}

object NoteUtil {
  def create(ownerId: Int, header: String, content: String): Note = {
    val now = new Timestamp(System.currentTimeMillis())
    Note(-1, ownerId, now, now, header, content, isDeleted = false)
  }

  def modify(note: Note, newHeader: String, newContent: String): Note =
    Note(note.id, note.ownerId, note.creationDate,
      new Timestamp(System.currentTimeMillis()), newHeader, newContent, note.isDeleted)

  def delete(note: Note): Note = Note(note.id, note.ownerId, note.creationDate,
    new Timestamp(System.currentTimeMillis()), note.header, note.content, isDeleted = true)
}