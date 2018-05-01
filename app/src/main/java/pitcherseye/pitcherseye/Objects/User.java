/*
  This class is our model for our users child in Firebase.
 */

package pitcherseye.pitcherseye.Objects;

public class User {

    // Values stored in User
    public String fname;
    public String lname;
    public String email;
    public String password;
    public String teamID;

    public User(String fname, String lname, String email, String password, String teamID) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.teamID = teamID;
    }
}
