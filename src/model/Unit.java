package model;

public class Unit extends GameObject {

    public String pluralName;

    // Base stats
    public int hp;
    public int attack;
    public int defense;
    public int layingTime;
    public int foodCost;

    @Override
    public String toString() {
        return "Unit { " +
                "name='" + name + '\'' +
                ", pluralName='" + pluralName +
                ", hp=" + hp +
                ", attack=" + attack +
                ", defense=" + defense +
                ", layingTime=" + layingTime +
                ", foodCost=" + foodCost + '\'' +
                " }";
    }
}
