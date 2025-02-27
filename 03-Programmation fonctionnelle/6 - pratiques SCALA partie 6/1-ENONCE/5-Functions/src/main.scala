

object main {
  def main(args:Array[String]) {
    var inc = (x:Int) => x + 1
    var x = inc(7)
    println("anonymous function called " + x)
    
    val mul = (x:Int, y:Int) => x * y
    println(mul(3,4))
    
    val fobj = new FunctionsClass()
    fobj.printTime()
    var timeCapture = fobj.time()
    println("----")
    println(timeCapture)
    println("this is only the function call")
    println(fobj.time())
    
    fobj.delayed(fobj.time())
    println("\n\n\n ")
    
    fobj.printStrings("Hello", "Scala", "Python")
    
    println("Retuned value: " + fobj.addInt(2, 3))
    
    fobj.printInt(b = 5, a = 7)
    
    println("Count is set as 10")
    var count = 10
    println("Function is called using an anonymous function")
    fobj.oncePerSecond(() => {println(count); println("count --"); count -=1; count > 0})
  }
}