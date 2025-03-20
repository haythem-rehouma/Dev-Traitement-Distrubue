# Installer et configurer Neo4j 

### Étape 1 : Mettre à jour la liste des packages
Commencez par mettre à jour la liste de vos packages APT avec la commande suivante :
```bash
sudo apt update
```

### Étape 2 : Installer les packages requis
Installez ensuite les packages prérequis pour permettre les installations via HTTPS :
```bash
sudo apt install apt-transport-https ca-certificates curl software-properties-common
```

### Étape 3 : Ajouter la clé GPG de Neo4j
Ajoutez la clé GPG du dépôt Neo4j pour vous assurer que les packages téléchargés sont fiables :
```bash
curl -fsSL https://debian.neo4j.com/neotechnology.gpg.key | sudo apt-key add -
```

### Étape 4 : Ajouter le dépôt Neo4j aux sources APT
Ajoutez le dépôt Neo4j à vos sources de packages :
```bash
sudo add-apt-repository "deb https://debian.neo4j.com stable 4.1"
```

### Étape 5 : Installer Neo4j
Installez Neo4j ainsi que ses dépendances (y compris Java si nécessaire) en utilisant la commande suivante :
```bash
sudo apt install neo4j
```

### Étape 6 : Activer Neo4j au démarrage
Configurez Neo4j pour qu'il démarre automatiquement après chaque redémarrage du système :
```bash
sudo systemctl enable neo4j.service
```

### Étape 7 : Vérifier le statut de Neo4j
Vérifiez que Neo4j fonctionne correctement en vérifiant l'état du service avec la commande suivante :
```bash
sudo systemctl status neo4j.service
```
Vous devriez voir une sortie similaire à :
```
neo4j.service - Neo4j Graph Database
Loaded: loaded (/lib/systemd/system/neo4j.service; enabled; vendor preset: enabled)
Active: active (running) since ……..
```

### Étape 8 : Utiliser Cypher Shell pour se connecter
Utilisez l'outil `cypher-shell` pour interagir avec Neo4j en ligne de commande. Lancez-le avec :
```bash
cypher-shell
```

Lors de la première connexion, utilisez les identifiants par défaut (utilisateur `neo4j`, mot de passe `neo4j`). Vous devrez ensuite changer le mot de passe après la connexion :
```
username: neo4j
password: neo4j
Password change required
new password: <votre-nouveau-mot-de-passe>
```

### Étape 9 : Accéder à l'interface web
Une fois connecté, vous pouvez également accéder à l'interface graphique de Neo4j à l'adresse suivante dans un navigateur :
```
http://localhost:7474
```

Neo4j est maintenant installé et prêt à être utilisé, et vous pouvez interagir avec la base de données en ligne de commande ou via l'interface graphique.

