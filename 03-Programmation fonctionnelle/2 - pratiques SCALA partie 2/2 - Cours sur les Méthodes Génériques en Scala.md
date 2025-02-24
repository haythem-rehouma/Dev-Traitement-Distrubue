# 1 - Théorie - Les Méthodes Génériques en Scala

Les méthodes génériques sont un outil puissant en Scala, permettant de créer des fonctions et des méthodes qui peuvent opérer sur des types de données variés. Cette capacité est essentielle pour écrire du code réutilisable et flexible. Dans ce cours, nous allons explorer comment définir et utiliser des méthodes génériques en Scala, en mettant l'accent sur la syntaxe et les concepts clés.

### Introduction aux Génériques

Les génériques permettent de paramétrer des classes, des traits, et des méthodes avec des types. Au lieu de coder pour un type spécifique, vous pouvez abstraire le type et permettre au code de fonctionner avec n'importe quel type de données. Cela est particulièrement utile pour les collections, les algorithmes, et les structures de données qui peuvent être appliqués à divers types.

### Définir une Méthode Générique

En Scala, vous définissez une méthode générique en spécifiant un ou plusieurs paramètres de type entre crochets juste après le nom de la méthode. Ces paramètres de type peuvent ensuite être utilisés pour définir le type des paramètres de la méthode, le type de retour, ou être utilisés dans le corps de la méthode.

#### Syntaxe

La syntaxe pour définir une méthode générique en Scala est la suivante :

```scala
def methodName[T](param: T): T = {...}
```

- **`[T]`** : C'est le paramètre de type générique. Vous pouvez spécifier plusieurs paramètres de type en les séparant par des virgules.
- **`param: T`** : Le type du paramètre `param` est `T`, ce qui signifie qu'il peut être de n'importe quel type.
- **`: T`** : Le type de retour de la méthode est également `T`, indiquant que la méthode retourne une valeur du même type que celui de son paramètre.

### Exemples d'Utilisation

Voyons quelques exemples pour illustrer l'utilisation des méthodes génériques.

#### Exemple 1: Fonction Identity

```scala
def identity[T](x: T): T = x
```

Cette méthode retourne simplement la valeur qu'elle reçoit en paramètre, quel que soit son type.

#### Exemple 2: Échange de Deux Éléments dans un Tuple

```scala
def swap[T, U](tuple: (T, U)): (U, T) = (tuple._2, tuple._1)
```

Cette méthode prend un tuple de deux éléments de types possiblement différents et retourne un nouveau tuple avec l'ordre des éléments inversé.

### Pourquoi `[T]` ?

Revenons à la question initiale sur comment définir une méthode générique. La raison pour laquelle `[T]` est utilisé pour définir des méthodes génériques est qu'il permet d'introduire un ou plusieurs types abstraits que la méthode peut utiliser. Cela rend le code plus flexible et réutilisable, car la même méthode peut fonctionner avec différents types de données sans modification.

# 2 - Annexe 1 - Explication plus détaillée sur les types génériques 

## Cours: Méthodes Génériques en Scala

Les méthodes génériques sont un outil puissant en Scala, permettant de créer des fonctions qui peuvent opérer sur des types de données qui ne sont spécifiés qu'au moment de l'appel de la méthode. Cette fonctionnalité est essentielle pour écrire du code réutilisable et type-safe sans se limiter à un type de données spécifique.

### Définition d'une Méthode Générique

La syntaxe correcte pour définir une méthode générique en Scala est:

```scala
def methodName[T](param: T): T = {...}
```

Expliquons chaque partie de cette syntaxe :

- **`def`** : Le mot-clé utilisé pour définir une méthode.
- **`methodName`** : Le nom de la méthode. Il peut être n'importe quel identifiant valide en Scala.
- **`[T]`** : La déclaration du type générique. `T` est un placeholder pour le type de données qui sera utilisé. Vous pouvez utiliser n'importe quelle lettre ou mot, pas seulement `T`. Ce type sera déterminé au moment de l'appel de la méthode.
- **`(param: T)`** : La liste des paramètres de la méthode. Ici, `param` est un paramètre de type `T`. Cela signifie que le type de `param` sera le même que celui spécifié lors de l'appel de la méthode.
- **`: T`** : Le type de retour de la méthode. Cela indique que la méthode retournera une valeur du même type que celui passé en paramètre.
- **`{...}`** : Le corps de la méthode. Il contient le code qui sera exécuté lorsque la méthode est appelée.

### Exemple: Méthode Générique pour Inverser un Élément

Supposons que nous voulons écrire une méthode générique `reverse` qui peut inverser à la fois des `String` et des listes d'éléments de n'importe quel type.

#### Étape 1: Définition de la Méthode Générique

```scala
def reverse[T](input: T)(implicit ev: T => IterableOnce[_]): T = {
  val reversed = input.iterator.toSeq.reverse
  (input match {
    case _: String => reversed.mkString
    case _ => reversed
  }).asInstanceOf[T]
}
```

#### Explication Étape par Étape

1. **Définition de la méthode avec un type générique `T`** :
   - `def reverse[T](input: T)` déclare une méthode `reverse` qui accepte un argument `input` de type générique `T`.

2. **Utilisation d'une preuve implicite** :
   - `(implicit ev: T => IterableOnce[_])` : Cet argument implicite demande une preuve que `T` peut être itéré, permettant d'utiliser `iterator` sur `input`. Cette partie permet de traiter `input` comme une collection ou une chaîne de caractères (car les chaînes en Scala sont également des collections de caractères).

3. **Inversion de l'élément** :
   - `val reversed = input.iterator.toSeq.reverse` convertit `input` en une séquence (`Seq`) et l'inverse.

4. **Corps de la méthode** :
   - Le `match` expression vérifie si `input` est une chaîne de caractères ou une autre forme d'itérable. Si `input` est une chaîne, il combine les éléments inversés en une nouvelle chaîne avec `mkString`. Sinon, il retourne la séquence inversée telle quelle.

5. **Casting du type de retour** :
   - `.asInstanceOf[T]` assure que le type de retour est le même que le type d'`input`. C'est nécessaire car le corps de la méthode pourrait retourner soit une `String` soit une `Seq`, selon le type d'`input`.

#### Utilisation de la Méthode Générique

```scala
val reversedString = reverse("Scala")  // Appel avec un String
val reversedList = reverse(List(1, 2, 3))  // Appel avec une List[Int]

println(reversedString)  // alacS
println(reversedList)  // List(3, 2, 1)
```

- Dans cet exemple, `reverse` est appelé une fois avec une `String` et une fois avec une `List[Int]`.
- Grâce à la généricité et au pattern matching, `reverse` peut traiter les deux types d'input, démontrant la puissance et la flex


# 3 - Pratique 

Pour illustrer la création et l'utilisation de méthodes génériques en Scala, nous allons créer un exemple complet similaire à celui des employés, mais cette fois, nous allons définir une méthode générique qui s'applique à différents types d'objets. Supposons que nous voulions créer une fonction générique qui pourrait augmenter le salaire des employés ou ajuster le budget des départements, en fonction de l'objet passé en paramètre.

### Étape 1: Définition des Objets Case

D'abord, définissons deux objets `case` : un pour les employés et un pour les départements.

```scala
case class Employee(name: String, salary: Double)
case class Department(name: String, budget: Double)
```

### Étape 2: Méthode Générique pour l'Augmentation

Ensuite, créons une méthode générique `increaseAmount` qui peut accepter soit un `Employee` soit un `Department` et retourner une instance mise à jour avec un montant augmenté (soit le salaire soit le budget).

```scala
def increaseAmount[T](entity: T, increase: Double)(implicit updater: (T, Double) => T): T = {
  updater(entity, increase)
}
```

Cette méthode nécessite un paramètre implicite `updater`, une fonction qui sait comment mettre à jour le type spécifique `T` avec le montant de l'augmentation.

### Étape 3: Définition des Fonctions de Mise à Jour

Nous devons fournir des fonctions qui savent comment augmenter le salaire d'un employé et le budget d'un département.

```scala
implicit val updateEmployee: (Employee, Double) => Employee =
  (employee, increase) => employee.copy(salary = employee.salary + increase)

implicit val updateDepartment: (Department, Double) => Department =
  (department, increase) => department.copy(budget = department.budget + increase)
```

Ces fonctions sont marquées `implicit`, ce qui permet à Scala de les passer automatiquement en tant que paramètre implicite à `increaseAmount`.

### Étape 4: Utilisation de la Méthode Générique

Finalement, utilisons notre méthode générique pour augmenter le salaire d'un employé et le budget d'un département.

```scala
val employee = Employee("Alice", 50000)
val department = Department("IT", 200000)

val updatedEmployee = increaseAmount(employee, 5000)
val updatedDepartment = increaseAmount(department, 10000)

println(updatedEmployee)  // Employee(Alice,55000.0)
println(updatedDepartment) // Department(IT,210000.0)
```

### Explication Complète

- **Objets `case`** (`Employee` et `Department`): Représentent les données sur lesquelles nous voulons opérer.
- **Méthode Générique `increaseAmount`**: Capable d'accepter n'importe quel type `T`, à condition qu'une fonction de mise à jour soit disponible pour ce type. Utilise un paramètre implicite pour trouver automatiquement cette fonction.
- **Fonctions de Mise à Jour `implicit`**: Définit comment augmenter le salaire ou le budget. Scala les sélectionne automatiquement en fonction du type de l'objet passé à `increaseAmount`.
- **Utilisation**: Nous passons des instances de `Employee` et `Department` à `increaseAmount` avec les montants d'augmentation respectifs. Grâce à la généricité et aux implicits, la même méthode `increaseAmount` peut appliquer l'augmentation appropriée à des types d'objets différents.

Cet exemple illustre la puissance de la programmation générique en Scala, permettant de créer des opérations réutilisables et type-safe qui peuvent travailler avec divers types de données.

# 4 - Annexe 2 - type-safe ?

Le terme "type-safe" ou "sûreté de type" désigne une propriété fondamentale de certains langages de programmation qui vise à prévenir ou à réduire les erreurs liées à la manipulation des types de données. Dans un langage de programmation type-safe, le compilateur ou l'environnement d'exécution assure que les opérations sont réalisées sur des données de types compatibles, minimisant ainsi les risques d'erreurs à l'exécution.

### Signification et Importance de la Sûreté de Type

- **Contrôles à la Compilation** : Dans les langages type-safe, beaucoup d'erreurs potentielles liées au type sont détectées lors de la compilation. Cela signifie que le code qui compile est, dans une large mesure, garanti d'être libre de certains types d'erreurs d'exécution qui résultent d'une mauvaise manipulation des types.
  
- **Prévention des Comportements Imprévisibles** : En s'assurant que les opérations sur les données sont effectuées avec des types appropriés, la sûreté de type aide à prévenir des comportements imprévisibles qui peuvent résulter de conversions de types incorrectes ou inattendues, comme accéder à un emplacement mémoire non valide.
  
- **Facilitation du Raisonnement sur le Code** : Quand un langage est type-safe, cela facilite le raisonnement sur le code, car le développeur peut avoir confiance dans les types des données qu'il manipule. Cela réduit le besoin de vérifications de type manuelles et de conversions explicites de type dans le code, rendant le code plus lisible et maintenable.

### Exemples de Sûreté de Type

- **Contrôle de Type Statique** : Scala, comme beaucoup de langages fortement typés, effectue un contrôle de type statique, où les types sont vérifiés à la compilation. Par exemple, tenter d'additionner un entier (`Int`) et une chaîne de caractères (`String`) entraînera une erreur de compilation.

- **Inférence de Type** : Scala et d'autres langages modernes utilisent l'inférence de type pour déduire automatiquement les types de certaines expressions, réduisant ainsi la nécessité pour le développeur de spécifier explicitement les types. Cela maintient la sûreté de type tout en allégeant la syntaxe.

- **Génériques** : Les fonctionnalités génériques, comme illustrées dans l'exemple précédent avec `increaseAmount[T]`, permettent de créer des fonctions et des structures de données qui peuvent opérer de manière sûre sur divers types, tant que ces types respectent certaines contraintes. Cela augmente la réutilisabilité du code sans sacrifier la sûreté de type.

### Limitations et Considérations

- **Performance** : Parfois, les vérifications de type, surtout si elles sont effectuées dynamiquement à l'exécution, peuvent introduire un surcoût en termes de performance. Cependant, dans les langages à typage statique comme Scala, la plupart de ces vérifications sont effectuées à la compilation.

- **Complexité** : Pour les langages très type-safe avec des systèmes de types avancés, il peut y avoir une courbe d'apprentissage plus raide et une complexité accrue du code, notamment avec l'utilisation intensive de génériques et de types abstraits.

- En résumé, la sûreté de type est un aspect crucial de la conception de langages de programmation modernes, offrant un équilibre entre la flexibilité du code et la prévention des erreurs.
- Elle joue un rôle essentiel dans le développement de logiciels fiables et maintenables.

### Conclusion

- Les méthodes génériques sont une partie essentielle de la programmation en Scala, offrant une flexibilité et une réutilisabilité du code grâce à l'abstraction des types.
- La capacité à travailler avec des types génériques rend Scala particulièrement puissant pour la création de bibliothèques et de frameworks, où la généralité et la réutilisabilité sont clés.
- En comprenant et en utilisant des méthodes génériques, vous pouvez écrire des codes plus expressifs et plus robustes.
