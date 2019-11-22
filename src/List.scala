class ListTest {

}

object ListTest {
  def main(args: Array[String]): Unit = {
    val fruit: List[String] = List("apples", "oranges", "pears")
    val diag: List[List[Int]] =
      List(
        List(1, 0, 0),
        List(0, 1, 0),
        List(0, 0, 1)
      )

    val empty: List[Nothing] = List()

    //Scala 的列表类型是协变（ covariant ）的。意思是对每一组类型 S和T，如果
    //S是T的子类型 那么 List[S] 就是 List[T] 的子类型 例如， List[String］是
    //List[Object] 的子类型。因为每个字符串列表也都可以被当作对象列表，这很自然

    //空列表的类型为 List[Nothing]
    //对于任何T而言， List[Nothing] 都是 List[T] 的子类型。因此既然空列表对象的类型为
    //List[Nothing] ，可以被当作是其他形如 List[T］类型的对象
    //所以，以下定义合法
    val xs: List[String] = List()

    // :: 读作“cons” 表示在列表前追加元素,右结合的
    val fr = "apples" :: ("oranges" :: ("pears" :: Nil))
    println(fr)

    println(isort(List(3, 5, 6, 1, 9, 4)))

    //右结合的
    println(List(1, 2) ::: List(3, 4, 5))

    println(List(1, 2).length)

    val abcde = List('a', 'b', 'c', 'd', 'e')
    println(abcde.last)

    println(abcde.init)

    println(abcde.reverse)

    println("take:" + (abcde take 2))

    println("drop:" + (abcde drop 2))

    println("splitAt:" + (abcde splitAt 2))

    println("apply:" + (abcde apply 2))

    println("indices:" + (abcde.indices))

    println("flatten:" + (List(List(1, 2), List(3, 4)).flatten))

    println("zip:" + (abcde.indices zip abcde))
    //如果两个列表的长度不同，那么任何没有配对上的元素将被丢弃
    val zipped = abcde zip List(1, 2, 3)
    println(zipped)

    println("zipWithIndex:" + (abcde.zipWithIndex))

    println("unzip:" + (zipped.unzip))

    println("toString:" + (abcde.toString()))

    println("mkString:" + (abcde mkString ","))

    val buf = new StringBuilder
    println("addString:" + (abcde.addString(buf, "(", ";", ")")))

    val arr = abcde.toArray
    println("toArray:" + (arr.toList))

    val arr2 = new Array[Int](10)
    println("copyToArray:" + (List(1, 2, 3) copyToArray(arr2, 3)))

    println("iterator:" + abcde.iterator.next())

    println("merge sort:" + msort((x: Int, y: Int) => x < y)(List(5, 7, 1, 3)))

    println("map:" + (List(1, 2, 3) map (_ + 1)))

    val words = List("the", "quick", "brown", "fox")
    println("map2:" + (words map (_.length)))

    //flatMap 操作符跟 map类似 ，不过它要求右 的操作元是 个返回元素列
    //表的函数 它将这个函数应用到 表的每个元素，然后将所有结果拼接起来返回
    //下面的例子展示了 map flatMap 的区别

    println("map3:" + (words map (_.toList)))
    println("flatMap:" + (words flatMap (_.toList)))

    var sum = 0
    List(1, 2, 3, 4, 5) foreach (sum += _)
    println("foreach:" + sum)

    println("filter:" + (List(1, 2, 3, 4, 5) filter (_ % 2 == 0)))


    println(words filter (_.length == 3))

    println("partition:" + (List(1, 2, 3, 4, 5) partition (_ % 2 == 0)))

    println("find:" + (List(1, 2, 3, 4, 5) find (_ % 2 == 0)))

    //xs takeWhile p，返回列表 XS 中连续满足p的最长前缀
    println("takeWhile:" + (List(1, 2, 3, -4, 5) takeWhile (_ > 0)))
    //将去除列表 XS 中连续满足P的最长前缀
    println("dropWhile:" + (words dropWhile (_ startsWith "t")))

    println("span:" + (List(1, 2, 3, -4, 5) span (_ > 0)))

    println(hasZeroRow(diag))

    println("sortWith:" + (List(1, -2, 0, 3, 4) sortWith (_ < _)))
  }

  //折叠:折叠的结果是以z为前缀，对列表的元素依次连续应用op
  def sum(xs: List[Int]): Int = (0 /: xs) (_ + _)

  //是否存在一行的元素全为0:
  def hasZeroRow(m: List[List[Int]]) =
    m exists (row => row forall (_ == 0))

  //插入排序
  def isort(xs: List[Int]): List[Int] = xs match {
    case List() => List()
    case x :: xs1 => insert(x, isort(xs1))
  }

  def insert(x: Int, xs: List[Int]): List[Int] = xs match {
    case List() => List(x)
    case y :: ys =>
      if (x <= y) x :: xs
      else y :: insert(x, ys)
  }

  //柯里化，很容易将函数定制为一种采用特定比较函数的特例
  def msort[T](less: (T, T) => Boolean)(xs: List[T]): List[T] = {
    def merge(xs: List[T], ys: List[T]): List[T] =
      (xs, ys) match {
        case (Nil, _) => ys
        case (_, Nil) => xs
        case (x :: xs1, y :: ys1) =>
          if (less(x, y)) x :: merge(xs1, ys)
          else y :: merge(xs, ys1)
      }

    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (ys, zs) = xs splitAt n
      merge(msort(less)(ys), msort(less)(zs))
    }
  }

  def append[T](xs: List[T], ys: List[T]): List[T] =
    xs match {
      case List() => ys
      case x :: xs1 => append(xs1, ys)
    }
}
