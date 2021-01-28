package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MainCollection {
    public static void main(String[] args) {

        List<Resume> list = new LinkedList<>();
        for (int i = 30; i >= 0; i--) {
            list.add(new Resume("Name" + i));
        }

        for (int i = 30; i >= 0; i--) {
            list.add(new Resume("Name" + i));
        }



        System.out.println(list.toString());

        Set<Resume> set = new HashSet<>(list);
        System.out.println(set);

        Map<String, Resume> map = new HashMap<>();
        Resume r1 = new Resume("Asap");
        Resume r2 = new Resume("Jack");
        Resume r3 = new Resume("John");
        map.put(r1.getUuid(), r1);
        map.put(r2.getUuid(), r2);
        map.put(r3.getUuid(), r3);
        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }

    }
}
