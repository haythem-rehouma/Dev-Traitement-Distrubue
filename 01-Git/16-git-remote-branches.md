---
title: "Chapitre 16 - Branches Distantes Git"
description: "Découvrez comment gérer les branches distantes dans Git. Vous apprendrez à créer, pousser, récupérer et gérer des branches distantes dans GitHub et synchroniser votre travail avec vos collaborateurs."
emoji: "🔍"
---

---
<a name="table-des-matieres"></a>

## Table des matières
---

### **Objectif :**
Ce guide vous apprendra à gérer les **branches distantes** dans Git. Vous apprendrez comment créer, pousser, récupérer et gérer des branches distantes dans GitHub et synchroniser votre travail avec vos collaborateurs.

---

## **Partie 1 : Théorie**

### **Qu'est-ce qu'une branche distante ?**

- **Une branche distante** est une branche qui existe dans un dépôt distant, comme GitHub, mais pas forcément dans votre dépôt local. Les branches distantes vous permettent de collaborer avec d'autres personnes sur un projet, car elles sont partagées et accessibles par tous les contributeurs.

### **Pourquoi utiliser des branches distantes ?**

- **Collaboration** : Les branches distantes permettent à plusieurs personnes de travailler sur différentes fonctionnalités sans interférer avec la branche principale. Vous pouvez pousser vos branches vers GitHub pour les partager, puis les fusionner une fois le travail terminé.
- **Gestion du code** : Cela permet de maintenir une branche stable (souvent appelée `main` ou `master`) et de travailler sur des branches de développement ou de fonctionnalités sans perturber la branche principale.

### **Les commandes principales pour les branches distantes :**

1. **`git push`** : Pousser une branche locale vers le dépôt distant.
2. **`git fetch`** : Récupérer les branches distantes sans les fusionner avec vos branches locales.
3. **`git pull`** : Récupérer et fusionner les branches distantes avec vos branches locales.
4. **`git branch -r`** : Voir toutes les branches distantes.
5. **`git checkout -b`** : Créer une branche locale à partir d'une branche distante.

---

## **Partie 2 : Pratique**

Nous allons maintenant manipuler des branches distantes dans le projet **site-php-1**. Vous apprendrez à créer, récupérer et gérer des branches distantes avec GitHub.

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

### **Étape 2 : Créer une nouvelle branche locale et la pousser vers GitHub**

1. **Créer une nouvelle branche** :

   Nous allons créer une nouvelle branche locale appelée `feature-remote-branch` pour travailler dessus :

   ```bash
   git checkout -b feature-remote-branch
   ```

2. **Faire des modifications dans `app.js`** :

   Ouvrez le fichier `app.js` et ajoutez cette ligne :

   ```javascript
   console.log("Modification dans la branche distante feature-remote-branch");
   ```

3. **Ajouter et committer les modifications** :

   - Ajoutez `app.js` à la zone de staging :
   
     ```bash
     git add app.js
     ```

   - Créez un commit pour cette modification :
   
     ```bash
     git commit -m "Ajout d'un log dans app.js sur la branche feature-remote-branch"
     ```

4. **Pousser la branche vers GitHub** :

   Maintenant que nous avons fait des modifications dans la branche locale, nous allons pousser cette branche vers le dépôt GitHub pour la rendre accessible à d'autres collaborateurs :

   ```bash
   git push -u origin feature-remote-branch
   ```

   - **`-u`** : Cela permet de lier la branche locale à la branche distante.
   - **`origin`** : Il s'agit du dépôt distant (par défaut, le nom est `origin`).
   - **`feature-remote-branch`** : Il s'agit du nom de la branche locale que nous poussons vers GitHub.

---

### **Étape 3 : Voir toutes les branches distantes**

Pour voir toutes les branches distantes disponibles dans le dépôt GitHub, vous pouvez utiliser la commande suivante :

```bash
git branch -r
```

Cette commande liste toutes les branches distantes du dépôt GitHub. Vous verrez quelque chose comme ceci :

```
  origin/main
  origin/feature-remote-branch
```

Cela signifie que `main` et `feature-remote-branch` sont des branches distantes disponibles sur GitHub.

---

### **Étape 4 : Récupérer une branche distante avec `git fetch`**

Supposons qu'un autre développeur a créé une branche sur GitHub et que vous voulez la récupérer sans fusionner les modifications directement dans votre branche locale.

1. **Récupérer les branches distantes sans les fusionner** :

   Tapez la commande suivante pour récupérer toutes les branches distantes :

   ```bash
   git fetch
   ```

   Cette commande va récupérer les informations sur les branches distantes, mais elle ne fusionnera pas les modifications dans votre branche locale.

2. **Voir les branches locales et distantes après un `fetch`** :

   Après avoir utilisé `git fetch`, tapez la commande suivante pour voir toutes les branches locales et distantes disponibles :

   ```bash
   git branch -a
   ```

   Vous verrez une liste comme celle-ci :

   ```
   * main
     feature-remote-branch
     remotes/origin/main
     remotes/origin/feature-remote-branch
   ```

   Les branches locales sont celles sans le préfixe `remotes/origin`, et les branches distantes sont préfixées par `remotes/origin`.

---

### **Étape 5 : Travailler avec une branche distante**

Si vous souhaitez travailler sur une branche distante (par exemple `feature-remote-branch`), vous pouvez créer une copie locale de cette branche.

1. **Créer une nouvelle branche locale à partir d'une branche distante** :

   Si la branche distante `feature-remote-branch` n'existe pas localement, vous pouvez la récupérer et basculer dessus avec la commande suivante :

   ```bash
   git checkout -b feature-remote-branch origin/feature-remote-branch
   ```

   Cela crée une nouvelle branche locale basée sur la branche distante `feature-remote-branch` et vous permet de travailler dessus.

2. **Faire des modifications et pousser les changements vers GitHub** :

   - Faites des modifications dans cette branche.
   - Ajoutez les fichiers modifiés à la zone de staging :

     ```bash
     git add <nom_du_fichier>
     ```

   - Créez un commit :

     ```bash
     git commit -m "Modifications dans la branche distante feature-remote-branch"
     ```

   - Poussez la branche locale modifiée vers GitHub :

     ```bash
     git push origin feature-remote-branch
     ```

---

### **Étape 6 : Fusionner une branche distante dans `main`**

Après avoir travaillé sur une branche distante, vous pouvez vouloir fusionner cette branche dans la branche principale (`main`).

1. **Basculer sur la branche `main`** :

   ```bash
   git checkout main
   ```

2. **Récupérer les dernières modifications de `main` depuis GitHub** :

   Avant de fusionner quoi que ce soit, assurez-vous d'avoir les dernières modifications de la branche `main` depuis GitHub :

   ```bash
   git pull origin main
   ```

3. **Fusionner la branche distante dans `main`** :

   Fusionnez ensuite la branche distante (qui a été récupérée localement) dans `main` :

   ```bash
   git merge feature-remote-branch
   ```

4. **Pousser les modifications de `main` vers GitHub** :

   Après la fusion, poussez les changements dans `main` vers GitHub :

   ```bash
   git push origin main
   ```

---

### **Étape 7 : Supprimer une branche distante**

Une fois que vous avez fusionné une branche et qu'elle n'est plus nécessaire, vous pouvez la supprimer de GitHub.

1. **Supprimer une branche distante** :

   Pour supprimer une branche distante sur GitHub, utilisez la commande suivante :

   ```bash
   git push origin --delete feature-remote-branch
   ```

Cela supprimera la branche `feature-remote-branch` du dépôt GitHub, mais la branche locale ne sera pas affectée.

---

### **Étape 8 : Résumé des commandes pour gérer les branches distantes**

1. **Créer une nouvelle branche et la pousser vers GitHub** :
   ```bash
   git checkout -b feature-remote-branch
   git push -u origin feature-remote-branch
   ```

2. **Voir toutes les branches distantes** :
   ```bash
   git branch -r
   ```

3. **Récupérer les branches distantes sans les fusionner** :
   ```bash
   git fetch
   ```

4. **Créer une branche locale à partir d'une branche distante** :
   ```bash
   git checkout -b feature-remote-branch origin/feature-remote-branch
   ```

5. **Fusionner une branche distante dans `main`** :
  




-------------
### **Étape 8 : Résumé des commandes pour gérer les branches distantes** (suite)
-------------

5. **Fusionner une branche distante dans `main`** :
   ```bash
   git checkout main
   git pull origin main
   git merge feature-remote-branch
   git push origin main
   ```

6. **Supprimer une branche distante sur GitHub** :
   ```bash
   git push origin --delete feature-remote-branch
   ```

---

### **Conclusion**

Ce guide vous a appris à manipuler les **branches distantes** dans Git, notamment à créer des branches, à les pousser vers GitHub, à récupérer les branches distantes créées par d'autres collaborateurs, et à fusionner ces branches dans votre projet principal. Grâce à ces techniques, vous pouvez facilement collaborer sur des projets partagés et gérer efficacement plusieurs branches dans un environnement distribué comme GitHub.

Les branches distantes sont essentielles pour maintenir un flux de travail propre et collaboratif, en veillant à ce que tout le monde puisse contribuer de manière structurée sans perturber le développement principal.
