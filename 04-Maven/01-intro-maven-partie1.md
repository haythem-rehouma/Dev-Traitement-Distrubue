### **Introduction à MAVEN - Projet de calculatrice**

#### **Contenu**
1. Compiler le projet  
2. Exécuter les tests unitaires  
3. Construire le projet  
4. Nettoyer le projet  
5. Installer le projet dans le référentiel local Maven  
6. Générer un site pour le projet  
7. Explication de MAVEN  
8. Structure du projet  
9. Calculator.java  
10. CalculatorTest.java  
11. pom.xml  
12. Exécution des commandes Maven  

---

### **1. Compiler le projet**
Commande :
```sh
mvn compile
```
Cette commande compile le code source du projet (`src/main/java`) et place les fichiers `.class` générés dans le dossier `target/classes`.

---

### **2. Exécuter les tests unitaires**
Commande :
```sh
mvn test
```
Cette commande exécute les tests unitaires situés dans `src/test/java`.

---

### **3. Construire le projet**
Commande :
```sh
mvn package
```
Cette commande compile le projet, exécute les tests et crée un fichier `.jar` dans `target/`.

---

### **4. Nettoyer le projet**
Commande :
```sh
mvn clean
```
Supprime le dossier `target`, nettoyant ainsi les fichiers générés précédemment.

---

### **5. Installer le projet dans le référentiel local Maven**
Commande :
```sh
mvn install
```
Installe le projet localement pour une réutilisation.

---

### **6. Générer un site pour le projet**
Commande :
```sh
mvn site
```
Génère un site avec documentation et rapports.

---

### **7. Explication de MAVEN**
Maven est un outil de gestion de projet Java qui automatise :
- La compilation
- Les tests
- Le packaging
- Le déploiement

Le fichier `pom.xml` définit la configuration du projet.

---

### **8. Structure du projet**
```
CalculatorProject/
|-- pom.xml
-- src/
    |-- main/
    |   -- java/
    |       -- com/example/
    |           -- Calculator.java
    -- test/
        -- java/
            -- com/example/
                -- CalculatorTest.java
```

---

### **9. Calculator.java**
Fichier :
```java
package com.example;

public class Calculator {
    public int add(int a, int b) { return a + b; }
    public int subtract(int a, int b) { return a - b; }
    public int multiply(int a, int b) { return a * b; }
    public double divide(int a, int b) {
        if (b == 0) throw new IllegalArgumentException("Division by zero.");
        return (double) a / b;
    }
}
```

---

### **10. CalculatorTest.java**
Fichier :
```java
package com.example;
import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {
    private Calculator calculator = new Calculator();

    @Test
    public void testAdd() { Assert.assertEquals(5, calculator.add(2, 3)); }
    @Test
    public void testSubtract() { Assert.assertEquals(1, calculator.subtract(3, 2)); }
    @Test
    public void testMultiply() { Assert.assertEquals(6, calculator.multiply(2, 3)); }
    @Test
    public void testDivide() { Assert.assertEquals(2.0, calculator.divide(4, 2), 0); }
    @Test(expected = IllegalArgumentException.class)
    public void testDivideByZero() { calculator.divide(1, 0); }
}
```

---

### **11. pom.xml**
Fichier :
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>calculator</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

---

### **12. Exécution des commandes Maven**
- Compiler le projet : `mvn compile`
- Exécuter les tests unitaires : `mvn test`
- Construire le projet : `mvn package`
- Nettoyer le projet : `mvn clean`
- Installer dans le référentiel local : `mvn install`
- Générer un site pour le projet : `mvn site`

