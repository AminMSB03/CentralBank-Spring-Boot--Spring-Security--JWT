package ma.centralbank.repository.cin;

import ma.centralbank.models.Cin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinRepository extends JpaRepository<Cin, Long> {
}
