package com.example.repolist;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class RepoListService {
    private final RestClient restClient;

    @Value("${github.api.url}")
    private String githubApiUrl;

    public RepoListService(RestClient restClient){
        this.restClient = restClient;
    }

    public List<ResponseDto> fetchRepos(String username){
        var repos = restClient.get()
                .uri( githubApiUrl + "/users/" + username + "/repos")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    if (response.getStatusCode() == HttpStatus.NOT_FOUND) throw new HttpClientErrorException(HttpStatusCode.valueOf(404), "User not found");
                })
                .toEntity(new ParameterizedTypeReference<List<RepoDto>>() {})
                .getBody();

        if (repos == null || repos.isEmpty()) return new ArrayList<>();

        List<CompletableFuture<ResponseDto>> futures = repos
                .stream()
                .filter(r -> !r.fork())
                .map(r -> CompletableFuture.supplyAsync(() -> {
                        var branches = restClient
                                .get()
                                .uri(r.branchesUrl().replace("{/branch}", ""))
                                .retrieve()
                                .toEntity(new ParameterizedTypeReference<List<BranchDto>>() {})
                                .getBody()
                                .stream()
                                .map( b ->
                                        new ResponseDto.Branch(
                                                b.name(),
                                                b.commit().sha()
                                        )
                                )
                                .toList();

                        return new ResponseDto(
                                r.name(),
                                r.owner().login(),
                                branches
                        );
                    }))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }
}

