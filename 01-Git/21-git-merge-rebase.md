---
title: "Chapitre 21 - Git Merge et Rebase"
description: "Découvrez comment utiliser les commandes **`git merge`** et **`git rebase`**, deux façons différentes de combiner les modifications de branches dans Git. Vous apprendrez quand utiliser chaque approche et comment les mettre en pratique dans votre projet Git."
emoji: "🔍"
---

---
<a name="table-des-matieres"></a>

## Table des matières
---

### **Objectif :**
Ce guide vous apprendra à comprendre et utiliser les commandes **`git merge`** et **`git rebase`**, deux façons différentes de combiner les modifications de branches dans Git. Vous apprendrez quand utiliser chaque approche et comment les mettre en pratique dans votre projet Git.

---

## **Partie 1 : Théorie**

### **Qu'est-ce que `git merge` ?**

- **`git merge`** est une commande qui vous permet de **fusionner** les modifications d'une branche dans une autre. Cette approche conserve l'historique des commits et ajoute un commit spécial de "fusion" lorsque les branches sont combinées. Les changements de chaque branche sont appliqués l'un après l'autre.

### **Quand utiliser `git merge` ?**

- **Collaboration** : Lorsque vous travaillez avec d'autres développeurs, `git merge` est souvent la méthode la plus simple et la plus directe pour combiner des branches.
- **Historique complet** : `git merge` conserve l'historique complet des commits des deux branches, ce qui est utile si vous voulez voir exactement comment une fonctionnalité a été développée.

### **Qu'est-ce que `git rebase` ?**

- **`git rebase`** est une commande qui **réapplique** les commits d'une branche sur une autre en les réorganisant comme si toutes les modifications avaient été faites après les commits de la branche cible. Cela permet de garder un historique plus linéaire.

### **Quand utiliser `git rebase` ?**

- **Historique propre** : Si vous voulez éviter un historique avec de nombreux commits de fusion et garder un arbre de commits plus linéaire, `git rebase` est utile.
- **Relecture** : `git rebase` permet de rebaser des changements pour mieux intégrer des modifications à un moment donné, comme si elles avaient été faites après une autre branche.

### **Différence entre `merge` et `rebase`**

- **Merge** : Conserve l'historique complet des deux branches, ce qui peut créer des "branches" dans l'historique.
- **Rebase** : Réapplique les commits d'une branche sur une autre, créant un historique linéaire sans commits de fusion supplémentaires.

---

## **Partie 2 : Pratique**

Nous allons maintenant mettre en pratique l'utilisation de **`git merge`** et **`git rebase`** dans le cadre du projet **site-php-1**.

### **Étape 1 : Cloner le projet existant depuis GitHub**

Si vous n'avez pas encore de dépôt local, commencez par cloner le projet **site-php-1** depuis GitHub.

1. Ouvrez votre terminal et exécutez la commande suivante pour cloner le projet :

   ```bash
   git clone https://github.com/hrhouma1/site-php-1.git
   ```

2. Entrez dans le répertoire du projet cloné :

   ```bash
   cd site-php-1
   ```

---

### **Étape 2 : Créer deux nouvelles branches et faire des modifications**

Nous allons créer deux nouvelles branches (`feature-A` et `feature-B`) pour illustrer la différence entre `merge` et `rebase`.

1. **Créer la branche `feature-A`** :

   ```bash
   git checkout -b feature-A
   ```

2. **Modifier `app.js` dans `feature-A`** :

   Ouvrez le fichier `app.js` et ajoutez cette ligne :

   ```javascript
   console.log("Modification dans feature-A");
   ```

3. **Ajouter et committer les modifications** :

   - Ajoutez `app.js` à la zone de staging :

     ```bash
     git add app.js
     ```

   - Créez un commit pour cette modification :

     ```bash
     git commit -m "Ajout d'un log dans app.js sur feature-A"
     ```

4. **Créer la branche `feature-B`** (basée sur `main`) :

   Revenons sur la branche `main` et créons la branche `feature-B` :

   ```bash
   git checkout main
   git checkout -b feature-B
   ```

5. **Modifier `index.php` dans `feature-B`** :

   Ouvrez le fichier `index.php` et ajoutez cette ligne :

   ```php
   echo "Modification dans feature-B";
   ```

6. **Ajouter et committer les modifications** :

   - Ajoutez `index.php` à la zone de staging :

     ```bash
     git add index.php
     ```

   - Créez un commit pour cette modification :

     ```bash
     git commit -m "Ajout d'un echo dans index.php sur feature-B"
     ```

---

### **Étape 3 : Utiliser `git merge` pour combiner les branches**

Nous allons maintenant fusionner les deux branches (`feature-A` et `feature-B`) dans la branche `main` en utilisant `git merge`.

1. **Basculer sur la branche `main`** :

   ```bash
   git checkout main
   ```

2. **Fusionner `feature-A` dans `main`** :

   Nous allons commencer par fusionner la branche `feature-A` dans `main` :

   ```bash
   git merge feature-A
   ```

   Git créera un **commit de fusion** pour combiner les modifications de `feature-A` avec `main`. L'historique Git montrera clairement que deux branches ont été fusionnées.

3. **Fusionner `feature-B` dans `main`** :

   Répétez la même opération pour `feature-B` :

   ```bash
   git merge feature-B
   ```

   Vous avez maintenant fusionné les deux branches dans `main`.

4. **Vérifier l'historique** :

   Vous pouvez vérifier l'historique des commits pour voir les commits de fusion :

   ```bash
   git log --oneline --graph
   ```

   Vous verrez un graphique montrant que les branches `feature-A` et `feature-B` ont été fusionnées dans `main`.

---

### **Étape 4 : Utiliser `git rebase` pour une approche différente**

Nous allons maintenant voir comment utiliser `git rebase` pour une approche plus linéaire. Recommençons le processus en réinitialisant `main` avant les fusions et en utilisant `rebase`.

1. **Revenir sur la branche `feature-B`** :

   ```bash
   git checkout feature-B
   ```

2. **Rebaser `feature-B` sur `main`** :

   Nous allons réappliquer les commits de `feature-B` après ceux de `main` pour éviter un commit de fusion :

   ```bash
   git rebase main
   ```

   Cela "rejoue" les commits de `feature-B` comme s'ils avaient été faits après les commits de `main`, créant un historique linéaire.

3. **Basculer sur `main` et fusionner** :

   Après le rebase, vous pouvez basculer sur la branche `main` et fusionner `feature-B` sans créer de commit de fusion supplémentaire :

   ```bash
   git checkout main
   git merge feature-B
   ```

4. **Vérifier l'historique après `rebase`** :

   Utilisez la commande suivante pour voir l'historique après le `rebase` :

   ```bash
   git log --oneline --graph
   ```

   Vous verrez un historique linéaire sans commit de fusion, contrairement à l'approche avec `merge`.

---

### **Étape 5 : Pousser les modifications vers GitHub**

1. **Pousser les modifications dans `main`** :

   Une fois la fusion ou le rebase terminé, vous pouvez pousser les modifications dans la branche principale `main` vers GitHub :

   ```bash
   git push origin main
   ```

2. **Pousser la branche rebasée `feature-B`** :

   Si vous avez utilisé `rebase`, vous devrez forcer la mise à jour de la branche distante pour refléter les nouveaux commits réordonnés :

   ```bash
   git push --force origin feature-B
   ```

---

### **Étape 6 : Résumé des commandes `git merge` et `git rebase`**

1. **Créer une nouvelle branche** :
   ```bash
   git checkout -b feature-A
   ```

2. **Fusionner une branche avec `git merge`** :
   - Basculez sur la branche `main` :
     ```bash
     git checkout main
     ```
   - Fusionnez la branche `feature-A` :
     ```bash
     git merge feature-A
     ```

3. **Rebaser une branche avec `git rebase`** :
   - Basculez sur la branche `feature-B` :
     ```bash
     git checkout feature-B
     ```
   - Rebasez la branche sur `main` :
     ```bash
     git rebase main
     ```

4. **Pousser les modifications vers GitHub** :
   - Pour pousser avec `merge` :
     ```bash
     git push origin main
     ```
   - Pour pousser après un `rebase` :
     ```bash
     git push --force origin feature-B
     ```



--------------------
### **Étape 6 : Résumé des commandes `git merge` et `git rebase`** (suite)
--------------------

5. **Vérifier l'historique des commits** :

   - Pour voir un aperçu graphique des commits et des branches fusionnées (ou réordonnées avec `rebase`), utilisez la commande suivante :
     ```bash
     git log --oneline --graph
     ```

   Cela vous montrera un graphe de l'historique Git, avec les branches fusionnées dans le cas de `merge`, et un historique linéaire dans le cas de `rebase`.

6. **Supprimer les branches après fusion ou rebase** :
   
   Une fois que vous avez fusionné ou rebasé avec succès vos branches dans `main`, vous pouvez les supprimer pour nettoyer votre dépôt.

   - Pour supprimer une branche locale (par exemple, `feature-A`) :
     ```bash
     git branch -d feature-A
     ```

   - Pour supprimer une branche distante (par exemple, `feature-A`) :
     ```bash
     git push origin --delete feature-A
     ```

---

### **Étape 7 : Cas des conflits avec `git merge` et `git rebase`**

Lors de l'utilisation de `git merge` ou `git rebase`, vous pouvez parfois rencontrer des **conflits** si des modifications incompatibles ont été faites sur les mêmes lignes de code dans les deux branches. Voici comment gérer ces conflits.

#### **1. Gérer un conflit avec `git merge` :**

1. **Conflit détecté après un merge** :

   Si vous obtenez un conflit après avoir tenté de fusionner une branche, Git vous indiquera quels fichiers sont en conflit.

2. **Ouvrir le fichier en conflit** :

   Ouvrez le fichier dans votre éditeur de texte. Vous verrez des sections comme ceci :

   ```
   <<<<<<< HEAD
   Votre version locale
   =======
   La version sur la branche distante
   >>>>>>> nom_du_commit_distant
   ```

3. **Résoudre le conflit manuellement** :

   Vous devez manuellement choisir quelles modifications garder, ou fusionner les deux versions. Une fois le conflit résolu, enregistrez le fichier.

4. **Ajouter le fichier résolu à la zone de staging** :

   Après avoir résolu le conflit, ajoutez le fichier modifié à la zone de staging :

   ```bash
   git add <nom_du_fichier>
   ```

5. **Compléter le merge** :

   Enfin, terminez le merge avec un commit automatique de Git :

   ```bash
   git commit
   ```

#### **2. Gérer un conflit avec `git rebase` :**

1. **Conflit détecté après un rebase** :

   Si un conflit survient lors d'un rebase, Git met en pause le processus et vous demande de résoudre le conflit.

2. **Résoudre le conflit de la même manière qu'avec `merge`** :

   Comme pour `merge`, ouvrez le fichier en conflit et résolvez manuellement les différences.

3. **Ajouter le fichier résolu et continuer le rebase** :

   Une fois les conflits résolus, ajoutez les fichiers modifiés à la zone de staging :

   ```bash
   git add <nom_du_fichier>
   ```

   Puis, continuez le rebase avec :

   ```bash
   git rebase --continue
   ```

4. **Annuler un rebase si nécessaire** :

   Si le rebase devient trop complexe ou si vous voulez annuler le rebase, vous pouvez l'abandonner avec la commande suivante :

   ```bash
   git rebase --abort
   ```

---

### **Conclusion**

Dans ce guide, vous avez appris les différences entre **`git merge`** et **`git rebase`**, deux techniques pour combiner des branches dans Git. Vous avez également vu comment les utiliser dans des situations concrètes, quand préférer `merge` pour garder l'historique complet et quand préférer `rebase` pour avoir un historique plus propre et linéaire.

- **Utiliser `git merge`** : Si vous voulez conserver un historique complet avec des commits de fusion pour chaque branche.
- **Utiliser `git rebase`** : Si vous voulez un historique propre et linéaire, sans commits de fusion.

De plus, vous avez appris à gérer les **conflits** qui peuvent survenir lors des fusions ou des rebases, ainsi que les commandes essentielles pour pousser, récupérer et gérer des branches locales et distantes.

Grâce à ces techniques, vous pouvez mieux gérer vos branches, garder votre historique Git propre et compréhensible, et collaborer efficacement sur des projets partagés via GitHub.


