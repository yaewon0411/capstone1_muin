package capstone_demo.repository;

import capstone_demo.domain.Parcel;
import capstone_demo.repository.search.ParcelSearch;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import jakarta.persistence.EntityManager;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ParcelRepository {
    private final EntityManager em;

    //택배 저장
    public void save(Parcel parcel){
        em.persist(parcel);
    }

    //모든 택배 조회
    public List<Parcel> findAll(){
        return em.createQuery("select p from Parcel p",Parcel.class)
                .getResultList();
    }


    //name, address 를 통해 택배 조회
    public List<Parcel> findAllByString(ParcelSearch search) {

        String jpql = "select p from Parcel p join p.resident p";
        boolean isFirstCondition = true;

        if (StringUtils.hasText(search.getName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " p.resident_name like :name";
        }
        if (StringUtils.hasText(search.getAddress())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " p.resident_address like :address";
        }
        TypedQuery<Parcel> query = em.createQuery(jpql, Parcel.class);

        if (StringUtils.hasText(search.getName()))
            query.setParameter("name", search.getName());
        if (StringUtils.hasText(search.getAddress()))
            query.setParameter("address", search.getAddress());

        return query.setFirstResult(1).setMaxResults(100)
                .getResultList();
    }

    //택배 제거
    public void deleteParcel(Parcel parcel){
        em.createQuery("delete table Parcel p where p.resident like :resident")
                .setParameter("resident", parcel.getResident());
    }


}
