---
title: "Chapitre 2 - Installation de Git"
description: "Guide complet pour installer Git sur différents systèmes d'exploitation"
emoji: "⚙️"
---

# Installation de Git


---
<a name="table-des-matieres"></a>

## Table des matières
---


1. [Introduction](#introduction)
2. [Installation sur Windows](#installation-windows)
3. [Installation sur Ubuntu](#installation-ubuntu)
4. [Installation sur macOS](#installation-macos)
5. [Configuration de Git](#configuration-git)
6. [Conclusion](#conclusion)
7. [Référence](#reference)

<br/>
---
<a name="introduction"></a>
# Introduction
---
<br/>

Git est un **système de contrôle de version** distribué qui permet de suivre les modifications dans le code source sur votre machine locale.

#### [⬆️ retour à la table des matières](#table-des-matieres)
<br/>


---
<a name="installation-windows"></a>


# 1-**Étapes d'installation de Git sur Windows**
---
<br/>



<a name="installation-git-windows"></a>
#### Étape 1 : Télécharger Git
1. Rendez-vous sur le site officiel de Git : [git-scm.com](https://git-scm.com).
2. Cliquez sur le bouton "Download for Windows" (Télécharger pour Windows).
3. Sélectionnez la version 64-bit de Git pour Windows et lancez le téléchargement.

<a name="installation-git-windows-etape-2"></a>
#### Étape 2 : Installation de Git
1. Une fois le fichier téléchargé, ouvrez-le pour lancer l'installation.
2. Suivez les instructions de l'installateur et acceptez les paramètres par défaut, sauf si vous avez des exigences spécifiques.

#### https://phoenixnap.com/kb/how-to-install-git-windows


<a name="installation-git-windows-etape-3"></a>
#### Étape 3 : Vérification de l'installation de Git Bash
- Après l'installation, vous devriez voir l'option **Git Bash** dans votre menu contextuel (clic droit sur l'écran de votre bureau ou dans un répertoire).
- Git Bash est une interface de ligne de commande qui vous permet d’exécuter les commandes Git.



<a name="installation-git-windows-etape-4"></a>
#### Étape 4 : Vérifier l'installation de Git
- Ouvrez l'invite de commande (cmd) ou Git Bash.
- Tapez la commande suivante pour vérifier que Git est correctement installé :
  ```
  git --version
  ```
- Si l'installation est réussie, vous verrez la version de Git installée s'afficher, par exemple : `git version 2.34.1`.

#### [⬆️ retour à la table des matières](#table-des-matieres)
<br/>
---
<a name="installation-ubuntu"></a>


# 2-**Installation sur Ubuntu**
---
<br/>

<a name="installation-git-ubuntu-etape-1"></a>
#### Étape 1 : Télécharger Git
1. Consultez le site officiel de Git pour vérifier les étapes d'installation spécifiques à Ubuntu : [git-scm.com](https://git-scm.com).
2. Connectez-vous à votre système Ubuntu.

<a name="installation-git-ubuntu-etape-2"></a>
#### Étape 2 : Désinstaller une ancienne version de Git (si nécessaire)
- Avant d’installer la dernière version de Git, il est recommandé de désinstaller toute ancienne version déjà présente. Exécutez la commande suivante :
  ```
  apt update
  apt git --version
  sudo apt purge git -y
  ```

<a name="installation-git-ubuntu-etape-3"></a>
#### Étape 3 : Installer la dernière version de Git
1. Ajoutez le dépôt PPA pour Git :
   ```
   sudo add-apt-repository ppa:git-core/ppa
   ```
2. Mettez à jour la liste des paquets :
   ```
   sudo apt update
   ```
3. Installez Git :
   ```
   sudo apt install git -y
   ```

<a name="installation-git-ubuntu-etape-4"></a>
#### Étape 4 : Vérifier l'installation de Git
- Après l'installation, vérifiez que Git est bien installé en exécutant :
  ```
  git --version
  ```
- Cela devrait afficher la version de Git installée, par exemple : `git version 2.34.1`.

#### [⬆️ retour à la table des matières](#table-des-matieres)
<br/>

---
<a name="installation-macos"></a>

# 3-**Installation sur macOS**
---
<br/>

<a name="installation-homebrew"></a>
#### Étape 1 : Installer Homebrew (si nécessaire)
- Si vous n’avez pas encore installé Homebrew (un gestionnaire de paquets pour macOS), vous pouvez l’installer en exécutant la commande suivante dans votre terminal :
  ```
  /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
  ```
<a name="installation-git-macos-etape-2"></a>
#### Étape 2 : Installer Git via Homebrew
- Une fois Homebrew installé, installez Git en exécutant la commande suivante :
  ```
  brew install git
  ```

<a name="installation-git-macos-etape-3"></a>
#### Étape 3 : Vérifier l'installation de Git
- Vérifiez que Git est installé en tapant dans votre terminal :
  ```
  git --version
  ```
- Si l’installation est réussie, la version de Git installée s’affichera.

#### [⬆️ retour à la table des matières](#table-des-matieres)
<br/>

---
<a name="configuration-git"></a>


# 4-**Configuration de Git après l'installation**

---
<br/>

Après avoir installé Git sur n'importe quel système d'exploitation, il est nécessaire de le configurer pour la première fois.

### Étape 1 : Configurer le nom d'utilisateur
- Entrez la commande suivante pour configurer votre nom d’utilisateur (qui apparaîtra dans chaque commit que vous faites) :
  ```
  git config --global user.name "Votre Nom"
  ```

### Étape 2 : Configurer l'adresse email
- Configurez votre adresse email qui sera associée à vos commits :
  ```
  git config --global user.email "votre.email@example.com"
  ```

### Étape 3 : Vérifier la configuration de Git
- Pour vérifier toutes vos configurations actuelles de Git, exécutez :
  ```
  git config --list
  ```

#### [⬆️ retour à la table des matières](#table-des-matieres)
<br/>
---
<a name="conclusion"></a>

# Conclusion
---
<br/>

- Git est un outil essentiel pour le suivi des modifications dans le code source et facilite la collaboration entre développeurs. Après avoir suivi les étapes ci-dessus pour l'installation sur Windows, Ubuntu ou macOS, vous pouvez maintenant commencer à utiliser Git pour gérer vos projets de manière efficace. Il est également important de configurer correctement Git après l’installation pour assurer un bon suivi des contributions.

- Ce cours couvre les bases de l'installation de Git sur différents systèmes d'exploitation. Assurez-vous de bien configurer Git pour un usage optimal dans vos projets.

<br/>
#### [⬆️ retour à la table des matières](#table-des-matieres)
---
<a name="reference"></a>

---
# Référence :
---
- https://phoenixnap.com/kb/how-to-install-git-windows


#### [⬆️ retour à la table des matières](#table-des-matieres)

