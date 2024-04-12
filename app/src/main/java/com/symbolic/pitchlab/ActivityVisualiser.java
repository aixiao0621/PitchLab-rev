package com.symbolic.pitchlab;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.PowerManager;
import android.widget.LinearLayout;
import android.content.res.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.Toast;
//import com.google.android.gms.ads.C0172e;
//import com.google.android.gms.ads.C0174f;
//import com.google.android.gms.common.C0200g;


public class ActivityVisualiser extends Activity {

    /* renamed from: f */
    private static Context f2952f;

    /* renamed from: a */
    private PowerManager.WakeLock f2953a;

    /* renamed from: b */
    private LinearLayout f2954b;

    /* renamed from: c */
    private openGLActivity f2955c;

    /* renamed from: d */
//    private C0174f f2956d;
    //GMS

    /* renamed from: e */
    private InterfaceC0941bo f2957e;

    /* renamed from: a */
    static long m137a(String str, long j) {
        long j2;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
            try {
                j2 = Long.parseLong(randomAccessFile.readLine());
            } catch (IOException e) {
                j2 = j;
            }
            try {
                randomAccessFile.close();
                return j2;
            } catch (IOException e2) {
                return j2;
            }
        } catch (FileNotFoundException e3) {
            return j;
        }
    }

    /* renamed from: a */
    private static kindsOfTuna m138a(String str) {
        kindsOfTuna m88a = kindsOfTuna.searchTuna(kindsOfTuna.m85c(), str);
        return m88a != null ? m88a : kindsOfTuna.searchTuna(settings.m57h(), str);
    }

    /* renamed from: a */
    private void m141a() {
        m136b();
        try {
            this.f2957e = version.m117a(settings.m70c(), new processStream(this));
            settings.m52j(0);
        } catch (Exception e) {
            this.f2957e = null;
            settings.m52j(2);
        }
    }

    /* renamed from: a */
//    private void m140a(Configuration configuration) {
//        if (this.f2956d != null) {
//            boolean z = configuration.orientation == 1;
//            this.f2956d.setVisibility(z ? 0 : 8);
//            if (z) {
//                this.f2956d.m2681c();
//            } else {
//                this.f2956d.m2682b();
//            }
//        }
//    }

    /* renamed from: b */
    private static Tuna m135b(String str) {
        Tuna m29a = Tuna.m29a(Tuna.m26c(), str);
        return m29a != null ? m29a : Tuna.m29a(settings.m51k(), str);
    }

    /* renamed from: b */
    private void m136b() {
        if (this.f2957e != null) {
            this.f2957e.mo110a();
            this.f2957e = null;
        }
    }

    /* renamed from: c */
    private void m134c() {
        int i = 8;
        int i2 = 0;
        kindsOfTuna m138a = m138a(settings.m59g());
        kindsOfTuna kindsOfTuna = m138a == null ? (kindsOfTuna) com.symbolic.pitchlab.kindsOfTuna.m85c().get(0) : m138a;
        settings.m79a(kindsOfTuna.m94a());
        Tuna m135b = m135b(settings.m53j());
        Tuna tuna = m135b == null ? (Tuna) Tuna.m26c().get(0) : m135b;
        settings.m72b(tuna.m36a());
        switch (settings.m66d()) {
            case -1:
                i = 4;
                break;
            case 1:
                i = 16;
                break;
        }
        if (settings.m41t().equals("1.0.15")) {
            i2 = 1;
        } else if (settings.m41t().equals("1.0.16")) {
            i2 = 2;
        }
        PitchLabNative.setAnalysisParameters(kindsOfTuna.m86b(kindsOfTuna.m94a()), kindsOfTuna.m87b(), tuna.m28b(), settings.m63e(), settings.m46o(), i, i2);
    }

    /* renamed from: d */
    private void m133d() {
        settings.m80a(getApplicationContext());
        m134c();
        switch (settings.m45p()) {
            case 1:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case 2:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                break;
        }
        float m63e = settings.m63e();
        String str = m63e != settings.m61f() ? String.valueOf("") + String.format("\nA4 Reference: %.1fhz", Float.valueOf(m63e)) : "";
        settings.m59g().equals(((kindsOfTuna) kindsOfTuna.m85c().get(0)).m94a());
        String m53j = settings.m53j();
        if (!m53j.equals(((Tuna) Tuna.m26c().get(0)).m36a())) {
            str = Tuna.m32a(m53j) ? String.valueOf(str) + String.format("\nCustom Temperament: '%s'", Tuna.m27b(m53j)) : String.valueOf(str) + String.format("\nPreset Temperament: '%s'", m53j);
        }
        String trim = str.trim();
        if (trim.length() > 0) {
            Toast.makeText(getApplicationContext(), trim, Toast.LENGTH_LONG).show();
        }
    }

    /* renamed from: e */
    private void m132e() {
        settings.m73b(getApplicationContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: f */
    public void m131f() {
        startActivity(new Intent(this, ActivityOptions.class));
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
//        m140a(configuration);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        PitchLabNative.initialize();
        f2952f = getApplicationContext();
        super.onCreate(bundle);
//        setContentView(R.layout.activity_visualiser);
//        this.f2953a = ((PowerManager) getSystemService("power")).newWakeLock(26, "DoNotDimScreen");
        try {
//            AudioManager audioManager = (AudioManager) getSystemService("audio");
//            int mode = audioManager.getMode();
//            audioManager.setMode(2);
//            audioManager.setMicrophoneMute(false);
//            audioManager.setMode(mode);
        } catch (Exception e) {
        }
//        this.f2954b = (LinearLayout) findViewById(R.id.VisualiserLayout);
        this.f2955c = new openGLActivity(f2952f);
        this.f2954b.addView(this.f2955c, new LinearLayout.LayoutParams(-1, -1, 1.0f));
        this.f2955c.setActivity(this);
//        if ((m137a("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq", 0L) / 1000) * Runtime.getRuntime().availableProcessors() >= 2000 && C0200g.m2597a(f2952f) == 0) {
//            this.f2956d = new C0174f(this);
//            this.f2956d.setAdSize(C0172e.f448g);
//            this.f2956d.setAdUnitId("ca-app-pub-1361322998704129/5393057496");
//            this.f2954b.addView(this.f2956d, new LinearLayout.LayoutParams(-1, -2));
//            this.f2956d.m2683a(C0939bm.m120b());
//        }
//        this.f2955c.setOnLongPressListener(new View$OnClickListenerC0935bi(this));
        m133d();
    }

    @Override // android.app.Activity
    protected Dialog onCreateDialog(int i) {
        switch (i) {
            case 100:
                int numViews = PitchLabNative.getNumViews();
                CharSequence[] charSequenceArr = new CharSequence[numViews + 1];
                for (int i2 = 0; i2 < numViews; i2++) {
                    charSequenceArr[i2] = PitchLabNative.getViewName(i2);
                }
                charSequenceArr[numViews] = "Options";
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("PitchLab Views");
//                builder.setSingleChoiceItems(charSequenceArr, -1, new DialogInterface$OnClickListenerC0936bj(this, numViews));
                return builder.create();
            default:
                return null;
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        gms.m121a();
//        if (this.f2956d != null) {
//            this.f2956d.m2684a();
//        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 82) {
            m131f();
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
//        if (this.f2956d != null) {
//            this.f2956d.m2682b();
//        }
        this.f2955c.onPause();
        this.f2953a.release();
        m136b();
        m132e();
        PitchLabNative.appPaused();
    }

    @Override // android.app.Activity
    protected void onPrepareDialog(int i, Dialog dialog) {
        switch (i) {
            case 100:
                int m44q = settings.m44q();
                ListView listView = ((AlertDialog) dialog).getListView();
                listView.setItemChecked(m44q, true);
                listView.setSelection(m44q);
                return;
            default:
                return;
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        m133d();
        m141a();
        this.f2953a.acquire();
        this.f2955c.onResume();
//        m140a(getResources().getConfiguration());
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        PitchLabNative.appPaused();
    }
}
