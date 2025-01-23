---
title: "Chapitre 9 - Commande Git Reset"
description: "Découvrez comment utiliser Git Reset pour réinitialiser l'état de votre dépôt à une version antérieure"
emoji: "🔍"
---



---
<a name="table-des-matieres"></a>

## Table des matières
---

1. [Introduction](#introduction)
2. [Partie 1 : Théorie](#partie-1-theorie)
   1. [Qu'est-ce que `git reset` ?](#qu-est-ce-que-git-reset)
   2. [Les trois modes principaux de Git Reset](#les-trois-modes-principaux-de-git-reset)
   3. [Quand utiliser `git reset` ?](#quand-utiliser-git-reset)
3. [Partie 2 : Pratique](#partie-2-pratique)
   1. [Étape 1 : Cloner le projet existant](#etape-1)
   2. [Étape 2 : Créer une nouvelle branche et faire plusieurs commits](#etape-2)
   3. [Étape 3 : Utiliser `git reset --soft`](#etape-3)
   4. [Étape 4 : Utiliser `git reset --mixed`](#etape-4)
   5. [Étape 5 : Utiliser `git reset --hard`](#etape-5)
   6. [Étape 6 : Pousser la branche vers GitHub](#etape-6)
4. [Résumé des commandes](#resume-commandes)
5. [Conclusion](#conclusion)

<br/>
---

<a name="introduction"></a>
## Introduction
---

La commande `git reset` est l'une des plus puissantes de Git, mais elle peut aussi être dangereuse si elle est mal utilisée. Ce guide va vous expliquer **qu'est-ce que git reset**, comment il fonctionne, et comment l'utiliser de manière sécurisée avec des exemples pratiques sur le projet **site-php-1**.


[⬆️ retour à la table des matières](#table-des-matieres)
<br/>

---

<a name="partie-1-theorie"></a>
## 1 - Partie 1 : Théorie
---

<a name="qu-est-ce-que-git-reset"></a>
### **Qu'est-ce que `git reset` ?**

`git reset` est une commande Git qui permet de réinitialiser l'état de votre dépôt à une version antérieure. Il modifie la position du pointeur de la branche actuelle et, en fonction des options utilisées, peut également modifier les fichiers dans la zone de staging et/ou dans le répertoire de travail.

[⬆️ retour à la table des matières](#table-des-matieres)
<br/>

<a name="les-trois-modes-principaux-de-git-reset"></a>
### **Les trois modes principaux de Git Reset :**

1. **`--soft`** : Réinitialise uniquement le pointeur HEAD vers un commit antérieur, mais laisse les modifications dans la zone de staging. Cela signifie que vous pouvez créer un nouveau commit avec ces changements.

2. **`--mixed` (par défaut)** : Réinitialise le pointeur HEAD et enlève les fichiers de la zone de staging (les modifications restent dans votre répertoire de travail). Vous devrez utiliser `git add` si vous voulez les committer à nouveau.

3. **`--hard`** : Réinitialise le pointeur HEAD, la zone de staging et le répertoire de travail. Cela supprime toutes les modifications non committées, y compris les fichiers modifiés.

[⬆️ retour à la table des matières](#table-des-matieres)
<br/>

<a name="quand-utiliser-git-reset"></a>
### **Quand utiliser `git reset` ?**
- **Corriger un commit récent** : Si vous avez fait un commit par erreur ou vous souhaitez changer quelque chose dans le dernier commit.
- **Annuler des modifications locales** : Pour annuler des modifications dans la zone de staging ou dans le répertoire de travail.
- **Nettoyer l'historique** : Pour revenir à un état antérieur dans l'historique Git.

[⬆️ retour à la table des matières](#table-des-matieres)
<br/>

---

<a name="partie-2-pratique"></a>
## 2 - Partie 2 : Pratique
---

Nous allons maintenant voir comment utiliser `git reset` dans un cas concret en travaillant sur le projet **site-php-1**.

<a name="etape-1-cloner-le-projet-existant-depuis-github"></a>
### **Étape 1 : Cloner le projet existant depuis GitHub**

Tout d'abord, clonez le projet **site-php-1** en exécutant la commande suivante dans votre terminal :

```bash
git clone https://github.com/hrhouma1/site-php-1.git
```

Ensuite, entrez dans le dossier cloné :

```bash
cd site-php-1
```

---   

[⬆️ retour à la table des matières](#table-des-matieres)
<br/>

<a name="etape-2-creer-une-nouvelle-branche-et-faire-plusieurs-commits"></a>
### **Étape 2 : Créer une nouvelle branche et faire plusieurs commits**

1. **Créer une nouvelle branche pour expérimenter avec `git reset`** :
   ```bash
   git checkout -b experiment-reset
   ```

2. **Effectuer plusieurs petites modifications et commits** :

   - **Premier commit** : Modifiez le fichier `app.js` et ajoutez cette ligne :
   
     ```javascript
     console.log("Première modification pour git reset");
     ```

     Ensuite, ajoutez le fichier à la zone de staging et créez un commit :
     ```bash
     git add app.js
     git commit -m "Ajout d'un log dans app.js pour git reset"
     ```

   - **Deuxième commit** : Modifiez `index.php` pour y ajouter une ligne d'affichage :
   
     ```php
     echo "Test pour git reset";
     ```

     Ensuite, créez un commit :
     ```bash
     git add index.php
     git commit -m "Ajout d'un echo dans index.php pour git reset"
     ```

   - **Troisième commit** : Modifiez `connection.php` avec une ligne supplémentaire :
   
     ```php
     echo "Mise à jour des informations de connexion pour git reset";
     ```

     Puis, créez un commit :
     ```bash
     git add connection.php
     git commit -m "Mise à jour de connection.php pour git reset"
     ```

3. **Vérifier l'historique des commits** :
   
   Exécutez cette commande pour voir les trois commits que vous avez créés :
   ```bash
   git log --oneline
   ```

   Vous devriez voir quelque chose comme ça :
   ```
   f24c8ab Mise à jour de connection.php pour git reset
   a18e7f9 Ajout d'un echo dans index.php pour git reset
   6b72c8d Ajout d'un log dans app.js pour git reset
   ```

---
[⬆️ retour à la table des matières](#table-des-matieres)
<br/>


<a name="etape-3-utiliser-git-reset-soft"></a>
### **Étape 3 : Utiliser `git reset --soft`**

#### **Cas d’utilisation :**

Supposons que vous réalisiez que vous ne voulez pas avoir trois commits séparés pour ces petites modifications. Vous voulez les combiner en un seul commit, mais vous ne voulez pas perdre vos changements actuels.

1. **Réinitialiser à un commit précédent avec `--soft`** :

   Vous pouvez utiliser `git reset --soft` pour revenir en arrière dans l'historique tout en gardant vos changements dans la zone de staging. Ici, nous allons revenir avant le **premier commit**.

   Exécutez la commande suivante pour revenir à l’état avant le premier commit (en utilisant l'ID du commit approprié) :
   ```bash
   git reset --soft HEAD~3
   ```

2. **Vérifier l’état** :

   Après avoir exécuté `git reset --soft`, tapez `git status` pour voir que tous vos fichiers sont toujours dans la zone de staging, prêts à être committés.

3. **Créer un nouveau commit** :

   Maintenant, vous pouvez créer un nouveau commit unique qui combine les trois précédents :
   ```bash
   git commit -m "Regroupement des changements pour app.js, index.php, et connection.php"
   ```

---

[⬆️ retour à la table des matières](#table-des-matieres)
<br/>

<a name="etape-4-utiliser-git-reset-mixed"></a>
### **Étape 4 : Utiliser `git reset --mixed`**

#### **Cas d’utilisation :**

Supposons que vous avez ajouté des fichiers à la zone de staging par erreur et que vous voulez les retirer, mais vous ne voulez pas perdre les modifications que vous avez faites. `git reset --mixed` est utile pour cela.

1. **Ajouter `app.js` à la zone de staging** :
   ```bash
   git add app.js
   ```

2. **Réinitialiser la zone de staging avec `--mixed`** :
   ```bash
   git reset --mixed
   ```

3. **Vérifier l’état** :

   Tapez `git status` pour voir que `app.js` n’est plus dans la zone de staging, mais les modifications locales sont toujours présentes dans le répertoire de travail.

---

[⬆️ retour à la table des matières](#table-des-matieres)
<br/>

<a name="etape-5-utiliser-git-reset-hard"></a>
### **Étape 5 : Utiliser `git reset --hard`**

#### **Cas d’utilisation :**

Supposons que vous ayez modifié plusieurs fichiers et que vous réalisez que vous voulez tout annuler. `git reset --hard` efface toutes les modifications locales dans la zone de staging et dans votre répertoire de travail.

**Attention : Cette commande est dangereuse, car elle supprime toutes les modifications non committées.**

1. **Faire une modification dans `index.php`** :
   
   Modifiez `index.php` pour y ajouter cette ligne :
   ```php
   echo "Ceci est un test pour git reset --hard";
   ```

2. **Vérifier l’état** :
   ```bash
   git status
   ```

   Vous verrez que `index.php` est marqué comme modifié.

3. **Réinitialiser avec `--hard`** :

   Exécutez la commande suivante pour annuler la modification et revenir à l’état du dernier commit :
   ```bash
   git reset --hard
   ```

4. **Vérifier que la modification est annulée** :

   Ouvrez `index.php` et constatez que la ligne ajoutée a été supprimée. Vous pouvez également exécuter `git status` pour vérifier que tout est revenu à l’état initial.

---

[⬆️ retour à la table des matières](#table-des-matieres)
<br/>

<a name="etape-6-pousser-la-branche-vers-github"></a>
### **Étape 6 : Pousser la branche vers GitHub**

Après avoir expérimenté avec les différentes options de `git reset`, vous pouvez pousser la branche vers GitHub comme suit :

```bash
git push -u origin experiment-reset
```

Ensuite, vous pouvez créer une **pull request** pour fusionner vos changements dans la branche principale si nécessaire.


[⬆️ retour à la table des matières](#table-des-matieres)
<br/>

---


<a name="resume-commandes"></a>
## 3 - Résumé des commandes
---

1. **Cloner le projet** :
   ```bash
   git clone https://github.com/hrhouma1/site-php-1.git
   cd site-php-1
   ```

2. **Créer une nouvelle branche** :
   ```bash
   git checkout -b experiment-reset
   ```

3. **Effectuer plusieurs commits** :
   - `git add app.js`
   - `git commit -m "Ajout d'un log dans app.js pour git reset"`
   - `git add index.php`
   - `git commit -m "Ajout d'un echo dans index.php pour git reset"`
   - `git add connection.php`
   - `git commit -m "Mise à jour de connection.php pour git reset"`

4. **Utiliser `git reset --soft`** :
   ```bash
   git reset --soft HEAD~3
   ```

5. **Utiliser `git reset --mixed`** :
   ```bash
   git reset --mixed
   ```

6. **Utiliser `git reset --hard`** :
   ```bash
   git reset --hard
   ```

7. **Pousser la branche vers GitHub** :
   ```bash
   git push -u origin experiment-reset
   ```



[⬆️ retour à la table des matières](#table-des-matieres)
<br/>

---

