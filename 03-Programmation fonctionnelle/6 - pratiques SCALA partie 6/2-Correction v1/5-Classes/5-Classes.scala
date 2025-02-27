

object main {
  def main(args: Array[String]){
    
    //Statements
    // declaration de variable
    val myVariable = 10
    //Afficher hello world
    //println("Hello World!")

    
    //val lines = scala.io.Source.fromFile("file.txt").mkString
    //Lire un fichier
    //println(lines)
    
    //Expressions
    val myVar = 5
    
    val calculus = {val PI=3.14; PI*myVar}
    // Afficher le calcul de 3.14*5
    //println("Calculus = " + calculus)
    
    val number = 2
    //Afficher divisible by two si tel est le cas sinon afficher None
    //val divideByTwo = if(number % 2 == 0) {println("Divisible by two!")} else {None}
    //println(divideByTwo)
    val result = 3 * 2 * {if(number %2 == 0) {number * 2} else {0}}
    //Afficher équation si divisible par 2 sinon 0
    //println("result = " + result)
    
    //Match Expressions

    // La function match case peut remplacer une serie de if else instructions.
    val laptop = "PC"
    val laptopType = laptop match {
      case "Mac" => "hello apple!"
      case "PC" => "Hellow PC!"
      case nothingMatches => "Are you sure you're a laptop? :)"
    }
    println("laptop = " + laptopType)

    // si on est dans le cas ou la val productType est = pc et se trouve dans le cas du pc ou surface ou windowsphone alors hello microsoft et si elle est null par donc pas de match sorry........
    val productType = "PC"
    val productTypeMatch = productType match {
      case "Mac" | "iPod" | "iPad" => "Hello Apple!"
      case "PC" | "Surface" | "WindowsPhone" => "Hello Microsoft!"
      case nothingMatches => "Sorry, don't know what you're talking about..."
    }
    println("product type = " + productTypeMatch)
    
    //Pattern Guards
    //si ne figure pas dans les cas alors par defaut ca va etre Linux qui est definit comme  dans le cas d'un non match si ce n est ni pc ni mac
    // si non répertorié alors sorry........
    val productType2 = "khj,"
    val productTypeMatch2 = productType2 match {
      case "Mac" | "iPod" | "iPad" => "Hello Apple!"
      case "PC" | "Surface" | "WindowsPhone" => "Hello Microsoft!"
      case nothingMatches if nothingMatches == "Linux" => "Great Choice!"
      case nothingMatches => "Sorry, don't know what you're talking about..."
    }
    println("product type = " + productTypeMatch2)
    
    //Match-All
    //une autre facon decrire : instancier le cas et l appeler dans la meme function
    val matchAll = "pc"
    val matchEmAll = matchAll match {
      case "Mac" => "hello apple!"
      case "PC" => "Hellow PC!"
      case nothingMatches => s"What do you mean by $nothingMatches?"
    }
    println("Match All = " + matchEmAll)
    
    //wildcards
    // une cas alternatif case_ dans le cas ou pas de match
    val wildCard = "Blaah"
    val wildCardVal = wildCard match {
      case "Mac" => "hello apple!"
      case "PC" => "Hellow PC!"
      case _ => s"I don't think that your product is a PC or Apple, it's $wildCard"
    }
    println("Wildcard = " + wildCardVal)
    
    //Down casting - pattern variables
    // type double comme float ou decimal. chiffre a virgules
    val typedVariable:Any = 2.3
    val typeOfVariable = typedVariable match {
      case typedVariable:Int => "integer, I can do my calculations."
      case typedVariable:String => "string, cannot do calculations but I can use the value for text."
      case typedVariable:Double => "Double, let's go!"
      case _ => "Any"
    }
    println("The type of my variable is: " + typeOfVariable)
    
    //Closures
    //ajouter une variable a la function elle doit etre du meme type declaré
    val y = 5
    val addYVariable = (x:Int) => x + y
    println("Adding an outside variable to my function: " + addYVariable(1))
    var extras = 10
    val fullPrice = (price:Int) => price + extras
    println("Full price with extras = " + fullPrice(40))
    //redefinir extra a 20
    extras = 20
    println("Full price with extras = " + fullPrice(40))
    
    
  }
}