---
title: "Chapitre 7 - Etude de cas : Merge vs Rebase"
description: "Comprendre les différences entre git merge et git rebase"
emoji: "🔍"
---



## Etude de cas : Merge vs Rebase

```bash
git init

touch fichier1.txt
touch fichier2.txt
touch fichier3.txt
``` 

```bash
code .
git status
git add fichier1.txt
git status
```

```bash
git config --global user.name "John Doe"
git config --global user.email "john.doe@example.com"
```

```bash
git config --local user.name "John Doe"
git config --local user.email "john.doe@example.com"
```

```bash
git commit -m "Ajout du fichier 1"
git status
```

```bash
git add fichier2.txt
git commit -m "Ajout du fichier 2"
git status
```

```bash
git add fichier3.txt
git commit -m "Ajout du fichier 3"
git status
```

```bash
git log 
git log --oneline
git log --graph
git log --oneline --graph
```

```bash
git checkout -b hotfix
git branch
ls
git status
touch fichier4.txt
git add fichier4.txt
git commit -m "Ajout du fichier 4"
git status
git log
```

```bash
git checkout master
git log
```

```bash
git remote add origin https://github.com/JohnDoe/mon-projet.git
git branch -M main
git push -u origin main
```

```bash
git checkout hotfix
git push --set-upstream origin hotfix
```


# Merge fast-forward
```bash
git checkout master
git merge hotfix
git push
```

# Merge three-way
```bash
git branch
git checkout master
git checkout hotfix
touch fichier5_from_hotfix.txt
git add fichier5_from_hotfix.txt
git commit -m "Ajout du fichier 5 from hotfix"
```

```bash
git checkout main
touch fichier5_from_main.txt
git add fichier5_from_main.txt
git commit -m "Ajout du fichier 5 from main"
```

```bash
git merge hotfix
git push 
git checkout hotfix
git status
git push --set-upstream origin hotfix
```

```bash
git checkout main
git log --graph
```

# Merge conflict
```bash
git checkout hotfix
touch index.html
echo "Hello from hotfix" > index.html
git add index.html
git commit -m "Ajout du fichier index.html from hotfix"
```

```bash
git checkout main
touch index.html
echo "Hello from main" > index.html
git add index.html
git commit -m "Ajout du fichier index.html from main"
```

```bash
git merge hotfix
```



```bash

> # Résolution du conflit
> Ouvrez le fichier index.html dans un éditeur de texte
> Vous verrez quelque chose comme:

```bash
> #`<<<<<<<` HEAD
> # Hello from main
> # =======
> # Hello from hotfix
> #`>>>>>>>` hotfix

```


# Explication des marqueurs de conflit :

```bash
> # `<<<<<<<` HEAD
> # Ce marqueur indique le début du conflit. 
> # Les lignes qui suivent représentent le contenu de la branche courante (HEAD)

> # =======
> # Ce marqueur sépare les deux versions en conflit.
> # Au-dessus se trouve le contenu de la branche courante,
> # En-dessous se trouve le contenu de la branche qu'on essaie de fusionner

> # `>>>>>>>` hotfix 
> # Ce marqueur indique la fin du conflit.
> # Les lignes au-dessus (jusqu'au =======) représentent le contenu 
> # de la branche qu'on essaie de fusionner (ici 'hotfix')


> # Choisissez la version à conserver ou fusionnez les deux
> # Supprimez les marqueurs de conflit `<<<<<<<` HEAD et `>>>>>>>` hotfix et aussi =======
> # Sauvegardez le fichier
> #
> # Ensuite:
```

 
```bash
git add index.html
git commit -m "Résolution du conflit de fusion"
```




```


# Merge vs rebase


La différence entre git merge et git rebase :

Git rebase réécrit l'historique en réappliquant les commits d'une branche sur une autre. Il prend tous les commits de la branche de travail et les rejoue un par un sur la branche cible (ex: main), créant ainsi un historique parfaitement linéaire. Les avantages sont :
- Un historique plus propre et plus facile à suivre
- Évite les commits de fusion superflus
- Permet de garder une chronologie claire des développements

Git merge, quant à lui, crée un nouveau commit de fusion qui unit deux branches. Cette approche :
- Préserve l'historique complet et la chronologie réelle du développement
- Montre clairement où les branches ont divergé et fusionné
- Est plus sûre car elle ne modifie pas l'historique existant
- Est recommandée pour les branches partagées avec d'autres développeurs

Le merge fast-forward est un cas particulier qui se produit quand la branche à fusionner est directement devant la branche cible, sans commits divergents. Dans ce cas, Git déplace simplement le pointeur de la branche cible vers l'avant, produisant un résultat similaire à rebase mais sans réécrire l'historique.

Recommandations d'utilisation :
- Utilisez rebase pour nettoyer l'historique de vos branches locales 
- Préférez merge pour les branches partagées ou publiques
- Activez le fast-forward quand possible pour éviter les commits de fusion inutiles
- Après une fusion réussie (merge ou rebase), vous pouvez supprimer la branche source avec git branch -d <nom-branche> car ses modifications ont été intégrées dans la branche cible.



# Démonstration pratique de merge vs rebase



La Règle d'Or du Rebase
Une fois que vous comprenez ce qu'est le rebase, la chose la plus importante à apprendre est quand ne pas l'utiliser. La règle d'or du git rebase est de ne jamais l'utiliser sur des branches publiques.


- Créez un nouveau dépôt et initialisez-le :

```bash
git init
touch fichier1.txt
git add fichier1.txt
git commit -m "first commit"
git status
git log
touch fichier2.txt
git add fichier2.txt
git commit -m "second commit"
git status
git log
git branch -M master
git push -u origin master
git log
gitk
```







Le gitk est l'explorateur de dépôt git. Voici ce qui se passe lors de l'exécution de la commande git rebase master. La branche feature est rejouée au-dessus de la branche master.


- Créez une branche feature :

```bash
git checkout -b feature
echo "Hello from feature modified second file" > fichier2.txt
git add fichier2.txt
git commit -m "modified second file"
git status
git log
gitk
```

```bash
echo "Hello from feature modified first file" > fichier1.txt
git add fichier1.txt
git commit -m "modified first file"
git status
git log
gitk
```

```bash
git checkout feature
git log
```




```
HOME@LAPTOP-MSQ6RBUO MINGW64 /g/git-tutorials (feature)
$ git log

commit aeb86d6c4d4af765f98879a94d6ede56bde22018 (HEAD -> feature)
Author: hrehouma <hrehouma@gmail.com>
Date:   Thu Aug 1 16:38:22 2019 +0530

    modified first file

commit e51753de3a28f28f88047804f8b1e9988120ebf2
Author: hrehouma <hrehouma@gmail.com>
Date:   Thu Aug 1 16:37:15 2019 +0530

    modified second file

commit 4cac872867b8f03986c6d7d4843ad5532d0ea0b (master)
Author: hrehouma <hrehouma@gmail.com>
Date:   Thu Aug 1 16:34:10 2019 +0530

    second commit

commit 3fcf3e1f4e253bc6cccb1139d1c9e1b97e24078e
Author: hrehouma <hrehouma@gmail.com>
Date:   Thu Aug 1 16:33:13 2019 +0530

    first commit
```










































Note : Pendant le développement, d'autres développeurs peuvent avoir poussé des changements sur la branche master distante.

Par exemple, si vos collègues ont créé un fichier "fichier3.txt" sur master et l'ont poussé, vous devrez synchroniser votre code avec git pull.

Regardons l'historique des commits de master :

```bash
git checkout master
git pull
git log
```


```
HOME@LAPTOP-MSQ6RBUO MINGW64 /g/git-tutorials (master)
$ git log

commit c6305de0c9d0faa5e15bb11df8a1ffe2eba29411 (HEAD -> master)
Author: hrehouma <hrehouma@gmail.com>
Date:   Thu Aug 1 16:39:40 2019 +0530

    added third file

commit 4cac872867b8f03986c6d7d4843ad5532d0ea0b
Author: hrehouma <hrehouma@gmail.com>
Date:   Thu Aug 1 16:34:10 2019 +0530

    second commit

commit 3fcf3e1f4e253bc6cccb1139d1c9e1b97e24078e
Author: hrehouma <hrehouma@gmail.com>
Date:   Thu Aug 1 16:33:13 2019 +0530

    first commit
```

Cela reflète les commits présents sur la branche `master`




Maintenant que je suis terminé avec le développement de ma fonctionnalité, je veux fusionner les modifications sur la branche master. Pour ce faire, je vais vérifier ma branche feature et la rebaser sur ma branche master locale. Cela ré-ancrera ma branche contre les dernières modifications. De plus, à ce stade, Git me dira si j'ai des conflits et je peux les régler sur ma branche.


```bash
git checkout feature    
git rebase master
gitk
```

```
git-tutorials: All files - gitk
---------------------------------
File  Edit  View  Help

o feature          modified first file
|
o                 modified second file
|
o master          added third file <== ajout du troisième fichier
|
o                 second commit
|
o                 first commit
```

Notez que ma branche feature n'a pas de conflits, je peux donc retourner sur ma branche master et y placer mes modifications.



git checkout master
git rebase feature
gitk


```
o feature  master      modified first file
|
o                    modified second file
|
o                    added third file
|
o                    second commit
|
o                    first commit
```


Comme vous pouvez le constater, après avoir exécuté la commande git rebase feature, la branche master pointe désormais au même endroit que la branche feature. C'est-à-dire que tous les commits sont alignés sur une seule ligne et l'historique des commits a été réécrit.

Maintenant, je devrais être en mesure de pousser mes modifications vers la branche master distante sans problème en utilisant la commande git push.


Référence : https://medium.com/mindorks/understanding-git-merge-git-rebase-88e2afd42671

---
