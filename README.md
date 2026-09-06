<div align="center">
  <a href="https://github.com/jigga">
    <img src="https://github.com/jigga.png" alt="jigga" width="80" height="80">
  </a>

  <h3 align="center">jigga/gradle-helm-plugin</h3>

  <p align="center">
    A suite of Gradle Plugins for <br />building, publishing and managing <a href="https://www.helm.sh">Helm</a> Charts
    <br />
  </p>

  <p align="center">
    <a href="https://jigga.github.io/gradle-helm-plugin/"><img src="https://img.shields.io/badge/read%20our%20documentation-0f1632"></a>
    <a href="https://plugins.gradle.org/plugin/io.github.jigga.helm/2.2.0"><img src="https://img.shields.io/gradle-plugin-portal/v/io.github.jigga.helm?versionPrefix=2.2.0&colorA=0f1632&colorB=255be3" /></a>
    <a href="./LICENSE"><img src="https://img.shields.io/github/license/jigga/gradle-helm-plugin?label=license&colorA=0f1632&colorB=255be3" /></a>
  </p>
</div>

<br />

## Features

- Gradle task types for common Helm CLI commands

- Build, package and publish Helm Charts using a declarative Gradle DSL

- Resolve placeholders like ${chartVersion} from chart source files before packaging

- Resolve dependencies between charts using Gradle artifact dependencies

- Install, upgrade and uninstall releases to/from a Kubernetes cluster

## Quick Start

Add `io.github.jigga.helm` to your Gradle project:

```gradle
plugins {
    id 'io.github.jigga.helm' version '2.2.0'
}
```

```gradle
plugins {
    id("io.github.jigga.helm") version "2.2.0"
}
```

```gradle
📂 (project root)
    📂 src
        📂 main
            📂 helm
                📂 templates
                    📄 ...
                📄 Chart.yaml
                📄 values.yaml
```

## Requirements

- Gradle 7 or higher

- JDK 1.8 or higher (for running Gradle)

- Helm command-line client 3.+

## Contributing

Your contributions are at the core of making this a true open source project. Any contributions you make are **greatly appreciated**.

We welcome you to:

- Fix typos or touch up documentation
- Share your opinions on [existing issues](https://github.com/jigga/gradle-helm-plugin/issues)
- Help expand and improve our library by [opening a new issue](https://github.com/jigga/gradle-helm-plugin/issues/new)

Please review our [functional contribution guidelines](./CONTRIBUTING.md) to get started 👍

## License

This project is distributed under the [MIT License](https://opensource.org/license/mit). See [`LICENSE`](./LICENSE) for more information.

## Contact

If you have a query or require support with this project, [raise an issue](https://github.com/jigga/gradle-helm-plugin/issues).
