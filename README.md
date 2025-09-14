# MDD Monde De Dév Fullstack

![MDD](./front/src/assets/logo_p6.png)


MDD est un reseaux social intitié par ORION pour aider les développeurs qui cherchent un travail. Cette application permettra de mettre en relation les professionnels en encourageant les liens et la collaboration entre pairs qui ont des intérêts communs. 

Avant de lancer MDD auprès d’un large public, l’entreprise veut le tester avec une version minimale déployée en interne (aussi nommé MVP : Minimum Viable Product).

Le MVP permettra aux utilisateurs de s’abonner à des sujets liés à la programmation (comme JavaScript, Java, Python, Web3, etc.). Son fil d’actualité affichera chronologiquement les articles correspondants. L’utilisateur pourra également écrire des articles et poster des commentaires.

## Prérequis

- [Angular](https://angular.dev/installation)
- [JDK 17+](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [MySQL](https://dev.mysql.com/downloads/)

Assurez vous que les variables d'environnement Java sont bien configuré dans votre systeme, il s'agit d'un prérequis indispensable pour que l'application puisse se lancer. Si ce n'est pas le cas, procéder comme suit: 

- Windows:

1. Open the System Properties.
2. Click on the `Advanced` tab.
3. Click the `Environment Variables` button.
4. Under `System variables`, create a new variable named `JAVA_HOME`.
5. Add the path to your JDK's binary directory (e.g., `C:\sdkman\candidates\java\[JAVA VERSION NAME]\bin`)
6. Click `OK` to save your changes


## Project stack

### Front-End

<a href="https://developer.mozilla.org/en-US/docs/Glossary/HTML5" target="_blank" rel="noreferrer" title="HTML5">
  <img src="https://raw.githubusercontent.com/danielcranney/readme-generator/main/public/icons/skills/html5-colored.svg" width="36" height="36" alt="HTML5" />
</a>
<a href="https://sass-lang.com/" target="_blank" rel="noreferrer" title="SASS">
  <img src="https://raw.githubusercontent.com/danielcranney/readme-generator/main/public/icons/skills/sass-colored.svg" width="36" height="36" alt="SASS" />
</a>
<a href="https://www.typescriptlang.org/" target="_blank" rel="noreferrer" title="TypeScript">
  <img src="https://raw.githubusercontent.com/danielcranney/readme-generator/main/public/icons/skills/typescript-colored.svg" width="36" height="36" alt="TypeScript" />
</a>
<a href="https://angular.dev/" target="_blank" rel="noreferrer" title="Angular">
  <img src="https://miro.medium.com/v2/resize:fit:1400/1*Klh1l7wkoG6PDPb9A5oCHQ.png" width="44" height="40" alt="Angular" />
</a>

### Back-End

<a href="https://www.java.com/en/" target="_blank" rel="noreferrer" title="Java">
  <img src="https://raw.githubusercontent.com/devicons/devicon/6910f0503efdd315c8f9b858234310c06e04d9c0/icons/java/java-original.svg" width="36" height="36" alt="Java" />
</a>
<a href="https://spring.io/projects/spring-boot" target="_blank" rel="noreferrer" title="Spring">
  <img src="https://raw.githubusercontent.com/devicons/devicon/6910f0503efdd315c8f9b858234310c06e04d9c0/icons/spring/spring-original.svg" width="36" height="36" alt="Spring" />
</a>
<a href="https://www.mysql.com/" target="_blank" rel="noreferrer" title="MySQL">
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mysql/mysql-original.svg" width="36" height="36" alt="MySQL" />
</a>

## Installation rapide

1. Cloner le dépôt : `https://github.com/afarkhsi/MDD-Fullstack-project.git`

2. Configurer la base de données MySQL :

Vous trouverez dans le dossier "sql" au même niveau que le dossier "front" et "back" un script sql à appliquer pour creer et alimenter la base de donnée mdd. Celle ci vous permettra d'avoir des données disponible pour une bonne simulation des usages de l'application.

### Front-End

1. Installation des dépendances:

```shell
cd front
```

```shell
npm install
```

2. Lancement de l'application 

```shell
npx ng serve
```
### Back-End

1. Information du fichier `application.properties` dans `src/main/resources/` :
   ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/mdd?allowPublicKeyRetrieval=true
      spring.datasource.username=root
      # your datasource password
      spring.datasource.password=${DB_PASSWORD}
      spring.jpa.hibernate.ddl-auto=update
      
      spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
      spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
      spring.jpa.show-sql=true
      spring.jpa.defer-datasource-initialization=true
      oc.app.jwtSecret=openclassrooms
      oc.app.jwtExpirationMs=86400000
      
      # TomCat server
      server.port=8080
   ```
2. Installation des dépendances: `mvn clean install`
3. Lancement de l'application  `mvn spring-boot:run`

#### Fonctionnalités principales

- **Authentification JWT** : Seules les pages de connexion et d'enregistrement ne necessitent pas de contrôle par le token
- **Validation des données** : Les données entrantes sont validées avant traitement

#### Technologies utilisées

- Spring Boot 3.x
- Spring Security avec JWT
- Spring Data JPA
- MySQL
- Lombok
- ModelMapper
- Servlet API

## API Endpoints

### Authentification (`/api/auth`)

| Méthode | Endpoint  | Description          | Corps de la requête                   | Réponse                |
|---------|-----------|----------------------|--------------------------------------|------------------------|
| POST    | /register | Inscription          | `{email, username, password}`            | `{id, username, email}`  |
| POST    | /login    | Connexion            | `{email or username, password}`                  | `{id, username, email}` |

### Utilisateurs (`/api/user`)

| Méthode | Endpoint | Description        | Réponse                |
|---------|----------|--------------------|-----------------------|
| GET     | /me    | Détails utilisateur |`{id, username, email, subscribedTopics}` |
| PUT     | /me    | Détails utilisateur |`{id, username, email, subscribedTopics}` |
| GET     | /me/subscriptions    | Utilisateur abonnement |`{id, name, description, isSubscribed}` |

### Topics (`/api/topics`)

| Méthode | Endpoint | Description         | Corps/Paramètre           | Réponse                |
|---------|----------|---------------------|--------------------------|------------------------|
| GET     | /        | Liste des topics | -                        | Array [] of {id, name, description, isSubscribed}  |

### Abonnements (`/api/subscription`)

| Méthode | Endpoint | Description       | Corps de la requête                 | Réponse     |
|---------|----------|-------------------|-------------------------------------|-------------|
| POST    | /{id}/subscribe        | S'abonner a un topic  |      | `{message, topic{id, name, description, isSubscribed}}`  |
| POST    | /{id}/unsubscribe      | Se desabonner a un topic  |      | `{message, topic{id, name, description, isSubscribed}}`  |
| GET    | /{id}/user-subscription      | Récupere les abonnements d'un user  |      | Array [] of {id, name, description, isSubscribed}  |

### Articles (`/api/articles`)

| Méthode | Endpoint | Description       | Corps de la requête                 | Réponse     |
|---------|----------|-------------------|-------------------------------------|-------------|
| POST    | /      | Créer un article  |   `{title, description, topicId}`    | `{message}`  |
| GET    | /{id}     | Détail d'un article  |    | `{id, title, description, authorUsername{id, name, email}, topic{id, name}, createdAt, updatedAt } `  |
| GET    | /{id}/comments     | Récupère les commentaires d'un article par son id  |    | Array [] of {id, comment, username }  |
| POST    | /comment     | Créer un commentaire dans un article  |  `{articleId, comment}`  |`{message}` |
| GET    | /subscribed      | Récupère la list des articles selon les topics suivis  |      |Array [] of {id, title, description, authorUsername{id, name, email}, topic{id, name}, createdAt, updatedAt }  |
| GET    | /{id}/user-subscription      | Récupere les abonnements d'un user  |      | Array [] of {id, name, description, isSubscribed}  |

