package org.example.launch;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class TweakLauncher {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String[] newaArgs = {"org.example.launch.QuickLaunch"};
        com.jab125.tweakloader.Main.main(concatWithArrayCopy(newaArgs, args));
    }

    static <T> T[] concatWithArrayCopy(T[] array1, T[] array2) {
        T[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }
}
