package hw8v1;

/*
    Задание 2 - LinkedList#
    Написать свой класс MyLinkedList как аналог классу LinkedList.

    Нельзя использовать массив. Каждый элемент должен быть отдельным объектом-посредником(Node - нода)
    который хранит ссылку на предыдущий и следующий элемент коллекции (двусвязный список).

    Методы:
    add(Object value) добавляет элемент в конец
    remove(int index) удаляет элемент под индексом
    clear() очищает коллекцию
    size() возвращает размер коллекции
    get(int index) возвращает элемент под индексом
 */

public class MyLinkedList<B> {
    private Node<B> first; // первый элемент коллекции

    static class Node<B> { // Класс нода. То есть элемент коллекции
        private B value; // Хранимое значение в этом элементе, изначально null
        private Node<B> next; // Ссылка на следующий элемент, изначально null

        public B getValue() { // получить значение элемента
            return value; // вернуть значение поля value
        }

        public void setValue(B value) { // присвоить полю value значение
            this.value = value; // запись в поле value
        }

        public Node<B> getNext() { // получить информацию оо ссылке на следующий элемент
            return next; // вернуть значение поля next
        }

        public void setNext(Node<B> next) { // присвоить полю next значение
            this.next = next; // запись в поле next
        }
    }

    public void add(B item) { // метод который, добавляет элемент в конец
        Node<B> node = new Node<>(); // создать новый экземпляр элемента коллекции
        node.setValue(item); // присвоить в значение этого экземпляра, переданное значение item
        if (first == null) { // если первый элемент был пустым
            first = node; // присвоить ему этот экземпляр
        } else { // Иначе. Коллекция уже не пустая
            Node<B> last = first; // переменной last типа Node<B> присвоить первый элемент коллекции
            while (last.getNext() != null) { // выполнять цикл пока, не попадется что в ноде не указана следующая нода
                last = last.getNext(); // если ссылка есть, то записываем ее в переменную
            }
            last.setNext(node); // в последнем нет ссылки, записывает ссылку на наш новый экземпляр элемента
        }
    }

    public void remove(int index) throws CollectionIsEmpty, ExceedingTheIndex {// удаляет элемент под индексом
        if (first == null | size() == 0) { // если в переменной first не хранится ссылка на следующий элемент
            throw new CollectionIsEmpty(); // вернуть исключение, коллекция пуста
        }
        if (size() <= index || index < 0) { // если индекс больше чем элементов в коллекции, или индекс меньше нуля
            throw new ExceedingTheIndex(index, size()); // вернуть исключение, передаваемый индекс выходит за пределлы коллекции
        }
        if (size() == 1) { //если в коллекции только 1 элемент
            first = null; //записать как пустую ноду
            return;
        }

        int count = 0; // счетчик
        Node<B> node1 = first; Node<B> node2 = null; //хранилище нод, для того чтобы была возможность работы с предыдущей нодой
        while (node1.getNext() != null & count != index) { //выполнять пока, в ноде ссылка не пуста, и это еще не удаляемый индекс
            node2 = node1; node1 = node1.getNext(); // сохранить ссылки на ноды
            count++; // повысить счетчик шага
        }

        if (count == 0) { // если это первая нода
            first.setValue(node1.getNext().getValue()); // присвоить в первую ноду значение второй ноды
            first.setNext(node1.getNext().getNext()); // присвоить в первую ноду ссылку из второй ноды на третью
        } else { // иначе, это не первая нода
            node2.setNext(node1.getNext()); //в предыдущую ноду присвоить ссылку на следующую ноду из следующей ноды
        }
    }

    static class CollectionIsEmpty extends Exception{  //собственный класс унаследованный от исключений
        CollectionIsEmpty() { //конструктор класса
            super("Коллекция и так пуста, с нее невозможно удалить что-то"); //что будет написано
        }
    }

    static class ExceedingTheIndex extends Exception{  //собственный класс унаследованный от исключений
        ExceedingTheIndex(int index, int size) { //конструктор класса
            super("Переданный индекс = " + index + ", выходит за пределы коллекции, элементов в коллекции = " + size); //что будет написано
        }
    }

    public void clear() { // очищает коллекцию
        first = null; //записать как пустую ноду
    }

    public int size() { // метод возвращает размер коллекции
        if (first == null) { // если в переменной first не хранится ссылка на следующий элемент
            return 0; // вернуть инфу о том, что коллекция пуста
        }
        int count = 1; // счетчик
        Node<B> last = first; // переменная типа нода, присвоенная ссылка с первой ноды на следую
        while (last.getNext() != null) { //выполнять пока ссылка есть
            last = last.getNext(); // в переменную записать ссылку на следующую ноду
            count++; //повысить счетчик
        }
        return count; // отдать результат счетчика
    }

    public B get(int index) { //метод который, возвращает элемент под индексом
        if (first == null) { // если коллекция пустая
            throw new IndexOutOfBoundsException("Коллекция пустая"); //вызвать исключение, и выдать такой текст
        }
        Node<B> search = first; // создать временную переменную экземпляра ноды и присвоить ей первый элемент коллекции
        for (int i = 0; i < index; i++) { // пробежка от начала до индекса
            search = search.getNext(); // присваиваем следующий элемент в переменную
            if (search == null) { //если следующий элемент имел нулевую ссылку
                throw new IndexOutOfBoundsException("Неверный индекс: " + index + ", Размер коллекции: " + size()); //вызвать исключение, и выдать такой текст
            }
        }
        return search.getValue(); //выдать значение
    }

    @Override
    public String toString() { // чтобы можно было посмотреть коллекцию
        if (size() == 0) { // если коллекция пустая
            return "{}"; //выдать пустое содержание в скобках
        }
        StringBuilder result = new StringBuilder("{"); //переменна типа стрингбилдера, с открывающейся фигурной скобкой
        for (int i = 0; i < size(); i++) { // пробежка по коллекции
            result.append(get(i)).append(", "); // добавить элемент в строку, с запятой и пробелом в концу
        }
        result = new StringBuilder(result.substring(0, result.length() - 2) + "}"); //удалить последнюю запитую и пробел и закрыть скобку
        return result.toString(); //выдать всю строку
    }


    public static void main(String[] args) throws ExceedingTheIndex, CollectionIsEmpty {
        MyLinkedList<String> test = new MyLinkedList<>();
        System.out.println(test); //глянуть всю коллекцию
        test.add("one"); // добавить элемент в коллекцию
        System.out.println(test); //глянуть всю коллекцию
        test.add("two"); test.add("free"); test.add("four"); test.add("five"); // добавить элементы в коллекцию
        System.out.println(test); //глянуть всю коллекцию

        System.out.println("---------------------------"); //разделитель между тестами

        System.out.println(test); //глянуть всю коллекцию
        test.remove(0); //удалит первый элемент коллекции
        System.out.println(test); //глянуть всю коллекцию
        //test.remove(5); //выдаст исключение, индекс выходит за пределы коллекции
        //test.remove(0); test.remove(0); test.remove(0); test.remove(0); test.remove(0); // выдаст исключение о том, что коллекция пустая

        System.out.println("---------------------------"); //разделитель между тестами

        System.out.println(test); //глянуть всю коллекцию
        test.clear(); // очистим коллекцию
        System.out.println(test); //глянуть всю коллекцию
        //System.out.println(test.get(0)); // будет вызвано исключение о пустой коллекции

        System.out.println("---------------------------"); //разделитель между тестами

        System.out.println(test.size()); //узнаем сколько элементов в коллекции

        System.out.println("---------------------------"); //разделитель между тестами

        test.add("000"); test.add("555"); test.add("car"); test.add("off"); // добавить элементы в коллекцию
        System.out.println(test); //глянуть всю коллекцию
        System.out.println(test.get(1)); //вывести второй элемент
        //System.out.println(test.get(4)); //будет вызвано исключение, индекс выходит за пределы коллекции
        test.clear(); // очистим коллекцию
        //System.out.println(test.get(1)); // будет вызвано исключение о пустой коллекции
    }
}