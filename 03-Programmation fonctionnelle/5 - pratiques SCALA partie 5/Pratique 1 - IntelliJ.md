### **Tutoriel : Exécution d’un Programme Spark en Scala avec Maven et IntelliJ IDEA**




# Table des Matières : Tutoriel Spark en Scala avec Maven et IntelliJ IDEA

### **1. [Prérequis](#1-prérequis)**  
- Installation et configuration des outils nécessaires.  

### **2. [Création du Projet Maven dans IntelliJ IDEA](#2-création-du-projet-maven-dans-intellij-idea)**  
- **[Créer un projet Maven](#21-créer-un-projet-maven)**  
- **[Configuration du fichier `pom.xml`](#22-configuration-du-fichier-pomxml)**  
- **[Recharger le projet Maven](#23-recharger-le-projet-maven)**  

### **3. [Ajout du Code Scala](#3-ajout-du-code-scala)**  
- **[Création de la classe `StockProcessor.scala`](#31-création-de-la-classe-stockprocessorscala)**  
- **[Gestion des fichiers de données avec RDD et DataFrames](#32-gestion-des-fichiers-de-données-avec-rdd-et-dataframes)**  

### **4. [Configuration de l’Exécution](#4-configuration-de-lexécution)**  
- Paramétrage des options d’exécution dans IntelliJ IDEA.  

### **5. [Exécution du Programme](#5-exécution-du-programme)**  
- Lancement de l’application Spark.  

### **6. [Résultat Attendu](#6-résultat-attendu)**  
- Affichage du tableau de données traitées.  

### **7. [Exercice](#7-exercice)**  
- Manipulation des données et filtrage.  



## **📂 Annexes**
### **🔹 [Annexe 1 : Remarques Importantes](#annexe-1--remarques-importantes)**  
- Versions de Maven, Java et IntelliJ IDEA.  

### **🔹 [Annexe 2 : Arborescence du `pom.xml`](#annexe-2--arborescence-du-pomxml)**  
- Structure détaillée du fichier `pom.xml`.  

### **🔹 [Annexe 3 : Explication détaillée du fichier `pom.xml`](#annexe-3--explication-détaillée-du-fichier-pomxml)**  
- Fonctionnement du `pom.xml` et de ses dépendances.  

### **🔹 [Annexe 4 : Explication détaillée du Code Scala](#annexe-4--explication-détaillée-du-code-scala)**  
- Description des fonctions et des transformations Spark.  

### **🔹 [Annexe 5 : Comparaison `parseStock` vs `parseRDD`](#annexe-5--comparaison-parsestock-vs-parserdd)**  
- Justification de l’utilisation des RDD avant conversion en DataFrame.  

### **🔹 [Annexe 6 : Workflow du Programme Scala avec Spark](#annexe-6--workflow-du-programme-scala-avec-spark)**  
- Diagramme et explication du processus de traitement des données.  

---

### 📌 **Liens Rapides**
- 🔹 [Retour en haut 🔝](#📖-table-des-matières--tutoriel-spark-en-scala-avec-maven-et-intellij-idea)  
- 🔹 [Aller directement à l’exécution ▶](#5-exécution-du-programme)  



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

---
# Annexe 2 - Explication détaillée de chaque section de notre fichier `pom.xml`
---

*Avec cet annexe, **vous comprendrez comment fonctionne un `pom.xml`** pour un projet **Scala avec Spark et Maven dans IntelliJ IDEA** !*



### **📌 Explication des Sections du `pom.xml`**
```
+----------------------+----------------------------------------------------------+
|      Section        |                          Explication                       |
+----------------------+----------------------------------------------------------+
| project             | Décrit le projet Maven et contient toutes les autres      |
|                      | sections nécessaires pour le build et la gestion des     |
|                      | dépendances.                                             |
+----------------------+----------------------------------------------------------+
| modelVersion        | Indique la version du modèle de projet Maven utilisé.     |
|                      | Ici, c'est la version `4.0.0` qui est standard.          |
+----------------------+----------------------------------------------------------+
| groupId             | Identifie le groupe du projet. C'est une sorte de         |
|                      | "namespace". Ici, `sample` est défini.                   |
+----------------------+----------------------------------------------------------+
| artifactId          | Nom unique du projet dans le groupe. Ici,                 |
|                      | `scala-module-dependency-sample` est défini.             |
+----------------------+----------------------------------------------------------+
| version             | Version actuelle du projet. Ici, `1.0-SNAPSHOT` indique   |
|                      | qu’il s’agit d’une version en développement.             |
+----------------------+----------------------------------------------------------+
| properties          | Contient des variables globales pour le projet Maven.     |
|                      | Exemple : `encoding=UTF-8` pour gérer l’encodage.        |
+----------------------+----------------------------------------------------------+
| profiles            | Permet de définir plusieurs configurations pour un même   |
|                      | projet. Chaque `profile` peut contenir des dépendances   |
|                      | différentes selon les besoins.                           |
+----------------------+----------------------------------------------------------+
| profile scala-2.12  | Configuration spécifique pour Scala 2.12.7.               |
|                      | Il définit les dépendances pour cette version.           |
+----------------------+----------------------------------------------------------+
| profile scala-2.11  | Configuration spécifique pour Scala 2.11.12.              |
|                      | Il définit ses propres dépendances.                      |
+----------------------+----------------------------------------------------------+
| profile scala-2.10  | Configuration spécifique pour Scala 2.10.7.               |
|                      | Il définit ses propres dépendances.                      |
+----------------------+----------------------------------------------------------+
| dependencies        | Liste des bibliothèques nécessaires pour exécuter le code.|
|                      | Exemple : Spark, Scala standard, XML, Parser, etc.      |
+----------------------+----------------------------------------------------------+
| build              | Contient les instructions pour compiler et exécuter le    |
|                      | projet. Il inclut des plugins qui facilitent le travail.|
+----------------------+----------------------------------------------------------+
| sourceDirectory     | Définit où se trouvent les fichiers Scala.                |
|                      | Ici, `src/main/scala` est défini comme source.          |
+----------------------+----------------------------------------------------------+
| testSourceDirectory | Définit où se trouvent les fichiers de tests Scala.      |
|                      | Ici, `src/test/scala` est défini.                        |
+----------------------+----------------------------------------------------------+
| plugins             | Liste des outils Maven qui aident au processus de build.  |
+----------------------+----------------------------------------------------------+
| maven-compiler-plugin | Plugin pour compiler le code Java/Scala dans le projet. |
|                      | Ici, la version utilisée est `3.3`.                      |
+----------------------+----------------------------------------------------------+
| scala-maven-plugin  | Plugin qui permet de compiler et exécuter du Scala       |
|                      | dans un projet Maven. Version `3.2.2` utilisée ici.      |
+----------------------+----------------------------------------------------------+
| executions         | Définit quelles tâches Maven doit exécuter automatiquement.|
|                      | Exemple : compilation et test du code Scala.            |
+----------------------+----------------------------------------------------------+
| goals               | Liste des actions à exécuter lors du build.               |
|                      | Ici : `compile` (compiler le code), `testCompile` (tests).|
+----------------------+----------------------------------------------------------+
| configuration       | Contient des paramètres avancés pour les plugins.         |
|                      | Exemple : `-nobootcp` pour éviter certains conflits.    |
+----------------------+----------------------------------------------------------+
```

---

### **🛠 Détails des Versions Scala utilisées**
```
+------------+------------------------------+--------------------------+
| Version    | Compatibilité avec Spark     | Commentaire               |
+------------+------------------------------+--------------------------+
| Scala 2.12 | Compatible avec Spark 3.3.0  | Utilisée par défaut       |
+------------+------------------------------+--------------------------+
| Scala 2.11 | Ancienne version supportée   | Nécessaire pour certains  |
|            | mais obsolète pour Spark 3.x | projets legacy            |
+------------+------------------------------+--------------------------+
| Scala 2.10 | Très ancienne version        | Rarement utilisée         |
+------------+------------------------------+--------------------------+
```

---

### **🚀 Explication des Profils (`profiles`)**
Les **profils Maven** permettent d’avoir **différentes configurations** pour un même projet. Ici, trois profils sont définis :
1. **Scala 2.12 (Par défaut)** :  
   - Active les dépendances pour Scala 2.12.7.  
   - Utilise Spark 3.3.0.  

2. **Scala 2.11** :  
   - Charge les dépendances pour Scala 2.11.12.  
   - Peut être utile pour des projets plus anciens.  

3. **Scala 2.10** :  
   - Définit les bibliothèques compatibles avec Scala 2.10.7.  
   - Très peu utilisé aujourd’hui.  

---

### **📦 Explication des Dépendances (`dependencies`)**
```
+------------------------------------------+--------------------------------------------+
| Dépendance                               | Explication                                |
+------------------------------------------+--------------------------------------------+
| org.scala-lang:scala-library             | Bibliothèque standard Scala                |
|                                          | Permet d'exécuter du code Scala            |
+------------------------------------------+--------------------------------------------+
| org.scala-lang.modules:scala-xml         | Gestion des fichiers XML en Scala         |
+------------------------------------------+--------------------------------------------+
| org.scala-lang.modules:scala-parser-...  | Librairie pour parser du texte            |
+------------------------------------------+--------------------------------------------+
| org.apache.spark:spark-core_2.12         | Noyau de Spark pour Scala 2.12            |
+------------------------------------------+--------------------------------------------+
| org.apache.spark:spark-sql_2.12          | Bibliothèque SQL de Spark                 |
+------------------------------------------+--------------------------------------------+
```

---

### **🛠 Explication des Plugins (`plugins`)**
1. **maven-compiler-plugin**  
   - Utilisé pour compiler le code Java et Scala.  
   - Version `3.3` utilisée ici.  

2. **scala-maven-plugin**  
   - Permet de compiler du **Scala** avec **Maven**.  
   - Version `3.2.2`.  
   - Définit que **`compile` et `testCompile`** doivent être exécutés.  

---

### **🎯 Exécutions (`executions`)**
L’exécution définit **quelles tâches** Maven doit réaliser lors du build :
- **compile** : Compile le code Scala.  
- **testCompile** : Compile les tests Scala.  

---

### **⚙ Configuration**
Dans la section **configuration**, l’argument `-nobootcp` est utilisé pour éviter certains conflits de classpath avec Scala.

---

### **📌 Récapitulatif**
- **Le projet utilise trois versions de Scala (2.12, 2.11, 2.10)** mais **Scala 2.12 est activé par défaut**.  
- **Les dépendances Spark et Scala sont chargées dynamiquement selon le profil sélectionné**.  
- **Les plugins permettent de compiler et exécuter du code Scala avec Maven**.  
- **L’exécution de Maven compile le code et les tests Scala**.  


# Annexe 3 - Expliaction détaillée du code



```scala
// Importation des bibliothèques nécessaires pour utiliser Spark et manipuler les données
import org.apache.spark.sql.{SparkSession, DataFrame}  // SparkSession est utilisé pour créer une session Spark, DataFrame pour manipuler les données
import org.apache.spark.rdd.RDD  // RDD (Resilient Distributed Dataset) est une abstraction de données distribuées dans Spark
import org.apache.spark.sql.Encoders  // Utilisé pour convertir des objets Scala en format compatible avec les DataFrames

// Définition d'une case class `Stock` qui représente un enregistrement d'action boursière
case class Stock(
                  dt: String,           // Date de l'enregistrement
                  openprice: Double,    // Prix d'ouverture
                  highprice: Double,    // Prix le plus haut
                  lowprice: Double,     // Prix le plus bas
                  closeprice: Double,   // Prix de clôture
                  volume: Double,       // Volume échangé
                  adjcloseprice: Double // Prix ajusté de clôture
                )

// Définition d'un objet `StockProcessor` contenant les méthodes pour analyser et charger les données
object StockProcessor {

  // Fonction qui prend une ligne CSV (sous forme de String) et la convertit en objet `Stock`
  def parseStock(str: String): Option[Stock] = {
    val line = str.split(",")  // Sépare la ligne en utilisant la virgule comme séparateur

    try {
      Some(Stock(   // Essaie de créer un objet Stock avec les valeurs extraites
        line(0),            // Date (dt)
        line(1).toDouble,   // Prix d'ouverture (openprice)
        line(2).toDouble,   // Prix le plus haut (highprice)
        line(3).toDouble,   // Prix le plus bas (lowprice)
        line(4).toDouble,   // Prix de clôture (closeprice)
        line(5).toDouble,   // Volume échangé (volume)
        line(6).toDouble    // Prix ajusté de clôture (adjcloseprice)
      ))
    } catch {
      case e: Exception =>  // En cas d'erreur lors de la conversion
        println(s"Erreur de parsing pour la ligne : $str -> ${e.getMessage}") // Affiche un message d'erreur
        None  // Retourne `None` pour ignorer cette ligne incorrecte
    }
  }

  // Fonction qui prend un RDD[String] et le convertit en un RDD[Stock]
  def parseRDD(rdd: RDD[String]): RDD[Stock] = {
    val header = rdd.first() // Récupération de la première ligne du fichier (l'en-tête)
    rdd
      .filter(_ != header) // Supprime la première ligne (l'en-tête) pour ne pas la traiter
      .flatMap(parseStock) // Applique la fonction `parseStock` à chaque ligne et ignore les erreurs
      .cache() // Met en cache le RDD pour améliorer les performances
  }

  def main(args: Array[String]): Unit = {
    // Création de la session Spark (point d'entrée pour utiliser Spark)
    val spark = SparkSession.builder()
      .appName("Stock Analysis") // Nom de l'application Spark
      .master("local[*]") // Mode local (utilise tous les cœurs disponibles de la machine locale)
      .getOrCreate() // Crée ou récupère une session Spark existante

    import spark.implicits._ // Importation implicite pour convertir un RDD en DataFrame

    // Charger le fichier CSV et le transformer en DataFrame
    val stocksAAPLDF: DataFrame = parseRDD(spark.sparkContext.textFile("C:/Users/rehou/Downloads/AAPL.csv"))
      .toDF() // Conversion du RDD[Stock] en DataFrame
      .cache() // Met en cache les données pour éviter de relire le fichier à chaque requête

    // Affichage des premières lignes du DataFrame sous forme de tableau
    stocksAAPLDF.show()
  }
}
```

---

### **Explication Générale du Code**
1. **Importation des bibliothèques** :  
   - SparkSession est utilisé pour créer une session Spark.
   - DataFrame est une abstraction de Spark SQL pour manipuler les données tabulaires.
   - RDD (Resilient Distributed Dataset) est utilisé pour la gestion des données en mémoire et leur traitement distribué.

2. **Définition de la case class `Stock`** :  
   - Permet de structurer les données en objets Scala.
   - Facilite leur manipulation et leur conversion en DataFrame.

3. **Fonction `parseStock(str: String)`** :  
   - Convertit une ligne de texte CSV en objet `Stock`.
   - Gère les erreurs en cas de format incorrect.

4. **Fonction `parseRDD(rdd: RDD[String])`** :  
   - Lit un fichier CSV sous forme de `RDD[String]`.
   - Supprime l'en-tête du fichier.
   - Convertit chaque ligne en objet `Stock`.
   - Met en cache les données pour améliorer les performances.

5. **Fonction `main(args: Array[String])`** :  
   - Crée une session Spark.
   - Charge les données d'un fichier CSV.
   - Convertit le fichier en DataFrame.
   - Affiche les premières lignes du DataFrame.

---

### ** Points Clés à Retenir**
- **RDD vs DataFrame** :  
  - Un **RDD** est une collection distribuée d'objets Scala.
  - Un **DataFrame** est une table optimisée pour Spark SQL (plus performant que les RDDs).

- **`cache()`** :  
  - Évite la relecture des données depuis le fichier en stockant les résultats en mémoire.

- **Utilisation de `Option[Stock]`** :  
  - Permet de gérer les erreurs de parsing en évitant d'inclure des lignes corrompues dans les résultats.

# Annexe 4 -  parseStock vs parseRDD

Nous ne sommes pas obligés de transformer en RDD avant de créer un DataFrame dans Spark. On le fait ici pour contrôler manuellement le parsing des données avant de les convertir en DataFrame.


Non, nous ne sommes **pas obligés** de transformer en **RDD** avant de créer un **DataFrame** dans Spark. On le fait ici pour **contrôler manuellement le parsing des données** avant de les convertir en **DataFrame**.



## ** Explication du Workflow**
L’objectif est de **charger un fichier CSV** en Spark et de le convertir en un format utilisable pour l’analyse des données.

### ** Étapes du Workflow**
1. **Lecture du fichier CSV brut** :  
   - Spark charge les données sous forme de **RDD[String]** (chaque ligne est une chaîne de caractères).
   
2. **Traitement des erreurs via `parseStock(str: String)`** :  
   - Convertit une ligne CSV en un objet `Stock`.  
   - Ignore les lignes mal formatées grâce à `Option[Stock]`.  

3. **Transformation complète avec `parseRDD(rdd: RDD[String])`** :  
   - Applique `parseStock` sur tout le fichier.
   - Supprime l’en-tête du fichier.
   - Filtre les erreurs.
   - Convertit ensuite en DataFrame.

---

## ** Pourquoi `parseStock(str: String)` ?**
- **Problème** : Quand on charge un CSV, les données sont brutes (du texte).  
- **Solution** : `parseStock` transforme une ligne CSV (`String`) en **objet structuré `Stock`**.
- **Gestion d’erreurs** :  
  - Si une ligne a **des valeurs invalides** (`"abc"` au lieu de `12.34`), elle est ignorée **au lieu de faire planter Spark**.
  - Cela évite d'avoir **des erreurs de parsing massives** si le fichier contient des données incorrectes.

### ** Exemple d’Utilisation**
```scala
val ligne = "2024-02-25,100.5,105.0,98.3,104.2,200000,104.2"
val stock = parseStock(ligne)
println(stock)  
// Résultat : Some(Stock(2024-02-25,100.5,105.0,98.3,104.2,200000,104.2))
```
Mais si on passe une ligne invalide :
```scala
val ligneErronee = "2024-02-25,100.5,abc,98.3,104.2,200000,104.2"
val stockErrone = parseStock(ligneErronee)
println(stockErrone)  
// Résultat : None (et affiche un message d'erreur)
```
Cela **évite que Spark plante** à cause d'une seule erreur.

---

## ** Pourquoi `parseRDD(rdd: RDD[String])` ?**
- **Problème** : Spark charge un CSV comme un **RDD de Strings**.  
- **Solution** : `parseRDD` applique `parseStock` **à toutes les lignes du fichier**.
- **Gestion de l’en-tête** :  
  - Supprime la **première ligne** qui contient `"Date,Open,High,Low,Close,Volume,AdjClose"` (inutile dans le traitement).
- **Améliore les performances** :  
  - **RDD caché (`.cache()`)** :  
    - Stocke les données en mémoire pour éviter **de relire le fichier plusieurs fois**.

### ** Exemple d’Utilisation**
```scala
val rddBrut = spark.sparkContext.parallelize(Seq(
  "Date,Open,High,Low,Close,Volume,AdjClose",  // En-tête à supprimer
  "2024-02-25,100.5,105.0,98.3,104.2,200000,104.2",
  "2024-02-26,102.0,106.5,100.0,105.5,180000,105.5",
  "2024-02-27,abc,107.0,101.0,106.0,210000,106.0"  // Erreur ici !
))

val rddStock = parseRDD(rddBrut)
rddStock.collect().foreach(println)
```
**Sortie :**
```
Stock(2024-02-25,100.5,105.0,98.3,104.2,200000,104.2)
Stock(2024-02-26,102.0,106.5,100.0,105.5,180000,105.5)
Erreur de parsing pour la ligne : 2024-02-27,abc,107.0,101.0,106.0,210000,106.0 -> For input string: "abc"
```
👉 La **ligne invalide est ignorée** sans faire crasher Spark !

---

## ** Conclusion : Pourquoi ce Workflow ?**
| Étape                 | Pourquoi ?                                                   |
|----------------------|-----------------------------------------------------------|
| **1. Lire un fichier CSV**  | Spark traite les lignes comme du texte brut (`RDD[String]`). |
| **2. `parseStock`**         | Transforme **chaque ligne** en un objet `Stock` structuré.  |
| **3. `parseRDD`**           | - Supprime l’en-tête <br>- Gère les erreurs <br>- Transforme en `RDD[Stock]` |
| **4. Convertir en DataFrame** | Permet d’utiliser Spark SQL (`toDF()`).                           |

👉 **On transforme en RDD pour gérer les erreurs et structurer les données avant de les convertir en DataFrame !** 🚀



<a name="annexe-6--workflow-du-programme-scala-avec-spark"></a>


---
# Annexe 6 -  Workflow du Programme Scala avec Spark
---


```
+---------------------------+
|   Définition de la classe |
|        Stock.scala        |
+---------------------------+
         ⬇ (Structure de données)
+------------------------------------------------------+
| case class Stock(dt, openprice, highprice, lowprice,|
|                 closeprice, volume, adjcloseprice)  |
| - Représente une ligne du fichier CSV              |
| - Facilite la conversion en DataFrame               |
+------------------------------------------------------+
         ⬇ (Transformation du fichier CSV en RDD)
+-----------------------------------------------+
|  Lecture du fichier CSV brut en RDD[String]  |
|  → Chaque ligne est une String               |
|                                               |
|  Exemple:                                     |
|  "Date,Open,High,Low,Close,Volume,AdjClose"  |  <- En-tête (à ignorer)
|  "2024-02-25,100.5,105.0,98.3,104.2,200000,104.2" |
|  "2024-02-26,102.0,106.5,100.0,105.5,180000,105.5" |
+-----------------------------------------------+
         ⬇ (Nettoyage et Parsing des données)
+-----------------------------------------------+
|  Fonction parseStock(str: String)            |
|  → Transforme une ligne CSV en objet Stock   |
|  → Gère les erreurs (ignore les lignes invalides) |
|                                               |
|  Exemple :                                    |
|  Entrée : "2024-02-25,100.5,105.0,98.3,104.2,200000,104.2"  |
|  Sortie : Stock(2024-02-25,100.5,105.0,98.3,104.2,200000,104.2) |
+-----------------------------------------------+
         ⬇ (Application du parsing à tout le fichier)
+-----------------------------------------------+
|  Fonction parseRDD(rdd: RDD[String])         |
|  → Supprime l'en-tête du fichier CSV         |
|  → Applique parseStock à toutes les lignes   |
|  → Ignore les lignes incorrectes             |
|  → Renvoie un RDD[Stock]                     |
|                                               |
|  Exemple :                                    |
|  Entrée : RDD[String] (chaque ligne du CSV)  |
|  Sortie : RDD[Stock] (objets Stock bien structurés) |
+-----------------------------------------------+
         ⬇ (Conversion en DataFrame)
+-----------------------------------------------+
|  Transformation du RDD[Stock] en DataFrame   |
|  → Permet d'utiliser Spark SQL               |
|  → Optimisé pour les requêtes rapides        |
|                                               |
|  Exemple :                                    |
|  Entrée : RDD[Stock]                          |
|  Sortie : DataFrame                           |
|                                               |
|  +----------+---------+---------+--------+   |
|  |    dt    |openprice|highprice|lowprice|   |
|  +----------+---------+---------+--------+   |
|  |2024-02-25|  100.5  |  105.0  |  98.3  |   |
|  |2024-02-26|  102.0  |  106.5  | 100.0  |   |
|  +----------+---------+---------+--------+   |
+-----------------------------------------------+
         ⬇ (Affichage des résultats)
+-----------------------------------------------+
|  stocksAAPLDF.show()                         |
|  → Affiche les premières lignes du DataFrame |
+-----------------------------------------------+
```

---

### **🛠 Résumé en Étapes**
| **Étape** | **Explication** |
|-----------|----------------|
| **1. Définition de `Stock`** | Crée une classe Scala qui structure les données |
| **2. Lecture du CSV** | Charge un fichier sous forme de texte brut (`RDD[String]`) |
| **3. `parseStock(str: String)`** | Transforme une ligne en objet `Stock` et gère les erreurs |
| **4. `parseRDD(rdd: RDD[String])`** | Supprime l’en-tête et applique `parseStock` à tout le fichier |
| **5. Conversion en DataFrame** | Convertit `RDD[Stock]` en `DataFrame` optimisé |
| **6. Affichage des données** | Affiche les 20 premières lignes avec `show()` |

---

### **Pourquoi ce Workflow ?**
- **RDD permet de contrôler les erreurs** avant la conversion en DataFrame.
- **Le parsing manuel** (`parseStock`) évite que Spark plante si une ligne est invalide.
- **Les DataFrames sont plus rapides et optimisés pour Spark SQL**.

