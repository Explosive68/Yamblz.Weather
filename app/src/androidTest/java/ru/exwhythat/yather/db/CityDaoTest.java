package ru.exwhythat.yather.db;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import ru.exwhythat.yather.data.local.CityDao;
import ru.exwhythat.yather.data.local.entities.City;
import ru.exwhythat.yather.util.LiveDataTestUtil;
import ru.exwhythat.yather.util.TestData;

import static junit.framework.Assert.assertEquals;
import static ru.exwhythat.yather.util.TestData.TestCity.testCityId1;

/**
 * All db tests are outdated. They were written for LiveData, now there is Flowable and Single result types of DB queries.
 * But you can use these tests as scheme.
 */

@RunWith(AndroidJUnit4.class)
public class CityDaoTest extends DbTest {

    private CityDao cityDao;

    @Before
    public void prepare() {
        cityDao = db.cityDao();
    }

    @Test
    public void insertOneAndLoadById() throws InterruptedException {
        City expectedCity = TestData.TestCity.testCity1;
        expectedCity.setSelected(true);
        cityDao.insert(expectedCity);


        cityDao.getSelectedCitySingle()
                .test()
                .assertResult(expectedCity);
    }

    @Test
    public void insertOneAndLoadAll() throws InterruptedException {
        City expectedCity = TestData.TestCity.testCity1;
        cityDao.insert(expectedCity);

        //List<City> loadedCities = LiveDataTestUtil.getValue(cityDao.get());
        //assertTrue(loadedCities.size() == 1);

        //assertEquals(expectedCity, loadedCities.get(0));
    }

    @Test
    public void insertTwoAndLoadAll() throws InterruptedException {
        City expectedCity1 = TestData.TestCity.testCity1;
        cityDao.insert(expectedCity1);
        City expectedCity2 = TestData.TestCity.testCity2;
        cityDao.insert(expectedCity2);
        City notExpectedCity = TestData.TestCity.testCity3;

        /*List<City> loadedCities = LiveDataTestUtil.getValue(cityDao.getAll());
        assertTrue(loadedCities.size() == 2);

        assertThat(loadedCities, hasItem(expectedCity1));
        assertThat(loadedCities, hasItem(expectedCity2));
        assertThat(loadedCities, not(hasItem(notExpectedCity)));*/
    }

    @Test
    public void insertTwiceOverwrites() throws InterruptedException {
        City initial = TestData.TestCity.testCity1;
        cityDao.insert(initial);
        // Create another city with same id but different in other fields
        City replacement = new City(testCityId1, "RandomCity", false);
        cityDao.insert(replacement);

        /*List<City> loadedCities = LiveDataTestUtil.getValue(cityDao.getAll());
        assertEquals(1, loadedCities.size());
        assertThat(loadedCities, hasItem(replacement));
        assertThat(loadedCities, not(hasItem(initial)));*/
    }

    @Test
    public void clearNotEmptyTable() throws InterruptedException {
        cityDao.insert(TestData.TestCity.testCity1);
        cityDao.insert(TestData.TestCity.testCity2);
        cityDao.insert(TestData.TestCity.testCity3);

        int count = cityDao.clearTable();

        /*List<City> loadedCities = LiveDataTestUtil.getValue(cityDao.getAll());

        assertEquals(3, count);
        assertEquals(0, loadedCities.size());*/
    }
}
