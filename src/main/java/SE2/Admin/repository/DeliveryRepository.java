package SE2.Admin.repository;

import SE2.Admin.model.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<DeliveryStatus, Long> {
}
