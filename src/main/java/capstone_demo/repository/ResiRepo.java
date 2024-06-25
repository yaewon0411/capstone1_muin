package capstone_demo.repository;

import capstone_demo.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResiRepo extends JpaRepository<Admin, Optional> {

}
