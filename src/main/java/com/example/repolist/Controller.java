package com.example.repolist;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    RepoListService repoListService;

    public Controller(RepoListService repoListService){
        this.repoListService = repoListService;
    }

    @GetMapping("/{username}")
    public List<ResponseDto> fetchRepos(@PathVariable String username){
        return repoListService.fetchRepos(username);
    }
}
