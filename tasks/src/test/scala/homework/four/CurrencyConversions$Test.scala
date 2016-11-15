package homework.four

import org.scalatest.FunSuite
import homework.four.CurrencyConversions._
/**
  * @author Vitaliy.Bibaev
  */
class CurrencyConversions$Test extends FunSuite {
  test("abc") {
    val b = 1.23.usd to eur
    assert(b.countableCurrency.value == 1.23)
  }
}
