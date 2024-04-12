package com.symbolic.pitchlab;


import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/***
 * 调音相关的方法和数据结构
 */
public class Tuna {

    // 静态变量，故意混淆命名
    static final /* synthetic */ boolean f3093a;

    // 私有静态列表，存储某种数据对象
    private static List f3094d;

    // 私有字符串变量，用于存储某种标志或者名称
    private String f3095b;

    // 私有浮点数组，表示一系列的浮点数据
    private float[] f3096c;

    // 静态代码块，初始化静态变量
    static {
        f3093a = !Tuna.class.desiredAssertionStatus();
        f3094d = null;
    }

    // 多参数构造函数，接受一个字符串和多个双精度浮点数
    public Tuna(String str, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10, double d11, double d12) {
        this.f3095b = str;
        this.f3096c = new float[]{m35a(d), m35a(d2), m35a(d3), m35a(d4), m35a(d5), m35a(d6), m35a(d7), m35a(d8), m35a(d9), m35a(d10), m35a(d11), m35a(d12)};
    }

    // 使用字符串和浮点数组构造对象的构造函数
    public Tuna(String str, float[] fArr) {
        this.f3095b = str;
        if (!f3093a && fArr.length != 12) {
            throw new AssertionError(); // 断言，用于检查传递的浮点数组长度是否为12
        }
        this.f3096c = (float[]) fArr.clone(); // 克隆传入的数组
    }

    // 将double类型的数值转换成float类型的数值
    private static float m35a(double d) {
        return (float) d;
    }

    // 根据名称从列表中找到对应的对象
    public static Tuna m29a(List list, String str) {
        int i = 0;
        // 遍历列表
        while (true) {
            int i2 = i;
            if (i2 >= list.size()) {
                return null; // 如果未找到，则返回null
            }
            // 检查名称是否相同，如果相同，则返回该对象
            if (((Tuna) list.get(i2)).m36a().compareTo(str) == 0) {
                return (Tuna) list.get(i2);
            }
            i = i2 + 1;
        }
    }

    // 从SharedPreferences中加载列表数据
    public static List m33a(SharedPreferences sharedPreferences, String str, String str2) {
        int i = sharedPreferences.getInt(str, 0); // 获取列表总数
        ArrayList arrayList = new ArrayList();
        // 遍历SharedPreferences中存储的数据，以Index为后缀获取数据
        for (int i2 = 0; i2 < i; i2++) {
            // 构造存储时使用的key的前部
            String str3 = String.valueOf(str2) + Integer.toString(i2) + "_";
            // 完整key名称
            String str4 = String.valueOf(str3) + "Name";
            // 如果存在此key
            if (sharedPreferences.contains(str4)) {
                // 获取存储的名称，默认值为未知加编号
                String string = sharedPreferences.getString(str4, String.format("<unknown %d>", Integer.valueOf(i2)));
                // 存储浮点数数据的数组
                float[] fArr = new float[12];
                // 遍历获取相关浮点数据
                for (int i3 = 0; i3 < 12; i3++) {
                    fArr[i3] = sharedPreferences.getFloat(String.valueOf(str3) + "Cents" + Integer.toString(i3), 0.0f);
                }
                // 添加到列表中
                arrayList.add(new Tuna(string, fArr));
            }
        }
        return m31a(arrayList); // 对获取到的数据列表进行排序后返回
    }

    // 对列表进行排序
    public static List m31a(List list) {
        ArrayList arrayList = new ArrayList(list);
        Collections.sort(arrayList, new C0959cf()); // 使用自定义的比较器C0959cf进行排序
        return arrayList;
    }

    // 在列表中更新或添加一个对象
    public static List m30a(List list, Tuna tuna) {
        ArrayList arrayList = new ArrayList(list);
        int i = 0;
        int i2 = -1;
        // 遍历列表查找需要更新的对象
        while (true) {
            int i3 = i;
            if (i3 >= arrayList.size() || i2 >= 0) {
                break;
            }
            // 名称相同则记录索引位置
            if (((Tuna) arrayList.get(i3)).m36a().compareTo(tuna.m36a()) == 0) {
                i2 = i3;
            }
            i = i3 + 1;
        }
        // 如果找到了，就更新对象，否则添加新对象
        if (i2 >= 0) {
            arrayList.set(i2, tuna);
        } else {
            arrayList.add(tuna);
        }
        return m31a(arrayList); // 对更新后的列表进行排序
    }

    // 将列表数据保存到SharedPreferences
    public static void m34a(SharedPreferences.Editor editor, String str, String str2, List list) {
        editor.putInt(str, list.size()); // 保存列表总数
        // 遍历列表，将每个对象的数据保存
        for (int i = 0; i < list.size(); i++) {
            Tuna tuna = (Tuna) list.get(i);
            // 构造用于存储的key前部
            String str3 = String.valueOf(str2) + Integer.toString(i) + "_";
            // 将名称和相关浮点数数据保存
            editor.putString(String.valueOf(str3) + "Name", tuna.m36a());
            float[] m28b = tuna.m28b();
            for (int i2 = 0; i2 < 12; i2++) {
                editor.putFloat(String.valueOf(str3) + "Cents" + Integer.toString(i2), m28b[i2]);
            }
        }
    }

    // 检查字符串是否以特殊字符'!'开始
    public static boolean m32a(String str) {
        return str.length() > 0 && str.charAt(0) == '!';
    }

    // 如果字符串以'!'开始，则去除这个字符，否则返回原字符串
    public static String m27b(String str) {
        return m32a(str) ? str.substring(1) : str;
    }

    // 初始化静态列表，添加一系列预定义的C0958ce对象
    public static List m26c() {
        if (f3094d == null) {
            f3094d = new ArrayList();
            // 添加多个具有特定名称和浮点数数据的C0958ce对象
            // 数据表示不同的音乐调式和频率调整
        }
        return f3094d;
    }

    // 返回名称
    public String m36a() {
        return this.f3095b;
    }

    // 返回浮点数数组
    public float[] m28b() {
        return this.f3096c;
    }
}