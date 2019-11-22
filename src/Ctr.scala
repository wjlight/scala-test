import java.io.File

class Ctr {

}


object Ctr {
  def main(args: Array[String]): Unit = {

    //if for try match有返回值
    val fileName =
      if (!args.isEmpty) args(0)
      else "default.txt"

    println(fileName)

    forTest()

    print(multiTable())
  }

  //赋值语句的返回结果永远是单元值 （）
  //赋值语句“line = readLine （） ”将永远返回 （）
  val fileNames = (new File(".")).listFiles()

  def forTest() = {
    for (file <- fileNames
         if file.getName.endsWith("txt") //过滤器
         if file.isFile)
      println(file)


    for (i <- 1 to 4)
      println(i)
    //不包括4
    for (i <- 1 until 4)
      println(i)
  }

  def fileLines(file: java.io.File) =
    scala.io.Source.fromFile(file).getLines().toList

  def grep(pattern: String) = {

    for {
      file <- fileNames
      if file.getName.endsWith(".scala")
      line <- fileLines(file)
      //中途（ mid-stream ）变量绑走
      trimmed = line.trim
      if trimmed.matches(pattern)
    } println(file + ": " + trimmed)
  }

  //for 子句 yield 代码体
  def yieldTest() = {
    //for表达式将Array[File］转换成Array[Int]
    val forLineLengths =
      for {
        file <- fileNames
        if file.getName.endsWith(".scala")
        line <- fileLines(file)
        trimed = line.trim
        if trimed.matches(".*for.*")
      } yield trimed.length //如果是file，就是Array[File]
  }

  //返回2
  def f(): Int = {
    try return 1 finally return 2
  }

  //返回1
  //最好避免在 finally 子句中返回值
  def g(): Int = {
    try return 1 finally 2
  }

  //这段代码将不断反复 从标准输入读取非空的文本行 而一旦用户输入空
  //行，控制流就会从外层的 breakable 码块退出， while 循环也随之退出
  def breakTest() = {

    import scala.util.control.Breaks._
    import java.io._

    val in = new BufferedReader(new InputStreamReader(System.in))

    breakable {
      while (true) {
        println("? ")
        if (in.readLine() == "") break
      }
    }

  }


  def makeRowSeq(row: Int) = {
    for (col <- 1 to 10) yield {
      val prod = (row * col).toString
      val padding = " " * (4 - prod.length)
      padding + prod
    }
  }

  //以字符串形式返回一行
  def makeRow(row: Int) = {
    makeRowSeq(row).mkString
  }

  def multiTable() = {
    val tableSeq =
      for (row <- 1 to 10)
        yield makeRow(row)
    tableSeq.mkString("\n")
  }
}
