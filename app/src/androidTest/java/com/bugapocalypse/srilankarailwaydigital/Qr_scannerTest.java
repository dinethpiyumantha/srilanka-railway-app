package com.bugapocalypse.srilankarailwaydigital;

import junit.framework.TestCase;

import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class Qr_scannerTest extends TestCase {
    @Test
    public void testIntent(){
       // onView(withId(R.id.scannerView)).perform(click())
        onView(withId(R.id.scannerView)).perform(click());
        intended(hasComponent(TicketNote.class.getName()));




    }
}