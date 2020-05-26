package com.example.mapgame;

import android.content.pm.ActivityInfo;
import android.util.Log;
import android.widget.DatePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

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

    public void hasAuthor(){
        onView(withId(R.id.author))
                .check(matches(withText(R.string.authors)));
    }
}