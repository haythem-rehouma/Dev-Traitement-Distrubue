#  **Examen de Troubleshooting AWS CLI : Pipeline S3 → Glue → Athena → Redshift**

### Contexte pédagogique :
Vous avez reçu un script conçu pour automatiser un pipeline de données en AWS. Ce pipeline doit :
- Stocker un fichier CSV dans Amazon S3
- Cataloguer ce fichier avec AWS Glue
- L'interroger avec Athena
- Charger les résultats dans Redshift

Cependant, **plusieurs erreurs empêchent son exécution correcte**. Votre mission est de **l’analyser, détecter les erreurs et proposer des corrections précises**.

Chaque question inclut :
- Le **code fautif ou incomplet**
- Une **situation réaliste d’erreur**
- Des **questions guidées**
- L’attente explicite : identifier le problème, décrire ses effets, et proposer une ou plusieurs **solutions concrètes avec CLI ou JSON**



# Partie 1 — Création du bucket S3

```bash
aws s3api create-bucket \
--bucket pipeline-data-bucket \
--region us-east-1
```

### Contexte :
Cette commande est censée créer un bucket dans la région `us-east-1`, mais elle provoque une erreur.

### Questions :
1. Observez la commande. **Que manque-t-il dans ce cas spécifique à `us-east-1` pour éviter une erreur ?**
2. Écrivez la **version correcte et complète** de la commande pour créer un bucket dans `us-east-1`, en respectant la syntaxe CLI recommandée.



# Partie 2 — Envoi de fichier CSV

Le fichier CSV uploadé dans S3 contient la ligne :

```csv
VTS,2017-01-01 00:30:00,2017-01-01 00:45:00,1,3,1,0,100,150,1,12.5,0.5,0.5,2.0,0.0
```

La table Glue prévue s’attend à **18 colonnes** correspondant à :

```
vendor, pickup, dropoff, count, distance, ratecode, storeflag, pulocid, dolocid, paytype, fare, extra, mta_tax, tip, tolls, surcharge, total
```

### Questions :
3. Le fichier n’a que 16 champs. **Quelle erreur précise Athena ou Glue renverra-t-elle au moment de la requête ?** (message attendu, comportement)
4. **Quelles colonnes sont manquantes ?** Rédigez la ligne CSV correcte en ajoutant les champs manquants avec des valeurs cohérentes.
5. Donnez la **commande CLI complète** pour envoyer ce fichier corrigé dans le bon répertoire du bucket.



# Partie 3 — Définition Glue incorrecte

Voici un extrait du fichier `table-definition.json` :

```json
"SerdeInfo": {
  "SerializationLibrary": "org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe"
}
```

### Questions :
6. Quelle erreur provoque l’absence du champ `"field.delim": ","` dans `SerdeInfo` ?  
   (indice : que va-t-il se passer lors du parsing ?)
7. Proposez la version **complète et correcte** du bloc `"SerdeInfo"` avec le champ de délimitation et tout ce qui est attendu pour du CSV.
8. Si vous remplacez le fichier CSV par un fichier JSON **sans changer la SerDe**, que va-t-il se passer ? (expliquer le comportement + comment corriger)



# Partie 4 — Erreur sur Athena : mauvaise configuration

Commande exécutée :

```bash
aws athena start-query-execution \
--query-string "SELECT * FROM yellow LIMIT 10;" \
--query-execution-context Database=taxidata \
--result-configuration OutputLocation=s3://pipeline-data-bucket/athena/
```

Le répertoire `athena/` n’existe pas encore dans le bucket.

### Questions :
9. Athena produit une erreur. **Laquelle ? Et pourquoi ?**  
   (indice : Athena n’a pas besoin du dossier explicite, mais a-t-elle le droit de l’écrire ?)
10. Donnez une policy IAM JSON permettant à Athena d’écrire les résultats dans le bucket.
11. Quelle commande CLI permettrait à un étudiant de tester s’il a bien les permissions nécessaires sur le bucket S3 ?



# Partie 5 — `COPY` vers Redshift échoue

Le script suivant est utilisé :

```bash
aws redshift-data execute-statement \
--cluster-identifier redshift-demo \
--database prod \
--db-user admin \
--sql "COPY revenus_par_type FROM 's3://pipeline-data-bucket/results/athena-output/result.csv' IAM_ROLE 'arn:aws:iam::1234567890:role/MyRedshiftRole' FORMAT AS CSV;"
```

Erreur reçue :  
`Invalid credentials or S3 file not found`

### Questions :
12. Donnez les **3 causes possibles** de cette erreur (`COPY` depuis S3). Soyez précis.
13. Écrivez la **policy IAM minimale** pour que Redshift ait accès au bucket.
14. Écrivez la commande CLI permettant d’**attacher un rôle IAM** à un cluster Redshift.



# Partie 6 — Erreur d’envoi dans Firehose

Un étudiant tente d’envoyer un enregistrement JSON dans Firehose :

```bash
aws firehose put-record \
--delivery-stream-name firehose-taxi \
--record="{ \"vendor\": \"VTS\", \"fare\": 18.75 }"
```

### Questions :
15. Pourquoi cette commande renvoie-t-elle une erreur ? (indice : format, guillemets, JSON incorrect)
16. Écrivez la version **correcte** de cette commande CLI, avec **format JSON, échappement correct**, et fin de ligne `\n`.
17. Si la table Redshift cible ne contient pas la colonne `fare`, **que se passe-t-il exactement dans Firehose ?** Décrivez le comportement.


# Partie 7 — Analyse finale

18. Quelle est l’utilité du mot-clé `IGNOREHEADER 1` dans une commande `COPY` vers Redshift ?  
    Donnez un exemple de situation où son absence causerait une mauvaise interprétation des données.
19. Quelle approche recommandez-vous pour automatiser l’exécution régulière de ce pipeline (quotidiennement à 3h du matin) ?
    - Bash + cron
    - Step Functions
    - Lambda + CloudWatch Events
    - Terraform
    Justifiez votre réponse.
20. Proposez **2 tests automatisés** qu’un étudiant pourrait écrire pour **vérifier le bon fonctionnement du pipeline** chaque jour.

