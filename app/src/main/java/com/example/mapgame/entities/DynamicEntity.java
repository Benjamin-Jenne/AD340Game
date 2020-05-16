package com.example.mapgame.entities;

public class DynamicEntity extends Entity {
    private int HEALTH;
    private String DIRECTION_FACING;
    public int getHealth(){
        return HEALTH;
    }
    public String getDirection_Facing(){
        return DIRECTION_FACING;
    }
    public void setHealth(int newHealth){
        HEALTH = newHealth;
    }
    public void setDIRECTION_FACING(String newDirection_Facing){
        DIRECTION_FACING = newDirection_Facing;
    }
}
