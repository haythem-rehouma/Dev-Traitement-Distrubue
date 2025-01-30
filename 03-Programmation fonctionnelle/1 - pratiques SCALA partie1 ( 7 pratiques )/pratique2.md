# PRATIQUE 2 - RDD ET AUTRES OPÉRATIONS DE BASE

## (Section 01/05) FONCTION SAMPLE
```scala
val varsample= sc.parallelize( 1 to 100)
varsample.sample(true, 0.2, 5).collect
varsample.count
```

## (Section 02/05) OPÉRATIONS RELATIONNELLES
```scala
// Transformation union
val rdd1= sc.parallelize( List(1,2,3,4,5))
val rdd2= sc.parallelize( List(1,3,6,7))
rdd1.union(rdd2).collect

// Transformation intersection
rdd1.intersection(rdd2).collect

// Transformation subtract
rdd1.subtract(rdd2).collect
rdd2.subtract(rdd1).collect

// Transformation cartesian
rdd1.collect
rdd2.collect 
rdd1.cartesian(rdd2).collect 

// Transformation distinct
val rdd3 = sc.parallelize( List(1,3,6,3,1,7,8,9))
rdd3.distinct().collect

// Transformation cogroup (comme le full outer join)
val rdd4 = sc.parallelize( Array(("A","1"),("B","2"), ("C","3"), ("D","4") ))
val rdd5 = sc.parallelize( Array(("A","a"),("B","b"), ("C","c"), ("D","d") ))
rdd4.cogroup(rdd5).collect
rdd5.cogroup(rdd4).collect
val rdd6 = sc.parallelize( Array(("A","a"),("C","c")))
rdd4.cogroup(rdd6).collect
```

## (Section 03/05) LES OPÉRATIONS DATA STRUCTURE
```scala
// Transformation keyBy
val rdd1 = sc.parallelize(List("1 val1","2 val2","3 val3","4 val4"))
rdd1.map(x=>(x.split(" ")(0),x.split(" ")(1))).collect
rdd1.keyBy(x=>x.split(" ")(0)).collect
rdd1.map(x=>(x.split(" ")(0),x)).collect
rdd1.map(x=>(x.split(" ")(0),x.split(" ")(1))).collect

// Transformation coalesce
val rdd3 = sc.parallelize(List(1,2,3,4,5,6,7,8,9,10,11),5)
rdd3.glom.collect 
rdd3.coalesce(3).glom.collect  
rdd3.coalesce(7).glom.collect  

// Action saveAsTextFile
val rdd5 = sc.parallelize(List("toto tata titi toto tutu tata"))
val rddsave = rdd5.flatMap(l => l.split(" ")).map(w => (w,1)).reduceByKey(_+_)
rddsave.saveAsTextFile("resultat1")
```

## (Section 04/05) LES OPÉRATIONS DATA STRUCTURE
```scala
// EXERCICE#8. Analyse de Fréquence des Mots avec Spark RDD
val rdd5 = sc.parallelize(List("toto tata titi toto tutu tata"))
val rdd6 = sc.parallelize(List("toto tata", "titi toto", "tutu tata"))
val rdd7 = sc.parallelize(List("toto tata titi", "toto tutu tata"))

rdd5.flatMap(l => l.split(" ")).map(w => (w,1)).reduceByKey(_+_)
rdd6.flatMap(l => l.split(" ")).map(w => (w,1)).reduceByKey(_+_)
rdd7.flatMap(l => l.split(" ")).map(w => (w,1)).reduceByKey(_+_)
```

## (Section 05/05) fullOuterJoin VS cogroup
```scala
// Transformation cogroup (comme le full outer join)
val rdd4 = sc.parallelize(Array(("A","1"),("B","2"), ("C","3"), ("D","4")))
val rdd6 = sc.parallelize(Array(("A","a"),("C","c")))
rdd4.cogroup(rdd6).collect

rdd4.fullOuterJoin(rdd6).collect
rdd4.cogroup(rdd6).collect
```

# Différences:
- La méthode `cogroup` retourne un résultat de type `Array[(String, (Iterable[String], Iterable[String]))]`, où chaque élément du tableau est une paire (clé, (liste de valeurs de rdd4, liste de valeurs de rdd6)).
- La méthode `fullOuterJoin` retourne un résultat de type `Array[(String, (Option[String], Option[String]))]`, où chaque élément du tableau est une paire (clé, (valeur de rdd4, valeur de rdd6)), avec les valeurs de rdd4 et rdd6 enveloppées dans des Options pour gérer les valeurs manquantes.

# Conclusion
Dans cette pratique, nous avons exploré diverses opérations sur les RDD (Resilient Distributed Datasets) dans Apache Spark, notamment les transformations, les actions et les jointures. Les exemples fournis illustrent comment utiliser ces opérations pour manipuler des données distribuées de manière efficace.

# Références :
https://github.com/hrhouma/beginingSpark-part1/blob/main/CoGroupPartie1.md
https://github.com/hrhouma/beginingSpark-part1/blob/main/CoGroupePartie2.md
