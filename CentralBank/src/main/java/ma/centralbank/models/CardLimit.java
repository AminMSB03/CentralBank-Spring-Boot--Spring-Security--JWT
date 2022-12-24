package ma.centralbank.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.centralbank.enums.CardType;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class CardLimit {


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int cardId;

    private int limitForDay;
    private int limitForYear;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

}
