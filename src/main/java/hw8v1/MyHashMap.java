package hw8v1;

/*
    Задание 5 - HashMap#
    Написать свой класс MyHashMap как аналог классу HashMap.

    Нужно делать с помощью односвязной Node.
    Не может хранить две ноды с одинаковых ключами одновременно.

    Методы:
    put(Object key, Object value) добавляет пару ключ + значение
    remove(Object key) удаляет пару по ключу
    clear() очищает коллекцию
    size() возвращает размер коллекции
    get(Object key) возвращает значение(Object value) по ключу
 */

//TODO видеоролик, который помог понять, чего нужно достичь, при решении этой задачи https://youtu.be/DpHI6yRT6Es

public class MyHashMap<K, V> {
    private Node[] table = new Node[16]; // массив, по умолчанию из 16 пустых нодов

    static class Node<K, V> { // клас создающий ноды
        final int hash; // хеш текущего элемента
        final K key; // ключ текущего элемента
        V value; // значение текущего элемента
        Node<K, V> next; // ссылка на следующий узел в пределах одной корзины
        public Node(int hash, K key, V value, Node<K, V> next) { // конструктор для создания новой ноды
            this.key = key; // ключ
            this.value = value; // значение
            this.hash = hash; // хеш
            this.next = next; // ссылка на следующий узел в пределах одной корзины
        }
        public final K getKey() {return key;} // узнать ключ ноды
        public final V getValue() {return value;} //узнать значение ноды
        public final String toString() {return key + "=" + value;} //показать ключ-значение ноды
        public final void setValue(V newValue) {value = newValue;} // установить новое значение
    }

    public void put(Object key, Object value) { //добавляет пару ключ + значение
        int h; // изначально хеш пусть будет пустым, по дефолту нуль
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16); // если ключ не нул, воспользуемся стандартным хешь методом
        int n = table.length; // размер массива
        int buckets = hash % n; // получить индекс корзины для данных
        if (table[buckets] == null) { // в этой позиции пустая нода
            table[buckets] = new Node<Object, Object>(hash, key, value, null); // перезаписываем ее на наши данные
        } else { //иначе, в позиции уже есть минимум одна нода
            Node nexts = table[buckets]; //временная нода
            int count = 0; // счетчик сколько нодов в данной корзине
            while (nexts.next != null) { // выполнять пока ссылка на следующую ноду не пуста
                if (nexts.key == key) {break;} // если нода с таким ключем, уже есть
                nexts = nexts.next; // записать следующую ноду
                count++; //повысить счетчик
            }
            if (nexts.key == key) { // если нода с таким ключем, уже есть
                nexts.setValue(value); // перезаписать в нее значение с добавляемой
            } else { // иначе, такого ключа еще не было
                nexts.next = new Node<Object, Object>(hash, key, value, null); // создаем новую ноду, и отправляем на нее ссылку
            }

            if(count == 8){ // если в затронутой корзине уже 8 нодов
                collectionExpansion(); // расширить коллекцию
            }
        }
    }

    private void collectionExpansion() { // расширение коллекции
        Node[] old = table; // сохранить старую коллекцию
        table =  new Node[table.length * 2]; // создать новую пустую в два раза больше коллекцию
        //записать в новую коллекцию старую коллекцию
        for (int i = 0; i < old.length; i++) { // пробежка по старой коллекции
            if (old[i] == null) {continue;} //если нет ноды в позиции, перейти на следующую позицию
            Node nexts = old[i]; //временная нода
            put(nexts.getKey(), nexts.getValue()); //добавление ноды в новую коллекцию
            while (nexts.next != null) { //пока есть ссылка на следующую ноду
                nexts = nexts.next; // записать следующую ноду
                put(nexts.getKey(), nexts.getValue()); //добавление ноды в новую коллекцию
            }
        }
    }

    @Override //метка переопределения
    public String toString() { // посмотреть коллекцию мапы
        StringBuilder result = new StringBuilder("["); // для быстрой конкатенации стрингбилдер
        for (int i = 0; i < table.length; i++) { // пробежка мо массиву нод
            if (table[i] == null) {continue;} //если нет ноды в позиции
            Node nexts = table[i]; //временная нода
            result.append(nexts).append(", "); // внести ноду в результат
            while (nexts.next != null) { //пока есть ссылка на следующую ноду
                nexts = nexts.next; // записать следующую ноду
                result.append(nexts).append(", "); // внести ноду в результат
            }
        }
        String res; //для вывода
        if (result.toString().length() == 1) { //если в стрингбилдере всего 1 элемент
            res = result.append("]").toString(); // добавить закрывающуюся скобку
        } else { // иначе, значит коллекция не пустая была
            res = result.substring(0, result.length() - 2) + "]"; // удалить последние два символа и закрыть скобочку
        }
        return res; //возвращаем убрав два последних символа
    }

    public void remove(Object key) throws KeyNotFoundException { // удаляет пару по ключу
        //TODO еще бы добавить обработку по уменьшению корзин в зависимости от заполненности, если ниже 50%
        int h; // изначально хеш пусть будет пустым, по дефолту нуль
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16); // если ключ не нул, воспользуемся стандартным хешь методом
        int n = table.length; // размер массива
        int buckets = hash % n; // получить индекс корзины для данных
        if (table[buckets] == null) { // ели корзина пуста
            throw new KeyNotFoundException("Такого ключа нет в коллекции"); //вызвать исключение, и выдать такой текст
        }
        Node removable = table[buckets]; // корзина в которой нужно поискать удаляемого
        Node previousNode = null; // хранилище для предыдущей ноды
        if (removable.getKey() == key) { // если ключ ноды совпадает с удаляемым
            table[buckets] = removable.next; // шапка корзины станет не первый, а второй элемент
            return; // прекратить выполнение метода
        }
        while (removable.next != null) { // выполнять пока ссылка не пустая
            previousNode = removable; // запись в память предыдущей ноды
            removable = removable.next; // перешагнуть на следующую ноду
            if (removable.getKey() == key) { // если ключ ноды совпадает с удаляемым
                previousNode.next = removable.next; // в предыдущей ноде изменить ссылку через одну ноду
                return; // прекратить выполнение метода
            }
        }
        throw new KeyNotFoundException("Такого ключа нет в коллекции"); //вызвать исключение, и выдать такой текст
    }

    static class KeyNotFoundException extends Exception{  //собственный класс унаследованный от исключений
        KeyNotFoundException(String text) { //конструктор класса
            super(text); //что будет написано
        }
    }

    public void clear() { // очищает коллекцию
        table = new Node[16]; // присвоить пустую коллекцию
    }

    public int size() { // возвращает размер коллекции
        int result = 0; // счетчик нод
        for (int i = 0; i < table.length; i++) { // пробежка мо массиву нод
            if (table[i] == null) {continue;} //если нет ноды в позиции
            Node nexts = table[i]; //временная нода
            result++; // внести ноду в результат
            while (nexts.next != null) { //пока есть ссылка на следующую ноду
                nexts = nexts.next; // записать следующую ноду
                result++; // внести ноду в результат
            }
        }
        return result; //возвращаем убрав два последних символа
    }

    public Object get(Object key) throws KeyNotFoundException { // возвращает значение(Object value) по ключу
        int h; // изначально хеш пусть будет пустым, по дефолту нуль
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16); // если ключ не нул, воспользуемся стандартным хешь методом
        int n = table.length; // размер массива
        int buckets = hash % n; // получить индекс корзины для данных
        if (table[buckets] == null) { // ели корзина пуста
            throw new KeyNotFoundException("Такого ключа нет в коллекции"); //вызвать исключение, и выдать такой текст
        }
        Node result = table[buckets]; // корзина в которой нужно поискать ключ для выдачи значения
        if (result.getKey() == key) { // если ключ ноды совпадает с искомым
            return result.getValue(); // отдать результат
        }
        while (result.next != null) { // выполнять пока ссылка не пустая
            result = result.next; // перешагнуть на следующую ноду
            if (result.getKey() == key) { // если ключ ноды совпадает с искомым
                return result.getValue(); // отдать результат
            }
        }
        throw new KeyNotFoundException("Такого ключа нет в коллекции"); //вызвать исключение, и выдать такой текст
    }

    public static void main(String[] args) throws KeyNotFoundException { // запускалка для тестов с интеграцией исключений
        MyHashMap<String, Integer> test = new MyHashMap<>(); // создать экземпляр коллекции мап
        System.out.println(test); // посмотреть на пустую коллекцию
        test.put("A", 1); test.put("B", 2); test.put("C", 3); test.put("D", 4); test.put("E", 5); test.put("F", 6);
        test.put("G", 7); test.put("H", 8); test.put("I", 9); test.put("J", 10); test.put("K", 11); test.put("L", 12);
        test.put("M", 13); test.put("N", 14); test.put("O", 15); test.put("P", 16); // заполнили коллекцию по 1му элементу
        System.out.println(test); // глянуть на коллекцию
        test.put("Q", 17); test.put("R", 18); //добавить в один и тот же индекс по одному ноду
        test.put("A", 555); // добавить ноду с уже существующим ключем, будет перезапись значения в существующей ноде
        System.out.println(test); // глянуть на коллекцию

        //задача заставить расшириться коллекцию
        test.put("S", 19); test.put("T", 20); test.put("U", 21); test.put("V", 22); test.put("W", 23); test.put("X", 24);
        test.put("Y", 25); test.put("Z", 26);

        test.put("AA", 27); test.put("AB", 28); test.put("AC", 29); test.put("AD", 30); test.put("AE", 31); test.put("AF", 32);
        test.put("AG", 33); test.put("AH", 34); test.put("AI", 35); test.put("AJ", 36); test.put("AK", 37); test.put("AL", 38);
        test.put("AM", 39); test.put("AN", 40); test.put("AO", 41); test.put("AP", 42); test.put("AQ", 43); test.put("AR", 44);
        test.put("AS", 45); test.put("AT", 46); test.put("AU", 47); test.put("AV", 48); test.put("AW", 49); test.put("AX", 50);
        test.put("AY", 51); test.put("AZ", 52);

        test.put("BA", 53); test.put("BB", 54); test.put("BC", 55); test.put("BD", 56); test.put("BE", 57); test.put("BF", 58);
        test.put("BG", 59); test.put("BH", 60); test.put("BI", 61); test.put("BJ", 62); test.put("BK", 63); test.put("BL", 64);
        test.put("BM", 65); test.put("BN", 66); test.put("BO", 67); test.put("BP", 68); test.put("BQ", 69); test.put("BR", 70);
        test.put("BS", 71); test.put("BT", 72); test.put("BU", 73); test.put("BV", 74); test.put("BW", 75); test.put("BX", 76);
        test.put("BY", 77); test.put("BZ", 78);

        test.put("CA", 79); test.put("CB", 80); test.put("CC", 81); test.put("CD", 82); test.put("CE", 83); test.put("CF", 84);
        test.put("CG", 85); test.put("CH", 86); test.put("CI", 87); test.put("CJ", 88); test.put("CK", 89); test.put("CL", 90);
        test.put("CM", 91); test.put("CN", 92); test.put("CO", 93); test.put("CP", 94); test.put("CQ", 95); test.put("CR", 96);
        test.put("CS", 97); test.put("CT", 98); test.put("CU", 99); test.put("CV", 100); test.put("CW", 101); test.put("CX", 102);
        test.put("CY", 103); test.put("CZ", 104);

        test.put("DA", 105); test.put("DB", 106); test.put("DC", 107); test.put("DD", 108); test.put("DE", 109); test.put("DF", 110);
        test.put("DG", 111); test.put("DH", 112); test.put("DI", 113); test.put("DJ", 114); test.put("DK", 115); test.put("DL", 116);
        test.put("DM", 117); test.put("DN", 118); test.put("DO", 119); test.put("DP", 120); test.put("DQ", 121); test.put("DR", 122);
        test.put("DS", 123); test.put("DT", 124);

        System.out.println(test); // глянуть на коллекцию
        test.put("DU", 125); // эта строчка вызовет расширение коллекции
        System.out.println(test); // глянуть на коллекцию, расширилась и отсортировалась по новому

        System.out.println("----------------------------------------------"); //разделитель между тестами

        test.remove("DU"); // убрать ноду по ключу
        System.out.println(test); // глянуть на коллекцию

        System.out.println("----------------------------------------------"); //разделитель между тестами

        test.clear(); // очистим коллекцию
        System.out.println(test); // глянуть на коллекцию

        System.out.println("----------------------------------------------"); //разделитель между тестами

        System.out.println(test.size()); // глянуть размер коллекции

        System.out.println("----------------------------------------------"); //разделитель между тестами

        test.put("DS", 123); test.put("DT", 124); // добавить парочку ключ-значений
        System.out.println(test.get("DT")); // получить значение по ключу
    }
}