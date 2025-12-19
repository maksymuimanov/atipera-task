# GitHub Repositories Proxy (Atipera task)

A small Spring Boot 4 application that exposes one API endpoint to list a GitHub user's repositories that are not forks. For each repository it returns the repository name, owner login, and all branches with the last commit SHA. When a user does not exist, it returns a 404 with a compact error body.

## Tech stack
- Java 25
- Spring Boot 4.0
- Jackson
- WireMock + RestAssured + Spring Boot Test

## Run locally
Prerequisites:
- Java 25
- Maven 3.9+

Build and run:
```shell
# From project root
start.sh
```
Or with the Spring Boot Maven plugin:
```shell
# From project root
mvn spring-boot:run
```
By default the app starts on port 8080. You can then call:
```shell
curl http://localhost:8080/api/v1.0/repos/octocat
```

## API

- Method: `GET`
- Path: `/api/v1.0/repos/{username}`
- Produces: `application/json`

### Success 200 example
Request:
```
GET /api/v1.0/repos/example
```
Response body:
```json
[
  {
    "name": "service-api",
    "ownerLogin": "example",
    "branches": [
      { 
         "name": "main",    
         "lastCommitSha": "..."
      },
      { 
         "name": "develop", 
         "lastCommitSha": "..."
      }
    ]
  },
  {
    "name": "smth",
    "ownerLogin": "example",
    "branches": [ 
      {
         "name": "main", 
         "lastCommitSha": "..."
      } 
    ]
  }
]
```

### Not found 404 example
If the GitHub user does not exist, response is:
```json
{
  "status": 404,
  "message": "User [{username}] repos are not found! Check if the username is valid!"
}
```

## How it works
1. Client calls our endpoint: `GET /api/v1.0/repos/{username}`.
2. Service calls GitHub:
   - List repositories: `GET https://api.github.com/users/{username}/repos`.
   - For each non-fork repository, list branches: `GET https://api.github.com/repos/{owner}/{repo}/branches`.
3. Response returns only non-fork repositories with branches and their last commit SHA.
4. If GitHub returns 404 for the user, we return 404 with a small error JSON.

## Tests
This project contains only integration tests, with no mocks. Tests use WireMock to emulate the GitHub API and RestAssured to call the running Spring Boot application.

Run tests:
```shell
mvn test
```

## License
This repository is provided for the purposes of the recruitment task.