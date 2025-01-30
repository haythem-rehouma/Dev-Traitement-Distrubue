### Apache Spark Streaming 

---

🚀 **Introduction à Apache Spark Streaming**

Bienvenue dans ce tutoriel interactif sur Apache Spark Streaming ! Nous allons plonger dans le monde fascinant du traitement des flux de données en temps réel. Préparez-vous à transformer des flots de données en informations précieuses avec Apache Spark. 🌟

---

### 🛠 Prérequis

Avant de commencer, assurez-vous d'avoir :

1. **Apache Spark** : Installé et configuré sur votre machine. Spark est le moteur qui alimente nos explorations de données.
2. **Netcat (ncat)** : Outil pour tester des flux de données. Il nous permettra d'envoyer des données à notre application Spark en temps réel.

---

### 📖 Guide Pas à Pas

#### 🔹 **Terminal 1: Configuration de Spark Streaming**

```scala
// 🌟 Importations pour commencer
import org.apache.spark._
import org.apache.spark.streaming._

// 🎩 Création du contexte de streaming - Votre baguette magique pour les données en temps réel
val ssc = new StreamingContext(sc, Seconds(3))

// 📡 Écoute des données sur localhost:9988 - Où la magie commence
val lines = ssc.socketTextStream("localhost", 9988)

// 📖 Transformation des lignes en mots - Découper les données en pièces compréhensibles
val words = lines.flatMap(_.split(" "))

// 🔢 Comptage des mots - Trouvez la valeur dans le chaos
val pairs = words.map(word => (word, 1))
val wordCounts = pairs.reduceByKey(_ + _)

// 👀 Affichage des résultats - Voyez ce que vous avez accompli
wordCounts.print()

// 🚀 Lancement du traitement - Mettez votre casque, c'est parti !
ssc.start()
```

---

#### 🔹 **Terminal 2: Envoi de Données avec Netcat**

```bash
ncat -lk 9988
```
ou
```bash
nc -lk 9988 
```
# Lien de téléchargement de ncat ou nc via le site de nmap : https://nmap.org/ncat/ 

📝 **Note :** C'est ici que vous pouvez écrire les mots qui seront ensuite traités par votre application Spark Streaming. Chaque ligne que vous entrez sera envoyée à Spark, qui comptera les mots en temps réel.

---

### 🎨 Résumé

# 🔹 **Terminal 1: Programme scala**

```scala
import org.apache.spark._
import org.apache.spark.streaming._
val ssc = new StreamingContext(sc, Seconds(3))
val lines = ssc.socketTextStream("localhost", 9988)
val words = lines.flatMap(_.split(" "))
// Count each word in each batch
val pairs = words.map(word => (word, 1))
val wordCounts = pairs.reduceByKey(_ + _)
wordCounts.print()
// Start the computation
ssc.start()
```
# 🔹 **Terminal 2: Envoi de Données avec Netcat**

```bash
ncat -lk 9988
```
ou
```bash
nc -lk 9988 
```


Bon travail ! 🚀
