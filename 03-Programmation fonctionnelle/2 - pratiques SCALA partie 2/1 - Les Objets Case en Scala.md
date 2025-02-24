# 1 - Théorie et cours sur les Objets Case en Scala

## Introduction

Les objets `case` en Scala sont un élément central de la programmation fonctionnelle et du pattern matching, offrant une syntaxe concise pour définir des classes immuables. Ils jouent un rôle clé dans la création de modèles de données et le traitement des données de manière expressive et sûre. Ce cours explore les particularités des objets `case`, leur utilisation, et leur intégration dans les paradigmes de programmation Scala.

## Définition et Syntaxe

Un objet `case` en Scala est défini en utilisant le mot-clé `case` devant une classe. Cette définition crée automatiquement plusieurs fonctionnalités supplémentaires par rapport à une classe standard :

```scala
case class Person(name: String, age: Int)
```

### Caractéristiques Clés

1. **Immuabilité** : Les instances d'un objet `case` sont immuables par défaut. Une fois une instance créée, ses champs ne peuvent pas être modifiés.

2. **Constructeur Factory** : Scala génère automatiquement une méthode `apply`, permettant d'instancier des objets `case` sans utiliser le mot-clé `new`.

   ```scala
   val john = Person("John", 30) // Pas besoin de 'new'
   ```

3. **Méthodes `toString`, `equals`, et `hashCode`** : Scala implémente ces méthodes pour faciliter l'affichage, la comparaison, et le calcul du hash des instances.

4. **Support du Pattern Matching** : Les objets `case` sont conçus pour être utilisés avec le pattern matching, rendant les opérations basées sur la structure des données simples et expressives.

## Utilisation dans le Pattern Matching

Le pattern matching est l'une des fonctionnalités les plus puissantes en Scala, permettant de décomposer les structures de données et d'exécuter du code en fonction de leur forme. Les objets `case` sont souvent utilisés dans ce contexte pour une syntaxe claire et concise :

```scala
def greeting(person: Person): String = person match {
  case Person("Alice", _) => "Hello, Alice!"
  case Person("Bob", age) if age < 30 => "Hi, young Bob!"
  case Person(name, age) => s"Welcome, $name, age $age."
}
```

## Avantages des Objets Case

- **Simplicité** : Le code est plus concis et lisible, réduisant le besoin de boilerplate pour les classes de données.
- **Sécurité** : L'immutabilité par défaut encourage des pratiques de programmation sûres, facilitant la gestion des états.
- **Interopérabilité** : Ils facilitent l'utilisation de patterns fonctionnels, comme le pattern matching, améliorant l'expressivité du code.

## Limitations

- Bien que très utiles, les objets `case` ne sont pas toujours la solution idéale.
- Leur immuabilité peut être restrictive dans certains contextes où une modification d'état est nécessaire.
- De plus, l'utilisation excessive d'objets `case` pour de grandes structures de données peut augmenter l'empreinte mémoire.

# 2 - Exemple pratique 

Pour illustrer l'utilisation des objets `case` en Scala, nous allons créer un exemple complet qui met en œuvre un petit programme de gestion des employés dans une entreprise. Cet exemple démontrera comment définir des objets `case`, utiliser le pattern matching, et manipuler une liste d'employés.

### Étape 1: Définition de l'Objet Case

Nous commençons par définir un objet `case` pour représenter un employé dans notre entreprise. Chaque employé a un nom, un département, et un salaire.

```scala
case class Employee(name: String, department: String, salary: Double)
```

### Étape 2: Création d'une Liste d'Employés

Ensuite, nous créons une liste d'employés à utiliser dans notre programme. Cette liste sera notre base de données simplifiée.

```scala
val employees = List(
  Employee("Alice", "IT", 60000),
  Employee("Bob", "Marketing", 45000),
  Employee("Charlie", "IT", 55000),
  Employee("Diana", "Sales", 50000)
)
```

### Étape 3: Fonction de Recherche par Département

Nous ajoutons une fonction qui utilise le pattern matching pour trouver tous les employés d'un certain département et les afficher.

```scala
def findByDepartment(department: String): Unit = {
  employees.filter(_.department == department).foreach {
    case Employee(name, _, salary) =>
      println(s"$name travaille dans le département $department avec un salaire de $salary")
  }
}
```

### Étape 4: Augmentation de Salaire

Nous allons maintenant ajouter une fonction qui augmente le salaire de tous les employés d'un certain département en utilisant une transformation avec `map`.

```scala
def raiseSalary(department: String, raiseAmount: Double): List[Employee] = {
  employees.map {
    case e @ Employee(_, dep, salary) if dep == department => e.copy(salary = salary + raiseAmount)
    case e => e
  }
}
```

### Étape 5: Programme Principal

Enfin, nous combinons toutes les pièces dans notre programme principal pour démontrer l'utilisation des fonctions.

```scala
object EmployeeManagement extends App {
  println("Employés IT avant augmentation :")
  findByDepartment("IT")
  
  val updatedEmployees = raiseSalary("IT", 5000)
  
  println("\nEmployés IT après augmentation :")
  updatedEmployees.filter(_.department == "IT").foreach { e =>
    println(s"${e.name} a maintenant un salaire de ${e.salary}")
  }
}
```

# 3 - Explication détaillée de l'exemple précédent

Cet exemple montre comment les objets `case` facilitent la modélisation et la manipulation de données en Scala. Nous avons vu comment utiliser le pattern matching pour effectuer des opérations conditionnelles sur des collections d'objets `case`, et comment manipuler ces objets de manière immuable avec `copy`. Les objets `case` rendent le code plus lisible, expressif, et sécurisé en encourageant l'immutabilité et en fournissant des fonctionnalités utiles automatiquement.
Ce programme Scala illustre un système simple de gestion des employés dans une entreprise. Il utilise les objets `case` pour modéliser les employés, démontrant leur utilité dans la définition de structures de données immuables, le pattern matching, et les opérations sur des collections. Le programme permet de filtrer les employés par département, d'afficher des informations sur eux, et d'appliquer une augmentation de salaire à un groupe spécifique d'employés. Voici un détail des composantes principales du programme et des concepts de Scala utilisés :

### Définition de l'Objet Case `Employee`
```scala
case class Employee(name: String, department: String, salary: Double)
```
- **`case class`** : Définit une classe immuable avec des propriétés `name`, `department`, et `salary`. Scala génère automatiquement des méthodes utiles comme `apply`, `toString`, `equals`, et `hashCode`.
- **Immuabilité** : Les instances de `Employee` sont immuables, ce qui signifie que leurs valeurs ne peuvent pas être modifiées après leur création.

### Création d'une Liste d'Employés
```scala
val employees = List(
  Employee("Alice", "IT", 60000),
  Employee("Bob", "Marketing", 45000),
  Employee("Charlie", "IT", 55000),
  Employee("Diana", "Sales", 50000)
)
```
- Une liste d'employés est créée en utilisant la méthode `apply` générée automatiquement par Scala pour les objets `case`. Cette liste sert de base de données simplifiée pour notre exemple.

### Fonction de Recherche par Département
```scala
def findByDepartment(department: String): Unit = {
  employees.filter(_.department == department).foreach {
    case Employee(name, _, salary) =>
      println(s"$name travaille dans le département $department avec un salaire de $salary")
  }
}
```
- **Pattern Matching** : Utilise le pattern matching pour extraire `name` et `salary` de chaque `Employee` correspondant au `department` spécifié. Le symbole `_` est utilisé pour ignorer le champ `department` lors de l'extraction puisqu'il est déjà connu.
- **`Unit`** : Le type de retour `Unit` en Scala est similaire à `void` en Java. Il signifie que la fonction ne retourne aucune valeur utile et est utilisée principalement pour ses effets secondaires, ici l'affichage des informations.

### Fonction d'Augmentation de Salaire
```scala
def raiseSalary(department: String, raiseAmount: Double): List[Employee] = {
  employees.map {
    case e @ Employee(_, dep, salary) if dep == department => e.copy(salary = salary + raiseAmount)
    case e => e
  }
}
```
- **`map` et `copy`** : Parcourt chaque `Employee`, et si l'employé appartient au département spécifié, il utilise la méthode `copy` pour créer une nouvelle instance de `Employee` avec un salaire augmenté. Les employés d'autres départements sont retournés tels quels.
- Cette fonction illustre comment effectuer des modifications immuables sur des collections d'objets `case` en Scala.

### Programme Principal
```scala
object EmployeeManagement extends App {
  println("Employés IT avant augmentation :")
  findByDepartment("IT")
  
  val updatedEmployees = raiseSalary("IT", 5000)
  
  println("\nEmployés IT après augmentation :")
  updatedEmployees.filter(_.department == "IT").foreach { e =>
    println(s"${e.name} a maintenant un salaire de ${e.salary}")
  }
}
```
- **`object` avec `App`** : Scala permet de créer un singleton `object` qui étend `App` pour exécuter du code. Les instructions dans le corps de cet objet sont exécutées lorsque le programme est lancé.
- **Exécution du Programme** : Affiche d'abord les employés du département IT, applique une augmentation de salaire, puis affiche à nouveau les employés IT avec leur nouveau salaire.

Globalement, ce programme démontre la puissance et la flexibilité des objets `case` en Scala pour manipuler des données de manière fonctionnelle et immuable, en utilisant des concepts comme le pattern matching et les opérations sur les collections.

# 4 - Conclusion

- Les objets `case` en Scala offrent un moyen puissant et expressif de modéliser des données immuables et de les manipuler à travers le pattern matching. Ils sont un pilier de la programmation fonctionnelle en Scala, simplifiant la définition de classes de données et favorisant des pratiques de programmation sûres et expressives. Comme pour tout outil, leur utilisation doit être équilibrée avec les besoins spécifiques du projet et les caractéristiques du domaine d'application.

- Ce cours a exploré les bases et les avantages des objets `case`, fournissant une fondation solide pour leur utilisation efficace dans vos projets Scala.
