package com.dstudios.pokermon;

/**
 * Created by dante on 29/05/2016.
 */

public class Level {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private Integer number;
    private Integer small_blind;
    private Integer big_blind;
    private Integer ante;
    private Integer time;
    private Boolean break_after;
    private Boolean add_on_after;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    private String mode;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSmall_blind() {
        return small_blind;
    }

    public void setSmall_blind(Integer small_blind) {
        this.small_blind = small_blind;
    }

    public Integer getBig_blind() {
        return big_blind;
    }

    public void setBig_blind(Integer big_blind) {
        this.big_blind = big_blind;
    }

    public Integer getAnte() {
        return ante;
    }

    public void setAnte(Integer ante) {
        this.ante = ante;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Boolean getBreak_after() {
        return break_after;
    }

    public void setBreak_after(Boolean break_after) {
        this.break_after = break_after;
    }

    public Boolean getAdd_on_after() {
        return add_on_after;
    }

    public void setAdd_on_after(Boolean add_on_after) {
        this.add_on_after = add_on_after;
    }
}
