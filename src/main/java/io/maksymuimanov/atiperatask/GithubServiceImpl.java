package io.maksymuimanov.atiperatask;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubServiceImpl implements GithubService {
    public static final String USER_REPOS_API_URL = "https://api.github.com/users/{username}/repos";
    public static final String REPO_BRANCHES_API_URL = "https://api.github.com/repos/{username}/{repoName}/branches";
    private final RestClient restClient;

    @Override
    public List<GithubRepository> fetchRepos(String username) {
        List<GithubRepository> repositories = restClient.get()
                .uri(USER_REPOS_API_URL, username)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::isSameCodeAs, (_, _) -> {
                    throw new RepoNotFoundException();
                })
                .body(new ParameterizedTypeReference<>() {});
        if (repositories == null) return List.of();
        return repositories.stream()
                .filter(GithubRepository::notFork)
                .map(repo -> {
                    List<GithubBranch> branches = restClient.get()
                            .uri(REPO_BRANCHES_API_URL, username, repo.name())
                            .retrieve()
                            .body(new ParameterizedTypeReference<>() {});

                    return new GithubRepository(
                            repo.name(),
                            repo.fork(),
                            repo.ownerLogin(),
                            branches != null ? branches : List.of()
                    );
                })
                .toList();
    }
}
