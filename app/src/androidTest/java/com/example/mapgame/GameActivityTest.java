package com.example.mapgame;

import androidx.test.espresso.action.ViewActions;
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

    private static final String TAG = GameActivityTest.class.getSimpleName();
//    private UiDevice mDevice;
//    private static final String BASIC_SAMPLE_PACKAGE
//            = "com.example.mapgame";
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testGameActivity() throws InterruptedException {
        MainActivityTest main = new MainActivityTest();
        main.testClickPlay();

        checkViews();

        rightClick();
        attackClick();

        upClick();
        attackClick();

        leftClick();
        attackClick();

        downClick();
        attackClick();

        gameOver();

//        Thread.sleep(5000);
//        attacked();
    }


    // Check game activity views are present
    public void checkViews() {
        onView(withId(R.id.textViewHealth)).check(matches(withText(R.string.health)));
        onView(withId(R.id.determinateBar))
                .check(matches(isDisplayed()));
        onView(withId(R.id.chronometerTimer)).check(matches(isDisplayed()));
    }

    // Click attack button
    public void attackClick() {
        onView(withId(R.id.buttonAttack)).perform(ViewActions.longClick());
    }

    // Directional clicks. Pair with player movement
    public void leftClick() {
        onView(withId(R.id.buttonLeft)).perform(click());
        onView(withId(R.id.buttonLeft)).perform(ViewActions.longClick());
        onView(withId(R.id.buttonLeft)).perform(ViewActions.longClick());
        onView(withId(R.id.buttonLeft)).perform(ViewActions.longClick());
        onView(withId(R.id.buttonLeft)).perform(ViewActions.longClick());
        onView(withId(R.id.buttonLeft)).perform(ViewActions.longClick());
    }
    public void rightClick() {
        onView(withId(R.id.buttonRight)).perform(click());
        onView(withId(R.id.buttonRight)).perform(ViewActions.longClick());
        onView(withId(R.id.buttonRight)).perform(ViewActions.longClick());
        onView(withId(R.id.buttonRight)).perform(ViewActions.longClick());
        onView(withId(R.id.buttonRight)).perform(ViewActions.longClick());
    }
    public void upClick() {
        onView(withId(R.id.buttonUp)).perform(click());
        onView(withId(R.id.buttonUp)).perform(ViewActions.longClick());
        onView(withId(R.id.buttonUp)).perform(ViewActions.longClick());
        onView(withId(R.id.buttonUp)).perform(ViewActions.longClick());
        onView(withId(R.id.buttonUp)).perform(ViewActions.longClick());
    }
    public void downClick() {
        onView(withId(R.id.buttonDown)).perform(click());
        onView(withId(R.id.buttonDown)).perform(ViewActions.longClick());
        onView(withId(R.id.buttonDown)).perform(ViewActions.longClick());
        onView(withId(R.id.buttonDown)).perform(ViewActions.longClick());
        onView(withId(R.id.buttonDown)).perform(ViewActions.longClick());
    }

    // Observe decrease in progress bar
    public void attacked() {

    }

    // Click Restart button
    public void gameOver() throws InterruptedException {

//        onView(withId(R.id.determinateBar))
//                .check(matches(isDisplayed()));

        Thread.sleep(30000);

        onView(withId(R.id.textViewGameOver)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonRestart)).check(matches(isDisplayed()));

        onView(withId(R.id.buttonRestart)).perform(click());


    }

}
