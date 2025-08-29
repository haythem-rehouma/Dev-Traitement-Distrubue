# Les principaux paradigmes de programmation

## 1. **Paradigme impératif**

* **Principe** : Décrire étape par étape *comment* exécuter un programme.
* **Caractéristiques** : instructions séquentielles, variables modifiables, boucles, conditions.
* **Exemple** : C, Pascal, Python (quand on écrit du code procédural).
* **Analogie** : comme une recette de cuisine où l’on suit chaque étape dans l’ordre.

```python
# Exemple impératif : calcul de la somme de 1 à 5
somme = 0
for i in range(1, 6):
    somme += i
print(somme)
```



## 2. **Paradigme procédural**

* **Sous-type de l’impératif** : organise le code en **procédures/fonctions** réutilisables.
* **Caractéristiques** : factorisation du code, évite les répétitions.
* **Langages** : C, Fortran, Ada, Python.
* **Analogie** : une grande recette divisée en sous-recettes (fonctions).

```python
def somme_n(n):
    return sum(range(1, n+1))

print(somme_n(5))
```



## 3. **Paradigme orienté objet (POO)**

* **Principe** : organiser le programme en objets qui combinent **données (attributs)** et **méthodes (comportements)**.
* **Concepts clés** : encapsulation, héritage, polymorphisme.
* **Langages** : Java, C++, C#, Python (POO).
* **Analogie** : comme modéliser des objets du monde réel (une voiture, un étudiant, un compte bancaire).

```python
class CompteBancaire:
    def __init__(self, solde):
        self.solde = solde
    
    def depot(self, montant):
        self.solde += montant

    def retrait(self, montant):
        self.solde -= montant

c = CompteBancaire(100)
c.depot(50)
print(c.solde)  # 150
```



## 4. **Paradigme fonctionnel**

* **Principe** : les programmes sont construits avec des **fonctions pures** (sans effets de bord).
* **Caractéristiques** : immutabilité, récursivité, absence de variables globales.
* **Langages** : Haskell, Lisp, OCaml, Scala ; Python (partiellement).
* **Analogie** : comme les mathématiques : une fonction retourne toujours le même résultat pour les mêmes paramètres.

```python
# Exemple fonctionnel : somme d’une liste avec reduce
from functools import reduce

nums = [1, 2, 3, 4, 5]
somme = reduce(lambda x, y: x + y, nums)
print(somme)
```



## 5. **Paradigme déclaratif**

* **Principe** : dire *ce que l’on veut*, pas *comment le faire*.
* **Exemples** :

  * SQL : `SELECT * FROM etudiants WHERE age > 18;`
  * Prolog : programmation logique.
* **Analogie** : commander un plat au restaurant (vous dites le résultat attendu, pas les étapes de préparation).


## 6. **Paradigmes hybrides**

* La plupart des langages modernes (Python, JavaScript, Scala, Rust) combinent plusieurs paradigmes : impératif + objet + fonctionnel.



# Résumé visuel

| Paradigme     | Idée clé                            | Exemple de langage |
| ------------- | ----------------------------------- | ------------------ |
| Impératif     | Étapes séquentielles                | C, Python          |
| Procédural    | Fonctions réutilisables             | C, Pascal          |
| Orienté Objet | Objets + méthodes                   | Java, C++, Python  |
| Fonctionnel   | Fonctions pures, immutabilité       | Haskell, Lisp      |
| Déclaratif    | Décrire le résultat, pas la méthode | SQL, Prolog        |
| Hybride       | Mélange de paradigmes               | Python, Scala      |

