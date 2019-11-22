
/**
 * 创建1个主构造方法 primary constructor ，接收同样的这两个参数
 *
 * @param n
 * @param d
 */
class Rational(n: Int, d: Int) {
  //require 方法接收一个 boolean 的参数 如果传人的参数为 true
  //require 将会正常返回 require 将会抛出 IllegalArgumentException
  //来阻止对象的构建
  require(d != 0)

  private val g = gcd(n.abs, d.abs)

  val number: Int = n / g
  val denom: Int = d / g

  override def toString: String = number + "/" + denom

  //辅助构造方法
  def this(n: Int) = this(n, 1)

  def add(that: Rational): Rational = {
    new Rational(
      number * that.denom + that.number * denom,
      denom * that.denom
    )
  }

  def +(that: Rational): Rational = {
    this.add(that)
  }

  def +(i: Int): Rational = {
    new Rational(number + i * denom, denom)
  }

  def *(that: Rational): Rational = {
    new Rational(
      number * that.number, denom * that.denom
    )
  }

  def *(i: Int): Rational = {
    new Rational(number * i, denom)
  }

  def lessThan(that: Rational) = {
    this.number * that.denom < that.number * that.denom
  }

  def max(that: Rational) = {
    if (this.lessThan(that)) that else this
  }

  def gcd(a: Int, b: Int): Int = {
    if (b == 0) a else gcd(b, a % b)
  }


}

object Rational {

  implicit def intToRational(x: Int) = new Rational(x)

  def main(args: Array[String]): Unit = {
    val n: Rational = new Rational(1, 4)
    println(n)
    println(n.add(new Rational(1, 4)))

    println(n * 2)
    println(2 * n)
  }
}

