package com.symbolic.pitchlab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 这个类提供了一系列关于音符转换的静态方法。
 */
public class music_translate {

    // 存储音符到音阶位置的映射，初始化为null，由m107a方法填充
    private static Map f3065b = null;

    // 两个字符串数组，存储了两种不同命名法的音符名称
    public static final String[][] f3064a = {new String[]{"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"}, new String[]{"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"}};

    /**
     * 将字符串表示的音符转换为对应的音高数值。
     *
     * @param str 待转换的音符字符串，例如 "C4"。
     * @return 音符的音高数值。
     */
    public static int m104a(String str) {
        // 分离音符和 octave 部分
        int i = 0;
        String str2 = "";
        while (!m106a(str.charAt(i))) {
            str2 = String.valueOf(str2) + str.charAt(i);
            i++;
        }
        String str3 = "";
        for (int i2 = i; i2 < str.length(); i2++) {
            str3 = String.valueOf(str3) + str.charAt(i2);
        }
        // 根据音符名称获取音阶位置，加上 octave 乘以 12
        return ((Integer) m107a().get(str2)).intValue() + (Integer.parseInt(str3) * 12);
    }

    /**
     * 私有方法，用于寻找字符串中第一个非音符字符的位置。
     *
     * @param str 待搜索的字符串。
     * @param i   开始搜索的位置。
     * @return 第一个非音符字符的位置。
     */
    private static int m103a(String str, int i) {
        while (i < str.length() && m101b(str.charAt(i))) {
            i++;
        }
        return i;
    }

    /**
     * 根据给定的音高数值和音阶（调性）返回音符的字符串表示。
     *
     * @param i    音高数值。
     * @param i2   音阶（调性）索引。
     * @return 音符的字符串表示，例如 "C4"。
     */
    public static String m105a(int i, int i2) {
        return String.format("%s%d", f3064a[i2][i % 12], Integer.valueOf(i / 12));
    }

    /**
     * 将一个音高数值数组转换为字符串表示，每个元素之间以空格分隔。
     *
     * @param iArr 音高数值数组。
     * @param i    音阶（调性）索引。
     * @return 音高数值数组的字符串表示，例如 "C4 E5 G4"。
     */
    public static String m102a(int[] iArr, int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 : iArr) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(m105a(i2, i));
        }
        return sb.toString();
    }

    /**
     * 私有方法，返回音符名称到音阶位置的映射表。
     *
     * @return 音符名称到音阶位置的映射表。
     */
    private static Map m107a() {
        if (f3065b == null) {
            f3065b = new HashMap();
            for (int i = 0; i < f3064a.length; i++) {
                for (int i2 = 0; i2 < 12; i2++) {
                    f3065b.put(f3064a[i][i2], Integer.valueOf(i2));
                }
            }
        }
        return f3065b;
    }

    /**
     * 判断给定字符是否为数字。
     *
     * @param c 待判断字符。
     * @return 如果字符为数字，返回true；否则返回false。
     */
    private static boolean m106a(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * 判断给定字符是否为空格。
     *
     * @param c 待判断字符。
     * @return 如果字符为空格，返回true；否则返回false。
     */
    private static boolean m101b(char c) {
        return c == ' ';
    }

    /**
     * 将包含音符名称和 octave 的字符串数组转换为对应的音高数值数组。
     *
     * @param str 待转换的字符串，例如 "C4 E5 G4"。
     * @return 对应的音高数值数组。
     */
    public static int[] m100b(String str) {
        // 分割并转换字符串
        int m103a = m103a(str, 0);
        ArrayList arrayList = new ArrayList();
        while (m103a < str.length()) {
            int i = m103a;
            while (i < str.length() && !m101b(str.charAt(i))) {
                i++;
            }
            arrayList.add(Integer.valueOf(m104a(str.substring(m103a, i))));
            m103a = m103a(str, i);
        }
        int[] iArr = new int[arrayList.size()];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr[i2] = ((Integer) arrayList.get(i2)).intValue();
        }
        return iArr;
    }
}
