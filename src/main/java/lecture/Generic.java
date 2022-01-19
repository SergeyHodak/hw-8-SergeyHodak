package lecture;

import java.util.List;

public class Generic {}

/*
    Generic method
    Первое применение Generic - методы. Предположим, мы хотим вывести массив произвольных объектов в консоль.
    Тогда Generic метод будет выглядеть следующим образом:
 */

class TypeArrayPrinter {
    public <E> void printArray(E[] data) {
        for(E item: data) {
            System.out.println(item);
        }
    }
}

class TypeArrayPrinterTest {
    public static void main(String[] args) {
        String[] items = {"Hello", "Java"};
        Integer[] years = {2000, 3000};

        TypeArrayPrinter typeArrayPrinter = new TypeArrayPrinter();
        typeArrayPrinter.printArray(items);
        typeArrayPrinter.printArray(years);
    }
}
/*
В примере выше у метода printArray() есть типизированный параметр, объявленный в угловых скобках - <E>.
И сам метод printArray() принимает массив типа E. E - это произвольное название, мы сами придумываем его.
Обычно это большие буквы - E, T, V.
    ВНИМАНИЕ. Generic работает только с ссылочными типами, он не может работать с примитивами - int и т.д.
 */

/*
    Generic class
    Следующее применение generic - это обобщенные классы. Для этого после имени класса мы указываем в угловых
    скобках один или больше типизированных параметров. Например, вот так можно объявить класс, который хранит
    ключ и соответствующее этому ключу значение. При этом мы сами выбираем тип ключа, и тип значения:
*/
class KeyValuePair<K, V> {
    private K key;
    private V value;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return key + " = " + value;
    }
}

class KeyValueTest {
    public static void main(String[] args) {
        KeyValuePair<String, Integer> javaSalary = new KeyValuePair<String, Integer>();
        javaSalary.setKey("Java");
        javaSalary.setValue(5000);
        System.out.println(javaSalary);

        KeyValuePair<Integer, Float> yearAvgTemperature = new KeyValuePair<Integer, Float>();
        yearAvgTemperature.setKey(3200);
        yearAvgTemperature.setValue(15f);
        System.out.println(yearAvgTemperature);
    }
}
/*
Мы объявили класс KeyValuePair, у которого есть два типизированных параметра - K и V. Это тип ключа и
тип значения соответственно. Дальше, когда мы создаем объекты этого класса, мы указываем не только имя
класса KeyValuePair, а и указываем, какими типами параметров типизировать этот класс. В нашем случае
это String и Integer. Когда мы инициализируем объект (new KeyValuePair<String, Integer>()), мы тоже
указываем эти типы данных.
 */

/*
    Работа с подклассами - Extends
    Мы можем указать, что метод или класс может работать только с определенным набором классов.
    Для этого мы используем ключевое слово extends. Например, вот так мы можем объявить метод,
    который будет печатать массив чисел любого типа - Integer, Double, и т.д.
 */
class Test1 {
    public <E extends Number> void printNumbers(E[] array) {
        for(E item: array) {
            System.out.println(item);
        }
    }
}
/*
Если мы попытаемся передать в этот метод не числа любого типа, компилятор не позволит нам сделать это:
    String[] items = {"Hello", "World"};
    printNumbers(items); //Ошибка компиляции
Тип может иметь сразу несколько ограничений:
<T extends Number & Comparable>
 */

/*
    Wildcard
    Предположим, что у нас есть два класса: Transport, и его наследник Car.
    У них есть метод drive (который переопределен у наследника). Мы хотим создать метод, который будет принимать
    список с транспортом и запускать каждый транспорт (вызывать метод drive):

    public static void driveAllTransport(List<Transport> transports) {
        for(Transport transport : transports) {
            transport.drive();
        }
    }

    Однако если мы попытаемся передать в метод список машин, то получим ошибку компиляции. Почему?
    ВНИМАНИЕ
    Все объекты наследуются от Object, но коллекция объектов НЕ является прародителем коллекций всех объектов.
    То есть, утверждение, что List<String> extends List<Object> неверно!

    Для того, чтобы наш метод работал, нам нужно специальное средство языка - wildcard.
    Записывается с помощью символа ? в Java:
 */
class Test2 {
    public static void driveAllTransport(List<? extends Transport> transports) {
        for(Transport transport : transports) {
            transport.drive();
        }
    }
}
class Transport {
    public void drive() {};
}
/*
Пример выше читается так - "принимаем коллекцию элементов, тип элементов - любой, который унаследуется от Car".
 */

/*
    Нижняя граница иерархии
    Для wildcard можно установить нижнюю границу иерархии, то есть написать <? super T>.
    Это значит, что метод может работать только с классом T и его родителями. Классы-наследники T уже не подходят.

    Польза Generic
    Generic-типизация делает несколько полезных вещей:
        Статическая типизация на этапе компиляции. Вы описываете методы и классы, и указываете с какими именно
        типами данных работает ваш код. Если вы попытаетесь передать что-то не-то, ваша программа даже не
        скомпилируется. Раньше для похожих целей широко использовался класс Object, что приводило к интересным ошибкам.

        Обобщенное программирование. Мы можем писать алгоритмы для разных типов данных. Например, метод сортировки
        может принимать список элементов любого типа - строк, чисел и так далее. Стандартная библиотека Java во многом
        использует дженерики для реализации стандартных алгоритмов (сортировка, поиск минимального-максимального
        элементов и т.д.).
 */