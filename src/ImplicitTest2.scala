import java.time.LocalDate

class ImplicitTest2 {
  def main(args: Array[String]): Unit = {

  }
}

/**
 * 因为隐式转换太过强大，隐式类只能是局部定义的类，不可以是全局类，否则编译器会报错。
 *
 * 在上述的隐式类示例中，编译器会为隐式类生成一个类似的隐式方法，从而可以实现隐式转换的功能。
 * implicit def DateHelperImplicitClass(offset: Int) = new DateHelperImplicitClass(offset)
 *
 * 基本与隐式函数的效果和使用方法一致，但是在实际每次执行的时候，会有创建一个隐式类的实例，然后调用方法，这样就是有短生命周期的对象的创建和销毁。
 * Scala 有一种值类（Value Class）的方法，可以消除这种额外的开销。唯一的改变就是继承 AnyVal。
 *
 * implicit class DateHelperImplicitClass(offset: Int) extends AnyVal {
 *
 * 在 Scala 隐式包装类都是作为值类实现的，例如 RichInt 等。
 */
object DataUtil {
  val ago = "ago"
  val from_now = "from_now"

  implicit class DateHelperImplicitClass(offset: Int) {
    def days(when: String): LocalDate = {
      val today = LocalDate.now
      when match {
        case "ago" => today.minusDays(offset)
        case "from_now" => today.plusDays(offset)
        case _ => today
      }
    }
  }

}

object DaysDSL2 extends App {

  import DateHelper._

  println(2 days ago)
  println(5 days from_now)
}
