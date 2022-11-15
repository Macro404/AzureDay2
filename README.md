# JFS Azure introduction


## A. Scenario

> You know what would be cool...?

Before that sentence is even finished you know that you have reached that wonderful point of software development, where deployments are fast, the team is confident and just your imagination is hindering you from making something great.

You also know that you soon have the team at bezoscorp beaten...

What was that thing that would be cool, being able to rate craete product reviews online

## B. What you will be working on

Today you will make the product API available in the cloud


## C. Setup

You will need to create an azure account. 

### D. Lab instructions

First go through [this tutorial](https://learn.microsoft.com/en-us/azure/app-service/quickstart-java?tabs=javase&pivots=platform-linux-development-environment-azure-portal) to get familiar with the Azure UI and the concepts we mentioned in the lecture.

Thats a great, quick way to get started, but thats not how we usually do it, we want a bit more control. We want to build amnd test our application first, and then deploy it when we have verified it.

Lets build an deploy the product DB and deploy it in the cloud.

Use this [tutorial] to guide you on how to deploy the application. After this you should be able to access the API, list products and create reviews.

- Start the product API locally
  - configure it to use your database connections to mongoDB atlas and elephantSQL
  - deploy it with the az cli
  - view the logs
  - fix any issues 
- Update your application.yml to use environment variables to protect your passwords
  - test that the configuration works locally.
  - update the spring app instance and set the `env` variables
  - deploy the updated version


---

Good luck and have fun!
