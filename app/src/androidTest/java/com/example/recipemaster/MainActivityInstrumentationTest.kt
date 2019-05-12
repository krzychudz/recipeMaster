package com.example.recipemaster

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.linkedin.android.testbutler.TestButler
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.rule.GrantPermissionRule



@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentationTest {

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @get:Rule var permissionRule = GrantPermissionRule.grant(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)


    @Test
    fun fabMenu_items_should_not_be_displayed_in_start(){

        Espresso.onView(withId(R.id.menu)).check(ViewAssertions.matches(isDisplayed()))

        Espresso.onView(withId(R.id.menu_fb)).check(ViewAssertions.matches(not(isDisplayed())))
        Espresso.onView(withId(R.id.menu_getData)).check(ViewAssertions.matches(not(isDisplayed())))
    }

    @Test
    fun fabMenu_open_and_close(){

        Espresso.onView(allOf(withParent(withId(R.id.menu)), withClassName(endsWith("ImageView")), isDisplayed()))
            .perform(click())

        Espresso.onView(withId(R.id.menu_fb)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.menu_getData)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.menu)).check(ViewAssertions.matches(isDisplayed()))

        Espresso.onView(allOf(withParent(withId(R.id.menu)), withClassName(endsWith("ImageView")), isDisplayed()))
            .perform(click())

        Espresso.onView(withId(R.id.menu_fb)).check(ViewAssertions.matches(not(isDisplayed())))
        Espresso.onView(withId(R.id.menu_getData)).check(ViewAssertions.matches(not(isDisplayed())))
        Espresso.onView(withId(R.id.menu)).check(ViewAssertions.matches(isDisplayed()))

    }

    @Test
    fun logo_fragment_should_be_visible_in_start(){
        Espresso.onView(withId(R.id.main_page_fragment)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.logo_image)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.logo_name)).check(ViewAssertions.matches(isDisplayed()))

    }



    @Test
    fun get_data_from_api_and_display_it(){

        Espresso.onView(allOf(withParent(withId(R.id.menu)), withClassName(endsWith("ImageView")), isDisplayed()))
            .perform(click())

        Espresso.onView(withId(R.id.menu_getData)).perform(click())

        Espresso.onView(withId(R.id.images)).check(ViewAssertions
            .matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
            .check(ViewAssertions.matches(withText(containsString("Images:"))))

        Espresso.onView(withId(R.id.recipe_fragment)).check(ViewAssertions.matches(isDisplayed()))
            .check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

        Espresso.onView(withId(R.id.name)).check(ViewAssertions.matches(isDisplayed()))
            .check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

        Espresso.onView(withId(R.id.Ingredients)).check(ViewAssertions.matches(isDisplayed()))
            .check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
            .check(ViewAssertions.matches(withText(containsString("Ingredients:"))))

        Espresso.onView(withId(R.id.Preparing)).check(ViewAssertions.matches(isDisplayed()))
            .check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
            .check(ViewAssertions.matches(withText(containsString("Preparing:"))))

        Espresso.onView(withId(R.id.description))
            .check(ViewAssertions.matches(isDisplayed()))
            .check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

        Espresso.onView(withId(R.id.ingredients))
            .check(ViewAssertions.matches(isDisplayed()))
            .check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

    }

    @Test
    fun show_dialog_after_click_on_image_to_save(){

        Espresso.onView(allOf(withParent(withId(R.id.menu)), withClassName(endsWith("ImageView")), isDisplayed()))
            .perform(click())

        Espresso.onView(withId(R.id.menu_getData)).perform(click())


        Espresso.onView(withId(R.id.flexboxImages))
            .perform(scrollTo())

        Espresso.onView(
            withTagValue(`is`("Image0" as Object))
        ).perform(click())

        Espresso.onView(withText("Save image")).check(ViewAssertions.matches(isDisplayed()))

    }


    @Test
    fun show_toast_after_save_file_correct(){
        Espresso.onView(allOf(withParent(withId(R.id.menu)), withClassName(endsWith("ImageView")), isDisplayed()))
            .perform(click())

        Espresso.onView(withId(R.id.menu_getData)).perform(click())


        Espresso.onView(withId(R.id.flexboxImages))
            .perform(scrollTo())

        Espresso.onView(
            withTagValue(`is`("Image0" as Object))
        ).perform(click())

        Espresso.onView(withId(android.R.id.button1)).perform(click())

        Espresso.onView(withText("Image saved!")).inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView)))).
            check(ViewAssertions.matches(isDisplayed()))

    }


    @Test
    fun snackbar_should_be_displayed_after_login(){
        activityRule.activity.setIsLog(true)
        activityRule.activity.setUser("test test")

        Espresso.onView(allOf(withParent(withId(R.id.menu)), withClassName(endsWith("ImageView")), isDisplayed()))
            .perform(click())

        Espresso.onView(withId(R.id.menu_getData)).perform(click())

        Espresso.onView(withText("Logged as test test")).check(ViewAssertions.matches(isDisplayed()))

    }

    @Test
    fun show_dialog_when_user_wants_logout(){
        activityRule.activity.setIsLog(true)
        activityRule.activity.setUser("test test")

        Espresso.onView(allOf(withParent(withId(R.id.menu)), withClassName(endsWith("ImageView")), isDisplayed()))
            .perform(click())

        Espresso.onView(withId(R.id.menu_fb)).perform(click())

        Espresso.onView(withText("Logged in as: test test")).inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView)))).
            check(ViewAssertions.matches(isDisplayed()))

    }

    @Test
    fun show_toast_after_logout(){
        activityRule.activity.setIsLog(true)
        activityRule.activity.setUser("test test")

        Espresso.onView(allOf(withParent(withId(R.id.menu)), withClassName(endsWith("ImageView")), isDisplayed()))
            .perform(click())

        Espresso.onView(withId(R.id.menu_fb)).perform(click())

        Espresso.onView(withId(android.R.id.button1)).perform(click())

        Espresso.onView(withText("You have been logged out.")).inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView)))).
        check(ViewAssertions.matches(isDisplayed()))
    }

}

