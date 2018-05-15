package com.assignment.image.MagicImages;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MagicImagesApplicationTests {

	@Test
	public void contextLoads() {
	}

	
	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
	}

	@Test
	public void whenRequestGet_thenOK() {
		Response res = when().request("/api/v1/photos");
		int code = res.getStatusCode();
		ResponseBody resBody = res.body();
		when().request("GET", "api/v1/photos").then().statusCode(200);
	}

	@Test
	public void whenRequestGetID_thenOK() {
		given().pathParam("photoId", 1).when().get("/api/v1/photos/{photoId}").then().statusCode(200);
	}

	@Test
	public void whenRequestGetPagNation_thenOK() {
		given().queryParam("offset", "2").queryParam("limit", "8").when().get("/api/v1/photos/pagnation").then()
				.statusCode(200);
	}

	@Test
	public void whenMeasureResponseTime_thenOK() {
		Response response = RestAssured.get("api/v1/photos");
		long timeInMS = response.time();
		long timeInS = response.timeIn(TimeUnit.SECONDS);

		assertEquals(timeInS, timeInMS / 1000);
	}

	@Test
	public void whenValidateResponseTime_thenSuccess() {
		when().get("/api/v1/photos").then().time(lessThan(5000L));
	}

	@Test
	public void whenValidateResponseTimeInSeconds_thenSuccess() {
		when().get("/api/v1/photos").then().time(lessThan(5L), TimeUnit.SECONDS);
	}
	
}
