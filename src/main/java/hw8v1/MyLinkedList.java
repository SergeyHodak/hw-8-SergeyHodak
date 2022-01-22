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
    private Node<B> first; // первый элемент массива

    static class Node<B> { // Класс нода. То есть элемент массива
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
        Node<B> node = new Node<>(); // создать новый экземпляр элемента массива
        node.setValue(item); // присвоить в значение этого экземпляра, переданное значение item
        if (first == null) { // если первый элемент был пустым
            first = node; // присвоить ему этот экземпляр
        } else { // Иначе. Массив уже не пустой
            Node<B> last = first; // переменной last типа Node<B> присвоить первый элемент массива
            System.out.println(last.getNext() + " 1");
            while (last.getNext() != null) { // выполнять цикл пока, не попадется что в ноде не указана следующая нода
                System.out.println(last.getNext() + " 2");
                last = last.getNext(); // если ссылка есть, то меняем ее на следующую
            }
            last.setNext(node); // в последнюю записывает ссылку на наш новый экземпляр элемента
        }
    }

    public static void main(String[] args) {
        MyLinkedList<String> test = new MyLinkedList<>();
        System.out.println(test + " " + test.first);
        test.add("one");
        System.out.println(test + " " + test.first);
        test.add("one");
        System.out.println(test + " " + test.first);
    }
}