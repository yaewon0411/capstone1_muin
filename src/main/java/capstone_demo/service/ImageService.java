package capstone_demo.service;

import capstone_demo.domain.Image;
import capstone_demo.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    @Transactional
    public void imageSave(Image image){
        imageRepository.save(image);
    }
    @Transactional
    public void deleteImageByHistoryId(Long id) {
        findPathByHistoryId(id);
        imageRepository.deleteImageByHistoryId(id);
    }
    public boolean checkImage(Long id){
        return imageRepository.checkImage(id);
    }
    public String findPathByHistoryId(Long id) {
        Image findImage = imageRepository.findPathByHistoryId(id);
        return findImage.getPath();
    }

}
