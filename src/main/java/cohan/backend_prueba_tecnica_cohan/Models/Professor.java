package cohan.backend_prueba_tecnica_cohan.Models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Professor extends Person{

    @NotNull
    @Min(0)
    private Integer salary;

    public Professor() {
    }

    public Professor(String name, String phoneNumber, String emailAddress, Address address,
                     int salary) {
        super(name, phoneNumber, emailAddress, address);
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary){
        this.salary = salary;
    }
}
