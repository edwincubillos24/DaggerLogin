package com.edwinacubillos.daggerlogin.login;

public class MemoryRepository implements LoginRepository {

    private User user;

    @Override
    public void saveUser(User user) {
        if (user == null){
            user = getUser();
        }
        this.user = user; //aqui se escribira en sqlite, firebase, etc
    }

    @Override
    public User getUser() {
        if (user == null){
            user = new User ("Antonio","Banderas");
            user.setId(0);
            return user;
        } else {
            return user;
        }
    }
}
