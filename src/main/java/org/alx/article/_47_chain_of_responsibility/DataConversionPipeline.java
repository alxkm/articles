package org.alx.article._47_chain_of_responsibility;

// Define the base interface for data converters
interface DataConverter {
    void setNextConverter(DataConverter nextConverter);
    String convert(String data);
}

// Concrete implementation of DataConverter for JSON to XML conversion
class JsonToXmlConverter implements DataConverter {
    private DataConverter nextConverter;

    @Override
    public void setNextConverter(DataConverter nextConverter) {
        this.nextConverter = nextConverter;
    }

    @Override
    public String convert(String data) {
        // Perform JSON to XML conversion
        return "<xml>" + data + "</xml>";
    }
}

// Concrete implementation of DataConverter for XML to CSV conversion
class XmlToCsvConverter implements DataConverter {
    private DataConverter nextConverter;

    @Override
    public void setNextConverter(DataConverter nextConverter) {
        this.nextConverter = nextConverter;
    }

    @Override
    public String convert(String data) {
        // Perform XML to CSV conversion
        return data.replaceAll("<", ",").replaceAll(">", "");
    }
}

// Concrete implementation of DataConverter for CSV to JSON conversion
class CsvToJsonConverter implements DataConverter {
    private DataConverter nextConverter;

    @Override
    public void setNextConverter(DataConverter nextConverter) {
        this.nextConverter = nextConverter;
    }

    @Override
    public String convert(String data) {
        // Perform CSV to JSON conversion
        return "{\"json\": \"" + data + "\"}";
    }
}

public class DataConversionPipeline {
    public static void main(String[] args) {
        // Create instances of converters
        DataConverter jsonToXmlConverter = new JsonToXmlConverter();
        DataConverter xmlToCsvConverter = new XmlToCsvConverter();
        DataConverter csvToJsonConverter = new CsvToJsonConverter();

        // Set up the pipeline
        jsonToXmlConverter.setNextConverter(xmlToCsvConverter);
        xmlToCsvConverter.setNextConverter(csvToJsonConverter);

        // Input data in JSON format
        String jsonData = "{ \"key\": \"value\" }";

        // Process data through the pipeline
        String result = jsonToXmlConverter.convert(jsonData);

        // Output the result
        System.out.println("Result: " + result);
    }
}
