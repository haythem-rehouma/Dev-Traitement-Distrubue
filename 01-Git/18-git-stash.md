---
title: "Chapitre 18 - Commande Git Stash"
description: "Découvrez comment utiliser la commande **`git stash`**, qui vous permet de sauvegarder temporairement des modifications non committées dans Git sans les perdre, puis de les récupérer plus tard. Vous apprendrez comment utiliser `git stash` pour suspendre votre travail en cours sans le committer et comment récupérer ces modifications lorsque vous êtes prêt à les réappliquer."
emoji: "🔍"
---

---
<a name="table-des-matieres"></a>

## Table des matières
---

### **Objectif :**
Ce guide vous apprendra à utiliser la commande **`git stash`**, qui vous permet de sauvegarder temporairement des modifications non committées dans Git sans les perdre, puis de les récupérer plus tard. Vous apprendrez comment utiliser `git stash` pour suspendre votre travail en cours sans le committer et comment récupérer ces modifications lorsque vous êtes prêt à les réappliquer.

---

## **Partie 1 : Théorie**

### **Qu'est-ce que `git stash` ?**

- **`git stash`** est une commande qui vous permet de **mettre de côté** vos modifications non committées sans les perdre. Git les enregistre temporairement dans un "stash" (réserve) que vous pouvez récupérer plus tard. Cela est particulièrement utile lorsque vous devez passer à une autre tâche ou basculer de branche sans committer vos changements actuels.

### **Quand utiliser `git stash` ?**

- **Interruption de travail** : Lorsque vous travaillez sur des modifications mais que vous devez basculer rapidement sur une autre branche sans committer.
- **Problèmes de compilation** : Si vous avez des modifications en cours qui ne sont pas prêtes à être committées mais que vous devez corriger un bug urgent sur une autre branche.
- **Réinitialiser l'état du projet** : Si vous voulez basculer entre des contextes de travail différents sans perdre vos modifications en cours.

### **Que fait `git stash` ?**

- **Sauvegarde temporaire** : Git met de côté les modifications de votre répertoire de travail et de la zone de staging.
- **Stockage dans un stack (pile)** : Les stashes sont stockés dans une pile, et vous pouvez appliquer ou supprimer les stashes dans l'ordre de votre choix.
  
---

## **Partie 2 : Pratique**

Nous allons maintenant utiliser **`git stash`** dans le projet **site-php-1** pour voir comment sauvegarder, récupérer et gérer des modifications non committées.

### **Étape 1 : Cloner le projet existant depuis GitHub**

Si vous n'avez pas encore de dépôt local, commencez par cloner le projet **site-php-1** depuis GitHub.

1. Ouvrez votre terminal et exécutez cette commande pour cloner le projet :

   ```bash
   git clone https://github.com/hrhouma1/site-php-1.git
   ```

2. Entrez dans le répertoire du projet cloné :

   ```bash
   cd site-php-1
   ```

---

### **Étape 2 : Faire des modifications dans plusieurs fichiers sans committer**

Nous allons faire des modifications dans des fichiers, mais nous ne les committerons pas tout de suite. Ensuite, nous les mettrons de côté avec `git stash`.

1. **Modifier `app.js`** :

   Ouvrez le fichier `app.js` et ajoutez cette ligne :

   ```javascript
   console.log("Modification temporaire dans app.js avant git stash");
   ```

2. **Modifier `index.php`** :

   Ouvrez le fichier `index.php` et ajoutez cette ligne :

   ```php
   echo "Modification temporaire dans index.php avant git stash";
   ```

3. **Vérifier l'état du dépôt** :

   Tapez la commande suivante pour voir les fichiers modifiés mais non committés :

   ```bash
   git status
   ```

   Vous verrez que `app.js` et `index.php` ont été modifiés mais ne sont pas encore ajoutés à la zone de staging.

---

### **Étape 3 : Utiliser `git stash` pour sauvegarder les modifications**

Nous allons maintenant sauvegarder ces modifications avec `git stash` pour pouvoir passer à une autre tâche sans perdre notre travail actuel.

1. **Sauvegarder les modifications avec `git stash`** :

   Tapez la commande suivante pour stasher (mettre de côté) les modifications en cours :

   ```bash
   git stash
   ```

   Cela sauvegarde toutes les modifications non committées dans un stash et restaure votre répertoire de travail à l'état du dernier commit.

2. **Vérifier l'état après le stash** :

   Tapez à nouveau `git status` pour vérifier que les modifications ont bien été mises de côté. Vous verrez que le répertoire est maintenant **propre** et que toutes les modifications non committées ont été retirées.

---

### **Étape 4 : Récupérer les modifications stasheées avec `git stash pop`**

Lorsque vous êtes prêt à récupérer les modifications que vous avez mises de côté, vous pouvez utiliser la commande `git stash pop`.

1. **Récupérer les modifications avec `git stash pop`** :

   Pour réappliquer les modifications stasheées dans votre répertoire de travail, tapez :

   ```bash
   git stash pop
   ```

   Cette commande réapplique les modifications depuis le stash et les supprime de la pile de stashes.
   
![image](https://github.com/user-attachments/assets/f29688a0-a92e-49f6-923f-5fdb9a6c752f)

- https://www.programiz.com/dsa/stack
   

3. **Vérifier les modifications** :

   Tapez `git status` pour vérifier que les modifications dans `app.js` et `index.php` ont bien été restaurées. Vous devriez voir que les fichiers sont à nouveau modifiés mais non ajoutés à la zone de staging.

---

### **Étape 5 : Sauvegarder plusieurs stashes et les gérer**

Git vous permet de stasher plusieurs ensembles de modifications. Vous pouvez ensuite choisir lequel appliquer ou supprimer.

1. **Faire d'autres modifications dans `app.js`** :

   Ajoutez une nouvelle ligne dans `app.js` :

   ```javascript
   console.log("Nouvelle modification avant un autre git stash");
   ```

2. **Faire un deuxième stash** :

   Stashez à nouveau les modifications avec `git stash` :

   ```bash
   git stash
   ```

3. **Lister tous les stashes** :

   Pour voir tous les stashes disponibles dans la pile, tapez :

   ```bash
   git stash list
   ```

   Vous verrez une liste des stashes que vous avez mis de côté.

   Exemple de sortie :

   ```
   stash@{0}: WIP on main: Ajout de modification temporaire
   stash@{1}: WIP on main: Ajout d'un log dans app.js
   ```

4. **Appliquer un stash spécifique** :

   Si vous voulez appliquer un stash spécifique (par exemple, `stash@{1}`), utilisez la commande suivante :

   ```bash
   git stash apply stash@{1}
   ```

   Cela réapplique les modifications du stash numéro 1 sans le supprimer de la pile.

5. **Supprimer un stash** :

   Si vous voulez supprimer un stash spécifique sans l'appliquer, utilisez :

   ```bash
   git stash drop stash@{1}
   ```

   Cela supprime le stash numéro 1 de la pile.

---

### **Étape 6 : Nettoyer tous les stashes avec `git stash clear`**

Si vous avez terminé avec tous vos stashes et que vous voulez nettoyer la pile, vous pouvez supprimer tous les stashes en une seule commande.

1. **Supprimer tous les stashes** :

   Tapez la commande suivante pour supprimer tous les stashes en cours :

   ```bash
   git stash clear
   ```

---

### **Étape 7 : Résumé des commandes `git stash`**

1. **Stasher les modifications** :
   ```bash
   git stash
   ```

2. **Lister tous les stashes disponibles** :
   ```bash
   git stash list
   ```

3. **Appliquer et supprimer un stash** :
   - Appliquer et supprimer le stash le plus récent :
     ```bash
     git stash pop
     ```

   - Appliquer un stash spécifique sans le supprimer :
     ```bash
     git stash apply stash@{1}
     ```

   - Supprimer un stash spécifique sans l'appliquer :
     ```bash
     git stash drop stash@{1}
     ```

4. **Supprimer tous les stashes** :
   ```bash
   git stash clear
   ```

---

### **Conclusion**

Ce guide vous a montré comment utiliser la commande **`git stash`** pour sauvegarder temporairement des modifications non committées dans Git, les récupérer plus tard, et gérer plusieurs stashes en même temps. Cette fonctionnalité est très utile pour interrompre votre travail sans risquer de perdre vos modifications en cours. Vous pouvez maintenant passer d'une tâche à une autre en toute sécurité, en sachant que vos changements sont bien sauvegardés dans la pile de stashes.
