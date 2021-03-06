package iccl.workshifts.m;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewActivityUser extends Activity implements OnClickListener {
	private boolean lock = MainActivity.g10;
	private ArrayList<CheckedTextView> CTV = new ArrayList<CheckedTextView>();
	private CheckedTextView cb_A, cb_B, cb_C, cb_D, cb_E;
	private TextView title_left, title_right;
	private ImageView yes, no;
	private String ms = "1";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_view);
		findViewById();
		load();
	}

	protected void onStart() {
		super.onStart();
	}

	protected void onPause() {
		super.onPause();
	}

	protected void onResume() {
		super.onResume();
	}

	protected void onDestroy() {
		super.onDestroy();
		MainActivity.main = true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return false;
	}

	public void onClick(View v) {
		if (v == yes) {
			save();
		}
		if (v == no) {
			finish();
		}
		if (v == cb_A || v == cb_B || v == cb_C || v == cb_D || v == cb_E) {
			((CheckedTextView) v).setChecked(!((CheckedTextView) v).isChecked());
		}
	}

	private void findViewById() {
		MainActivity.main = false;
		title_left = (TextView) findViewById(R.id.user_tl);
		title_right = (TextView) findViewById(R.id.user_tr);
		CTV.add(cb_A = (CheckedTextView) findViewById(R.id.cb_A));
		CTV.add(cb_B = (CheckedTextView) findViewById(R.id.cb_B));
		CTV.add(cb_C = (CheckedTextView) findViewById(R.id.cb_C));
		CTV.add(cb_D = (CheckedTextView) findViewById(R.id.cb_D));
		CTV.add(cb_E = (CheckedTextView) findViewById(R.id.cb_E));
		yes = (ImageView) findViewById(R.id.uv_yes);
		no = (ImageView) findViewById(R.id.uv_no);
	}

	private void save() {
		String m = "";
		for (int i = 0; i < CTV.size(); i++) {
			if (CTV.get(i).isChecked()) {
				m += "1";
			} else {
				m += "0";
			}
		}
		ms += m;
		MainActivity.setDayItem(this, ms);
		finish();
	}

	private void load() {
		if (!lock) {
			Boolean[] ETB = { false, false, false, false, false };
			for (int ds = 0; ds < MainActivity.Daylist.size(); ds++) {
				String td = MainActivity.Daylist.get(ds);
				if ("0".equals(td.substring(0, 1))) {
					String ep = MainActivity.getLine(td, 1, '/'); // work.get(p);
					String es;
					// 白班教學回診1|1|08|11|
					int r = 0;
					int awp = Integer.parseInt(ep);
					if (awp < MainActivity.Worklist.size()) {
						es = MainActivity.Worklist.get(awp);
						String ena = MainActivity.getLine(es, r, '|');
						r += ena.length() + 1;
						String tp = MainActivity.getLine(es, r, '|');
						ETB[Integer.parseInt(tp)] = true;
					}
				}
			}
			for (int i = 0; i < CTV.size(); i++) {
				CTV.get(i).setChecked(ETB[i]);
				CTV.get(i).setEnabled(ETB[i]);
			}
		}
		String m = MainActivity.getDayItem();
		String p = MainActivity.getLine(m, 1, '/');
		String s;
		String c = m.substring(2 + p.length());
		// A||
		s = MainActivity.Userlist.get(Integer.parseInt(p));
		String na = MainActivity.getLine(s, 0, '|');
		title_left.setText(na);
		title_right.setText((MainActivity.getMonthDate() + 1) + "月" + MainActivity.getDayDate());
		if (c.length() > 0) {
			for (int i = 0; i < c.length(); i++) {
				int j = Integer.parseInt(c.substring(i, i + 1));
				if (j == 0) {
					CTV.get(i).setChecked(false);
				} else {
					CTV.get(i).setChecked(true);
				}
				CTV.get(i).setOnClickListener(this);
			}
		} else {
			MainActivity.toast(this, "錯誤!");
		}
		yes.setOnClickListener(this);
		no.setOnClickListener(this);
		ms += (p + '/');
		if (lock) {
			for (int i = 0; i < CTV.size(); i++) {
				CTV.get(i).setEnabled(false);
			}
			yes.setVisibility(View.INVISIBLE);
		}
	}
}
