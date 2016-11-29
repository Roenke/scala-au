package five

object HLists {

  sealed trait Nat

  case object Zero extends Nat

  case class Succ[N <: Nat](prev: N) extends Nat

  type Zero = Zero.type

  val ZERO = Zero
  var ONE = Succ(ZERO)
  var TWO = Succ(ONE)
  val THREE = Succ(TWO)
  val FOUR = Succ(THREE)

  sealed trait HList

  case class HCons[+H, +T <: HList](head: H, tail: T) extends HList

  case object HNil extends HList {}

  type HNil = HNil.type

  trait Splittable[S <: HList, N <: Nat, L <: HList, R <: HList] {
    def apply(n: N, src: S): (L, R)
  }

  object Splittable {
    // apply (lst, 0) -> (nil, lst)
    implicit def zeroSplittable[R <: HList]: Splittable[R, Zero, HNil, R] = {
      new Splittable[R, Zero, HNil, R] {
        override def apply(n: Zero, src: R): (HNil, R) = (HNil, src)
      }
    }

    // apply (lst, n + 1) -> (lst.head : apply(lst.tail, n).first, apply(lst.tail, n).second)
    implicit def splittable[H, S <: HList, N <: Nat, L <: HList, R <: HList]
    (implicit splittable: Splittable[S, N, L, R]): Splittable[HCons[H, S], Succ[N], HCons[H, L], R] = {
      new Splittable[HCons[H, S], Succ[N], HCons[H, L], R] {
        override def apply(n: Succ[N], src: HCons[H, S]): (HCons[H, L], R) = {
          val (left, right) = splittable(n.prev, src.tail)
          (HCons(src.head, left), right)
        }
      }
    }
  }

  def splitAt[S <: HList, N <: Nat, L <: HList, R <: HList](n: N, src: S)
                                                           (implicit splittable: Splittable[S, N, L, R]): (L, R) = {
    splittable(n, src)
  }
}
