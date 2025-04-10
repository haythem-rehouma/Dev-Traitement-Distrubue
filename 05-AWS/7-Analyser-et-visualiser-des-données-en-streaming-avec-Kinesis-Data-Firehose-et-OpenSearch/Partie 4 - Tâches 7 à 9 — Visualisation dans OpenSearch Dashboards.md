#  Partie 4 : Tâches 7 à 9 — Visualisation dans OpenSearch Dashboards


<br/> 
<br/> 

#  **Tâche 7 : Création d’un modèle d’index dans OpenSearch Dashboards**

> OpenSearch utilise des **modèles d’index (index patterns)** pour identifier les données à visualiser.  
> On utilise ici le champ `datetime` pour filtrer les données dans le temps.

###  Étapes :

1. Retournez dans l’onglet où **OpenSearch Dashboards** est ouvert.
2. Dans le menu de navigation, cliquez sur **Discover**.
3. Cliquez sur **Create index pattern**.

#### Étape 1 :
- **Index pattern name** : entrez `apache_logs`
- Cliquez sur **Next step**

#### Étape 2 :
- Pour **Time field**, sélectionnez `datetime`
- Cliquez sur **Create index pattern**

 **Félicitations !** Vous avez créé un modèle d’index basé sur le champ `datetime`.


<br/>
<br/> 

##  **Tâche 8 : Création d’un diagramme circulaire (pie chart)**

### Objectif :
Analyser les produits les plus vus en fonction du système d’exploitation et du navigateur des visiteurs.

### Étapes :

1. Dans le menu de navigation, allez à **Visualize** > **Create new visualization**.
2. Sélectionnez le type **Pie**.
3. Choisissez la source de données : `apache_logs*`
4. Dans le coin supérieur droit, sélectionnez **Last 30 minutes**, puis cliquez sur **Update**.



###  Création des “buckets” (regroupements de données)

>  Les **bucket aggregations** regroupent les documents en catégories.

#### 1. Ajout du **webpage** :
- Section **Buckets** : cliquez sur `Add > Split slices`
- Agrégation : `Terms`
- Champ : `webpage`
- Cochez : `Group other values in separate bucket`
- Laissez les autres paramètres par défaut.

#### 2. Ajout du champ **os** (système d’exploitation) :
- Cliquez sur `Add > Split slices`
- Sous-agrégation : `Terms`
- Champ : `os`
- Cochez : `Group other values in separate bucket`

#### 3. Ajout du champ **browser** :
- Répétez la même méthode avec `browser` comme champ.



###  Ajouter un filtre sur un produit spécifique (facultatif) :

1. Cliquez sur **Add filter** (en haut à gauche).
2. Champ : `webpage`
3. Opérateur : `is`
4. Valeur : `kindle`
5. Cliquez sur **Save**



### Options de style :

1. Dans le panneau latéral droit, cliquez sur **Options**
2. Activez :
   - `Show labels`
3. Cliquez sur **Update**

Vous devriez maintenant voir les étiquettes de données dans le diagramme.  
Les couleurs peuvent varier selon votre interface.



### Analyse :

- Passez votre curseur sur chaque part du camembert pour voir :
  - le navigateur,
  - le système d’exploitation,
  - la page produit concernée.
- Vous pouvez aussi filtrer par navigateur ou retirer des filtres pour afficher tous les produits.

 **Félicitations !** Vous avez construit un **diagramme circulaire empilé** représentant la navigation selon les OS et navigateurs.


<br/>
<br/>


##  **Tâche 9 : Création d’une carte thermique (heat map)**

###  Objectif :
Analyser si les visiteurs accèdent aux pages produits depuis la page de recherche ou la page de recommandations.


### Étapes :

1. Dans le menu, allez à **Visualize** > **Create new visualization**.
2. Choisissez le type **Heat Map**.
3. Source : `apache_logs*`



### Création des axes de la carte

#### Axe X : Page de provenance
- Dans la section **Buckets**, cliquez sur `Add > X-axis`
- Agrégation : `Terms`
- Champ : `refering_page`
- Activez : `Group other values in separate bucket`

#### Axe Y : Page produit visitée
- Cliquez sur `Add > Y-axis`
- Sous-agrégation : `Terms`
- Champ : `webpage`
- Activez : `Group other values in separate bucket`



###  Options d'affichage :

1. Onglet **Options**
2. Dans la section **Labels**, activez **Show labels**
3. Cliquez sur **Update**


<br/>
<br/> 

###  Ajuster l’intervalle de temps :

- Dans le coin supérieur droit, changez la période à **Last 1 hour**
- Cliquez sur **Refresh**


<br/>

### Analyse :

- La carte montre la fréquence des visites selon :
  - la **page d’origine** (search ou recommendation),
  - la **page produit** visitée.
- Ex. : Si la case `search → firetvstick` est plus sombre, cela signifie que la majorité des visiteurs sont arrivés là via la **page de recherche**.

**Félicitations !** Vous avez construit une carte thermique démontrant l'efficacité des pages de redirection vers les produits.

