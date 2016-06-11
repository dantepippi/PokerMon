package com.dstudios.pokermon;

/**
 * Created by dante on 29/05/2016.
 */

public class Tournament {

    private String name;
    private Long timestamp_created;
    private String structure;
    private String owner_uid;
    private String buyin;
    private String rebuy;
    private String addon;
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

    public String getBuyIn() {
        return buyin;
    }

    public void setBuyIn(String buyIn) {
        this.buyin = buyIn;
    }

    public String getRebuy() {
        return rebuy;
    }

    public void setRebuy(String rebuy) {
        this.rebuy = rebuy;
    }

    public String getAddon() {
        return addon;
    }

    public void setAddon(String addon) {
        this.addon = addon;
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

    public Long getTimestamp_created() {
        return this.timestamp_created;
    }

    public void setTimestamp_created(Long timestamp_created) {
        this.timestamp_created = timestamp_created;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
