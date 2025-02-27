// on importe le module Classes
import Classes._
// on cree un objet Main en definissant la valeur person avec deux attribut "jhon"et "Doe"
object main {
  def main(args:Array[String]){
    val person = new Person("John", "Doe")
 //on appelle les methodes myfirstName,  mylastName et getFullName en les affichants dans un println
    println("The first name is " + person.myfirstName)
    println("The last name is " + person.mylastName)
    println("The full name is " + person.getFullName)
    println(person.getFullInfo)
// on identifie une nouvelle valeur "p" a partir de la valeur class"Person" et on appelle les methodes "getOnlyFirstName" a evaluation immediate et la methode "getOnlyFirstNameLazily" a evaluation retardee
    val p = new Person("Jane", "Doe", 30)
    println(p.getFullInfo)
    println("get first name non-lazy: " + p.getOnlyFirstName)
    println("get first name lazy: " + p.getOnlyFirstNameLazily)

    //Student on  crée une instance Student avec comme attribut "firstName" et "LastName" , puis on affiche les infos etudiants
    val student = new Student("James", "Madison", 17)
    println("Student Info: " + student.studentInfo)

    //Pet on definit l'instance cat et on affiche on appelant la methode  to.string
    val cat = new Cat("plain")
    println("This cat is type: " + cat.race + ". Here is the name: " + cat.toString())

    //Polymorphism
    // on definit l'instance "listWithDifferentTypes" avec le Nil comme liste vide
    val listWithDifferentTypes = "String" :: 2 :: 3 :: 10 :: "AnotherString" :: Nil
    println("##Begin List##")
    // on fait appel a la fonction "foreach" pour afficher les valeur de la liste
    listWithDifferentTypes.foreach(println)
    println("##End List##")
    // on cree une liste avec les attributs "string1","string2" et " string3" et on fait appel a la fonction foreach pour afficher les elements de la liste
    val typeList: List[String] = "String 1" :: "String 2" :: "String 3" :: Nil
    typeList.foreach(println)

    //functions on definit une fonction "getHead" , pour afficher le premier element de la liste A
    def getHead[A](l: List[A]) = l.head
    println(getHead(List[Int](2,3)))

    //challenge
    val w = new WebProgrammer("IE", "Google")
    println(w.webNavigatorName + " " + w.workingCompany + " " + w.programmerPreferredLanguage + " " + w.codeEditor)

  }
}