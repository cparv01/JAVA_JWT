package com.firstProject.springbootCrud.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class JWTRequest {
	
	private String email;
	
	private String password;
}
