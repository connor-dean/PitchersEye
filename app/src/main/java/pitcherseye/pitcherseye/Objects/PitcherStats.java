package pitcherseye.pitcherseye.Objects;

import java.util.Date;

/**
 * Created by Connor on 2/25/2018.
 */

public class PitcherStats {

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
    public int fastballCount;
    public int changeupCount;
    public int curveballCount;
    public int sliderCount;
    public int otherCount;

    /* TODO:
        Will want to add:
            - Event name
            - Event date
            - Array of pitcher IDs
     */

    public PitcherStats() {}

    public PitcherStats(String eventID, String eventName, String eventDate, int playerID, int teamID, int pitchCount, int strikeCount, int ballCount,
                      int R1C1Count, int R1C2Count,  int R1C3Count, int R2C1Count, int R2C2Count,
                      int R2C3Count, int R3C1Count, int R3C2Count, int R3C3Count, int fastballCount,
                      int changeupCount, int curveballCount, int sliderCount, int otherCount) {
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
        this.fastballCount = fastballCount;
        this.changeupCount = changeupCount;
        this.curveballCount = curveballCount;
        this.sliderCount = sliderCount;
        this.otherCount = otherCount;
    }
}
