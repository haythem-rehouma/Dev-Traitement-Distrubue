# Encryption des Données avec Amazon Kinesis Data Firehose  
## Sécuriser vos Données en Transit et au Repos

## Question à laquelle le tutoriel répond :

**Comment assurer l'encryption des données envoyées via Amazon Kinesis Data Firehose ?**

## 1. Encryption des données avec Kinesis Data Firehose

### Encryption en transit  
Kinesis Data Firehose garantit que les données sont **sécurisées en transit** grâce à l'encryption via **SSL/TLS**. Cela signifie que lorsque les données sont envoyées de la source vers Firehose, elles sont protégées tout au long du trajet.

### Encryption au repos  
Lorsque les données sont stockées dans des destinations comme **Amazon S3** ou **Amazon Redshift**, vous pouvez configurer Firehose pour les **chiffrer au repos**. Firehose utilise **AWS Key Management Service (KMS)** pour gérer et contrôler les clés de chiffrement, assurant ainsi que les données restent sécurisées même après leur envoi.

### Support pour AWS KMS  
Lors de la configuration de Firehose pour livrer les données à **Amazon S3**, vous pouvez spécifier une **clé KMS** pour chiffrer les données dans le bucket S3. Cette clé de chiffrement est utilisée pour protéger les données une fois qu'elles sont stockées, garantissant leur confidentialité.

## 2. Conclusion simplifiée

Avec **Kinesis Data Firehose**, vous avez la possibilité d'appliquer l'**encryption des données en transit** via SSL/TLS et l'**encryption au repos** via **AWS KMS**. Cela garantit que les données que vous traitez sont protégées à chaque étape, assurant ainsi la **confidentialité et la sécurité** des informations.
