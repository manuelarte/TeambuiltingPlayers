# Microservice TeamBuiltingCore

This microservice contains the information related to the teams and players.

# Endpoints available

* /core/teams
** Retrieve

## Gradle linter

The project contains a plugin called gradle linter.  
It prevents you from building/running if dependencies are transient while should be direct referenced, also if you have unused direct dependencies.

### Automated Gradle Linter Fixes

Make sure you stage `build.gradle` if any changes were made, then run `gradle fixGradleLint` or `gradle fGL` for short.
Confirm and reorder the changes to dependencies.

## Testing

All layers are independent:

| Layer | command | source location | requirements |
| ----- | ------- | --------------- | ------------ |
| *Unit test* | `./gradlew jacoco test`| `/src/test` | no database or queue |
| *Integration test* | `./gradlew jacoco integrationTest` or `./gradlew jacoco iT`| `/src/integrationTest` | self provided in memory H2 and RabbitMQ |

## Reports

General reports:

	gradle buildDashboard projectReport

Allure reports:

	gradle allure

## Running on any plataform

1. It needs a Mongo DB server

2. An instance of RabbitMQ

3. Then run the spring-boot application.

## Gradle based execution

		gradle bootRun