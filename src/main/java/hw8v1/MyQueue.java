package hw8v1;

/*
    Задание 3 - Queue#
    Написать свой класс MyQueue как аналог классу Queue, который будет работать по принципу FIFO (first-in-first-out).

    Можно делать либо с помощью Node либо с помощью массива.

    Методы
    add(Object value) добавляет элемент в конец
    remove(int index) удаляет элемент под индексом
    clear() очищает коллекцию
    size() возвращает размер коллекции
    peek() возвращает первый элемент в очереди (FIFO)
    poll() возвращает первый элемент в очереди и удаляет его из коллекции
 */

import java.util.Arrays;

public class MyQueue<C> {
    //очередь в магазине
    private Object[] queue = new Object[]{}; //по создании класса создается пустой список

    public void add(C value) { // метод, который добавляет элемент в конец
        if (queue.length == 0) { //если очередь пустая
            queue = new Object[]{value}; // добавляем первого
        } else { // Иначе. Очередь не пустая, добавляем в конец очереди
            Object[] temp = queue; //временное хранилище
            queue = new Object[queue.length + 1]; // создаем новую очередь на 1го больше
            System.arraycopy(temp, 0, queue, 0, temp.length); // копия временного массива в новый
            queue[queue.length -1] = value; //записать последний элемент
        }
    }

    @Override //метка переопределение
    public String toString() { //переопределим метод вывода в консоль, при вызове класса
        return Arrays.toString(queue); //вывести в консоль очередь
    }

    public void remove(int index) throws ArrayIsEmptyn, ExceedingTheIndex { // метод, который удаляет элемент под индексом с очереди
        if (queue.length == 0) { //если очередь пустая
            throw new ArrayIsEmptyn(); // вызываем собственный класс ошибок
        } else if (index >= queue.length || index < 0) { //если мы пытаемся обратиться в несуществующий индекс очереди
            throw new ExceedingTheIndex(index, size()); // вызываем собственный класс ошибок
        }
        Object[] temp = queue; //сохраним имеющуюся очередь во временную переменную
        queue = new Object[queue.length -1]; //заменим существующую очередь на очередь пустых элементов с нужным их количеством
        int j = 0; // шагатель по временной переменной со старой очереди
        // с, старой очереди, с позиции в старой очереди = 0, в новую очередь, с позиции в новой очереди = 0,
        // количество элементов из старой очереди index
        System.arraycopy(temp, 0, queue, 0, index);
        // с, старой очереди, с позиции в старой очереди = index + 1, в новую очередь, с позиции в новой очереди = index,
        // количество элементов из старой очереди temp.length-(index+1)
        System.arraycopy(temp, index + 1, queue, index, temp.length - (index + 1));
    }

    static class ArrayIsEmptyn extends Exception{  //собственный класс унаследованный от исключений
        ArrayIsEmptyn() { //конструктор класса
            super("Операция не доступна, очередь ничего не содержит"); //что будет написано
        }
    }

    static class ExceedingTheIndex extends Exception{ //собственный класс унаследованный от исключений
        ExceedingTheIndex(int index, int count) { //конструктор класса
            super("Вызываемый индекс " + index + " для удаления, выходит за рамки существующей очереди, количество элементов в ней = " + count); //что будет написано
        }
    }

    public void clear() { //метод, очищает очередь
        queue = new Object[]{}; //заменить на пустую очередь
    }

    public int size() { //метод который, возвращает размер очереди
        return queue.length;  //размер очереди
    }

    public Object peek() throws ArrayIsEmptyn { // возвращает первый элемент в очереди (FIFO)
        if (size() == 0) { // если очередь пустая
            throw new ArrayIsEmptyn(); // вызываем собственный класс ошибок
        }
        return queue[0];
    }

    public Object poll() throws ArrayIsEmptyn, ExceedingTheIndex { // возвращает первый элемент в очереди и удаляет его из коллекции
        Object result = peek(); // сохранить первый элемент для отдачи результата
        remove(0); //удалит первый элемент
        return result; // отдаст удаленный элемент вместо вызова
    }

    public static void main(String[] args) throws ExceedingTheIndex, ArrayIsEmptyn {
        MyQueue<String> test = new MyQueue<>();
        System.out.println(test); // посмотреть очередь
        test.add("1"); // добавить в очередь
        System.out.println(test); // посмотреть очередь
        test.add("0"); // добавить в очередь
        System.out.println(test); // посмотреть очередь

        System.out.println("-----------------------------------------"); //разделитель между тестами

        test.add("25"); test.add("50"); test.add("770"); test.add("10"); // добавить в очередь
        System.out.println(test); // посмотреть очередь
        test.remove(4); //удалить 5й элемент
        System.out.println(test); // посмотреть очередь
        test.remove(0); //удалить 1й элемент
        System.out.println(test); // посмотреть очередь
        test.remove(3); //удалить 3й элемент
        System.out.println(test); // посмотреть очередь
        //test.remove(3); //удалить 3й элемент // вызовет исключение, выход за рамки очереди

        System.out.println("-----------------------------------------"); //разделитель между тестами

        test.clear(); //очистить очередь
        System.out.println(test); // посмотреть очередь

        System.out.println("-----------------------------------------"); //разделитель между тестами

        System.out.println(test.size()); // размер коллекции

        System.out.println("-----------------------------------------"); //разделитель между тестами

        //System.out.println(test.peek()); // Посмотреть первый элемент очереди. Вызовет исключение о пустой очереди
        test.add("25"); test.add("50"); test.add("770"); test.add("10"); // добавить в очередь
        System.out.println(test); // посмотреть очередь
        System.out.println(test.peek()); //посмотреть первый элемент очереди

        System.out.println("-----------------------------------------"); //разделитель между тестами

        System.out.println(test); // посмотреть очередь
        System.out.println(test.poll()); //удалит первый элемент и вернет его вместо вызова
        System.out.println(test); // посмотреть очередь
        //test.poll(); test.poll(); test.poll(); test.poll(); // вызовет исключение о пустой очереди
    }
}