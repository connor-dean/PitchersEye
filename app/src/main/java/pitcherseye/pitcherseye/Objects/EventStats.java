/*
  This class is our model for our eventStats child in Firebase.
 */

package pitcherseye.pitcherseye.Objects;

public class EventStats {

    public String eventID;
    public String eventName;
    public String eventDate;
    public Boolean isGame;
    public Boolean isHome;
    public int playerID;
    public int teamID;
    public int pitchCount;
    public int strikeCount;
    public int eventBallCount;
    public int eventBallCountLow;
    public int eventBallCountHigh;
    public int eventBallCountLeft;
    public int eventBallCountRight;
    public int eventR1C1Count;
    public int eventR1C2Count;
    public int eventR1C3Count;
    public int eventR2C1Count;
    public int eventR2C2Count;
    public int eventR2C3Count;
    public int eventR3C1Count;
    public int eventR3C2Count;
    public int eventR3C3Count;
    public int eventFastballCount;
    public int eventChangeupCount;
    public int eventCurveballCount;
    public int eventSliderCount;
    public int eventOtherCount;

    public EventStats(String eventID, String eventName, String eventDate, Boolean isGame, Boolean isHome,
                      int playerID, int teamID, int pitchCount, int strikeCount,
                      int eventBallCount, int eventBallCountLow, int eventBallCountHigh, int eventBallCountLeft,
                      int eventBallCountRight, int R1C1Count, int R1C2Count,  int R1C3Count, int R2C1Count, int R2C2Count,
                      int R2C3Count, int R3C1Count, int R3C2Count, int R3C3Count, int fastballCount,
                      int changeupCount, int curveballCount, int sliderCount, int otherCount) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.isGame = isGame;
        this.isHome = isHome;
        this.playerID = playerID;
        this.teamID = teamID;
        this.pitchCount = pitchCount;
        this.strikeCount = strikeCount;
        this.eventBallCount = eventBallCount;
        this.eventBallCountLow = eventBallCountLow;
        this.eventBallCountHigh = eventBallCountHigh;
        this.eventBallCountLeft = eventBallCountLeft;
        this.eventBallCountRight = eventBallCountRight;
        this.eventR1C1Count = R1C1Count;
        this.eventR1C2Count = R1C2Count;
        this.eventR1C3Count = R1C3Count;
        this.eventR2C1Count = R2C1Count;
        this.eventR2C2Count = R2C2Count;
        this.eventR2C3Count = R2C3Count;
        this.eventR3C1Count = R3C1Count;
        this.eventR3C2Count = R3C2Count;
        this.eventR3C3Count = R3C3Count;
        this.eventFastballCount = fastballCount;
        this.eventChangeupCount = changeupCount;
        this.eventCurveballCount = curveballCount;
        this.eventSliderCount = sliderCount;
        this.eventOtherCount = otherCount;
    }
    public String getEventName() { return eventName; }

    public String getEventDate() { return eventDate; }

    public Boolean getGame() { return isGame; }

    public Boolean getHome() { return isHome; }

    public int getPitchCount() { return pitchCount; }

    public int getStrikeCount() {
        return strikeCount;
    }

    public int getEventR1C1Count() {
        return eventR1C1Count;
    }

    public int getEventR1C2Count() {
        return eventR1C2Count;
    }

    public int getEventR1C3Count() {
        return eventR1C3Count;
    }

    public int getEventR2C1Count() {
        return eventR2C1Count;
    }

    public int getEventR2C2Count() {
        return eventR2C2Count;
    }

    public int getEventR2C3Count() {
        return eventR2C3Count;
    }

    public int getEventR3C1Count() {
        return eventR3C1Count;
    }

    public int getEventR3C2Count() {
        return eventR3C2Count;
    }

    public int getEventR3C3Count() {
        return eventR3C3Count;
    }

    public int getEventBallCount() {
        return eventBallCount;
    }

    public int getEventBallCountLow() {
        return eventBallCountLow;
    }

    public int getEventBallCountHigh() {
        return eventBallCountHigh;
    }

    public int getEventBallCountLeft() {
        return eventBallCountLeft;
    }

    public int getEventBallCountRight() {
        return eventBallCountRight;
    }

    public int getEventFastballCount() {
        return eventFastballCount;
    }

    public int getEventChangeupCount() {
        return eventChangeupCount;
    }

    public int getEventCurveballCount() {
        return eventCurveballCount;
    }

    public int getEventSliderCount() {
        return eventSliderCount;
    }

    public int getEventOtherCount() {
        return eventOtherCount;
    }
}
