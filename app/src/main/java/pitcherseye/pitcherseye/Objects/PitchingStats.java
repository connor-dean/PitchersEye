package pitcherseye.pitcherseye.Objects;

/**
 * Created by Connor on 2/25/2018.
 */

public class PitchingStats {

    // Change pending
    public int gameID;
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


    public PitchingStats() { }

    public PitchingStats(int gameID, int playerID, int teamID, int pitchCount, int strikeCount, int ballCount,
                 int R1C1Count, int R1C2Count,  int R1C3Count, int R2C1Count, int R2C2Count,
                 int R2C3Count, int R3C1Count, int R3C2Count, int R3C3Count) {
        this.gameID = gameID;
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
}
