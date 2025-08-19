package com.itwillbs.db.commons.enums;

public enum MemberRoleType {
	USER("USER"),
	ADMIN("ADMIN");
	
	private final String code;

	private MemberRoleType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
