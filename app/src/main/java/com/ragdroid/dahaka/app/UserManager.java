package com.ragdroid.dahaka.app;


import com.ragdroid.dahaka.api.entity.Pokemon;
import com.ragdroid.dahaka.user.PokemonService;
import com.ragdroid.dahaka.user.UserComponent;

import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.functions.Consumer;

/**
 * Created by garimajain on 13/08/17.
 */
@Singleton
public class UserManager {

    private final PokemonService service;
    private final UserComponent.Builder userComponentBuilder;

    HashMap<String, Pokemon> pokemonCache = new HashMap<>();
    private UserComponent userComponent;

    @Inject
    public UserManager(PokemonService service, UserComponent.Builder builder) {
        this.service = service;
        this.userComponentBuilder = builder;
    }

    public Flowable<Pokemon> loginWithUserName(String userName) {
        return getPokemonFromCache(userName)
                .concatWith(service.getPokemon(userName).toMaybe())
                .take(1)
                .doOnNext(new Consumer<Pokemon>() {
                    @Override
                    public void accept(Pokemon pokemon) throws Exception {
                        createUserSession(pokemon);
                    }
                });
    }

    private void createUserSession(Pokemon pokemon) {
        userComponent = userComponentBuilder
                .pokeMon(pokemon)
                .build();
    }


    private Maybe<Pokemon> getPokemonFromCache(String userName) {
        if (pokemonCache.containsKey(userName)) {
            return Maybe.just(pokemonCache.get(userName));
        } else {
            return Maybe.empty();
        }
    }

    public boolean isLoggedIn() {
        return userComponent != null;
    }

    public void logOut() {
        userComponent = null;
    }


}
