# Language percentage fetcher

## How To
The following guides illustrate how to run and use application

## Requirements

To run the application you need:

- [GitHub PAT](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)

## Running the application locally

To run a Spring Boot application on your local machine:
- setup env variable with your GitHub personal access token, it's prepared empty in IDEA project file 
- execute `com.pb.github.stats.lang.RepoStatsApplication` class from your IDE.

Scheduled job will immediately try to load GitHub public repositories and their languages for Productboard organization.
Some settings for client and scheduler are available in application properties.
## REST API

Endpoint to return percentage of each language used in Productboard repositories.
Executable in IDEA with [HTTP Client](https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html) in `lang-stats-api.http`
### Request

`GET /api/stats/lang/`

    curl -X GET --location "http://localhost:8080/api/stats/lang"

### Response example:

    { 
      "JavaScript": 0.2, 
      "TypeScript": 0.5, 
      "Ruby": 0.1, 
      "Java": 0.1
    }
