---
title: "Chapitre 11 - Commande Git Revert"
description: "Découvrez comment utiliser Git Revert pour annuler un ou plusieurs commits sans supprimer l'historique des modifications"
emoji: "🔍"
---

---
<a name="table-des-matieres"></a>

## Table des matières
---

### **Objectif :**
Ce guide vous apprendra à utiliser la commande `git revert`, qui permet d'annuler un ou plusieurs commits sans supprimer l'historique des modifications. Contrairement à `git reset`, `git revert` conserve les commits et ajoute un nouveau commit qui "inverse" les changements d'un commit spécifique.

---

## **Partie 1 : Théorie**

### **Qu'est-ce que `git revert` ?**

- **`git revert`** est une commande qui permet de revenir sur les changements apportés par un commit spécifique, en créant un nouveau commit "inverse". Contrairement à `git reset`, qui modifie l'historique en supprimant les commits, `git revert` conserve tous les commits précédents tout en annulant leurs effets. Cela rend la commande plus sûre à utiliser, surtout dans les environnements collaboratifs.

### **Quand utiliser `git revert` ?**
- **Corriger un commit erroné** : Si un commit a introduit un bug ou une erreur, vous pouvez utiliser `git revert` pour revenir en arrière tout en conservant l'historique des modifications.
- **Travail en équipe** : Lorsque vous collaborez avec d'autres développeurs, `git revert` est plus sûr que `git reset`, car il ne modifie pas l'historique partagé.
  
---

## **Partie 2 : Pratique**

Nous allons maintenant mettre en pratique l'utilisation de `git revert` dans un projet Git.

### **Étape 1 : Cloner le projet existant depuis GitHub**

Comme toujours, nous allons commencer par cloner le projet **site-php-1** pour travailler dessus.

1. Ouvrez votre terminal et exécutez la commande suivante pour cloner le projet :

   ```bash
   git clone https://github.com/hrhouma1/site-php-1.git
   ```

2. Ensuite, entrez dans le répertoire du projet cloné :

   ```bash
   cd site-php-1
   ```

---

### **Étape 2 : Créer une nouvelle branche pour tester `git revert`**

Nous allons créer une nouvelle branche pour faire des modifications et tester la commande `git revert`.

1. Créez une nouvelle branche appelée `revert-test-branch` :

   ```bash
   git checkout -b revert-test-branch
   ```

---

### **Étape 3 : Faire plusieurs modifications et commits**

Nous allons maintenant faire des modifications dans le projet et créer plusieurs commits pour ensuite annuler l'un d'entre eux avec `git revert`.

#### **1. Modifier et committer `app.js` :**

1. Ouvrez le fichier `app.js` dans votre éditeur de texte et ajoutez cette ligne au début du fichier :

   ```javascript
   console.log("Modification pour git revert dans app.js");
   ```

2. Ajoutez le fichier à la zone de staging et créez un commit :

   ```bash
   git add app.js
   git commit -m "Ajout d'un log dans app.js pour git revert"
   ```

#### **2. Modifier et committer `index.php` :**

1. Ouvrez le fichier `index.php` et ajoutez cette ligne en haut du fichier :

   ```php
   echo "Modification pour git revert dans index.php";
   ```

2. Ajoutez le fichier à la zone de staging et créez un commit :

   ```bash
   git add index.php
   git commit -m "Ajout d'un echo dans index.php pour git revert"
   ```

#### **3. Modifier et committer `connection.php` :**

1. Ouvrez le fichier `connection.php` et ajoutez cette ligne en haut du fichier :

   ```php
   echo "Test pour git revert dans connection.php";
   ```

2. Ajoutez le fichier à la zone de staging et créez un commit :

   ```bash
   git add connection.php
   git commit -m "Ajout d'un echo dans connection.php pour git revert"
   ```

#### **4. Vérifier l'historique des commits :**

Maintenant que nous avons fait trois commits, tapez la commande suivante pour vérifier l’historique :

```bash
git log --oneline
```

Vous devriez voir un résultat similaire à ceci :

```
f8c5a9a Ajout d'un echo dans connection.php pour git revert
9c7e1b4 Ajout d'un echo dans index.php pour git revert
c5a3e9d Ajout d'un log dans app.js pour git revert
```

---

### **Étape 4 : Utiliser `git revert` pour annuler un commit**

Nous allons maintenant annuler un des commits que nous avons faits.

**But :** Revenir en arrière et annuler le commit qui a modifié `index.php` sans supprimer l'historique.

1. **Identifier l'ID du commit à annuler** :

   Regardez l'historique des commits que vous avez obtenus à l'étape précédente. Trouvez l'ID du commit qui a modifié `index.php` (dans notre exemple, l'ID est `9c7e1b4`).

2. **Exécuter la commande `git revert`** :

   Pour annuler ce commit, exécutez la commande suivante en remplaçant `9c7e1b4` par l'ID de votre commit :

   ```bash
   git revert 9c7e1b4
   ```

   Cette commande crée un nouveau commit qui inverse les changements apportés par le commit `9c7e1b4`.

3. **Modifier le message du commit (facultatif)** :

   Après avoir exécuté `git revert`, Git vous demandera de modifier le message du commit qui annule les changements. Vous pouvez laisser le message par défaut ou le personnaliser, puis enregistrez et fermez l'éditeur.

4. **Vérifier l’historique après le revert** :

   Tapez la commande suivante pour voir le nouvel historique des commits :

   ```bash
   git log --oneline
   ```

   Vous devriez voir quelque chose comme ceci :

   ```
   d7e6a0f Revert "Ajout d'un echo dans index.php pour git revert"
   f8c5a9a Ajout d'un echo dans connection.php pour git revert
   9c7e1b4 Ajout d'un echo dans index.php pour git revert
   c5a3e9d Ajout d'un log dans app.js pour git revert
   ```

   Le commit `d7e6a0f` est le nouveau commit créé par `git revert`, qui inverse les changements du commit `9c7e1b4`.

---

### **Étape 5 : Vérifier les modifications dans le fichier `index.php`**

Après avoir annulé le commit qui modifiait `index.php`, nous allons vérifier que les modifications ont bien été annulées.

1. Ouvrez le fichier `index.php` dans votre éditeur de texte.
   
   Vous devriez voir que la ligne que vous aviez ajoutée :
   
   ```php
   echo "Modification pour git revert dans index.php";
   ```

   a été supprimée.

---

### **Étape 6 : Pousser la branche vers GitHub**

Si vous voulez pousser la branche vers GitHub avec vos modifications et le commit `revert`, utilisez la commande suivante :

```bash
git push -u origin revert-test-branch
```

Ensuite, vous pouvez créer une **pull request** sur GitHub si vous souhaitez fusionner ces changements dans la branche principale.

---

## **Résumé des commandes**

1. **Cloner le projet** :
   ```bash
   git clone https://github.com/hrhouma1/site-php-1.git
   cd site-php-1
   ```

2. **Créer une nouvelle branche** :
   ```bash
   git checkout -b revert-test-branch
   ```

3. **Effectuer plusieurs commits** :
   - `git add app.js`
   - `git commit -m "Ajout d'un log dans app.js pour git revert"`
   - `git add index.php`
   - `git commit -m "Ajout d'un echo dans index.php pour git revert"`
   - `git add connection.php`
   - `git commit -m "Ajout d'un echo dans connection.php pour git revert"`

4. **Annuler un commit avec `git revert`** :
   ```bash
   git revert <ID_du_commit>
   ```

5. **Pousser la branche vers GitHub** :
   ```bash
   git push -u origin revert-test-branch
   ```

---

### **Conclusion**

Avec ce guide, vous avez appris à utiliser la commande `git revert` pour annuler un commit sans supprimer l'historique Git. Contrairement à `git reset`, qui modifie l'historique, `git revert` crée un nouveau commit qui inverse les modifications d'un commit précédent. Cela est très utile lorsque vous travaillez en équipe, car cela conserve un historique complet et compréhensible.
