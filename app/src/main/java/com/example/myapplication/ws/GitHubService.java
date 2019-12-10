package com.example.myapplication.ws;

import com.example.myapplication.pojo.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("users/contrastinc/repos")
   // @GET("users/{user}/repos")
    //Call<List<Repo>> listRepos(@Path("user") String user);
    Call<List<Repo>> listRepos();

}
