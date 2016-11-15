package homework.four

import org.scalatest.FunSuite
import homework.four.CurrencyConversions._
import homework.four.CurrencyDateConversions._

/**
  * @author Vitaliy.Bibaev
  */
class FixerCurrencyExchangerClient$Test extends FunSuite {
  test("query") {
    print(((100.usd to eur on 1 -- 1 -- 2011: NamedCurrency) to rub).countableCurrency.value)
  }
}
