import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;

public class CardFormTest {

    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иванов Евгений");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSubmitRequestWithDoubleSurname() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Римский-Корсаков Евгений");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldNameError() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Bill Snow");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldPhoneError() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иванов Евгений");
        $("[data-test-id=phone] input").setValue("79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldErrorIfNameEmpty() {
        open("http://localhost:9999");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldErrorIfPhoneEmpty() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иванов Евгений");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldErrorWithoutCheckbox() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иванов Евгений");
        $("[data-test-id=phone] input").setValue("+79578664311");
        $(".button").click();
        $("[data-test-id='agreement'].input_invalid").should(exist);
    }

    @AfterEach
    void closeWebBrowser() {
        closeWebDriver();
    }
}
