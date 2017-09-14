package com.example.sysconvert;


import android.widget.Button;
import android.widget.EditText;

class Change {
	private Listener listener;
	private EditText et_define, et_2, et_8, et_10, et_16, et_select_input;

	private Button bt_negative;
	private MainActivity activity;

	private EditText[] editTexts_obj;

	private Change_point change_point;

	Change(EditText[] editTexts_obj, Button[] buttons_obj, EditText et_define,
			Listener listener, MainActivity activity) {

		this.listener = listener;
		this.editTexts_obj = editTexts_obj;
		this.activity = activity;
		this.et_define = et_define;

		for (Button bt : buttons_obj) {
			if (bt.getId() == R.id.bt_negative) {
				bt_negative = bt;
			}
		}

		for (EditText et : editTexts_obj) {
			if (et.getId() == R.id.et_2) {
				et_2 = et;
			} else if (et.getId() == R.id.et_8) {
				et_8 = et;
			} else if (et.getId() == R.id.et_10) {
				et_10 = et;
			} else if (et.getId() == R.id.et_16) {
				et_16 = et;
			} else if (et.getId() == R.id.et_select_input) {
				et_select_input = et;
			}
		}

		change_point = new Change_point(listener);
	}

	private void negative_normal() {
		// 负数及常规数共用代码
		try {
			// 将number_str转换成10进制Long类型
			Long to_number_ten = Long.parseLong(listener.number_str,
					listener.flag_jinzhi);
			String to_number_two = Long.toBinaryString(to_number_ten);
			String to_number_eight = Long.toOctalString(to_number_ten);
			String to_number_sixteen = Long.toHexString(to_number_ten)
					.toUpperCase();
			String to_number_et_select_input_str;
			if (!et_define.hasFocus()) {

				if (listener.et_select_input_str.length() != 0) {
					to_number_et_select_input_str = Long.toString(
							to_number_ten, 
							Integer.parseInt(listener.et_select_input_str));
					et_define.setText(to_number_et_select_input_str
							.toUpperCase());
				}
			}
			if (et_2.hasFocus()) {
				et_2.setText(listener.number_str);
			} else {
				et_2.setText(to_number_two);
			}
			if (et_8.hasFocus()) {
				et_8.setText(listener.number_str);
			} else {
				et_8.setText(to_number_eight);
			}

			if (et_10.hasFocus()) {
				et_10.setText(listener.number_str);
			} else {
				et_10.setText(String.valueOf(to_number_ten));
			}

			if (et_16.hasFocus()) {
				et_16.setText(listener.number_str);
			} else {
				et_16.setText(to_number_sixteen);
			}

		} catch (Exception e) {
			// 数据与标记不匹配
			// 数据超过长度
			if (!(listener.number_str.length() == 1 && listener.number_str
					.substring(0, 1).equals(bt_negative.getText().toString()))) {
				activity.showToast("数据不合法");
			}

			for (EditText et : editTexts_obj) {
				if (et.hasFocus() && et.getId() != et_select_input.getId()) {
					et.setText(listener.number_str);
				}
			}
		}
	}

	// 将number_str分别转换为2,8,10,16进制型数据并显示在edittext中
	public void conversion() {
		// 对number_str进行标记
		if (listener.number_str.length() != 0) {
			if (listener.number_str.substring(0, 1).equals(
					bt_negative.getText().toString())) {
				listener.flag_negative = true;
			} else {
				listener.flag_negative = false;
			}
			for (int i = 0; i < listener.number_str.length(); i++) {
				String judge_number_str = listener.number_str.charAt(i) + "";
				if (judge_number_str.equals(".")) {
					listener.flag_point = true;
					break;
				} else {
					listener.flag_point = false;
				}
			}
		} else {
			listener.flag_point = false;
			listener.flag_negative = false;
		}

		if (listener.number_str.length() != 0) {

			if (listener.flag_negative) {
				/*
				 * if (listener.flag_point) { // 是负数，并且是小数 } else { // 是负数
				 * negative_normal(); }
				 */
				negative_normal();
			} else if (listener.flag_point) {
				// 是小数
				if (!listener.number_str.substring(
						listener.number_str.length() - 1,
						listener.number_str.length()).equals(".")) {
					try {
						String to_number_two = change_point
								.to_number_two_method();
						String to_number_eight = change_point
								.to_number_eight_method();
						String to_number_ten = change_point
								.to_number_ten_method();
						String to_number_sixteen = change_point
								.to_number_sixteen_method();
						String to_number_et_select_input_str;
						if (!et_define.hasFocus()) {
							if (listener.et_select_input_str.length() != 0) {
								to_number_et_select_input_str = change_point
										.to_number_et_select_input_str_method();
								et_define.setText(to_number_et_select_input_str
										.toUpperCase());
							}
						}
						if (et_2.hasFocus()) {
							et_2.setText(listener.number_str);
						} else {
							et_2.setText(to_number_two);
						}
						if (et_8.hasFocus()) {
							et_8.setText(listener.number_str);
						} else {
							et_8.setText(to_number_eight);
						}

						if (et_10.hasFocus()) {
							et_10.setText(listener.number_str);
						} else {
							et_10.setText(to_number_ten);
						}

						if (et_16.hasFocus()) {
							et_16.setText(listener.number_str);
						} else {
							et_16.setText(to_number_sixteen);
						}

					} catch (Exception e) {
						// 数据与标记不匹配
						// 数据超过长度
						if (!(listener.number_str.length() == 1 && listener.number_str
								.substring(0, 1).equals(
										bt_negative.getText().toString()))) {
							activity.showToast("数据不合法");

						}

						for (EditText et : editTexts_obj) {
							if (et.hasFocus()
									&& et.getId() != et_select_input.getId()) {
								et.setText(listener.number_str);
							}
						}

					}
				} else {
					for (EditText et : editTexts_obj) {
						if (et.hasFocus()
								&& et.getId() != et_select_input.getId()) {
							et.setText(listener.number_str);
						}
					}
				}

			} else {
				// 是常规数
				negative_normal();
			}
		}
	}
}
