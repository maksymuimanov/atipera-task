package io.maksymuimanov.atiperatask;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/{version}/repos")
@RequiredArgsConstructor
public class GithubRepoController {
    private final GithubService githubService;

    @GetMapping(value = "/{username}")
    public List<GithubRepo> getGithubUserRepos(@PathVariable String username) {
        return githubService.fetchRepos(username);
    }
}
