# Contributing to Maya CBS Spring Microservices

Thank you for considering contributing, We're excited to collaborate with the open-source community
to improve this project. Below are some guidelines to help you get started.

## Table of Contents

1. [Code of Conduct](#code-of-conduct)
2. [How Can I Contribute?](#how-can-i-contribute)
    - [Reporting Bugs](#reporting-bugs)
    - [Suggesting Features](#suggesting-features)
    - [Submitting Pull Requests](#submitting-pull-requests)
3. [Development Setup](#development-setup)
4. [Style Guide](#style-guide)
5. [Commit Messages](#commit-messages)
6. [Code Reviews](#code-reviews)
7. [License](#license)

## Code of Conduct

This project adheres to
the [Contributor Covenant Code of Conduct](https://www.contributor-covenant.org/version/2/0/code_of_conduct/).
By participating, you are expected to uphold this code. Please report unacceptable behavior
to [arivanamin@gmail.com](mailto:email@example.com).

## How Can I Contribute?

### Reporting Bugs

If you find a bug, please create an issue on GitHub with the following details:

- A clear and descriptive title.
- Steps to reproduce the problem.
- Expected behavior.
- Actual behavior.
- Screenshots or code snippets (if applicable).
- Any relevant log files or error messages.

### Suggesting Features

We welcome new feature requests! To suggest a feature, please create a GitHub issue with the
following information:

- A clear and descriptive title.
- The problem that the feature would solve.
- How you envision the feature working.
- Any examples or mockups (if applicable).

### Submitting Pull Requests

1. **Fork the repository** and create a new branch:

    ```bash
    git checkout -b feature/YourFeatureName
    ```

2. **Make your changes** with clear, concise, and well-documented code.
3. **Ensure tests pass** and consider adding new tests for your changes:

    ```bash
    mvn test
    ```

4. **Commit your changes** with a meaningful commit message (
   see [Commit Messages](#commit-messages)):

    ```bash
    git commit -m "Add feature: YourFeatureName"
    ```

5. **Push to your fork**:

    ```bash
    git push origin feature/YourFeatureName
    ```

6. **Open a Pull Request** on GitHub and provide a detailed description of your changes.

## Development Setup

To set up your development environment, follow these steps:

1. **Clone the repository**:

    ```bash
    git clone https://github.com/yourusername/projectname.git
    cd projectname
    ```

2. **Install dependencies**:

    ```bash
    mvn clean install
    ```

3. **Run the application**:

    ```bash
    mvn spring-boot:run
    ```

4. **Run tests**:

    ```bash
    mvn test
    ```

## Style Guide

Please follow the coding standards and guidelines used in this project:

- **Java Style**:
  Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).
- **Naming Conventions**: Use descriptive and meaningful names for variables, methods, and classes.
- **Documentation**: Document your code using JavaDoc where necessary.

## Commit Messages

- **Format**: Use the imperative mood in the subject line (e.g., "Add feature" not "Added feature").
- **Structure**:
    - Subject: A short summary of the changes (50 characters or less).
    - Body: (Optional) A more detailed explanation of the changes.
- **Example**:

    ```plaintext
    Add support for user authentication

    This commit adds user authentication to the API using JWT tokens.
    ```

## Code Reviews

All pull requests will be reviewed before merging. Please ensure that your code follows the
project’s style guide and passes all tests. Be open to feedback and willing to make changes if
requested by the reviewers.

## License

By contributing to this project, you agree that your contributions will be licensed under
the [MIT License](LICENSE).
