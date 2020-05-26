package com.example.mapgame;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class GameActivityTest {

    private static final String TAG = GameActivityTest.class.getSimpleName();

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void buttonClick() {

        MainActivityTest.testClickPlay();

        onView(withId(R.id.buttonAttack)).perform(click());
        onView(withId(R.id.buttonLeft)).perform(click());
        onView(withId(R.id.buttonRight)).perform(click());
        onView(withId(R.id.buttonUp)).perform(click());
        onView(withId(R.id.buttonDown)).perform(click());
    }

}
