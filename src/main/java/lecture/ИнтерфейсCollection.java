package lecture;

import java.util.*;

public class ИнтерфейсCollection {}

/*
    Интерфейс Collection находится в составе JDK c версии 1.2 и определяет основные методы работы с простыми
    наборами элементов, которые будут общими для всех его реализаций (например size(), isEmpty(), add(E e) и др.).
    Интерфейс был слегка доработан с приходом Generic.

    ВНИМАНИЕ
    Также, в версии Java 8, было добавлено несколько новых методов для работы с лямбдами - такие как stream(),
    parallelStream(), removeIf() и др.). Мы рассмотрим эти методы детальней, когда познакомимся с лямбдами.

    List#
    ___________________________________________________________________________________________________________________
                                                                     <<<<interface>>>>
                                 <<<<interface>>>>                          Queue
                                        List                                  ^
                                         ^                                    |
                                         |                            <<<<interface>>>>
                               _______________________                       Deque
                               |         |           |                         ^
                            Vector   ArrayList   LinkedList ___________________|
                               ^
                               |
                             Stack
    ___________________________________________________________________________________________________________________
    Реализации этого интерфейса представляют собой упорядоченные коллекции. Можете представить его себе как массив,
    который может расширяться. Вы можете доступаться к элементам по индексу, и по значению.

    ArrayList#
    ArrayList - одна из наиболее популярных коллекций. Это динамический массив объектов, позволяет хранить
    любые данные. Внутрення реализация основана на обычном массиве.
    Вот пример использования ArrayList:
*/
class Test3 {
    public static void main(String[] args) {
        List<String> names = new ArrayList<String>();
        names.add("John");
        names.add("Bill");

        System.out.println(names.get(0)); //John
        System.out.println(names.get(1)); //Bill
    }
}
/*
ArrayList отлично работает, если вы часто доступаетесь к элементам коллекции по индексу, и добавляете элементы
в конец коллекции. Теоретически эта коллекция чуть хуже работает, если вы удаляете-добавляете элементы в середину
массива. На практике она отлично работает и в этом случае. Более детально про ArrayList можно почитать
здесь - https://habr.com/ru/post/128269/.

    LinkedList#
    LinkedList — ещё одна реализация List. Основное отличие - внутрення реализация основана на двунаправленном
    связном списке, что теоретически позволяет быстрее вставлять-удалять элементы из середины очереди. Эту коллекцию,
    из-за ее особенностей внутренней реализации, можно использовать как стек или очередь. На Хабре есть статья с
    подробным анализом и описанием этой коллекции.

    Stack и Vector#
    Эти коллекции уже не используются на практике, но еще могут встретиться в старом коде. Vector — реализация
    динамического массива объектов. Появился в Java 1.0, сейчас не рекомендуется использовать его. Как
    альтернативу используйте ArrayList. Stack — данная коллекция является расширением коллекции Vector. Была
    добавлена в Java 1.0 как реализация стека LIFO (last-in-first-out). Сейчас не используется, рекомендуется
    использовать вместо этого реализации интерфейса Deque (например, ArrayDeque).

    Работа с List#
    Обычно List используется как удобная альтернатива массиву. Не нужно следить за размером, просто удобно работаем,
    а нужная память выделяется автоматически. Наиболее частая операция - проход по всем элементам. Обычно реализуется
    циклом foreach, но можно реализовать и циклом со счетчиком:
*/
class Test4 {
    public static void main(String[] args) {
        List<String> planets = new ArrayList<>();
        planets.add("Earth");
        planets.add("Mars");
        planets.add("Venus");

        //Используем цикл foreach
        for(String planet: planets) {
            System.out.println(planet); //"Earth" \n "Mars" \n "Venus"
        }

        //Используем цикл for со счетчиком
        for(int i = 0; i < planets.size(); i++) {
            String planet = planets.get(i);
            System.out.println(planet); //"Earth" \n "Mars" \n "Venus"
        }
        System.out.println(planets.size());
    }
}
/*
В общем случае использование цикла foreach предпочтительней, так код понятней, короче, и меньше возможностей ошибиться.
Для получения элементов массиве есть метод get(), который принимает индекс элемента:
*/
class Test5 {
    public static void main(String[] args) {
        List<String> planets = new ArrayList<>();
        planets.add("Earth");
        planets.add("Mars");
        planets.add("Venus");

        //Первый элемент
        System.out.println(planets.get(0));

        //Последний элемент
        System.out.println(planets.get(planets.size()-1));
    }
}

/*
    Set#
    ___________________________________________________________________________________________________________________
                              <<<<interface>>>>                        <<<<interface>>>>
                                     Set         <-------------------      SortedSet
                                      ^                                        ^
                                      |                                        |
                             _______________________                           |
                             |                     |                   <<<<interface>>>>
                          HashSet               TreeSet  ------------->   NavigableSet
                             ^
                             |
                        LinkedHashSet
    ___________________________________________________________________________________________________________________
    Представляет собой неупорядоченную коллекцию, которая не может содержать дублирующиеся данные. Является программной
    моделью математического понятия «множество».

    HashSet#
    HashSet — реализация интерфейса Set, базирующаяся на HashMap.
    Внутри использует объект HashMap для хранения данных. В качестве ключа используется добавляемый элемент,
    а в качестве значения — объект-пустышка (new Object()). Из-за особенностей реализации порядок элементов
    не гарантируется при добавлении. Пример использования HashSet (для других реализаций Set подход аналогичный):
 */
class Test6 {
    public static void main(String[] args) {
        Set<Integer> numbers = new HashSet<>();
        numbers.add(100);
        numbers.add(100);

        System.out.println(numbers.size()); //1
    }
}
/*
В примере выше мы два раза добавили число 100 во множество, но оно добавилось лишь один раз, и размер коллекции - 1.

    LinkedHashSet#
    LinkedHashSet отличается от HashSet только тем, что в основе лежит LinkedHashMap вместо HashMap.
    Благодаря этому отличию порядок элементов при обходе коллекции является идентичным порядку добавления элементов.

    Работа с Set#
    Set - это множество без повторов. Значит, с его помощью удобно, например, получить список уникальных элементов.
*/
class Test7 {
    public static void main(String[] args) {
        String[] allNames = {"John", "Elon", "Bill"};
        //String[] allNames = {"John Dou", "Elon Musk", "John Bew", "Bill Cory", "Bill Elot"};
        Set<String> uniqueNames = new HashSet<>();
        for(String name: allNames) {
            uniqueNames.add(name);
        }

        for(String name: uniqueNames) {
            System.out.println(name); //John, Elon, Bill
        }
    }
}
/*
В примере выше в множестве uniqueNames останутся лишь строки John, Elon и Bill, причем их порядок не определен
(зависит от реализации hashCode() у класса String, может отличаться от одной виртуальной машины к другой).

Пройтись по всем элементам мы можем лишь с помощью цикла foreach. Метода get(), который возвращал бы элемент
по индексу, здесь нет. Часто нужно узнать, есть ли элемент во множестве. Для этого есть метод contains():
*/
class Test8 {
    public static void main(String[] args) {
        Set<Integer> prices = new HashSet<>();
        prices.add(99);

        System.out.println(prices.contains(99)); //true;
        System.out.println(prices.contains(0)); //false
    }
}
/*

    Queue#
    ___________________________________________________________________________________________________________________
                                                    <<<<interface>>>>
                                       -------------->    Queue
                                       |                    ^
                                       |                    |
                                   LinkedList        <<<<interface>>>>
                                                          Deque
                                                            ^
                                                            |
                                                        ArrayDeque
    ___________________________________________________________________________________________________________________
    Этот интерфейс описывает коллекции с предопределённым способом вставки и извлечения элементов, а именно
    — очереди FIFO (first-in-first-out). Помимо методов, определённых в интерфейсе Collection, интерфейс Queue
    определяет дополнительные методы для извлечения и добавления элементов в очередь. Большинство реализаций
    данного интерфейса находится в пакете java.util.concurrent и подробно рассматриваются в данном обзоре.

    ПОЛЕЗНО
    Queue работает как очередь в магазине. Элементы обслуживаются в порядке их поступления.
    Кто первый вошел, того первым и обслужат.

    ArrayDeque#
    ArrayDeque — реализация интерфейса Deque, который расширяет интерфейс Queue методами, позволяющими реализовать
    конструкцию вида LIFO (last-in-first-out). Интерфейс Deque и реализация ArrayDeque были добавлены в Java 1.6.
    Эта коллекция представляет собой реализацию с использованием массивов, подобно ArrayList, но не позволяет
    обращаться к элементам по индексу и хранение null. Как заявлено в документации, коллекция работает быстрее
    чем Stack, если используется как LIFO коллекция, а также быстрее чем LinkedList, если используется как FIFO.

    АНАЛОГИИ ИЗ ЖИЗНИ
    FIFO - First In First Out. Первый вошел, первый вышел. Очередь в магазине. Тот, кто стоит в очереди первый, первый будет обслужен.
    LIFO - Last In First Out. Магазин с патронами в автомате. Последний добавленный патрон будет израсходован первым.

    Пример работы с очередью:
*/
class DequeTest {
     public static void main(String[] args) {
        Deque<String> names = new ArrayDeque<>();
        names.add("John");
        names.add("Bill");

        System.out.println(names.poll()); //John
        System.out.println(names.poll()); //Bill
    }
}
/*
В примере выше мы добавляем элементы в очередь, используя метод add(). Дальше с помощью метода poll()
мы получаем последний элемент, одновременно удаляя его.

    PriorityQueue#
    PriorityQueue является единственной прямой реализацией интерфейса Queue. Особенность PriorityQueue - сортировка
    элементов при их добавлении. По умолчанию используется natural ordering. Но мы можем также передать свой Comparator,
    и изменить порядок сортировки.

    Работа с Queue#
    Типичное использование Queue - организация очереди задач. Например, у нас есть сайт с картинками. Когда
    пользователь добавляет картинку на сайт, ее вначале нужно обработать (обрезать, переконвертировать в нужный
    формат и так далее). Это трудоемкий процесс, и при добавлении картинок мы помещаем каждую в очередь. Дальше
    фоновый поток, берет каждую картинку, обрабатывает, и добавляет на сайт. Примерно так работает Youtube,
    когда вы добавляете видео.
 */
class Test9 {
    static class Photo {
        public void convert() {}
        public void addToSite() {}
    }

    public static void main(String[] args) {
        Queue<Photo> photoQueue = new ArrayDeque<>();

        //Добавляем много картинок
        for(int i = 0; i < 1000; i++) {
            photoQueue.add(new Photo());
        }

        while(!photoQueue.isEmpty()) {
            Photo photo = photoQueue.poll();
            photo.convert();
            photo.addToSite();
            System.out.println(photoQueue);
        }
    }
}
/*
Использование очереди в данном случае удобно тем, что мы обрабатываем фотографии в порядке их поступления.
Чем раньше добавлена фотография, тем раньше она обработается и добавится на сайт, что вполне логично.
 */