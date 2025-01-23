---
title: "Chapitre 14 - Synchronisation avec GitHub"
description: "Découvrez comment synchroniser votre dépôt local Git avec GitHub. Vous apprendrez à pousser vos changements locaux vers GitHub, à récupérer les changements distants, et à garder votre dépôt à jour avec les dernières modifications de votre équipe."
emoji: "🔍"
---

---
<a name="table-des-matieres"></a>

## Table des matières
---

### **Objectif :**
Ce guide vous apprendra à synchroniser votre dépôt local Git avec GitHub. Vous apprendrez à pousser vos changements locaux vers GitHub, à récupérer les changements distants, et à garder votre dépôt à jour avec les dernières modifications de votre équipe.

---

## **Partie 1 : Théorie**

### **Qu'est-ce que la synchronisation Git avec GitHub ?**

La synchronisation entre un dépôt Git local et un dépôt GitHub permet de maintenir les deux à jour. Voici les actions principales :

1. **`git push`** : Envoie vos commits locaux vers le dépôt GitHub.
2. **`git pull`** : Récupère les modifications effectuées dans le dépôt GitHub par d'autres collaborateurs et les fusionne avec votre dépôt local.
3. **`git fetch`** : Récupère les modifications du dépôt GitHub, mais ne les fusionne pas automatiquement. Vous devez ensuite examiner ou fusionner ces modifications manuellement.
4. **`git clone`** : Télécharge un dépôt GitHub dans un nouveau répertoire local.

---

## **Partie 2 : Pratique**

Nous allons maintenant synchroniser notre projet local avec GitHub.

### **Étape 1 : Cloner un dépôt GitHub existant**

Si vous n'avez pas encore de copie locale du projet, vous devez d'abord cloner le dépôt GitHub.

1. Ouvrez votre terminal et exécutez cette commande pour cloner le projet **site-php-1** :

   ```bash
   git clone https://github.com/hrhouma1/site-php-1.git
   ```

2. Entrez dans le répertoire cloné :

   ```bash
   cd site-php-1
   ```

Cela vous donne une copie locale du projet sur laquelle vous pouvez travailler.

---

### **Étape 2 : Ajouter et committer des modifications locales**

Nous allons maintenant faire quelques modifications dans notre projet local et les pousser vers GitHub.

#### **1. Modifier `app.js` :**

1. Ouvrez le fichier `app.js` et ajoutez cette ligne :

   ```javascript
   console.log("Modification locale pour la synchronisation avec GitHub");
   ```

2. Sauvegardez le fichier.

#### **2. Ajouter les modifications à la zone de staging et les committer :**

1. **Ajouter le fichier modifié à la zone de staging** :

   ```bash
   git add app.js
   ```

2. **Créer un commit pour ces modifications** :

   ```bash
   git commit -m "Ajout d'une ligne de log dans app.js pour la synchronisation avec GitHub"
   ```

---

### **Étape 3 : Pousser les modifications locales vers GitHub avec `git push`**

Une fois que vous avez committé les modifications localement, vous pouvez les envoyer sur GitHub avec `git push`.

1. Exécutez la commande suivante pour pousser les modifications vers le dépôt distant :

   ```bash
   git push origin main
   ```

- **`origin`** : C’est le nom par défaut du dépôt distant (GitHub).
- **`main`** : C’est la branche principale du projet.

Cette commande envoie tous vos commits locaux vers GitHub et met à jour le dépôt distant.

---

### **Étape 4 : Récupérer les changements depuis GitHub avec `git pull`**

Si des collaborateurs ont fait des modifications dans le dépôt GitHub, vous devez les récupérer pour les synchroniser avec votre dépôt local.

1. Exécutez la commande suivante pour récupérer et fusionner les modifications depuis GitHub :

   ```bash
   git pull origin main
   ```

- **`pull`** : Cette commande fait deux actions. Elle récupère d'abord les changements (`fetch`), puis elle les fusionne automatiquement avec votre travail local (`merge`).

**Cas où vous pourriez avoir un conflit :**
- Si vous et votre collaborateur avez modifié le même fichier de manière incompatible, un **conflit de fusion** pourrait se produire. Git vous demandera alors de résoudre le conflit manuellement.

---

### **Étape 5 : Travailler avec `git fetch` pour plus de contrôle**

Si vous voulez récupérer les changements distants sans les fusionner automatiquement, vous pouvez utiliser `git fetch` :

1. Exécutez cette commande pour récupérer les changements sans les fusionner :

   ```bash
   git fetch origin
   ```

Cette commande télécharge les modifications de GitHub mais ne les fusionne pas dans votre dépôt local. Vous pouvez ensuite inspecter les différences et décider de la marche à suivre.

Pour voir quelles branches ont été mises à jour après un `fetch`, tapez :

```bash
git status
```

---

### **Étape 6 : Créer une nouvelle branche et la pousser vers GitHub**

Si vous voulez travailler sur une nouvelle fonctionnalité sans affecter la branche principale, vous pouvez créer une nouvelle branche et la pousser vers GitHub.

1. **Créer une nouvelle branche** :

   ```bash
   git checkout -b feature/new-functionality
   ```

2. **Faire des modifications dans cette branche** (par exemple, ajouter une ligne dans `app.js`).

3. **Pousser cette nouvelle branche vers GitHub** :

   ```bash
   git push -u origin feature/new-functionality
   ```

Cette commande crée la branche `feature/new-functionality` sur GitHub et y pousse vos modifications.

---

### **Étape 7 : Gérer les conflits lors de la synchronisation**

Lorsque plusieurs personnes travaillent sur le même projet, des conflits peuvent survenir si deux personnes modifient le même fichier de manière incompatible.

#### **1. Comment résoudre un conflit de fusion :**

1. Si un conflit survient, Git vous le dira après avoir tenté une fusion (lors d'un `git pull` ou d'un `git merge`).

2. Ouvrez les fichiers indiqués par Git dans votre éditeur de texte et cherchez les lignes marquées comme suit :

   ```
   <<<<<<< HEAD
   Votre version locale
   =======
   La version distante de GitHub
   >>>>>>> nom_du_commit_distant
   ```

3. Modifiez le fichier pour garder la bonne version (ou une combinaison des deux).

4. Après avoir résolu le conflit, ajoutez le fichier modifié à la zone de staging :

   ```bash
   git add <nom_du_fichier>
   ```

5. Puis, complétez la fusion avec un commit :

   ```bash
   git commit -m "Résolution du conflit dans <nom_du_fichier>"
   ```

---

### **Étape 8 : Résumé des commandes de synchronisation avec GitHub**

1. **Cloner un dépôt GitHub** :
   ```bash
   git clone https://github.com/hrhouma1/site-php-1.git
   ```

2. **Pousser des modifications locales vers GitHub** :
   ```bash
   git push origin main
   ```

3. **Récupérer et fusionner les modifications depuis GitHub** :
   ```bash
   git pull origin main
   ```

4. **Récupérer sans fusionner automatiquement** :
   ```bash
   git fetch origin
   ```

5. **Créer une nouvelle branche et la pousser vers GitHub** :
   ```bash
   git push -u origin feature/new-functionality
   ```

6. **Résoudre un conflit de fusion** :
   - Ouvrir le fichier avec un conflit, choisir quelle version garder.
   - Ajouter le fichier modifié :
     ```bash
     git add <nom_du_fichier>
     ```
   - Créer un commit :
     ```bash
     git commit -m "Résolution du conflit"
     ```

---

### **Conclusion**

Ce guide vous a montré comment synchroniser votre dépôt Git local avec GitHub. Vous avez appris à **pousser** vos modifications vers GitHub, à **récupérer** celles de vos collaborateurs, et à gérer les éventuels conflits. Utiliser efficacement `git push`, `git pull`, et `git fetch` vous permet de garder votre travail synchronisé avec votre équipe.
