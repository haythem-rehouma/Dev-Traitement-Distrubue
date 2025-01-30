# Tutoriel d'Analyse de Sentiment avec PySpark

Ce tutoriel vous guidera à travers les étapes nécessaires pour configurer et exécuter une analyse de sentiment en temps réel des tweets en utilisant PySpark. Nous utiliserons un script Python nommé `sentiment_analysis.py` pour analyser les sentiments des tweets en fonction de leur contenu.

## Prérequis

- Apache Spark installé sur votre machine.
- Python et PySpark configurés.
- Accès à un terminal ou une ligne de commande.
- Le fichier `twitter_sentiments.csv`, qui contient les données des tweets pour entraîner notre modèle.

## Étape 1: Télécharger le Jeu de Données

1. Téléchargez le fichier `twitter_sentiments.csv`. Ce fichier contient des exemples de tweets avec des sentiments labellisés que nous utiliserons pour entraîner notre modèle.
   
## Étape 2: Préparer le Script Python

Copiez le script suivant dans un fichier nommé `sentiment_analysis.py`. Ce script définit le pipeline de traitement des données, le modèle d'apprentissage automatique et le streaming en temps réel pour l'analyse de sentiment.

```python
# Importation des bibliothèques nécessaires
from pyspark import SparkContext
from pyspark.sql.session import SparkSession
from pyspark.streaming import StreamingContext
import pyspark.sql.types as tp
from pyspark.ml import Pipeline
from pyspark.ml.feature import RegexTokenizer, StopWordsRemover, Word2Vec
from pyspark.ml.classification import LogisticRegression
from pyspark.sql import Row

def get_prediction(tweet_text):
    try:
        # Suppression des tweets vides
        tweet_text = tweet_text.filter(lambda x: len(x) > 0)
        # Création du DataFrame avec chaque ligne contenant un texte de tweet
        rowRdd = tweet_text.map(lambda w: Row(tweet=w))
        wordsDataFrame = spark.createDataFrame(rowRdd)
        # Obtention des sentiments pour chaque ligne
        pipelineFit.transform(wordsDataFrame).select('tweet', 'prediction').show()
    except:
        print('Pas de données')

if __name__ == "__main__":
    sc = SparkContext(appName="PySparkShell")
    spark = SparkSession(sc)
    
    # Définition du schéma
    my_schema = tp.StructType([
        tp.StructField(name='id', dataType=tp.IntegerType(), nullable=True),
        tp.StructField(name='label', dataType=tp.IntegerType(), nullable=True),
        tp.StructField(name='tweet', dataType=tp.StringType(), nullable=True)    
    ])
    
    # Lecture du jeu de données
    print('\nLecture du jeu de données...\n')
    my_data = spark.read.csv('chemin/vers/twitter_sentiments.csv', schema=my_schema, header=True)
    my_data.show(2)

    my_data.printSchema()
    
    # Définition des étapes du pipeline
    stage_1 = RegexTokenizer(inputCol='tweet', outputCol='tokens', pattern='\\W')
    stage_2 = StopWordsRemover(inputCol='tokens', outputCol='filtered_words')
    stage_3 = Word2Vec(inputCol='filtered_words', outputCol='vector', vectorSize=100)
    model = LogisticRegression(featuresCol='vector', labelCol='label')
    
    pipeline = Pipeline(stages=[stage_1, stage_2, stage_3, model])
    
    # Entraînement du modèle
    print('Entraînement du modèle...\n')
    pipelineFit = pipeline.fit(my_data)

    print('Modèle entraîné. En attente de données...\n')
    ssc = StreamingContext(sc, batchDuration=3)
    lines = ssc.socketTextStream("localhost", 9999)
    words = lines.flatMap(lambda line: line.split('TWEET_APP'))

    words.foreachRDD(get_prediction)

    ssc.start()             # Démarrage du calcul
    ssc.awaitTermination()  # Attente de la fin du calcul
```

Assurez-vous de remplacer `chemin/vers/twitter_sentiments.csv` par le chemin réel du fichier `twitter_sentiments.csv` sur votre machine.

## Étape 3: Exécuter le Script

Ouvrez un terminal et naviguez vers le répertoire contenant `sentiment_analysis.py`. Exécutez le script en utilisant la commande suivante:

```
spark-submit sentiment_analysis.py localhost 9999
```

## Étape 4: Envoyer des Données de Test via Netcat

Ouvrez un autre terminal et utilisez `ncat` pour écouter sur le port `9999` et envoyer des tweets de test au script. Si `ncat` n'est

 pas installé, vous pouvez l'utiliser en installant le package `nmap`.

```
ncat -lk 9999
```

Vous pouvez tester ces mots : 

love trump media war obama Arianna Huffington usa canada president religion racism white 

Dans ce terminal, vous pouvez maintenant taper des tweets et appuyer sur Entrée pour les envoyer au script `sentiment_analysis.py` pour l'analyse.

## Conclusion

Ce tutoriel vous a guidé à travers la configuration d'un environnement d'analyse de sentiment en temps réel pour les tweets en utilisant PySpark. En suivant ces étapes, vous pouvez étendre ce projet pour analyser des données de streaming en temps réel et obtenir des insights précieux sur les sentiments exprimés dans les données de tweet.
