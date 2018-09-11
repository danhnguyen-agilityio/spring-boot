package com.agility.handlerexception;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HandlerExceptionApplicationTests {

	@Test
	public void contextLoads() throws Exception {
		HttpUriRequest request = new HttpGet( "http://localhost:8080/customer/Jack" );

		// When
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

		// Then
		assertThat(
			httpResponse.getStatusLine().getStatusCode(),
			equalTo(HttpStatus.SC_OK));
	}

}
