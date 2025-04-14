# Vue d'ensemble :

« On va analyser un fichier de ventes de billets, qui est dans un autre compte AWS, en le chargeant dans Redshift pour faire des requêtes SQL. »

![image](https://github.com/user-attachments/assets/ab4da5ec-045a-4379-921d-0edf3690c827)


# Étapes simplifiées :

1. **On crée les permissions** (rôle et politique IAM) pour sécuriser l'accès.
2. **On crée Redshift** (cluster + base de données).
3. **On crée des tables vides** dans la base.
4. **On charge les données** depuis un bucket S3 (autre compte AWS).
5. **On interroge les données** via SQL pour en tirer des conclusions.
6. **On utilise Cloud9** pour tout faire avec du code (pas besoin d’interface graphique).
7. **On contrôle les accès** avec les rôles IAM.
8. **Même d'autres personnes** (comme Mary) peuvent utiliser l’API s’ils ont les droits.

