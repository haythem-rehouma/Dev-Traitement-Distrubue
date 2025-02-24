# Type-safe? 

Le terme "type-safe" ou "sûreté de type" désigne une propriété fondamentale de certains langages de programmation qui vise à prévenir ou à réduire les erreurs liées à la manipulation des types de données. Dans un langage de programmation type-safe, le compilateur ou l'environnement d'exécution assure que les opérations sont réalisées sur des données de types compatibles, minimisant ainsi les risques d'erreurs à l'exécution.

# 1 - Signification et Importance de la Sûreté de Type

- **Contrôles à la Compilation** : Dans les langages type-safe, beaucoup d'erreurs potentielles liées au type sont détectées lors de la compilation. Cela signifie que le code qui compile est, dans une large mesure, garanti d'être libre de certains types d'erreurs d'exécution qui résultent d'une mauvaise manipulation des types.
  
- **Prévention des Comportements Imprévisibles** : En s'assurant que les opérations sur les données sont effectuées avec des types appropriés, la sûreté de type aide à prévenir des comportements imprévisibles qui peuvent résulter de conversions de types incorrectes ou inattendues, comme accéder à un emplacement mémoire non valide.
  
- **Facilitation du Raisonnement sur le Code** : Quand un langage est type-safe, cela facilite le raisonnement sur le code, car le développeur peut avoir confiance dans les types des données qu'il manipule. Cela réduit le besoin de vérifications de type manuelles et de conversions explicites de type dans le code, rendant le code plus lisible et maintenable.

# 2 -  Exemples de Sûreté de Type

- **Contrôle de Type Statique** : Scala, comme beaucoup de langages fortement typés, effectue un contrôle de type statique, où les types sont vérifiés à la compilation. Par exemple, tenter d'additionner un entier (`Int`) et une chaîne de caractères (`String`) entraînera une erreur de compilation.

- **Inférence de Type** : Scala et d'autres langages modernes utilisent l'inférence de type pour déduire automatiquement les types de certaines expressions, réduisant ainsi la nécessité pour le développeur de spécifier explicitement les types. Cela maintient la sûreté de type tout en allégeant la syntaxe.

- **Génériques** : Les fonctionnalités génériques, comme illustrées dans l'exemple précédent avec `increaseAmount[T]`, permettent de créer des fonctions et des structures de données qui peuvent opérer de manière sûre sur divers types, tant que ces types respectent certaines contraintes. Cela augmente la réutilisabilité du code sans sacrifier la sûreté de type.

# 3 -  Limitations et Considérations

- **Performance** : Parfois, les vérifications de type, surtout si elles sont effectuées dynamiquement à l'exécution, peuvent introduire un surcoût en termes de performance. Cependant, dans les langages à typage statique comme Scala, la plupart de ces vérifications sont effectuées à la compilation.

- **Complexité** : Pour les langages très type-safe avec des systèmes de types avancés, il peut y avoir une courbe d'apprentissage plus raide et une complexité accrue du code, notamment avec l'utilisation intensive de génériques et de types abstraits.

# 4 - Pourquoi type-safe ?

Dans un langage de programmation qui n'est pas type-safe, ou dans un environnement d'exécution qui permet des manipulations peu sûres des types de données, plusieurs formes d'attaques peuvent être menées. Ces attaques exploitent les faiblesses liées à la gestion des types de données pour compromettre la sécurité d'un système. Voici quelques exemples de telles attaques :

### 1. **Dépassement de Tampon (Buffer Overflow)**

Le dépassement de tampon est une des attaques les plus connues et les plus dangereuses dans les langages qui ne sont pas type-safe, comme le C. Elle se produit quand un programme écrit des données au-delà des limites d'un tampon alloué, écrasant la mémoire adjacente. Cela peut permettre à un attaquant de remplacer le code exécutable par du code malveillant, menant à l'exécution de code arbitraire.

### 2. **Injection de Code**

L'injection de code, bien que souvent associée à des failles dans des applications web (comme l'injection SQL), peut également survenir dans des langages non type-safe lorsqu'un attaquant parvient à insérer du code dans un flux de données qui n'est pas correctement validé ou échappé. Cela pourrait mener à l'exécution de code malveillant au sein du processus de l'application.

### 3. **Corruption de Mémoire**

- La corruption de mémoire peut se produire quand un programme accède à une zone de mémoire qu'il n'était pas censé, souvent due à une confusion de types ou à une manipulation directe de la mémoire. 
- Cela peut entraîner des comportements indéterminés, y compris la possibilité pour un attaquant de modifier l'état interne de l'application de manière imprévue.

### 4. **Attaques de Type Confusion**

- Les attaques de type confusion exploitent les scénarios où un programme peut être amené à traiter des données d'un type comme si elles appartenaient à un autre type. 
- Cela peut permettre à un attaquant de manipuler le programme pour qu'il exécute des opérations non sécurisées, comme accéder ou modifier des données qu'il ne devrait pas pouvoir.

### Prévention

La prévention de ces attaques dans un environnement non type-safe repose largement sur des pratiques de programmation sécurisée, telles que :

- **Validation Rigoureuse des Entrées** : S'assurer que toutes les données entrantes sont validées par rapport à un ensemble attendu de critères avant de les utiliser.
- **Limitation de la Manipulation Directe de la Mémoire** : Éviter autant que possible la manipulation directe de la mémoire et privilégier les abstractions fournies par le langage qui sont souvent plus sûres.
- **Utilisation de Fonctions Sécurisées** : Préférer les fonctions qui effectuent des vérifications de limites et d'autres contrôles de sécurité lors de la manipulation de chaînes de caractères et de tampons de données.
- **Sanitisation des Données** : Assurer que toutes les données qui seront interprétées ou exécutées d'une manière ou d'une autre sont correctement nettoyées et échappées pour éviter l'injection de code.

 - - La sûreté de type offerte par des langages comme Scala peut grandement réduire le risque de telles attaques en éliminant de nombreuses erreurs de manipulation de type à la source, grâce à des vérifications de type strictes effectuées à la compilation.
 - - En résumé, la sûreté de type est un aspect crucial de la conception de langages de programmation modernes, offrant un équilibre entre la flexibilité du code et la prévention des erreurs. Elle joue un rôle essentiel dans le développement de logiciels fiables et maintenables.
