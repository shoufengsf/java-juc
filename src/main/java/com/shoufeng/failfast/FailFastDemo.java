package com.shoufeng.failfast;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * fail-fast
 * final void checkForComodification() {
 *             if (modCount != expectedModCount)
 *                 throw new ConcurrentModificationException();
 *         }
 * @author shoufeng
 */
public class FailFastDemo {
    public static void main(String[] args) {
//        CopyOnWriteArrayList<Person> list = new CopyOnWriteArrayList<>();
        ArrayList<Person> list = new ArrayList<>();
        list.add(new Person("zhangsan", 3));
        list.add(new Person("lisi", 2));
        list.add(new Person("wangwu", 1));
        list.add(new Person("renliu", 4));
        list.add(new Person("wuqi", 1));
        list.add(new Person("xx", 7));
        for (Person person : list) {
            if (person.age == 1) {
                list.remove(person);
            }
        }
        System.out.println(list.toString());
    }
}

class Person {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
