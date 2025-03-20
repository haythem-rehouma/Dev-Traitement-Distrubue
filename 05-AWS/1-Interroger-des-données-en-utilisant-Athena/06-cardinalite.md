# Cardinalité :

La cardinalité fait référence au nombre de valeurs distinctes dans une colonne d'une table de données. Voici une explication des différents types de cardinalité :

1. **Haute cardinalité** : Cela désigne une colonne qui contient un grand nombre de valeurs uniques ou très rares. Par exemple, une colonne `USER_ID` dans une table `USERS` aurait une haute cardinalité si chaque utilisateur avait un identifiant unique. D'autres exemples typiques incluent des adresses e-mail, des numéros de téléphone ou des identifiants d'utilisateur.

2. **Cardinalité normale** : Ce type de cardinalité décrit une colonne qui contient un mélange de valeurs communes et rares. Par exemple, une colonne `LAST_NAME` dans une table de clients pourrait avoir une cardinalité normale. Certains noms de famille sont courants (comme "Dupont"), mais il y a aussi une grande diversité de noms moins fréquents.

3. **Faible cardinalité** : Une colonne à faible cardinalité contient un nombre limité de valeurs distinctes. Un exemple courant est une colonne `NEW_CUSTOMER` qui pourrait ne contenir que deux valeurs, comme `Y` (Oui) ou `N` (Non), indiquant si un client est nouveau ou non.

En résumé, la cardinalité donne une idée de la diversité des valeurs dans une colonne, ce qui peut être utile pour optimiser les bases de données et comprendre la nature des données.

# Les trois types de cardinalité.

### 1. **Haute Cardinalité** :
Imagine une colonne dans une table qui contient les numéros de sécurité sociale (`NUM_SS`) de personnes. Chaque personne a un numéro unique. Donc, si tu as 1000 personnes dans ta base de données, cette colonne aura 1000 valeurs différentes. C’est ça qu’on appelle **haute cardinalité** : beaucoup de valeurs uniques.

Exemple :
```
NUM_SS
----------
123-45-6789
987-65-4321
456-78-9012
```
Chaque valeur est unique.

### 2. **Cardinalité Normale** :
Pense à une colonne qui contient les noms de famille des clients (`NOM_DE_FAMILLE`). Certains noms comme "Dupont" ou "Martin" apparaissent plusieurs fois, mais beaucoup d'autres noms seront différents. Il y a un mélange de valeurs uniques et de valeurs répétées, ce qui correspond à une **cardinalité normale**.

Exemple :
```
NOM_DE_FAMILLE
--------------
Dupont
Martin
Nguyen
Dupont
```
Ici, "Dupont" apparaît plusieurs fois, mais d’autres noms sont uniques ou rares.

### 3. **Faible Cardinalité** :
Ici, imagine une colonne qui indique si un client est nouveau ou non (`NOUVEAU_CLIENT`), avec seulement deux réponses possibles : Oui (`OUI`) ou Non (`NON`). C’est un exemple typique de **faible cardinalité**, car il n’y a que deux valeurs possibles.

Exemple :
```
NOUVEAU_CLIENT
--------------
OUI
NON
OUI
NON
```
Ici, les valeurs sont très limitées et répétées.

