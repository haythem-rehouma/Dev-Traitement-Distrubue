---
title: "Chapitre 12 - Fichier .gitignore"
description: "Découvrez comment utiliser le fichier .gitignore pour exclure certains fichiers et dossiers du suivi par Git"
emoji: "🔍"
---

---
<a name="table-des-matieres"></a>

## Table des matières
---

### **Objectif :**
Ce guide vous apprendra à utiliser le fichier `.gitignore` pour exclure certains fichiers et dossiers du suivi par Git. Vous allez comprendre ce qu’est un fichier `.gitignore`, pourquoi il est important, et comment l’utiliser efficacement dans votre projet.

---

## **Partie 1 : Théorie**

### **Qu'est-ce qu'un fichier `.gitignore` ?**

- **Le fichier `.gitignore`** est un fichier texte spécial qui indique à Git quels fichiers ou répertoires ne doivent **pas** être suivis ou inclus dans les commits. Par exemple, vous pouvez l'utiliser pour exclure des fichiers de configuration, des dossiers de build, des fichiers temporaires, ou des fichiers contenant des informations sensibles.

### **Pourquoi utiliser `.gitignore` ?**

- **Éviter les fichiers inutiles** : Par exemple, les fichiers générés automatiquement comme les fichiers de log ou les dossiers `node_modules` dans un projet Node.js.
- **Protéger les informations sensibles** : Comme les fichiers contenant des mots de passe ou des informations d'accès (fichiers `.env`).
- **Réduire la taille du dépôt** : En excluant les fichiers inutiles, votre dépôt Git reste plus léger et plus rapide.

---

## **Partie 2 : Pratique**

Nous allons maintenant utiliser le fichier `.gitignore` dans le cadre du projet **site-php-1**. Vous allez apprendre à créer et configurer un fichier `.gitignore`, puis à tester son effet sur les fichiers de votre projet.

### **Étape 1 : Cloner le projet existant depuis GitHub**

Comme d'habitude, nous allons commencer par cloner le projet **site-php-1** pour travailler dessus.

1. Ouvrez votre terminal et exécutez la commande suivante :

   ```bash
   git clone https://github.com/hrhouma1/site-php-1.git
   ```

2. Ensuite, entrez dans le répertoire du projet cloné :

   ```bash
   cd site-php-1
   ```

---

### **Étape 2 : Créer un fichier `.gitignore`**

Nous allons créer un fichier `.gitignore` pour indiquer à Git quels fichiers et dossiers ne doivent pas être suivis.

1. **Créer le fichier `.gitignore`** :

   Tapez la commande suivante pour créer un fichier `.gitignore` à la racine du projet :

   ```bash
   touch .gitignore
   ```

   Vous venez de créer un fichier texte spécial appelé `.gitignore`.

2. **Ouvrir le fichier `.gitignore`** :

   Ouvrez le fichier `.gitignore` dans votre éditeur de texte (VSCode, Sublime Text, etc.).

---

### **Étape 3 : Ajouter des fichiers et dossiers à ignorer**

Nous allons maintenant ajouter des fichiers et dossiers spécifiques que Git ne doit pas suivre. Voici quelques exemples courants.

1. **Ignorer les fichiers de configuration sensibles** :

   Si vous avez un fichier contenant des informations sensibles, comme un fichier `.env` pour les variables d'environnement (mot de passe, clés API), vous pouvez dire à Git de l'ignorer en ajoutant cette ligne dans `.gitignore` :

   ```
   .env
   ```

   Cela signifie que Git n'inclura pas le fichier `.env` dans les commits.

2. **Ignorer les fichiers de log** :

   Si votre projet génère des fichiers de log (comme `error.log` ou `debug.log`), vous pouvez les ignorer en ajoutant ces lignes :

   ```
   *.log
   ```

   Cela va exclure tous les fichiers avec l'extension `.log`.

3. **Ignorer un répertoire complet** :

   Par exemple, si vous avez un répertoire temporaire nommé `tmp/` que vous ne voulez pas suivre, ajoutez cette ligne :

   ```
   tmp/
   ```

4. **Ignorer tous les fichiers dans un répertoire sauf certains** :

   Par exemple, si vous voulez ignorer tous les fichiers d’un répertoire `uploads/` sauf les fichiers `.jpg`, vous pouvez ajouter les lignes suivantes :

   ```
   uploads/*
   !uploads/*.jpg
   ```

   Cela signifie que Git ignorera tous les fichiers dans `uploads/`, sauf les fichiers `.jpg`.

---

### **Étape 4 : Tester le fichier `.gitignore`**

Nous allons maintenant vérifier que Git ignore bien les fichiers et dossiers que nous avons spécifiés dans `.gitignore`.

#### **1. Créer un fichier de test `.env` :**

1. Créez un fichier nommé `.env` qui contiendra des informations sensibles. Tapez la commande suivante :

   ```bash
   touch .env
   ```

2. Ouvrez le fichier `.env` et ajoutez-y quelques informations fictives :

   ```bash
   DB_PASSWORD=supersecretpassword
   ```

#### **2. Créer un fichier de log et un répertoire temporaire :**

1. Créez un fichier de log `error.log` :

   ```bash
   touch error.log
   ```

2. Créez un répertoire temporaire `tmp/` et un fichier dedans :

   ```bash
   mkdir tmp
   touch tmp/tempfile.txt
   ```

#### **3. Vérifier l'état de Git :**

Maintenant que nous avons créé des fichiers que nous voulons ignorer, vérifiez l'état de Git pour voir s'il les ignore correctement.

1. Tapez la commande suivante pour vérifier l'état de votre dépôt :

   ```bash
   git status
   ```

   Si votre `.gitignore` est configuré correctement, vous ne devriez pas voir les fichiers `.env`, `error.log`, ou `tmp/` dans la liste des fichiers à suivre.

---

### **Étape 5 : Ajouter et committer le fichier `.gitignore`**

Une fois que vous avez configuré votre fichier `.gitignore`, vous devez l'ajouter à la zone de staging et le committer pour le partager avec votre équipe.

1. **Ajouter le fichier `.gitignore` à la zone de staging** :

   ```bash
   git add .gitignore
   ```

2. **Créer un commit pour le fichier `.gitignore`** :

   ```bash
   git commit -m "Ajout du fichier .gitignore pour exclure les fichiers sensibles et temporaires"
   ```

---

### **Étape 6 : Pousser la branche vers GitHub**

Si vous avez terminé, vous pouvez pousser votre branche vers GitHub pour partager votre fichier `.gitignore` avec votre équipe.

1. **Pousser les changements** :

   ```bash
   git push -u origin main
   ```

Cela mettra à jour le dépôt GitHub avec votre nouveau fichier `.gitignore`.

---

## **Résumé des commandes**

1. **Cloner le projet** :
   ```bash
   git clone https://github.com/hrhouma1/site-php-1.git
   cd site-php-1
   ```

2. **Créer un fichier `.gitignore`** :
   ```bash
   touch .gitignore
   ```

3. **Ajouter des fichiers à ignorer** :
   - Ignorer un fichier `.env` :
     ```
     .env
     ```

   - Ignorer tous les fichiers `.log` :
     ```
     *.log
     ```

   - Ignorer un répertoire entier `tmp/` :
     ```
     tmp/
     ```

4. **Tester le fichier `.gitignore`** :
   - Créer un fichier `.env` :
     ```bash
     touch .env
     ```

   - Créer un fichier `error.log` :
     ```bash
     touch error.log
     ```

   - Créer un répertoire `tmp/` :
     ```bash
     mkdir tmp
     touch tmp/tempfile.txt
     ```

   - Vérifier que ces fichiers sont bien ignorés par Git :
     ```bash
     git status
     ```

5. **Ajouter et committer le fichier `.gitignore`** :
   ```bash
   git add .gitignore
   git commit -m "Ajout du fichier .gitignore pour exclure les fichiers sensibles et temporaires"
   ```

6. **Pousser la branche vers GitHub** :
   ```bash
   git push -u origin main
   ```

---

### **Conclusion**

Ce guide vous a montré comment créer et configurer un fichier `.gitignore` pour exclure des fichiers spécifiques du suivi par Git. En utilisant `.gitignore`, vous pouvez protéger vos informations sensibles, éviter les fichiers inutiles dans votre dépôt, et réduire la taille de votre projet.
