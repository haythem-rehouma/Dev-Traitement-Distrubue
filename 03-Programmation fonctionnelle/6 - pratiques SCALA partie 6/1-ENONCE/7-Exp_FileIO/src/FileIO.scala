import java.io._
import scala.io.Source._
import scala.io.Source

class FileIO {
  def fileWriter(){
    val writer = new PrintWriter(new File("demo.txt"))
    writer.write("Hello Scala")
    writer.write("2nd line")
    writer.close()
  }
  
  def fileReader(){
    val fileName = "demo.txt"
    println(Source.fromFile(fileName).getLines().toList.length)
    for (line <- Source.fromFile(fileName).getLines()) {
      println(line)
    }
  }
}