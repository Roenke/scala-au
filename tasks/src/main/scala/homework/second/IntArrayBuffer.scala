package homework.second


class IntArrayBuffer(capacity: Int = 10) extends IntTraversable {
  private var myData = new Array[Int](capacity)
  private var mySize = 0

  def this(other: IntArrayBuffer) = {
    this(other.myData.length)
    this ++= other
  }

  private def this(data: Array[Int]) = {
    this(data.length)
    for (i <- data.indices) {
      this += data(i)
    }
  }

  def apply(index: Int): Int = {
    checkBounds(index)
    myData(index)
  }

  def update(index: Int, element: Int): Unit = {
    checkBounds(index)
    myData(index) = element
  }

  def clear(): Unit = mySize = 0

  def +=(element: Int): IntArrayBuffer = {
    if (mySize >= myData.length) {
      ensureSize(myData.length * 2)
    }

    myData(mySize) = element
    mySize += 1
    this
  }

  def ++=(elements: IntTraversable): IntArrayBuffer = {
    elements foreach { x => this += x }
    this
  }

  def remove(index: Int): Int = {
    checkBounds(index)
    val value = myData(index)
    mySize -= 1
    for (i <- index until mySize) {
      myData(i) = myData(i + 1)
    }

    value
  }

  override def isEmpty: Boolean = mySize == 0

  override def size: Int = mySize

  override def contains(element: Int): Boolean = {
    for(i <- 0 until mySize) {
      if(myData(i) == element) {
        return true
      }
    }

    false
  }

  override def head: Int = {
    checkBounds(0)
    myData(0)
  }

  override def tail: IntArrayBuffer = {
    checkBounds(0)
    new IntArrayBuffer(myData.slice(1, mySize))
  }

  override def ++(traversable: IntTraversable): IntArrayBuffer = new IntArrayBuffer(this) ++= traversable

  override def filter(predicate: (Int) => Boolean): IntArrayBuffer = {
    val result = new IntArrayBuffer()
    foreach { x => if (predicate(x)) result += x }

    result
  }

  override def map(function: (Int) => Int): IntArrayBuffer = {
    val result = new IntArrayBuffer()
    foreach { x => result += function(x) }

    result
  }

  override def flatMap(function: (Int) => IntTraversable): IntArrayBuffer = {
    val result = new IntArrayBuffer()
    foreach { x => result ++= function(x) }
    result
  }

  override def foreach(function: (Int) => Unit): Unit = {
    for (i <- 0 until mySize) {
      function(myData(i))
    }
  }

  protected def ensureSize(size: Int): Unit = {
    if (myData.length < size) {
      val newBuffer = Array[Int](size)
      Array.copy(myData, 0, newBuffer, 0, mySize)
      myData = newBuffer
    }
  }

  private def checkBounds(ix: Int) =
    if (!(0 <= ix && ix < mySize))
      throw new ArrayIndexOutOfBoundsException(ix)
}

object IntArrayBuffer {
  private val EMPTY: IntArrayBuffer = new IntArrayBuffer(0)

  def empty: IntArrayBuffer = EMPTY

  def apply(elements: Int*): IntArrayBuffer = new IntArrayBuffer(elements.toArray)

  def unapplySeq(buffer: IntArrayBuffer): Option[IntArrayBuffer] = Option.apply(buffer)
}
