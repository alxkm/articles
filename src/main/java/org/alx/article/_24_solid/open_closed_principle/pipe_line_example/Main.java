package org.alx.article._24_solid.open_closed_principle.pipe_line_example;

import java.util.List;

class ImageBitmap {

}

// Functional interface for pipe methods
@FunctionalInterface
interface PipeMethod {
    void apply(ImageBitmap bitmap);
}

// Pipeline class for sequential processing
class Pipeline {
    private ImageBitmap bitmap;

    public Pipeline(ImageBitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void pipe(PipeMethod method) {
        method.apply(this.bitmap);
    }

    public ImageBitmap getResult() {
        return this.bitmap;
    }
}

// ImageProcessor class to process images using pipeline
class ImageProcessor {
    public static ImageBitmap processImage(ImageBitmap bitmap, List<PipeMethod> pipeMethods) {
        Pipeline pipeline = new Pipeline(bitmap);
        pipeMethods.forEach(method -> pipeline.pipe(method));

        return pipeline.getResult();
    }
}

// Usage example
public class Main {
    public static void main(String[] args) {
        ImageBitmap scannedImage = new ImageBitmap();
        List<PipeMethod> pipeMethods = List.of(
                Main::fixColorBalance,
                Main::increaseContrast,
                Main::fixSkew,
                Main::highlightLetters
        );

        ImageBitmap result = ImageProcessor.processImage(scannedImage, pipeMethods);
    }

    // Example pipe methods
    private static void fixColorBalance(ImageBitmap bitmap) {
        // Implementation
    }

    private static void increaseContrast(ImageBitmap bitmap) {
        // Implementation
    }

    private static void fixSkew(ImageBitmap bitmap) {
        // Implementation
    }

    private static void highlightLetters(ImageBitmap bitmap) {
        // Implementation
    }
}
