package org.alx.article._17_java_classloaders_developing_own_classloader;

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