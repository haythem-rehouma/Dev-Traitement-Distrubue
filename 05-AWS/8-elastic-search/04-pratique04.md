### Requêtes pour interroger les professeurs

#### 1. **Lister tous les professeurs** :
```cypher
MATCH (p:professeur) RETURN p;
```
Cette requête retournera tous les nœuds avec l'étiquette `professeur`.

#### 2. **Lister les professeurs avec des propriétés spécifiques** :
Par exemple, si vous voulez afficher les prénoms et noms de tous les professeurs :
```cypher
MATCH (p:professeur) RETURN p.prenom, p.nom;
```

#### 3. **Trouver un professeur par nom** :
Pour trouver un professeur avec un nom ou un prénom spécifique, vous pouvez utiliser une clause `WHERE`. Par exemple, pour trouver "Haythem Rehouma" :
```cypher
MATCH (p:professeur)
WHERE p.prenom = 'Haythem' AND p.nom = 'Rehouma'
RETURN p;
```

#### 4. **Lister les professeurs et leurs cours** :
Vous pouvez récupérer les professeurs et les cours qu'ils enseignent en interrogeant les relations `ENSEIGNER` :
```cypher
MATCH (p:professeur)-[r:ENSEIGNER]->(c:cours)
RETURN p.prenom, p.nom, c.sigle;
```
Cette requête retournera le prénom, le nom du professeur, ainsi que le sigle des cours qu'il enseigne.

#### 5. **Lister les relations entre collègues** :
Pour récupérer les relations entre les professeurs qui sont `COLLEGUES` :
```cypher
MATCH (p1:professeur)-[r:COLLEGUES]->(p2:professeur)
RETURN p1.prenom, p1.nom, p2.prenom, p2.nom, r.programme;
```
Cela retournera les prénoms et noms des collègues, ainsi que le programme associé à la relation de collègues.

---

### Suppression de professeurs

#### 1. **Supprimer un professeur spécifique** :
Si vous voulez supprimer un professeur spécifique (par exemple, "Haythem Rehouma"), vous pouvez d'abord vérifier son existence, puis le supprimer :
```cypher
MATCH (p:professeur)
WHERE p.prenom = 'Haythem' AND p.nom = 'Rehouma'
DETACH DELETE p;
```
- **`DETACH DELETE`** : Cette commande supprime le professeur ainsi que toutes les relations associées (par exemple, `ENSEIGNER`, `COLLEGUES`, etc.).

#### 2. **Supprimer tous les professeurs** :
Pour supprimer tous les nœuds avec l'étiquette `professeur`, vous pouvez utiliser la commande suivante :
```cypher
MATCH (p:professeur)
DETACH DELETE p;
```
Cette requête supprimera tous les professeurs et toutes les relations associées.

#### 3. **Supprimer un professeur et les relations avec un type spécifique** :
Si vous souhaitez supprimer un professeur et uniquement certaines de ses relations (par exemple, la relation `ENSEIGNER`), mais pas toutes, vous pouvez faire comme suit :
```cypher
MATCH (p:professeur)-[r:ENSEIGNER]->(c:cours)
WHERE p.prenom = 'Haythem' AND p.nom = 'Rehouma'
DELETE r;
```
Cette requête supprime uniquement la relation `ENSEIGNER`, mais laisse le professeur et le cours en place.

#### 4. **Supprimer un professeur avec une condition sur le nombre d'heures d'enseignement** :
Si vous voulez supprimer un professeur qui enseigne un certain nombre d'heures, par exemple 60 heures :
```cypher
MATCH (p:professeur)-[r:ENSEIGNER {nbrhrs: 60}]->(c:cours)
DETACH DELETE p;
```
Cela supprimera le professeur qui enseigne exactement 60 heures.

---

### Supprimer toutes les relations associées à des professeurs

#### 1. **Supprimer toutes les relations entre professeurs (`COLLEGUES`)** :
Si vous voulez supprimer uniquement les relations `COLLEGUES` sans toucher aux professeurs eux-mêmes :
```cypher
MATCH (p1:professeur)-[r:COLLEGUES]->(p2:professeur)
DELETE r;
```

#### 2. **Supprimer toutes les relations `ENSEIGNER` entre professeurs et cours** :
Pour supprimer toutes les relations `ENSEIGNER` sans supprimer les professeurs ou les cours :
```cypher
MATCH (p:professeur)-[r:ENSEIGNER]->(c:cours)
DELETE r;
```

---

### Bonus : Supprimer toutes les données dans la base

Si vous souhaitez réinitialiser complètement votre base de données en supprimant tous les nœuds (professeurs, cours, relations, etc.), vous pouvez exécuter la commande suivante :
```cypher
MATCH (n)
DETACH DELETE n;
```
Cela supprimera **tous** les nœuds et toutes les relations de votre base Neo4j.

---

### Résumé des principales commandes :
- **Lister les professeurs** : `MATCH (p:professeur) RETURN p;`
- **Supprimer un professeur spécifique** : `MATCH (p:professeur) WHERE p.prenom = 'Haythem' DETACH DELETE p;`
- **Supprimer tous les professeurs** : `MATCH (p:professeur) DETACH DELETE p;`
- **Supprimer toutes les relations `COLLEGUES`** : `MATCH (p1:professeur)-[r:COLLEGUES]->(p2:professeur) DELETE r;`
- **Réinitialiser toute la base de données** : `MATCH (n) DETACH DELETE n;`

