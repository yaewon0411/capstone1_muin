package capstone_demo.repository;

import capstone_demo.domain.*;
import capstone_demo.dto.ParcelInfo;
import jakarta.persistence.TypedQuery;
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

        return em.createQuery("select ph from ParcelHistory ph where ph.status like :status",ParcelHistory.class)
                .setParameter("status", status)
                .getResultList();
    }

    // 택배를 통해 택배 내역 추출
    public List<ParcelHistory> findHistoryByParcel(@NonNull Parcel parcel){ //지금 deliverer 필드 null로 나옴 수정해야함
        String jpql = "select ph from ParcelHistory ph left join fetch ph.parcel p " +
                "left join fetch ph.deliverer d where ph.parcel like :parcel " +
                "and p.deliverer.id = ph.deliverer.id "+
                "and p.deliverer.company = ph.deliverer.company";
        return em.createQuery(jpql, ParcelHistory.class)
                .setParameter("parcel",parcel)
                .getResultList();

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

        List<ParcelHistory> findHistory = em.createQuery("select ph from ParcelHistory ph " +
                        "left join fetch ph.resident r " +
                        "left join fetch ph.parcel p " +
                        "left join fetch ph.deliverer d " +
                        "where ph.resident like :resident", ParcelHistory.class)
                .setParameter("resident", resident).getResultList();
        for (ParcelHistory parcelHistory : findHistory) {
            em.remove(parcelHistory);
        }
//
//
//        em.createQuery("delete from ParcelHistory ph where ph.resident like :resident")
//                .setParameter("resident", resident)
//                .executeUpdate();
    }
    //사명과 송장번호, 상태, 회사만 추출
    public List<Object[]> findCompanyAndTrackingNumberAndStatus(Resident resident) {
        String jpql = "select ph.deliverer.company, ph.parcel.trackingNumber, " +
                "ph.status, ph.localDateTime, ph from ParcelHistory ph " +
                "left join fetch ph.deliverer d " +
                "left join fetch ph.parcel p " +
                "left join fetch ph.resident r "+
                "where ph.resident = :resident";

        return em.createQuery(jpql, Object[].class)
                .setParameter("resident", resident)
                .getResultList();
    }
    //사명, 거주인이름, 상태, 날짜, 송장번호 추출
    public List<Object[]> findCompanyAndTrackingNumberAndStatusAndName() {
        String jpql = "select ph.deliverer.company, ph.parcel.trackingNumber, ph.status, ph.localDateTime, ph.resident.name, ph from ParcelHistory ph " +
                "left join fetch ph.deliverer d " +
                "left join fetch ph.parcel p " +
                "left join fetch ph.resident r";

        return em.createQuery(jpql, Object[].class)
                .getResultList();
    }

    public List<Object[]> findAwaitingReturnParcelList(String company) {
        String jpql = "select ph.deliverer.company, ph.parcel.trackingNumber, ph.status, ph.localDateTime, ph from ParcelHistory ph " +
                "left join fetch ph.deliverer d " +
                "left join fetch ph.parcel p " +
                "left join fetch ph.resident r "+
                "where ph.status like :status and ph.deliverer.company like :company";

        return em.createQuery(jpql, Object[].class)
                .setParameter("status", ParcelStatus.AWAITING_RETURN)
                .setParameter("company",company)
                .getResultList();
    }

    public List<ParcelHistory> findHistoryByTrackingNumber(String trackingNumber) {

        String jpql = "select ph from ParcelHistory ph " +
                "left join fetch ph.parcel p " +
                "left join fetch ph.deliverer d "+
                "where ph.parcel.trackingNumber = p.trackingNumber and " +
                "ph.parcel.trackingNumber like :trackingNumber and " +
                "ph.parcel.deliverer.id = d.id and " +
                "ph.parcel.deliverer.company = d.company";

        return em.createQuery(jpql, ParcelHistory.class)
                .setParameter("trackingNumber", trackingNumber)
                .getResultList();
    }

    public List<ParcelHistory> findReturnedParcel(String trackingNumber) {

        System.out.println("trackingNumber = " + trackingNumber);
        String jpql = "select ph from ParcelHistory ph " +
                "left join fetch ph.parcel p " +
                "where ph.parcel.trackingNumber like :trackingNumber " +
                "and ph.status like :status";

        return em.createQuery(jpql, ParcelHistory.class)
                .setParameter("trackingNumber",trackingNumber)
                .setParameter("status",ParcelStatus.RETURNED)
                .getResultList();
    }
}
