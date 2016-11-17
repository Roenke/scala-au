package homework.four

/**
  * @author Vitaliy.Bibaev
  */
trait CurrencyExchanger {
  def exchange(from: Currency, to: Currency, count: Double, date: CurrencyDate): Option[Double]
}
