package capstone_demo.repository;

import capstone_demo.domain.Admin;
import capstone_demo.domain.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResiRepo extends JpaRepository<Admin, Optional> {

}
