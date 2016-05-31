package com.dstudios.pokermon;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dante on 29/05/2016.
 */

public class Tournament {

    private Map<String, String> timestamp_started;
    private Map<String, String> timestamp_finished;
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

    public Map<String,String> getTimestamp_created() {
        return timestamp_created;
    }

    public void setTimestamp_created(Map<String, String> timestamp_created) {
        this.timestamp_created = timestamp_created;
    }

    private Map<String, String> timestamp_created;

    public Map<String, String> getTimestamp_started() {
        return timestamp_started;
    }

    public void setTimestamp_started(Map<String, String> timestamp_started) {
        this.timestamp_started = timestamp_started;
    }

        public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("owner_uid", owner_uid);
        result.put("buyin", buy_in);
        result.put("rebuy", rebuy);
        result.put("addon", add_on);
        result.put("starting_chips", starting_chips);
        result.put("blind_interval", blind_interval);
        result.put("last_rebuy_level", last_rebuy_level);
        result.put("timestamp_created", ServerValue.TIMESTAMP);
        return result;
    }
}
