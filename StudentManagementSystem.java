import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;



public class StudentManagementSystem {
    private static ArrayList<Student> students = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);




    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/StudentManagementSystem";
        String userName = "root";
        String password = "";
        Connection connection = DriverManager.getConnection(url, userName, password);
        if (!connection.isClosed()) {
            System.out.println(connection.getMetaData().getDatabaseProductName());
        }
        Scanner sc = new Scanner(System.in);

        while (true){

            int choice;
            displayMenu();

            choice=sc.nextInt();
            sc.nextLine();

            System.out.println();

            switch(choice){
                case 1:
                    addStudent();
                    System.out.println();
                    break;
                case 2:
                    listOfStudents();
                    System.out.println();
                    break;
                case 3:
                    searchStudent();
                    System.out.println();
                    break;
                case 4:
                    updateStudent();
                    System.out.println();
                    break;
                case 5:
                    deleteStudent();
                    System.out.println();
                    break;
                case 6:
                    System.out.println("Existing");
                    System.out.println("*********");
                    return;
                default:
                    System.out.println("Invalid choice");

            }






        }


    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println("*** Welcome to Student Management System ***");
        System.out.println("--------------------------------------------");
        System.out.println("");

        System.out.println("1. Add Student");

        System.out.println("2. List Students");

        System.out.println("3. Search Student");

        System.out.println("4. Update Student");

        System.out.println("5. Delete Student");

        System.out.println("6. Exit");
        System.out.println();

        System.out.print("Enter your choice: ");

    }

    private static void addStudent() throws SQLException {

        System.out.println("--------------------");
        System.out.println("    Add Student     ");
        System.out.println("--------------------");
        System.out.println();
        Scanner sc = new Scanner(System.in);


        System.out.print("Enter student ID: ");
        int studentId = sc.nextInt();
        System.out.print("Enter student name: ");
        String name = sc.next();
        System.out.print("Enter student birthday: ");
        String birthday = sc.next();
        System.out.print("Enter student email: ");
        String email = sc.next();

        Student student = new Student(studentId, name, birthday, email);
        students.add(student);

        saveStudentToDatabase(student);
        System.out.println();

        System.out.println("---Student added successfully!---");

    }

    private static void saveStudentToDatabase(Student student) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/StudentManagementSystem";
        String userName = "root";
        String password = "";
        Connection connection = DriverManager.getConnection(url, userName, password);

        String query = "INSERT INTO student (studentId, name, birthday, email) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, student.getStudentId());
        preparedStatement.setString(2, student.getName());
        preparedStatement.setString(3, student.getBirthday());
        preparedStatement.setString(4, student.getEmail());
        preparedStatement.executeUpdate();
    }

    private static void listOfStudents() throws ClassNotFoundException, SQLException {

        System.out.println("--------------------");
        System.out.println("   List of Students ");
        System.out.println("--------------------");
        System.out.println();

        String url = "jdbc:mysql://localhost:3306/StudentManagementSystem";
        String userName = "root";
        String password = "";
        Connection connection = DriverManager.getConnection(url, userName, password);

        String query = "SELECT * FROM student";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if (!resultSet.isBeforeFirst()){
            System.out.println("No Students found.");
        } else {
            while (resultSet.next()) {
                int studentId = resultSet.getInt("studentId");
                String name = resultSet.getString("name");
                String birthday = String.valueOf(resultSet.getDate("birthday"));
                String email = resultSet.getString("email");
                System.out.println(new Student(studentId, name, birthday.toString(), email));
            }

        }




    }

    private static void searchStudent() throws ClassNotFoundException, SQLException {

        System.out.println("--------------------");
        System.out.println("    Search Student   ");
        System.out.println("--------------------");
        System.out.println();

        String url = "jdbc:mysql://localhost:3306/StudentManagementSystem";
        String userName = "root";
        String password = "";
        Connection connection = DriverManager.getConnection(url, userName, password);

        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        System.out.println();

        String query = "SELECT * FROM student WHERE StudentID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, studentId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            studentId = resultSet.getInt("studentId");
            String studentName = resultSet.getString("name");
            String birthday = String.valueOf(resultSet.getDate("birthday"));
            String email = resultSet.getString("email");
            System.out.println(new Student(studentId, studentName, birthday.toString(), email));
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void updateStudent() throws ClassNotFoundException , SQLException {

        System.out.println("--------------------");
        System.out.println("    Update Student  ");
        System.out.println("--------------------");
        System.out.println();

        String url = "jdbc:mysql://localhost:3306/StudentManagementSystem";
        String userName = "root";
        String password = "";
        Connection connection = DriverManager.getConnection(url, userName, password);

        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter student name: ");
        String name = scanner.next();
        System.out.print("Enter student birthday: ");
        String birthday = scanner.next();
        System.out.print("Enter student email: ");
        String email = scanner.next();
        System.out.println();

        String query = "UPDATE student SET name = ?, birthday = ?, email = ? WHERE studentId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, birthday);
        preparedStatement.setString(3, email);
        preparedStatement.setInt(4, studentId);
        preparedStatement.executeUpdate();
        System.out.println();

        System.out.println("--- Student updated successfully! ---");



    }

    private static void deleteStudent() throws ClassNotFoundException, SQLException {

        System.out.println("--------------------");
        System.out.println("    Delete Student  ");
        System.out.println("--------------------");
        System.out.println();

        String url = "jdbc:mysql://localhost:3306/StudentManagementSystem";
        String userName = "root";
        String password = "";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        System.out.println();

        String query = "DELETE FROM student WHERE studentId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, studentId);
        preparedStatement.executeUpdate();
        System.out.println();

        System.out.println("--- Student deleted successfully! ---");
    }


}
