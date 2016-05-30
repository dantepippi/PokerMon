package com.dstudios.pokermon;

/**
 * Created by dante on 29/05/2016.
 */

public class Tournament {
    private String owner_uid;
    private String buy_in;
    private String rebuy;
    private String add_on;
    private String last_rebuy_level;
    private Integer starting_chips;
    private Integer blind_interval;


    public String getOwner_uid() {
        return owner_uid;
    }

    public void setOwner_uid(String owner_uid) {
        this.owner_uid = owner_uid;
    }

    public String getBuy_in() {
        return buy_in;
    }

    public void setBuy_in(String buy_in) {
        this.buy_in = buy_in;
    }

    public String getRebuy() {
        return rebuy;
    }

    public void setRebuy(String rebuy) {
        this.rebuy = rebuy;
    }

    public String getAdd_on() {
        return add_on;
    }

    public void setAdd_on(String add_on) {
        this.add_on = add_on;
    }

    public String getLast_rebuy_level() {
        return last_rebuy_level;
    }

    public void setLast_rebuy_level(String last_rebuy_level) {
        this.last_rebuy_level = last_rebuy_level;
    }

    public Integer getStarting_chips() {
        return starting_chips;
    }

    public void setStarting_chips(Integer starting_chips) {
        this.starting_chips = starting_chips;
    }

    public Integer getBlind_interval() {
        return blind_interval;
    }

    public void setBlind_interval(Integer blind_interval) {
        this.blind_interval = blind_interval;
    }
}
