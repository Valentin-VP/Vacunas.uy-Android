package com.example.vacunasuy;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegistroTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void registroTest() {
        ViewInteraction materialButton = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.login), ViewMatchers.withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(android.R.id.content),
                                        0),
                                0),
                        ViewMatchers.isDisplayed()));
        materialButton.perform(ViewActions.click());

        ViewInteraction materialTextView = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.registrarse), ViewMatchers.withText("Registrarse"),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.linearLayout2),
                                        10),
                                0)));
        materialTextView.perform(ViewActions.scrollTo(), ViewActions.click());

        ViewInteraction appCompatEditText = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.cedula),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.linearLayout2),
                                        childAtPosition(
                                                ViewMatchers.withClassName(Matchers.is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                0)));
        appCompatEditText.perform(ViewActions.scrollTo(), ViewActions.replaceText("49457795"), ViewActions.closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.nombre),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.linearLayout2),
                                        childAtPosition(
                                                ViewMatchers.withClassName(Matchers.is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                1)));
        appCompatEditText2.perform(ViewActions.scrollTo(), ViewActions.replaceText("Valentin"), ViewActions.closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.apellido),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.linearLayout2),
                                        childAtPosition(
                                                ViewMatchers.withClassName(Matchers.is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                2)));
        appCompatEditText3.perform(ViewActions.scrollTo(), ViewActions.replaceText("Vasconcellos"), ViewActions.closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.departamento),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.linearLayout2),
                                        childAtPosition(
                                                ViewMatchers.withClassName(Matchers.is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                3)));
        appCompatEditText4.perform(ViewActions.scrollTo(), ViewActions.replaceText("Montevideo"), ViewActions.closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.barrio),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.linearLayout2),
                                        childAtPosition(
                                                ViewMatchers.withClassName(Matchers.is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                4)));
        appCompatEditText5.perform(ViewActions.scrollTo(), ViewActions.replaceText("Aguada"), ViewActions.closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.direccion),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.linearLayout2),
                                        childAtPosition(
                                                ViewMatchers.withClassName(Matchers.is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                5)));
        appCompatEditText6.perform(ViewActions.scrollTo(), ViewActions.replaceText("Calle 1"), ViewActions.closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.email),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.linearLayout2),
                                        childAtPosition(
                                                ViewMatchers.withClassName(Matchers.is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                6)));
        appCompatEditText7.perform(ViewActions.scrollTo(), ViewActions.replaceText("valevp2010@gmail.com"), ViewActions.closeSoftKeyboard());

        ViewInteraction materialTextView2 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.date), ViewMatchers.withText("Fec. nacimiento"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.linearLayout2),
                                        childAtPosition(
                                                ViewMatchers.withClassName(Matchers.is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                7)));
        materialTextView2.perform(ViewActions.scrollTo(), ViewActions.click());

        ViewInteraction materialTextView3 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withClassName(Matchers.is("com.google.android.material.textview.MaterialTextView")), ViewMatchers.withText("2021"),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withClassName(Matchers.is("android.widget.LinearLayout")),
                                        0),
                                0),
                        ViewMatchers.isDisplayed()));
        materialTextView3.perform(ViewActions.click());

        DataInteraction materialTextView4 = Espresso.onData(Matchers.anything())
                .inAdapterView(Matchers.allOf(ViewMatchers.withClassName(Matchers.is("android.widget.YearPickerView")),
                        childAtPosition(
                                ViewMatchers.withClassName(Matchers.is("com.android.internal.widget.DialogViewAnimator")),
                                1)))
                .atPosition(100);
        materialTextView4.perform(ViewActions.scrollTo(), ViewActions.click());

        ViewInteraction appCompatImageButton = Espresso.onView(
                Matchers.allOf(ViewMatchers.withClassName(Matchers.is("androidx.appcompat.widget.AppCompatImageButton")), ViewMatchers.withContentDescription("Previous month"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withClassName(Matchers.is("android.widget.DayPickerView")),
                                        childAtPosition(
                                                ViewMatchers.withClassName(Matchers.is("com.android.internal.widget.DialogViewAnimator")),
                                                0)),
                                1)));
        appCompatImageButton.perform(ViewActions.scrollTo(), ViewActions.click());

        ViewInteraction appCompatImageButton2 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withClassName(Matchers.is("androidx.appcompat.widget.AppCompatImageButton")), ViewMatchers.withContentDescription("Previous month"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withClassName(Matchers.is("android.widget.DayPickerView")),
                                        childAtPosition(
                                                ViewMatchers.withClassName(Matchers.is("com.android.internal.widget.DialogViewAnimator")),
                                                0)),
                                1)));
        appCompatImageButton2.perform(ViewActions.scrollTo(), ViewActions.click());

        ViewInteraction appCompatImageButton3 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withClassName(Matchers.is("androidx.appcompat.widget.AppCompatImageButton")), ViewMatchers.withContentDescription("Previous month"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withClassName(Matchers.is("android.widget.DayPickerView")),
                                        childAtPosition(
                                                ViewMatchers.withClassName(Matchers.is("com.android.internal.widget.DialogViewAnimator")),
                                                0)),
                                1)));
        appCompatImageButton3.perform(ViewActions.scrollTo(), ViewActions.click());

        ViewInteraction materialButton2 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(android.R.id.button1), ViewMatchers.withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withClassName(Matchers.is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton2.perform(ViewActions.scrollTo(), ViewActions.click());

        ViewInteraction appCompatSpinner = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.sexo),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.linearLayout2),
                                        childAtPosition(
                                                ViewMatchers.withClassName(Matchers.is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                8)));
        appCompatSpinner.perform(ViewActions.scrollTo(), ViewActions.click());

        DataInteraction appCompatCheckedTextView = Espresso.onData(Matchers.anything())
                .inAdapterView(childAtPosition(
                        ViewMatchers.withClassName(Matchers.is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(0);
        appCompatCheckedTextView.perform(ViewActions.click());

        ViewInteraction materialTextView5 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.registrarse), ViewMatchers.withText("Registrarse"),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.linearLayout2),
                                        10),
                                0)));
        materialTextView5.perform(ViewActions.scrollTo(), ViewActions.click());
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
