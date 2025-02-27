

object main {
  def main(args: Array[String]){
    
    //Statements
    val myVariable = 10
    println("Hello World!")
    
    //val lines = scala.io.Source.fromFile("file.txt").mkString
    //println(lines)
    
    //Expressions
    val myVar = 5
    
    val calculus = {val PI=3.14; PI*myVar}
    println("Calculus = " + calculus)
    
    val number = 2
    val divideByTwo = if(number % 2 == 0) {println("Divisible by two!")} else {None}
    println(divideByTwo)
    val result = 3 * 2 * {if(number %2 == 0) {number * 2} else {0}}
    println("result = " + result)
    
    //Match Expressions
    val laptop = "PC"
    val laptopType = laptop match {
      case "Mac" => "hello apple!"
      case "PC" => "Hellow PC!"
      case nothingMatches => "Are you sure you're a laptop? :)"
    }
    println("laptop = " + laptopType)

    val productType = "PC"
    val productTypeMatch = productType match {
      case "Mac" | "iPod" | "iPad" => "Hello Apple!"
      case "PC" | "Surface" | "WindowsPhone" => "Hello Microsoft!"
      case nothingMatches => "Sorry, don't know what you're talking about..."
    }
    println("product type = " + productTypeMatch)
    
    //Pattern Guards
    val productType2 = "Linux"
    val productTypeMatch2 = productType2 match {
      case "Mac" | "iPod" | "iPad" => "Hello Apple!"
      case "PC" | "Surface" | "WindowsPhone" => "Hello Microsoft!"
      case nothingMatches if nothingMatches == "Linux" => "Great Choice!"
      case nothingMatches => "Sorry, don't know what you're talking about..."
    }
    println("product type = " + productTypeMatch2)
    
    //Match-All
    val matchAll = "Blaah"
    val matchEmAll = matchAll match {
      case "Mac" => "hello apple!"
      case "PC" => "Hellow PC!"
      case nothingMatches => s"What do you mean by $nothingMatches?"
    }
    println("Match All = " + matchEmAll)
    
    //wildcards
    val wildCard = "Blaah"
    val wildCardVal = wildCard match {
      case "Mac" => "hello apple!"
      case "PC" => "Hellow PC!"
      case _ => s"I don't think that your product is a PC or Apple, it's $wildCard"
    }
    println("Wildcard = " + wildCardVal)
    
    //Down casting - pattern variables
    val typedVariable:Any = 2.3
    val typeOfVariable = typedVariable match {
      case typedVariable:Int => "integer, I can do my calculations."
      case typedVariable:String => "string, cannot do calculations but I can use the value for text."
      case typedVariable:Double => "Double, let's go!"
      case _ => "Any"
    }
    println("The type of my variable is: " + typeOfVariable)
    
    //Closures
    val y = 2
    val addYVariable = (x:Int) => x + y
    println("Adding an outside variable to my function: " + addYVariable(3))
    var extras = 10
    val fullPrice = (price:Int) => price + extras
    println("Full price with extras = " + fullPrice(40))
    extras = 20
    println("Full price with extras = " + fullPrice(40))
    
    
  }
}