package org.alx.article._32_java_concurrency_best_practices;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsynchronousFileIOExample {

    public static void main(String[] args) {
        Path path = Paths.get("example.txt");

        try {
            AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(
                    path, StandardOpenOption.READ);

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            long position = 0; // Start reading from the beginning of the file

            Future<Integer> readFuture = fileChannel.read(buffer, position);

            while (!readFuture.isDone()) {
                // Perform other tasks while waiting for the read operation to complete
                System.out.println("Performing other tasks...");
            }

            int bytesRead = readFuture.get(); // Get the number of bytes read
            buffer.flip(); // Flip the buffer for reading

            byte[] data = new byte[bytesRead];
            buffer.get(data); // Read data from the buffer
            System.out.println("Read data: " + new String(data));

            fileChannel.close(); // Close the file channel
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
