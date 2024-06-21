# Migrating from Java 8 to Java 17: What Changed and Potential Issues

Java is very long player in the game. This is not a secret that there are a lot of Legacy code in Java. And some Java programmers will face with this issue. That was with me also. I know, that this is very specific but I faced with this issue, Java 8 project migration to some newer Java. And there I will describe some issues which we faced.
Java 8, released in 2014, has been a staple in the development world for many years. However, with the release of Java 17 in September 2021, the Java ecosystem has evolved significantly. Migrating from Java 8 to Java 17 can bring many benefits, including performance improvements, new language features, and better support for modern development practices. This article outlines the key changes and potential issues you might face during this migration.

# Key Changes from Java 8 to Java17

### 1. Language Features and Enhancements
### Java 9:

* Module System (Project Jigsaw): Introduced the module system for better modularity and encapsulation.
* JShell: An interactive tool for learning and prototyping Java code.
* Improvements to the Streams API: Added methods like takeWhile, dropWhile, and iterate.
* Private Methods in Interfaces: Allowed private methods within interfaces.

### Java 10:

* Local Variable Type Inference: Introduced the var keyword for local variables.
* Garbage Collection Enhancements: The G1 garbage collector became the default.

### Java 11:

* New String Methods: Added methods like isBlank, lines, strip, stripLeading, and stripTrailing.
* HTTP Client API: Standardized the new HTTP client for HTTP/2 and WebSocket support.
* Lambda Parameter Syntax: Allowed the use of var in lambda expressions.
* Removal of Java EE and CORBA Modules: These modules were removed from the JDK.

### Java 12:

* Switch Expressions (Preview): Introduced switch expressions to simplify coding patterns.
* G1 Garbage Collector Enhancements: Improved the overall performance of G1.

### Java 13:
* Text Blocks (Preview): Introduced text blocks to simplify writing multiline strings.
* Dynamic CDS Archives: Improved application class-data sharing.

### Java 14:
* Records (Preview): Introduced records to simplify the creation of data carrier classes.
* Pattern Matching for instanceof (Preview): Simplified type checks and casting.

### Java 15:
* Sealed Classes (Preview): Allowed classes to restrict which other classes can extend or implement them.
* Text Blocks: Text blocks became a standard feature.

### Java 16:
* Records and Pattern Matching for instanceof: These features became standard.
* Sealed Classes (Second Preview): Further enhancements to sealed classes.
* Vector API (Incubator): Introduced for SIMD programming.

### Java 17:
* Sealed Classes: Sealed classes became a standard feature.
* Pattern Matching for switch (Preview): Added pattern matching capabilities to switch statements.
* Removal of Deprecated Features: Removal of RMI Activation, the Applet API, and other deprecated components.

### 2. Performance and Security Improvements
Each Java release brought numerous performance enhancements and security fixes. Java 17 includes updates to the G1 garbage collector, improved startup times, and better overall runtime performance.

# 3. Deprecations and Removals
Java 17 removed several outdated components, including:
   
* Applet API
* RMI Activation
* Security Manager (marked for future removal)

# Potential Issues and Challenges

1. Incompatibility with the ModuleSystem
   The introduction of the module system in Java 9 can cause compatibility issues, particularly with libraries and applications that rely on the classpath. Ensuring all dependencies are compatible with the module system can be challenging.
2. Deprecated and RemovedFeatures
   Some APIs and tools deprecated in earlier versions may no longer be available. Applications relying on these will need to find alternatives or refactor code.
3. Changes in Garbage Collection
   While newer garbage collectors like G1 offer better performance, they might require tuning. Applications with custom GC configurations will need to revisit these settings.
4. Library and Framework Support
   Not all third-party libraries and frameworks are guaranteed to support Java 17 immediately. Checking for compatibility and updates is crucial.
5. Tooling and BuildProcess
   Build tools (Maven, Gradle) and IDEs must be updated to support Java 17. Ensuring the build environment is compatible is essential for a smooth migration.
6. Behavioral Changes in theLanguage
   New language features and syntax might introduce subtle behavioral changes. Comprehensive testing is necessary to ensure that the application behaves as expected.


Also we described our potential migration plan:
Assessment and Planning:
* Inventory current applications and libraries.
* Identify deprecated APIs and removed features.
* Plan for updating or replacing incompatible components.

Update Development and Build Tools:
* Upgrade IDEs, build tools, and continuous integration systems to support Java 17.

Modularization:
* If applicable, start modularizing your application to take advantage of the module system.

Refactor and Test:
* Refactor code to replace deprecated features and adapt to new language changes.
* Conduct thorough testing, including unit, integration, and performance tests.

Performance Tuning:
* Review and adjust garbage collection settings and other performance-related configurations.

Deployment:
* Roll out the updated application in stages to monitor for any unforeseen issues.

Monitor and Iterate:
* Continuously monitor the application for performance and stability.
* Iterate based on feedback and performance metrics.

# Our issues

### Maven

We used this plan to update our project. Firstly we faced that we need to update maven plugin. We had old version of the Maven compiler plugin. If you want to work with Java 17, the Maven compiler plugin version must be at least 3.5.4.

```java
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.8.1</version>
     <configuration>
         <source>17</source>
         <target>17</target>
     </configuration>
 </plugin>
```

The first step in upgrading from Java 8 to Java 17 is to update your dependencies. The dependencies you initially used likely do not support Java 17, so updating them is essential to minimize the impact of the version upgrade. Failure to do so can result in various issues.

Want to mention, obvious fact, that after updating dependencies this fixed a lot of issues.

### Lombok

First problem was our outdated Lombok.

```java
Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.2:compile (default-compile) on project encloud-common: Fatal error compiling:
```

Because Lombok injects generated code at compile time and utilizes classes from the com.sun.tools.javac.* packages, you need to update Lombok to the latest version. Doing this resolved the issue.

### JAXB

We were using JAXB during the compilation process and were getting an error. This is because JAXB has been removed from Java 11. So you need to add some JAXB Maven dependencies.

```java
<dependency>
  <groupId>javax.xml.bind</groupId>
  <artifactId>jaxb-api</artifactId>
  <version>2.3.0</version>
</dependency>
<dependency>
  <groupId>com.sun.xml.bind</groupId>
  <artifactId>jaxb-core</artifactId>
  <version>2.3.0</version>
</dependency>
<dependency>
  <groupId>com.sun.xml.bind</groupId>
  <artifactId>jaxb-impl</artifactId>
  <version>2.3.0</version>
</dependency>
<dependency>
    <groupId>javax.annotation</groupId>
    <artifactId>javax.annotation-api</artifactId>
    <version>1.3.2</version>
</dependency>
```

Adding these dependencies solved our issues.

Thanks to Java back compatibility we didn't have issues with code syntax compilation.

Also we configured some JVM configuration flags and GC, and some runtime parameters, but this change was minor. Application started without all additional afford. Also need to mention Application started work a bit efficiently and less memory consumption. Just on changing Java version. That was a nice benefit to all benefits which comes from new Java 17 LTS.

# Conclusion

Migrating from Java 8 to Java 17 is a significant undertaking but brings many benefits. With careful planning, thorough testing, and consideration of potential challenges, you can take advantage of the modern features, performance enhancements, and security improvements that Java 17 offers. Embrace the changes to ensure your applications remain robust, secure, and maintainable for years to come.
