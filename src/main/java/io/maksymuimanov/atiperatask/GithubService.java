package io.maksymuimanov.atiperatask;

import java.util.List;

public interface GithubService {
    List<GithubRepository> fetchRepos(String username);
}
