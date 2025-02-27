package app

object Main {
  def main(args: Array[String]): Unit = println("hello world! Groupe 3")
  /*
    //language fundamentals
    var x = 10
    println(x)
    x = 11
    println(x)
    x = x + 1
    println(x)
    x += 1
    println(x)
    x -= 1
    println(x)

    val y = 10
    //y = 11

    val z:Double = 10
    println(z)
    val greeting:String = null
    val greeting1: Any = "hello"
    println(greeting)
    println(greeting1)

    val xmax, ymax = 100
    val a0=1; var b0=3; val c0= 2.4
    println(xmax, ymax)
    println(a0, b0, c0)
    val (a,b,c) = (5, "Hello", 3.14)
    println(a) //println(a,b,c)

    //String declarations
    val s1 = "String"
    println(s1)
    val s2 = "My " + s1
    println(s2)
    val s3 = s1 + " " + 100
    println(s3)
    val s4 = 100.toString()
    println(s4)
    val s5 =
      """first line
        second line"""
    println(s5)

    //Strings are java.lang.String
   println("".isEmpty)
   println("abc".getBytes)
   println("abc".charAt(2)) //zero index based
   println("abc".length())

   //Strings are Sea (scala list abstraction)
   println("Hello".intersect("world"))
   println("Hello" intersect "world")
   println("aabbbccc".distinct)
   println("abcde".head)
   println("abcde".size)
   println("abcde".reverse)
   println("aBcDe".filter(x => x.isLower))

   //Variable length arrays
   import scala.collection.mutable.ArrayBuffer
   val b2 = ArrayBuffer[Int]()
   b2 += 1
   println(b2)
   b2 += (1,2,3,5)
   println(b2)
   b2 ++= Array(8, 13, 21) //ArrayBuffer(1, 1, 2, 3, 5, 8, 13, 21)
   b2.trimEnd(5) //ArrayBuffer(1, 1, 2)
   println(b2)
   b2.insert(1,6) //index=2, ArrayBuffer(1, 1, 6, 2)
   println(b2)
  b2.insert(2,7,8,9)
  println(b2)
  b2.remove(2)
  println(b2)
  b2.remove(2,3)
  println(b2)
  val c2 = b2.toArray
  println("c2=",c2.toBuffer)

   //multi-dimensional arrays
   val matrix = Array.ofDim[Double](3,4) //3 rows, 4 cols
   println(matrix(2)(3))
   matrix(2)(3) = 42
   println(matrix(2)(3))

   //Java to Scala collection conversions
    import scala.collection.JavaConversions._
    val list = new java.util.ArrayList[String]()
    list.add("abc")
    println(list)
    println(list.toArray())
    list.toBuffer

    val map = new java.util.HashMap[String, Int]
    map.put("a", 10)
    println(map)
    println(map.toMap)

    //collections
    val l1 = List(1,2,3)
    println(l1)
    val l2 = List()
    println(l1 == Nil) //false
    println(l2 == Nil) //true
    val l3 = List(2,"a")
    println(l3)
    println(l3(1))
    val l4 = List(1,-2,3,2,-1,0,3)
    println(l4.sorted)
    println(l4.sorted.reverse)
    println(List("b", "a").sorted)
    //println(List(1, "a").sorted)
    println(l4.sortWith((x,y) => x+"" < y+""))

    //mutable list
    import scala.collection.mutable.ListBuffer
    val lb = ListBuffer.empty[String]
    lb += "a"
    println(lb)
    lb += ("c", "d", "e")
    println(lb)
    lb -= "d"
    println(lb)
    lb ++= List("f", "g")
    println(lb)

    println(List(1,1,2,2))
    println(Set(1,1,2,2))
    println(List(1,2,3,4) ++ List(3,4,5,6))
    println(Set(1,2,3,4) ++ Set(3,4,5,6))
    println(Set(1,2,3) == Set(3,1,2))
    println(List(1,2,3) == List(3,1,2))
    println(Set(1,2,3) + 2)
    println(Set(1,2,3,4,5).toList)
    println(Set(5,4,3,2,1).toList)

    //immutable Map
    val scores0 = Map("John" -> 75, "Julia" -> 60, "Kevin" -> 26) //Map[String,Int]
    println(scores0)
    val scores1 = Map(("John", 60), ("Julia", 60), ("Kevin", 26))
    println(scores1)
    println(scores0("Julia"))
    println(if (scores0.contains("Julia")) scores0("Julia") else 0)
    println(scores0.getOrElse("Julia", 0))
    println(scores0.get("Julia"))
    println(scores0.get("Jacob"))
    println(scores0.get("Julia").getOrElse(0))
    println(scores0.get("Jacob").getOrElse(0))

    //mutable maps
    import scala.collection.mutable.{Map => MMap}

    val scores = MMap("John" -> 75, "Julia" -> 60, "Kevin" -> 26)
    println(scores("Julia"))
  //println(scores("Jacob")) //no key, error thrown
  scores += ("Julia" -> 65, "Jacob" ->78)
  println(scores)
  scores -= "Jacob"
  println(scores)
  val scores3 = scores + ("Julia" -> 55, "Jacob" -> 77)
  println(scores3)
  val scores4 = scores - "Jacob"
  println(scores4)

  //element traversing
  println("scores: ", scores)
  for((k,v) <- scores) println((k,v))
  for((k,v) <- scores) println(k+" -> "+v)
  println(scores.keySet)
  for (v <- scores.values) println(v)
  for((k,v) <- scores) yield println((v,k))


    //streams are Lists with lazy access to elements
    val st = (1 to 100).toStream
    println(st)
    println(st.filter(_%10==0))
    println(st.filter(_%10==0).toList)
    */
  //Tuples

  val t = (1,3.14,"John")
  println(t)  // Affiche toutes les valeurs du Tuple t
  println(t._1) // Affiche le premier  élément (1)
  println(t._2) // Affiche le deuxiéme  élement(3.14)
  println(t._3) // Affiche le troisième élement John
  println("t._3: ", t._3)  // Affiche le string (t._3) et  le troisième élement(John)

  val (first, second, third) = t  // Affectation des valeurs au élements du Tuple
  //val (elm1, elm2, elm3) = t
   println("first: ", first, " second: ", second)
  //println("first: ", elm1, " second: ", elm2, " third: ", elm3)
  //val (first1, second1, _) = t // Affectation  des valeurs au 2 premiers éléments  du Tuple
  //println("first: ", first1, " second1: ", second1)

  //zipping
  // La méthode zip prend iterable ou conteneurs et renvoie un seul objet itérateur,
  //la boucle for affiche 2 fois '<' '>' et 10 fois  '-'
  // Les valeurs mappées donne le resultat <<---------->>
  val symbols = Array("<", "-", ">")
  val counts = Array(2,10,2)

  var pairs = symbols zip counts
  for ((s,n) <- pairs) print(s*n)
  println()


  //two Lists to Map transformation
  val keys = List(1,2,3)
  val values = List("a", "b", "c")
  //La méthode toMap() est utilisée pour renvoyer un Map composée de tous les éléments
  val newMap = (keys zip values).toMap
  println(newMap)
  println(newMap(1))
  //println(newMap(2))
  //println(newMap(3))


  //Lists
  //from abstraction collections to lists

  //Les fonctions Traversables, Iterable, Seq  renvoie le type( List(1, 2, 3))
  val t2 = Traversable(1,2,3)
  println(t2)
  val i = Iterable(1,2,3)
  println(i)
  val sq = Seq(1,2,3)
  println(sq)

  //operations with Lists
  val odds = List(1,3,5,7,9)
  val evens = List(2,4,6,8)
  val nums = odds ++ evens  // nums contient tout les valeurs des  deux listes respectivement
  println(nums) // Resultat  List(1, 3, 5, 7, 9, 2, 4, 6, 8)

  val digs = 0 :: nums // permet d'ajouter un élément 0 au début de la liste nums
  println(digs)  // Resultat List(0, 1, 3, 5, 7, 9, 2, 4, 6, 8)
  val lstr = "a" :: "b" :: "c" :: Nil // Déclaration des valeurs dans une liste
  println(lstr) //Resultat  List(a, b, c)

  //other useful examples
  val list = List(1,-2,3,2,-1,0,-3)
  println(list.head) // Renvoie le premier élément d'une liste (1)
  println(list.tail) // Rrenvoie une liste composée de tous les éléments sauf le premier.
  println(list.last) //Renvoie le dernier Élement
  println(list.take(4)) //Renvoie les 4 premiéres Élements de la liste
  println(list.takeRight(4)) //Renvoie les 4 dérniers Élements de la liste
  println("slice:", list.slice(3,6))// Renvoie une liste de l'index 3 jusqu'au 5
  println(list.sum) // calcule la somme des valeurs de la liste
  println(list.min) // Renvoie la valeur minimale (-3)
  println(list.max) // Renvoie la valeur maximale (3)
  println(list.contains(7)) // Renvoie true ou false si un élement existe
  println(list.indexOf(3)) // Renvoie l'index du l'élement 3
  println(list.mkString)  // Affiche tous les éléments de la liste dans une chaîne
  println(list.mkString("|")) //Ajout une chaîne de séparation |
  println(list.count( x => x*x>1)) // Compte tout les Élements  de la liste qui satisfait la condition x*x>1

  //Renvoyer une nouvelle pile composée d'éléments présents dans les deux listes
  println(List(1,2,3) intersect List(2,3,4))
  println(List(1,2,3) diff List(2,3,4)) // Renvoie les differents Éelemts
  println(List(1,2,3).permutations.toList) // liste de six chaînes, toutes les permutations possibles de "123"
  println(List(1,2,3).combinations(2).toList) // Eenvoie la combinaison de la liste(1,2,3)
  println(List(List(1,2), List(3,4)).flatten) //Convertir une liste de listes en une seule liste

  //Conditionals
  val x3 = 0
  val y2 = if (x3 > 0) 1 else -1 // Resultat du if (-1)
  println(y2)

  println(if (x3 > 0) "plus" else -1) // Utilisation du if dans un println
  println(if (x3 > 0) 1 else ()) //Retourne une liste vide ()
  println(if (x3 > 0) 1)  //Retourne une liste vide ()

  //Loops
  var sum = 0
  while (sum < 10) {sum += 1} // La boucle while  donne le resultat 10
  println(sum)


  //Utilisez les variables k et sum pour écrire une boucle while
  // qui calcule la somme des carrés des 9 premiers nombres
  sum = 0
  var k = 0
  while (k < 10) {
    sum += k*k
    k += 1
  }
  println(sum, k)

 // La boucle for calcule la somme de tout les élements  dans la list l, le resultat affiche 15
  var sum1 = 0
  val l = List(1,2,3,4,5)
  for (e <- l) sum1 += e
  println(sum1)

  println()

  sum1 = 0
  for(i <- 1 to 10) sum1 += i  // la valeur de sum1 est 55
  //println(sum1)

  sum1 = 0
  for(ch <- "Hello") sum1 += ch
  println(sum1)

  //Boucle imbriquée
  for (i <- 1 to 3; j <- 1 to 3) print((10 * i + j) + " ")
  println()
  //boucle imbriquée avec condition
  for (i <- 1 to 3; j <- 1 to 3 if (i != j)) print((10 * i + j) + " ")
  println()

  //Boucle qui force le compteur j avec la valeur 3
  for(i <- 1 to 3; from = 4 - 1; j <- from to 3) print((10 * i + j) + " ")
  println()

}