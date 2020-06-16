package com.ad340.mapgame.entities;

public interface HealthBarListener {
    void onPlayerAttacked();
    void onPlayerKilled();
    void onScore(String entity);
}
