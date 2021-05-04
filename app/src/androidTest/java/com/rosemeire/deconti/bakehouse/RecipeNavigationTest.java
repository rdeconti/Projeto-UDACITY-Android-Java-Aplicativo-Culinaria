package com.rosemeire.deconti.bakehouse;

import android.content.pm.ActivityInfo;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.AccessibilityChecks;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.rosemeire.deconti.bakehouse.userInterface.RecipesListActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeNavigationTest {

    private IdlingResource mIdlingResource;

    @Rule
    public final ActivityTestRule<RecipesListActivity> mActivityTestRule = new ActivityTestRule<>(RecipesListActivity.class);

    @BeforeClass
    public static void enableAccessibilityChecks() {
        AccessibilityChecks.enable().setRunChecksFromRootView(true);
    }

    @Before
    public void registerIdlingResource() {

        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();

        IdlingRegistry.getInstance().register(mIdlingResource);

        // navigation is not visible in landscape
        mActivityTestRule.getActivity()
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Test
    public void recipeNavigationTest() {

        //RecipeActivity with click at first recipe in grid view
        onView(ViewMatchers.withId(R.id.recyclerView_main))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        ViewInteraction tabViewSteps = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabLayout_recipes),
                                0),
                        0),
                        isDisplayed()));

        tabViewSteps.perform(click());

        // verify if steps list were created
        ViewInteraction linearLayoutSteps = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.include),
                                isDescendantOfA(withId(R.id.frameLayout))),
                        1),
                        isDisplayed()));

        linearLayoutSteps.check(matches(isDisplayed()));

        // go to StepsDetailsActivity with click at first step at list view
        ViewInteraction recyclerViewStepsDetails = onView(
                allOf(withId(R.id.include),
                        childAtPosition(
                                withId(R.id.frameLayout),
                                0)));

        recyclerViewStepsDetails.perform(actionOnItemAtPosition(0, click()));

        //Recipe Introduction
        // verify if title is same of first step of current recipe
        ViewInteraction textView = onView(
                allOf(withText("Recipe Introduction"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar_stepDetail),
                                        isDescendantOfA(withId(R.id.appBarLayout_stepDetail))),
                                0),
                        isDisplayed()));

        textView.check(matches(withText("Recipe Introduction")));

        //If 'Next Step' button is enabled
        ViewInteraction nextButtonFirst = onView(
                allOf(withId(R.id.button_stepsDetailFragmentNext),
                        isDisplayed()));

        nextButtonFirst.check(matches(isEnabled()));

        // them click 'Next Step'
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button_stepsDetailFragmentNext),
                        isDisplayed()));
        appCompatButton.perform(click());

        ///Recipe "Starting prep"
        ViewInteraction textViewStepOne = onView(
                allOf(withText("Starting prep"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar_stepDetail),
                                        isDescendantOfA(withId(R.id.appBarLayout_stepDetail))),
                                0),
                        isDisplayed()));

        textViewStepOne.check(matches(withText("Starting prep")));

        // verify if 'Prev Step' button is Enabled
        ViewInteraction prevButton = onView(
                allOf(withId(R.id.button_stepsDetailFragmentPrevious),
                        isDisplayed()));

        prevButton.check(matches(isDisplayed()));

        // verify if 'Next Step' button is Enabled
        ViewInteraction nextButton = onView(
                allOf(withId(R.id.button_stepsDetailFragmentNext),
                        isDisplayed()));

        nextButton.check(matches(isDisplayed()));

        // click 'Next Step'
        ViewInteraction appCompatButtonStep2 = onView(
                allOf(withId(R.id.button_stepsDetailFragmentNext),
                        isDisplayed()));

        appCompatButtonStep2.perform(click());

        // Recipe "Press the crust into baking form."
        ViewInteraction textViewStepThree = onView(
                allOf(withText("Prep the cookie crust."),
                        childAtPosition(
                                allOf(withId(R.id.toolbar_stepDetail),
                                        isDescendantOfA(withId(R.id.appBarLayout_stepDetail))),
                                0),
                        isDisplayed()));

        textViewStepThree.check(matches(withText("Prep the cookie crust.")));

        // verify if 'Prev Step' button is enabled
        ViewInteraction buttonPrevStep3 = onView(
                allOf(withId(R.id.button_stepsDetailFragmentPrevious),
                        isDisplayed()));

        buttonPrevStep3.check(matches(isDisplayed()));

        // verify if 'Next Step' button is enabled
        ViewInteraction buttonNextStep3 = onView(
                allOf(withId(R.id.button_stepsDetailFragmentNext),
                        isDisplayed()));

        buttonNextStep3.check(matches(isDisplayed()));

        // click 'Next Step'
        ViewInteraction appCompatButtonStep3 = onView(
                allOf(withId(R.id.button_stepsDetailFragmentNext),
                        isDisplayed()));

        appCompatButtonStep3.perform(click());

        // Recipe "Press the crust into baking form."
        ViewInteraction textViewStepFour = onView(
                allOf(withText("Press the crust into baking form."),
                        childAtPosition(
                                allOf(withId(R.id.toolbar_stepDetail),
                                        isDescendantOfA(withId(R.id.appBarLayout_stepDetail))),
                                0),
                        isDisplayed()));

        textViewStepFour.check(matches(withText("Press the crust into baking form.")));

        // click 'Next Step'
        ViewInteraction appCompatButtonStep4 = onView(
                allOf(withId(R.id.button_stepsDetailFragmentNext),
                        isDisplayed()));

        appCompatButtonStep4.perform(click());

        // Recipe "Start filling prep"
        ViewInteraction textViewStepFive = onView(
                allOf(withText("Start filling prep"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar_stepDetail),
                                        isDescendantOfA(withId(R.id.appBarLayout_stepDetail))),
                                0),
                        isDisplayed()));

        textViewStepFive.check(matches(withText("Start filling prep")));

        // click 'Next Step'
        ViewInteraction appCompatButtonStep5 = onView(
                allOf(withId(R.id.button_stepsDetailFragmentNext),
                        isDisplayed()));

        appCompatButtonStep5.perform(click());

        // Recipe "Finish filling prep"
        ViewInteraction textViewStepSix = onView(
                allOf(withText("Finish filling prep"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar_stepDetail),
                                        isDescendantOfA(withId(R.id.appBarLayout_stepDetail))),
                                0),
                        isDisplayed()));

        textViewStepSix.check(matches(withText("Finish filling prep")));

        // click 'Next Step' and reach last step of current recipe
        ViewInteraction appCompatButtonStep6 = onView(
                allOf(withId(R.id.button_stepsDetailFragmentNext),
                        isDisplayed()));

        appCompatButtonStep6.perform(click());

        // Recipe "Finishing Steps"
        ViewInteraction textViewStepSeven = onView(
                allOf(withText("Finishing Steps"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar_stepDetail),
                                        isDescendantOfA(withId(R.id.appBarLayout_stepDetail))),
                                0),
                        isDisplayed()));

        textViewStepSeven.check(matches(withText("Finishing Steps")));

        // verify if 'Prev Step' button is enabled
        ViewInteraction lastPrevButton = onView(
                allOf(withId(R.id.button_stepsDetailFragmentPrevious),
                        isDisplayed()));

        lastPrevButton.check(matches(isDisplayed()));

    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
