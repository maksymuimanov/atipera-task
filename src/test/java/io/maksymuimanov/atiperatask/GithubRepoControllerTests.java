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
import static io.maksymuimanov.atiperatask.TestConstants.*;

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
	void shouldReturnExistingRepos() {
		this.stubRepos(EXAMPLE_USERNAME, FOUR_REPOS_WITH_FORKS_RESPONSE);
		this.stubBranches(EXAMPLE_USERNAME, EXAMPLE_REPO_1, TWO_BRANCHES_RESPONSE);
		this.stubBranches(EXAMPLE_USERNAME, EXAMPLE_REPO_2, ONE_BRANCH_RESPONSE);

		RestAssured.given()
				.when()
				.get(API_ENDPOINT)
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("size()", Matchers.equalTo(2))
				.body("[0]", Matchers.aMapWithSize(3))
				.body("[0].name", Matchers.equalTo(EXAMPLE_REPO_1))
				.body("[0].ownerLogin", Matchers.equalTo(EXAMPLE_USERNAME))
				.body("[0].branches.size()", Matchers.equalTo(2))
				.body("[1]", Matchers.aMapWithSize(3))
				.body("[1].name", Matchers.equalTo(EXAMPLE_REPO_2))
				.body("[1].ownerLogin", Matchers.equalTo(EXAMPLE_USERNAME))
				.body("[1].branches.size()", Matchers.equalTo(1));
	}

	@Test
	void shouldReturnNotFound_whenUserDoesNotExist() {
		this.stubNotFoundRepos(EXAMPLE_USERNAME);

		RestAssured.given()
				.when()
				.get(API_ENDPOINT)
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value())
				.body("$", Matchers.aMapWithSize(2))
				.body("status", Matchers.equalTo(HttpStatus.NOT_FOUND.value()))
				.body("message", Matchers.equalTo(UserReposNotFoundException.NOT_FOUND_REASON.formatted(EXAMPLE_USERNAME)));
	}

	private void stubRepos(String username, String responseBody) {
		this.stubGetUrl(GITHUB_USER_REPOS_URL.formatted(username), okJson(responseBody));
	}

	private void stubBranches(String owner, String repoName, String responseBody) {
		this.stubGetUrl(GITHUB_REPO_BRANCHES_URL.formatted(owner, repoName), okJson(responseBody));
	}

	private void stubNotFoundRepos(String username) {
		this.stubGetNotFoundUrl(GITHUB_USER_REPOS_URL.formatted(username));
	}

	private void stubGetNotFoundUrl(String urlPattern) {
		this.wireMockServer.stubFor(get(urlEqualTo(urlPattern))
				.willReturn(notFound()));
	}

	private void stubGetUrl(String urlPattern, ResponseDefinitionBuilder response) {
		this.wireMockServer.stubFor(get(urlEqualTo(urlPattern))
				.willReturn(response));
	}
}
