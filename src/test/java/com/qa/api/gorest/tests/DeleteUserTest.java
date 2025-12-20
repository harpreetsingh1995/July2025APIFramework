package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteUserTest extends BaseTest{
	
	@Test
	public void deleteUserTest() {
		
		//1. create a user:
		User user = User.builder()
				.name("Peter")
						.email(StringUtils.getRandomEmailId())
								.status("active")
										.gender("male")
												.build();
		
		Response responsePost = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		int userId = responsePost.jsonPath().getInt("id");
		System.out.println("new user id : "+ userId);
		
		//2. get the same user using the userID:
		Response responseGet = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGet.jsonPath().getInt("id"), userId);
		
		//3. delete the same user using the UserID:
		Response responseDelete = restClient.delete(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseDelete.getStatusCode(), 204);
		
		//4. get the same user using the userID:
		responseGet = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGet.getStatusCode(), 404);
		Assert.assertEquals(responseGet.jsonPath().getString("message"), "Resource not found");


	}
	

}
