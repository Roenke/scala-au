package homework.second

import org.scalatest.FunSuite

class IntArrayBufferTest extends FunSuite {

  test("testDefaultConstructor") {
    val buffer = new IntArrayBuffer(1)
    assert(buffer.size == 0)
  }

  test("testFlatMap") {
    val buffer = IntArrayBuffer(10, 20, 30)
    assert(buffer.size == 3)
    assert(buffer.flatMap(x => new IntArrayBuffer()).size == 0)
    val repeat = buffer.flatMap(x => buffer)
    assert(buffer.flatMap(x => buffer).size == 9)
    assert(repeat(0) == repeat(3) && repeat(3) == repeat(6) && repeat(0) == 10)
  }

  test("testFilter") {
    val buffer = IntArrayBuffer(1, 2, 3)
    assert(buffer.filter(x => x % 2 == 0).size == 1)
    assert(buffer.filter(x => x % 2 != 0).size == 2)
  }

  test("testUpdate") {
    val buffer = IntArrayBuffer(1)
    buffer.update(0, 2)
    assert(buffer(0) == 2)
  }

  test("testConcat") {
    val b1 = IntArrayBuffer(1, 2)
    val b2 = IntArrayBuffer(3, 4)
    val concat = b1 ++ b2
    assert(b1.size == 2)
    assert(b2.size == 2)
    assert(concat.size == 4)
    assert(concat(0) == b1(0))
    assert(concat(2) == b2(0))
  }

  test("testClear") {
    val buffer = IntArrayBuffer(2)
    assert(buffer.size == 1)
    buffer.clear()
    assert(buffer.size == 0)
  }

  test("testSize") {
    val buffer = new IntArrayBuffer()
    assert(buffer.size == 0)
    buffer += 1
    assert(buffer.size == 1)
  }

  test("testRemove") {
    val buffer = IntArrayBuffer(1, 2, 3)

    assertThrows[IndexOutOfBoundsException] {
      buffer.remove(4)
    }

    assert(buffer.size == 3)
    assert(buffer.remove(2) == 3)
    assert(buffer.size == 2)
  }

  test("testTail") {
    val buffer = new IntArrayBuffer()
    assertThrows[IndexOutOfBoundsException] {
      buffer.tail
    }
    buffer ++= IntArrayBuffer(1, 2, 3)
    assert(buffer.tail.size == 2)
    assert(buffer.tail(0) == 2)
  }

  test("testAdd") {
    val buffer = IntArrayBuffer(1)
    assert(buffer(0) == 1)
  }

  test("testApply") {
    val buffer = IntArrayBuffer(1)
    assert(buffer(0) == 1)
    assertThrows[IndexOutOfBoundsException] {
      buffer(100)
    }
  }

  test("testContains") {
    val buffer = IntArrayBuffer(1, 2, 3)

    assert(buffer.contains(1))
    assert(buffer.contains(2))
    assert(buffer.contains(3))
    assert(!buffer.contains(0))
  }

  test("testIsEmpty") {
    val buffer = new IntArrayBuffer()
    assert(buffer.isEmpty)
    buffer += 1
    assert(!buffer.isEmpty)
  }

  test("testSelfConcat") {
    val buffer = IntArrayBuffer(1)
    val b2 = IntArrayBuffer(2)
    buffer ++= b2
    assert(buffer.size == 2)
    assert(b2.size == 1)
    assert(buffer(1) == b2(0))
  }

  test("testForeach") {
    val buffer = IntArrayBuffer(1, 2, 3)
    var acc = 0
    buffer foreach { x => acc += x }
    assert(acc == 6)
  }

  test("testHead") {
    val buffer = new IntArrayBuffer()
    assertThrows[IndexOutOfBoundsException] {
      buffer.head
    }
    buffer += 1
    assert(buffer.head == 1)
  }

  test("testMap") {
    val buffer = IntArrayBuffer(1, 2, 3)
    val sqrBuffer = buffer.map { x => x * x }
    assert(sqrBuffer.size == buffer.size)
    for (i <- 0 until sqrBuffer.size) {
      assert(sqrBuffer(i) == buffer(i) * buffer(i))
    }
  }

  test("testEmpty") {
    assert(IntArrayBuffer.empty.size == 0)
  }

  test("create") {
    val buf = IntArrayBuffer(1, 2, 3)
    assert(buf.size == 3)
  }

  test("testUnapply") {
    val buffer = new IntArrayBuffer()
    buffer += 1
    buffer match {
      case IntArrayBuffer(b) => b
    }
  }

  test("testUnapplySeq") {
    val buffer = new IntArrayBuffer()
    buffer += 1
    val tail = buffer match {
      case IntArrayBuffer(x, xs) => xs
    }
  }
}
