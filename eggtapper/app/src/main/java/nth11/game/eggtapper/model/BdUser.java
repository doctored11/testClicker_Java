package nth11.game.eggtapper.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Accounts",
        indices = {@Index(value = {"name"}, unique = true)}
)
public class BdUser {
    @PrimaryKey
    @NonNull
    private String name;
    private String password;

    @NonNull
    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setToolForce(int toolForce) {
        this.toolForce = toolForce;
    }

    public void setToolProfit(String toolProfit) {
        this.toolProfit = toolProfit;
    }

    public void setToolUpCoastProfit(String toolUpCoastProfit) {
        this.toolUpCoastProfit = toolUpCoastProfit;
    }

    public void setToolUpCoastForce(String toolUpCoastForce) {
        this.toolUpCoastForce = toolUpCoastForce;
    }

    public void setIncubatorForce(int incubatorForce) {
        this.incubatorForce = incubatorForce;
    }

    public void setIncubatorProfit(String incubatorProfit) {
        this.incubatorProfit = incubatorProfit;
    }

    public void setIncubatorUpCoastProfit(String incubatorUpCoastProfit) {
        this.incubatorUpCoastProfit = incubatorUpCoastProfit;
    }

    public void setIncubatorUpCoastForce(String incubatorUpCoastForce) {
        this.incubatorUpCoastForce = incubatorUpCoastForce;
    }

    public void setCountTapP(int countTapP) {
        this.countTapP = countTapP;
    }

    public void setCountTapF(int countTapF) {
        this.countTapF = countTapF;
    }

    public void setCountIncP(int countIncP) {
        this.countIncP = countIncP;
    }

    public void setCountIncF(int countIncF) {
        this.countIncF = countIncF;
    }

    public String getPassword() {
        return password;
    }

    public String getMoney() {
        return money;
    }

    public int getStrength() {
        return strength;
    }

    public int getToolForce() {
        return toolForce;
    }

    public String getToolProfit() {
        return toolProfit;
    }

    public String getToolUpCoastProfit() {
        return toolUpCoastProfit;
    }

    public String getToolUpCoastForce() {
        return toolUpCoastForce;
    }

    public int getIncubatorForce() {
        return incubatorForce;
    }

    public String getIncubatorProfit() {
        return incubatorProfit;
    }

    public String getIncubatorUpCoastProfit() {
        return incubatorUpCoastProfit;
    }

    public String getIncubatorUpCoastForce() {
        return incubatorUpCoastForce;
    }

    public int getCountTapP() {
        return countTapP;
    }

    public int getCountTapF() {
        return countTapF;
    }

    public int getCountIncP() {
        return countIncP;
    }

    public int getCountIncF() {
        return countIncF;
    }

    private String money;
    private int strength;

    @ColumnInfo(name = "tool_force") private int toolForce;
    @ColumnInfo(name = "tool_profit")private String toolProfit;
    @ColumnInfo(name = "tool_up_coast_profit")private String toolUpCoastProfit;
    @ColumnInfo(name = "tool_up_coast_force")private String toolUpCoastForce;
    @ColumnInfo(name = "incubator_force") private int incubatorForce;
    @ColumnInfo(name = "incubator_profit")private String incubatorProfit;
    @ColumnInfo(name = "incubator_up_coast_profit") private String incubatorUpCoastProfit;
    @ColumnInfo(name = "incubator_up_coast_force")private String incubatorUpCoastForce;
    @ColumnInfo(name = "count_tap_p")private int countTapP;
    @ColumnInfo(name = "count_tap_f")private int countTapF;
    @ColumnInfo(name = "count_inc_p")private int countIncP;
    @ColumnInfo(name = "count_inc_f")private int countIncF;


    public User getUser() {

        User user = new User(name, password, GameCurrency.parse(money), strength, toolForce,
                    GameCurrency.parse(toolProfit), GameCurrency.parse(toolUpCoastProfit), GameCurrency.parse(toolUpCoastForce),
                    incubatorForce, GameCurrency.parse(incubatorProfit), GameCurrency.parse(incubatorUpCoastProfit),
                    GameCurrency.parse(incubatorUpCoastForce),countTapP,countTapF,countIncP,countIncF);



        return user;
    }

    public BdUser(@NonNull String name, String password, String money, int strength, int toolForce, String toolProfit, String toolUpCoastProfit, String toolUpCoastForce, int incubatorForce, String incubatorProfit, String incubatorUpCoastProfit, String incubatorUpCoastForce, int countTapP, int countTapF, int countIncP, int countIncF) {
        this.name = name;
        this.password = password;
        this.money = money;
        this.strength = strength;
        this.toolForce = toolForce;
        this.toolProfit = toolProfit;
        this.toolUpCoastProfit = toolUpCoastProfit;
        this.toolUpCoastForce = toolUpCoastForce;
        this.incubatorForce = incubatorForce;
        this.incubatorProfit = incubatorProfit;
        this.incubatorUpCoastProfit = incubatorUpCoastProfit;
        this.incubatorUpCoastForce = incubatorUpCoastForce;
        this.countTapP = countTapP;
        this.countTapF = countTapF;
        this.countIncP = countIncP;
        this.countIncF = countIncF;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}