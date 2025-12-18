package io.maksymuimanov.atiperatask;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "github")
public record GithubProperties(String baseUrl,
                               String reposUrl,
                               String branchesUrl) {
}
