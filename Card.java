package de.telran.myshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cards")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    HomeWork
//    1. Добавьте валидацию в класс Card что name должно быть не пустым и длиной не менее 5 символов
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @ManyToMany(
        fetch = FetchType.LAZY, // пока явно не обратимся к продуктам карты,
        // они не загрузятся из базы данных
        cascade = {
            CascadeType.ALL, // как изменения карты должны отражаться на продукте
            CascadeType.MERGE
        },
        mappedBy = "cards" // поле в классе Product
    )
    @JsonIgnore
    @ToString.Exclude // не использовать поле для генерации toString
    @EqualsAndHashCode.Exclude // не использовать поле для equals/hashCode
    private Set<Product> products = new HashSet<>();
}
