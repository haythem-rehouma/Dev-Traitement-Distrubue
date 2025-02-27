

object Classes {
  class Person(firstName:String, lastName:String) {
    val myfirstName:String = firstName
    val mylastName:String = lastName
    var age = 0
    
    def getFullName:String = firstName + " - " + lastName
    def getFullInfo:String = "My name is " + firstName + " " + lastName + ", and I am " + age + " years old."
    
    def this(fName:String, lName:String, age:Int){
      this(fName, lName)
      this.age = age
    }
    
    val getOnlyFirstName = {println("initializing this...I'm not lazy at all!"); firstName}
    lazy val getOnlyFirstNameLazily = {println("This message will never print unless it's called explicitly"); firstName}
  }
  
  class Student(val firstName:String, val lastName:String, val mark: Double) extends Person (firstName, lastName) {
    def studentInfo:String = "Hi, I am " + firstName + " " + lastName + " " + " and I am a good student here is my mark: " + mark
  }
  
  abstract class Pet(val name:String) {
    override def toString = s"the pet of the family, they call me: $name"
    def animalRace:String
  }
  
  class Cat(val race:String, name:String="Miaouuw") extends Pet(name) {
    def animalRace = "Cat"
  }
  
  
  
//Challenge/////
//Write a class called "Engineer" which has a non-defined method: "programmerPreferredLanguage" and a lazy value "codeEditor".
//The Engineer class has only one field: the engineers working company with a default value.
//Then, a class called "WebProgrammer" which inherits from the Engineer class and has one other value: webNavigatorName.

  abstract class Engineer(val workingCompany:String = "IBM") {
    def programmerPreferredLanguage:String
    lazy val codeEditor = "Eclipse"
  }
  
  class WebProgrammer(val webNavigatorName:String = "Chrome", override val workingCompany:String="Microsoft") extends Engineer {
    def programmerPreferredLanguage = "Scala"
  }
}