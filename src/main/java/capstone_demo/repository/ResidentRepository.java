package capstone_demo.repository;

import capstone_demo.domain.Resident;
import capstone_demo.domain.ResidentId;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ResidentRepository{

    private final EntityManager em;

    public void save(Resident resident){

        em.persist(resident);
    }
    public List<Resident> findAllResident(){
        return em.createQuery("select r from Resident r",Resident.class)
                .getResultList();
    }
    public Resident findByResident(Resident resident){

        String jpql = "select r from Resident r where r.name like :name and r.address like :address and r.birth like :birth";
        List<Resident> findResidents = em.createQuery(jpql, Resident.class)
                .setParameter("name", resident.getName())
                .setParameter("address", resident.getAddress())
                .setParameter("birth", resident.getBirth())
                .getResultList();

        if (!findResidents.isEmpty()) {
            return findResidents.get(0);
        } else {
            throw new NoResultException("존재하는 거주인이 없습니다.");
        }


//        Resident findResident = null;
//        try {
//            findResident = em.createQuery("select r from Resident r where r.name like :name and r.address like :address and r.birth like :birth", Resident.class)
//                    .setParameter("name", resident.getName())
//                    .setParameter("address", resident.getAddress())
//                    .setParameter("birth", resident.getBirth())
//                    .getSingleResult();
//
//            System.out.println("findResident.getName() = " + findResident.getName());
//            System.out.println("findResident.getAddress() = " + findResident.getAddress());
//            System.out.println("findResident.getAddress() = " + findResident.getAddress());
//        }catch (NoResultException e){
//            e.printStackTrace();
//        }
//        System.out.println("(findResident==resident) = " + (findResident==resident));
//        return findResident;
    }
    public List<Resident> findByName(String name){
        return em.createQuery("select r from Resident r where r.name like :name",Resident.class)
                .setParameter("name",name)
                .setMaxResults(10)
                .getResultList();
    }
    public Resident findByBirthAndAddress(String birth, String address){
        System.out.println("레포 조회 메서드 실행");
        List<Resident> resultList = em.createQuery("select r from Resident r where r.birth like :birth and r.address like :address", Resident.class)
                .setParameter("birth", birth)
                .setParameter("address", address)
                .getResultList();
        for (Resident resident : resultList) {
            System.out.println("resident.getName() = " + resident.getName());
        }
        if(resultList.isEmpty()){
            throw new NoResultException("조회 결과 없음");
        }else{
            return resultList.get(0);
        }
    }
    public List<Resident> findByAddress(String address){
        return em.createQuery("select r from Resident r where r.address like :address",Resident.class)
                .setParameter("address",address)
                .getResultList();
    }
    public List<Resident> findByBirth(String birth){
        return em.createQuery("select r from Resident r where r.birth like :birth",Resident.class)
                .setParameter("birth",birth)
                .getResultList();
    }
        public List<Resident> findAllByString(ResidentSearch residentSearch){

        String jpql = "select r From Resident r";
        boolean isFirstCondition = true;

        if(StringUtils.hasText(residentSearch.getName())){
            if(isFirstCondition){
                jpql += " where";
                isFirstCondition = false;
            }else{
                jpql += " and";
            }
            jpql += " r.name like :name";
        }
        if(StringUtils.hasText(residentSearch.getAddress())){
            if(isFirstCondition){
                jpql += " where";
                isFirstCondition = false;
            }else{
                jpql += " and";
            }
            jpql += " r.address like :address";
        }
        if(StringUtils.hasText(residentSearch.getBirth())){
            if(isFirstCondition){
                jpql += " where";
                isFirstCondition = false;
            }else{
                jpql += " and";
            }
            jpql += " r.birth like :birth";
        }

            TypedQuery<Resident> query = em.createQuery(jpql, Resident.class).setMaxResults(1000); //최대 1000건

            if(StringUtils.hasText(residentSearch.getName())){
                query = query.setParameter("name", residentSearch.getName());
            }
            if(StringUtils.hasText(residentSearch.getAddress())){
                query = query.setParameter("address", residentSearch.getAddress());
            }
            if(StringUtils.hasText(residentSearch.getBirth())){
                query = query.setParameter("birth", residentSearch.getBirth());
            }

            return query.getResultList();
    }
    public void deleteResident(Resident resident){

        em.createQuery("delete from Resident r where r.name like :name and r.address like :address and r.birth like :birth")
                .setParameter("name", resident.getName())
                .setParameter("address", resident.getAddress())
                .setParameter("birth", resident.getBirth());
        System.out.print("[name = " + resident.getName());
        System.out.print(", address = " + resident.getAddress());
        System.out.print(", birth = " + resident.getBirth() + "] 삭제 완료");
    }

}
