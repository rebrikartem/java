package is.technologies.repos;

import is.technologies.models.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseRepo extends JpaRepository<House, Long> {
    List<House> findAllByStreetId(long id);
}
