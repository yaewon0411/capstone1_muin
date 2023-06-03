package capstone_demo.repository;

import capstone_demo.domain.*;
import capstone_demo.dto.ParcelInfo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ParcelHistoryRepository {


    private final EntityManager em;

    public void save(ParcelHistory parcelHistory){
        em.persist(parcelHistory);
    }

    // 상태를 통해 택배 내역 추출
    public List<ParcelHistory> findHistoryByStatus(@NonNull ParcelStatus status){

        return em.createQuery("select ph from ParcelHistory ph where ph.status like :status")
                .setParameter("status", status)
                .getResultList();
    }

    // 택배를 통해 택배 내역 추출
    public List<ParcelHistory> findHistoryByParcel(@NonNull Parcel parcel){

        List findHistory = em.createQuery("select ph from ParcelHistory ph where ph.parcel like :parcel", ParcelHistory.class)
                .setParameter("parcel", parcel.getTrackingNumber())
                .getResultList();
        return findHistory;

    }
    // 거주인을 통해 택배 내역 추출
    public List<ParcelHistory> findHistoryByResident(Resident resident){

        String jpql = "SELECT ph FROM ParcelHistory ph " +
                "LEFT JOIN FETCH ph.deliverer d " +
                "WHERE d.id = ph.deliverer.id " +
                "AND d.company = ph.deliverer.company " +
                "AND ph.resident LIKE :resident";

        List <ParcelHistory> findHistory;
        findHistory = em.createQuery(jpql, ParcelHistory.class)
                .setParameter("resident", resident)
                .getResultList();

        return findHistory;
    }
    // 거주인에 해당하는 모든 택배 내역 레코드 삭제
    public void deleteResidentParcelHistory(Resident resident){

        em.createQuery("delete from ParcelHistory ph where ph.resident like :resident")
                .setParameter("resident", resident)
                .executeUpdate();
    }
    //사명과 송장번호, 상태만 추출
    public List<Object[]> findCompanyAndTrackingNumberAndStatus(Resident resident) {
        String jpql = "select ph.deliverer.company, ph.parcel.trackingNumber, ph.status, ph.localDateTime, ph from ParcelHistory ph " +
                "left join fetch ph.deliverer d " +
                "left join fetch ph.parcel p " +
                "left join fetch ph.resident r "+
                "where ph.resident = :resident";

        List<Object[]> find = em.createQuery(jpql, Object[].class)
                .setParameter("resident", resident)
                .getResultList();
        

//        Object[] toRemove = list.get(0);
//        toRemove = Arrays.copyOf(toRemove, toRemove.length-1);
//        list.set(0, toRemove);
//
//        return list.get(0);

        return find;
    }

}
