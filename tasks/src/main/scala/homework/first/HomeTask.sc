import scala.io.{Codec, Source}

/*
TODO Прочитайте содержимое данного файла.
В случае неудачи верните сообщение соответствующего исключения.
 */
val filePath = "C:\\rojects\\scala-au\\tasks\\src\\main\\scala\\homework\\first\\HomeTask.sc"

def readThisWorksheet(): String = {
  try {
    Source.fromFile(filePath)(Codec.UTF8).mkString
  } catch {
    case e: Exception => e.getLocalizedMessage
  }
}

readThisWorksheet()
