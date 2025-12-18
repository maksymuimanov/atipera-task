package io.maksymuimanov.atiperatask;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock(@ConfigureWireMock(
		name = "github",
		baseUrlProperties = "github.base-url"
))
class GithubRepoControllerTests {
	@InjectWireMock("github")
	private WireMockServer wireMockServer;
	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Test
	void shouldReturnRepos() {
		this.stubExistingRepos("example", """
				[
				  {
				    "id": 100001,
				    "node_id": "MDEwOlJlcG9zaXRvcnkxMDAwMDE=",
				    "name": "service-api",
				    "full_name": "example/service-api",
				    "private": false,
				    "owner": {
				      "login": "example",
				      "id": 1,
				      "node_id": "MDQ6VXNlcjE="
				    },
				    "html_url": "/example/service-api",
				    "description": "Main backend service",
				    "fork": false
				  },
				  {
				    "id": 100002,
				    "node_id": "MDEwOlJlcG9zaXRvcnkxMDAwMDI=",
				    "name": "service-api-fork",
				    "full_name": "example/service-api-fork",
				    "private": false,
				    "owner": {
				      "login": "example",
				      "id": 1,
				      "node_id": "MDQ6VXNlcjE="
				    },
				    "html_url": "/example/service-api-fork",
				    "description": "Forked repository",
				    "fork": true
				  },
				  {
				    "id": 100005,
				    "node_id": "QDEwOlJ2cG9zaXRvcnkxMDAwMDE=",
				    "name": "service-api-fork2",
				    "full_name": "example/service-api-fork2",
				    "private": false,
				    "owner": {
				      "login": "example",
				      "id": 1,
				      "node_id": "MDQ6VXNlcjE="
				    },
				    "html_url": "/example/service-api-fork2",
				    "description": "Forked 2 repository",
				    "fork": true
				  },
				  {
				    "id": 100024,
				    "node_id": "AQEEwOEEEcG9zaXRvckxMDAwMDE=",
				    "name": "smth",
				    "full_name": "example/smth",
				    "private": false,
				    "owner": {
				      "login": "example",
				      "id": 3,
				      "node_id": "MDQ6VXNlcjE="
				    },
				    "html_url": "/example/smth",
				    "description": "Another repository",
				    "fork": false
				  }
				]
				""");
		this.stubRepoBranches("example/service-api", """
				[
				   {
				     "name": "main",
				     "commit": {
				       "sha": "ffffffffffffffffffffffffffffffffffffffff",
				       "url": "/repos/example/service-api-fork/commits/ffffffffffffffffffffffffffffffffffffffff"
				     },
				     "protected": false
				   },
				   {
					   "name": "develop",
					   "commit": {
						   "sha": "f1ffffffffffffffffffffffffffffffffffffff",
						   "url": "/repos/example/service-api-fork/commits/ffffffffffffffffffffffffffffffffffffffff"
					   },
					   "protected": false
				   }
				]
				""");
		this.stubRepoBranches("example/smth", """
				[
				   {
				     "name": "main",
				     "commit": {
				       "sha": "ffffffffffffffffffffffffffffffffffffffff",
				       "url": "/repos/example/smth/commits/ffffffffffffffffffffffffffffffffffffffff"
				     },
				     "protected": false
				   }
				]
				""");

		RestAssured.given()
				.when()
				.get("/api/v1.0/repos/example")
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("[0].name", Matchers.equalTo("service-api"))
				.body("[0].ownerLogin", Matchers.equalTo("example"))
				.body("[0].branches.size()", Matchers.equalTo(2));
	}

	private void stubExistingRepos(String username, String responseBody) {
		this.stubGetUrl("/users/%s/repos", new Object[]{username}, okJson(responseBody));
	}

	private void stubRepoBranches(String repoFullName, String responseBody) {
		this.stubGetUrl("/repos/%s/branches", new Object[]{repoFullName}, okJson(responseBody));
	}

	private void stubGetUrl(String urlPattern, Object[] params, ResponseDefinitionBuilder response) {
		this.wireMockServer.stubFor(get(urlEqualTo(urlPattern.formatted(params)))
				.willReturn(response));
	}
}
