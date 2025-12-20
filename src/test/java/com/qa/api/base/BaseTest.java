package com.qa.api.base;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.client.RestClient;
import com.qa.api.manager.ConfigManager;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

//@Listeners(ChainTestListener.class)
public class BaseTest {
	
protected static String BASE_URL_GOREST ;
	
	//**************API BASE URLs ***************//
	//protected static final String BASE_URL_GOREST = "https://gorest.co.in";protected static final String BASE_URL_FAKE_PRODUCT = "https://fakestoreapi.com";
protected static final String BASE_URL_FAKE_PRODUCT = "https://fakestoreapi.com";	
protected static final String BASE_URL_HEROKU_BASIC_AUTH = "https://the-internet.herokuapp.com";
	protected static final String BASE_URL_SPOTIFY_TOKEN = "https://accounts.spotify.com";
	protected static final String BASE_URL_SPOTIFY_ALBUMS = "https://api.spotify.com";

	protected static final String BASE_URL_GEMINI = "https://generativelanguage.googleapis.com";

	
	//**************API END POINTS **********//
	protected static final String GOREST_USERS_ENDPOINT = "/public/v2/users";
	protected static final String FAKE_PRODUCTS_ENDPOINT = "/products";
	protected static final String HEROKU_BASIC_AUTH_ENDPOINT = "/basic_auth";
	protected static final String SPOTIFY_TOKEN_ENDPOINT = "/api/token";
	protected static final String SPOTIFY_ALBUMS_ENDPOINT = "/v1/albums/";
	protected static final String GEMINI_ENDPOINT = "/v1beta/models/gemini-2.0-flash:generateContent";

		
	
	
	protected RestClient restClient;
	
	@BeforeTest
	public void setup() {
		restClient = new RestClient();
	}
	
	@BeforeSuite
	public void initSetup() {
		RestAssured.filters(new AllureRestAssured());
		 BASE_URL_GOREST = ConfigManager.get("baseurl.gorest");
	}
	
	
	
	
	

}
