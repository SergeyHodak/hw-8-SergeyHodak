package lecture;

import java.util.*;

public class ИнтерфейсMap {}

/*
    Интерфейс Map#
    Интерфейс Map находится в составе JDK c версии 1.2 и предоставляет разработчику базовые методы для работы с
    данными вида «ключ — значение». Так же как и Collection, он был дополнен дженериками (generic) в версии Java 1.5.
    В версии Java 8 появились дополнительные методы для работы с лямбдами. Есть несколько реализаций интерфейса Map.

    HashMap#
    Коллекция HashMap является альтернативой Hashtable. Ключевые отличия HashMap от Hashtable: HashMap не
    синхронизирована, и работает быстрее (но не потокобезопасна); HashMap позволяет использовать null в
    качестве ключа и значения. Пример использования HashMap:
*/
class Test10 {
    public static void main(String[] args) {
        Map<String, Integer> salaries = new HashMap<>();
        salaries.put("Java", 100500);

        System.out.println(salaries.get("Java")); //100500
    }
}
/*
HashMap не хранит данные в упорядоченном виде. Если мы будем поочередно добавлять данные, а потом попытаемся получить
их в том же порядке, в котором добавляли - так не получится. Порядок хранения элементов зависит от хеш-функции (метод
hashCode(), который мы рассматривали ранее).

    LinkedHashMap#
    LinkedHashMap — это упорядоченная реализация хэш-таблицы. Здесь, в отличие от HashMap, порядок итерирования равен
    порядку добавления элементов. В каком порядке мы добавляем элементы, в таком и считываем. Это получается благодаря
    двунаправленным связям между элементами. Каждый элемент имеет ссылку на текущий и следующий элемент (аналогично
    LinkedList). Из минусов такого подхода - увеличение памяти, которое занимает коллекция. Более подробная информация
    изложена в этой статье https://habr.com/ru/post/129037/. В остальном использование коллекции такое же, как и для
    HashMap:
*/
class Test11 {
    public static void main(String[] args) {
        Map<String, Integer> salaries = new LinkedHashMap();
        salaries.put("Java", 100500);

        System.out.println(salaries.get("Java")); //100500
    }
}
/*

    TreeMap#
    TreeMap — реализация Map основанная на красно-чёрных деревьях. Как и LinkedHashMap, коллекция TreeMap является
    упорядоченной. По умолчанию, коллекция сортируется по ключам с использованием принципа "natural ordering". Это
    поведение может быть настроено под конкретную задачу при помощи объекта Comparator, который указывается в качестве
    параметра при создании объекта TreeMap.
    ПОЛЕЗНО
    Ключевое отличие от HashMap - в HashMap значения хранятся в порядке добавления. В TreeMap при добавлении каждого
    объекта коллекция добавляет его в нужное место.

    Hashtable#
    -------------------------------------------------------------------------------------------------------------------
                                              <<<<interface>>>>
                                                     Map
                                                      ^
                                                      |
                               ---------------------------------------------------
                               |            |                 |                  |
                          WeakHashMap    HashMap      <<<<interface>>>>      Hashtable
                                            ^             SortedMap
                                            |                 ^
                                       LinkedHashMap          |
                                                       <<<<interface>>>>
                                                         NavigableMap
                                                              ^
                                                              |
                                                           TreeMap
    -------------------------------------------------------------------------------------------------------------------
    Hashtable — реализация такой структуры данных, как хэш-таблица. Она не позволяет использовать null в качестве
    значения или ключа. Эта коллекция была реализована раньше, чем Java Collection Framework, но в последствии была
    включена в его состав. Как и другие коллекции из Java 1.0, Hashtable является синхронизированной. Поэтому у нее
    есть проблемы со скоростью работы. Обычно вам не стоит ее использовать, вместо этого используйте другие реализации
    интерфейса Map. Она описана здесь, чтобы если вы встретите старый код, и там будет Hashtable, то вы понимали, что
    это структура данных ключ-значение, и реализация интерфейса Map;

    Работа с Map#
    Map удобно использовать в случаях, когда нам нужно хранить соответствие ключей и их значений. Например:
    должности и заработные платы; страны и население этих стран. Чтобы получить все ключи из Map, есть метод keySet().
    Чтобы получить все значения, есть метод values(). Добавляются значения методом put(key, value), получить элемент
    по ключу можно с помощью метода get(key). Вот пример, иллюстрирующий использование этих методов:
*/
class MapTest {
    public static void main(String[] args) {
        Map<String, Integer> salaries = new HashMap<>();
        salaries.put("Java", 5000);
        salaries.put("Frontend", 2000);

        //Все профессии
        System.out.println("All professions:");
        for(String profession: salaries.keySet()) {
            System.out.println(profession);
        }

        //Все зарплаты
        System.out.println("All salaries:");
        for(int salary: salaries.values()) {
            System.out.println(salary);
        }

        //Зарплата Java-разработчика
        System.out.println("Java salary is " + salaries.get("Java"));

    }
}
/*

    EnumSet and EnumMap#
    Мы можем хранить enum как ключи или значения в коллекциях:
*/

enum Season {
    Winter,
    Spring,
    Summer,
    Autumn
}

class SeasonTest {
    public static void main(String[] args) {
        Set<Season> seasons = new HashSet<>();
        seasons.add(Season.Winter); seasons.add(Season.Spring);

        Map<Season, String> descriptions = new HashMap<>();
        descriptions.put(Season.Winter, "Winter is coming");
        System.out.println(seasons); //[Winter]
        System.out.println(descriptions); //{Winter=Winter is coming}
    }
}
/*
Но для более эффективной работы можно использовать специальные коллекции для быстрой работы с enum.
Это EnumSet и EnumMap.
*/
//class Test12 {
//    public static void main(String[] args) {
//        EnumSet<DeveloperLevel> experiencedLevels =
//                new EnumSet.of(DeveloperLevel.MIDDLE, DeveloperLevel.SENIOR);
//        EnumMap<DeveloperLevel, List<Developer>> developers =
//                new EnumMap<DeveloperLevel, List<Developer>>(DeveloperLevel.class);
//    }
//}
/*
    ПОЛЕЗНО
    Использовать эти коллекции имеет смысл если быстродействие критично. Обычно хватает использования обычных коллекций
*/