# *Explication des étapes du schéma (Numérotées 1 à 9)*

![image](https://github.com/user-attachments/assets/455d28a5-dd72-4a4a-b029-4524eca478ef)



| # | **Étape** | **Description en français** |
|---|-----------|------------------------------|
| 1 | EC2 & IAM | Vous allez examiner la configuration de l’instance EC2 qui héberge le serveur web. Vous allez aussi analyser le rôle IAM `OsDemoWebserverIAMRole` et les politiques associées pour comprendre les autorisations accordées. |
| 2 | Kinesis Data Streams | Vous étudierez le flux de données Kinesis qui capture les journaux d’accès web en temps réel générés par les utilisateurs du site. |
| 3 | OpenSearch Service | Vous examinerez la configuration du cluster OpenSearch utilisé pour indexer et stocker les données. |
| 4 | Index OpenSearch | Vous configurerez un **index OpenSearch** pour stocker les journaux d’accès enrichis. Cet index permettra les recherches et les visualisations. |
| 5 | Navigation sur le site | Une fois la configuration terminée, vous naviguerez sur le site pour **générer des journaux d’accès**. Ces journaux simulent le trafic utilisateur. |
| 6 | Logs dans CloudWatch | Vous consulterez les **logs générés dans Amazon CloudWatch**, afin de comprendre comment Kinesis Firehose transmet les données à Lambda pour les enrichir avant l’indexation. |
| 7 | Pattern d’index | Vous créerez un **modèle d’index (index pattern)** dans OpenSearch Dashboards. Ce pattern est nécessaire pour pouvoir visualiser les données dans des graphiques. |
| 8 | Diagramme circulaire | Vous construirez une **visualisation en diagramme circulaire (pie chart)** pour montrer les systèmes d’exploitation et navigateurs utilisés par les visiteurs. |
| 9 | Carte thermique | Enfin, vous terminerez le laboratoire en créant une **carte thermique (heat map)** pour analyser les pages d’origine des visiteurs (page de recherche ou de recommandations). |
