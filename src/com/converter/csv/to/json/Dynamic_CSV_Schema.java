package com.converter.csv.to.json;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class Dynamic_CSV_Schema {

	public static void main(String[] args) {
		
		/**
		 * This approach reads the CSV file into an iterator of maps and converts
		 * each row into a JSON object. This approach is useful when the CSV file has a
		 * dynamic schema, meaning that the column names can change from file to file or
		 * the number of columns can be different for different files. However, this
		 * approach requires a little bit more code and can be slower than the second
		 * approach, especially for large files.
		 */
		
		// Set up the input and output files
        File csvFile = new File("PersonDetails.csv");
        File jsonFile = new File("PersonDetails.json");

        
     // Check if the output file already exists and create a new file with incremented number
        if (jsonFile.exists()) {
            String filename = jsonFile.getName();
            int lastDotIndex = filename.lastIndexOf('.');
            String extension = filename.substring(lastDotIndex);
            String newFilename = filename.substring(0, lastDotIndex) + (System.currentTimeMillis() / 1000) + extension;
            jsonFile = new File(jsonFile.getParent(), newFilename);
        }

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();

        // Increase buffer size to handle large number of records
        csvMapper.enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE);

        MappingIterator<Map<String, String>> it = null;
        try {
            // Read the CSV file into an iterator of maps
            it = csvMapper.readerFor(Map.class)
                          .with(csvSchema)
                          .readValues(csvFile);

            // Convert the iterator of maps to a JSON array
            ObjectMapper jsonMapper = new ObjectMapper();
            ArrayNode jsonArray = jsonMapper.createArrayNode();

            while (it.hasNext()) {
                Map<String, String> row = it.next();
                ObjectNode jsonRow = jsonMapper.createObjectNode();
                row.forEach((key, value) -> jsonRow.put(key, value));
                jsonArray.add(jsonRow);
            }

            // Write the JSON array to output file
            jsonMapper.writerWithDefaultPrettyPrinter()
                      .writeValue(jsonFile, jsonArray);
            System.out.println("Conversion completed successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (it != null) {
                try {
                    it.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
}


