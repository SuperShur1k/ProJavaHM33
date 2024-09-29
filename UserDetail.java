package de.telran.myshop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "details")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDetailId;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    private Date dob;
    private String tel;

    @OneToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId"
    )
    private User user;


    public void setDob(Date dob) {
        if (dob != null) {
            Calendar calendar = Calendar.getInstance();
            Date today = calendar.getTime();

//             Напишите валидацию для dob в UserDetail чтобы дата рождения не была в будущем от текущего дня
            if (dob.after(today)) {
                throw new IllegalArgumentException("Date of birth cannot be in the future");
            }

            // Напишите валидацию для dob в UserDetail  чтоб дата не была в прошлом больше чем на 100 лет от текущего дня 
            calendar.add(Calendar.YEAR, -100);
            Date hundredYearsAgo = calendar.getTime();

            if (dob.before(hundredYearsAgo)) {
                throw new IllegalArgumentException("Date of birth cannot be older than 100 years");
            }
        }
        this.dob = dob;
    }
}
