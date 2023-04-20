package com.github.zeneka44.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static java.lang.String.format;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.xpath;

public class OrderPage {
    private final WebDriver driver;
    String input = "//input[@placeholder='%s']";

    private final String metroItem = "//ul//li//*[text()='%s']";
    // Кнопка Далее
    private final By next = cssSelector("[class*='Order_NextButton'] button");

    // Срок аренды
    private final By termDropdownExpander = cssSelector("div.Dropdown-placeholder");
    private final String termDropdownItem = "//div[@class='Dropdown-option' and text()='%s']";
    // Цвет
    private final String color = "//div[contains(@class, 'Order_Checkboxes')]//label[text()='%s']/input";
    // Кнопка Заказать
    private final By order = xpath("//div[contains(@class, 'Order_Buttons')]//button[text()='Заказать']");
    // Кнопка да на модалке
    private final By yesButton = xpath("//div[contains(@class, 'Order_Modal')]//button[text()='Да']");
    // Заголовок модалки "Заказ оформлен"
    private final By modalOrderHeader = cssSelector("[class*='Order_Modal'] [class*='Order_ModalHeader']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillPersonalData(String name, String lastname, String address, String metro, String phone) {
        getInput("* Имя").sendKeys(name);
        getInput("* Фамилия").sendKeys(lastname);
        getInput("* Адрес: куда привезти заказ").sendKeys(address);
        getInput("* Станция метро").click();
        driver.findElement(xpath(format(metroItem, metro))).click();
        getInput("* Телефон: на него позвонит курьер").sendKeys(phone);
    }

    public void clickNext() {
        driver.findElement(next).click();
    }

    public WebElement getInput(String name) {
        return driver.findElement(xpath(format(input, name)));
    }

    public void fillRentDetails(String date, String term, String colorValue, String comment) {
        WebElement dateInput = getInput("* Когда привезти самокат");
        dateInput.sendKeys(date);
        dateInput.sendKeys(Keys.ENTER);
        driver.findElement(termDropdownExpander).click();
        driver.findElement(xpath(format(termDropdownItem, term))).click();
        driver.findElement(xpath(format(color, colorValue))).click();
        getInput("Комментарий для курьера").sendKeys(comment);
    }

    public void clickOrder() {
        driver.findElement(order).click();
    }

    public void confirmOrder() {
        driver.findElement(yesButton).click();
    }

    public String getModalHeaderText() {
        return driver.findElement(modalOrderHeader).getText();
    }

}
