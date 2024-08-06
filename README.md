# beyonnex-challenge

This project uses Quarkus. the function of this project is to provide a REST API to check if two strings are anagrams.
and also to list all saved anagrams for a specific string.

### Setup

#### Prerequisites

- JDK 11
- GraalVM (optional, for native compilation)
- Gradle


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only
> at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./gradlew build
```

It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into
the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./gradlew build -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./gradlew build -Dquarkus.native.enabled=true
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container
using:

```shell script
./gradlew build -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/beyonnex-challenge-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please
consult <https://quarkus.io/guides/gradle-tooling>.

## Provided Code


### REST

#### Endpoints of the Project

1. `POST /anagram/check`

   Description: This endpoint checks if two provided strings are anagrams of each other.
   Parameters:
   `firstAnagramArgument` (Query Parameter): The first string to be checked.
   `secondAnagramArgument` (Query Parameter): The second string to be checked.
   **Response:**
   `200 OK: Returns true if the strings are anagrams, otherwise false.
   Content-Type: text/plain`

    **Example Usage**
    ```sh
    curl localhost:8080/anagram/check?firstAnagramArgument=listen&secondAnagramArgument=silent
    ```
    **Response:**
        ```
       true
        ```

2. `GET /anagram/check/{anagram}`

   Description: This endpoint retrieves all saved anagrams for a specific string.
   Parameters:
   `anagram` (Path Parameter): The string for which to retrieve the anagrams.

   **Response:**
   `200 OK: Returns a JSON array of anagrams if any exist, otherwise an empty array.
   Content-Type: application/json
   405 Method Not Allowed: If the anagram parameter is an empty string.`

   **Example Usage**
   ```sh
   curl localhost:8080/anagram/check/listen
   ```
      **Response:**
       ```
      ["silent"]
       ```
### Service

The project contains a service class `AnagramService` that provides the following methods:
1. `boolean validateAnagramAndSave(String firstAnagramArgument, String secondAnagramArgument)`: This method validates if two strings are anagrams of each other and saves them if they are.
2. `List<String> getAnagrams(String anagram)`: This method retrieves all saved anagrams for a specific string.

there are two maps that are used on service level:
1. `anagramMap`: This map is used to store anagrams set for a specific character set.
2. `alphabetPrimeMap`: This map is used to store prime numbers for each character in the alphabet and is used to generate hash for character set to keep anagram strings mapped together.

### Tests

The project contains a test class `AnagramResourceTest` that tests the REST endpoints of the project.
and also a test class `AnagramServiceTest` that tests the service class of the project.

