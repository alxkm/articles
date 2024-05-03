# Java: Classloaders, developing own Classloader

Introduction

Are you curious about the inner workings of Java applications and how they manage to load classes dynamically? Dive into the fascinating world of Java Classloaders and take control of the class loading process by developing your very own Classloader.

In this article, we’ll embark on an enlightening journey through the intricacies of Java Classloaders. We’ll uncover the crucial role they play in the Java Virtual Machine (JVM) and how they facilitate the dynamic loading of classes during runtime. Understanding Classloaders is essential for Java developers looking to optimize application performance, implement custom class loading strategies, and delve deeper into the JVM’s runtime behavior.

By delving into the development of your own Classloader, you’ll gain invaluable insights into the mechanisms behind class loading in Java. You’ll learn how to customize the class loading process to suit your application’s unique requirements, whether it’s loading classes from unconventional sources, implementing custom class loading policies, or enhancing security measures.

Moreover, developing your own Classloader is not just an educational endeavor — it’s a gateway to unlocking new possibilities in Java development. Armed with a deeper understanding of Classloaders, you’ll be better equipped to tackle complex challenges, optimize application performance, and explore innovative approaches to software development.

Join us on this exciting journey as we unravel the mysteries of Java Classloaders and empower you to take your Java development skills to new heights. Whether you’re a seasoned Java developer or just starting your journey, this article promises to provide valuable insights and practical knowledge that will enrich your Java programming experience.

Are you ready to elevate your Java development skills and become a master of Classloaders? Let’s embark on this thrilling adventure together and unlock the full potential of Java development.


### Java ClassLoader

The Java ClassLoader is an abstract class found within the java.lang package. It serves the purpose of loading classes from various resources dynamically during runtime. Essentially, the Java ClassLoader facilitates the linking process within the JVM as it loads classes according to the program’s requirements. Additionally, if a loaded class has dependencies on other classes, those dependencies are also loaded. When a class loading request is made, it is delegated to its parent, ensuring consistency and uniqueness within the runtime environment. The role of the Java ClassLoader is indispensable for the execution of Java programs.

The foundation of Java ClassLoader rests upon three core principles: Delegation, Visibility, and Uniqueness.

- Delegation: Java ClassLoader follows the principle of delegation, where it delegates the task of loading classes to its parent ClassLoader before attempting to load them itself. This hierarchical structure ensures that class loading requests are handled systematically, allowing for flexibility and consistency in the loading process.
- Visibility: The visibility principle dictates that classes loaded by a particular ClassLoader are visible to that ClassLoader and its children but not to its parent or sibling ClassLoaders. This ensures encapsulation and prevents unintended dependencies between classes loaded by different ClassLoaders, promoting modularity and maintainability in Java applications.
- Uniqueness: Java ClassLoader maintains the principle of uniqueness, ensuring that each class loaded by a ClassLoader is unique within the JVM’s runtime environment. This prevents class duplication and ambiguity, enabling reliable and predictable behavior during class loading and execution.

These principles collectively define the behavior and functionality of Java ClassLoader, shaping the dynamic loading process and contributing to the robustness and stability of Java applications.

### Types of ClassLoader

- Bootstrap ClassLoader: This is the first ClassLoader that is responsible for loading core Java classes, such as those from the java.lang package, from the JDK's rt.jar file or equivalent. It is implemented in native code and is not written in Java. It forms the foundation upon which the Java runtime environment is built.
- Extension ClassLoader: Also known as the “Extension ClassLoader,” it is responsible for loading classes from the extension directories ($JAVA_HOME/jre/lib/ext or any other directories specified by the java.ext.dirs system property). It loads classes required by Java extension libraries, which are packages that provide additional functionality to the core Java platform.
- System ClassLoader: Also known as the “Application ClassLoader,” it is responsible for loading classes from the application classpath. It loads classes from directories and JAR files specified by the java.class.path system property. This ClassLoader is typically used for loading application-specific classes and resources.

### How ClassLoader works in Java

In Java, the ClassLoader is a crucial component of the Java Runtime Environment (JRE) responsible for dynamically loading classes into memory during runtime. Here’s how the ClassLoader works in Java:


- Class Loading: When a Java program is executed, the ClassLoader loads classes into memory as they are referenced or needed by the program. The process of loading a class involves locating the bytecode (compiled Java code) for the class and creating a corresponding Class object in memory.
- Delegation Model: The ClassLoader follows a delegation model, where it delegates the task of loading classes to its parent ClassLoader before attempting to load them itself. This hierarchical structure ensures that classes are loaded systematically and consistently across the Java runtime environment.
- Loading Strategy: The ClassLoader employs a loading strategy based on three principles: delegation, visibility, and uniqueness.
- Class Resolution: Once a class is loaded into memory, the ClassLoader performs class resolution, which involves linking the class with its dependencies and verifying its integrity. This includes resolving symbolic references, initializing static variables, and performing other necessary tasks to prepare the class for execution.
- ClassLoader Hierarchy: In Java, ClassLoaders are organized into a hierarchical structure, with each ClassLoader having a parent ClassLoader. The Bootstrap ClassLoader serves as the root of the hierarchy, and it is responsible for loading core Java classes. Extension ClassLoader and System ClassLoader are typically its children, responsible for loading classes from extension directories and the application classpath, respectively.
- Custom ClassLoaders: Java allows developers to create custom ClassLoaders to extend the functionality of the built-in ClassLoaders or provide custom loading behavior for classes from non-standard sources. Custom ClassLoaders offer flexibility and control over the class loading process, enabling the implementation of advanced class loading strategies tailored to specific application requirements.

The -verbose:class option is employed to provide detailed information regarding the classes being loaded by the JVM. This feature becomes particularly valuable when utilizing a class loader for dynamically loading classes. The output generated by this option offers insights into the loading process, aiding in troubleshooting and understanding the flow of class loading within the application. The following illustration illustrates a sample output produced by this option.

### Writing own Classloader:
This example demonstrates a simple custom class loader that loads class files from a specified directory. It extends the ClassLoader class and overrides the findClass() method to load class bytes from the file system and define the class using the defineClass() method. Finally, it demonstrates how to use the custom class loader to load and instantiate a class dynamically at runtime.

```java
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CustomClassLoader extends ClassLoader {

    private final String classPath;

    public CustomClassLoader(String classPath, ClassLoader parent) {
        super(parent);
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classBytes = loadClassBytes(name);
        return defineClass(name, classBytes, 0, classBytes.length);
    }

    private byte[] loadClassBytes(String className) throws ClassNotFoundException {
        String fileName = className.replace('.', File.separatorChar) + ".class";
        try {
            FileInputStream fis = new FileInputStream(classPath + File.separator + fileName);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int b;
            while ((b = fis.read()) != -1) {
                bos.write(b);
            }
            fis.close();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new ClassNotFoundException("Class '" + className + "' not found.", e);
        }
    }

    public static void main(String[] args) {
        // Define the path to the directory containing the class files
        String classPath = "/path/to/class/files";

        // Create an instance of the custom class loader
        CustomClassLoader classLoader = new CustomClassLoader(classPath, ClassLoader.getSystemClassLoader());

        try {
            // Load the class using the custom class loader
            Class<?> myClass = classLoader.loadClass("com.example.MyClass");

            // Create an instance of the loaded class
            Object obj = myClass.newInstance();

            // Invoke methods or access fields of the loaded class
            // For example:
            // Method method = myClass.getMethod("methodName");
            // Object result = method.invoke(obj);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
```

Replace “/path/to/class/files” with the path to the directory containing your class files, and “com.example.MyClass” with the fully qualified name of the class you want to load.

This example demonstrates a simple custom class loader that loads class files from a specified directory. It extends the ClassLoader class and overrides the findClass() method to load class bytes from the file system and define the class using the defineClass() method. Finally, it demonstrates how to use the custom class loader to load and instantiate a class dynamically at runtime.




