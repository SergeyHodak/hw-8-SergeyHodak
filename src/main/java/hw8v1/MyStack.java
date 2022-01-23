package hw8v1;

/*
    Задание 4 - Stack#
    Написать свой класс MyStack как аналог классу Stack, который работает по принципу LIFO (last-in-first-out).

    Можно делать либо с помощью Node либо с помощью массива.

    Методы
    push(Object value) добавляет элемент в конец
    remove(int index) удаляет элемент под индексом
    clear() очищает коллекцию
    size() возвращает размер коллекции
    peek() возвращает первый элемент в стеке (LIFO)
    pop() возвращает первый элемент в стеке и удаляет его из коллекции
 */

public class MyStack<D> {
    //LIFO - Last In First Out. Магазин с патронами в автомате. Последний добавленный патрон будет израсходован первым.
    private Node<D> first; // первый элемент коллекции

    static class Node<D> { // Класс нода. То есть элемент коллекции
        private D value; // Хранимое значение в этом элементе, изначально null
        private Node<D> next; // Ссылка на следующий элемент, изначально null

        public D getValue() { // получить значение элемента
            return value; // вернуть значение поля value
        }

        public void setValue(D value) { // присвоить полю value значение
            this.value = value; // запись в поле value
        }

        public Node<D> getNext() { // получить информацию о ссылке на следующий элемент
            return next; // вернуть значение поля next
        }

        public void setNext(Node<D> next) { // присвоить полю next значение
            this.next = next; // запись в поле next
        }
    }

    public int size() { // метод возвращает размер коллекции
        if (first == null) { // если в переменной first не хранится ссылка на следующий элемент
            return 0; // вернуть инфу о том, что коллекция пуста
        }
        int count = 1; // счетчик
        Node<D> last = first; // переменная типа нода, присвоенная ссылка с первой ноды на следую
        while (last.getNext() != null) { //выполнять пока ссылка есть
            last = last.getNext(); // в переменную записать ссылку на следующую ноду
            count++; //повысить счетчик
        }
        return count; // отдать результат счетчика
    }

    @Override // метка переопределения метода
    public String toString() { // чтобы можно было посмотреть коллекцию
        if (size() == 0) { // если коллекция пустая
            return "{}"; //выдать пустое содержание в скобках
        }
        StringBuilder result = new StringBuilder("{"); //переменная типа стрингбилдер, с открывающейся фигурной скобкой
        Node<D> last = first; // переменной last типа Node<D> присвоить первый элемент коллекции
        result.append(last.getValue()).append(", "); // добавить элемент в строку, с запятой и пробелом в концу
        while (last.getNext() != null) { // выполнять цикл пока, не попадется что в ноде не указана следующая нода
            last = last.getNext(); // если ссылка есть, то записываем ее в переменную
            result.append(last.getValue()).append(", "); // добавить элемент в строку, с запятой и пробелом в концу
        }
        result = new StringBuilder(result.substring(0, result.length() - 2) + "}"); //удалить последнюю запитую и пробел и закрыть скобку
        return result.toString(); //выдать всю строку
    }

    public void push(D value) { // метод который, добавляет элемент в конец
        Node<D> node = new Node<>(); // создать новый экземпляр элемента коллекции
        node.setValue(value); // присвоить в значение этого экземпляра, переданное значение value
        if (first == null) { // если первый элемент был пустым
            first = node; // присвоить ему этот экземпляр
        } else { // Иначе. Коллекция уже не пустая
            Node<D> last = first; // переменной last типа Node<D> присвоить первый элемент коллекции
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
        int count = 0; // счетчик шага
        Node<D> node1 = first; Node<D> node2 = null; //хранилище нод, для того чтобы была возможность работы с предыдущей нодой
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
            super("Коллекция пустая, вызываемое действие не возможно выполнить"); //что будет написано
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

    public D peek() { //метод который, возвращает первый элемент в стеке (LIFO)
        if (first == null) { // если коллекция пустая
            throw new IndexOutOfBoundsException("Коллекция пустая"); //вызвать исключение, и выдать такой текст
        }
        Node<D> search = first; // создать временную переменную экземпляра ноды и присвоить ей первый элемент коллекции
        for (int i = 0; i < size()-1; i++) { // пробежка от начала до конца
            search = search.getNext(); // присваиваем следующий элемент в переменную
        }
        return search.getValue(); //выдать значение
    }

    public Object pop() throws ExceedingTheIndex, CollectionIsEmpty { // возвращает первый элемент в стеке и удаляет его из коллекции
        Object result = peek(); // сохранить в переменную удаляемый элемент
        remove(size()-1); // удалить первый элемент в стеке (LIFO)
        return result; // отдать, в точку вызова, удаленный элемент
    }

    public static void main(String[] args) throws ExceedingTheIndex, CollectionIsEmpty {
        MyStack<Integer> test = new MyStack<>(); // экземпляр стека
        System.out.println(test); // посмотреть коллекцию
        test.push(100); //добавить в конец коллекции элемент
        System.out.println(test); // посмотреть коллекцию
        test.push(200); test.push(800); test.push(4); test.push(152); //добавить в конец коллекции элементы
        System.out.println(test); // посмотреть коллекцию

        System.out.println("-----------------------------------------"); //разделитель между тестами

        System.out.println(test); // посмотреть коллекцию
        test.remove(2); // удалить 3й элемент с коллекции
        System.out.println(test); // посмотреть коллекцию
        //test.remove(20); // Удалить 20й элемент с коллекции. Вызовет исключение о том что вы вышли за границы коллекции

        System.out.println("-----------------------------------------"); //разделитель между тестами

        test.clear(); //очистить коллекцию
        System.out.println(test); // посмотреть коллекцию
        //test.remove(2); // удалить 3й элемент с коллекции. Вызовет исключение, так как коллекция пустая и с нее нет что удалять

        System.out.println("-----------------------------------------"); //разделитель между тестами

        System.out.println(test); // посмотреть коллекцию
        System.out.println(test.size()); // посмотреть размер коллекции
        test.push(2022); test.push(1900); test.push(0); test.push(360); //добавить в конец коллекции элементы
        System.out.println(test); // посмотреть коллекцию
        System.out.println(test.size()); // посмотреть размер коллекции

        System.out.println("-----------------------------------------"); //разделитель между тестами

        System.out.println(test.peek()); // посмотреть первый элемент в стеке (LIFO)

        System.out.println("-----------------------------------------"); //разделитель между тестами

        System.out.println(test); // посмотреть коллекцию
        System.out.println(test.pop()); // удалить первый элемент из коллекции и посмотреть значение удаленного
        System.out.println(test); // посмотреть коллекцию
    }
}