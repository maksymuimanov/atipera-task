package io.maksymuimanov.atiperatask;

import java.util.List;

public interface GithubService {
    List<GithubRepo> fetchRepos(String username);
}
