package com.example.mapgame.entities;

public interface HealthBarListener {
    void onPlayerAttacked();
    void onPlayerKilled();
    void onScore(String entity);
}
