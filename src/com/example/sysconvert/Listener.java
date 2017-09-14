package com.example.sysconvert;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;

class Listener implements TextWatcher, OnClickListener {

    /**
     * number_str:用来保存按钮按下的字符,拼接成字符串 flag_jinzhi:标记焦点在哪个进制输入框 (number_str的进制类型)
     * et_select_input_str:用来保存与et_define对应的选择的进制类型 flag_point:标记number_str是否是小数
     * flag_negative:标记number_str是否是负数
     */
    String number_str = "", et_select_input_str = "";
    int flag_jinzhi;
    boolean flag_point = false, flag_negative = false;
    private EditText et_define, et_select_input;
    public Button bt_negative, bt_point;
    private MainActivity activity;

    private EditText[] editTexts_obj;
    private Button[] buttons_obj;


    Listener(EditText[] editTexts_obj, Button[] buttons_obj, EditText et_define, MainActivity activity) {
        this.editTexts_obj = editTexts_obj;
        this.buttons_obj = buttons_obj;
        this.et_define = et_define;
        this.activity = activity;

        for (Button bt : buttons_obj) {
            if (bt.getId() == R.id.bt_negative) {
                bt_negative = bt;
            } else if (bt.getId() == R.id.bt_point) {
                bt_point = bt;
            }
        }

        for (EditText et : editTexts_obj) {
            if (et.getId() == R.id.et_select_input) {
                et_select_input = et;
            }
        }

        setOnFocusChangeListener();
        setOnClickListener();
    }

    private void showToast(String text) {
        activity.showToast(text);
    }


    // 对所有button添加点击监听
    private void setOnClickListener() {
        for (Button bt : buttons_obj) {
            bt.setOnClickListener(Listener.this);
        }
    }

    // 对所有edittext控件添加焦点监听
    private void setOnFocusChangeListener() {
        for (final EditText et : editTexts_obj) {
            et.setKeyListener(null); // 禁止弹出输入法
            et.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    // 判断当前edittext控件是否获取焦点并作出相应反应
                    if (et.hasFocus()) {
                        if (et.getId() != R.id.et_select_input) {
                            number_str = et.getText().toString();
                            activity.forbid.negative_point();
                        }
                        // 根据当前edittext选择是否禁用相关button
                        if (R.id.et_2 == et.getId()) {
                            flag_jinzhi = 2;
                            activity.forbid.normal(flag_jinzhi);
                            activity.forbid.negative_point();
                        } else if (R.id.et_8 == et.getId()) {
                            flag_jinzhi = 8;
                            activity.forbid.normal(flag_jinzhi);
                            activity.forbid.negative_point();
                        } else if (R.id.et_10 == et.getId()) {
                            flag_jinzhi = 10;
                            activity.forbid.normal(flag_jinzhi);
                            activity.forbid.negative_point();
                        } else if (R.id.et_16 == et.getId()) {
                            flag_jinzhi = 16;
                            activity.forbid.normal(flag_jinzhi);
                            activity.forbid.negative_point();
                        } else if (R.id.et_select_input == et.getId()) {
                            activity.forbid.et_select_input();
                        }
                    }
                }
            });
        }
    }

    // 重写内容监听的方法
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (et_select_input_str.length() != 0) {
            if (!et_define.getText().toString().isEmpty()) {
                int negative_count = 0, point_count = 0;
                String str_et_define = et_define.getText().toString();
                for (int i = 0; i < str_et_define.length(); i++) {
                    String str = str_et_define.charAt(i) + "";
                    if (bt_negative.getText().toString().equals(str)) {
                        negative_count++;
                    } else if (bt_point.getText().toString().equals(str)) {
                        point_count++;
                    }
                }

                if (et_define.getText().toString().substring(0, 1).equals(".")) {
                    showToast("数据不合法");
                } else if (negative_count > 1 || point_count > 1) {
                    showToast("数据不合法");
                } else if (et_define.getText().toString().length() > 1 && et_define.getText().toString().substring(0, 1).equals(bt_negative.getText().toString())) {
                    if (point_count > 0) {
                        showToast("数据不合法");
                    } else {
                        number_str = et_define.getText().toString();
                        activity.change.conversion();
                    }
                } else if (et_define.getText().toString().length() > 1 && !et_define.getText().toString().substring(0, 1).equals(bt_negative.getText().toString())
                        && (negative_count > 0 || point_count > 1)) {
                    showToast("数据不合法");
                } else {
                    number_str = et_define.getText().toString();
                    // 调方法转换
                    activity.change.conversion();
                }
            } else {
                for (EditText et : editTexts_obj) {
                    if (et.getId() != R.id.et_select_input) {
                        et.setText("");
                    }
                }
                number_str = "";
            }
        } else {
            showToast("请先在左侧输入数据");
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    // 重写按钮点击方法
    @Override
    public void onClick(View v) {
        for (Button bt : buttons_obj) {
            if (bt == v) {
                // 如果焦点在et_select_input输入框
                if (et_select_input.hasFocus()) {
                    if (v.getId() == R.id.bt_delete) {
                        if (et_select_input_str.length() != 0) {
                            et_select_input_str = et_select_input_str.substring(0, et_select_input_str.length() - 1);
                            activity.forbid.et_select_input();
                            et_select_input.setText(et_select_input_str);
                        }
                    } else {
                        et_select_input_str += bt.getText();
                        activity.forbid.et_select_input();
                        et_select_input.setText(et_select_input_str);
                    }
                    activity.change.conversion();
                } else {
                    // 焦点不在et_select_input输入框
                    if (bt.getId() != R.id.bt_delete) {
                        // 其他按钮
                        if (R.id.bt_0 == bt.getId() && number_str.length() == 0) {
                            number_str = "0";
                        } else {
                            if ("0".equals(number_str)) {
                                if (bt.getId() == bt_point.getId()) {
                                    number_str += bt.getText();
                                } else {
                                    number_str = bt.getText().toString();
                                }
                            } else {
                                number_str += bt.getText();
                            }
                        }
                        //number_str的值就要判断并转换
                        activity.change.conversion();
                        activity.forbid.negative_point();

                    } else if (bt.getId() == R.id.bt_delete) {
                        // 删除按钮
                        if (number_str.length() != 0) {
                            number_str = number_str.substring(0, number_str.length() - 1);

                            if (number_str.length() == 0) {
                                for (EditText et : editTexts_obj) {
                                    if (et.getId() != R.id.et_select_input) {
                                        et.setText("");
                                    }
                                }
                                et_define.setText("");
                            }
                            activity.forbid.negative_point();
                            // 调方法转换
                            activity.change.conversion();
                        }
                    }

                }

            }

        }

    }

}
