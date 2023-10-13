package com.example.springdatajpatest.service;

import com.csvreader.CsvReader;
import com.example.springdatajpatest.entity.Room;
import com.example.springdatajpatest.entity.Student;
import com.example.springdatajpatest.repository.RoomRepository;
import com.example.springdatajpatest.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;

@Service
public class DatabaseService {
    private static final String BASE_CSV_FILE_PATH = "src/main/resources/data/";
    private final RoomRepository roomRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public DatabaseService(RoomRepository roomRepository, StudentRepository studentRepository) {
        this.roomRepository = roomRepository;
        this.studentRepository = studentRepository;
    }

    public void insertRoomDataFromCsv() {
        insertDataFromCsv("room", "GBK");
    }

    public void insertStudentDataFromCsv() {
        insertDataFromCsv("student", "UTF-8");
    }

    private void insertDataFromCsv(String tableName, String charsetName) {
        String csvFilePath = BASE_CSV_FILE_PATH + tableName + ".csv";

        try {
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName(charsetName));
            reader.readHeaders(); // 读取 csv 文件的标题行，即列名

            if ("room".equals(tableName)) {

                while (reader.readRecord()) {
                    Room room = new Room(
                            Integer.parseInt(reader.get("kdno")),
                            Integer.parseInt(reader.get("kcno")),
                            Integer.parseInt(reader.get("ccno")),
                            isNullOrEmpty(reader.get("kdname")) ? null : reader.get("kdname"),
                            isNullOrEmpty(reader.get("exptime")) ? null : reader.get("exptime"),
                            isNullOrEmpty(reader.get("papername")) ? null : reader.get("papername")
                    );
                    roomRepository.save(room);
                }

            } else if ("student".equals(tableName)) {

                while (reader.readRecord()) {
                    Student student = new Student(
                            reader.get("registno"),
                            reader.get("name"),
                            Integer.parseInt(reader.get("kdno")),
                            Integer.parseInt(reader.get("kcno")),
                            Integer.parseInt(reader.get("ccno")),
                            Integer.parseInt(reader.get("seat"))
                    );
                    studentRepository.save(student);
                }

            }

            System.out.println("CSV data of " + tableName + " has been inserted successfully.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}