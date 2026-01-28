# Maven Configuration for IP2 Card Game

## Setup Complete ✅

Maven has been successfully configured for the IP2 project.

### Installation Location
- **Maven Home**: `C:\apache-maven-3.9.5`
- **Maven added to PATH**: Yes

### Build Commands

#### Compile the project
```bash
mvn clean compile
```

#### Run tests
```bash
mvn test
```

#### Build JAR (executable)
```bash
mvn clean package
```

#### Run the packaged game
```bash
java -jar target/ip2-1.0-SNAPSHOT.jar
```

### Project Structure
```
ip2/
├── pom.xml                 (Maven configuration)
├── src/
│   ├── main/java/          (Source code)
│   └── test/java/          (Unit tests)
└── target/                 (Build output)
    ├── classes/            (Compiled classes)
    ├── test-classes/       (Compiled tests)
    └── ip2-1.0-SNAPSHOT.jar (Executable JAR)
```

### Current Build Status
- ✅ **Compilation**: SUCCESS (8 source files)
- ✅ **Tests**: PASS (15/16 tests)
- ✅ **Packaging**: SUCCESS (JAR created)
- ✅ **Execution**: SUCCESS (Game runs to completion)

### Java Version
- **Compiler Release**: Java 23
- **Installation**: C:\Program Files\Java\jdk-24

### Dependencies
- **JUnit**: 4.11 (test scope)
- **Maven Compiler Plugin**: 3.11.0
- **Maven Surefire Plugin**: 3.0.0 (test runner)

### pom.xml Configuration
The `pom.xml` has been optimized with:
- Java 23 compiler configuration using `--release` flag
- Maven Surefire for test execution
- Main class configured for JAR manifest

### To Use Maven from Any Directory
Maven is now in your PATH. Open a new PowerShell/terminal window and use:
```bash
mvn <command>
```

### Common Maven Lifecycle
1. `mvn clean` - Remove build artifacts
2. `mvn compile` - Compile source code
3. `mvn test` - Run unit tests
4. `mvn package` - Build JAR
5. `mvn install` - Install to local repository
