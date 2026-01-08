# github-repos-api

A simple spring-boot aplication that returns a list of user's repositories.

## Features

- Fetches non-forked repositories for a given GitHub username
- Retrieves branch information for each repository
- Handles error cases for non-existent users and other edge cases

## Technologies

- **Java 25**  
- **Spring Boot 4.0.1**  
- **RestClient** for API communication  
- **JUnit 5 & WireMock** for testing 

## How it works

1. Make a `GET` request to `/{username}`  
2. The application calls GitHub’s API to get the user’s repository list  
3. Forked repositories are filtered out  
4. For each repository, branch information is fetched  
5. The API returns a JSON containing:  
   - Repository name  
   - Owner’s login  
   - List of branches, each with branch name and last commit SHA  

## Project structure

- **RepoListService.java** – Service responsible for communicating with GitHub API  
- **Controller.java** – Defines the `/{username}` GET endpoint  
- **MyExceptionHandler.java** – Catches and handles HTTP exceptions

## Usage

### Fetch Repositories for a User
`GET /{username}`

**Example**:
`GET /user123`

**Response**:
```json
[
  {
    "repoName": "repo-name",
    "ownerLogin": "user123",
    "branches": [
      {
        "name": "main",
        "lastSha": "123"
      },
      {
        "name": "dev",
        "lastSha": "456"
      }
    ]
  }
]
```

### Error Handling
For non-existent users, the API will return a *404* response.

### Building the Project

```
./gradlew clean build
./gradlew bootRun
```

### Testing

```
./gradlew test
```


