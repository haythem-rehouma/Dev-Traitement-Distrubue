

object main {
  def main(args: Array[String]){
    val object1 = new FileIO()
    object1.fileWriter()
    object1.fileReader()
    
    val exObject = new Exceptions()
    exObject.demoException()
    
    val ch = new Challenge()
    ch.challenge()
  }
}