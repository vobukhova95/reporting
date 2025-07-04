import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        LocalDate date = LocalDate.now().plusDays(shift);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }

    public static String generateCity() {
        String[] cities = {"Москва", "Санкт-Петербург", "Севастополь", "Абакан", "Анадырь", "Архангельск",
                "Астрахань", "Барнаул", "Белгород", "Биробиджан", "Благовещенск", "Брянск", "Великий Новгород",
                "Владимир", "Владикавказ", "Владивосток", "Вологда", "Воронеж", "Горно-Алтайск", "Грозный",
                "Екатеринбург", "Иваново", "Ижевск", "Иркутск", "Йошкар-Ола", "Казань", "Калининград",
                "Калуга", "Кемерово", "Киров", "Кострома", "Краснодар", "Красноярск", "Курган", "Курск",
                "Кызыл", "Липецк", "Магадан", "Магас", "Майкоп", "Махачкала", "Нальчик", "Нарьян-Мар",
                "Нижний Новгород", "Новосибирск", "Омск", "Оренбург", "Орёл", "Пенза", "Пермь", "Петрозаводск",
                "Петропавловск-Камчатский", "Псков", "Пятигорск", "Ростов-на-Дону", "Рязань", "Салехард",
                "Самара", "Саранск", "Саратов", "Сыктывкар", "Тамбов", "Томск", "Тула", "Тюмень", "Улан-Удэ",
                "Ульяновск", "Уфа", "Хабаровск", "Ханты-Мансийск", "Чебоксары", "Челябинск", "Черкесск", "Чита",
                "Элиста", "Южно-Сахалинск", "Якутск", "Ярославль"};
        Random random = new Random();
        return cities[random.nextInt(cities.length)];
    }

    public static String generateName(Faker faker) {
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generatePhone(Faker faker) {
        return faker.numerify("+79#########");
    }


    public static class Registration {
        private static Faker faker;

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            faker = new Faker(new Locale(locale));
            String city = generateCity();
            String name = generateName(faker);
            String phone = generatePhone(faker);
            return new UserInfo(city, name, phone);

        }

        @Value
        public static class UserInfo {
            String city;
            String name;
            String phone;
        }
    }
}
