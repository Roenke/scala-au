package homework.four


/**
  * @author Vitaliy.Bibaev
  */
object CurrencyConversions {

  import scala.language.implicitConversions

  final class ConvertedCurrency(val from: NamedCurrency, val to: Currency, val currencyDate: CurrencyDate = CurrencyDate.today) {
    def on(currencyDate: CurrencyDate): ConvertedCurrency = new ConvertedCurrency(from, to, currencyDate)

  }

  implicit def toNamedCurrency(convertedCurrency: ConvertedCurrency): NamedCurrency = {
    val convertedValue = FixerCurrencyExchangerClient.exchange(convertedCurrency.from.currency,
      convertedCurrency.to, convertedCurrency.from.countableCurrency.value, convertedCurrency.currencyDate)
    convertedValue match {
      case Some(x) => new NamedCurrency(new CountableCurrency(x), convertedCurrency.to)
      case None => throw new Exception
    }
  }

  implicit def toDouble(namedCurrency: NamedCurrency): Double = {
    namedCurrency.countableCurrency.value
  }

  final class NamedCurrency(val countableCurrency: CountableCurrency,
                      val currency: Currency) {
    def to(to: Currency): ConvertedCurrency = {
      new ConvertedCurrency(this, to)
    }
  }

  final implicit class CountableCurrency(val value: Double) {
    def rub: NamedCurrency = {
      new NamedCurrency(this, homework.four.rub)
    }

    def eur: NamedCurrency = {
      new NamedCurrency(this, homework.four.eur)
    }

    def usd: NamedCurrency = {
      new NamedCurrency(this, homework.four.usd)
    }
  }

}
