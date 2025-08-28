
# 1) Prérequis

* Docker Desktop installé (Windows/Mac) ou Docker Engine (Linux).
* Un terminal (PowerShell, CMD, Terminal macOS, ou bash).

# 2) Option express : REPL Scala immédiat (sans projet)

Ouvrez un terminal dans un dossier de travail (par ex. `~/scala-docker` ou `C:\scala-docker`) puis lancez :

**macOS / Linux**

```bash
docker run -it --rm \
  -v "$PWD":/work -w /work \
  sbtscala/scala-sbt:latest \
  scala
```

**Windows PowerShell**

```powershell
docker run -it --rm `
  -v "${PWD}:/work" -w /work `
  sbtscala/scala-sbt:latest `
  scala
```

Cela télécharge l’image, monte votre dossier courant dans `/work`, et lance le **REPL** (`scala>`).
Test rapide :

```scala
scala> val xs = List(1,2,3,4)
scala> xs.map(_ * 10)
scala> "Bonjour".reverse
```

Tapez `:quit` pour sortir.



# 3) Option projet : créer un projet **sbt** propre

## 3.1 Créer la structure minimale

Dans votre dossier de travail :

```bash
mkdir -p scala-hello/src/main/scala
cd scala-hello
```

Créez **build.sbt** :

```scala
ThisBuild / scalaVersion := "2.13.14"  // vous pouvez choisir 3.x si désiré
name := "scala-hello"
version := "0.1.0"
```

Créez **src/main/scala/Main.scala** :

```scala
object Main {
  def main(args: Array[String]): Unit = {
    println("Bonjour Scala depuis Docker !")
    val data = List(1,2,3,4,5)
    println(s"Carrés: ${data.map(x => x*x)}")
  }
}
```

## 3.2 Lancer une **console sbt** (REPL avec le classpath du projet)

**macOS / Linux**

```bash
docker run -it --rm \
  -v "$PWD":/work -w /work \
  sbtscala/scala-sbt:latest \
  sbt console
```

**Windows PowerShell**

```powershell
docker run -it --rm `
  -v "${PWD}:/work" -w /work `
  sbtscala/scala-sbt:latest `
  sbt console
```

Dans la console sbt (`scala>`), essayez :

```scala
scala> import scala.util.Try
scala> Try(10/2).toOption
scala> List("a","bb","ccc").map(_.length)
:quit
```

## 3.3 Compiler et exécuter votre app

Toujours via Docker :

```bash
# compiler
docker run -it --rm -v "$PWD":/work -w /work sbtscala/scala-sbt:latest sbt compile

# exécuter la classe Main
docker run -it --rm -v "$PWD":/work -w /work sbtscala/scala-sbt:latest sbt "run"
```

PowerShell (même logique, remplacez les guillemets et backticks selon plus haut).

## 3.4 Ajouter des dépendances

Modifiez **build.sbt** :

```scala
libraryDependencies += "org.typelevel" %% "cats-core" % "2.12.0"
```

Puis :

```bash
docker run -it --rm -v "$PWD":/work -w /work sbtscala/scala-sbt:latest sbt update compile
```

## 3.5 Packager un JAR exécutable

Ajoutez le plugin `sbt-assembly` :

Créez **project/plugins.sbt** :

```scala
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "2.3.5")
```

Dans **build.sbt**, définissez la classe principale :

```scala
Compile / mainClass := Some("Main")
```

Assemblez :

```bash
docker run -it --rm -v "$PWD":/work -w /work sbtscala/scala-sbt:latest sbt assembly
```

Le JAR se trouvera dans `target/scala-2.13/scala-hello-assembly-0.1.0.jar`.
Exécution (avec Java sur votre machine ou en Docker) :

```bash
java -jar target/scala-2.13/scala-hello-assembly-0.1.0.jar
```



# 4) Option “tout-en-un” : votre propre **Dockerfile**

Si vous préférez une image figée (mêmes versions à chaque fois), créez un **Dockerfile** à la racine du projet :

```dockerfile
FROM sbtscala/scala-sbt:17.0.13_2.13.14_1.10.5
WORKDIR /app
# Pré-chauffer le cache (facultatif) :
COPY build.sbt ./
RUN sbt update
COPY project ./project
RUN sbt update
COPY . .
CMD ["sbt", "run"]
```

**Build & run** :

```bash
docker build -t scala-hello:dev .
docker run -it --rm -v "$PWD":/app scala-hello:dev
```



# 5) Option **docker compose** (pratique au quotidien)

Créez **compose.yaml** à la racine du projet :

```yaml
services:
  scala:
    image: sbtscala/scala-sbt:latest
    working_dir: /work
    volumes:
      - ./:/work
    tty: true
```

Lancez un shell dans le conteneur :

```bash
docker compose run --rm scala bash
# puis, dans le conteneur :
sbt compile
sbt run
sbt console
```



# 6) Manipulations utiles dans le REPL (rapide)

Dans `sbt console` ou `scala` :

```scala
// Collections
val nums = (1 to 10).toList
nums.filter(_ % 2 == 0).map(n => n*n)

// Pattern matching
def describe(x: Any) = x match {
  case i: Int if i > 0 => s"entier positif: $i"
  case s: String       => s"chaine (${s.length} chars)"
  case _               => "inconnu"
}
describe(42)
describe("Scala")

// Futures (concurrence)
import scala.concurrent._
import ExecutionContext.Implicits.global
val f = Future { Thread.sleep(500); 99 }
Await.result(f, scala.concurrent.duration.Duration.Inf)
```

---

# 7) Travailler aussi **sans sbt** (juste `scalac`/`scala`)

Vous pouvez compiler un fichier Scala simple :

**Hello.scala**

```scala
object Hello { def main(args: Array[String]): Unit = println("Yo Scala!") }
```

**Compiler + exécuter via Docker** :

```bash
docker run -it --rm -v "$PWD":/work -w /work sbtscala/scala-sbt:latest scalac Hello.scala
docker run -it --rm -v "$PWD":/work -w /work sbtscala/scala-sbt:latest scala Hello
```



# 8) Déboguer / Problèmes fréquents

* **Permission denied sur volumes (Linux/macOS)** : essayez `sudo chown -R $USER:$USER .` sur votre dossier, ou ajoutez `:delegated`/`:cached` sur macOS.
* **Windows chemins avec espaces** : entourez les chemins par des guillemets et préférez PowerShell.
* **Versions Scala 3** : remplacez `scalaVersion := "3.3.x"` et adaptez la cible `scala-3.x` dans `target/`.
* **Cache sbt lent** : gardez le conteneur vivant (shell `bash`) pendant la session, ou créez une image personnalisée (section 4).



# 9) Résumé commandes “essentielles”

```bash
# REPL rapide (sans projet)
docker run -it --rm -v "$PWD":/work -w /work sbtscala/scala-sbt:latest scala

# Projet sbt : console, compile, run
docker run -it --rm -v "$PWD":/work -w /work sbtscala/scala-sbt:latest sbt console
docker run -it --rm -v "$PWD":/work -w /work sbtscala/scala-sbt:latest sbt compile
docker run -it --rm -v "$PWD":/work -w /work sbtscala/scala-sbt:latest sbt "run"

# Sans sbt : compiler/exécuter un fichier
docker run -it --rm -v "$PWD":/work -w /work sbtscala/scala-sbt:latest scalac Hello.scala
docker run -it --rm -v "$PWD":/work -w /work sbtscala/scala-sbt:latest scala Hello
```

