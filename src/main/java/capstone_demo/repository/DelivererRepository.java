package capstone_demo.repository;

import capstone_demo.domain.Deliverer;
import capstone_demo.domain.Resident;
import capstone_demo.repository.search.DelivererSearch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DelivererRepository {

    private final EntityManager em;

    public void save(Deliverer deliverer){
        em.persist(deliverer);
    }

    public Deliverer findByDeliverer(Deliverer deliverer){
        Deliverer findOne = em.createQuery("select d from Deliverer d where id like :id" +
                        "and company like :company" +
                        "and name like :name", Deliverer.class)
                .setParameter("id", deliverer.getId())
                .setParameter("company", deliverer.getCompany())
                .setParameter("name", deliverer.getName())
                .getSingleResult();
        return findOne;
    }


    //모든 배달인 조회
    public List<Deliverer> findAllDeliverer(){

        List<Deliverer> findDeliverers = em.createQuery("select d from Deliverer d", Deliverer.class).getResultList();
        return findDeliverers;
    }
    //검색 조건에 해당하는 배달인 조회
    public List<Deliverer> findAllByString(DelivererSearch search){

        String jpql = "select d from Deliverer d";

        boolean isFirstCondition = true;

        if(StringUtils.hasText(search.getName())){
            if(isFirstCondition){
                jpql += " where";
                isFirstCondition = false;
            }else{
                jpql += " and";
            }
            jpql += " d.name like :name";
        }
        if(StringUtils.hasText(search.getId())){
            if(isFirstCondition){
                jpql += " where";
                isFirstCondition = false;
            }else{
                jpql += " and";
            }
            jpql += " d.id like :id";
        }
        if(StringUtils.hasText(search.getCompany())){
            if(isFirstCondition){
                jpql += " where";
                isFirstCondition = false;
            }else{
                jpql += " and";
            }
            jpql += "d.company like :company";
        }

        TypedQuery<Deliverer> query = em.createQuery(jpql, Deliverer.class); //최대 100건

        if(StringUtils.hasText(search.getName())){
            query = query.setParameter("name", search.getName());
        }
        if(StringUtils.hasText(search.getId())){
            query = query.setParameter("id", search.getId());
        }
        if(StringUtils.hasText(search.getCompany())){
            query = query.setParameter("company", search.getCompany());
        }

        return query.getResultList();
    }

    public List<Deliverer> findByUsername(String username) { //deliverer의 id값

        return em.createQuery("select d from Deliverer d where d.id like :id", Deliverer.class)
                .setParameter("id", username)
                .getResultList();
    }

    public List<Deliverer> findByIdAndName(String id, String name) {
        return em.createQuery("select d from Deliverer d where d.id like :id and d.name like :name",Deliverer.class)
                .setParameter("id",id)
                .setParameter("name",name)
                .getResultList();
    }
    public List<Deliverer> findNameByIdAndCompany(String id, String company){
        return em.createQuery("select d from Deliverer d where d.id like :id and d.company like :company",Deliverer.class)
                .setParameter("id",id)
                .setParameter("company",company)
                .getResultList();
    }
    public List<Deliverer> findById(String id){
        return em.createQuery("select d from Deliverer d where d.id like :id",Deliverer.class)
                .setParameter("id",id)
                .getResultList();
    }
}
