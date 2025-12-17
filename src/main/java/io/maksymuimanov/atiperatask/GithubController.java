package io.maksymuimanov.atiperatask;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/{version}/repos")
@RequiredArgsConstructor
public class GithubController {
    private final GithubService githubService;

    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getGithubUserRepos(@PathVariable String username) {
        return ResponseEntity.ok(githubService.fetchRepos(username));
    }
}
