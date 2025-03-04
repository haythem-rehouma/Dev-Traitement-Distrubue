
## 📖 Documentation  

Consultez la documentation complète ici :  
🔗 [Programmation Fonctionnelle - Pratiques SCALA (Partie 2)](https://github.com/haythem-rehouma/Dev-Traitement-Distrubue/tree/main/03-Programmation%20fonctionnelle/2%20-%20pratiques%20SCALA%20partie%202)  



*Je vous propose une **cheatsheet** sous forme de tableau*
- **Scala**
- **Case Class, Class, Trait**
    

| **Concept**       | **Description** | **Syntaxe** | **Exemple** |
|-------------------|---------------|------------|-------------|
| **Class** | Définit une classe standard avec des attributs et des méthodes | `class NomClasse { ... }` | ```scala class Person(name: String, age: Int) ``` |
| **Case Class** | Classe immuable avec des méthodes `apply`, `copy`, `toString`, `equals`, et `hashCode` générées automatiquement | `case class NomClasse(param1: Type, param2: Type)` | ```scala case class Person(name: String, age: Int) val p = Person("Alice", 25) ``` |
| **Trait** | Interface pouvant contenir des méthodes abstraites et concrètes. Permet un héritage multiple | `trait NomTrait { ... }` | ```scala trait Printable { def print(): Unit = println("Printing...") } class Document extends Printable ``` |
| **Object** | Singleton en Scala, utilisé pour contenir des fonctions ou des constantes partagées | `object NomObject { ... }` | ```scala object Utils { def hello(): String = "Hello, Scala!" } println(Utils.hello()) ``` |
| **Pattern Matching** | Vérification de motifs avec `match`, similaire au `switch` mais plus puissant | `val result = x match { case ... }` | ```scala val msg = "hello" match { case "hello" => "Hi!" case _ => "Bye!" } ``` |
| **Companion Object** | Objet singleton associé à une classe, utilisé pour définir des méthodes et attributs statiques | `class NomClasse {...}; object NomClasse {...}` | ```scala class Person(val name: String) object Person { def apply(name: String) = new Person(name) } ``` |
| **Lazy Val** | Initialisation différée jusqu'à son premier accès | `lazy val x = ...` | ```scala lazy val data = { println("Loading..."); 42 } println(data) // "Loading..." affiché seulement ici ``` |
| **Option** | Gestion des valeurs nulles de manière sécurisée (`Some(value)` ou `None`) | `val res: Option[Int] = Some(10) / None` | ```scala def safeDivide(x: Int, y: Int): Option[Int] = if (y != 0) Some(x / y) else None ``` |
| **Higher-Order Function** | Fonction qui prend une autre fonction en paramètre ou retourne une fonction | `def f(g: Int => Int): Int = ...` | ```scala def applyTwice(f: Int => Int, x: Int): Int = f(f(x)) println(applyTwice(_ * 2, 3)) // 12 ``` |
| **Map, Filter, Reduce** | Manipulation des collections de manière fonctionnelle | `list.map(...)`, `list.filter(...)`, `list.reduce(...)` | ```scala val nums = List(1,2,3,4) val even = nums.filter(_ % 2 == 0) val doubled = nums.map(_ * 2) ``` |
| **For Comprehension** | Alternative à `map` et `filter`, syntaxe plus lisible | `for (x <- list) yield ...` | ```scala val result = for (x <- List(1, 2, 3) if x % 2 == 0) yield x * 2 ``` |
| **Implicit** | Paramètres implicites pour éviter la répétition de code | `implicit val default = ...` | ```scala implicit val rate: Double = 0.05 def compute(amount: Double)(implicit r: Double): Double = amount * r ``` |
| **Monads (`Try`, `Future`)** | Gestion des erreurs et de l'asynchronisme | `Try { ... }`, `Future { ... }` | ```scala import scala.util.{Try, Success, Failure} val res = Try(10 / 0) match { case Success(value) => value case Failure(ex) => "Error" } ``` |
| **Type Inference** | Scala peut déduire les types des variables | `val x = 10` (pas besoin de `: Int`) | ```scala val message = "Hello" val number = 42 ``` |
| **Tuples** | Stocke plusieurs valeurs ensemble sans créer une classe | `(val1, val2, ...)` | ```scala val pair = ("Alice", 25) println(pair._1) // "Alice" ``` |
| **Compagnon d’Objet** | Un `object` avec le même nom qu’une `class` pour définir des méthodes statiques | `class NomClasse {...} object NomClasse {...}` | ```scala class Person(val name: String) object Person { def apply(name: String) = new Person(name) } ``` |
| **Trait avec Abstract Methods** | Un `trait` peut contenir des méthodes non définies | `trait T { def f(): Unit }` | ```scala trait Animal { def makeSound(): String } class Dog extends Animal { def makeSound() = "Woof!" } ``` |

