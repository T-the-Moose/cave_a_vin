# Cave à Vin - Gestion de Bouteilles

Ce projet est une application Java basée sur Spring Boot permettant de gérer une cave à vin (bouteilles, régions, couleurs, etc.).

## Fonctionnalités

- CRUD sur les bouteilles de vin
- Gestion des régions et couleurs associées
- Validation des données (nom, millésime, quantité, prix, etc.)
- API RESTful

## Technologies utilisées

- Java 17+ (ici -> 21)
- Spring Boot
- Spring Data JPA
- Hibernate
- Gradle
- Base de données SQL (H2, PostgreSQL, etc.)
- Lombok

## Lancement du projet

1. **Cloner le dépôt:**
   ```bash
    git clone <url-du-repo>
    cd <nom-du-repo>
   ```

2. **Configurer la base de données:**
  - Créer le fichier `application.properties` pour configurer la connexion à la base de données.
  - Exemple pour H2 (en mémoire) :
    ```properties
    spring.datasource.url=jdbc:h2:mem:testdb
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=
    spring.h2.console.enabled=true
    ```
  - Exemple pour My SQL Serveur Studio
     ```properties
    spring.application.name=cave_a_vin
    spring.datasource.url=jdbc:sqlserver://localhost:1433;database=CAVE_A_VIN;trustServerCertificate=true
    spring.datasource.username=sa
    spring.datasource.password=password

    spring.jpa.show-sql=true
    spring.jpa.hibernate.ddl-auto=create
    spring.sql.init.mode=always
    spring.jpa.defer-datasource-initialization=true
    ```

3. Lancer l’application:
   - Avec ./gradlew bootRun
   - Avec votre IDE préferé


4. Accéder à l’API:
   - L’application sera disponible sur http://localhost:8080.
   - Vous pouvez utiliser l'outil Postman pour accéder facilment à vos requête API.
     
## Exemples d’API

     GET : /bouteilles: Lister les bouteilles
     POST : /save: Ajouter une nouvelle bouteille
     PUT : /update/{id}: Mettre à jour une bouteille existante


## Validation
Les entités sont validées via les annotations Jakarta Validation (@NotBlank, @Size, @Min, etc.).
Les messages d’erreur sont personnalisés dans les fichiers de ressources.