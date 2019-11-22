import java.io.File

class FileMatcher {

}

object FileMatcher {

  private def fileHear = (new File(".")).listFiles

  private def filesMatching(matcher: String => Boolean) =
    for (file <- fileHear; if matcher(file.getName))
      yield file

  def filesEnding(query: String) =
    filesMatching(_.endsWith(query))

  def fileContaining(query: String) =
    filesMatching(_.contains(query))

  def fileRegex(query: String) =
    filesMatching(_.matches(query))


  private def filesMatching2(query: String, matcher: (String, String) => Boolean) =
    for (file <- fileHear; if matcher(file.getName, query))
      yield file


  def filesEnding2(query: String) =
    //(fileName: String, query: String) => fileName.endsWith(query)
    //由于 filesMatching 接收一个要求两个 String 参的函数，并不需
    //要显式地给出入参的类型，可以直接写 (fileName, query) => fileName.
    //endsWith(query) 因为这两个参数在函数体内分别只用到一次（第一个参
    //fileName 先被用到，然后是第二个参数 query ），可以用占位符语法来写：
    //_.endsWith （＿） 第一个下画线是第一个参数（即文件名）的占位符，而第二
    //个下画线是第 个参数（即查询字符串）的占位符
    filesMatching2(query, _.endsWith(_))

  def fileContaining2(query: String) =
    filesMatching2(query, _.contains(_))

  def fileRegex2(query: String) =
    filesMatching2(query, _.matches(_))
}
