package com.shop.service;


import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Transactional
@RequiredArgsConstructor //필요한 생성자 생성. new ItemTypeRepository();
@Service
public class ItemImgService {
    @Value("${itemImgLocation}") //application.properties파일에 등록한 값을 불러옴.
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    //이미지 등록
    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes()); //실제 파일올리는 여기에 들어가는 itemImgLocation인 거 같고 C:/shop/item
            imgUrl = "/images/item/" + imgName; //아니 shop/item 밑에 사진이 저장되는데 /images/item/밑에서 이미지를 어떻게 불러오는 거지?
        }

        //상품 이미지 정보 저장
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);  //여기가 dto에 set하는거고 그래서 test에서 여기서 넣은 oriImgName을 비교하네.
        itemImgRepository.save(itemImg); //여기가 DB에 set하는 거네
    }


    //이미지 추가 업데이트
    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws  Exception{
        if(!itemImgFile.isEmpty()){
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);
            //savedItem 엔티티는 현재 영속 상태이므로 데이터를 변경하는 것만으로 변경 감지 기능이 동작하여 트랜잭션이 끝날 대 update 쿼리가 실행됩니다.
            if(!StringUtils.isEmpty(savedItemImg.getImgName())){
                fileService.deleteFile(itemImgLocation+"/"+savedItemImg.getImgName());
            }
            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);

        }
    }
}
