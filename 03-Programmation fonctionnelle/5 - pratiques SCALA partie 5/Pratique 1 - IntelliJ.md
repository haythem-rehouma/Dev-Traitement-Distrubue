### **Tutoriel : Exécution d’un Programme Spark en Scala avec Maven et IntelliJ IDEA**

**Objectif** : 

- Apprendre à configurer un projet Scala avec Maven dans IntelliJ IDEA et exécuter un programme Spark.

---

# **1. Prérequis**
Avant de commencer, assurez-vous d'avoir installé :
- **Apache Spark 3.3.0**  
- **Java 8 (JDK 1.8)** (pour compatibilité avec Spark)  
- **Scala 2.12.7** (correspond à votre version de Spark)  
- **Maven 3.9.0** (version précisée)  
- **IntelliJ IDEA avec le plugin Scala**  

---

# **📂 2. Création du Projet Maven dans IntelliJ IDEA**
### **1️⃣ Créer un projet Maven**
1. **Ouvrez IntelliJ IDEA** et sélectionnez **"New Project"**.
2. Dans **"Project SDK"**, choisissez **JDK 1.8**.
3. Sélectionnez **"Maven"** comme type de projet.
4. **Décochez** "Create from Archetype".
5. Cliquez sur **Next**, donnez un **nom au projet** et cliquez sur **Finish**.

---

### **2️⃣ Configuration du fichier `pom.xml`**
Dans IntelliJ IDEA, ouvrez le fichier **`pom.xml`** et **remplacez son contenu** par ce code :

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>sample</groupId>
  <artifactId>scala-module-dependency-sample</artifactId>
  <version>1.0-SNAPSHOT</version>
  <properties>
    <encoding>UTF-8</encoding>
  </properties>
  <!-- Maven profiles allow you to support both Scala 2.10, 2.11 and Scala 2.12 with
    the right dependencies for modules specified for each version separately -->
  <profiles>
    <profile>
      <id>scala-2.12</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <properties>
        <scala.version>2.12.7</scala.version>
        <scala.compat.version>2.12</scala.compat.version>
      </properties>
      <dependencies>
        <dependency>
          <groupId>org.scala-lang</groupId>
          <artifactId>scala-library</artifactId>
          <version>${scala.version}</version>
        </dependency>
        <dependency>
          <groupId>org.scala-lang.modules</groupId>
          <artifactId>scala-xml_${scala.compat.version}</artifactId>
          <version>1.1.1</version>
        </dependency>
        <dependency>
          <groupId>org.scala-lang.modules</groupId>
          <artifactId>scala-parser-combinators_${scala.compat.version}</artifactId>
          <version>1.1.1</version>
        </dependency>
        <dependency>
          <groupId>org.scala-lang.modules</groupId>
          <artifactId>scala-swing_${scala.compat.version}</artifactId>
          <version>2.0.3</version>
        </dependency>




        <dependency>
          <groupId>org.apache.spark</groupId>
          <artifactId>spark-core_2.12</artifactId>
          <version>3.3.0</version> <!-- Vérifiez la compatibilité avec votre version de Scala -->
        </dependency>

        <dependency>
          <groupId>org.apache.spark</groupId>
          <artifactId>spark-sql_2.12</artifactId>
          <version>3.3.0</version>
        </dependency>


      </dependencies>
    </profile>
    <profile>
      <id>scala-2.11</id>
      <properties>
        <scala.version>2.11.12</scala.version>
        <scala.compat.version>2.11</scala.compat.version>
      </properties>
      <dependencies>
        <dependency>
          <groupId>org.scala-lang</groupId>
          <artifactId>scala-library</artifactId>
          <version>${scala.version}</version>
        </dependency>
        <dependency>
          <groupId>org.scala-lang.modules</groupId>
          <artifactId>scala-xml_${scala.compat.version}</artifactId>
          <version>1.1.1</version>
        </dependency>
        <dependency>
          <groupId>org.scala-lang.modules</groupId>
          <artifactId>scala-parser-combinators_${scala.compat.version}</artifactId>
          <version>1.1.1</version>
        </dependency>

      </dependencies>
    </profile>
    <profile>
      <id>scala-2.10</id>
      <properties>
        <scala.version>2.10.7</scala.version>
        <scala.compat.version>2.10</scala.compat.version>
      </properties>
      <dependencies>
        <dependency>
          <groupId>org.scala-lang</groupId>
          <artifactId>scala-library</artifactId>
          <version>${scala.version}</version>
        </dependency>

      </dependencies>
    </profile>
  </profiles>
  <build>
    <sourceDirectory>src/main/scala</sourceDirectory>
    <testSourceDirectory>src/test/scala</testSourceDirectory>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.3</version>
      </plugin>
      <plugin>
        <groupId>net.alchim31.maven</groupId>
        <artifactId>scala-maven-plugin</artifactId>
        <version>3.2.2</version>
        <executions>
          <execution>
            <goals>
              <goal>compile</goal>
              <goal>testCompile</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <args>
            <!-- work-around for https://issues.scala-lang.org/browse/SI-8358 -->
            <arg>-nobootcp</arg>
          </args>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

---

### **3️⃣ Recharger le projet Maven**
1. **Cliquez sur l’onglet "Maven"** dans IntelliJ IDEA.
2. Cliquez sur **"Reload All Maven Projects"** (icône de rafraîchissement).
3. Attendez que Maven télécharge toutes les dépendances.

---

# **3. Ajout du Code Scala**
Dans le dossier `src/main/scala`, créez un fichier `StockProcessor.scala` et **collez le code suivant** :

```scala
// Importation des bibliothèques nécessaires

import org.apache.spark.sql.{SparkSession, DataFrame}  // DataFrame est ici correctement importé
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Encoders


// Définition de la case class pour les stocks
case class Stock(
                  dt: String,
                  openprice: Double,
                  highprice: Double,
                  lowprice: Double,
                  closeprice: Double,
                  volume: Double,
                  adjcloseprice: Double
                )

// Objet contenant les méthodes pour parser et charger les données
object StockProcessor {

  def parseStock(str: String): Option[Stock] = {
    val line = str.split(",")

    try {
      Some(Stock(
        line(0),
        line(1).toDouble,
        line(2).toDouble,
        line(3).toDouble,
        line(4).toDouble,
        line(5).toDouble,
        line(6).toDouble
      ))
    } catch {
      case e: Exception =>
        println(s"Erreur de parsing pour la ligne : $str -> ${e.getMessage}")
        None
    }
  }
  def parseRDD(rdd: RDD[String]): RDD[Stock] = {
    val header = rdd.first() // Récupération de l'en-tête
    rdd
      .filter(_ != header) // Suppression de l'en-tête
      .flatMap(parseStock) // Utilisation de flatMap pour ignorer les erreurs
      .cache()
  }
  def main(args: Array[String]): Unit = {
    // Création de la session Spark
    val spark = SparkSession.builder()
      .appName("Stock Analysis")
      .master("local[*]") // Mode local
      .getOrCreate()

    import spark.implicits._ // Import pour convertir RDD en DataFrame

    // Charger le fichier CSV et transformer en DataFrame
    val stocksAAPLDF: DataFrame = parseRDD(spark.sparkContext.textFile("C:/Users/rehou/Downloads/AAPL.csv"))
      .toDF() // Conversion en DataFrame
      .cache()

    // Affichage des premières lignes
    stocksAAPLDF.show()
  }
}
```

---

# **⚙ 4. Configuration de l’Exécution**
### **🔹 Modifier les Configurations d'Exécution**
1. **Cliquez sur le bouton vert ▶** en haut.
2. Sélectionnez **"Edit Configurations..."**.
3. Cliquez sur **"Modify options"**.
4. Cochez **"Allow multiple instances"**.
5. Allez dans *Build and run.* ­> *Select Alternative JRE*
6. **Sélectionnez Java 8** dans les paramètres d’exécution.
7. Cliquez sur **Run**

---

# **▶ 5. Exécuter le Programme**
1. Cliquez sur **le bouton vert ▶** à côté de `main()`.
2. Attendez que Spark démarre et affiche les résultats.

---

# **6. Résultat Attendu**
```
+----------+---------+---------+---------+---------+-------+-------------+
|       dt |openprice|highprice|lowprice |closeprice|volume|adjcloseprice|
+----------+---------+---------+---------+---------+-------+-------------+
|2023-01-02|  125.02 |  130.05 |  124.52 |  129.43 |200000 |  129.43     |
|2023-01-03|  129.55 |  132.00 |  127.75 |  130.98 |220000 |  130.98     |
|2023-01-04|  131.00 |  135.12 |  130.45 |  134.52 |250000 |  134.52     |
+----------+---------+---------+---------+---------+-------+-------------+
```

---

# **7. Exercice**
1. **Changer le chemin du fichier CSV** en fonction de votre système.
2. **Ajouter une colonne `prix_moyen`** (`(openprice + closeprice) / 2`).
3. **Appliquer un filtre** pour afficher uniquement les actions avec un `volume > 210000`.



# 8. Annexe 1 - Remarques Importantes 

1️⃣ **Version de Maven** :  
   - **Il est impératif d’utiliser Maven 3.9.0**.  
   - **Si vous utilisez une autre version, vous risquez d’avoir des erreurs de compilation**.  
   - Vous pouvez vérifier votre version avec la commande suivante dans le terminal :  
     ```sh
     mvn -version
     ```
   - Si ce n’est pas la bonne version, mettez à jour Maven ou téléchargez **Maven 3.9.0** depuis [Apache Maven](https://maven.apache.org/download.cgi).

---

2️⃣ **Version de Java** :  
   - **Seule la version Java 8 (JDK 1.8) est compatible avec Spark 3.3.0 et Scala 2.12.7.**  
   - **N’utilisez pas Java 11, 17 ou plus, cela entraînera des erreurs de compatibilité**.  
   - Vérifiez votre version de Java avec la commande :  
     ```sh
     java -version
     ```
   - Si ce n’est pas Java 8, vous devez l’installer et le définir comme version active.

---

3️⃣ **Configuration d'IntelliJ IDEA** :  
   - **Dans les paramètres d'exécution du projet, il est obligatoire d’activer "Allow multiple instances"**.  
   - Pour cela :  
     1. **Cliquez sur "Run/Debug Configurations"**.  
     2. **Sélectionnez votre application Spark**.  
     3. **Cochez l’option "Allow multiple instances"**.  



# Annexe 1 - arborescence de notre pom.xml

*Cette arborescence dans notre annexe permet de visualiser clairement la hiérarchie de votre fichier `pom.xml`, y compris les dépendances, les propriétés, les profils Maven et les plugins utilisés.*

```
project
├── modelVersion: 4.0.0
├── groupId: sample
├── artifactId: scala-module-dependency-sample
├── version: 1.0-SNAPSHOT
├── properties
│   ├── encoding: UTF-8
├── profiles
│   ├── profile (id: scala-2.12)
│   │   ├── activation
│   │   │   ├── activeByDefault: true
│   │   ├── properties
│   │   │   ├── scala.version: 2.12.7
│   │   │   ├── scala.compat.version: 2.12
│   │   ├── dependencies
│   │   │   ├── dependency (org.scala-lang:scala-library:${scala.version})
│   │   │   ├── dependency (org.scala-lang.modules:scala-xml_${scala.compat.version}:1.1.1)
│   │   │   ├── dependency (org.scala-lang.modules:scala-parser-combinators_${scala.compat.version}:1.1.1)
│   │   │   ├── dependency (org.scala-lang.modules:scala-swing_${scala.compat.version}:2.0.3)
│   │   │   ├── dependency (org.apache.spark:spark-core_2.12:3.3.0)
│   │   │   ├── dependency (org.apache.spark:spark-sql_2.12:3.3.0)
│   ├── profile (id: scala-2.11)
│   │   ├── properties
│   │   │   ├── scala.version: 2.11.12
│   │   │   ├── scala.compat.version: 2.11
│   │   ├── dependencies
│   │   │   ├── dependency (org.scala-lang:scala-library:${scala.version})
│   │   │   ├── dependency (org.scala-lang.modules:scala-xml_${scala.compat.version}:1.1.1)
│   │   │   ├── dependency (org.scala-lang.modules:scala-parser-combinators_${scala.compat.version}:1.1.1)
│   ├── profile (id: scala-2.10)
│   │   ├── properties
│   │   │   ├── scala.version: 2.10.7
│   │   │   ├── scala.compat.version: 2.10
│   │   ├── dependencies
│   │   │   ├── dependency (org.scala-lang:scala-library:${scala.version})
├── build
│   ├── sourceDirectory: src/main/scala
│   ├── testSourceDirectory: src/test/scala
│   ├── plugins
│   │   ├── plugin (org.apache.maven.plugins:maven-compiler-plugin:3.3)
│   │   ├── plugin (net.alchim31.maven:scala-maven-plugin:3.2.2)
│   │   │   ├── executions
│   │   │   │   ├── execution
│   │   │   │   │   ├── goals
│   │   │   │   │   │   ├── goal: compile
│   │   │   │   │   │   ├── goal: testCompile
│   │   │   ├── configuration
│   │   │   │   ├── args
│   │   │   │   │   ├── arg: -nobootcp
```


