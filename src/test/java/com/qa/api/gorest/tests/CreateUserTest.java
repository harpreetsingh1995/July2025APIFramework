package com.qa.api.gorest.tests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.CSVUtil;
import com.qa.api.utils.ExcelUtil;
import com.qa.api.utils.StringUtils;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


@Epic("EP 100 -- Create User feature for Go Rest API")
@Feature("FF 50 - create user with POST calls")
@Story("US 200 - Create user with json, pojo and file")
public class CreateUserTest extends BaseTest{
	
	
	@Test
	public void createUserTest() {
		
		String userJson = "{\n"
				+ "    \"name\": \"Naveen Automation Labs CPA\",\n"
				+ "    \"email\": \"naveen123@altenwerth.test\",\n"
				+ "    \"gender\": \"male\",\n"
				+ "    \"status\": \"active\"\n"
				+ "}";
		
		
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, userJson, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 201);
	}
	
	@BeforeClass
	public void goRestTokenSetup(){
		ConfigManager.set("bearertoken", "d0bf1714ac04c10dd2982e009d2dffe694a8e0b53af518cb7370e41e046a72f6");
	}
	
	
	
	@DataProvider
	public Object[][] getUserData() {
		return new Object[][] {
			{"karim", "male", "active" },
			{"Jyothi", "female", "inactive"},
			{"sandehsh", "male", "active"}
		};
	}
	
	@DataProvider
	public Object[][] getUserSheetData() {
		return ExcelUtil.readData("createuser");
	}
	
	@DataProvider
	public Object[][] getUserCSVData() {
		return CSVUtil.csvData("testdata");
	}
		
	@Owner("Naveen Khunteta")
	@Description("create a user using POST call with POJO class, get the data from CSV..")
	@Severity(SeverityLevel.CRITICAL)
	@Test(dataProvider = "getUserCSVData")
	public void createUserWithPOJOTest(String name, String gender, String status) {
		
		User user = User.builder()
						.name(name)
								.email(StringUtils.getRandomEmailId())
										.status(status)
												.gender(gender)
														.build();
		
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		ChainTestListener.log(response.getBody().asString());
		ChainTestListener.log("status code: "+ response.statusCode());
		ChainTestListener.log(response.getHeaders().get("Content-Type").getValue());
		Assert.assertEquals(response.statusCode(), 201);
	}
	
	
	@Test
	public void createUserWithJSONFileTest() {
		
		File userFile = new File("src/test/resources/jsons/user.json");
		
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, userFile, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 201);
	}
	
	
	
	

}
