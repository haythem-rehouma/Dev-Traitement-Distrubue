---
title: "Chapitre 10 - Application de la commande Git Reset"
description: "Découvrez comment utiliser Git Reset pour réinitialiser l'état de votre dépôt à une version antérieure"
emoji: "🔍"
---

---
<a name="table-des-matieres"></a>

## Table des matières
---

### **Objectif :**
Ce guide vous apprendra à utiliser les trois modes principaux de la commande `git reset` : **--soft**, **--mixed**, et **--hard**. Chaque mode a un effet différent sur les commits, la zone de staging, et votre répertoire de travail. Vous apprendrez à réinitialiser l'état de votre dépôt Git et à comprendre les impacts de chaque mode sur vos fichiers.

---

## **Partie 1 : Théorie**

### **Les trois modes principaux de Git Reset :**

1. **`git reset --soft`** :
   - **Effet** : Réinitialise le pointeur **HEAD** (le commit auquel vous êtes actuellement), mais garde les modifications dans la zone de staging.
   - **Quand l’utiliser ?** : Utilisez ce mode si vous voulez refaire un commit sans perdre vos changements, par exemple pour corriger un message de commit ou regrouper plusieurs commits en un seul.
  
2. **`git reset --mixed` (par défaut)** :
   - **Effet** : Réinitialise **HEAD** et enlève les fichiers de la zone de staging, mais conserve les modifications dans votre répertoire de travail.
   - **Quand l’utiliser ?** : Utilisez ce mode si vous avez ajouté des fichiers à la zone de staging par erreur et souhaitez les retirer sans perdre les modifications dans votre répertoire de travail.

3. **`git reset --hard`** :
   - **Effet** : Réinitialise **HEAD**, la zone de staging, et le répertoire de travail. Cela supprime toutes les modifications non committées.
   - **Quand l’utiliser ?** : Utilisez ce mode avec prudence, car il supprime toutes les modifications locales non enregistrées. C'est utile si vous voulez annuler complètement des modifications et revenir à un état antérieur.

---

## **Partie 2 : Pratique**

### **Étape 1 : Cloner le projet existant depuis GitHub**

Pour commencer, nous allons cloner le projet **site-php-1** pour travailler dessus.

1. Ouvrez votre terminal et tapez la commande suivante :
   
   ```bash
   git clone https://github.com/hrhouma1/site-php-1.git
   ```

2. Entrez dans le répertoire du projet cloné :
   
   ```bash
   cd site-php-1
   ```

---

### **Étape 2 : Créer une nouvelle branche pour tester `git reset`**

Nous allons créer une nouvelle branche pour faire des modifications et tester les différents modes de `git reset`.

1. Créez une nouvelle branche :
   
   ```bash
   git checkout -b reset-test-branch
   ```

---

### **Étape 3 : Faire plusieurs modifications et commits**

Nous allons maintenant faire des petites modifications dans les fichiers, créer plusieurs commits, et ensuite utiliser `git reset` pour voir comment cela fonctionne.

#### **1. Modifier et committer `app.js` :**

1. Ouvrez le fichier `app.js` et ajoutez la ligne suivante :
   
   ```javascript
   console.log("Test de git reset --soft");
   ```

2. Ensuite, ajoutez le fichier à la zone de staging et créez un commit :
   
   ```bash
   git add app.js
   git commit -m "Ajout d'un log dans app.js pour git reset --soft"
   ```

#### **2. Modifier et committer `index.php` :**

1. Ouvrez le fichier `index.php` et ajoutez cette ligne en haut du fichier :
   
   ```php
   echo "Test de git reset --mixed";
   ```

2. Ensuite, ajoutez le fichier à la zone de staging et créez un commit :
   
   ```bash
   git add index.php
   git commit -m "Ajout d'un echo dans index.php pour git reset --mixed"
   ```

#### **3. Modifier et committer `connection.php` :**

1. Ouvrez le fichier `connection.php` et ajoutez cette ligne en haut du fichier :
   
   ```php
   echo "Test de git reset --hard";
   ```

2. Ensuite, ajoutez le fichier à la zone de staging et créez un commit :
   
   ```bash
   git add connection.php
   git commit -m "Ajout d'un echo dans connection.php pour git reset --hard"
   ```

#### **4. Vérifier l'historique des commits :**

Tapez la commande suivante pour voir l’historique des trois commits que nous venons de créer :

```bash
git log --oneline
```

Vous devriez voir un résultat comme celui-ci :

```
f24c8ab Ajout d'un echo dans connection.php pour git reset --hard
a18e7f9 Ajout d'un echo dans index.php pour git reset --mixed
6b72c8d Ajout d'un log dans app.js pour git reset --soft
```

---

### **Étape 4 : Utiliser `git reset --soft`**

**But :** Nous allons utiliser `git reset --soft` pour revenir avant le premier commit, tout en gardant les fichiers dans la zone de staging. Cela nous permettra de modifier le commit ou de regrouper plusieurs commits en un seul.

1. **Réinitialiser avec `--soft`** :

   Exécutez la commande suivante pour réinitialiser à l'état avant le premier commit :

   ```bash
   git reset --soft HEAD~3
   ```

2. **Vérifier l'état :**

   Tapez `git status`. Vous verrez que tous vos fichiers modifiés sont toujours dans la zone de staging, prêts à être committés de nouveau. Vous pouvez maintenant les committer à nouveau ou les regrouper en un seul commit.

3. **Créer un nouveau commit** :

   Créez un nouveau commit unique qui combine les trois précédents :

   ```bash
   git commit -m "Regroupement des modifications pour app.js, index.php, et connection.php"
   ```

---

### **Étape 5 : Utiliser `git reset --mixed`**

**But :** `git reset --mixed` enlève les fichiers de la zone de staging, mais garde les modifications dans le répertoire de travail.

1. **Ajoutez `app.js` à la zone de staging** :

   ```bash
   git add app.js
   ```

2. **Réinitialiser avec `--mixed`** :

   Exécutez la commande suivante pour retirer le fichier `app.js` de la zone de staging tout en gardant les modifications locales :

   ```bash
   git reset --mixed
   ```

3. **Vérifier l'état :**

   Tapez `git status`. Vous verrez que `app.js` est retiré de la zone de staging, mais les modifications sont toujours présentes dans le répertoire de travail. Si vous regardez dans l'éditeur, vous verrez toujours la ligne ajoutée dans `app.js`.

---

### **Étape 6 : Utiliser `git reset --hard`**

**But :** `git reset --hard` réinitialise tout : HEAD, la zone de staging, et le répertoire de travail. Cela supprime toutes les modifications non committées. **Attention, cette commande est irréversible !**

1. **Modifiez `index.php` :**

   Ouvrez le fichier `index.php` et ajoutez cette ligne :
   
   ```php
   echo "Ceci est un test pour git reset --hard";
   ```

2. **Réinitialiser avec `--hard`** :

   Exécutez la commande suivante pour annuler toutes les modifications locales, y compris celle que vous venez de faire dans `index.php` :

   ```bash
   git reset --hard
   ```

3. **Vérifier l'état :**

   Ouvrez à nouveau `index.php` dans votre éditeur. Vous verrez que la ligne que vous avez ajoutée a été supprimée et que toutes les modifications locales ont été annulées.

---

### **Étape 7 : Pousser la branche vers GitHub**

Si vous avez terminé vos expérimentations avec `git reset` et que vous souhaitez pousser la branche vers GitHub, utilisez la commande suivante :

```bash
git push -u origin reset-test-branch
```

---

## **Résumé des commandes**

1. **Cloner le projet** :
   ```bash
   git clone https://github.com/hrhouma1/site-php-1.git
   cd site-php-1
   ```

2. **Créer une nouvelle branche** :
   ```bash
   git checkout -b reset-test-branch
   ```

3. **Effectuer plusieurs commits** :
   - `git add app.js`
   - `git commit -m "Ajout d'un log dans app.js pour git reset --soft"`
   - `git add index.php`
   - `git commit -m "Ajout d'un echo dans index.php pour git reset --mixed"`
   - `git add connection.php`
   - `git commit -m "Ajout d'un echo dans connection.php pour git reset --hard"`

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
   git push -u origin reset-test-branch
   ```

