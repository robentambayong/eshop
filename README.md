eshop - Advanced Programming
============================

Reflection 1 (Module 1)
------------

### Clean Code Principles Applied

In the first iteration of development, I have applied several Clean Code principles to ensure the project remains maintainable and readable:

*   **Meaningful Names**: I used descriptive names for classes, methods, and variables to ensure their intent is clear without needing comments. For example, ProductRepository clearly indicates its role in managing product data.

*   **Functions Should Do One Thing**: Each method in my service and controller layers is focused on a single responsibility, such as create or findAll.

*   **Lombok for Concise Code**: I applied @Getter and @Setter annotations in the Product model to automatically generate boilerplate code, keeping the class clean and focused on the data structure.

*   **Separation of Concerns**: By following the **Model-View-Controller (MVC)** pattern, I separated the application logic into distinct layers: model for data structures, repository for data operations, service for business logic, and controller for request handling.

*   **Command Query Separation**: In the ProductController, functions either perform an action (Command) or return a view (Query), but not both, which reduces side-effect confusion.


### Secure Coding Practices

I have considered the following security aspects during implementation:

*   **Output Data Encoding**: By using **Thymeleaf** as the template engine, the application automatically escapes untrusted data before rendering it in the browser, which helps prevent Cross-Site Scripting (XSS) attacks.

*   **Internal Redirection**: The controller uses internal redirects (for example: redirect:list) rather than relying on user-supplied URLs, mitigating risks related to unvalidated redirects.


### Areas for Improvement

After evaluating the current source code, I identified these areas for future improvement:

*   **Input Data Validation**: Currently, there is no validation to ensure productQuantity is a non-negative number. I should implement validation logic in the service layer or use Spring's @Valid annotation to ensure data integrity.

*   **Secure ID Generation**: The productId should be generated using a more secure and unpredictable method, such as UUID.randomUUID().toString(), to prevent attackers from guessing IDs (Insecure Direct Object Reference).

*   **Error Handling**: I should replace generic responses with specific exception handling (for example: a custom ProductNotFoundException) to provide better context and robustness when a product cannot be found during future Edit or Delete operations.


Reflection 2 (Module 1)
------------

**1a. After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program?**

Writing unit tests provides a significant sense of security, especially after dealing with merge conflicts and compilation errors like the UUID symbol issue. There is no "magic number" for how many tests a class should have. Instead, the focus should be on covering all logical paths, including positive scenarios (creating a valid product) and negative scenarios (searching for a non-existent ID). To ensure tests are "enough," we can use **Code Coverage** metrics.

**1b. If you have 100% code coverage, does that mean your code has no bugs or errors?**

No, 100% code coverage does not guarantee bug-free code. It simply means every line of code was executed during a test. It does not account for:

*   **Logic errors**: The code might run but return the wrong result.

*   **Missing requirements**: The code covers what is written, but might miss features required by the user.

*   **Boundary cases**: The test might pass for common inputs but fail for extreme or unexpected values.


**2\. Potential Clean Code Issues in the New Functional Test Suite:**

If I were to create a new functional test suite for the "Product List" count using the exact same setup as CreateProductFunctionalTest.java, the primary issue would be **Code Duplication** (the "Don't Repeat Yourself" or **DRY** principle).

*   **Identified Issues**:

    *   **Duplicate Setup**: Both classes would re-declare the serverPort, baseUrl, and setupTest() logic.

    *   **Maintenance Overhead**: If the base URL structure changes (for example: adding a context path), I would have to update it in multiple files, increasing the risk of human error.

*   **Impact on Quality**: This reduces the "Cleanliness" of the codebase by making it more verbose and harder to maintain. High duplication often leads to technical debt.

*   **Suggested Improvements**:

    *   **Base Test Class**: Create an abstract BaseFunctionalTest class that handles the @LocalServerPort and @BeforeEach setup.

    *   **Inheritance**: Have both CreateProductFunctionalTest and the new list-count test extend this base class. This centralizes the configuration and keeps the specific test classes focused only on their unique interactions.

Module 2 Reflection
------------

**1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.**

* **Empty Method (contextLoads):** SonarCloud flagged the default `contextLoads()` method in my application tests as a "Critical" code smell because empty methods can indicate missing implementation. My strategy to fix this was to add a nested comment inside the method explaining that it is intentionally empty to verify the Spring context loads successfully.

**2. Look at your CI/CD workflows (GitHub Actions). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!**

Yes, the current implementation successfully meets the definition of both Continuous Integration (CI) and Continuous Deployment (CD).
For **Continuous Integration**, I have set up GitHub Actions workflows (`ci.yml` and `sonarcloud.yml`) that automatically run my full test suite and execute SonarCloud code quality analysis every time code is pushed or a pull request is made. This ensures that any new code integrated into the repository is verified to be bug-free, maintains 100% test coverage, and adheres to quality standards before it can be merged.
For **Continuous Deployment**, I connected my GitHub repository to Koyeb using a pull-based PaaS approach. Whenever code is merged into the `main` branch, Koyeb automatically detects the changes, reads my `Dockerfile`, builds the application image, and deploys the latest version to production without requiring any manual intervention.




