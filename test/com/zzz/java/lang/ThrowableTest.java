package com.zzz.java.lang;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * ThrowableTest
 */
public class ThrowableTest {

    private static final class MyInputStream extends ByteArrayInputStream {

        public MyInputStream(byte[] buf) {
            super(buf);
        }

        @Override
        public void close() throws IOException {
            super.close();
            throw new RuntimeException("Close Exception");
        }
    }

    private void suppressedTest() {
        try (ByteArrayInputStream bi = new MyInputStream(new byte[] {})) {
            throw new RuntimeException("I do it");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ThrowableTest test = new ThrowableTest();
        test.suppressedTest();
    }
}
