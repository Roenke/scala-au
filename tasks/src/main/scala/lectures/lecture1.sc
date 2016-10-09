val a: Int = 10

def divideBy3Or5(n: Long): Long = {
  (1L until n).filter((value: Long) => value % 3 == 0 || value % 5 == 0).sum
}

divideBy3Or5(10)

def isPrime(n: Long): Boolean = {
  (2L until n ).forall((l: Long) => n % l != 0)
}
