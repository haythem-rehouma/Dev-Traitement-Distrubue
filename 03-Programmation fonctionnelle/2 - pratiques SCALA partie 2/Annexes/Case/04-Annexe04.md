## **Différences entre Programmation Classique (Java) et Classes Case (Scala)**  

### **1. Moins de Code Boilerplate**  
En Java, pour représenter une simple structure de données (ex: `User`), il faut écrire **beaucoup de code** :  

#### **Java - Classe Standard**
```java
public class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return "User{name='" + name + "', age=" + age + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return age == user.age && name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
```
Ce code est **long et répétitif**.

---

#### **Scala - Classe Case**
Avec Scala, **tout est automatique** grâce à `case class` :
```scala
case class User(name: String, age: Int)
```
- Pas besoin de `getters/setters`
- `toString`, `equals`, `hashCode` sont générés automatiquement
- Pas besoin de `new` pour instancier  

---

### **2. Comparaison d’Objets**
En Java, comparer deux objets nécessite d’implémenter `equals` et `hashCode`.  
Avec Scala, la **comparaison est automatique** grâce aux classes case :

#### **Java - Comparaison avec `equals`**
```java
User u1 = new User("Alice", 25);
User u2 = new User("Alice", 25);
System.out.println(u1.equals(u2));  // true (grâce à la méthode equals)
```
#### **Scala - Comparaison directe**
```scala
val u1 = User("Alice", 25)
val u2 = User("Alice", 25)
println(u1 == u2)  // true (comparaison automatique des valeurs)
```

---

### **3. Utilisation en Big Data (Spark, Hadoop, etc.)**  

Scala est largement utilisé dans le **Big Data** (Apache Spark, Hadoop) grâce à sa concision et ses **classes case** qui facilitent la manipulation des données.  

#### **Avantages des Classes Case pour le Big Data**  
✅ **Représentation simple des objets** : Les classes case permettent de modéliser les données sans effort.  
✅ **Immuabilité** : Très utile pour les transformations de données sans effet de bord.  
✅ **Interopérabilité avec Spark** : Spark fonctionne avec des **Dataset[CaseClass]** qui permettent de manipuler les données comme des objets Java/Scala.

#### **Exemple d'utilisation avec Apache Spark**  
```scala
case class Person(name: String, age: Int)

val data = Seq(Person("Alice", 25), Person("Bob", 30))
val df = spark.createDataFrame(data)

df.show()
```
Les classes case permettent **d'exploiter la puissance de Spark sans écrire du code complexe**.

---

## **Résumé : Ce que les Classes Case Apportent**
| **Critère**           | **Java (Classique)** | **Scala (Case Class)** |
|----------------------|--------------------|----------------------|
| **Code concis**       | ❌ Beaucoup de code | ✅ Définition en une ligne |
| **Comparaison (`==`)** | ❌ Besoin d’implémenter `equals` | ✅ Comparaison automatique |
| **Immuabilité**       | ❌ Doit être définie explicitement | ✅ Automatique |
| **Utilisation en Big Data** | ⚠️ Moins pratique dans Spark | ✅ Optimisé pour Spark |

**Conclusion** :  
Les **classes case** rendent le développement **plus rapide et plus lisible**, ce qui est essentiel en **Big Data**, où l'on doit manipuler de grandes quantités de données efficacement.
