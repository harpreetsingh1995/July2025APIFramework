package com.qa.api.geminiapi.tests;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GoogleGeminiPromptTest extends BaseTest{
	
	
	@Test
	public void geminiPromptTest() {
		
		String requestBody = "{\n"
				+ "    \"contents\": [\n"
				+ "        {\n"
				+ "            \"parts\": [\n"
				+ "                {\n"
				+ "                    \"text\": \"what is xpath in selenium?\"\n"
				+ "                }\n"
				+ "            ]\n"
				+ "        }\n"
				+ "    ]\n"
				+ "}";
		
		ConfigManager.set("apikey", "AIzaSyCTJihMNUssEYSeikgA5cydA-dxo-FHOck");
		
		Response response = restClient.post(BASE_URL_GEMINI, GEMINI_ENDPOINT, requestBody, null, null, AuthType.API_KEY, ContentType.JSON);
		
	}
	

}
