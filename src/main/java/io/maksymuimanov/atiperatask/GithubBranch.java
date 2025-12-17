package io.maksymuimanov.atiperatask;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record GithubBranch(String name, String lastCommitSha) {
    @JsonCreator
    public GithubBranch(@JsonProperty("name") String name,
                        @JsonProperty("commit") Map<String, Object> commit) {
        this(name, commit.get("sha").toString());
    }
}
