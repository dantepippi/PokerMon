package com.dstudios.pokermon;

/**
 * Created by dante on 29/05/2016.
 */

class Level {

    private Integer number;
    private Integer small_blind;
    private Integer big_blind;
    private Integer ante;
    private Long sum;

    Long getSum() {
        return sum;
    }

    void setSum(Long sum) {
        this.sum = sum;
    }

    Integer getNumber() {
        return number;
    }

    void setNumber(Integer number) {
        this.number = number;
    }

    Integer getSmall_blind() {
        return small_blind;
    }

    void setSmall_blind(Integer small_blind) {
        this.small_blind = small_blind;
    }

    Integer getBig_blind() {
        return big_blind;
    }

    void setBig_blind(Integer big_blind) {
        this.big_blind = big_blind;
    }

    Integer getAnte() {
        return ante;
    }

    void setAnte(Integer ante) {
        this.ante = ante;
    }


}
