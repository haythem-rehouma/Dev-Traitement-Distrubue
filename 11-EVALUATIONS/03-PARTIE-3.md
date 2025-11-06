# Partie 3

**Fichier donné :** `spotify_albums.csv`, `spotify_artists.csv`, `spotify_tracks.csv`
> Liens : https://drive.google.com/drive/folders/1ti1pHjj285g-jB763vlC6949Nn0o-wW9?usp=sharing
> **Remise :** entre une page et deux pages (PDF ou .txt)

# Q1 — Problèmes de données

Citez **les problèmes** trouvés dans les CSV.

# Q2 — Contrainte

Écrivez **un exemple de** requête Cypher qui impose l’unicité de l’ID d’album.

# Q3 — Création d'une requête

Écrivez **une requête MERGE** qui crée :

* un nœud `Album` (props : `id`, `name`, `release_date`)
* un nœud `Artist` (props : `artist_id`, `name`)
* **et** la relation entre les deux.

# Q4 — Lecture simple

Écrivez **une requête MATCH** qui retourne les **3 albums** les plus récents d’un artiste connu par `artist_id`, triés du plus récent au plus ancien.

