object Part3Class {

}

object main {
  def main(args: Array[String]){

    val cmdArgument = "--version"
    //--------//
    /*CHALLENGE*/
    //--------//

    /*
    * Let's take this case: In Linux, we might have two ways to display the help of a command:
    * --help or -h
    * We also have two ways to display the version of a command or a program:
    * --version or -v
    *
    * If no argument is allowed for a given command, the terminal shows the "USAGE" of it to help us find
    * the argument that we need to type.
    *
    * How will you do that using the pattern matching paradigm?
    *
    * Then...
    * ... print out loop from 1 to 10
    * ...then...from 1 to 10, print out every i divisible by two.
    * */

    //--------//
    /*SOLUTION*/
    //--------//

    def usageFunction(): String = {
      "You entered a wrong argument: Please use -v or --version to know the version of your Command, or -h or --help for help."
    }

    val argument = cmdArgument match {
      case "-v" | "--version" => "The version of your command is 2.3.4"
      case "-h" | "--help" => "Here, we can help you type the right command!"
      case _ => usageFunction()
    }

    println("Result: "+argument)




    ////////BONUS///////////
    /*
    * FOR LOOPS can be either expression or statements...
    *
    * */

    //This is a normal FOR LOOP, considered as a statement (doesn't return a value)

    for (i <- 1 to 10) {
      println(i)              //print la boucle de 1 a 10
    }  //Simply prints out the value of my iterator.

    //This one is an expression, simply because we are using the keyword "yield"
    //The value of "i" is returned each time we loop.
    println("---------")//Some space in the Screen


    //for(i <- 1 to 10; if (i%2==0)) yield i * 2
    //For each iterator, if i is divisible by 2, multiply it by 2...


    for(i <- 1 to 10) yield if(i%2==0) println(i)
    //print chaque i devisé par 2
  }
}
