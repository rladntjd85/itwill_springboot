package com.itwillbs.db.commons.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileUtils {
	// 파일 업로드 처리에 사용할 경로를 properties 파일에서 가져오기
	// => 변수 선언부 앞(위)에 @Value("${프로퍼티속성명}") 형태로 선언
	@Value("${file.uploadBaseLocation}")
	private String uploadBaseLocation;
	
	@Value("${file.itemImgLocation}")
	private String itemImgLocation;

	public void test() {
		System.out.println("uploadBaseLocation : " + uploadBaseLocation);
		System.out.println("itemImgLocation : " + itemImgLocation);
	}
	
	
}
