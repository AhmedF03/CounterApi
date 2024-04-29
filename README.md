# Counter API

Deployed the Api to the cloud with GCP
https://burnished-attic-421600.nn.r.appspot.com/Counters/ 

## Overview
This Counter API is a Spring Boot application designed to manage a simple set of counters, allowing users to create, retrieve, increment, and decrement named counters.

## Features
- **Get All Counters**: Retrieve a map of all counters and their current values.
- **Create a New Counter**: Add a new counter with a specific initial value.
- **Increment a Counter**: Increase the value of a specific counter by 1.
- **Decrement a Counter**: Decrease the value of a specific counter by 1, or remove it if its value becomes zero or less.
- **Get Counter Value**: Retrieve the current value of a specific counter.

## API Endpoints
- `GET /Counters/`: Returns all counters.
- `POST /Counters`: Creates a new counter with a provided name and initial value.
- `PUT /Counters/{counterName}`: Increments the counter specified by `counterName`.
- `DELETE /Counters/{counterName}`: Decrements the counter specified by `counterName` or removes it if its value is 0 or less.
- `GET /Counters/{counterName}`: Retrieves the current value of the counter specified by `counterName`.
  
## Unit Tests
Unit tests are available in ApiControllerTest.java
