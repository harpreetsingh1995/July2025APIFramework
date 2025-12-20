package com.qa.api.schema.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.SchemaValidator;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GoRestAPISchemaTest extends BaseTest{
	
	
	@BeforeClass
	public void goRestTokenSetup(){
		ConfigManager.set("bearertoken", "d0bf1714ac04c10dd2982e009d2dffe694a8e0b53af518cb7370e41e046a72f6");
	}
	
	@Test
	public void getUsersAPISchemaTest() {
		Response response = 
				restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.ANY);
				
		Assert.assertTrue(SchemaValidator.validateSchema(response, "getusersschema.json"));
		
	}
	
	
	@Test
	public void createUserAPISchemaTest() {
		
		User user = User.builder()
				.name("Peter")
						.email(StringUtils.getRandomEmailId())
								.status("active")
										.gender("male")
												.build();
		
		Response response = 
				restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		
		Assert.assertTrue(SchemaValidator.validateSchema(response, "createuserschema.json"));
		
	}
	
	
	
	

}
