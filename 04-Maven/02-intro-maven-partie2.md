### **Création de la structure du projet - Projet de calculatrice**



## **1. Commandes MAVEN couramment utilisées**  
Maven est un outil de gestion de projet et de construction pour Java. Voici quelques commandes essentielles :

- `mvn archetype:generate` → Générer un projet à partir d'un archétype  
- `mvn compile` → Compiler le code source  
- `mvn test` → Exécuter les tests  
- `mvn package` → Créer un fichier JAR ou WAR  
- `mvn verify` → Vérifier l'intégrité du projet  
- `mvn install` → Installer le package dans le référentiel local  
- `mvn deploy` → Déployer le package dans un référentiel distant  
- `mvn clean` → Supprimer les fichiers temporaires  
- `mvn site` → Générer la documentation du projet  
- `mvn dependency:analyze` → Analyser les dépendances du projet  
- `mvn dependency:update-snapshots` → Mettre à jour les dépendances snapshot  

---

## **2. Création manuelle du projet**  

### **Étapes :**
1. **Créer la structure de dossiers**  
   Exécutez les commandes suivantes dans un terminal :

   ```sh
   mkdir -p CalculatorProject/src/main/java/com/example
   mkdir -p CalculatorProject/src/test/java/com/example
   ```

   Sur Windows (Invite de commandes) :

   ```sh
   mkdir CalculatorProject\src\main\java\com\example
   mkdir CalculatorProject\src\test\java\com\example
   ```

2. **Créer les fichiers essentiels**  
   - `Calculator.java` dans `src/main/java/com/example/`
   - `CalculatorTest.java` dans `src/test/java/com/example/`
   - `pom.xml` à la racine du projet (`CalculatorProject/`)

---

## **3. Création automatique du projet - Utilisation de Maven Archetype**  

Pour générer un projet Maven standard en quelques secondes, utilisez la commande suivante :

```sh
mvn archetype:generate -DgroupId=com.example -DartifactId=calculator -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

### **Explication des paramètres :**
- `-DgroupId=com.example` → Identifiant de groupe (organisation ou namespace)  
- `-DartifactId=calculator` → Nom du projet (nom du dossier racine)  
- `-DarchetypeArtifactId=maven-archetype-quickstart` → Archétype Maven standard pour une application Java  
- `-DinteractiveMode=false` → Désactive le mode interactif pour exécuter la commande sans confirmation  

### **Résultat attendu :**
Après exécution, Maven génère la structure suivante :

```
calculator/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/example/
│   │           └── App.java
│   ├── test/
│   │   └── java/
│   │       └── com/example/
│   │           └── AppTest.java
├── pom.xml
```

Vous pouvez modifier `App.java` et `AppTest.java` pour correspondre à votre projet de calculatrice.

### **Avantages de l'approche Maven :**
✅ Gain de temps  
✅ Structure conforme aux standards  
✅ Facilité de gestion des dépendances  

