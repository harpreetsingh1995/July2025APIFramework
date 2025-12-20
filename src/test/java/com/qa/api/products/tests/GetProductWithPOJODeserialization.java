package com.qa.api.products.tests;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Product;
import com.qa.api.utils.ObjectMapperUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetProductWithPOJODeserialization extends BaseTest{
	
	
	@Test
	public void getAllProductsTest() {
		Response response = 
				restClient.get(BASE_URL_FAKE_PRODUCT, FAKE_PRODUCTS_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);
		
		Product[] Allproducts =  ObjectMapperUtil.deserialize(response, Product[].class);
		
		for(Product product : Allproducts) {
			System.out.println("id : " + product.getId());
			System.out.println("id : " + product.getTitle());
			System.out.println("id : " + product.getPrice());
			System.out.println("id : " + product.getDescription());
			System.out.println("id : " + product.getImage());
			System.out.println("id : " + product.getCategory());
			
			System.out.println("rate: "+ product.getRating().getRate());
			System.out.println("count: "+ product.getRating().getCount());
			
			System.out.println("-----------");

		}
		
		
		
		
	}
	
	
	
	
	
	
	
	

}
