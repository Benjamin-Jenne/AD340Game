package com.example.mapgame;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void hasTitle() {
        onView(withId(R.id.game_title))
                .check(matches(withText(R.string.game_title)));
    }
    @Test
    public void hasDescription() {
        onView(withId(R.id.game_description))
                .check(matches(withText(R.string.game_description)));
    }
    @Test
    public void hasInstructions() {
        onView(withId(R.id.game_instructions))
                .check(matches(withText(R.string.game_instructions)));
    }
    @Test
    public static void testClickPlay(){
        onView(withId(R.id.buttonPlay)).perform(click());
    }

    @Test
    public void hasAuthor(){
        onView(withId(R.id.author))
                .check(matches(withText(R.string.authors)));
    }
}