import java.io.{File, PrintWriter}
import java.net.URI

class ChouXiang {

}

object ChouXiang {

  def withPrintWriter(file: File, op: PrintWriter => Unit) = {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  def main(args: Array[String]): Unit = {
    withPrintWriter(
      new File("data.txt"),
      writer2 => writer2.println(new java.util.Date)
    )

    //这样，可以使用花括号了
    withPrintWriter2(new File("data.txt")) {
      writers => writers.println(new java.util.Date())
    }

    byNameAssert(5 > 3)
  }

  def withPrintWriter2(file: File)(op: PrintWriter => Unit) = {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  //传名参数
  //在 byNameAssert(5 > 3）的圆括号中的表达式在调用
  //byNameAssert 之前并不会被求值，而是会有一个函数值被创建出来，这个函
  //数值的 apply 方法将会对5 > 3求值，传人 byNameAssert 的是这个函数值
  def byNameAssert(pre: => Boolean) = {
    if (!pre)
      throw new AssertionError()
  }

}
