import Classes._

object main {
  def main(args:Array[String]){
    val person = new Person("John", "Doe")
    println("The first name is " + person.myfirstName)
    println("The last name is " + person.mylastName)
    println("The full name is " + person.getFullName)
    println(person.getFullInfo)
    
    val p = new Person("Jane", "Doe", 30)
    println(p.getFullInfo)
    println("get first name non-lazy: " + p.getOnlyFirstName)
    println("get first name lazy: " + p.getOnlyFirstNameLazily)
    
    //Student
    val student = new Student("James", "Madison", 17)
    println("Student Info: " + student.studentInfo)
    
    //Pet
    val cat = new Cat("plain")
    println("This cat is type: " + cat.race + ". Here is the name: " + cat.toString())
    
    //Polymorphism
    val listWithDifferentTypes = "String" :: 2 :: 3 :: 10 :: "AnotherString" :: Nil
    println("##Begin List##")
    listWithDifferentTypes.foreach(println)
    println("##End List##")
    val typeList: List[String] = "String 1" :: "String 2" :: "String 3" :: Nil
    typeList.foreach(println)
    
    //functions
    def getHead[A](l: List[A]) = l.head
    println(getHead(List[Int](2,3)))
    
    //challenge
    val w = new WebProgrammer("IE", "Google")
    println(w.webNavigatorName + " " + w.workingCompany + " " + w.programmerPreferredLanguage + " " + w.codeEditor)
    
 }
}