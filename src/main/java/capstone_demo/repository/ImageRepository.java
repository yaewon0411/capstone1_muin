package capstone_demo.repository;

import capstone_demo.domain.Image;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageRepository {
    private final EntityManager em;

    public void save(Image image){
        em.persist(image);
    }

    public Image findPathByHistoryId(Long id) {
        List<Image> findList = em.createQuery("select i from Image i " +
                        "left join fetch i.parcelHistory ph " +
                        "where i.parcelHistory.id = :id",Image.class)
                .setParameter("id", id)
                .getResultList();
        if(findList.size()==0) throw new NoResultException("존재하는 이미지 경로가 없습니다.");
        else return findList.get(0);
    }
    public void deleteImageByHistoryId(Long id) {
        List<Image> findList = em.createQuery("select i from Image i " +
                        "left join fetch i.parcelHistory ph " +
                        "where i.parcelHistory.id = :id",Image.class)
                .setParameter("id", id)
                .getResultList();

        em.remove(findList.get(0));
    }
    public boolean checkImage(Long id){
        List<Image> findList = em.createQuery("select i from Image i " +
                        "left join fetch i.parcelHistory ph " +
                        "where i.parcelHistory.id = :id",Image.class)
                .setParameter("id", id)
                .getResultList();
        if(findList.size()==1) return true;
        else return false;
    }

}
