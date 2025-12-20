package com.qa.api.client;

import static io.restassured.RestAssured.expect;

import java.io.File;
import java.util.Base64;
import java.util.Map;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.APIException;
import com.qa.api.manager.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.config.Config;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.*;

public class RestClient {
	
	//define Response Specs:
	private ResponseSpecification responseSpec200 = expect().statusCode(200);
	private ResponseSpecification responseSpec200or201 = expect().statusCode(anyOf(equalTo(200), equalTo(201)));
	private ResponseSpecification responseSpec200or404 = expect().statusCode(anyOf(equalTo(200), equalTo(404)));

	private ResponseSpecification responseSpec204= expect().statusCode(204);


	
	
	private RequestSpecification setupRequest(String baseUrl, AuthType authType, ContentType contentType) {
		
		RequestSpecification request =  RestAssured.given().log().all()
											.baseUri(baseUrl)
											.contentType(contentType)
											.accept(contentType);
		
		switch (authType) {
		case BEARER_TOKEN:
			request.header("Authorization", "Bearer "+ ConfigManager.get("bearertoken"));
			break;
		case BASIC_AUTH:
			request.header("Authorization", "Basic " + generateBasicAuthToken() );
			break;
		case API_KEY:
			request.header("X-goog-api-key", ConfigManager.get("apikey"));
			break;
		case NO_AUTH:
			System.out.println("Auth is not required....");
			break;
		default:
			System.out.println("this auth type is not supported...please pass the right authtype...");
			throw new APIException("=====INVALID AUTHTYPE=====");
		}
		
		return request;
		
	}
	
	
	private String generateBasicAuthToken() {
		//admin:admin ==> YWRtaW46YWRtaW4=
		String credentails = ConfigManager.get("basicauthusername").trim() + ":" + ConfigManager.get("basicauthpassword").trim();
		return Base64.getEncoder().encodeToString(credentails.getBytes());
	}
	
	
	
	
	private void applyParams(RequestSpecification request, Map<String, String> queryParams, Map<String, String> pathParams) {
		if(queryParams!=null) {
			request.queryParams(queryParams);
		}
		if(pathParams!=null) {
			request.pathParams(pathParams);
		}
		
	}
	
	
	
	/**
	 * This method is used to call GET APIs..
	 * @param baseUrl
	 * @param endPoint
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return it returns the GET API call response..
	 */
	public Response get(String baseUrl, String endPoint, Map<String, String> queryParams, Map<String, String> pathParams,
									AuthType authType, ContentType contentType) {
		
		
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		
		Response response = request.urlEncodingEnabled(false).get(endPoint).then().spec(responseSpec200or404).extract().response();
		response.prettyPrint();
		return response;
	}
	
	
	/**
	 * this method is used to create a resource using post call method - accepts any type of request body 
	 * except the file type
	 * @param <T>
	 * @param baseUrl
	 * @param endPoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return
	 */
	public <T> Response post(String baseUrl, String endPoint, T body, 
					Map<String, String> queryParams, Map<String, String> pathParams,
					AuthType authType, ContentType contentType) {
		
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		ChainTestListener.log("Full API url: "+ baseUrl+endPoint);
		ChainTestListener.log("Auth Type: "+ authType);
		
		Response response = request.urlEncodingEnabled(false).body(body).post(endPoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();
		return response;
		
	}
	
	
	/**
	 * this method is used to create a resource using post call method - accepts the file type body 
	 * @param baseUrl
	 * @param endPoint
	 * @param file
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return
	 */
	public Response post(String baseUrl, String endPoint, File file, 
			Map<String, String> queryParams, Map<String, String> pathParams,
			AuthType authType, ContentType contentType) {

				RequestSpecification request = setupRequest(baseUrl, authType, contentType);
				applyParams(request, queryParams, pathParams);
				
				Response response = request.urlEncodingEnabled(false).body(file).post(endPoint).then().spec(responseSpec200or201).extract().response();
				response.prettyPrint();
				return response;

	}
	
	/**
	 * this method is used to get the access token for Oauth2.0 based apis.
	 * @param baseUrl
	 * @param endPoint
	 * @param clientId
	 * @param clientSecret
	 * @param grantType
	 * @param contentType
	 * @return this will return a response with access token
	 */
	public Response post(String baseUrl, String endPoint, 
					String clientId, String clientSecret, String grantType, ContentType contentType) {
		
		Response response = RestAssured.given()
						.contentType(contentType)
						.formParam("grant_type", grantType)
						.formParam("client_id", clientId)
						.formParam("client_secret", clientSecret)
				   .when().urlEncodingEnabled(false)
				   		.post(baseUrl+endPoint);
		response.prettyPrint();
		return response;
					
	}
	
	
	
	/**
	 * this method is used to update a resource using put call method
	 * @param <T>
	 * @param baseUrl
	 * @param endPoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return
	 */
	public <T>Response put(String baseUrl, String endPoint, T body, 
			Map<String, String> queryParams, Map<String, String> pathParams,
			AuthType authType, ContentType contentType) {

				RequestSpecification request = setupRequest(baseUrl, authType, contentType);
				applyParams(request, queryParams, pathParams);
				
				Response response = request.urlEncodingEnabled(false).body(body).put(endPoint).then().spec(responseSpec200or201).extract().response();
				response.prettyPrint();
				return response;

	}
	
	
	
	/**
	 * this method is used to update a resource using put call method
	 * @param <T>
	 * @param baseUrl
	 * @param endPoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return
	 */
	public <T>Response patch(String baseUrl, String endPoint, T body, 
			Map<String, String> queryParams, Map<String, String> pathParams,
			AuthType authType, ContentType contentType) {

				RequestSpecification request = setupRequest(baseUrl, authType, contentType);
				applyParams(request, queryParams, pathParams);
				
				Response response = request.urlEncodingEnabled(false).body(body).patch(endPoint).then().spec(responseSpec200or201).extract().response();
				response.prettyPrint();
				return response;

	}
	
	
	
	/**
	 * this method is used to delete a resource using delete call method
	 * @param <T>
	 * @param baseUrl
	 * @param endPoint
	 * @param authType
	 * @param contentType
	 * @return
	 */
	public <T>Response delete(String baseUrl, String endPoint, AuthType authType, ContentType contentType) {

				RequestSpecification request = setupRequest(baseUrl, authType, contentType);
				
				Response response = request.urlEncodingEnabled(false).delete(endPoint).then().spec(responseSpec204).extract().response();
				response.prettyPrint();
				return response;

	}

	
	
	
	
	
	
	
	
	
	
}
