package five

import five.HLists._
import org.scalatest._

/**
  * @author Vitaliy.Bibaev
  */
class HLists$Test extends FlatSpec with Matchers {
  val lst = HCons(1, HCons(true, HCons(2.3, HCons("string", HNil))))

  "Split by zero " should " return first nil" in {
    splitAt(ZERO, lst)._1 should be (HNil)
  }

  "Split by zero " should "return list when split by zero" in {
    splitAt(ZERO, lst)._2.head should be (1)
  }

  "Split by one " should " take element with index 0" in {
    splitAt(ONE, lst)._2.head should be (true)
  }

  "Split by two " should " take element with index 1" in {
    splitAt(TWO, lst)._2.head should be (2.3)
  }

  "Split by three " should " take element with index 2" in {
    splitAt(THREE, lst)._2.head should be ("string")
  }
}
