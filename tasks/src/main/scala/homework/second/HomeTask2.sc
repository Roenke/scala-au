import IntArrayBuffer._
import homework.second.IntArrayBuffer

import scala.annotation.tailrec

//Реалзуйте IntArrayBuffer с интерфейсом IntTraversable
trait IntTraversable {
  def isEmpty: Boolean

  def size: Int

  def contains(element: Int): Boolean

  def head: Int

  def tail: IntTraversable

  def ++(traversable: IntTraversable): IntTraversable

  def filter(predicate: Int => Boolean): IntTraversable

  def map(function: Int => Int): IntTraversable

  def flatMap(function: Int => IntTraversable): IntTraversable

  def foreach(function: Int => Unit): Unit
}

class IntArrayBuffer(capacity: Int = 1) extends IntTraversable {
  private var currentSize: Int = 0
  private var myBuffer: Array[Int] = Array[Int](capacity)

  def this(other: IntArrayBuffer) = {
    this(other.capacity)
    currentSize = other.currentSize
    myBuffer = Array[Int](currentSize)
    def cp(ix: Int): Unit = {
      if (ix < currentSize) {
        myBuffer(ix) = other.myBuffer(ix)
        cp(ix + 1)
      }
    }

    cp(0)
  }

  def this(buffer: Array[Int]) = {
    this(buffer.length)
    currentSize = buffer.length
    myBuffer = buffer.clone()
  }

  def apply(index: Int): Int = {
    checkBounds(index)
    myBuffer(index)
  }

  def update(index: Int, element: Int): Unit = {
    checkBounds(index)
    myBuffer(index) = element
  }

  def clear(): Unit =
    currentSize = 0

  def +=(element: Int): IntArrayBuffer = ???

  def ++=(elements: IntTraversable): IntArrayBuffer = ???

  def remove(index: Int): Int = ???

  override def isEmpty: Boolean = currentSize == 0

  override def size: Int = currentSize

  override def contains(element: Int): Boolean = myBuffer.indexOf(element) < currentSize

  override def head: Int = apply(0)

  override def tail: IntArrayBuffer =
    if (isEmpty)
      throw new IllegalStateException("Buffer is empty")
    else
      new IntArrayBuffer(myBuffer.slice(1, currentSize))


  override def ++(traversable: IntTraversable): IntArrayBuffer = ???

  override def filter(predicate: (Int) => Boolean): IntTraversable = ???

  override def map(function: (Int) => Int): IntTraversable = ???

  override def flatMap(function: (Int) => IntTraversable): IntTraversable = {
    @tailrec
    def inner(acc: IntTraversable, ix: Int): IntTraversable = {
      if (ix < currentSize)
        acc
      else
        inner(acc ++ function(myBuffer(ix)), ix + 1)
    }

    inner(empty, 0)
  }

  override def foreach(function: (Int) => Unit): Unit =
    myBuffer.slice(0, currentSize).foreach(function)

  protected def ensureSize(size: Int): Unit = {
    if (myBuffer.length < size) {
      val newBuffer = Array[Int](size)
      Array.copy(myBuffer, 0, newBuffer, 0, currentSize)
    }
  }

  private def checkBounds(ix: Int) =
    if (0 <= ix && ix < currentSize)
      throw new ArrayIndexOutOfBoundsException(ix)
}

object IntArrayBuffer {
  def empty: IntArrayBuffer = ???

  def apply(elements: Int*): IntArrayBuffer = new IntArrayBuffer(elements.toArray)

  def unapplySeq(buffer: IntArrayBuffer): Option[IntArrayBuffer] = ???
}
