//import com.opencsv.CsvReader;

import com.csvreader.CsvReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws Exception {

        InitDatabase.init();

        try {

            DatabaseOperation.insertCsvData("room", "GBK");
            DatabaseOperation.insertCsvData("student", "utf-8");

//                System.out.println(reader1.getRawRecord());

            System.out.println("CSV data has been inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}