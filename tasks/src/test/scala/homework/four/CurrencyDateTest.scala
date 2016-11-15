package homework.four

import org.scalatest.FunSuite
import homework.four.CurrencyDateConversions._

/**
  * @author Vitaliy.Bibaev
  */
class CurrencyDateTest extends FunSuite {
  test("An empty Set should have size 0") {
    val date: CurrencyDate = 1 -- 12 -- 2017
    assert(date.day == 1)
    assert(date.month == 12)
    assert(date.year == 2017)
  }
}
