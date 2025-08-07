package com.itwillbs.db.items.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.db.commons.util.FileUtils;
import com.itwillbs.db.items.dto.ItemImgDTO;
import com.itwillbs.db.items.entity.Item;
import com.itwillbs.db.items.entity.ItemImg;
import com.itwillbs.db.items.repository.ItemImgRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ItemImgService {
	// 파일 업로드 처리에 사용할 경로를 properties 파일에서 가져오기
	// => 변수 선언부 앞(위)에 @Value("${프로퍼티속성명}") 형태로 선언
	@Value("${file.uploadBaseLocation}")
	private String uploadBaseLocation;
	
	@Value("${file.itemImgLocation}")
	private String itemImgLocation;
	
	private final ItemImgRepository itemImgRepository;

	public ItemImgService(ItemImgRepository itemImgRepository) {
		this.itemImgRepository = itemImgRepository;
	}
	// ======================================================
	@Autowired
	private FileUtils fileUtils;
	// ======================================================
	// 상품 이미지 첨부파일 등록 
	public void registItemImg(Item item, List<MultipartFile> itemImgFiles) throws IOException {
		// ItemImg 엔티티 목록을 저장할 List<ItemImg> 객체 생성
		List<ItemImg> itemImgList = new ArrayList<ItemImg>();
		
		// 임시) 이미지 파일(배열) 인덱스를 기록할 변수(대표이미지 설정에 활용)
		int index = 0;
		
		// 파일이 저장된 List<MultipartFile> 객체 반복
		for(MultipartFile mFile : itemImgFiles) {
			// 원본 파일명 추출
			String originalFileName = mFile.getOriginalFilename();
//			System.out.println("originalFileName : " + originalFileName);
			
			// 파일 이름 중복방지 대책
			String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
//			String fileName = System.currentTimeMillis() + new SecureRandom().nextInt(10) + "_" + originalFileName;
//			System.out.println("fileName : " + fileName);
			
			// 파일을 저장할 경로에 대한 Path 객체 생성하고 해당 경로를 실제 위치에 생성
			// 단, 파일 디렉토리를 하나의 디렉토리에 관리하지 않도록 서브디렉토리 구분(날짜 활용)
			LocalDate today = LocalDate.now(); // 현재 시스템의 날짜 정보 생성(2025-08-06)
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // 날짜 포맷 변경
			String subDir = today.format(dtf);
//			System.out.println("subDir : " + subDir);
			
			// 기본 경로 + 상세 경로 + 날짜별 서브 디렉토리 결합하여 디렉토리 생성
			Path uploadDir = Paths.get(uploadBaseLocation, itemImgLocation, subDir).toAbsolutePath().normalize();
//			System.out.println("uploadDir : " + uploadDir);
			// 생성된 Path 객체에 해당하는 디렉토리가 실제 디렉토리로 존재하지 않을 경우 해당 디렉토리 생성
			if(!Files.exists(uploadDir)) {
				Files.createDirectories(uploadDir); // 하위 경로를 포함한 경로 상의 모든 디렉토리 생성
			}
			
			// 디렉토리와 파일명 결합하여 Path 객체 생성
			// => 기존 경로를 담고 있는 Path 객체의 resolve() 메서드 사용하여 기존 경로에 파일명 추가
			Path uploadPath = uploadDir.resolve(fileName);
//			System.out.println("uploadPath : " + uploadPath);
			
			// 임시 경로에 보관되어 있는 첨부파일 1개를 실제 업로드 경로로 이동
			mFile.transferTo(uploadPath);
			// -------------------------------------------------------------
			// ItemImg 엔티티 생성 및 파일 관련 정보 저장
			ItemImg itemImg = new ItemImg();
			itemImg.setItem(item);
			itemImg.setImgName(fileName);
			itemImg.setOriginalImgName(originalFileName);
			itemImg.setImgLocation(itemImgLocation + "/" + subDir);
			
			// 첫번째 이미지를 대표이미지로 지정
			if(index == 0) {
				itemImg.setRepImgYn("Y");
			} else {
				itemImg.setRepImgYn("N");
			}
			
			// 생성된 ItemImg 엔티티를 리스트에 추가
			itemImgList.add(itemImg);
			
			// 인덱스값 1 증가
			index++;
			
		} // 파일 객체 반복 끝
		
		// ItemImgRepository - saveAll() 메서드 호출하여 첨부파일 리스트를 한꺼번에 INSERT 요청
		itemImgRepository.saveAll(itemImgList);
	}
	
	// 상품이미지 정보 조회 요청
	@Transactional(readOnly = true)
	public ItemImgDTO getItemImg(Long itemImgId) {
		ItemImg itemImg = itemImgRepository.findById(itemImgId)
				.orElseThrow(() -> new EntityNotFoundException("해당 이미지가 존재하지 않습니다!"));
		return ItemImgDTO.fromEntity(itemImg);
	}

	// 첨부파일 다운로드를 위한 응답 데이터 생성 요청
	@Transactional(readOnly = true)
	public ResponseEntity<Resource> getDownloadResponse(Long itemImgId) {
		ItemImg itemImg = itemImgRepository.findById(itemImgId)
				.orElseThrow(() -> new EntityNotFoundException("해당 이미지가 존재하지 않습니다!"));
		
		// FileUtils 클래스의 createDownloadResponse() 메서드 호출하여 첨부파일 다운로드 응답 데이터 생성 요청
		// => 파라미터 : ItemImgDTO 객체  리턴타입 : ResponseEntity<Resource>
		ResponseEntity<Resource> responseEntity = fileUtils.createDownloadResponse(ItemImgDTO.fromEntity(itemImg));
		
		// 다운로드 횟수 증가시키려면 ItemImg 엔티티의 다운로드 횟수 필드값 변경(UPDATE) - 더디체킹에 의해 DB 데이터가 업데이트됨(생략)
		
		return responseEntity;
	}
	
	
	
}














