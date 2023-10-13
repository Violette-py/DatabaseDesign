public class Main {
    public static void main(String[] args) throws Exception {

        try {
            InitDatabase.init();

            DatabaseOperation.insertCsvData("room", "GBK");
            DatabaseOperation.insertCsvData("student", "utf-8");

//                System.out.println(reader1.getRawRecord());

            System.out.println("CSV data has been inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}