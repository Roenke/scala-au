package homework.four

import com.typesafe.scalalogging.Logger
import play.api.libs.json._

import scala.io.Source

/**
  * @author Vitaliy.Bibaev
  */
object FixerCurrencyExchangerClient extends CurrencyExchanger {
  private val logger = Logger("FixerCurrencyExchangerClient")
  private val API_URL: String = "http://api.fixer.io/"

  override def exchange(from: Currency, to: Currency, count: Double, date: CurrencyDate): Option[Double] = {
    if (from.shortName == to.shortName) return Some(count)
    makeRequest(from.shortName, to.shortName, dateString(date)) match {
      case Left(x) => Some(count * x)
      case Right(error) =>
        logger.error(error)
        None
    }
  }

  private def makeRequest(from: String, to: String, date: String): Either[Double, String] = {
    def createErrorMessage(json: JsValue): String = s"Request failed. Answer: ${json.toString()}"

    val url = s"$API_URL$date?base=$from"
    logger.info(s"make request to $url")
    val json = Json.parse(Source.fromURL(url, "UTF-8").mkString)
    json match {
      case JsObject(fields) => fields.get("rates") match {
        case Some(JsObject(rates)) => rates.get(to) match {
          case Some(JsNumber(x)) => Left(x.toDouble)
          case None => Right(createErrorMessage(json))
        }
        case None => Right(createErrorMessage(json))
      }
      case _ => Right(createErrorMessage(json))
    }
  }

  private def dateString(date: CurrencyDate): String = {
    s"${
      date.year
    }-${
      "%02d".format(date.month)
    }-${
      "%02d".format(date.day)
    }"
  }
}
