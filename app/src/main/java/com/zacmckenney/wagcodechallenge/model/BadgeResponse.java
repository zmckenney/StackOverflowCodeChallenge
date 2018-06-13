package com.zacmckenney.wagcodechallenge.model;

import com.google.gson.annotations.SerializedName;

public class BadgeResponse {

    @SerializedName("bronze")
    private int bronze;

    @SerializedName("silver")
    private int silver;

    @SerializedName("gold")
    private int gold;

    public int getBronze() {
        return bronze;
    }

    public void setBronze(int bronze) {
        this.bronze = bronze;
    }

    public int getSilver() {
        return silver;
    }

    public void setSilver(int silver) {
        this.silver = silver;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getBronzeString() {
        return Integer.toString(bronze);
    }

    public String getSilverString() {
        return Integer.toString(silver);
    }

    public String getGoldString() {
        return Integer.toString(gold);
    }
}
