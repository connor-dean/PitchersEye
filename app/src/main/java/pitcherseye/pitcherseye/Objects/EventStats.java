package pitcherseye.pitcherseye.Objects;

import java.util.Date;

/**
 * Created by Connor on 2/25/2018.
 */

public class EventStats {

    // Change pending
    public String eventID;
    public String eventName;
    public String eventDate;
    public int playerID;
    public int teamID;
    public int pitchCount;
    public int strikeCount;
    public int ballCount;
    public int R1C1Count;
    public int R1C2Count;
    public int R1C3Count;
    public int R2C1Count;
    public int R2C2Count;
    public int R2C3Count;
    public int R3C1Count;
    public int R3C2Count;
    public int R3C3Count;

    /* TODO:
        Will want to add:
            - Event name
            - Event date
            - Array of pitcher IDs
     */

    public EventStats() { }

    public EventStats(String eventID, String eventName, String eventDate, int playerID, int teamID, int pitchCount, int strikeCount, int ballCount,
                 int R1C1Count, int R1C2Count,  int R1C3Count, int R2C1Count, int R2C2Count,
                 int R2C3Count, int R3C1Count, int R3C2Count, int R3C3Count) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.playerID = playerID;
        this.teamID = teamID;
        this.pitchCount = pitchCount;
        this.strikeCount = strikeCount;
        this.ballCount = ballCount;
        this.R1C1Count = R1C1Count;
        this.R1C2Count = R1C2Count;
        this.R1C3Count = R1C3Count;
        this.R2C1Count = R2C1Count;
        this.R2C2Count = R2C2Count;
        this.R2C3Count = R2C3Count;
        this.R3C1Count = R3C1Count;
        this.R3C2Count = R3C2Count;
        this.R3C3Count = R3C3Count;
    }
    public String getEventID() {
        return eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getTeamID() {
        return teamID;
    }

    public int getPitchCount() {
        return pitchCount;
    }

    public int getStrikeCount() {
        return strikeCount;
    }

    public int getBallCount() {
        return ballCount;
    }

    public int getR1C1Count() {
        return R1C1Count;
    }

    public int getR1C2Count() {
        return R1C2Count;
    }

    public int getR1C3Count() {
        return R1C3Count;
    }

    public int getR2C1Count() {
        return R2C1Count;
    }

    public int getR2C2Count() {
        return R2C2Count;
    }

    public int getR2C3Count() {
        return R2C3Count;
    }

    public int getR3C1Count() {
        return R3C1Count;
    }

    public int getR3C2Count() {
        return R3C2Count;
    }

    public int getR3C3Count() {
        return R3C3Count;
    }
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public void setPitchCount(int pitchCount) {
        this.pitchCount = pitchCount;
    }

    public void setStrikeCount(int strikeCount) {
        this.strikeCount = strikeCount;
    }

    public void setBallCount(int ballCount) {
        this.ballCount = ballCount;
    }

    public void setR1C1Count(int r1C1Count) {
        R1C1Count = r1C1Count;
    }

    public void setR1C2Count(int r1C2Count) {
        R1C2Count = r1C2Count;
    }

    public void setR1C3Count(int r1C3Count) {
        R1C3Count = r1C3Count;
    }

    public void setR2C1Count(int r2C1Count) {
        R2C1Count = r2C1Count;
    }

    public void setR2C2Count(int r2C2Count) {
        R2C2Count = r2C2Count;
    }

    public void setR2C3Count(int r2C3Count) {
        R2C3Count = r2C3Count;
    }

    public void setR3C1Count(int r3C1Count) {
        R3C1Count = r3C1Count;
    }

    public void setR3C2Count(int r3C2Count) {
        R3C2Count = r3C2Count;
    }

    public void setR3C3Count(int r3C3Count) {
        R3C3Count = r3C3Count;
    }
}
