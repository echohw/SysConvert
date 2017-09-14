package com.example.sysconvert;

class Change_point {
	Listener listener;
	private String number_str, et_select_input_str, temp;
	private int flag_jinzhi, num_integer;
	private double num_decimal;
	private String digit = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	Change_point(Listener listener) {
		this.listener = listener;
	}

	private void init() {
		number_str = listener.number_str;
		et_select_input_str = listener.et_select_input_str;
		flag_jinzhi = listener.flag_jinzhi;

		number_str = number_str.toUpperCase();
		String[] strArray = number_str.split("\\.");

		// integer:保存切割后的整数部分
		String integer = strArray[0];
		// decimal:保存切割后的小数部分
		String decimal = strArray[1];

		// 将整数部分(integer)转换成int类型,并存储
		num_integer = Integer.parseInt(integer, flag_jinzhi);
		// 将计算后的小数存储
		num_decimal = 0;

		// 将小数部分切割成单个字符并转为String类型存储在str_arr数组中
		String[] str_arr = new String[decimal.length()];
		double[] dou_arr = new double[decimal.length()];

		// 将9以上的字符转换成对应的数字字符,并存储在str_arr中
		for (int i = 0; i < decimal.length(); i++) {
			String str = decimal.charAt(i) + "";
			if (str.matches("[A-Z]")) {
				str_arr[i] = decimal.charAt(i) - 'A' + 10 + "";
			} else {
				str_arr[i] = str;
			}
		}

		// 將字符转换成double类型,并存储在dou_arr中
		for (int i = 0; i < decimal.length(); i++) {
			dou_arr[i] = Double.parseDouble(str_arr[i]);

			num_decimal += (dou_arr[i] / Math.pow(flag_jinzhi, i + 1));
		}
	}

	private void other(int target_jinzhi) {
		temp = "";
		while (temp.length() < 64) {
			num_decimal *= target_jinzhi;
			String[] strArr_temp = String.valueOf(num_decimal).split("\\.");
			temp += digit.charAt(Integer.parseInt(strArr_temp[0]));
			if (num_decimal == (int) num_decimal) {
				break;
			}
			if (num_decimal > 1) {
				num_decimal -= (int) num_decimal;
			}
		}
	}

	String to_number_two_method() {
		init();
		other(2);
		return String.valueOf(Long.toBinaryString(num_integer)) + "." + temp;
	}

	String to_number_eight_method() {
		init();
		other(8);
		return String.valueOf(Long.toOctalString(num_integer)) + "." + temp;
	}

	String to_number_ten_method() {
		init();
		return String.valueOf(num_integer + num_decimal);
	}

	String to_number_sixteen_method() {
		init();
		other(16);
		return String.valueOf(Long.toHexString(num_integer)) + "." + temp;
	}

	String to_number_et_select_input_str_method() {
		init();
		other(Integer.parseInt(et_select_input_str));
		return String.valueOf(Long.toString(num_integer,
				Integer.parseInt(et_select_input_str)))
				+ "." + temp;
	}

}
