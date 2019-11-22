
//单例对象的伴生类，必须在同个文件中，可以互访问对方的私有成员
class ClassTest {

}

//class ClassTest的伴生对象,也叫单例对象，单例对象不接收参数
object ClassTest {
  def main(args: Array[String]): Unit = {
    println(
      """原生字符串
        |这里是换行
        |""".stripMargin) //| 加上 stripMargin组成


    //s插值器会对内嵌的每个表达式求值，对求值结果调用 toString,
    //替换掉字面量中的那些表达式
    val name = "reader"
    println(s"Hello, $name!")

    //把从美元符开始到首个非标识符字符的部分作为表达式 如果表达式包含了非标
    //识符字符，就必须将它放在花括号中，左花括号需要紧跟美元
    println(s"The answer is ${7 * 6}")

    println(raw"No\\\\escape ！")

    //f字符串插值器允许你给内嵌的表达式 printf 风格的指令 需要将
    //指令放在表达式之后，以百分号（%）开始，
    println(f"${math.Pi}%.5f")
  }
}
