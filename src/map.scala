object Test {

  def main(args: Array[String]): Unit = {
    var a: Map[String, Int] = Map("k5" -> 1, "k3" -> 2)
    a += ("k4" -> 4)
    for ((k, v) <- a) {
      println(k, v)
    }

    a.foreach {
      case (e, i) => println(e, i)
    }

    a.keys.foreach(println)
    a.values.foreach(println)

    //自定义按英文或者数字排序
    implicit val KeyOrdering = new Ordering[String] {
      override def compare(x: String, y: String): Int = {
        -x.compareTo(y)
      }
    }

    println(a.toSeq.sorted)

    var b: scala.collection.mutable.Map[String, Int] = scala.collection.mutable.Map("k1" -> 1, "k2" -> 2)
    b("k1") = 5
  }


}
