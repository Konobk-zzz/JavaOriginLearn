package com.zzz.java.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * IOTest
 */
public class IOTest {

    private static final String FILE_NAME = "C:\\Users\\YS\\Desktop\\io.log";

    private static void printWriterTest() {
        FileWriter fo = null;
        try {
//            PrintWriter pw = new PrintWriter(new FileWriter(new File("C:\\Users\\YS\\Desktop\\io.log"), true));
//            fo = new FileWriter(new File("C:\\Users\\YS\\Desktop\\io.log"), true);
//            fo.write("[fo]");
//            fo.flush();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(FILE_NAME)));
            oos.writeObject(new Entity());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveObj2File(Object obj, File file) {
        if (obj == null || file == null || !file.exists()) {
            throw new IllegalArgumentException();
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Object readObjFromFile(File file) {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException();
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        File file = new File(FILE_NAME);
//        saveObj2File(new Entity(), file);
        readObjFromFile(file);
//        printWriterTest();
    }

    public static class Entity implements Serializable {
        public String name;
        public String age;
    }
}
