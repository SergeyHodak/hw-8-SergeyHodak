package drugieStudentu;

public class Test1 {
    static class MzArrayList1<T> {
        private final int INIT_SIZE = 16;
        private Object[] array = new Object[INIT_SIZE];
        private int pointer = 0;

        public void add(T item) {
            if (pointer == array.length - 1)
                resize(array.length * 2);
            array[pointer++] = item;
        }

        public T get(int index) {
            return (T) array[index];
        }

        public void remove(int index) {
            for (int i = index; i < pointer; i++) {
                array[pointer] = null;
                pointer--;
            }
            int CUT_RATE = 4;
            if (array.length > INIT_SIZE && pointer < array.length / CUT_RATE) {
                resize(array.length / 2);
            }
        }

        public int size() {
            return pointer;
        }

        public void clear() {
            for (int i = 0; i < pointer; i++)
                array[i] = null;
            pointer = 0;
        }

        private void resize(int newLength) {
            Object[] newArray = new Object[newLength];
            System.arraycopy(array, 0, newArray, 0, pointer);
            array = newArray;
        }

        public static void main(String[] args) { //запускалка для тестов, с интеграцией исключений
            Test1.MzArrayList1<Object> test = new Test1.MzArrayList1<>(); //создаю экземпляр моего класса с домашней работы
            System.out.println(test); // смотрю на то как выглядит массив при создании экземпляра
            test.add(1); //добавляю первый элемент
            System.out.println(test); // смотрю что происходит с массивом
            test.add(2);
            test.add(3);
            test.add(4); //добавляю несколько элементов
            System.out.println(test); // смотрю что происходит

            System.out.println("----------------------------------------------"); //разделитель между тестами

            Test1.MzArrayList1<Object> test1 = new Test1.MzArrayList1<>(); //создаю 2й экземпляр моего класса с домашней работы
            //test1.remove(1); // тест как себя поведет, если массив пуст. Вызовет исключение
            test1.add(2);
            test1.add("data");
            test1.add(4); // немножко заполним массив
            System.out.println(test1); // смотрим добавленные
            test1.remove(1); // удаляем второй элемент
            System.out.println(test1); // смотрим результат удаления
            //test1.remove(3); // удаляем 4й элемент //вызовет исключение

            System.out.println("----------------------------------------------"); //разделитель между тестами

            test.clear();
            test1.clear(); //очищаем массивы
            System.out.println(test); // смотрю результат
            System.out.println(test1); // смотрю результат

            System.out.println("----------------------------------------------"); //разделитель между тестами

            Test1.MzArrayList1<Object> test2 = new Test1.MzArrayList1<>(); //создаю 3й экземпляр моего класса с домашней работы
            test2.add("car");
            test2.add("Data");
            test2.add(10004); // немножко заполним массив
            System.out.println(test2.size()); //смотри размер коллекции

            System.out.println("----------------------------------------------"); //разделитель между тестами
            System.out.println(test2.get(0)); //выведет в консоль 1й элемент
            System.out.println(test2.get(1)); //выведет в консоль 2й элемент
            System.out.println(test2.get(2)); //выведет в консоль 3й элемент
            //System.out.println(test2.get(3)); // вызовет исключение связанное с несуществующим индексом в коллекции
        }
    }
}