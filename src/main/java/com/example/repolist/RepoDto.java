package com.example.repolist;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RepoDto(
        String name,
        boolean fork,
        Owner owner,
        @JsonProperty("branches_url") String branchesUrl

) {
        record Owner(
                String login
        ) {}
}
