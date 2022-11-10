<div id="top"></div>

<!-- PROJECT SHIELDS -->

<!-- END OF PROJECT SHIELDS -->

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="#">
    <img src="/images/logo.png" alt="Logo" height="200">
  </a>

<h3 align="center">DigiWF <i>Core</i></h3>

  <p align="center">
    <i>This repository includes all DigiWF Core Services</i>
    <br /><a href="https://github.com/it-at-m/digiwf-core/issues">Report Bug</a>
    ·
    <a href="https://github.com/it-at-m/digiwf-core/issues">Request Feature</a>
  </p>
</div>

<!-- ABOUT THE PROJECT -->

## About The Project

*Add a description from your project here.*
<p align="right">(<a href="#top">back to top</a>)</p>

### Built With

The documentation project is built with technologies we use in our projects:

* Camunda
* Spring Boot
* Vue.js
* JSON Schema

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- ROADMAP -->

## Roadmap

*if you have a ROADMAP for your project add this here*

See the [open issues](#) for a full list of proposed features (and known issues).

<p align="right">(<a href="#top">back to top</a>)</p>

## Set up

*how can I start and fly this project*

<p align="right">(<a href="#top">back to top</a>)</p>

## Documentation

### Building a new maven version

For updating the version of the project we use maven-versions plugin.

#### update new patch version

1. run `mvn versions:set -DprocessAllModules -DnewVersion=<your version>`
2. check if update is correct
3. if update is correct, run `mvn versions:commit` otherwise run `mvn versions:revert`

#### update to a specific version

1. run `mvn versions:set -DprocessAllModules -DnextSnapshot`
2. check if update is correct
3. if update is correct, run `mvn versions:commit` otherwise run `mvn versions:revert`

### Deploy a new maven version

- Execute the release pipeline manually and set `Release services (y/n)?` to `y`
- `-SNAPSHOT` will be removed automatically by the pipeline if `Snapshot build (y/n)?` is set to `n`

### Deploy a new apps version

- execute `npm run versioning` in the digiwf-apps folder
- Execute the release pipeline manually and set `Release apps (y/n)?` to `y`
- `-SNAPSHOT` suffix will be set if `Snapshot build (y/n)?` is set to `y`

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONTRIBUTING -->

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any
contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please open an issue with the tag "enhancement", fork the repo and
create a pull request. You can also simply open an issue with the tag "enhancement". Don't forget to give the project a
star! Thanks again!

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
