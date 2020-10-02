package com.focusedpoint.weighttracker;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.focusedpoint.weighttracker", appContext.getPackageName());
    }
    @Test
    public void testUser() {
        User Christopher = new User("Christopher", "1234", User.sex.MALE, 20, 134, 5, 8);

        //test getters
        System.out.println("Printing User Details...");
        System.out.println();
        assertEquals("Christopher", Christopher.getUsername());
        assertEquals("1234", Christopher.getPassword());
        assertEquals(User.sex.MALE, Christopher.uSex);
        assertEquals(20, Christopher.getAge());
        assertEquals(134, Christopher.currentWeight());
        assertEquals(5, Christopher.getHeightFT());
        assertEquals(8, Christopher.getHeightIN());


        System.out.println();
        System.out.println("=======================================================");
        System.out.println();

        //Test setters
        System.out.println("Changing Username...");
        Christopher.setUsername("Lagos");
        assertEquals("Lagos", Christopher.getUsername());
        System.out.println("Username Changed to: " + Christopher.getUsername());

        System.out.println();

        System.out.println("Changing Password...");
        Christopher.setPassword("AppleSauce$4683");
        assertEquals("AppleSauce$4683", Christopher.getPassword());
        System.out.println("Password Changed to: " + Christopher.getPassword());

        Christopher.setAge(21);
        Christopher.setWeight(141);
        Christopher.setHeightFT(6);
        Christopher.setHeightIN(0);

        System.out.println();

        System.out.println("User has had the following changes after a year: ");
        System.out.println(Christopher.toString());

        System.out.println();

        System.out.println("Entry #1 yields a weight of: " + Christopher.weightAt(1) + " lb");
        System.out.println("Entry #2 yields a weight of: " + Christopher.weightAt(2) + " lb");


        System.out.println();
        System.out.println("=======================================================");
        System.out.println();

        //BMI test
        System.out.println("Calculating Users Body Mass Index...");
        double bmi = Christopher.BMI();
        System.out.println("BMI: " + bmi);
        System.out.println("A BMI of " + bmi + " classifies as " + Christopher.classifyBMI());

        System.out.println();
        System.out.println("=======================================================");
        System.out.println();

        //BFP
        System.out.println("Calculating Users Body Fat Percentage using the BMI Method...");
        System.out.println(Christopher.BFP() + "%");

        System.out.println();
        System.out.println("=======================================================");
        System.out.println();

        //Meals
        Christopher.addMeal("Philly Cheesesteak", 680);
        Christopher.addMeal("Ramen", 436);
        Christopher.addMeal("Travipatty", 630);

        System.out.println("Printing today's meals...");
        Christopher.ht.print(System.out);

        System.out.println();
        System.out.println("The User had a total of " + Christopher.ht.size() + " meals.");

    }
}