/*
  This class is our model for our pitcherStats child in Firebase.
 */

package pitcherseye.pitcherseye.Objects;

public class PitcherStats {

    // Values stored in pitcherStats
    public String eventID;
    public String eventName;
    public String eventDate;
    public Boolean isGame;
    public Boolean isHome;
    public String pitcherName;
    public int pitcherID;
    public int teamID;
    public int pitchCount;
    public int strikeCount;
    public int pitcherBallCount;
    public int pitcherBallCountLow;
    public int pitcherBallCountHigh;
    public int pitcherBallCountLeft;
    public int pitcherBallCountRight;
    public int pitcherR1C1Count;
    public int pitcherR1C2Count;
    public int pitcherR1C3Count;
    public int pitcherR2C1Count;
    public int pitcherR2C2Count;
    public int pitcherR2C3Count;
    public int pitcherR3C1Count;
    public int pitcherR3C2Count;
    public int pitcherR3C3Count;
    public int pitcherFastballCount;
    public int pitcherChangeupCount;
    public int pitcherCurveballCount;
    public int pitcherSliderCount;
    public int pitcherOtherCount;

    // Firebase requires a no-arg constructor or else you'll get:
    // com.google.firebase.database.DatabaseException: Class pitcherseye.pitcherseye.Objects.EventStats is missing a constructor with no arguments
    public PitcherStats() { }

    public PitcherStats(String eventID, String eventName, String eventDate, Boolean isGame, Boolean isHome,
                        int pitcherID, String pitcherName, int teamID, int pitchCount, int strikeCount,
                        int pitcherBallCount, int pitcherBallCountLow, int pitcherBallCountHigh,
                        int pitcherBallCountLeft, int pitcherBallCountRight, int R1C1Count, int R1C2Count,
                        int R1C3Count, int R2C1Count, int R2C2Count, int R2C3Count, int R3C1Count,
                        int R3C2Count, int R3C3Count, int fastballCount, int changeupCount, int curveballCount,
                        int sliderCount, int otherCount) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.isGame = isGame;
        this.isHome = isHome;
        this.pitcherID = pitcherID;
        this.pitcherName = pitcherName;
        this.teamID = teamID;
        this.pitchCount = pitchCount;
        this.strikeCount = strikeCount;
        this.pitcherBallCount = pitcherBallCount;
        this.pitcherBallCountLow = pitcherBallCountLow;
        this.pitcherBallCountHigh = pitcherBallCountHigh;
        this.pitcherBallCountLeft = pitcherBallCountLeft;
        this.pitcherBallCountRight = pitcherBallCountRight;
        this.pitcherR1C1Count = R1C1Count;
        this.pitcherR1C2Count = R1C2Count;
        this.pitcherR1C3Count = R1C3Count;
        this.pitcherR2C1Count = R2C1Count;
        this.pitcherR2C2Count = R2C2Count;
        this.pitcherR2C3Count = R2C3Count;
        this.pitcherR3C1Count = R3C1Count;
        this.pitcherR3C2Count = R3C2Count;
        this.pitcherR3C3Count = R3C3Count;
        this.pitcherFastballCount = fastballCount;
        this.pitcherChangeupCount = changeupCount;
        this.pitcherCurveballCount = curveballCount;
        this.pitcherSliderCount = sliderCount;
        this.pitcherOtherCount = otherCount;
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

    public Boolean getGame() {
        return isGame;
    }

    public Boolean getHome() {
        return isHome;
    }

    public String getPitcherName() {
        return pitcherName;
    }

    public int getPitcherID() {
        return pitcherID;
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

    public int getPitcherBallCount() {
        return pitcherBallCount;
    }

    public int getPitcherBallCountLow() {
        return pitcherBallCountLow;
    }

    public int getPitcherBallCountHigh() {
        return pitcherBallCountHigh;
    }

    public int getPitcherBallCountLeft() {
        return pitcherBallCountLeft;
    }

    public int getPitcherBallCountRight() {
        return pitcherBallCountRight;
    }

    public int getPitcherR1C1Count() {
        return pitcherR1C1Count;
    }

    public int getPitcherR1C2Count() {
        return pitcherR1C2Count;
    }

    public int getPitcherR1C3Count() {
        return pitcherR1C3Count;
    }

    public int getPitcherR2C1Count() {
        return pitcherR2C1Count;
    }

    public int getPitcherR2C2Count() {
        return pitcherR2C2Count;
    }

    public int getPitcherR2C3Count() {
        return pitcherR2C3Count;
    }

    public int getPitcherR3C1Count() {
        return pitcherR3C1Count;
    }

    public int getPitcherR3C2Count() {
        return pitcherR3C2Count;
    }

    public int getPitcherR3C3Count() {
        return pitcherR3C3Count;
    }

    public int getPitcherFastballCount() {
        return pitcherFastballCount;
    }

    public int getPitcherChangeupCount() {
        return pitcherChangeupCount;
    }

    public int getPitcherCurveballCount() {
        return pitcherCurveballCount;
    }

    public int getPitcherSliderCount() {
        return pitcherSliderCount;
    }

    public int getPitcherOtherCount() {
        return pitcherOtherCount;
    }
}
