import scala.language.implicitConversions

class ImplicitTest3 {

}

/**
 *
 * 基本规则如下：
 *
 * 标记规则：隐式转换中涉及到的变量、函数、类都是带有 implicit 关键词的，也就是说隐式转换必须有 implicit 定义才能生效。
 * 作用域规则：在隐式转换使用的位置必须是单标识符可见的，也就是可以无前缀引用，例如，可以是x，但不可以是 foo.x
 * 作用域规则延伸：作用域规则未找到的情况下，会在类型的隐式作用域（伴生对象中）内查找，隐式作用域是指与该类型相关联的全部伴生模块，此部分参见《深入理解 Scala》的 5.1.3。笔者对隐式作用域的理解，一般只需要关注伴生 object 即可，伴生 object 对应 Java 中类的 static 变量和函数。
 * 代码优先规则：隐式转换触发的时机是在编译器出现了查找方法失败的情况下才会被触发，因此如果代码可以正常执行的话，是不会触发隐式转换的。
 * 有且只有一次隐式转换规则：触发一次隐式转换，只能转换一次。例如 x + y 的 x 触发隐式转换，只会被隐式转换为 convert(x) + y，而不会进行两次隐式转换 convert2(convert1(x)) + y。
 *
 */

class RDD[T] {
  def map(): Unit = {
    println("map")
  }
}

/**
 * 通过在伴生对象 object 中定义了一个 implicit 方法，来对特定的(K, V)类型的 RDD 进行了隐式转换
 *
 * 这里的隐式作用查找范围与之前的示例是不同的，此处使用了类型的隐式作用域。
 * 隐式转换的查找是分为两个作用域的：
 * 首先，在使用的作用域内查找，这里没有 RDD 到 PairRDDFunctions 的隐式转换方法，
 * 然后会到类型定义的隐式作用域范围，指与该类型相关联的全部伴生模块，
 * 这里 RDD 有个 object 的模块，这里有 implicit 方法，定义了到 PairRDDFunctions 的转换，所以隐式转换成功，找到了 combineByKey 方法。
 */
object RDD {
  implicit def rddToPairRDDFunc[K, V](rdd: RDD[(K, V)]): PairRDDFunc[K, V] = {
    new PairRDDFunc(rdd)
  }
}

class PairRDDFunc[K, V](self: RDD[(K, V)]) {
  def combineByKey(): Unit = {
    println("combineByKey")
  }
}

object Demo {
  def main(args: Array[String]): Unit = {
    val rdd = new RDD[String]
    rdd.map()

    val pairRdd = new RDD[(String, String)]
    pairRdd.map()
    pairRdd.combineByKey()
  }
}