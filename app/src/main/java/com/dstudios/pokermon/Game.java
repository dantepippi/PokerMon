package com.dstudios.pokermon;

/**
 * Created by dante on 11/06/2016.
 */

public class Game {
    private Long timestampStarted;
    private Long timestampFinished;
    private boolean paused;
    private String creatorUid;
    private Long nextLevelTime;
    private Long lastActionTime;
    private Level currentLevel;
    private Integer millisLeft;
    private String tournament;

    public String getCreatorUid() {
        return creatorUid;
    }

    public void setCreatorUid(String creatorUid) {
        this.creatorUid = creatorUid;
    }

    public Long getLastActionTime() {
        return lastActionTime;
    }

    public void setLastActionTime(Long lastActionTime) {
        this.lastActionTime = lastActionTime;
    }

    public Integer getMillisLeft() {
        return millisLeft;
    }

    public void setMillisLeft(Integer millisLeft) {
        this.millisLeft = millisLeft;
    }

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    public Long getTimestampStarted() {
        return timestampStarted;
    }

    public void setTimestampStarted(Long timestampStarted) {
        this.timestampStarted = timestampStarted;
    }

    public Long getTimestampFinished() {
        return timestampFinished;
    }

    public void setTimestampFinished(Long timestampFinished) {
        this.timestampFinished = timestampFinished;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public Long getNextLevelTime() {
        return nextLevelTime;
    }

    public void setNextLevelTime(Long nextLevelTime) {
        this.nextLevelTime = nextLevelTime;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }


    public Long getTimestamp_started() {
        return this.timestampStarted;
    }


    public void setTimestamp_started(Long timestamp_started) {
        this.timestampStarted = timestamp_started;
    }


}
