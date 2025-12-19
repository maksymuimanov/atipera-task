package io.maksymuimanov.atiperatask;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubServiceImpl implements GithubService {
    private final RestClient restClient;
    private final GithubProperties githubProperties;

    @Override
    public List<GithubRepo> fetchRepos(String username) {
        List<GithubRepo> repositories = restClient.get()
                .uri(githubProperties.reposUrl(), username)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::isSameCodeAs, (_, _) -> {
                    throw new UserReposNotFoundException(username);
                })
                .body(new ParameterizedTypeReference<>() {});
        if (repositories == null) return List.of();
        return repositories.stream()
                .filter(GithubRepo::notFork)
                .map(repo -> {
                    List<GithubBranch> branches = restClient.get()
                            .uri(githubProperties.branchesUrl(), repo.ownerLogin(), repo.name())
                            .retrieve()
                            .body(new ParameterizedTypeReference<>() {});
                    return new GithubRepo(
                            repo.name(),
                            repo.ownerLogin(),
                            branches != null ? branches : List.of(),
                            repo.fork()
                    );
                })
                .toList();
    }
}
