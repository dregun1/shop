package com.shop.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //파일 확장자 추출
        String savedFileName = uuid.toString() + extension;  //새로운파일이름 + 확장자
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;  //파일경로 + 파일이름.확장자
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl); //주어진 파일 경로(fileUploadFullUrl)에 새로운 파일을 생성하고 해당 파일에 데이터를 쓸 수 있는 FileOutputStream을 생성하는 코드
        fos.write(fileData); //fileData에는 업로드된 파일의 실제 데이터가 담겨 있고, 이를 사용하여 파일에 데이터를 씁니다.
        fos.close(); //파일 출력 스트림(FileOutputStream)을 닫는 역할
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);  // filePath는 uploadFile 메서드에서 생성한 파일의 전체 경로

        if(deleteFile.exists()){
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        }else{
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
