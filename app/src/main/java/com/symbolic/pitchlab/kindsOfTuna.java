package com.symbolic.pitchlab;

import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* renamed from: com.symbolic.pitchlab.cb */
/* loaded from: classes.dex */
public class kindsOfTuna {

    /* renamed from: a */
    private static List f3068a = null;

    /* renamed from: b */
    private String f3069b;

    /* renamed from: c */
    private int[] f3070c;

    public kindsOfTuna(String str, String str2) {
        this.f3069b = str;
        this.f3070c = music_translate.m100b(str2);
    }

    /* renamed from: a */
    /**
     * 在给定的列表中查找与指定字符串相匹配的tuna类型。
     *
     * @param list 包含tuna类型的列表，列表中的元素应为kindsOfTuna类型。
     * @param str 要在列表中查找的字符串。
     * @return 如果找到与字符串匹配的kindsOfTuna类型，则返回该类型实例；如果没有找到，则返回null。
     */
    public static kindsOfTuna searchTuna(List list, String str) {
        int i = 0;
        while (true) {
            int i2 = i; // 临时变量，用于遍历列表
            if (i2 >= list.size()) {
                // 如果遍历到列表末尾，仍未找到匹配项，则返回null
                return null;
            }
            if (((kindsOfTuna) list.get(i2)).m94a().compareTo(str) == 0) {
                // 找到列表中与指定字符串匹配的kindsOfTuna类型，返回该类型实例
                return (kindsOfTuna) list.get(i2);
            }
            i = i2 + 1; // 更新遍历位置
        }
    }

    /* renamed from: a */
    public static List m92a(SharedPreferences sharedPreferences, String str, String str2) {
        int i = sharedPreferences.getInt(str, 0);
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < i; i2++) {
            String str3 = String.valueOf(str2) + Integer.toString(i2) + "_";
            String str4 = String.valueOf(str3) + "Name";
            if (sharedPreferences.contains(str4)) {
                arrayList.add(new kindsOfTuna(sharedPreferences.getString(str4, String.format("<unknown %d>", Integer.valueOf(i2))), sharedPreferences.getString(String.valueOf(str3) + "Notes", "")));
            }
        }
        return m90a(arrayList);
    }

    /* renamed from: a */
    public static List m90a(List list) {
        ArrayList arrayList = new ArrayList(list);
        Collections.sort(arrayList, new C0956cc());
        return arrayList;
    }

    /* renamed from: a */
    public static List m89a(List list, kindsOfTuna kindsOfTuna) {
        ArrayList arrayList = new ArrayList(list);
        int i = 0;
        int i2 = -1;
        while (true) {
            int i3 = i;
            if (i3 >= arrayList.size() || i2 >= 0) {
                break;
            }
            if (((kindsOfTuna) arrayList.get(i3)).m94a().compareTo(kindsOfTuna.m94a()) == 0) {
                i2 = i3;
            }
            i = i3 + 1;
        }
        if (i2 >= 0) {
            arrayList.set(i2, kindsOfTuna);
        } else {
            arrayList.add(kindsOfTuna);
        }
        return m90a(arrayList);
    }

    /* renamed from: a */
    public static void m93a(SharedPreferences.Editor editor, String str, String str2, List list) {
        editor.putInt(str, list.size());
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= list.size()) {
                return;
            }
            kindsOfTuna kindsOfTuna = (kindsOfTuna) list.get(i2);
            String str3 = String.valueOf(str2) + Integer.toString(i2) + "_";
            editor.putString(String.valueOf(str3) + "Name", kindsOfTuna.m94a());
            editor.putString(String.valueOf(str3) + "Notes", kindsOfTuna.toString());
            i = i2 + 1;
        }
    }

    /* renamed from: a */
    public static boolean m91a(String str) {
        return str.length() > 0 && str.charAt(0) == '!';
    }

    /* renamed from: b */
    public static String m86b(String str) {
        return m91a(str) ? str.substring(1) : str;
    }

    /* renamed from: c */
    public static List m85c() {
        if (f3068a == null) {
            f3068a = new ArrayList();
            f3068a.add(new kindsOfTuna("Guitar, Standard", "E2 A2 D3 G3 B3 E4"));
            f3068a.add(new kindsOfTuna("Guitar, Drop D", "D2 A2 D3 G3 B3 E4"));
            f3068a.add(new kindsOfTuna("Guitar, Open D", "D2 A2 D3 F#3 A3 D4"));
            f3068a.add(new kindsOfTuna("Guitar, Open G", "D2 G2 D3 G3 B3 D4"));
            f3068a.add(new kindsOfTuna("Guitar, Open A", "E2 A2 E3 A3 C#4 E4"));
            f3068a.add(new kindsOfTuna("Guitar, Lute", "E2 A2 D3 F#3 B3 E4"));
            f3068a.add(new kindsOfTuna("Guitar, Irish", "D2 A2 D3 G3 A3 D4"));
            f3068a.add(new kindsOfTuna("Bass Guitar, (4 String), Standard", "E1 A1 D2 G2"));
            f3068a.add(new kindsOfTuna("Bass Guitar, (5 String), Low B", "B0 E1 A1 D2 G2"));
            f3068a.add(new kindsOfTuna("Bass Guitar, (5 String), Low E", "E1 A1 D2 G2 C3"));
            f3068a.add(new kindsOfTuna("Bass Guitar, (6 String), Standard", "B0 E1 A1 D2 G2 C3"));
            f3068a.add(new kindsOfTuna("Violin", "G3 D4 A4 E5"));
            f3068a.add(new kindsOfTuna("Violin (Tenor)", "G2 D3 A3 E4"));
            f3068a.add(new kindsOfTuna("Viola", "C3 G3 D4 A4"));
            f3068a.add(new kindsOfTuna("Fiddle, Standard", "G3 D4 A4 E5"));
            f3068a.add(new kindsOfTuna("Fiddle, Cajun", "F3 C4 G4 D5"));
            f3068a.add(new kindsOfTuna("Fiddle, Open G", "G3 D4 G4 B4"));
            f3068a.add(new kindsOfTuna("Fiddle, Sawmill", "G3 D4 G4 D5"));
            f3068a.add(new kindsOfTuna("Fiddle, Gee-dad", "G3 D4 A4 D5"));
            f3068a.add(new kindsOfTuna("Fiddle, Open D", "D3 D4 A4 D5"));
            f3068a.add(new kindsOfTuna("Fiddle, High bass", "A3 D4 A4 E5"));
            f3068a.add(new kindsOfTuna("Fiddle, Cross tuning", "A3 E4 A4 E5"));
            f3068a.add(new kindsOfTuna("Fiddle, Calico", "A3 E4 A4 C#5"));
            f3068a.add(new kindsOfTuna("Mandolin", "G3 G3 D4 D4 A4 A4 E5 E5"));
            f3068a.add(new kindsOfTuna("Mandolin (Octave)", "G2 G2 D3 D3 A3 A3 E4 E4"));
            f3068a.add(new kindsOfTuna("Ukulele (Concert), Standard", "G4 C4 E4 A4"));
            f3068a.add(new kindsOfTuna("Ukulele (Concert), D6/Soprano", "A4 D4 F#4 B4"));
            f3068a.add(new kindsOfTuna("Ukulele (Tenor)", "G3 C4 E4 A4"));
            f3068a.add(new kindsOfTuna("Ukulele (Baritone)", "D3 G3 B3 E4"));
            f3068a.add(new kindsOfTuna("Ukulele (Bass)", "E1 A1 D2 G2"));
            f3068a.add(new kindsOfTuna("Banjo (5 string), Standard/Open G", "G4 D3 G3 B3 D4"));
            f3068a.add(new kindsOfTuna("Banjo (5 string), C tuning", "G4 C3 G3 B3 D4"));
            f3068a.add(new kindsOfTuna("Banjo (5 string), Double C", "G4 C3 G3 C4 D4"));
            f3068a.add(new kindsOfTuna("Banjo (5 string), Sawmill", "G4 D3 G3 C4 D4"));
            f3068a.add(new kindsOfTuna("Banjo (5 string), Open D", "F#4 D3 F#3 A3 D4"));
            f3068a.add(new kindsOfTuna("Banjo (5 string), Guitar", "G4 D3 G3 B3 E4"));
            f3068a.add(new kindsOfTuna("Banjo (5 string), Willie Moore", "G4 D3 G3 A3 D4"));
            f3068a.add(new kindsOfTuna("Banjo (5 string), Dock Boggs (Low C)", "F#4 C3 G3 A3 D4"));
            f3068a.add(new kindsOfTuna("Banjo (5 string), Dock Boggs (Low D)", "F#4 D3 G3 A3 D4"));
            f3068a.add(new kindsOfTuna("Banjo (5 string), Cumberland Gap", "G4 E3 A3 D4 E4"));
            f3068a.add(new kindsOfTuna("Banjo (5 string), G Minor", "G4 D3 G3 A#3 D4"));
            f3068a.add(new kindsOfTuna("Banjo (5 string), Open C", "G4 C3 G3 C4 E4"));
            f3068a.add(new kindsOfTuna("Banjo (Plectrum)", "C3 G3 B3 D4"));
            f3068a.add(new kindsOfTuna("Banjo (Tenor), Standard", "C3 G3 D4 A4"));
            f3068a.add(new kindsOfTuna("Banjo (Tenor), Irish", "G2 D3 A3 E4"));
            f3068a.add(new kindsOfTuna("Banjo (Long neck)", "G4 B2 E3 G#3 B3"));
            f3068a.add(new kindsOfTuna("Bouzouki (8 string)", "C3 C4 F3 F4 A3 A3 D4 D4"));
            f3068a.add(new kindsOfTuna("Bouzouki (6 string)", "D3 D4 A3 A3 D4 D4"));
            f3068a.add(new kindsOfTuna("Pedal Steel, Standard/E9th", "B2 D3 E3 F#3 G#3 B3 E4 G#4 D#4 F#4"));
            f3068a.add(new kindsOfTuna("Pedal Steel, C6th", "C2 F2 A2 C3 E3 G3 A3 C4 E4 D4"));
            f3068a.add(new kindsOfTuna("Pedal Steel, A7th", "A1 E2 G2 A2 C#3 E3 G3 A3 C#4 E4"));
            f3068a.add(new kindsOfTuna("Pedal Steel, C Diatonic", "G2 A2 C3 E3 F3 G3 A3 B3 C4 E4"));
        }
        return f3068a;
    }

    /* renamed from: a */
    public String m94a() {
        return this.f3069b;
    }

    /* renamed from: b */
    public int[] m87b() {
        return (int[]) this.f3070c.clone();
    }

    public String toString() {
        return music_translate.m102a(this.f3070c, 0);
    }
}