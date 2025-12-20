package com.qa.api.products.tests;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.JsonPathValidatorUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetProductsWithJsonPathTest extends BaseTest{
	
	
	@Test
	public void getAllProductsTest() {
		Response response = 
				restClient.get(BASE_URL_FAKE_PRODUCT, FAKE_PRODUCTS_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
		
		
		List<Number> pricesList = JsonPathValidatorUtil.readList(response, "$[?(@.price > 50)].price");
		System.out.println(pricesList);
		
		List<Number> idsList = JsonPathValidatorUtil.readList(response, "$[?(@.price > 50)].id");
		System.out.println(idsList);
		
		List<Number> rateList = JsonPathValidatorUtil.readList(response, "$[?(@.price > 50)].rating.rate");
		System.out.println(rateList);
		
		List<Number> countList = JsonPathValidatorUtil.readList(response, "$[?(@.price > 50)].rating.count");
		System.out.println(countList);
		
		List<Map<String, Object>> idTitleList = JsonPathValidatorUtil.readListOfMaps(response, "$[*].['id', 'title']");
		System.out.println(idTitleList);
		
		List<Map<String, Object>> idTitleCatList = JsonPathValidatorUtil.readListOfMaps(response, "$[*].['id', 'title', 'category']");
		System.out.println(idTitleCatList);
		
		List<Number> jewleryIds = JsonPathValidatorUtil.readList(response, "$[?(@.category === 'jewelery')].id");
		System.out.println(jewleryIds);
		
		
		List<Map<String, Object>> jewleryIdTitle = JsonPathValidatorUtil.readList(response, "$[?(@.category === 'jewelery')].['id', 'title']");
		System.out.println(jewleryIdTitle);
		
		
		Double minPrice = JsonPathValidatorUtil.read(response, "min($[*].price)");
		System.out.println(minPrice);
		
		Double maxPrice = JsonPathValidatorUtil.read(response, "max($[*].price)");
		System.out.println(maxPrice);
		
		
	}

}
