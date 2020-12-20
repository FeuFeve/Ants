package model;

public class Resource extends GameObject {

    public int amount;


    @Override
    public String toString() {
        return name + ": " + amount;
    }
}
