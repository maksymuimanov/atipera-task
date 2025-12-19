package io.maksymuimanov.atiperatask;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"name", "ownerLogin", "branches"})
public record GithubRepo(String name,
                         String ownerLogin,
                         List<GithubBranch> branches,
                         @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) Boolean fork) {
    private static final String OWNER_LOGIN_KEY = "login";

    @JsonCreator
    public GithubRepo(@JsonProperty("name") String name,
                      @JsonProperty("owner") Map<String, Object> owner,
                      @JsonProperty("fork") Boolean fork) {
        this(name, owner.get(OWNER_LOGIN_KEY).toString(), new ArrayList<>(), fork);
    }

    public Boolean notFork() {
        return Boolean.FALSE.equals(fork);
    }
}
