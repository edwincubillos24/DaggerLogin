
package com.edwinacubillos.daggerlogin.http.twitch;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Twitch {

    @SerializedName("data")
    @Expose
    private List<Game> data = null;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public List<Game> getGame() {
        return data;
    }

    public void setData(List<Game> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
