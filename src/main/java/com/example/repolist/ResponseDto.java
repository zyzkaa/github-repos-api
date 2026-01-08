package com.example.repolist;

import java.util.List;

public record ResponseDto(
        String repoName,
        String ownerLogin,
        List<Branch> branches
) {
    record Branch(
            String name,
            String lastSha
    ) {}
}
