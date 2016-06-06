package com.dstudios.pokermon;

/**
 * Created by dante on 29/05/2016.
 */

public class Level {

    private Integer number;
    private Integer small_blind;
    private Integer big_blind;
    private Integer ante;
    private Long sum;

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

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


}
