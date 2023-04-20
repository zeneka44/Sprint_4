package com.github.zeneka44.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.String.format;
import static java.time.Duration.ofSeconds;
import static org.openqa.selenium.By.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    // Кнопка в хедере вопроса
    private final String questionHeaderButton = "//div[@data-accordion-component='AccordionItemButton' and text()='%s']";
    // Панель вопроса
    private final String questionItem = questionHeaderButton + "/ancestor::div[@data-accordion-component='AccordionItem']";
    // Ответ на вопрос
    private final String answer = questionItem + "//div[@data-accordion-component='AccordionItemPanel']//p";
    // Кнопка "Да все привыкли"
    private final By acceptCookieButton = id("rcc-confirm-button");
    // Кнопка "Заказать" в хедере
    private final By orderHeaderButton = cssSelector("[class*='Header_Nav'] button[class*='Button_Button']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, ofSeconds(10));
    }

    public void acceptCookies() {
        WebElement button = driver.findElement(acceptCookieButton);
        button.click();
        wait.until(invisibilityOf(button));
    }

    public void expandQuestion(String question) {
        WebElement element = driver.findElement(xpath(format(questionHeaderButton, question)));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
        element.click();
        wait.until(visibilityOfElementLocated(xpath(format(answer, question))));
    }

    public String getAnswerText(String question) {
        return driver.findElement(xpath(format(answer, question))).getText();
    }

    public OrderPage clickOrder() {
        driver.findElement(orderHeaderButton).click();
        return new OrderPage(driver);
    }
}

