package com.example.springdatajpatest.service;

import com.csvreader.CsvReader;
import com.example.springdatajpatest.entity.Room;
import com.example.springdatajpatest.entity.Student;
import com.example.springdatajpatest.model.RoomId;
import com.example.springdatajpatest.repository.RoomRepository;
import com.example.springdatajpatest.repository.StudentRepository;
import org.mozilla.universalchardet.UniversalDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
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

    public void insertDataFromCsv(String tableName) {
        String csvFilePath = BASE_CSV_FILE_PATH + tableName + ".csv";

        try {

            String detectedCharset = getFileEncode(csvFilePath);

            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName(detectedCharset));
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

                    String registno = reader.get("registno");
                    Integer kdno = Integer.parseInt(reader.get("kdno"));
                    Integer kcno = Integer.parseInt(reader.get("kcno"));
                    Integer ccno = Integer.parseInt(reader.get("ccno"));

                    Student existingStudent = studentRepository.findById(registno).orElse(null);
                    Room room = roomRepository.findById(new RoomId(kdno, kcno, ccno)).orElse(null);

                    if (existingStudent != null) {
                        System.out.println("主键冲突，未插入：" + existingStudent);
                    } else if (room == null) {
                        System.out.println("外键冲突，未插入：" + kdno + ", kcno=" + kcno + ", ccno=" + ccno + ".");
                    } else {
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

            }

            System.out.println("CSV data of " + tableName + " has been inserted successfully.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 自动识别 csv文件的编码格式
    public static String getFileEncode(String filePath) throws IOException {
        FileInputStream in = new FileInputStream(new File(filePath));
        String code = "utf-8";
        byte[] buf = new byte[4096];
        UniversalDetector detector = new UniversalDetector(null);

        int nread;
        while ((nread = in.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }

        detector.dataEnd();

        String encoding = detector.getDetectedCharset();
        if (!encoding.isEmpty()) {
            code = encoding;
        }
        in.close();
        return code;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}