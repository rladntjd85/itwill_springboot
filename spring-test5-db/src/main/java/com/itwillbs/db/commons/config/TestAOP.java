package com.itwillbs.db.commons.config;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.itwillbs.db.commons.enums.MemberRoleType;
import com.itwillbs.db.commons.repository.CommonCodeRepository;
import com.itwillbs.db.items.dto.ItemDTO;
import com.itwillbs.db.members.entity.MemberRole;

import lombok.extern.log4j.Log4j2;

@Aspect
@Component
@Log4j2
public class TestAOP {
	private CommonCodeRepository commonCodeRepository;
	
//	@Before("execution(* com.itwillbs.db..service.*Service.regist*(..))")
	@Before("execution(* com.itwillbs.db..service.*Service.*(com.itwillbs.db.commons.dto.LotDTO))")
	public void aopTest(JoinPoint joinPoint) {
		log.info("★★★★★★★★★★★★★★★ 메서드 정보 : " + joinPoint.getSignature().toShortString());
		log.info("★★★★★★★★★★★★★★★ 파라미터 정보 : " + Arrays.toString(joinPoint.getArgs()));
		
		for(Object obj : joinPoint.getArgs()) {
			if(obj instanceof ItemDTO itemDTO) {
				System.out.println("호출!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				LOT생성메서드(itemDTO, MemberRoleType.ADMIN);
			}
		}
	}
	
	public void LOT생성메서드(ItemDTO itemDTO, MemberRoleType memberRoleType) {
		log.info("★★★★★★★★★★★★★★★ ItemDTO 정보 : " + itemDTO);
		log.info("★★★★★★★★★★★★★★★ MemberRoleType 정보 : " + memberRoleType.getCode() + ", " + memberRoleType.name());
		
		String tableName = "";
		
//		switch (memberRoleType) {
//		case ADMIN:
//		case ADMIN1:
//		case ADMIN2:
//			tableName = "ADMIN"
//			break;
//		case USER:
//		case USER2:
//			tableName = "2";
//		default:
//			throw new IllegalArgumentException("Unexpected value: " + memberRoleType);
//		}
		
	}
	
}














