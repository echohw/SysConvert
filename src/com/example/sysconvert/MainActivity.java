package com.example.sysconvert;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private Listener listener;
	public Forbid forbid;
	public Change change;

	private EditText et_define;
	private long exitTime = 0;
	private Toast mToast;

	/**
	 * 创建并将edittext和button的ID值存入数组中
	 */
	int[] editTexts_int = { R.id.et_2, R.id.et_8, R.id.et_10, R.id.et_16,
			R.id.et_select_input };
	EditText[] editTexts_obj = new EditText[editTexts_int.length];
	int[] buttons_int = { R.id.bt_0, R.id.bt_1, R.id.bt_2, R.id.bt_3,
			R.id.bt_4, R.id.bt_5, R.id.bt_6, R.id.bt_7, R.id.bt_8, R.id.bt_9,
			R.id.bt_A, R.id.bt_B, R.id.bt_C, R.id.bt_D, R.id.bt_E, R.id.bt_F,
			R.id.bt_delete, R.id.bt_negative, R.id.bt_point };
	Button[] buttons_obj = new Button[buttons_int.length];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	// 初始化操作
	public void init() {
		// 对edittext及button进行初始化
		for (int i = 0; i < editTexts_obj.length; i++) {
			editTexts_obj[i] = (EditText) findViewById(editTexts_int[i]);
		}
		for (int j = 0; j < buttons_obj.length; j++) {
			buttons_obj[j] = (Button) findViewById(buttons_int[j]);
		}

		et_define = (EditText) findViewById(R.id.et_define);

		changeBackground();

		listener = new Listener(editTexts_obj, buttons_obj, et_define,
				MainActivity.this);

		forbid = new Forbid(buttons_obj, listener);

		change = new Change(editTexts_obj, buttons_obj, et_define, listener,
				MainActivity.this);

		// 对clean按钮添加事件
		findViewById(R.id.bt_clear).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.number_str = "";
				forbid.negative_point();
				if (findViewById(R.id.et_select_input).hasFocus()) {
					forbid.et_select_input();
				}
				if (!et_define.getText().toString().isEmpty()) {
					et_define.setText("");
				}
				for (final EditText et : editTexts_obj) {
					if (et.getId() != R.id.et_select_input) {
						et.setText("");
					}
				}
				if (et_define.hasFocus()) {
					for (Button bt : buttons_obj) {
						bt.setEnabled(false);
						bt.setBackgroundColor(Color.parseColor("#F7F8F9"));
					}
				}
			}
		});

		et_define.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (et_define.hasFocus()) {
					if (listener.et_select_input_str.length() != 0) {
						listener.flag_jinzhi = Integer
								.parseInt(listener.et_select_input_str);
						listener.number_str = et_define.getText().toString();
						change.conversion();
					} else {
						showToast("请先在左侧输入数据");
					}
					et_define.addTextChangedListener(listener);
					for (Button bt : buttons_obj) {
						bt.setEnabled(false);
						bt.setBackgroundColor(Color.parseColor("#F7F8F9"));
					}
				} else {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					boolean isOpen = imm.isActive();
					if (isOpen) {
						imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
					}
					et_define.removeTextChangedListener(listener);
				}
			}
		});
	}

	// 添加按钮按下抬起事件，在按下抬起过程中修改按钮的背景颜色
	public void changeBackground() {
		for (final Button bt : buttons_obj) {
			bt.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View view, MotionEvent motionEvent) {
					// 按下操作
					if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
						bt.setBackgroundColor(Color.parseColor("#F7F8F9"));
					}
					// 抬起操作
					if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
						bt.setBackgroundColor(Color.parseColor("#D2D2D2"));
					}
					/*
					 * 移动操作 if
					 * (motionEvent.getAction()==MotionEvent.ACTION_MOVE) {}
					 */
					return false;
				}
			});
		}
	}

	// 解决toast连续弹出的问题
	public void showToast(String text) {
		if (mToast == null) {
			mToast = Toast
					.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	// 连续按两次退出程序
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void exit() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			showToast("再按一次退出程序");
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			System.exit(0);
		}
	}
}
