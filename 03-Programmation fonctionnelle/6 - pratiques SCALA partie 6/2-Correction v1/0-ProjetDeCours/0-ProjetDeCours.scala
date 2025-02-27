object HelloWorldBigDataStreaming {
  def main(args: Array[String]): Unit = {

    //TestHello("Groupe3");
    //TestHelloVal ();
    //TestHelloWhile ();
    //TestHelloFOR();
    //TestHelloLISTES();
    //TestHelloTuples ();
    //TestHelloMaps();
    //TestHelloTableaux();
      TestHelloReferences();
  }


  //La méthode TestHello permet d'afficher le message "voici votre message groupe3
  def TestHello (texte : String) : Unit = {
    println("voici votre message " + texte)
  }
  //La méthode TestHelloVal calcule la somme de test  et test2 (resultat:17)
  def TestHelloVal () : Unit = {
    val test : Int = 15;
    val test2 = test + 2;
    println(test2);
  }

 // La méthode TestHelloWhile utlise la boucle while pour affiché les nombres 0 1 2 3 4 5 6
  //on as tester ce code  en utilisant juste print le resultat est le même mais sur la meme ligne
  def TestHelloWhile () : Unit = {
    var i = 0;
    while (i < 7) {
      println(i)
      i = i + 1;
    }
  }

  // La méthode TestHelloFOR Utilise la boucle for pour affiché les nombre entre 0 et 10(les deux inclut)
  def TestHelloFOR () : Unit = {
    var i = 0
    for (i <-  0 to 10) {
      println(i)
    }
  }

  def TestHelloLISTES () : Unit = {
    //LES STUCTURES DE DONNEES SCALA
    //Scala fournit une bibliothèque riche pour la collecte de données. Ces collections de données peuvent être mutables ou immutables. Voici les librairies qui contiennent toutes les collections de Scala :
    //  - Scala.collection.mutable
    //- Scala.collection.immutable
    //La collection immutable est par défaut chargée et il faut importer la collection mutable avant de pouvoir l'utiliser.
    //  Sinon, les collections les plus utilisées sont : les listes, les tuples, les Map, les sets et les "tableaux"
    //1) Les listes
    //  collection immutable et ordonée de données de même type.
    //eg :
    val list2:List[Int] = List(1,8,5,6,9,58,23,15,4)
    val names = List("joel", "ed", "chris", "maurice")
    val nums = List.range(0, 10)
    for (name <- names) println(name)
    //pour manipuler les listes, les fonctions anonymes sont très importantes.
    val resultat2 = names.count( s => s.startsWith("b"))
    val liste2 = names.filter(s => s.startsWith("b"))
    val ints = List(1,2,3)
    val doubledInts = ints.map(_ * 2)
    val doubledInts1 = ints.map(_ * 2)
    val doubledInts2 = ints.map((i: Int) => i * 2)
    val doubledInts3 = ints.map(i => i * 2)
    val x1 = ints.filter(_ > 5)
    val x2 = ints.filter((i: Int) => i % 2 == 0)
    //Il y'a également l'itérateur foreach.
    names.foreach(println) //ou bien names.foreach( e => println(e))
    names.filter(s => s.startsWith("b")).foreach(println)
    //Il y'a aussi la fonction map, qui applique le calcul ou l'algorithme à tous les élements de la liste
    val capNames = names.map(_.capitalize)
    //val lessThanFive = liste2.map(_ < 5)
    val doubles = liste2.map(_ * 2)
  }


  def TestHelloTuples () : Unit = {
    //2) les tuples
    //  collection immutable de données hétérogènes.Les tuples sont des ensembles ordonnés de valeurs. Ce sont des enregistrements immuables qui peuvent être reférencés par leur position.
    //Syntaxe :
    val nom_tuple = ("jvc", 40, true)
    //Tout comme dans les listes, les tuples peuvent contenue plusieurs types.
    //nom_tuple._1  renvoie "jvc" (la reférence dans les tuples commence à 1, et non 0 comme dans les tableaux et listes)
    //val tuple_name = (element, element, element)
    class Person(var name: String)
    //eg :
    val letters1 = ('a' to 'f' by 2)
    val t = (11, "Eleven", new Person("Eleven"))
    t._1
    t._2
    //on peut convertir des tuples en liste
    val nums = (1 to 10 by 2).toList
    val letters2 = ('a' to 'f').toList
    val letters3 = ('a' to 'f' by 2).toList
    for (test1 <- letters2) println(test1)
  }


  def TestHelloMaps () : Unit = {
    //3) Les Map
    //les Maps sont des tables de hachage, c'est-à-dire un tableau de clé-valeur.
    //Syntaxe :
    //val nom_map = Map("clé" -> valeur, "clé" -> valeur, etc..)
    //eg  :
    var test_map = Map("nom" -> ("Julien"), "prenom" -> "chokogoue", "age" -> 40)
    //Manipulation
    test_map.keys
    test_map.values
    test_map.keys.foreach(x => println(x))
    // si on veux affiché les valuer on met value a la place de keys
    println(' ')
    test_map.values.foreach(x => println(x))
    //on peut mettre les values ainsi que leur clé
    test_map.foreach(x => println(x))

    //table de clés-valeurs
    val states = Map(
      "AK" -> "Alaska",
      "IL" -> "Illinois",
      "KY" -> "Kentucky"
    )
  }



  def TestHelloTableaux () : Unit = {
    //4) Les tableaux
    // collection ordonnée et mutables des valeurs du même type.
    //Syntaxe :
    //val arrayName : Array[arrayType] = Array(elt1, el2, el3, etc);
    //eg :
    val tableau : Array[String] = Array("a", "b", "c")
    // Syntaxe : [val|var] nom_tableau = new Array[type] (nbre_elements)
    // eg : tableau immutable de chaîne de caractères
    val a = new Array[String] (4)
    a(0) = "Juvenal"
    a(1) = "John"

    for (i <- 0 to 3) {
      println (a(i))
    }
  }

//une fonction   d'affichage des liens
  def TestHelloReferences () : Unit = {
    println("Site officiel : https://www.scala-lang.org/ ");
    println("Documentation officielle : https://docs.scala-lang.org/overviews/scala-book/introduction.html");
    println("tuto : https://www.javatpoint.com/scala-tutorial ");
  }



}


