package ma.centralbank.repository.payments;

import ma.centralbank.models.OnlinePayments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlinePaymentsRepository extends JpaRepository<OnlinePayments, Integer> {
}
