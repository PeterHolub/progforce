# ProgForce Task
implementation of ProgForce task
## Prerequisites
* **JDK >= 1.8.0_131**;
* **Apache Maven >= 3.5.2**
## Project structure 
* src\main\java\com.peterholub.entity - package with domain  model object Product and Enum.
* src\main\java\com.peterholub.store - package with interface Abstarct Class and implementation of Store as database persistence layer.
* src\main\java\com.peterholub.util - package with Util objects, for database connection and sql script file reader.
* src\main\java\com.peterholub.Main - main class with business logic.
* resources- folder with properties file and sql script files.
## Run 

```
com.peterholub.Main.main
```