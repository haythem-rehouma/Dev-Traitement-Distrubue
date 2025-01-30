# SPARK SQL

## PARTIE 1 AVEC SCALA 
### RDD => DataFrame => DataFrame SQL (SPARK SQL)

Utilisez `spark-shell`

```scala
val rdd = sc.parallelize(List((1,"toto","yoyo"),(2,"titi","jiji"),(3,"tata","gogo"),(4,"tutu","nono")))
val dataframe = rdd.toDF("id","nom","prenom")
dataframe.show()
dataframe.createOrReplaceTempView("personnes")
val dataframeSQL = spark.sql("select * from personnes")
dataframeSQL.show
```

## PARTIE 2 AVEC PYSPARK 
### RDD => DataFrame => DataFrame SQL (SPARK SQL)

Utilisez GOOGLE COLAB OU NOTEBOOK DATABRICKS

```python
!pip install pyspark
import pyspark
from pyspark import SparkContext
from pyspark.sql import SparkSession

sc.stop()
sc = SparkContext()
spark = SparkSession.builder.master("local").appName("exemple1").getOrCreate()

x=[(1,'toto','yoyo'),(2,'titi','jiji'),(3,'tata','gogo'),(4,'tutu','nono')]
rdd = sc.parallelize(x)
dataframe = rdd.toDF(("id","nom","prenom"))
dataframe.show()
dataframe.createOrReplaceTempView("personnes")
dataframeSQL = spark.sql("select * from personnes where id= 1")
dataframeSQL.show()
spark.sql("select * from personnes where id= 1").show()
```

## PARTIE 3 - SUITE SPARK SQL

```scala
val rdd = sc.parallelize(List((1,"toto","yoyo"),(2,"titi","jiji"),(3,"tata","gogo"),(4,"tutu","nono")))
val dataframe = rdd.toDF("id","nom","prenom")
dataframe.show()
dataframe.createOrReplaceTempView("personnes")
val dataframeSQL = spark.sql("select * from personnes")

spark.sql("select * from personnes").show()
spark.sql("select * from personnes where id =1").show()
spark.sql("select count(*) from personnes").show()
spark.sql("select * from personnes order by nom asc").show()
spark.sql("select * from personnes order by nom desc").show()
```
## Questions : 

1. Quelle est la différence entre PySpark et Spark SQL, et quel est le langage natif utilisé par Spark ?
2. Pouvez-vous comparer les RDD, les DataFrames et les DataSets dans Spark en termes de fonctionnalités et de transformations disponibles ?
