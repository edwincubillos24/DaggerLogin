package com.edwinacubillos.daggerlogin.http;

import com.edwinacubillos.daggerlogin.http.twitch.Twitch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface TwitchAPI {

    @GET("games/top")
    Call<Twitch> getTopGames(@Header("Client-Id") String clientId);

    @GET("games")
    Call<Twitch> getGames(
            @Header("Client-Id") String clientId,
            @Query("name") String name);

}

    /* sin ID
    @GET("games/top")
    Call <Twitch> getTopGames();*/


