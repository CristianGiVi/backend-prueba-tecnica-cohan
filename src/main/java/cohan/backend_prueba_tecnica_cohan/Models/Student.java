package cohan.backend_prueba_tecnica_cohan.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "student")
public class Student extends Person{

    @NotBlank
    private String studentNumber;

    @NotNull
    @Min(0)
    @Max(5)
    private float averageMark;

    public Student() {
    }

    public Student(String name, String phoneNumber, Address address, String emailAddress,
                   String studentNumber, float averageMark) {
        super(name, phoneNumber, emailAddress, address);
        this.studentNumber = studentNumber;
        this.averageMark = averageMark;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public float getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(float averageMark) {
        this.averageMark = averageMark;
    }

    public boolean isEligibleToEnroll(){
        return true;
    }

    public List<String> getSeminarsTaken(){
        return List.of();
    }
}
