# Introduction aux paradigmes de programmation (version vulgarisée)

Un **paradigme de programmation**, c’est une manière de **penser** et **organiser** un programme.
Autrement dit : différents styles pour expliquer à l’ordinateur ce qu’on veut qu’il fasse.

Il n’y a pas un seul bon paradigme : chaque approche a ses avantages, et les langages modernes en mélangent souvent plusieurs.



## 1. **Paradigme impératif**

* On dit **exactement quoi faire, étape par étape**.
* Comme une **recette de cuisine** où l’on suit les instructions dans l’ordre.
* Exemples : C, Python (quand on écrit du code simple), Shell scripts.

### Exemple Linux (impératif) :

```bash
# Étape 1 : mettre à jour la liste des paquets
apt update

# Étape 2 : installer le serveur SSH
apt install openssh-server -y

# Étape 3 : vérifier le statut du service
systemctl status ssh

# Étape 4 : activer au démarrage
systemctl enable ssh

# Étape 5 : démarrer le service
systemctl start ssh

# Étape 6 : vérifier à nouveau
systemctl status ssh
```

👉 Ici, vous avez décrit **chaque commande, dans l’ordre**, comme dans une recette.



## 2. **Paradigme procédural**

* C’est de l’impératif, mais on **organise les instructions dans des fonctions** pour réutiliser le code.
* Exemple : un **script Bash** avec une fonction.

```bash
# Script procédural : fonction pour installer SSH
installer_ssh() {
    apt update
    apt install openssh-server -y
    systemctl enable ssh
    systemctl start ssh
}

# On appelle la procédure
installer_ssh
systemctl status ssh
```

👉 Au lieu de réécrire les étapes partout, on les range dans une "procédure".



## 3. **Paradigme orienté objet**

* On pense en termes **d’objets** qui ont des **propriétés** (attributs) et des **actions** (méthodes).
* En Python : un objet *ServeurSSH* qui peut s’installer, démarrer, arrêter.

```python
class ServeurSSH:
    def __init__(self):
        self.installe = False
        self.active = False

    def installer(self):
        print("Installation de SSH...")
        self.installe = True

    def demarrer(self):
        if self.installe:
            print("Démarrage de SSH...")
            self.active = True

ssh = ServeurSSH()
ssh.installer()
ssh.demarrer()
```

👉 On pense comme en vrai : **un serveur SSH** est un objet qui peut être installé ou démarré.



## 4. **Paradigme fonctionnel**

* On utilise des **fonctions pures** (toujours le même résultat avec les mêmes entrées).
* Pas d’effets de bord : une fonction ne change pas l’extérieur.
* Exemple Python : calculer des IP à partir d’une liste d’interfaces.

```python
ips = ["192.168.1.10", "10.0.0.5", "172.16.0.3"]

# Fonction pure : retourne la partie réseau de chaque IP
reseaux = list(map(lambda ip: ip.split(".")[0], ips))
print(reseaux)  # ['192', '10', '172']
```

👉 On raisonne comme en mathématiques.



## 5. **Paradigme déclaratif**

* On décrit **ce qu’on veut obtenir**, pas comment.
* Exemple Linux : avec une configuration réseau dans `/etc/netplan/`
  Vous dites *"je veux que cette interface ait telle IP"*, et Linux s’occupe du reste.

```yaml
network:
  version: 2
  ethernets:
    ens33:
      dhcp4: no
      addresses: [192.168.1.50/24]
      gateway4: 192.168.1.1
      nameservers:
        addresses: [8.8.8.8, 8.8.4.4]
```

👉 Vous n’écrivez pas les commandes pour configurer l’IP, vous déclarez juste le résultat souhaité.



# Annexe — Comparaison avec Linux

| Paradigme         | Exemple Linux                                                      | Commentaire pédagogique                 |
| ----------------- | ------------------------------------------------------------------ | --------------------------------------- |
| **Impératif**     | `apt update ; apt install openssh-server -y ; systemctl start ssh` | Étapes une par une, comme une recette   |
| **Procédural**    | Script Bash avec fonction `installer_ssh()`                        | On réutilise du code                    |
| **Orienté Objet** | Python avec classe `ServeurSSH`                                    | On pense en objets (serveur = instance) |
| **Fonctionnel**   | Python `map`/`lambda` sur une liste d’IP                           | On transforme les données par fonctions |
| **Déclaratif**    | Fichier YAML Netplan                                               | On décrit le résultat, pas les étapes   |
