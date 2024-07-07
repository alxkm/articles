
## Introduction to GraalVM, and usage

Exploring GraalVM: An Introduction and Practical Guide to Leveraging Its Capabilities

![](https://cdn-images-1.medium.com/max/2000/1*oxQAqm_oDH-5DqJRlLLsMA.jpeg)

In this guide we will review what GraalVM is, how it works, and the differences between Just-In-Time (JIT) compilation and Ahead-Of-Time (AOT) compilation. If you are interested in this, then this guide is is for you.

GraalVM is a high-performance runtime that provides significant advantages for applications written in Java and other languages. It was developed by Oracle and offers the capability to run applications faster and more efficiently by leveraging advanced optimizations and a unique architecture.

History of GraalVM

**Development Origins**:

* The GraalVM project began at Oracle Labs in 2011 as part of the research on optimizing Java compilers.

* The goal was to create a new high-performance JIT compiler, known as the Graal compiler, which could replace the existing HotSpot C2 compiler in the JVM.

**Milestones**:

* **2012–2013**: Initial development focused on creating a research compiler that could demonstrate significant performance improvements.

* **2014**: The first public release of the Graal compiler, mainly targeting researchers and early adopters interested in JVM performance enhancements.

* **2017**: Introduction of the Truffle framework, which allows the GraalVM to execute code from multiple languages by providing an efficient way to implement language interpreters on top of the JVM.

* **2018**: Official release of GraalVM 1.0, which included support for multiple languages and the native image feature. This marked a significant milestone as GraalVM became available for general use.

* **2019–2020**: Continued enhancements to performance, language support, and developer tools. GraalVM gained traction in the industry as more companies adopted it for its performance benefits and polyglot capabilities.

* **2021-Present**: Further optimizations and stability improvements, expanding support for more languages and frameworks. The community around GraalVM grew, contributing to its ecosystem and adoption in various domains such as cloud computing, microservices, and enterprise applications.

### **GraalVM** as a polyglot virtual machine

**GraalVM** is a polyglot virtual machine, which means it can run applications written in multiple programming languages. Usually when all guides related to GraalVM, then with a high degree of probability it will be hastily cobbled together constructions from a number of facts. Most likely something like this:

* GraalVM is a Java Virtual Machine (JVM) (who would have thought) that can run Oracle-enabled Java (byte)code and provides possibility to run JVM-based languages (like Kotlin, Scala, Groovy).

* Framework Truffle, GraalVM can run not only Java, but also JS, Python, Ruby, R and a whole bunch of other LLVM-based languages (like C, C++, Rust) that I can’t remember right now.

* GraalVM supports native images (Native Image), which are created Ahead-of-Time (AOT) by the Graal compiler.

* The Graal compiler is a Just-In-Time (JIT) compiler.

### GraalVM offers several key features

* **High-Performance JIT Compiler**: GraalVM includes a Just-In-Time (JIT) compiler that improves the performance of Java applications by dynamically compiling bytecode into optimized machine code at runtime.

* **Native Image**: This feature allows ahead-of-time (AOT) compilation of Java applications into native executables. These native executables start faster and use less memory compared to traditional JVM-based applications.

* **Polyglot Capabilities**: GraalVM enables seamless interoperability between different programming languages, allowing developers to use the best features of each language within a single application.

* **Tooling Support**: GraalVM integrates with various development tools and frameworks, enhancing debugging, monitoring, and profiling capabilities.

So, from this persepective using GraalVM looks like best tool. For every Java application. Why do not use it everywhere. So let’s investigate this.

## Javac

To answer that question firstly we need to discover javac JIT compilation, and understand it strong sides.

The default Java compiler, javac, converts your Java source code (the .java files) into Java bytecode, generating class files (.class files). These class files can be executed on any machine with the Java Virtual Machine (JVM) installed.

### Understanding Your Bytecode

When you attempt to run your Java class or application , you'll notice that the bytecode generated previously hasn't been compiled into machine code yet. The JVM interprets this bytecode using the **TemplateInterpreter**.

### What Does TemplateInterpreter Do?

In essence, the **TemplateInterpreter **processes each bytecode instruction one by one, determining the necessary actions based on the specific operating system and architecture you're using.

### What is a JIT Compiler?

The JVM doesn’t just endlessly interpret your bytecode. Instead, it identifies frequently executed code (known as hot paths) and directly compiles that bytecode into machine code. This process involves the Just-In-Time (JIT) compiler, which optimizes performance by translating hot paths into machine code.

For those particularly curious, it’s worth exploring the Java C1 and C2 compilers and the concept of tiered compilation.

### JIT Compiler Optimization

Through extensive static code analysis and execution profiling, the JIT compiler can generate machine code that is finely optimized for your specific platform.

### JIT Advanteges

“Write once, run anywhere” — this has always been the foundational promise of Java, as long as the JVM is installed. After an initial warm-up period of interpretation, Java delivers good runtime performance through Just-In-Time (JIT) compilation.

## Ahead-Of-Time (AOT) compiler

As we know for now what is JIT. We can proceed and understand what is confrontable compilation type, AOT compilation.

An Ahead-Of-Time (AOT) compiler translates Java bytecode into native machine code before the application runs, rather than at runtime. This process involves compiling the application during the build phase, producing an executable binary tailored for a specific operating system and architecture.

The AOT compiler takes Java source code and compiles it into bytecode. It then translates this bytecode into native machine code. Finally, it generates an executable binary that can be run directly on the target platform.

Essentially, the AOT compiler will do a bunch of static code analysis (at build time, not runtime/JIT) and then create a native executable for a specific platform: Windows, Mac, Linux, x64, ARM, etc. and so on. — as, for example, if you receive .exe. This means you avoid bytecode interpretation or compilation after your program starts, resulting in the fastest possible application launch. However, you must create a specific executable for each combination of platform and architecture on which you want your program to run, along with other limitations we’ll discuss shortly. Essentially, this approach contradicts Java’s core promise of “write once, run anywhere.”

### Benefits of AOT Compilation

Since the code is already compiled, applications start almost instantaneously. AOT-compiled applications often use less memory as there’s no need for a JIT compiler and runtime optimizations. The compiler can perform extensive optimizations specific to the target platform. With no JIT compilation overhead, performance is more consistent and predictable. AOT compilation can help reduce the attack surface by eliminating the need for runtime code generation.

Overall, AOT compilation offers significant advantages for environments where startup time, memory usage, and consistent performance are critical.

## GraalVM approaches

GraalVM supports two approaches to code execution: Just-In-Time (JIT) and Ahead-Of-Time (AOT) compilation.

JIT compilation enhances performance by dynamically compiling frequently executed bytecode into optimized machine code during runtime, thus balancing startup time and peak performance, and aimed to replace C2 compiler. In contrast, AOT (Native Image)compilation translates bytecode into native machine code before execution, resulting in faster startup times and lower memory usage at the cost of platform-specific executables. Together, these approaches provide developers with flexible and powerful options for optimizing Java applications across various use cases and environments.

### Where we can use Native Image

When compiled and executed as native executables, applications can launch within milliseconds. This rapid launch time makes native images particularly well-suited for use cases such as Lambdas or CLI applications. In these scenarios, the scale of the project typically mitigates potential limitations associated with Ahead-Of-Time (AOT) compilation.

## Limitation for GraalVM’s Closed-World Assumption

Let’s talk about CWA. The closed-world assumption (CWA) in GraalVM refers to the requirement that the entire program and its dependencies are known at compile time. This assumption enables more aggressive optimizations during Ahead-Of-Time (AOT) compilation, as the compiler can make precise decisions without the need to account for dynamic loading of classes or methods at runtime.

In other words CWA is limitation for our program. That means we need to compile every code before run. So Java Reflection, Dynamic Proxy and all such stuff stop working.

### Key Aspects of the Closed-World Assumption:

1. **Whole-Program Analysis**: The AOT compiler analyzes the entire program and all its dependencies, allowing for thorough optimizations.

2. **No Dynamic Class Loading**: Since the compiler assumes all code is known, it does not need to handle dynamic class loading, resulting in simpler and more efficient code.

3. **Optimized Executable**: The resulting executable is highly optimized for performance, as the compiler can perform advanced optimizations that are not possible in a more dynamic environment.

4. **Platform-Specific**: Executables are tailored to specific platforms and architectures, ensuring optimal performance but requiring separate binaries for each target environment.

5. **Reduced Overhead**: With no need for runtime interpretation or JIT compilation, the application can achieve faster startup times and reduced memory usage.

**Benefits**:

* **Faster Startup Times**: Precompiled code eliminates the need for runtime compilation.

* **Lower Memory Usage**: No JIT compiler or runtime optimization data structures are needed.

* **Better Performance**: Aggressive optimizations lead to highly efficient executables.

**Trade-offs**:

* **Platform Specificity**: Each target environment requires a separate executable.

* **Less Flexibility**: The inability to load new classes or methods at runtime limits dynamic capabilities.

* **Build Complexity**: More complex build processes to ensure all code and dependencies are included.

So we understand that, GraalVM’s closed-world assumption allows for significant performance enhancements through AOT compilation, making it ideal for scenarios where startup time and memory efficiency are critical.

### GraalVM Spring Limitations

Main limitations for Spring applications, primarily related to the reflection and dynamic proxy usage commonly employed within the Spring framework.

### Reflection Limitations:

* **Dynamic Bean Loading**: Spring’s dynamic bean loading mechanisms heavily rely on reflection, which can be problematic for native image compilation. The reflective access required by Spring’s bean instantiation process may not be fully supported or optimized in the native image environment.

* **Configuration Metadata**: Spring relies on reflection to parse and process configuration metadata, such as annotations and XML descriptors. While GraalVM’s native image supports reflection configuration, achieving optimal performance and compatibility may require additional configuration and customization.

### Dynamic Proxy Limitations:

* **AOP and Proxy-based Mechanisms**: Spring’s Aspect-Oriented Programming (AOP) and proxy-based mechanisms for cross-cutting concerns rely on dynamic proxies. GraalVM’s native image compilation restricts dynamic proxy generation, impacting the functionality and performance of Spring’s AOP features.

* **Transactional and Security Proxies**: Spring’s transactional and security proxies, used for declarative transaction management and security enforcement, may encounter limitations when compiled into native images. The dynamic nature of these proxies conflicts with GraalVM’s ahead-of-time compilation model.

Let’s take a look at a standard example of how using **SpringBoot**. Very often you are running **SpringBoot **application, depending on the specific properties or profiles you set on startup, like parameters, depending on this you can have different beans loaded at runtime.

For example **AutoConfiguration**, which will create a bean only if a certain property is set, when application starts(**ConditionalOnProperties **).

For such cases **GraalVM **compiler, has no idea which parameter will be loaded.

So **SpringBoot **doesn’t support **@Profiles** and **@ConditionalOnProperties** for its native images at all. And other mechanisms also.

### Workarounds and Solutions:

1. **Explicit Reflection Configuration**: Spring developers can mitigate reflection-related issues by providing explicit reflection configuration for critical classes and methods. This involves specifying reflection metadata in the native image configuration to ensure proper runtime behavior.

2. **Static Analysis Tools**: Leveraging static analysis tools, such as Spring Native and GraalVM’s native image agent, can help identify and resolve reflection and proxy-related issues during development. These tools offer insights into problematic code patterns and suggest optimizations for GraalVM compatibility.

3. **Alternative Implementations**: In some cases, developers may need to explore alternative implementations or design patterns that minimize reliance on reflection and dynamic proxies. This approach may involve refactoring code to use compile-time instrumentation or static weaving techniques.

While Spring’s feature-rich ecosystem enhances developer productivity and application scalability, achieving seamless compatibility with GraalVM’s native image compilation requires careful consideration of reflection and dynamic proxy usage. By addressing these limitations through proactive configuration and optimization, developers can leverage the benefits of both Spring and GraalVM for building efficient and performant applications.

GraalVM represents a significant advancement in virtual machine technology, offering a versatile and high-performance runtime environment for modern applications. Its ability to run and interoperate with multiple languages on a single platform, combined with advanced compilation techniques, makes it a powerful tool for developers looking to build efficient and scalable applications.
