package com.napier.sem;

import java.lang.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Main function for start of application
 * last edited by Angel Tenev at 10:42 AM 4.28.2021
 */
//Main class for connecting to a local database
public class App {

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */

    public void connect(String location)
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries =30;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }
    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }


    public static void main(String args[]) throws SQLException {
        // Create new Application
        App a = new App();

        // Connect to database
        if (args.length < 1)
        {
            a.connect("localhost:3306");
        }
        else
        {
            a.connect(args[0]);
        }



        //issue N35 - print all countries in world
        //a.printCountries(a.getCountryListByWorld());

        //issue N34 - print countries by continent
        //a.printCountriesByN(a.getCountryListByContinent("Europe"), 10);

        //issue N33 - print countries by region
        //a.printCountries(a.getCountryListByRegion("Caribbean"));

        //issue N32 - print N of all countries in world
        //a.printCountriesByN(a.getCountryListByWorld(), 10);

        //issue N31 - print n countries by continent
        //a.printCountriesByN(a.getCountryListByContinent("Europe"), 10);

        //issue N30 - print n countries by region
        //a.printCountriesByN(a.getCountryListByRegion("Caribbean"), 5);

        //issue N29 - print all cities in world
        //a.printCities(a.getCityListByWorld());

        //issue N28 - print cities by continent
        //a.printCities(a.getCityListByContinent("Europe"));

        //issue N27 - print cities by region
        //a.printCities(a.getCityListByRegion("Caribbean"));

        //issue N26 - print cities by country
        //a.printCities(a.getCityListByCountry("Argentina"));

        //issue N25 - print cities by district
        //a.printCities(a.getCityListByDistrict("Burgas"));

        //issue N24 - print n of all cities in world
        //a.printCitiesByN(a.getCityListByWorld(), 10);

        //issue N23 - print n cities by continent
        //a.printCitiesByN(a.getCityListByContinent("Europe"), 10);

        //issue N22 - print n cities by region
        //a.printCitiesByN(a.getCityListByRegion("Caribbean"), 10);

        //issue N21 - print n cities by country
        //a.printCitiesByN(a.getCityListByCountry("Bulgaria"), 10);

        //issue N20 - print n cities by district
        //a.printCitiesByN(a.getCityListByDistrict("Burgas"), 10);

        //issue N19 - print all capital cities in world
        //a.printCities(a.getCapitalListInWorld());

        //issue N18 - print capital cities by continent
        //a.printCities(a.getCapitalListInContinent("Europe"));

        //issue N17 - print capital cities by region
        //a.printCities(a.getCapitalListInRegion("Caribbean"));

        //issue N16 - print n of all capital cities in world
        //a.printCitiesByN(a.getCapitalListInWorld(),10);

        //issue N15 - print n capital cities in continent
        //a.printCitiesByN(a.getCapitalListInContinent("Europe"), 10);

        //issue N14 - print n capital cities in region
        //a.printCitiesByN(a.getCapitalListInRegion("Caribbean"), 10);

        //issue N13 - print number of people, living in cities and number of people living outside of cities in each continent
        //a.printPeople(a.getCityListByContinent("Europe"), a.getCountryListByContinent("Europe"));
        //a.printPeopleInCitiesAndOutCitiesByContinent();

        //issue N12 - print number of people, living in cities and number of people living outside of cities in each region
        //a.printPeopleInCitiesAndOutCitiesByRegion();

        //issue N11 - print number of people, living in cities and number of people living outside of cities in each country
        //a.printPeopleInCitiesAndOutCitiesByCountry();

        //Print Regions - Feature added by Vesko - not in coursework requirements
        //a.printRegions(a.getRegionList());
        //may not be included

        // World population
        //a.printPopulationWorld();

        // The population of a continent
        //a.printPopulationContinent();

        // The population of a region
        //a.printPopulationRegion("Melanesia");

        // The population of a country
        //a.printPopulationCountry("Bulgaria");

        // The population of a district
        //a.printPopulationDistrict("Grad Sofija");

        // The population of a city
        //a.printPopulationCity("Sofija");

        // The population of a continent
        //a.printPeopleWhoSpeak();

        // Disconnect from database
        a.disconnect();
    }


    /**
     * Get all countries languages
     */
    public ArrayList<CountryLanguage> getCountryLanguageList()
    {

        try
        {
            // Make string used for sql statement
            String strSelect = "SELECT countrylanguage.countrycode, countrylanguage.language, countrylanguage.isofficial, countrylanguage.percentage" +
                    " FROM countrylanguage;";
            PreparedStatement stmt = con.prepareStatement(strSelect);

            // Execute SQL statement
            ResultSet resultSet = stmt.executeQuery();


            // Creating array list of class country
            ArrayList<CountryLanguage> countryLanguages  = new ArrayList<>();


            // Check one is returned
            while (resultSet.next())
            {
                CountryLanguage cntrLang = new CountryLanguage();
                cntrLang.countryCode = resultSet.getString("countrycode");
                cntrLang.language = resultSet.getString("language");
                cntrLang.percentage = resultSet.getInt("percentage");
                countryLanguages.add(cntrLang);
            }
            return countryLanguages;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Print population of the world who speak
     */
    public void printPeopleWhoSpeak(){
        long populationWorld = printPopulationWorld();
        double [] peopleSpeakingThisLang = new double[5];

        double chinese = printPeopleWhoSpeak("Chinese");
        peopleSpeakingThisLang[0] = chinese;
        double english = printPeopleWhoSpeak("English");
        peopleSpeakingThisLang[1] = english;
        double hindi = printPeopleWhoSpeak("Hindi");
        peopleSpeakingThisLang[2] = hindi;
        double spanish = printPeopleWhoSpeak("Spanish");
        peopleSpeakingThisLang[3]  = spanish;
        double arabic = printPeopleWhoSpeak("Arabic");
        peopleSpeakingThisLang[4]  = arabic;

        double temporary;
        for (int i = 1; i < peopleSpeakingThisLang.length; i++) {
            for (int k = i; k > 0; k--) {
                if (peopleSpeakingThisLang[k] < peopleSpeakingThisLang [k - 1]) {
                    temporary = peopleSpeakingThisLang[k];
                    peopleSpeakingThisLang[k] = peopleSpeakingThisLang[k - 1];
                    peopleSpeakingThisLang[k - 1] = temporary;
                }//end if loop
            }//end for loop
        }//end for loop

        for(int x = peopleSpeakingThisLang.length - 1; x >= 0; x--){
            double percentage = (peopleSpeakingThisLang[x]/populationWorld) * 100;
            double roundPercentage = (double) Math.round(percentage * 100) / 100;

            if(peopleSpeakingThisLang[x]==chinese){
                System.out.println((int) chinese + " people speak chinese, which is "
                        + roundPercentage + "% of World population");
            } else if (peopleSpeakingThisLang[x]==english){
                System.out.println((int)english + " people speak english, which is "
                        + roundPercentage + "% of World population");
            } else if(peopleSpeakingThisLang[x]==hindi){
                System.out.println((int) hindi + " people speak hindi, which is "
                        + roundPercentage + "% of World population");
            } else if (peopleSpeakingThisLang[x]==spanish){
                System.out.println((int) spanish + " people speak spanish, which is "
                        + roundPercentage + "% of World population");
            } else if (peopleSpeakingThisLang[x]==arabic){
                System.out.println((int) arabic + " people speak arabic, which is "
                        + roundPercentage + "% of World population");
            }
        }//end for loop
    }//end method

    /**
     * Print population of the world
     */

    public long printPeopleWhoSpeak(String language){
        ArrayList<Continent> continents = getContinentList();
        ArrayList<CountryLanguage> countryLanguages = getCountryLanguageList();

        long peopleSpeakingThisLanguage = 0;
        for(CountryLanguage countryLanguage : countryLanguages){
            if(countryLanguage.language.equals(language)){
                for(Continent continent : continents) {
                    ArrayList<Country> countries = getCountryListByContinent(continent.name);
                    for (Country country : countries) {
                        long test=0;
                        if(countryLanguage.countryCode.equals(country.code))
                            test = country.population;
                        peopleSpeakingThisLanguage += test*(countryLanguage.percentage/100);
                    }
                }
            }
        }
        return peopleSpeakingThisLanguage;

    }


    /**
     * Get all countries in world
     */
    public ArrayList<Country> getCountryListByWorld()
    {

        try
    {
        // Make string used for sql statement
        String strSelect = "SELECT country.code, country.name, country.continent, country.region, country.population" +
                " FROM country ORDER BY Population DESC;";
        PreparedStatement stmt = con.prepareStatement(strSelect);

        // Execute SQL statement
        ResultSet rset = stmt.executeQuery();


        // Creating array list of class country
        ArrayList<Country> countries  = new ArrayList<>();


        // Check one is returned
        while (rset.next())
        {
            Country cntr = new Country();
            cntr.code = rset.getString("code");
            cntr.continent = rset.getString("continent");
            cntr.region = rset.getString("region");
            cntr.population = rset.getInt("population");
            cntr.name = rset.getString("name");
            countries.add(cntr);
        }
        return countries;
    }
    catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }


    /**
     * Get all countries in continent
     */
    public ArrayList<Country> getCountryListByContinent(String continent)
    {

        try
        {
            // Make string used for sql statement
            String strSelect = "SELECT country.code, country.name, country.continent, country.region, country.population" +
                    " FROM country ORDER BY Population DESC;";
            PreparedStatement stmt = con.prepareStatement(strSelect);

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery();


            // Creating array list of class country
            ArrayList<Country> countries  = new ArrayList<>();


            // Check one is returned
            while (rset.next())
            {
                Country cntr = new Country();
                cntr.code = rset.getString("code");
                cntr.name = rset.getString("name");
                cntr.continent = rset.getString("continent");
                cntr.region = rset.getString("region");
                cntr.population = rset.getInt("population");
                if (cntr.continent.contains(continent))
                {
                    countries.add(cntr);
                }
            }
            return countries;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Get all countries in region
     */
    public ArrayList<Country> getCountryListByRegion(String region)
    {

        try
        {
            // Make string used for sql statement
            String strSelect = "SELECT country.code, country.name, country.continent, country.region, country.population" +
                    " FROM country ORDER BY Population DESC;";
            PreparedStatement stmt = con.prepareStatement(strSelect);

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery();


            // Creating array list of class country
            ArrayList<Country> countries  = new ArrayList<>();


            // Check one is returned
            while (rset.next())
            {
                Country cntr = new Country();
                cntr.code = rset.getString("code");
                cntr.name = rset.getString("name");
                cntr.continent = rset.getString("continent");
                cntr.region = rset.getString("region");
                cntr.population = rset.getInt("population");
                if (cntr.region.equals(region))
                {
                    countries.add(cntr);
                }
            }
            return countries;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }


    /**
     * Get all cities in world
     */
    public ArrayList<Cities> getCityListByWorld()
    {

        try
        {
            // Make string used for sql statement
            String strSelect = "SELECT city.id, city.name, city.countrycode, city.district, city.population FROM city ORDER BY Population DESC;";
            PreparedStatement stmt = con.prepareStatement(strSelect);

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery();


            // Creating array list of class Cities
            ArrayList<Cities> towns  = new ArrayList<>();


            // Check one is returned
            while (rset.next())
            {
                Cities city = new Cities();
                city.id = rset.getInt("id");
                city.name = rset.getString("name");
                city.countryCode = rset.getString("countrycode");
                city.district = rset.getString("district");
                city.population = rset.getInt("population");
                towns.add(city);
            }

            return towns;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }


    /**
     * Get all cities in continent
     */
    public ArrayList<Cities> getCityListByContinent(String continent)
    {

        try
        {
            // Make string used for sql statement
            String strSelect = "SELECT city.id, city.name, city.countrycode, city.district, city.population, country.code, country.continent " +
                    "FROM city JOIN country ON city.countrycode = country.code ORDER BY Population DESC;";
            PreparedStatement stmt = con.prepareStatement(strSelect);

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery();


            // Creating array list of class Cities
            ArrayList<Cities> towns  = new ArrayList<>();


            // Check one is returned
            while (rset.next())
            {
                Cities city = new Cities();
                Country cntr = new Country();
                city.id = rset.getInt("id");
                city.name = rset.getString("name");
                city.countryCode = rset.getString("countrycode");
                city.district = rset.getString("district");
                city.population = rset.getInt("population");
                cntr.continent = rset.getString("continent");
                if (cntr.continent.equals(continent))
                {
                    towns.add(city);
                }
            }

            return towns;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }


    /**
     * Get all cities in region
     */
    public ArrayList<Cities> getCityListByRegion(String region)
    {

        try
        {
            // Make string used for sql statement
            String strSelect = "SELECT city.id, city.name, city.countrycode, city.district, city.population, country.code, country.region " +
                    "FROM city JOIN country ON city.countrycode = country.code ORDER BY Population DESC;";
            PreparedStatement stmt = con.prepareStatement(strSelect);

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery();


            // Creating array list of class Cities
            ArrayList<Cities> towns  = new ArrayList<>();


            // Check one is returned
            while (rset.next())
            {
                Cities city = new Cities();
                Country cntr = new Country();
                city.id = rset.getInt("id");
                city.name = rset.getString("name");
                city.countryCode = rset.getString("countrycode");
                city.district = rset.getString("district");
                city.population = rset.getInt("population");
                cntr.region = rset.getString("region");
                if (cntr.region.equals(region))
                {
                    towns.add(city);
                }
            }

            return towns;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }


    /**
     * Get all cities in country
     */
    public ArrayList<Cities> getCityListByCountry(String cnt)
    {

        try
        {
            // Make string used for sql statement
            String strSelect = "SELECT city.id, city.name, city.countrycode, city.district, city.population, country.code, country.name " +
                    "FROM city JOIN country ON city.countrycode = country.code ORDER BY Population DESC;";
            PreparedStatement stmt = con.prepareStatement(strSelect);

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery();


            // Creating array list of class Cities
            ArrayList<Cities> towns  = new ArrayList<>();


            // Check one is returned
            while (rset.next())
            {
                Cities city = new Cities();
                Country cntr = new Country();
                city.id = rset.getInt("id");
                city.name = rset.getString("name");
                city.countryCode = rset.getString("countrycode");
                city.district = rset.getString("district");
                city.population = rset.getInt("population");
                cntr.name = rset.getString("country.name");
                if (cntr.name.equals(cnt))
                {
                    towns.add(city);
                }
            }

            return towns;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Get all continent
     */
    public ArrayList<Continent> getContinentList()
    {

        try
        {
            // Make string used for sql statement
            String strSelect = "SELECT DISTINCT  country.continent " +
                    "FROM country;";
            PreparedStatement stmt = con.prepareStatement(strSelect);

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery();


            // Creating array list of class Cities
            ArrayList<Continent> continents  = new ArrayList<>();


            // Check one is returned
            while (rset.next())
            {
                Continent contnt = new Continent();
                contnt.name = rset.getString("continent");
                continents.add(contnt);
            }

            return continents;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Get all regions
     */
    public ArrayList<Region> getRegionList()
    {

        try
        {
            // Make string used for sql statement
            String strSelect = "SELECT DISTINCT country.region " +
                    "FROM country;";
            PreparedStatement stmt = con.prepareStatement(strSelect);

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery();


            // Creating array list of class Cities
            ArrayList<Region> regions  = new ArrayList<>();


            // Check one is returned
            while (rset.next())
            {
                Region rgon = new Region();
                rgon.name = rset.getString("region");
                regions.add(rgon);
            }

            return regions;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Get all cities in district
     */
    public ArrayList<Cities> getCityListByDistrict(String district)
    {

        try
        {
            // Make string used for sql statement
            String strSelect = "SELECT city.id, city.name, city.countrycode, city.district, city.population " +
                    "FROM city ORDER BY Population DESC;";
            PreparedStatement stmt = con.prepareStatement(strSelect);

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery();


            // Creating array list of class Cities
            ArrayList<Cities> towns  = new ArrayList<>();


            // Check one is returned
            while (rset.next())
            {
                Cities city = new Cities();
                city.id = rset.getInt("id");
                city.name = rset.getString("name");
                city.countryCode = rset.getString("countrycode");
                city.district = rset.getString("district");
                city.population = rset.getInt("population");
                if (city.district.equals(district))
                {
                    towns.add(city);
                }
            }

            return towns;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }


    /**
     * Get all capital cities
     */
    public ArrayList<Cities> getCapitalListInWorld()
    {

        try
        {
            // Make string used for sql statement
            String strSelect = "SELECT city.id, city.name, city.countrycode, city.district, city.population " +
                    "FROM city JOIN country ON city.id = country.capital WHERE city.id = country.capital ORDER BY Population DESC;";
            PreparedStatement stmt = con.prepareStatement(strSelect);

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery();


            // Creating array list of class Cities
            ArrayList<Cities> towns  = new ArrayList<>();


            // Check one is returned
            while (rset.next())
            {
                Cities city = new Cities();
                city.id = rset.getInt("id");
                city.name = rset.getString("name");
                city.countryCode = rset.getString("countrycode");
                city.district = rset.getString("district");
                city.population = rset.getInt("population");
                towns.add(city);

            }

            return towns;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }


    /**
     * Get all capital cities in continent
     */
    public ArrayList<Cities> getCapitalListInContinent(String continent)
    {

        try
        {
            // Make string used for sql statement
            String strSelect = "SELECT city.id, city.name, city.countrycode, city.district, city.population, country.continent " +
                    "FROM city JOIN country ON city.id = country.capital WHERE city.id = country.capital ORDER BY Population DESC;";
            PreparedStatement stmt = con.prepareStatement(strSelect);

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery();


            // Creating array list of class Cities
            ArrayList<Cities> towns  = new ArrayList<>();


            // Check one is returned
            while (rset.next())
            {
                Cities city = new Cities();
                Country cntr = new Country();
                city.id = rset.getInt("id");
                city.name = rset.getString("name");
                city.countryCode = rset.getString("countrycode");
                city.district = rset.getString("district");
                city.population = rset.getInt("population");
                cntr.continent = rset.getString("continent");
                if (cntr.continent.contains(continent))
                {
                    towns.add(city);
                }

            }

            return towns;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }


    /**
     * Get all capital cities in region
     */
    public ArrayList<Cities> getCapitalListInRegion(String region)
    {

        try
        {
            // Make string used for sql statement
            String strSelect = "SELECT city.id, city.name, city.countrycode, city.district, city.population, country.region " +
                    "FROM city JOIN country ON city.id = country.capital WHERE city.id = country.capital ORDER BY Population DESC;";
            PreparedStatement stmt = con.prepareStatement(strSelect);

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery();


            // Creating array list of class Cities
            ArrayList<Cities> towns  = new ArrayList<>();


            // Check one is returned
            while (rset.next())
            {
                Cities city = new Cities();
                Country cntr = new Country();
                city.id = rset.getInt("id");
                city.name = rset.getString("name");
                city.countryCode = rset.getString("countrycode");
                city.district = rset.getString("district");
                city.population = rset.getInt("population");
                cntr.region = rset.getString("region");
                if (cntr.region.contains(region))
                {
                    towns.add(city);
                }

            }

            return towns;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }


    /**
     * Get population by continent
     */
    public ArrayList<Country> getPopulationListByContinent(String continent)
    {

        try
        {
            // Make string used for sql statement
            String strSelect = "SELECT city.id, city.name, city.countrycode, city.district, city.population, country.population, country.continent " +
                    "FROM city JOIN country ON city.countrycode = country.code ORDER BY Population DESC;";
            PreparedStatement stmt = con.prepareStatement(strSelect);

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery();


            // Creating array list of class country
            ArrayList<Country> people  = new ArrayList<>();


            // Check one is returned
            while (rset.next())
            {
                Country person = new Country();
                Cities city = new Cities();
                person.population = rset.getInt("country.population");
                city.population = rset.getInt("city.population");
                if (person.continent.contains(continent))
                {
                    people.add(person);
                }
            }
            return people;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }



    /**
     * Print countries
     */
    public void printCountries(ArrayList<Country> countries)
    {
        if (countries == null)
        {
            System.out.println("No countries");
            return;
        }
        if (countries.isEmpty())
        {
            System.out.println("No countries! The list of countries is empty.");
            return;
        }
        // Spacing out data for user accessibility
        System.out.println(String.format("%-20s %-50s %-20s %-35s %-15s",
                "Country Code", "Country Name", "Country Continent", "Country Region", "Country Population"));

        for(Country country : countries)
        {
            if (country == null)
                continue;
            String country_data =
                    String.format("%-20s %-50s %-20s %-35s %-15s",
                            country.code, country.name, country.continent, country.region, country.population);
            System.out.println(country_data);
        }
    }


    /**
     * Print set amount(n) of countries
     */
    public void printCountriesByN(ArrayList<Country> countries, int i)
    {
        if (countries == null)
        {
            System.out.println("No countries By N");
            return;
        }
        if (countries.isEmpty())
        {
            System.out.println("No countries by N! The list of country is empty.");
            return;
        }
        if (i <= 0)
        {
            System.out.println("Please specify positive integer!");
            return;
        }

        // Spacing out data for user accessibility
        System.out.println(String.format("%-20s %-50s %-20s %-35s %-15s",
                "Country Code", "Country Name", "Country Continent", "Country Region", "Country Population"));

        for(Country country : countries)
        {
            if (country == null)
                continue;
            String country_data =
                    String.format("%-20s %-50s %-20s %-35s %-15s",
                            country.code, country.name, country.continent, country.region, country.population);
            System.out.println(country_data);
        }
    }


    /**
     * Print cities
     */
    public void printCities(ArrayList<Cities> cities)
    {

        if (cities == null)
        {
            System.out.println("No cities");
            return;
        }
        if (cities.isEmpty())
        {
            System.out.println("No cities! The list of cities is empty.");
            return;
        }
        // Spacing out data for user accessibility
        System.out.println(String.format("%-10s %-35s %-20s %-25s %-15s",
                "City ID", "City Name", "City Country Code", "City District", "City Population"));

        for(Cities city : cities)
        {
            if (city == null)
                continue;
            String city_data =
                    String.format("%-10s %-35s %-20s %-25s %-15s",
                            city.id, city.name, city.countryCode, city.district, city.population);
            System.out.println(city_data);

        }
    }


    /**
     * Print set amount(n) of cities
     */
    public void printCitiesByN(ArrayList<Cities> cities, int n)
    {
        if (cities == null)
        {
            System.out.println("No cities");
            return;
        }
        if (cities.isEmpty())
        {
            System.out.println("No cities! The list of cities is empty.");
            return;
        }

        if (n <= 0)
        {
            System.out.println("Please specify positive integer!");
            return;
        }
        //declaring and init x as counter
        int x = 0;

        // Spacing out data for user accessibility
        System.out.println(String.format("%-10s %-35s %-20s %-25s %-15s",
                "City ID", "City Name", "City Country Code", "City District", "City Population"));

        for(Cities city : cities)
        {
            if (city == null)
                continue;
            String city_data =
                    String.format("%-10s %-35s %-20s %-25s %-15s",
                            city.id, city.name, city.countryCode, city.district, city.population);
            System.out.println(city_data);

            //Check if n is reached
            x ++;
            if (x == n)
            {
                break;
            }
        }
    }


    /**
     * Print amount of people, amount of people living in cities and amount of people living outside of cities by continent
     */

    public void printPeopleInCitiesAndOutCitiesByContinent(){
        ArrayList<Continent> continents = getContinentList();
        System.out.println(String.format("%-50s %-25s %-25s",
                "Continent", "People living in cities", "People not living in cities"));
        if(continents.size()>0){
            for(Continent continent : continents) {
                ArrayList<Country> countries = getCountryListByContinent(continent.name);
                int popCountInContinent = 0;
                int popCountOutContinent = 0;
                for (Country country : countries) {
                    ArrayList<Cities> cities = getCityListByCountry(country.name);
                    int popCountInCity = 0;
                    int popCountOutCity;
                    for (Cities city : cities) {
                        if (city == null)
                            continue;
                        popCountInCity += city.population;
                    }
                    popCountOutCity = country.population - popCountInCity;

                    popCountInContinent += popCountInCity;
                    popCountOutContinent += popCountOutCity;

                }
                String populationData =
                        String.format("%-50s %-25s %-25s",
                                continent.name, popCountInContinent, popCountOutContinent);
                System.out.println(populationData);
            }
        }
    }

    /**
     * Print amount of people, amount of people living in cities and amount of people living outside of cities by country
     */

    public void printPeopleInCitiesAndOutCitiesByCountry(){
        ArrayList<Country> countries = getCountryListByWorld();
        System.out.println(String.format("%-50s %-25s %-25s",
                "Country", "People living in cities", "People not living in cities"));

        if(countries.size()>0){
            for (Country country : countries){
                ArrayList<Cities> cities = getCityListByCountry(country.name);
                int popCountInCity = 0;
                int popCountOutCity;
                for (Cities city : cities) {
                    if (city == null)
                        continue;
                    popCountInCity += city.population;
                }
                popCountOutCity = country.population - popCountInCity;
                String populationData =
                        String.format("%-50s %-25s %-25s",
                                country.name, popCountInCity, popCountOutCity);
                System.out.println(populationData);
            }
        }
    }

    /**
     * Print amount of people, amount of people living in cities and amount of people living outside of cities by Region
     */

    public void printPeopleInCitiesAndOutCitiesByRegion(){
        ArrayList<Region> regions = getRegionList();
        System.out.println(String.format("%-50s %-25s %-25s",
                "Region", "People living in cities", "People not living in cities"));
        if(regions.size()>0){
            for(Region region : regions) {
                ArrayList<Country> countries = getCountryListByRegion(region.name);
                int popCountInRegion = 0;
                int popCountOutRegion = 0;
                for (Country country : countries) {
                    ArrayList<Cities> cities = getCityListByCountry(country.name);
                    int popCountInCity = 0;
                    int popCountOutCity;
                    for (Cities city : cities) {
                        if (city == null)
                            continue;
                        popCountInCity += city.population;
                    }
                    popCountOutCity = country.population - popCountInCity;

                    popCountInRegion += popCountInCity;
                    popCountOutRegion += popCountOutCity;

                }
                String populationData =
                        String.format("%-50s %-25s %-25s",
                                region.name, popCountInRegion, popCountOutRegion);
                System.out.println(populationData);
            }
        }
    }

    /**
     * Print population of the world
     */

    public long printPopulationWorld(){
        ArrayList<Continent> continents = getContinentList();
        long population = 0;
        if(continents.size()>0){
            for(Continent continent : continents) {
                ArrayList<Country> countries = getCountryListByContinent(continent.name);
                for (Country country : countries) {
                    population += country.population;
                }
            }
            System.out.println("World population: " + population);
        }
        return population;
    }

    /**
     * Print population of the continent
     */
    public void printPopulationContinent() {
        ArrayList<Continent> continents = getContinentList();

        if(continents.size()>0){
            for(Continent continent : continents) {
                long population = 0;
                ArrayList<Country> countries = getCountryListByContinent(continent.name);
                for (Country country : countries) {
                    population += country.population;
                }
                System.out.println(continent.name + " population is: " + population);
            }

        }
    }


    /**
     * Print population of the region
     */
    public void printPopulationRegion(String reg) {
        ArrayList<Region> regions = getRegionList();

        long population = 0;

        if(regions.size()>0){
            ArrayList<Cities> cities = getCityListByRegion(reg);
            for (Cities city : cities) {
                population += city.population;

            }
            System.out.println(reg + " population is: " + population);
        }
    }


    /**
     * Print population of country
     */
    public void printPopulationCountry(String cntr) {
        ArrayList<Country> countries = getCountryListByWorld();

        long population = 0;

        if(countries.size()>0){
            for (Country country : countries) {
                if (country.name.equals(cntr))
                {
                    population += country.population;
                    break;
                }
            }
            System.out.println(cntr + " population is: " + population);
        }
    }


    /**
     * Print population of district
     */
    public void printPopulationDistrict(String distr) {
        ArrayList<Cities> cities = getCityListByDistrict(distr);

        long population = 0;

        if(cities.size()>0){
            for (Cities city : cities) {
                if (city.district.equals(distr))
                {
                    population += city.population;
                    break;
                }
            }
            System.out.println(distr + " population is: " + population);
        }
    }


    /**
     * Print population of city
     */
    public void printPopulationCity(String ctname) {
        ArrayList<Cities> cities = getCityListByWorld();

        long population = 0;

        if(cities.size()>0){
            for (Cities city : cities) {
                if (city.name.equals(ctname))
                {
                    population += city.population;
                    break;
                }
            }
            System.out.println(ctname + " population is: " + population);
        }
    }


    /**
     * Print amount of regions // test function
     */

    public void printRegions(ArrayList<Region> regions)
    {
        if (regions == null)
        {
            System.out.println("No regions");
            return;
        }
        if (regions.isEmpty())
        {
            System.out.println("No regions! The list of regions is empty.");
            return;
        }
        // Spacing out data for user accessibility
        System.out.println("Region Name");

        for(Region region : regions)
        {
            if (region == null)
                continue;
            String country_data =
                    String.format("%-50s %-50s ",
                            region.name, "");
            System.out.println(country_data);
        }
    }


}

/**
 * include:
 *           - stage: unit tests
 *           install: skip
 *           jdk: oraclejdk11
 *           script: mvn test -Dtest=com.napier.sem.AppTest
 *
*/