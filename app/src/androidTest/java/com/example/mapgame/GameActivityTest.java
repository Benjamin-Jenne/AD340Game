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

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testGameActivity() throws InterruptedException {

        pressPlay();

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

    }

    @Test
    public void testItemScore() {
        pressPlay();
        downClickSingle();
        rightClick();
        onView(withId(R.id.textViewScoreAmount)).check(matches(withText(R.string.fifty)));
    }

    @Test
    public void itemCollect() {
        pressPlay();
        rightClick();
        rightClickSingle();
        downClick();
        leftClick();
        rightClickSingle();
        upClick();
        leftClickSingle();
        downClickSingle();
    }

    @Test
    public void testEnemyScore() throws InterruptedException {
        pressPlay();
        downClickSingle();
        Thread.sleep(3000);
        attackClick();
        onView(withId(R.id.textViewScoreAmount)).check(matches(withText(R.string.one_hundred)));
    }

    @Test
    public void killEnemy() throws InterruptedException {
        pressPlay();
        Thread.sleep(3000);
        downClickSingle();
        attackClick();
        downClickSingle();
        attackClick();
        downClickSingle();
        downClickSingle();
        attackClick();
        rightClickSingleTap();
        attackClick();

    }



    public void pressPlay() {
        MainActivityTest main = new MainActivityTest();
        main.testClickPlay();
    }

    // Check game activity views are present
    public void checkViews() {
        onView(withId(R.id.textViewHealth)).check(matches(withText(R.string.health)));
        onView(withId(R.id.determinateBar))
                .check(matches(isDisplayed()));
        onView(withId(R.id.chronometerTimer)).check(matches(isDisplayed()));
        onView(withId(R.id.textViewScore)).check(matches(withText(R.string.score)));
        onView(withId(R.id.textViewScoreAmount)).check(matches(isDisplayed()));
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
    public void downClickSingle() {
        onView(withId(R.id.buttonDown)).perform(ViewActions.longClick());
    }
    public void rightClickSingle() {
        onView(withId(R.id.buttonRight)).perform(ViewActions.longClick());
    }
    public void rightClickSingleTap() {
        onView(withId(R.id.buttonRight)).perform(click());
    }
    public void upClickSingle() {
        onView(withId(R.id.buttonUp)).perform(ViewActions.longClick());
    }
    public void leftClickSingle() {
        onView(withId(R.id.buttonLeft)).perform(ViewActions.longClick());
    }

    // Observe decrease in progress bar
    public void attacked() {

    }

    // Click Restart button
    public void gameOver() throws InterruptedException {

        Thread.sleep(30000);

        onView(withId(R.id.textViewGameOver)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonRestart)).check(matches(isDisplayed()));

        onView(withId(R.id.buttonRestart)).perform(click());


    }

}
