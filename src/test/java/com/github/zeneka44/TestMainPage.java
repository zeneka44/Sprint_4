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

import static java.time.Duration.ofSeconds;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestMainPage {
    private WebDriver driver;
    private MainPage mainPage;
    private final String question;
    private final String answer;

    public TestMainPage(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    @Parameters
    public static Object[][] getData() {
        return new Object[][] {
                { "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                { "Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат." +
                        " Если хотите покататься с друзьями," +
                        " можете просто сделать несколько заказов — один за другим."},
                { "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая." +
                        " Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента," +
                        " когда вы оплатите заказ курьеру." +
                        " Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                { "Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                { "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное" +
                        " — всегда можно позвонить в поддержку по красивому номеру 1010."},
                { "Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток" +
                        " — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                { "Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет," +
                        " объяснительной записки тоже не попросим. Все же свои."},
                { "Я жизу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."},

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
    public void testQuestions() {
        mainPage.expandQuestion(question);
        String actualAnswer = mainPage.getAnswerText(question);
        assertEquals(answer, actualAnswer);
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
