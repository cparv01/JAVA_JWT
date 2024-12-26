package com.firstProject.springbootCrud.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString

public class JWTResponse {

	private String token;
	
	private String username;
}
