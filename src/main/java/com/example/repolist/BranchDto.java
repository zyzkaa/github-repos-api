package com.example.repolist;

public record BranchDto(
        String name,
        Commit commit
) {
    record Commit(
            String sha
    ) {}
}
