import scala.collection.mutable.ArrayBuffer

class Trait {

}

trait Philosophical {
  def philosophical() = {
    println("I consume memory, therefore I am!")
  }
}

abstract class IntQueue {
  def get(): Int

  def put(x: Int)
}

class BasicIntQueue extends IntQueue {
  private val buf = new ArrayBuffer[Int]

  def get(): Int = buf.remove(0)

  def put(x: Int): Unit = {
    buf += x
  }
}

trait Doubling extends IntQueue {
  //abstract override修饰符组合只允许用在特质的成员上
  //含义是该特质必须混人某个拥有该方法具体定义的类中
  abstract override def put(x: Int): Unit = {
    //由于特质 中的
    //super 调用是动态绑定的，只要在给出了该方法具体定义的特质或类之后混人，
    //Doubling 特质里的 super 调用就可 正常工作
    super.put(2 * x)
  }
}

trait Incrementing extends IntQueue {
  abstract override def put(x: Int): Unit = {
    super.put(x + 1)
  }
}

trait Filtering extends IntQueue {
  abstract override def put(x: Int): Unit = {
    if (x >= 0) super.put(x)
  }
}

class MyQueue extends BasicIntQueue with Doubling

//以下是一个既过滤掉负数同时还对所有数字加一的队列
//越靠右出现的特质越先起作用, Filtering的put最先被调用
class MyQueue2 extends BasicIntQueue with Incrementing with Filtering

object MyTest {
  def main(args: Array[String]): Unit = {
    val queue = new MyQueue
    queue.put(10)
    println(queue.get())

    val queue2 = new MyQueue2
    queue2.put(-1)
    queue2.put(0)
    queue2.put(1)
    println(queue2.get())
    println(queue2.get())
  }
}


//线性化
//在任何线性化中，类总是位于所有它的超类和混人的特质之前。
//因此，当你写下调用super的方法时，那个方法绝对是在修改超类和
//混人特质的行为，而不是反过来

//当你用new实例化一个类的时候，
//Scala 会将类及它所有继承的类和特质都拿出来，将它们线性地排列在一起。
//然后，当你在某一个类中调用super时，被调用的方法是这个链条中向上最近
//的那一个。如果除了最后一个方法外，所有的方法都调用了 super ，那么最终
//的结果就是叠加在 起的行为

class Animal

trait Furry extends Animal

trait HasLegs extends Animal

trait FourLegged extends HasLegs

class Cat extends Animal with Furry with FourLegged

//cat 线性化的最后一个部分是其超类 Animal 的线性化
//Animal => AnyRef => Any

//线性化的倒数第二个部分是首个混人（即 Furry 特质 ）的线性化，不过所有已经出现在 Animal 线性化中的类都不再重复出现，每个类在 Cat 线性化
//当中只出现一次 结果是：
//Furry => Animal => AnyRef => Any

//在这个结果之前，是 FourLegged 的线性化 ，同样地，任何已经在超类或首个混人中拷贝过的类都不再重复出现
//FourLegged => HasLegs => Furry => Animal => AnyRef => Any

//最后， Cat 线性化中的第一个类是 Cat 自己
//Cat => FourLegged => HasLegs => Furry => Animal => AnyRef => Any

