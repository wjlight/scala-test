class case_class {

}

//样例类
abstract class Expr

case class Var(name: String) extends Expr

case class Number(num: Double) extends Expr

case class UnOp(opr: String, arg: Expr) extends Expr

case class BinOp(opr: String, left: Expr, right: Expr) extends Expr

object Cast {
  def main(args: Array[String]): Unit = {
    //1、它会添加一个跟类同名的工厂方法
    val v = Var("x")
    val op = BinOp("+", Number(1), v)

    //2、参数列表中的参数都隐式地获得了一个val前缀，因此它们会被当作字段处理
    println(v.name)
    println(op.left)

    //3、编译器会帮我们以“自然”的方式实现 toString hashCode和equals方法
    println(op)
    println(op.right == Var("x"))

    //4、编译器还会添加一个 copy 方法用于制作修改过的拷贝
    val newOp = op.copy(opr = "-")
    println(newOp)

    //可以用来检查该函数是否对某个特定的值有定义
    println(second.isDefinedAt(List(5, 6, 7)))
    println(second.isDefinedAt(List()))
  }

  //添加@unchecked，编译器对后续模式分支的覆盖完整性检查就会被压制,常用于，你真的认为你的case是完整了，然后想让编译器闭嘴
  def describe(e: Expr): String = (e: @unchecked) match {
    case Number(_) => "a number"
    case Var(_) => "a variable"
  }

  //偏函数
  val second: PartialFunction[List[Int], Int] = {
    case x :: y :: _ => y
  }
}

//密封类,作用：如果定义漏掉了某些可能的case的模式匹配，就会报错
sealed abstract class Expr2

case class Var2(name: String) extends Expr2 {
}