package capstone_demo.repository;

import capstone_demo.domain.resident.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentRepo extends JpaRepository<Resident, Long> {
}
