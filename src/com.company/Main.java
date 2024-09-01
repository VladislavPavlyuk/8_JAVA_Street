/*
С помощью классов и связей между ними описать улицу города. На улице должны присутствовать дома разного типа. Например,
- жилые дома,
- магазины,
- больницы,
- школы.
Предусмотреть тестовую инициализацию каждого объекта и улицы в целом.
Каждый дом должен иметь определенный адрес в рамках улицы.
Кроме того каждый тип дома должен иметь свою совокупность полей.
Например, должен быть тип магазина и перечень отделов в нем (например частный маленький магазин долже иметь 1 отдел,
супермаккет 5 отделов),
- количество жильцов в жилом доме,
- количество учащихся и уровень аккредитации в школе (общеобразовательная,
гимназия, лицей и т.д.). количество учащихся в школе привязать к уровню аккредитации и случайно выбирать в определенном диапазоне.
Предусмотреть возможность установки адреса для каждого объекта как через конструктор, так и через сеттер.

- Предусмотреть возможность добавления нового дома на улицу и удаления дома.
Также предусмотреть виртуальный метод, который будет принимать строку и на ее основе устанавливать поля объекта.
Сделать вывод информации о каждом доме в консоль.
Сделать вывод информации по улице в консоль.
Сделать метод, который для случайно выбранного жилого дома находит в заданной окрестности (определенное количество домов от адреса
дома) все магазины, имеющие отдел заданного типа.
Генерацию тестового типа сделать через использование статической фабрики.
Взаимодействие с пользователем сделать через меню, для этого предусмотреть отдельный класс.
В работе использовать интерфейсы, абстрактные классы (при необходимости) и перечисления.
При необходимости использовать механизм обработки исключений.
*/


package com.company;


    public class Main {
        public static void main(String[] args) {

            System.out.println("Hello street!");

            Street street1 = StreetFactory.createRandomPerson();
            person1.print();

            Person person2 = PersonFactory.createRandomPerson();
            person2.print();

            System.out.println("-".repeat(50));

            Person[] arrPerson = new Person[10];

            for (int i = 0; i < arrPerson.length; i++) {
                arrPerson[i] = PersonFactory.createRandomPerson();
            }

            for (int i = 0; i < arrPerson.length; i++) {
                System.out.println();
                arrPerson[i].print();
                System.out.println();
            }
           /* Street street = new Street();

            Residential house1 = new Residential(1, 4);
            Shop shop1 = new Shop(2, 1);
            School school1 = new School("125 Main St", 300, "High School");

            street.addBuilding(house1);
            street.addBuilding(shop1);
            street.addBuilding(school1);

            street.printStreetInfo();

            List<Shop> nearbyShops = street.findShopsNearResidentialBuilding(house1, 2, "Grocery");
            System.out.println("Nearby shops:");
            for (Shop shop : nearbyShops) {
                shop.printInfo();
            }*/


        }
    }