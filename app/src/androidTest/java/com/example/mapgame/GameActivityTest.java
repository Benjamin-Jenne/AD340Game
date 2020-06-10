package com.example.mapgame;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

//@RunWith(AndroidJUnit4.class)
public class GameActivityTest {

//    private static final String TAG = GameActivityTest.class.getSimpleName();

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void buttonClick() {

        MainActivityTest main = new MainActivityTest();
        main.testClickPlay();

        onView(withId(R.id.textViewHealth)).check(matches(withText(R.string.health)));

        onView(withId(R.id.buttonLeft)).perform(click());
        onView(withId(R.id.buttonRight)).perform(click());
        onView(withId(R.id.buttonUp)).perform(click());
        onView(withId(R.id.buttonDown)).perform(click());

        attackClick();

    }

    public void attackClick() {
        onView(withId(R.id.buttonAttack)).perform(click());

        onView(withId(R.id.textViewAttachedText))
                .check(matches(withText(R.string.doh)));

        onView(withId(R.id.determinateBar))
                .check(matches(isDisplayed()));
    }

    public void leftClick() {

    }
    public void rightClick() {

    }
    public void upClick() {

    }
    public void downClick() {

    }

}
