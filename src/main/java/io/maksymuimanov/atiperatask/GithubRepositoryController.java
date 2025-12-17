package io.maksymuimanov.atiperatask;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/{version}/repos")
@RequiredArgsConstructor
public class GithubRepositoryController {
    private final GithubService githubService;

    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getGithubUserRepos(@PathVariable String username) {
        List<GithubRepository> repositories = githubService.fetchRepos(username);
        return ResponseEntity.ok(repositories);
    }
}
