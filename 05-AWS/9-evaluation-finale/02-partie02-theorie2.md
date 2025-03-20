# Partie 02- 2 Questions de développements de 20h à 22h

-----------------
# Question 1
-----------------


*À partir des trois diagrammes représentant différentes architectures de pipeline de données (pipeline1, pipeline2, pipeline3), expliquez les étapes clés de chaque pipeline, en mettant en évidence les aspects suivants :*

### Pipeline 1
![pipeline1](https://github.com/user-attachments/assets/84ed9186-a77d-40a4-b97f-13e939596ad4)

### Pipeline 2
![pipeline2](https://github.com/user-attachments/assets/6aeb023f-50a7-44c3-b028-05b98817ab02)

### Pipeline 3
![pipeline3](https://github.com/user-attachments/assets/f82ef59e-a43a-4e2f-849a-f2c0eda01016)

1. **Description de chaque pipeline** : Décrivez les différentes étapes du traitement des données dans chaque pipeline, depuis l'ingestion jusqu'à la visualisation ou l'analyse des données.
   
2. **Comparaison** : Identifiez les similitudes et les différences entre ces pipelines en termes de :
   - Acquisition des données
   - Transformation des données
   - Stockage des données
   - Analyse des données

3. **Rôle des services AWS** : Discutez de l'importance des services AWS utilisés dans chaque pipeline pour assurer une ingestion fluide, un traitement efficace, un stockage approprié, et une visualisation performante des données.

4. **Cas d'usage** : Proposez un exemple de cas d'usage spécifique pour lequel chaque pipeline serait le plus adapté, en justifiant votre choix en fonction de la nature des données (en temps réel, données volumineuses, données complexes, etc.)."


-----------------
# Question 2
-----------------

**Étude de cas :**

Une entreprise de vente en ligne souhaite améliorer sa capacité à analyser en temps réel les comportements d'achat de ses clients. L'objectif est de détecter immédiatement les tendances d'achat, les produits populaires, et de réagir rapidement aux événements de vente, tels que des pics d'achats soudains lors de promotions. Les données à traiter incluent des milliers de transactions par seconde, comprenant des informations sur les produits, les prix, les clients, et les régions géographiques.

L'entreprise souhaite :
1. Ingestion des données en temps réel, car les transactions doivent être analysées dès qu'elles se produisent.
2. Une transformation des données en continu, avec une vérification de la validité (comme la cohérence des prix ou des données clients) avant qu'elles ne soient stockées.
3. Un stockage des données qui permet à la fois d'accéder rapidement aux données pour des analyses immédiates, mais aussi de conserver les données historiques pour des analyses futures.
4. Un système qui permet de visualiser les données en temps réel et d'obtenir des rapports réguliers sur les tendances de vente.
5. Une approche évolutive qui peut gérer des augmentations soudaines du volume de données, par exemple, lors des soldes ou événements spéciaux.
6. Des coûts maîtrisés et une architecture gérable par une petite équipe DevOps.

**Question :**

*En fonction des exigences énoncées ci-dessus, proposez une architecture de pipeline de données sur AWS qui permet de répondre aux besoins de cette entreprise. Décrivez les services AWS que vous utiliseriez à chaque étape du pipeline (ingestion, traitement, stockage, analyse et visualisation). Justifiez vos choix de services en fonction des exigences de traitement en temps réel, du volume élevé de données et de la nécessité d'une analyse rapide et précise. Dessinez ensuite l'architecture de votre pipeline et expliquez comment elle fonctionne de bout en bout.*
