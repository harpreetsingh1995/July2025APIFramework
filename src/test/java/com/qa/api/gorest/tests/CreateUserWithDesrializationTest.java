package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.ObjectMapperUtil;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserWithDesrializationTest extends BaseTest {
	
	@BeforeClass
	public void goRestTokenSetup(){
		ConfigManager.set("bearertoken", "d0bf1714ac04c10dd2982e009d2dffe694a8e0b53af518cb7370e41e046a72f6");
	}
	
	
	@Test
	public void getAUserTest() {
		
		//POST:
		User user = new User(null, "Tom", StringUtils.getRandomEmailId(), "male", "active");

		Response response = 
				restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		
		Assert.assertEquals(response.jsonPath().getString("name"), "Tom");
		
		int userId = response.jsonPath().getInt("id");
		System.out.println("user id : "+ userId);
		
		//GET:
		Response getResponse = 
				restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.ANY);
		
		//response json ----> POJO:
		User userResponse = ObjectMapperUtil.deserialize(getResponse, User.class);
		
		//Assert.assertEquals(userResponse.getId(), userId);
		Assert.assertEquals(userResponse.getName(), user.getName());
		Assert.assertEquals(userResponse.getEmail(), user.getEmail());
		Assert.assertEquals(userResponse.getStatus(), user.getStatus());
		Assert.assertEquals(userResponse.getGender(), user.getGender());

		
	}
	
	
	
	
	
	

}
