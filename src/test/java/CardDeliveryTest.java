import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    String returnDate(int daysFromToday) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, daysFromToday);
        return dateFormat.format(cal.getTime());
    }

    @BeforeEach
    void openWebSite() {
        open("http://localhost:7777");
    }

    @Test
    void shouldSuccess() {
        $("[data-test-id=city] .input__control").setValue("Санкт-Петербург");
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(returnDate(3));
        $("[data-test-id=name] .input__control").setValue("Иванов Иван");
        $("[name=phone]").setValue("+79111111111");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $(byText("Успешно!")).waitUntil(visible, 15000);
        $("[data-test-id=notification] .notification__content").
                shouldHave(exactText("Встреча успешно забронирована на " + returnDate(3)));

    }

    @Test
    void shouldSuccessComplexElements() {
        $("[data-test-id=city] .input__control").setValue("Са");
        $(byText("Самара")).click();
        $("[placeholder=\"Дата встречи\"]").click();
        for (int i = 0; i < 4; i++) {
            $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.DOWN, Keys.RIGHT);
        }
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.DOWN, Keys.ENTER);
        $("[data-test-id=name] .input__control").setValue("Иванов Иван");
        $("[name=phone]").setValue("+79111111111");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $(byText("Успешно!")).waitUntil(visible, 15000);
        $("[data-test-id=notification] .notification__content").
                shouldHave(exactText("Встреча успешно забронирована на " + returnDate(7)));

    }

}
