<div id="top"></div>

<!-- PROJECT SHIELDS -->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

<!-- END OF PROJECT SHIELDS -->

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="#">
    <img src="/images/logo.png" alt="Logo" height="200">
  </a>

<h3 align="center">DigiWF ALW-Integration</h3>

  <p align="center">
    Spring-Boot-Starter project to integrate the ALW-System into DigiWF
    <br /><a href="https://github.com/it-at-m/digiwf-alw-integration/issues">Report Bug</a>
    ·
    <a href="https://github.com/it-at-m/digiwf-alw-integration/issues">Request Feature</a>
  </p>
</div>

<!-- ABOUT THE PROJECT -->
## About The Project

The goal of this library is to enable async communication with the ALW System dispatched by an EventBus of your environment.

Features:

* Can be used to dispatch requests/responses of the ALW Personeninfo Feature asynchronously through an eventbus.
* Can inform the receiver through an eventbus if the request was successful or if there was a problem.
* Performs a functional ping to the ALW System to check connectivity.

<p align="right">(<a href="#top">back to top</a>)</p>



### Built With

The documentation project is built with technologies we use in our projects:

* Spring-Boot
* Spring-Cloud-Stream
* Apache Kafka

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- ROADMAP -->
## Roadmap

See the [open issues](#) for a full list of proposed features (and known issues).

<p align="right">(<a href="#top">back to top</a>)</p>

## Set up
Follow these steps to use the starter in your application:

1. Use the spring initalizer and create a Spring Boot application with `Spring Web`
   dependencies [https://start.spring.io](https://start.spring.io)
2. Add the digiwf-alw-integration-starter dependency.

With Maven:

```
   <dependency>
        <groupId>io.muenchendigital.digiwf</groupId>
        <artifactId>digiwf-alw-integration-starter</artifactId>
        <version>${digiwf.version}</version>
   </dependency>
```

With Gradle:

```
implementation group: 'io.muenchendigital.digiwf', name: 'digiwf-alw-integration-starter', version: '${digiwf.version}'
```

3. Add your preferred binder (see [Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream)). In this
   example, we use kafka.

Maven:

 ```
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-stream-binder-kafka</artifactId>
</dependency>
```

Gradle:

```
implementation group: 'org.springframework.cloud', name: 'spring-cloud-stream-binder-kafka'
```

4. Configure your binder.<br>
   For an example on how to configure your binder,
   see [DigiWF Spring Cloudstream Utils](https://github.com/it-at-m/digiwf-spring-cloudstream-utils#getting-started)
   Note that you DO have to
   configure ```spring.cloud.function.definition=functionRouter;sendMessage;sendCorrelateMessage;```, but you don't need
   typeMappings. These are configured for you by the digiwf-alw-integration-starter. You also have to configure the
   topics you want to read / send messages from / to.
   
5. Configure these items for your event bus:
```
spring.cloud.stream.bindings.sendMessage-out-0.destination: <YOUR CUSTOM REQUEST TOPIC>
spring.cloud.stream.bindings.sendCorrelateMessage-out-0.destination: <YOUR CUSTOM RESPONSE TOPIC>
spring.cloud.stream.bindings.functionRouter-in-0.group: <YOUR GROUP>
spring.cloud.stream.bindings.functionRouter-in-0.destination: <YOUR CUSTOM REQUEST TOPIC> # For a roundtrip use the same value as in "spring.cloud.stream.bindings.sendMessage-out-0.destination" 
```
6. Configure details of your ALW System:
```
digiwf.alw.personeninfo:
  base-url: <YOUR ALW SYSTEM URL>
  rest-endpoint: <YOUR PERSONENINFO ENDPOINT>
  timeout: <YOUR CONNECTION TIMEOUT>
  username: <YOUR BASIC AUTH USER>
  password: <YOUR BASIC AUTH PASSWORD>
  functional-ping:
    enabled: true
    azr-number: <YOUR SAMPLE AZR NUMBER>
```
7. Define a map as a named resource bean (see **BEAN_ALW_SACHBEARBEITUNG** of <i>[SachbearbeitungMapperConfig](https://github.com/it-at-m/digiwf-alw-integration/blob/dev/digiwf-alw-integration/src/main/java/io/muenchendigital/digiwf/alw/integration/configuration/SachbearbeitungMapperConfig.java) </i> ) to support mapping of the ALW System responses to directory-ous. 


For an example, please refer to the [example project](https://github.com/it-at-m/digiwf-alw-integration/tree/dev/example-digiwf-alw-integration).
There you can:
* Configure the example application (see above)
* Start the example application
* Make a http request to the configured test endpoints from <i>[ExampleController](https://github.com/it-at-m/digiwf-alw-integration/blob/dev/example-digiwf-alw-integration/src/main/java/io/muenchendigital/digiwf/alw/integration/api/controller/ExampleController.java) </i> on http://localhost:10006/testGetAlwZustaendigkeitEventBus
* Observe the output in the console

<p align="right">(<a href="#top">back to top</a>)</p>

## Documentation
For a detailed documentation see [docs](docs)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please open an issue with the tag "enhancement", fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Open an issue with the tag "enhancement"
2. Fork the Project
3. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
4. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
5. Push to the Branch (`git push origin feature/AmazingFeature`)
6. Open a Pull Request

More about this in the [CODE_OF_CONDUCT](/CODE_OF_CONDUCT.md) file.

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` file for more information.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

it@m - opensource@muenchendigital.io

[join our slack channel](https://join.slack.com/t/digiwf/shared_invite/zt-14jxazj1j-jq0WNtXp7S7HAwJA7tKgpw)

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/it-at-m/digiwf-alw-integration.svg?style=for-the-badge

[contributors-url]: https://github.com/it-at-m/digiwf-alw-integration/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/it-at-m/digiwf-alw-integration.svg?style=for-the-badge

[forks-url]: https://github.com/it-at-m/digiwf-alw-integration/network/members

[stars-shield]: https://img.shields.io/github/stars/it-at-m/digiwf-alw-integration.svg?style=for-the-badge

[stars-url]: https://github.com/it-at-m/digiwf-alw-integration/stargazers

[issues-shield]: https://img.shields.io/github/issues/it-at-m/digiwf-alw-integration.svg?style=for-the-badge

[issues-url]: https://github.com/it-at-m/digiwf-alw-integration/issues

[license-shield]: https://img.shields.io/github/license/it-at-m/digiwf-alw-integration.svg?style=for-the-badge

[license-url]: https://github.com/it-at-m/digiwf-alw-integration/blob/master/LICENSE
