class Method {

}

object Method {
  def main(args: Array[String]): Unit = {
    var increase = (x: Int) => {
      println("12")
      x + 1
    }

    println(increase(1))
    list()
    test()
  }

  def list() = {
    val list = List(-11, -1, 2, 0, 4)
    //filter 方法将首先把 一＞ 0
    //中的空替换成－11 ，即－11 > ，然后替换成－10 ，即 10 > 然后替换成
    //-5 ，即 > ，以此类推，直到 List 末尾
    println(list.filter(_ > 0))

    //下画线替换掉单独的参数， 也可以用下画线替换整个参数列表
    list.foreach(println(_)) // x => println(x)

    echo("one", "two")

    val array = Array("what", "up", "dock?")
    //将 arr 每个元素作为参数传给 cho
    echo(array: _*)
  }

  def sum(a: Int, b: Int, c: Int) = a + b + c

  def test() = {
    //部分应用的函数
    //当你调用某个函数，传入任何需要的参数时，你实际上是应用那个函数到这些参数上
    val s = sum _
    println("sum:" + s(1, 2, 3))

    val b = sum(1, _: Int, 3)
    println("sum2:" + b(2))
  }

  //参数的类型后面加上一个星号（*），可变长参数列表
  def echo(args: String*) = {
    for (arg <- args) println(arg)
  }
}
