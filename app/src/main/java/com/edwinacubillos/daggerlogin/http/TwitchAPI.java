package com.edwinacubillos.daggerlogin.http;

import com.edwinacubillos.daggerlogin.http.streams.Streams;
import com.edwinacubillos.daggerlogin.http.twitch.Twitch;

import io.reactivex.rxjava3.core.Observable;
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

    @GET("games/top")
    Observable<Twitch> getTopGamesObservable(@Header ("Client-Id") String clientId);

    @GET("streams")
    Observable<Streams> getStreamsObservable(@Header ("Client-Id") String clientId);

    @GET("games")
    Observable<Twitch> getGamesObservable(
            @Header("Client-Id") String clientId,
            @Query("id") String id);
}

    /* sin ID
    @GET("games/top")
    Call <Twitch> getTopGames();*/


