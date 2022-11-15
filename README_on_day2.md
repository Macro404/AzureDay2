# JFS Azure Automation

> Awesome, we have BeozsCorp on the run, but they have thousands of people, we need to make sure we can stay ahad.
Manually deploying applications is not that great, it takes time and is prone to errors, so over time it will become harder and harder and will become less frequent. What we want is a way to release new features quickly, in a controlled manner that involves checks and balances. That's right, it's time for Continuous Delivery.


## B. What you will be working on

Today you will automate the deployment of the product reviews API using [GitHub Actions][gh-actions-link] and Azure features. You will build a pipeline that checks out the code whenever there is a push to the `main` branch, runs the automated tests, and if that pass, it will deploy the application to production.


## C. Setup

You will need
- your own GitHub repository
- an Azure account


## D. Lab instructions

Today you will create a GitHub action to deploy your API.

- Create your own GitHub repository (you can use https://github.com/saltsthlm organization if you don't want to use a personal account for sharing the code with your team). You can fork this repo or create a blank one, it's up to you.

We want you to build a GitHub action pipeline that
- Has multiple steps
- Builds and tests the application
- Creates an artifact and passes it on to a new stage
- Logs in to azure in stage 2
- deploys the artifact to Azure via the azure CLI GitHub task


#### Stretch goals

Once that is done try these extra steps to get into some more CI/CD concepts
1. use the build identifier from GitHub to the [artifact](https://docs.github.com/en/actions/using-workflows/storing-workflow-data-as-artifacts
  - Hint: GitHub action exposes lots of metadata in its [context](https://docs.github.com/en/actions/learn-github-actions/contexts).
  - Hint: Use [maven properties](https://maven.apache.org/pom.html#properties) to add that number as the version
2. Add that version number as a response header in the API, so you can verify that the new version has been deployed. This can be useful information, e.g. when testing an application
  - Hint:  use [maven filtering](https://maven.apache.org/plugins/maven-resources-plugin/examples/filter.html) and [spring config](https://docs.spring.io/spring-boot/docs/2.0.0.M7/reference/html/howto-properties-and-configuration.html) to inject the value in a property. Then use springs auto-config to get the value and set it in a header of responses. (Do one as a proof of concept, then add more if you want to)


### Hints

- You should never use your own user to deploy using GitHub actions. Create a [service user](https://github.com/marketplace/actions/azure-login#configure-deployment-credentials) and use those credentials
- Don't hardcode the credentials, use GitHub secrets to access the credentials of the service user.
- Work step by step. Start with building and testing, then create the artifact, etc.

---

Good luck and have fun!

[gh-actions-link]: https://docs.github.com/en/actions/automating-builds-and-tests/building-and-testing-java-with-maven