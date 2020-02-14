package com.edwinacubillos.daggerlogin.login;

public class LoginActivityModel implements LoginActivityMVP.Model{

    private LoginRepository repository;

    public LoginActivityModel(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createUser(String firstName, String lastName) {
        //logica de business comprobar correo, etc
        repository.saveUser(new User(firstName, lastName));
    }

    @Override
    public User getUser() {
        //logica de business
        return repository.getUser();
    }
}
