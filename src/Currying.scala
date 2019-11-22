class Currying {

}

//柯里化

object Currying {
  //普通函数
  def plainOldSum(x: Int, y: Int) = x + y

  //经过柯里化
  //当你调用 curriedSum ，实际上是连着做了两次传统
  //的函数调用 第一次调用接收了一个名为 Int 参数，返回一个用于第二次
  //调用的函数值，这个函数接收一个 Int 参数 。
  def curriedSum(x: Int)(y: Int) = x + y

  def first(x: Int) = (y: Int) => x + y

  def main(args: Array[String]): Unit = {
    //下画线是第二个参数列表的占位符
    val onePlus = curriedSum(1) _
    println(onePlus)
    println(onePlus(3))
  }
}
