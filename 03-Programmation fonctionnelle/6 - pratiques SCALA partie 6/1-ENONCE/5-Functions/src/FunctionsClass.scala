

class FunctionsClass {
  def printTime() {
    println("Inside basic function printTime")
    println("Getting time in nano seconds")
    println("Time in nano seconds is " + System.nanoTime)
  }
  
  def time() = {
    println("Inside basic function time")
    println("Getting time in nano seconds")
    System.nanoTime
  }
  
  def delayed(t: => Long) = {
    println("Entering into delayed method")
    println("Param: " + t)
    println("Exited from function")
  }
  
  def printStrings(args: String*) = {
    println("Entering into function print string")
    var i: Int = 0
    println("Interating in foreach loop")
    for (arg <- args) {
      println("arg value[" + i + "] = " + arg)
      i = i+1
    }
  }
  
  def addInt(a:Int, b:Int): Int = {
    var sum:Int = 0
    println("Entered into the function addInt")
    sum = a + b
    return sum
  }
  
  def printInt(a:Int, b:Int) = {
    println("Value of a: " + a)
    println("Value of b: " + b)
    println("Function exited")
  }
  
  def oncePerSecond(callback: () => Boolean) {
    while(callback()) {
      Thread sleep 1000
      println("Function sleep for 1s...")
    }
  }
}