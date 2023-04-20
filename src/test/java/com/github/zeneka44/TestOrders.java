package com.github.zeneka44;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.github.zeneka44.pages.MainPage;
import com.github.zeneka44.pages.OrderPage;

import static java.time.Duration.ofSeconds;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class TestOrders {
    private WebDriver driver;
    private MainPage mainPage;
    private final String name;
    private final String lastname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String term;
    private final String colorValue;
    private final String comment;

    public TestOrders(String name, String lastname, String address, String metro, String phone, String date, String term, String colorValue, String comment) {
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.term = term;
        this.colorValue = colorValue;
        this.comment = comment;
    }

    @Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"Валерий", "Меладзе", "Москва, Ленинский проспект, 1", "Бульвар Рокоссовского", "+79113215465", "30.04.2023", "сутки", "чёрный жемчуг", "тестовый заказ"},
                {"Иван", "Иванов", "СПб, Московский проспект, 1", "Черкизовская", "+79111234567", "29.04.2023", "двое суток", "серая безысходность", "тестовый заказ2"}
        };
    }

    @Before
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(ofSeconds(5));
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage = new MainPage(driver);
        mainPage.acceptCookies();
    }

    @Test
    public void testFlowByHeaderOrderButton() {
        OrderPage orderPage = mainPage.clickOrder();
        orderPage.fillPersonalData(name, lastname, address, metro, phone);
        orderPage.clickNext();
        orderPage.fillRentDetails(date, term, colorValue, comment);
        orderPage.clickOrder();
        orderPage.confirmOrder();
        assertTrue(orderPage.getModalHeaderText().contains("Заказ оформлен"));
        assertTrue(orderPage.getModalHeaderText().contains("Номер заказа:"));
    }
    @Test
    public void testFlowByRoadMapOrderButton() {
        OrderPage orderPage = mainPage.clickRoadMapOrderButton();
        orderPage.fillPersonalData(name, lastname, address, metro, phone);
        orderPage.clickNext();
        orderPage.fillRentDetails(date, term, colorValue, comment);
        orderPage.clickOrder();
        orderPage.confirmOrder();
        assertTrue(orderPage.getModalHeaderText().contains("Заказ оформлен"));
        assertTrue(orderPage.getModalHeaderText().contains("Номер заказа:"));
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
