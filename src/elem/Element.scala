package elem

import Element.elem

abstract class Element {
  def contents: Array[String]

  //推荐的做法是对于没有参数且只通过读取所在对
  //象字段的方式访问可变状态（确切地说不改变状态）的情况下尽量使用无参方法
  //这样的做法支持所谓的统一访问原则
  def height: Int = contents.length

  def width: Int = contents(0).length

  def demo() = {
    println("Element's implementation invoked")
  }

  def above(that: Element): Element = {
    val this1 = this widen that.width
    val that1 = that widen this.width
    elem(this1.contents ++ that1.contents)
  }

  def beside(that: Element): Element = {
    val this1 = this heighten that.height
    val that1 = that heighten this.height
    elem(
      for (
        //这个 zip 操作符从它的两个操作元中
        //选取对应的元素，组装成一个对偶(pair)的数组
        (line1, line2) <- this1.contents zip that1.contents
      ) yield line1 + line2
    )
  }

  def widen(w: Int): Element = {
    if (w <= width) this
    else {
      val left = elem(' ', (w - width) / 2, height)
      val right = elem(' ', w - width - left.width, height)
      left beside this beside right
    }
  }

  def heighten(h: Int): Element = {
    if (h <= height) this
    else {
      val top = elem(' ', width, (h - height) / 2)
      val bot = elem(' ', width, h - height - top.height)
      top above this above bot
    }
  }

  override def toString = contents mkString "\n"
}

object Element {
  def elem(contents: Array[String]): Element =
    new ArrayElement(contents)

  def elem(chr: Char, width: Int, height: Int): Element =
    new UniformElement(chr, width, height)

  def elem(line: String): LineElement =
    new LineElement(line)
}

class ArrayElement(conts: Array[String]) extends Element {
  override def contents: Array[String] = conts
}

class LineElement(s: String) extends Element {
  val contents = Array(s)

  override def height: Int = 1

  override def width: Int = s.length
}

//要调用超类的构造方法，只需将你打算传入的入参放在
//超类名称后的圆括号里即可
class LineElement2(s: String) extends ArrayElement(Array(s)) {
  override def height: Int = s.length

  override def width: Int = 1

  override def demo() = {
    println("LineElement's implementation invoked")
  }
}

class UniformElement(
                      ch: Char,
                      override val width: Int,
                      override val height: Int
                    ) extends Element {
  private val line = ch.toString * width

  def contents = Array.fill(height)(line)
}

class Cat {
  val dangerous = false
}

class Tiger(
             override val dangerous: Boolean,
             private var age: Int
           ) extends Cat

//Tiger定义是如下这个包含重写成员 dangerous 和私有成员 age 的类
//定义的简写方式

class Tiger2(param1: Boolean, param2: Int) extends Cat {
  override val dangerous: Boolean = param1
  private var age = param2
}

object Spiral {
  val space = elem(" ")
  val corner = elem("+")

  def spiral(nEdges: Int, direction: Int): Element = {
    if (nEdges == 1)
      elem("+")
    else {
      val sp = spiral(nEdges - 1, (direction + 3) % 4)

      def verticalBar = elem('|', 1, sp.height)

      def horizontalBar = elem('-', sp.width, 1)

      if (direction == 0)
        (corner beside horizontalBar) above (sp beside space)
      else if (direction == 1)
        (sp above space) beside (corner above verticalBar)
      else if (direction == 2)
        (space beside sp) above (horizontalBar beside corner)
      else
        (verticalBar above corner) beside (space above sp)
    }
  }

  def main(args: Array[String]): Unit = {
    val nSides = 17
    println(spiral(nSides, 0))

    //    val l = elem("x")
    //    val r = elem("1")
    //    val a = l beside elem(" + ")
    //    println(a)
    //    val a = l beside elem(" " + "+" + " ") beside r
    //    println(a)
  }
}