import scala.io.Source

object FileTest {
  def main(args: Array[String]): Unit = {
    val fileUrl = "e://work//java/scala-test/src/Array.scala"

    def widthOfLength(s: String) = s.length.toString.length

    val lines = Source.fromFile(fileUrl).getLines().toList

    val longestLine = lines.reduceLeft(
      (a, b) => if (a.length > b.length) a else b
    )

    val maxWidth = widthOfLength(longestLine)

    for (line <- lines) {
      val numSpaces = maxWidth - widthOfLength(line)
      val padding = " " * numSpaces
      println(padding + line.length + " | " + line)
    }
  }
}
