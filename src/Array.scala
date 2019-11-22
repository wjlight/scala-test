object ArrayTest {

  def main(args: Array[String]): Unit = {

    //之所以需妥在末尾放一个 Nil ，是因为：：是 List 类上定义的方法 如果只是写成 :2: :3
    //译是不会通过的，因为 是个 Int ，而 Int 并没有：：方法
    val a = "wa" :: "waw" :: "tt" :: Nil

    println(a)

    var list = List("a", "b") ::: List("c", "d")
    println(list)

    tuple()
    map()
  }

  /**
   * 列表的 apply 方法永远只返回同
   * 种类型，但元组里的元素可以是不同类型的：＿1 可能是一种类型，
   * _2 可能是另一种，等等 这些＿N 表示的字段名是从1开始而不是0
   * 开始的，
   */
  def tuple(): Unit = {
    val pair = (99, "waaw")
    println(pair._1)
  }

  /**
   * Sca la 编译器会将二元（ binary）的操
   * 作，比如 1 - >’'Go to island. ，转换成标准的方法调用， 即（ 1）.－＞（” Go
   * to island. ”） ，当你写 1 －＞” Go to island ．” 实际上是对这个值
   * 的整数调用 -> 方法，传人字符串 Go to island ．” Scala 的任
   * 何对象上调用这个-> 方法，它将返回包含键和值两个元素的元组 然后将这
   * 个元组传给 trMap 指向的那个映射对象的 ＋＝ 方法
   */
  def map(): Unit = {
    import scala.collection.mutable
    val trMap = mutable.Map[Int, String]()
    trMap += (1 -> "Go to island")
    println(trMap(1))
  }
}
