package homework.four

import java.util.Calendar

class CurrencyDate(val day: Int, val month: Int, val year: Int)

object CurrencyDate {
  private val myCalendar = Calendar.getInstance()

  def today = new CurrencyDate(myCalendar.get(Calendar.DAY_OF_MONTH),
    myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.YEAR))
}

object CurrencyDateConversions {

  import scala.language.implicitConversions

  class DayMonth(val day: Int, val month: Int) {
    def --(year: Int): CurrencyDate = new CurrencyDate(day, month, year)
  }

  implicit class IntExt(day: Int) {
    def --(month: Int): DayMonth = new DayMonth(day, month)
  }

  implicit def dayMonth2FullDate(dayMonth: DayMonth): CurrencyDate = new CurrencyDate(dayMonth.day,
    dayMonth.month, Calendar.getInstance().get(Calendar.YEAR))
}
