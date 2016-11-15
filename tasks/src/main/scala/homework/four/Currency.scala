package homework.four

/**
  * @author Vitaliy.Bibaev
  */
sealed trait Currency {
  def shortName: String
}

object usd extends Currency {
  override def shortName: String = "USD"
}

object eur extends Currency {
  override def shortName: String = "EUR"
}

object rub extends Currency {
  override def shortName: String = "RUB"
}
