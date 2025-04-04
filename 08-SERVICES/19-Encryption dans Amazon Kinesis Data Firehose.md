# Configuration de l'Encryption dans Amazon Kinesis Data Firehose  
## Sécuriser vos Données en Transit et au Repos

## Question à laquelle le README répond :

**Comment configurer et utiliser l'encryption des données avec Amazon Kinesis Data Firehose pour sécuriser les données en transit et au repos ?**

## 1. Données non encryptées avec Kinesis Data Firehose  

Par défaut, **Amazon Kinesis Data Firehose** ne chiffre pas les données à moins qu'une configuration spécifique soit mise en place. Voici les scénarios où les données ne sont **pas encryptées**.

### Pas d'encryption en transit  
Si vous n'activez pas l'encryption avec **SSL/TLS**, les données envoyées vers Firehose ne seront pas protégées en transit.  
Exemple : Utilisation d’une connexion **HTTP non sécurisée** pour l’envoi des données.

### Pas d'encryption au repos  
Si vous ne configurez pas l’encryption pour la destination des données (comme **Amazon S3** ou **Redshift**), celles-ci seront stockées en **clair**.  
Exemple : Stocker les données dans un bucket S3 sans **chiffrement activé**.

### Clés de chiffrement désactivées  
Si vous ne liez pas votre destination à une clé de gestion **KMS**, vos données ne seront pas encryptées.  
Exemple : Envoi de données à **S3** sans associer une clé **KMS** pour le chiffrement.

### Conseils  
Il est recommandé d'activer systématiquement l'encryption pour **protéger** vos données sensibles, aussi bien en transit qu'au repos.

## 2. Données encryptées avec Kinesis Data Firehose  

Pour assurer la **sécurité des données**, vous pouvez activer l'encryption avec Kinesis Data Firehose. Voici comment cela fonctionne :

### Encryption en transit  
Vous pouvez chiffrer les données **en transit** avec **SSL/TLS**, garantissant qu'elles sont protégées lorsqu'elles sont envoyées vers Firehose.  
Exemple : Utilisation de **HTTPS** pour sécuriser les données pendant leur transfert vers Firehose.

### Encryption au repos  
Vous pouvez configurer l'encryption des données une fois qu'elles sont **stockées** dans des destinations comme **S3** ou **Redshift**. Cela utilise **AWS KMS** pour gérer les clés de chiffrement.  
Exemple : Configuration d'une clé **KMS** pour chiffrer les données stockées dans un bucket S3.

### Support pour AWS KMS  
Lors de la configuration de **Firehose** pour livrer les données à **Amazon S3**, vous pouvez **spécifier une clé KMS** afin de protéger les données.  
Exemple : Spécifier une clé KMS pour garantir que toutes les données dans **S3** sont **encryptées**.

### Conseils  
Il est toujours préférable d’activer l’encryption **en transit** et **au repos** pour garantir la confidentialité des données tout au long de leur cycle de vie.

## 3. Conclusion simplifiée

- Sans encryption : Les données sont transmises et stockées **en clair** si l'encryption n'est pas configurée  
- Avec encryption : Vous pouvez activer l'encryption avec **SSL/TLS** pour les données en transit et utiliser **AWS KMS** pour les protéger au repos  

Il est **fortement recommandé** d'activer l’encryption pour toutes les données sensibles et de suivre les bonnes pratiques de sécurité AWS.

## Références supplémentaires

- [AWS KMS Documentation](https://docs.aws.amazon.com/kms)  
- [Kinesis Data Firehose Encryption Guide](https://docs.aws.amazon.com/firehose/latest/dev/encryption.html)
