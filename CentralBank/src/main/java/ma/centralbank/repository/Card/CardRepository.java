package ma.centralbank.repository.Card;

import ma.centralbank.models.CardLimit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardLimit, Integer> {
}
