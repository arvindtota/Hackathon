package net.dinglisch.android.tasker;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.google.android.gms.C0145R;

public class ProfileProperties extends MyActivity implements OnClickListener, OnCheckedChangeListener, OnSeekBarChangeListener {
    private static final int[] f5411l = new int[]{C0145R.id.button_priority_help, C0145R.id.button_cooldown_help, C0145R.id.button_limit_repeats_help, C0145R.id.button_limit_repeats_to_help, C0145R.id.button_delete_help, C0145R.id.button_enforce_task_order_help, C0145R.id.button_restore_help};
    private static final int[] f5412m = new int[]{C0145R.string.ppselect_text_priority, C0145R.string.ppselect_text_cooldown, C0145R.string.ppselect_text_limit_repeats, C0145R.string.ppselect_text_limit_repeats_to, C0145R.string.ppselect_text_delete_on_disable, 588, 1603};
    private static final int[] f5413n = new int[]{241, 1969, 1370, 565, 315, 1185, 903};
    int f5414a = -1;
    private TextView f5415b;
    private CheckBox f5416c;
    private CheckBox f5417d;
    private CheckBox f5418e;
    private CheckBox f5419f;
    private CheckBox f5420g;
    private EditText f5421h;
    private LinearLayout f5422i;
    private LinearLayout f5423j;
    private LinearLayout f5424k;
    private TextView f5425o;
    private Button f5426p;
    private SeekBar f5427q;

    public void onCreate(Bundle bundle) {
        int i = 0;
        super.onCreate(bundle);
        setResult(0);
        setContentView(C0145R.layout.profileproperties);
        this.f5416c = (CheckBox) findViewById(C0145R.id.button_delete_on_disable);
        this.f5416c.setOnClickListener(this);
        this.f5417d = (CheckBox) findViewById(C0145R.id.button_show_in_notification);
        this.f5417d.setOnClickListener(this);
        this.f5419f = (CheckBox) findViewById(C0145R.id.button_restore_settings);
        this.f5419f.setOnClickListener(this);
        this.f5420g = (CheckBox) findViewById(C0145R.id.button_enforce_task_order);
        this.f5420g.setOnClickListener(this);
        this.f5427q = (SeekBar) findViewById(C0145R.id.priority_seeker);
        this.f5427q.setMax(50);
        this.f5426p = (Button) findViewById(C0145R.id.button_cooldown);
        this.f5426p.setOnClickListener(this);
        this.f5425o = (TextView) findViewById(C0145R.id.priority_value);
        this.f5427q.setOnSeekBarChangeListener(this);
        this.f5418e = (CheckBox) findViewById(C0145R.id.button_limit_repeats);
        this.f5418e.setOnClickListener(this);
        this.f5418e.setOnCheckedChangeListener(this);
        this.f5421h = (EditText) findViewById(C0145R.id.repeat_value_text);
        this.f5415b = (TextView) findViewById(C0145R.id.creation_date);
        for (int findViewById : f5411l) {
            int findViewById2;
            ((ImageButton) findViewById(findViewById2)).setOnClickListener(this);
        }
        this.f5422i = (LinearLayout) findViewById(C0145R.id.arg4_layout);
        this.f5423j = (LinearLayout) findViewById(C0145R.id.arg5_layout);
        this.f5424k = (LinearLayout) findViewById(C0145R.id.arg6_layout);
        setTitle(xr.m13370a((Context) this, 1489, new Object[0]));
        apq.m11343a((Activity) this, (int) C0145R.id.pl_show_in_notification, 1178);
        apq.m11343a((Activity) this, (int) C0145R.id.pl_restore_settings, 1603);
        apq.m11343a((Activity) this, (int) C0145R.id.pl_enforce_task_order, 588);
        C0868a.m9071a((Activity) this, true);
        if (bundle == null) {
            bundle = getIntent().getExtras();
        }
        if (bundle != null) {
            this.f5414a = bundle.getInt("profile");
        }
        if (this.f5414a != -1) {
            amv c = amv.m10799c((Context) this);
            if (c != null) {
                anm g = c.m10911g(this.f5414a);
                boolean J = g.m10988J();
                this.f5418e.setChecked(J);
                if (J) {
                    findViewById2 = g.m10986H();
                    this.f5416c.setChecked(g.m11043v());
                    this.f5421h.setText(Integer.toString(findViewById2));
                    m8651a(true);
                } else {
                    m8651a(false);
                }
                CheckBox checkBox = this.f5417d;
                if (g.m11036n()) {
                    J = false;
                } else {
                    J = true;
                }
                checkBox.setChecked(J);
                LinearLayout linearLayout = this.f5424k;
                if (g.m11013b((Context) this)) {
                    i = 8;
                }
                linearLayout.setVisibility(i);
                this.f5419f.setChecked(g.m11041t());
                this.f5420g.setChecked(g.m11042u());
                this.f5427q.setProgress(g.m11039r());
                m8650a();
                this.f5426p.setText(new amk(g.m11040s()).toString());
                this.f5415b.setText(wa.m13190b((Context) this, wa.m13198c(g.m9249e())) + " ");
                return;
            }
            ng.m12730c("PP", "onCreate: no data");
            finish();
            return;
        }
        ng.m12730c("PP", "onCreate: no profile ID specified");
        finish();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("profile", this.f5414a);
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (seekBar.equals(this.f5427q)) {
            m8650a();
        }
    }

    private void m8650a() {
        this.f5425o.setText(Integer.toString(this.f5427q.getProgress()));
    }

    private void m8651a(boolean z) {
        int i;
        int i2 = 0;
        LinearLayout linearLayout = this.f5423j;
        if (z) {
            i = 0;
        } else {
            i = 8;
        }
        linearLayout.setVisibility(i);
        LinearLayout linearLayout2 = this.f5422i;
        if (!z) {
            i2 = 8;
        }
        linearLayout2.setVisibility(i2);
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (z) {
            this.f5421h.setText("1");
            this.f5416c.setChecked(true);
        }
        m8651a(z);
    }

    public void onBackPressed() {
        int i;
        boolean z;
        boolean z2 = true;
        boolean z3 = false;
        anm g = amv.m10799c((Context) this).m10911g(this.f5414a);
        boolean z4 = g == null;
        if (!this.f5418e.isChecked()) {
            i = Integer.MAX_VALUE;
            z = true;
        } else if (this.f5421h.length() == 0) {
            if (z4) {
                apq.m11459d((Context) this, 1772, new Object[0]);
            }
            i = Integer.MAX_VALUE;
            z = false;
        } else {
            i = new Integer(apq.m11326a(this.f5421h)).intValue();
            z = true;
        }
        if (z) {
            if (this.f5418e.isChecked() && i == 0 && this.f5416c.isChecked()) {
                if (z4) {
                    apq.m11459d((Context) this, 800, new Object[0]);
                }
                if (z3) {
                    setResult(-1);
                    finish();
                }
            } else if (g != null) {
                g.m11028h(this.f5418e.isChecked());
                if (this.f5417d.isChecked()) {
                    z2 = false;
                }
                g.m11016c(z2);
                g.m11022e(this.f5419f.isChecked());
                g.m11024f(this.f5420g.isChecked());
                if (this.f5418e.isChecked()) {
                    g.m11035n(i);
                    z3 = this.f5416c.isChecked();
                } else {
                    g.m10990L();
                }
                g.m11019d(z3);
                g.m11011b(this.f5427q.getProgress());
                g.m11014c(new amk(this.f5426p.getText().toString()).m10707c());
            }
        }
        z3 = z;
        if (z3) {
            setResult(-1);
            finish();
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        C0868a.m9078a(this, 1, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 1:
                finish();
                break;
            case 16908332:
                onBackPressed();
                break;
        }
        return true;
    }

    public void onClick(View view) {
        if (this.f5426p.equals(view)) {
            df.m12092a(this, new aar(this), null, new amk(this.f5426p.getText().toString()), false).m9160a((Activity) this, "countdown");
            return;
        }
        int id = view.getId();
        for (int i = 0; i < f5411l.length; i++) {
            if (id == f5411l[i]) {
                zc.m13469a((Activity) this, f5412m[i], xr.m13370a((Context) this, f5413n[i], new Object[0]), null);
                return;
            }
        }
    }

    public static Intent m8648a(anm net_dinglisch_android_tasker_anm) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(xj.m13334b(), ProfileProperties.class.getName()));
        intent.putExtra("profile", net_dinglisch_android_tasker_anm.m10982D());
        return intent;
    }

    public void onDestroy() {
        super.onDestroy();
        this.f5415b = null;
        this.f5416c = null;
        this.f5417d = null;
        this.f5418e = null;
        this.f5419f = null;
        this.f5420g = null;
        this.f5421h = null;
        this.f5422i = null;
        this.f5423j = null;
        this.f5424k = null;
    }
}
