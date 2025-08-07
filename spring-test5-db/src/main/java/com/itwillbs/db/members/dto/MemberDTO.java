package com.itwillbs.db.members.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import com.itwillbs.db.items.dto.ItemImgDTO;
import com.itwillbs.db.items.entity.ItemImg;
import com.itwillbs.db.members.entity.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
	private Long id;

	@NotEmpty(message = "이메일은 필수 입력값입니다!")
	@Email(message = "이메일 형식으로 입력해주세요!")
	private String email;
	
	@NotEmpty(message = "패스워드는 필수 입력값입니다!")
	@Length(min = 4, max = 16, message = "패스워드는 4 ~ 16자리 필수!")
	private String passwd;
	
	@NotBlank(message = "이름은 필수 입력값입니다!")
	private String name;
	
	@NotBlank(message = "주소는 필수 입력값입니다!")
	private String address;
	
	private LocalDate regDate;
	
	// ------------------------------
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Member toEntity() {
		return modelMapper.map(this, Member.class);
	}
	
	public static MemberDTO fromEntity(Member member) {
		return modelMapper.map(member, MemberDTO.class);
	}
}















