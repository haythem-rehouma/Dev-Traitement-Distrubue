import scala.io.Source

class Challenge {
  def challenge() {
    val s1 = Source.fromFile("demo.txt").mkString
    println(s1)
    
    val counts = s1.split("\\s+").groupBy(x=>x).mapValues(x=>x.length)
    println(counts)
    println("Count of words: " + counts)
  }
}