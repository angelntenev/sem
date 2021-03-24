package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060");
    }

    @Test
    void testGetCountriesByN()
    {
        ArrayList<Country> countries = new ArrayList<Country>();
        Country cntry = new Country();
        cntry = app.printCountriesByN(countries, 1);
        assertEquals(cntry.name, "Aruba");
        assertEquals(cntry.code, "ABW");
        assertEquals(cntry.continent, "North America");
    }
}