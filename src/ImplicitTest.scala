import java.time.LocalDate

object ImplicitTest {

  /**
   * 简单来理解 implicit 的作用，可以用一句话来概括：在编译器出现错误的时候，可以通过隐式转换进行一次补救。
   * 隐式转换，可以是参数、函数、类
   */


  /**
   * 上述代码中定义了一个 log 方法，其中参数列表分为了两个部分，一般在第二个参数列表中定义隐式参数。
   * 在使用的时候，只需要提前定义 implicit 参数并且类型匹配，就可以在后续的调用中省略第二个参数列表，实现了一个范围内的默认值。
   * 当然也可以显示传入一个值。相对来说，比参数默认值更加灵活。
   */
  implicit val name: String = "default" //定义隐式变量
  log("init")

  def log(msg: String)(implicit name: String): Unit = {
    println(s"[$name] $msg")
  }

  def process(): Unit = {
    implicit val name: String = "process"
    log("doing something")
  }

  def param(): Unit = {
    implicit val name: String = "main"
    log("start")
    process()
    log("end")("custom name")
  }


  /**
   * 编译器在出现方法调用未找到合适匹配的时候，当前作用域内如果有隐式函数，编译器会尝试进行转换后，再进行调用。
   * 相当于给了一个修复编译器调用错误的机会，同时也赋予了强大的扩展能力，
   *
   */


  def main(args: Array[String]): Unit = {
    param()
  }


}


class DateHelper(offset: Int) {
  def days(when: String): LocalDate = {
    val today = LocalDate.now()
    when match {
      case "ago" => today.minusDays(offset)
      case "from_now" => today.plusDays(offset)
      case _ => today
    }
  }
}

/**
 * 上述的代码通过将 Int 转换为 DateHelper 类，实现了对 Int 方法的扩展，提供了一个方便的辅助日期功能。
 * 实用的过程中，是需要 implicit 的函数被引入到当前使用的作用域内，所以会有一个 import 语句。否则，编译器是无法进行隐式转换的。
 */
object DateHelper {
  val ago = "ago"
  val from_now = "from_now"

  implicit def convertInt2DateHelper2sss(offset: Int): DateHelper = {
    new DateHelper(offset)
  }
}

object DaysDSL extends App {

  import DateHelper._

  println(2 days ago)
  println(4 days from_now)
}

