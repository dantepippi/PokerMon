package com.dstudios.pokermon;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dante on 29/05/2016.
 */

public class Tournament {

    private String name;
    private Long timestamp_created;
    private Long timestamp_started;
    private Long timestamp_finished;
    private String structure;
    private String owner_uid;
    private String buy_in;
    private String rebuy;
    private String add_on;
    private Integer last_rebuy_level;
    private Integer starting_chips;
    private Integer blind_interval;

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

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

    public Integer getLast_rebuy_level() {
        return last_rebuy_level;
    }

    public void setLast_rebuy_level(Integer last_rebuy_level) {
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


    public Map<String, String> getTimestamp_created() {
        return ServerValue.TIMESTAMP;
    }

    @JsonIgnore
    public Long getTimestamp_createdLong() {
        return this.timestamp_created;
    }

    public void setTimestamp_created(Long timestamp_created) {
        this.timestamp_created = timestamp_created;
    }



    @JsonIgnore
    public Long getTimestamp_startedLong() { return this.timestamp_started;}

    public Map<String, String> getTimestamp_started() {
        return ServerValue.TIMESTAMP;
    }

    public void setTimestamp_started(Long timestamp_started) {
        this.timestamp_started = timestamp_started;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("owner_uid", owner_uid);
        result.put("buyin", buy_in);
        result.put("rebuy", rebuy);
        result.put("addon", add_on);
        result.put("starting_chips", starting_chips);
        result.put("blind_interval", blind_interval);
        result.put("last_rebuy_level", last_rebuy_level);
        result.put("timestamp_created", ServerValue.TIMESTAMP);
        result.put("structure", structure);
        return result;
    }
}
