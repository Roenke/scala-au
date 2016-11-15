package homework

import java.util.Calendar

/**
  * @author Vitaliy.Bibaev
  */
package object conversions {

  sealed trait NamedCurrency {
    def getShortName: String
  }

  class MyDate(day: Int, month: Int, year: Int)

  class DayMonth(day: Int, month: Int) {
    def --(year: Int): MyDate = new MyDate(day, month, year)
  }

  implicit class IntExt3(i2: Int) {
    def --(i: Int): DayMonth = new DayMonth(i2, i)
  }

  class CountedCurrency(val count: BigDecimal, currency: NamedCurrency) {
    private val calendar = Calendar.getInstance

    def to(other: NamedCurrency): ConvertedCurrency = {

      new ConvertedCurrency(this, other, new MyDate(calendar.get(Calendar.DAY_OF_MONTH),
        calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)))
    }
  }

  class ConvertedCurrency(val from: CountedCurrency, val to: NamedCurrency, val date: MyDate) extends NamedCurrency {
    override def getShortName: String = to.getShortName

    def on(newDate: MyDate): ConvertedCurrency = {
      new ConvertedCurrency(from, to, newDate)
    }
  }

  implicit def toBigDecimal(currency: ConvertedCurrency): BigDecimal = {
    // TODO: use API
    0
  }

  implicit def toCurrency(value: BigDecimal): UnknownCurrency = {
    new UnknownCurrency()
  }

  class UnknownCurrency extends NamedCurrency {
    override def getShortName: String = ""
    implicit def toUnknownCurrency(value: BigDecimal): UnknownCurrency = new
  }

  object rub extends NamedCurrency {
    override def getShortName: String = "RUB"
  }

  object usd extends NamedCurrency {
    override def getShortName: String = "USD"

    def usd(implicit value: BigDecimal): CountedCurrency = {
      new CountedCurrency(value, conversions.usd)
    }
  }

  object app extends App {
    val a: BigDecimal = 1
    val b: BigDecimal = a.usd to rub on (21 -- 12 -- 2015)
    println(b)
  }
}
