package is.technologies.repos;

import is.technologies.models.House;
import is.technologies.models.Street;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreetRepo extends JpaRepository<Street, Long> {
}
