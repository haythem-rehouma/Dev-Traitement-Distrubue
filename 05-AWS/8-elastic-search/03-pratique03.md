
### Script Cypher :

```cypher
// Création des cours avec sigles et diplômes pour "Intelligence Artificielle"
CREATE (:cours {sigle: '420-AI01-RO', diplome: 'AEC/DEC/MAITRISE/BAC/DOCTORAT'}),
       (:cours {sigle: '420-AI02-RO', diplome: 'AEC/DEC/MAITRISE/BAC/DOCTORAT'}),
       (:cours {sigle: '420-AI03-RO'}),
       (:cours {sigle: '420-AI04-RO'}),
       (:cours {sigle: '420-AI05-RO'}),
       (:cours {sigle: '420-AI06-RO'});

// Création des professeurs et relations d'enseignement
CREATE (:professeur {matricule: 101, prenom: 'John', nom: 'Smith'})-[:ENSEIGNER]->(:cours {sigle: '420-AI01-RO'}),
       (:professeur {matricule: 102, prenom: 'Emily', nom: 'Johnson'})-[:ENSEIGNER {nbrhrs: 45}]->(:cours {sigle: '420-AI02-RO'}),
       (:professeur {matricule: 103, prenom: 'Michael', nom: 'Williams'})-[:ENSEIGNER]->(:cours {sigle: '420-AI03-RO'}),
       (:professeur {matricule: 104, prenom: 'Sarah', nom: 'Brown'})-[:ENSEIGNER]->(:cours {sigle: '420-AI04-RO'}),
       (:professeur {matricule: 105, prenom: 'Haythem', nom: 'Rehouma'})-[:ENSEIGNER {nbrhrs: 60}]->(:cours {sigle: '420-AI05-RO'});

// Création des relations préalables entre les cours
CREATE (:cours {sigle: '420-AI05-RO'})-[:PREALABLE]->(:cours {sigle: '420-AI01-RO'}),
       (:cours {sigle: '420-AI06-RO'})-[:PREALABLE]->(:cours {sigle: '420-AI02-RO'})-[:PREALABLE]->(:cours {sigle: '420-AI03-RO'});

// Création des relations collègues entre les professeurs dans le domaine de l'Intelligence Artificielle
MATCH (a:professeur {prenom: 'Haythem', nom: 'Rehouma'}), (b:professeur {prenom: 'John', nom: 'Smith'})
MERGE (a)-[r:COLLEGUES {programme: 'Intelligence Artificielle'}]->(b);

MATCH (a:professeur {prenom: 'Emily', nom: 'Johnson'}), (b:professeur {prenom: 'Sarah', nom: 'Brown'})
MERGE (a)-[r:COLLEGUES {programme: 'Intelligence Artificielle'}]->(b);

// Lister tous les nœuds et relations
MATCH (a)-[r]->(b) RETURN a, r, b;

// Unwind des diplômes et ordre des résultats
MATCH (n:cours) WHERE n.sigle = "420-AI01-RO"
UNWIND split(n.diplome, "/") AS element RETURN element ORDER BY element ASC;

// Transformation des parties de sigle avec WITH
MATCH (n:cours)
WITH substring(n.sigle, 0, 3) AS prefixe, substring(n.sigle, 8, 2) AS college
RETURN prefixe, college;

// Lister les relations ENSEIGNER et leurs propriétés
MATCH (a)-[r:ENSEIGNER]->(b) RETURN a.matricule, r.nbrhrs, b.sigle;

// Relations préalables entre les cours
MATCH (a)-[r:PREALABLE]->(b) RETURN a.sigle, r, b.sigle;

// Filtrer les professeurs par prénoms et créer des relations COLLEGUES
MATCH (a:professeur), (b:professeur)
WHERE a.prenom = 'Michael' AND b.prenom = 'Sarah'
CREATE (a)-[r:COLLEGUES {programme: 'Intelligence Artificielle'}]->(b)
RETURN a, r, b;
```

### Détails du script :
1. **Création des cours** : Tous les cours sont créés avec le programme "Intelligence Artificielle" et des sigles spécifiques commençant par "420-AI".
2. **Création des professeurs** : Les noms des professeurs (John, Emily, Michael, Sarah) et "Haythem Rehouma".
3. **Relations `ENSEIGNER`** : Chaque professeur est assigné à enseigner un cours, avec un nombre d'heures spécifié pour certains cours.
4. **Relations `PREALABLE`** : Des relations de prérequis entre différents cours sont créées, structurant ainsi une progression académique.
5. **Relations `COLLEGUES`** : Les professeurs collaborent entre eux sur le programme "Intelligence Artificielle".
6. **Requêtes avancées** : Des requêtes de transformation et d'ordonnancement sont utilisées, notamment `UNWIND`, `WITH`, et des requêtes pour lister les relations avec des filtres.

### Comment l'exécuter ?
Vous pouvez exécuter ce script de la même manière que mentionné précédemment :
1. Copiez ce script dans un fichier texte nommé, par exemple, `script_neo4j_intelligence_artificielle.cypher`.
2. Exécutez-le via **cypher-shell** en utilisant :
   ```bash
   cypher-shell -u neo4j -p <mot_de_passe> < script_neo4j_intelligence_artificielle.cypher
   ```
   Ou utilisez **Neo4j Browser** pour coller et exécuter le script directement.

