import java.sql.Date;
import java.util.ArrayList;

public class Student {

    ArrayList <Student> students = new ArrayList<>();
    private int studentId;
    private String name;
    private String birthday;
    private String email;

    public Student(int studentId, String name, String birthday, String email) {
        this.studentId = studentId;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "StudentId: " + studentId + ", Name: " + name + ", Birthday: " + birthday + ", Email: " + email;
    }
}
