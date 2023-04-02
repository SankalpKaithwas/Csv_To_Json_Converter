package com.converter.csv.to.json;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class Csv_TOJson {

	public static void main(String[] args) throws IOException {
		
		/**
		 * This approach reads the CSV file directly into a list of Person objects
		 * and uses Jackson's ObjectMapper to serialize the list to a JSON array. This
		 * approach is faster and requires less code, but it assumes that the CSV file
		 * has a fixed schema and that the column names and types are known in advance.
		 */

		// Set up the input and output files
        File csvFile = new File("Peoples.csv");
        File jsonFile = new File("People.json");

        
        if (jsonFile.exists()) {
            String filename = jsonFile.getName();
            int lastDotIndex = filename.lastIndexOf('.');
            String extension = filename.substring(lastDotIndex);
            String newFilename = filename.substring(0, lastDotIndex) + (System.currentTimeMillis() / 1000) + extension;
            jsonFile = new File(jsonFile.getParent(), newFilename);
        }

        // Create a CsvMapper
        CsvMapper csvMapper = new CsvMapper();

        // Set up the schema to use the first row as the header
        CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();

        // Increase buffer size to handle large number of records
        csvMapper.enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE);

        // Read the CSV file into a list of Person objects
        List<Person> persons = null;
        MappingIterator<Person> it = null;
        try {
            it = csvMapper.readerFor(Person.class)
                    .with(csvSchema)
                    .readValues(csvFile);
            persons = it.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (it != null) {
                it.close();
            }
        }

        // Convert the list of Person objects to a JSON array
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ObjectWriter writer = jsonMapper.writerWithDefaultPrettyPrinter();
        try {
            writer.writeValue(jsonFile, persons);
            System.out.println("Conversion completed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	}



