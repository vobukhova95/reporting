import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {


    @BeforeAll
    static void setUpAll() {
        // Добавляем листенер в тестовый класс перед выполнением всех тестов
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        // Удаляем листенер после выполнения всех тестов
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] .input__control")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("label[data-test-id='agreement']").click();
        $("form .button").click();
        $(Selectors.byText("Успешно!"))
                .shouldHave(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate), Condition.visible);
        $("[data-test-id='date'] .input__control")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(secondMeetingDate);
        $("form .button").click();
        $("[data-test-id='replan-notification'] .notification__title")
                .should(Condition.and("text and visible", Condition.exactText("Необходимо подтверждение"), Condition.visible), Duration.ofSeconds(15));
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldHave(Condition.exactText("У вас уже запланирована встреча на другую дату. Перепланировать? Перепланировать"), Condition.visible);
        $x("//button[contains(., 'Перепланировать')]").click();
        $(Selectors.byText("Успешно!"))
                .shouldHave(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate), Condition.visible);

    }

}
