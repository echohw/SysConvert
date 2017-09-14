package com.example.sysconvert;


import android.graphics.Color;
import android.widget.Button;
import android.widget.EditText;

public class Forbid {

	private Button[] buttons_obj;
	private Listener listener;
	private Button bt_negative;
	private Button bt_point;

	public Forbid(Button[] buttons_obj, Listener listener) {

		this.buttons_obj = buttons_obj;
		this.listener = listener;

		bt_negative = listener.bt_negative;
		bt_point = listener.bt_point;
	}

	// edittext变换过程恢复按钮可点击状态
	public void setEnabledTrue() {
		for (Button bt : buttons_obj) {
			bt.setEnabled(true);
			bt.setBackgroundColor(Color.parseColor("#D2D2D2"));
		}
	}

	// 根据长度禁用相关按钮
	public void et_select_input() {
		setEnabledTrue();
		if (listener.et_select_input_str.length() == 0) {
			for (Button bt : buttons_obj) {
				if (bt.getId() == R.id.bt_0 || bt.getId() == R.id.bt_A
						|| bt.getId() == R.id.bt_B || bt.getId() == R.id.bt_C
						|| bt.getId() == R.id.bt_D || bt.getId() == R.id.bt_E
						|| bt.getId() == R.id.bt_F
						|| bt.getId() == R.id.bt_negative
						|| bt.getId() == R.id.bt_point) {
					bt.setEnabled(false);
					bt.setBackgroundColor(Color.parseColor("#F7F8F9"));
				}
			}
		} else if (listener.et_select_input_str.length() == 1) {
			if (Integer.parseInt(listener.et_select_input_str) > 3) {
				for (Button bt : buttons_obj) {
					if (bt.getId() != R.id.bt_delete) {
						bt.setEnabled(false);
						bt.setBackgroundColor(Color.parseColor("#F7F8F9"));
					}
				}
			} else if (Integer.parseInt(listener.et_select_input_str) == 3) {
				for (Button bt : buttons_obj) {
					if (bt.getId() != R.id.bt_0 && bt.getId() != R.id.bt_1
							&& bt.getId() != R.id.bt_2
							&& bt.getId() != R.id.bt_3
							&& bt.getId() != R.id.bt_4
							&& bt.getId() != R.id.bt_5
							&& bt.getId() != R.id.bt_6
							&& bt.getId() != R.id.bt_delete) {
						bt.setEnabled(false);
						bt.setBackgroundColor(Color.parseColor("#F7F8F9"));
					}
				}
			} else {
				for (Button bt : buttons_obj) {
					if (bt.getId() == R.id.bt_A || bt.getId() == R.id.bt_B
							|| bt.getId() == R.id.bt_C
							|| bt.getId() == R.id.bt_D
							|| bt.getId() == R.id.bt_E
							|| bt.getId() == R.id.bt_F
							|| bt.getId() == R.id.bt_negative
							|| bt.getId() == R.id.bt_point) {
						bt.setEnabled(false);
						bt.setBackgroundColor(Color.parseColor("#F7F8F9"));
					}
				}
			}

		} else if (listener.et_select_input_str.length() == 2) {
			for (Button bt : buttons_obj) {
				if (bt.getId() != R.id.bt_delete) {
					bt.setEnabled(false);
					bt.setBackgroundColor(Color.parseColor("#F7F8F9"));
				}
			}
		}

	}

	public void negative_point() {
		if (listener.number_str.length() != 0) {
			bt_negative.setEnabled(false);
			bt_negative.setBackgroundColor(Color.parseColor("#F7F8F9"));
			if (listener.number_str.substring(0, 1).equals(
					bt_negative.getText())) {
				bt_point.setEnabled(false);
				bt_point.setBackgroundColor(Color.parseColor("#F7F8F9"));
			} else {
				for (int i = 0; i < listener.number_str.length(); i++) {
					char ch = listener.number_str.charAt(i);
					if ((ch + "").equals(".")) {
						listener.flag_point = true;
						break;
					} else {
						listener.flag_point = false;
					}
				}

				if (listener.flag_point) {
					bt_point.setEnabled(false);
					bt_point.setBackgroundColor(Color.parseColor("#F7F8F9"));
				} else {
					bt_point.setEnabled(true);
					bt_point.setBackgroundColor(Color.parseColor("#D2D2D2"));
				}
			}
		} else {
			bt_negative.setEnabled(true);
			bt_negative.setBackgroundColor(Color.parseColor("#D2D2D2"));
			bt_point.setEnabled(false);
			bt_point.setBackgroundColor(Color.parseColor("#F7F8F9"));
		}
	}

	// 根据输入框焦点的改变禁用相关按钮
	public void normal(int flag_jinzhi) {

		setEnabledTrue();

		if (flag_jinzhi == 2) {
			for (Button bt : buttons_obj) {
				if (bt.getId() != R.id.bt_0 && bt.getId() != R.id.bt_1
						&& bt.getId() != R.id.bt_delete
						&& bt.getId() != R.id.bt_negative) {
					bt.setEnabled(false);
					bt.setBackgroundColor(Color.parseColor("#F7F8F9"));
				}
			}

		} else if (flag_jinzhi == 8) {
			for (Button bt : buttons_obj) {
				if (bt.getId() == R.id.bt_8 || bt.getId() == R.id.bt_9
						|| bt.getId() == R.id.bt_A || bt.getId() == R.id.bt_B
						|| bt.getId() == R.id.bt_C || bt.getId() == R.id.bt_D
						|| bt.getId() == R.id.bt_E || bt.getId() == R.id.bt_F
						|| bt.getId() == R.id.bt_point) {
					bt.setEnabled(false);
					bt.setBackgroundColor(Color.parseColor("#F7F8F9"));
				}
			}

		} else if (flag_jinzhi == 10) {
			for (Button bt : buttons_obj) {
				if (bt.getId() == R.id.bt_A || bt.getId() == R.id.bt_B
						|| bt.getId() == R.id.bt_C || bt.getId() == R.id.bt_D
						|| bt.getId() == R.id.bt_E || bt.getId() == R.id.bt_F
						|| bt.getId() == R.id.bt_point) {
					bt.setEnabled(false);
					bt.setBackgroundColor(Color.parseColor("#F7F8F9"));
				}
			}

		} else if (flag_jinzhi == 16) {
			for (Button bt : buttons_obj) {
				if (bt.getId() == R.id.bt_point) {
					bt.setEnabled(false);
					bt.setBackgroundColor(Color.parseColor("#F7F8F9"));
				}
			}
		}

	}

}
