package capstone_demo.repository;

import capstone_demo.domain.Admin;
import capstone_demo.repository.search.AdminSearch;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminRepository {

    private final EntityManager em;

    public void save(Admin admin){
        em.persist(admin);
    }
    //등록된 모든 관리자 리스트 조회
    public List<Admin> findAllAdmin(){

        return  em.createQuery("select a from Admin a", Admin.class).getResultList();
    }
    public Admin findByAdmin(Admin admin){
        return  em.createQuery("select a from Admin a where a.id like :id and a.password like :password", Admin.class)
                .setParameter("id", admin.getId())
                .setParameter("password", admin.getPassword())
                .getSingleResult();
    }
    //관리자 비밀번호 찾기
    public String findAdminPasswordById(AdminSearch adminSearch){

        String password="";

        String jpql = "select a from Admin a where a.id like :id and a.residence like :residence and a.zipcode like :zipcode";

        Admin findAdmin = em.createQuery(jpql, Admin.class)
                .setParameter("id", adminSearch.getId())
                .setParameter("residence", adminSearch.getResidence())
                .setParameter("zipcode", adminSearch.getZipcode())
                .getSingleResult();
        password = findAdmin.getPassword();

        return password;
    }


}
