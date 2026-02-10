eshop - Advanced Programming
============================

Reflection 1
------------

### Clean Code Principles Applied

In the first iteration of development, I have applied several Clean Code principles to ensure the project remains maintainable and readable:

*   **Meaningful Names**: I used descriptive names for classes, methods, and variables to ensure their intent is clear without needing comments. For example, ProductRepository clearly indicates its role in managing product data.+2

*   **Functions Should Do One Thing**: Each method in my service and controller layers is focused on a single responsibility, such as create or findAll.

*   **Lombok for Concise Code**: I applied @Getter and @Setter annotations in the Product model to automatically generate boilerplate code, keeping the class clean and focused on the data structure.+1

*   **Separation of Concerns**: By following the **Model-View-Controller (MVC)** pattern, I separated the application logic into distinct layers: model for data structures, repository for data operations, service for business logic, and controller for request handling.+2

*   **Command Query Separation**: In the ProductController, functions either perform an action (Command) or return a view (Query), but not both, which reduces side-effect confusion.


### Secure Coding Practices

I have considered the following security aspects during implementation:

*   **Output Data Encoding**: By using **Thymeleaf** as the template engine, the application automatically escapes untrusted data before rendering it in the browser, which helps prevent Cross-Site Scripting (XSS) attacks.+2

*   **Internal Redirection**: The controller uses internal redirects (e.g., redirect:list) rather than relying on user-supplied URLs, mitigating risks related to unvalidated redirects.+1


### Areas for Improvement

After evaluating the current source code, I identified these areas for future improvement:

*   **Input Data Validation**: Currently, there is no validation to ensure productQuantity is a non-negative number. I should implement validation logic in the service layer or use Spring's @Valid annotation to ensure data integrity.+2

*   **Secure ID Generation**: The productId should be generated using a more secure and unpredictable method, such as UUID.randomUUID().toString(), to prevent attackers from guessing IDs (Insecure Direct Object Reference).+1

*   **Error Handling**: I should replace generic responses with specific exception handling (e.g., a custom ProductNotFoundException) to provide better context and robustness when a product cannot be found during future Edit or Delete operations.+2