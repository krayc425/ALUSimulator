/**
 * 模拟ALU进行整数和浮点数的四则运算
 * @author 141210026_宋奎熹
 *
 */

public class ALU {

	/**
	 * 生成十进制整数的二进制补码表示。
	 * 例：integerRepresentation("9", 8)
	 * @param number 十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制补码表示的长度
	 * @return number的二进制补码表示，长度为length
	 */
	public String integerRepresentation (String number, int length) {
		String result = "";
		String tmpNum;
		boolean isMinus;
		if(number.charAt(0) == '-'){
			isMinus = true;
			tmpNum = number.substring(1);
		}else{
			isMinus = false;
			tmpNum = number;
		}
		//下面对绝对值进行处理
		int n = Integer.valueOf(tmpNum);
		while(n >= 1){
			result = String.valueOf(n % 2) + result;
			n = (n - n % 2) / 2;
		}
		//若是负数,取反加一
		if(isMinus){
			result = oneAdder(negation(result)).substring(1, result.length() + 1);
		}
		//补全到 length 位
		while(result.length() < length){
			if(isMinus){
				result = "1" + result;
			}else{
				result = "0" + result;
			}
		}
		return result;
	}

	/**
	 * 生成十进制浮点数的二进制表示。
	 * 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
	 * 舍入策略为向0舍入。
	 * 例：floatRepresentation("11.375", 8, 11)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		String result = "";
		int n;
		//注意:以小数点分隔,必须加双右斜杠
		String strs[] = number.split("\\.");
		if (strs[0].charAt(0) != '-') {
			result = "0" + result;
			n = Integer.valueOf(strs[0]);
		} else {
			result = "1" + result;
			n = Integer.valueOf(strs[0].substring(1));
		}
		//判断是否是0?若是零直接返回
		boolean isZero = true;
		for (String str : strs) {
			if (Integer.valueOf(str) != 0) {
				isZero = false;
				break;
			}
		}
		if (isZero) {
			while (result.length() < 1 + eLength + sLength) {
				result = result + "0";
			}
			return result;
		}
		//生成整数部分的二进制表示
		String beforeDot = "";
		if (n != 0) {
			while (n >= 1) {
				beforeDot = String.valueOf(n % 2) + beforeDot;
				n = (n - n % 2) / 2;
			}
		}
		//生成小数部分的二进制表示(若有)
		String afterDot = "";
		if (strs.length > 1) {
			float m = (float) (Integer.valueOf(strs[1])) * (float) Math.pow(10, -strs[1].length());
			int i = strs[1].length();
			if (m == 0) {
				afterDot = allZeroWithLength(eLength + sLength + 1);
			} else {
				//最后一个1是为了能最后向0舍入
				do {
					if ((m *= 2) >= 1) {
						m -= 1;
						afterDot = afterDot + "1";
					} else {
						afterDot = afterDot + "0";
					}
					i--;
				} while (m != 1 && beforeDot.length() + afterDot.length() <= eLength + sLength + 1 + 1);
			}
		}
		//TODO
		//是否要规格化?
		//拼接整数和小数,且算出指数
		int e;
		String exponent;
		int bias = bias(eLength);
		if (beforeDot.equals("")) {
			e = normalize(afterDot);
			//TODO
			if(bias - e < 0){
				//反规格化
				System.out.println(bias - e);
				System.out.println(afterDot);
				afterDot = afterDot.substring(normalize(afterDot) - 1);
				while(afterDot.length() < sLength){
					afterDot = afterDot + "0";
				}
				return result + allZeroWithLength(eLength) + afterDot;
			}
			exponent = integerRepresentation((bias - e) + "", eLength);
		} else {
			e = beforeDot.length() - 1;
			exponent = integerRepresentation((bias + e) + "", eLength);
		}
		if (strs.length > 1) {
			if (beforeDot.equals("")) {
				afterDot = leftShift(afterDot, e);
				result = result + exponent + afterDot;
			} else {
				result = result + exponent + beforeDot.substring(1) + afterDot;
			}
		} else {
			result = result + exponent + (beforeDot.equals("") ? "" : beforeDot.substring(1));
		}
		if (result.length() > 1 + eLength + sLength) {
			result = result.substring(0, 1 + eLength + sLength);
		}
		//+-Inf,NaN
		if (exponent.equals(allOneWithLength(exponent.length()))) {
			if (result.substring(1 + eLength, result.length()).equals(allZeroWithLength(1 + eLength + sLength))) {
				return result.charAt(0) == '0' ? "+Inf" : "-Inf";
			} else {
				return "NaN";
			}
		}
		return result;
	}

	/**
	 * 生成十进制浮点数的IEEE 754表示，要求调用floatRepresentation(String, int, int)实现。
	 * 例：ieee754("11.375", 32)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制表示的长度，为32或64
	 * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String ieee754 (String number, int length) {
		if(length == 32){
			return floatRepresentation(number, 8, 23);
		}else if(length == 64){
			return floatRepresentation(number, 11, 52);
		}else {
			return "";
		}
	}

	/**
	 * 计算二进制补码表示的整数的真值。
	 * 例：integerTrueValue("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 */
	public String integerTrueValue (String operand) {
		int num = 0;
		for (int i = 0; i < operand.length(); i++) {
			if(i == 0){
				num -= (operand.charAt(i) - 48) * Math.pow(2, operand.length() - 1);
			}else{
				num += (operand.charAt(i) - 48) * Math.pow(2, operand.length() - 1 - i);
			}
		}
		return String.valueOf(num);
	}

	/**
	 * 计算二进制原码表示的浮点数的真值。
	 * 例：floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand 二进制表示的操作数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”， NaN表示为“NaN”
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		//先记录是否为负数
		boolean isMinus = false;
		if(operand.charAt(0) == '1'){
			isMinus = true;
		}
		String exponent = operand.substring(1, 1 + eLength);
		String tailNum = operand.substring(1 + eLength);
//		System.out.println(exponent);
//		System.out.println(tailNum);
		//正负无穷大/NaN
		if(exponent.equals(allOneWithLength(eLength))){
			if(tailNum.equals(allZeroWithLength(sLength))) {
				if (operand.charAt(0) == '0') {
					return "+Inf";
				} else {
					return "-Inf";
				}
			}else{
				return "NaN";
			}
		}
		String bias = integerRepresentation(String.valueOf(bias(eLength)), eLength);
		//零,反规格化数
		if(exponent.equals(allZeroWithLength(eLength))){
			//0
			if(tailNum.equals(allZeroWithLength(sLength))){
				return "0";
			}else{
				//反规格化
				String exp = integerSubtraction(allZeroWithLength(eLength-1) + "1", bias, eLength).substring(1);
//				System.out.println(integerTrueValue(exp));
				tailNum = "0" + tailNum;
//				System.out.println(tailNum);
				double result = 0;
				int dotPos = tailNum.length();
				for (int i = dotPos - 1; i >= 0; i--) {
					result += (Math.pow(2, -i) * (tailNum.charAt(i) - 48));
				}
				result *= Math.pow(2.0, Integer.valueOf(integerTrueValue(exp)));
				return isMinus ? "-" + String.valueOf(result) : String.valueOf(result);
			}
		}
		//其他情况
		//算出指数(先是0+全1串,计算时候减去)
		exponent = adder(exponent, negation(bias), '1', eLength).substring(1);
		//补上尾数前面的1
		tailNum = "1" + tailNum;
		int dotPos = 1;
		if(exponent.charAt(0) == '0'){
			dotPos += Integer.valueOf(integerTrueValue(exponent));
		}
		//开始计算结果
		float result = 0;
		while(tailNum.length() < dotPos){
			tailNum = tailNum + "0";
		}
		for (int i = dotPos - 1; i >= 0; i--) {
			result += (float)(Math.pow(2, dotPos - 1 - i) * (tailNum.charAt(i) - 48));
		}
		for (int i = dotPos; i < sLength; i++) {
			result += (float)(Math.pow(2, dotPos - 1 - i) * (tailNum.charAt(i) - 48));
		}
		return isMinus ? "-" + String.valueOf(result) : String.valueOf(result);
	}

	/**
	 * 按位取反操作。
	 * 例：negation("00001001")
	 * @param operand 二进制表示的操作数
	 * @return operand按位取反的结果
	 */
	public String negation (String operand) {
		String result = "";
		for(int i = 0; i < operand.length(); i++){
			if(operand.charAt(i) == '0'){
				result = result + "1";
			}else{
				result = result + "0";
			}
		}
		return result;
	}

	/**
	 * 左移操作。
	 * 例：leftShift("00001001", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 左移的位数
	 * @return operand左移n位的结果
	 */
	public String leftShift (String operand, int n) {
		String result = operand.substring(n, operand.length());
		while(result.length() < operand.length()){
			result = result + "0";
		}
		return result;
	}

	/**
	 * 逻辑右移操作。
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand逻辑右移n位的结果
	 */
	public String logRightShift (String operand, int n) {
		String result;
		if(n < operand.length()) {
			result = operand.substring(0, operand.length() - n);
		}else{
			result = "";
		}
		while (result.length() < operand.length()) {
			result = "0" + result;
		}
		return result;
	}

	/**
	 * 算术右移操作。
	 * 例：ariRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand算术右移n位的结果
	 */
	public String ariRightShift (String operand, int n) {
		if(n < operand.length()) {
			String result = operand.substring(0, operand.length() - n);
			while (result.length() < operand.length()) {
				result = (result.charAt(0) == '0' ? "0" : "1") + result;
			}
			return result;
		}else{
			String result = "";
			while (result.length() < operand.length()) {
				result = (operand.charAt(0) == '0' ? "0" : "1") + result;
			}
			return result;
		}
	}

	/**
	 * 全加器，对两位以及进位进行加法运算。
	 * 例：fullAdder('1', '1', '0')
	 * @param x 被加数的某一位，取0或1
	 * @param y 加数的某一位，取0或1
	 * @param c 低位对当前位的进位，取0或1
	 * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
	 */
	public String fullAdder (char x, char y, char c) {
		char si = xorGate(xorGate(x, y) , c);
		char ci = orGate(orGate(andGate(x, c), andGate(y, c)), andGate(x, y));
		return ci + "" + si;
	}

	/**
	 * 4位先行进位加法器。
	 * 要求采用 fullAdder 来实现
	 * 例：claAdder("1001", "0001", '1')
	 * @param operand1 4位二进制表示的被加数
	 * @param operand2 4位二进制表示的加数
	 * @param c 低位对当前位的进位，取0或1
	 * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环获得
	 */
	public String claAdder (String operand1, String operand2, char c) {
		char[] p = new char[5];
		char[] g = new char[5];
		//获得Pi,Gi
		for (int i = 1; i <= 4; i++) {
			p[i] = orGate(operand1.charAt(4-i), operand2.charAt(4-i));
			g[i] = andGate(operand1.charAt(4-i), operand2.charAt(4-i));
		}
		//获得Ci
		char[] ci = new char[5];
		ci[0] = c;
		ci[1] = orGate(g[1], andGate(p[1], c));
		ci[2] = orGate(orGate(g[2], andGate(p[2], g[1])), andGate(c, andGate(p[2], p[1])));
		ci[3] = orGate(orGate(orGate(g[3], andGate(p[3], g[2])), andGate(g[1], andGate(p[3], p[2]))),
				andGate(c, andGate(p[3], andGate(p[2], p[1]))));
		ci[4] = orGate(orGate(orGate(orGate(g[4], andGate(p[4], g[3])), andGate(g[2], andGate(p[4], p[3]))),
				andGate(g[1], andGate(p[4], andGate(p[3], p[2])))), andGate(c , andGate(p[4], andGate(p[3], andGate(p[2], p[1])))));
		//获得Si
		String result = "";
		for (int i = 1; i <= 4; i++) {
			result = fullAdder(operand1.charAt(4-i), operand2.charAt(4-i), ci[i-1]).charAt(1) + result;
		}
		return ci[4] + result;
	}

	/**
	 * 加一器,实现操作数加 1 的运算。
	 * 需要采用与门、或门、异或门等模拟,不可以直接调用 fullAdder、claAdder、adder、integerAddition 方法。
	 * 例：oneAdder("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
	 */
	public String oneAdder (String operand) {
		String result = "";
		char si;
		char ci = '1';
		for (int i = 0; i < operand.length(); i++) {
			si = xorGate(operand.charAt(operand.length() - i - 1), ci);
			result = si + result;
			ci = andGate(ci, operand.charAt(operand.length() - i - 1));
		}
		//判断是否溢出
		if(ci == '1'){
			result = "1" + result;
		}else{
			result = "0" + result;
		}
		return result;
	}

	/**
	 * 加法器，要求调用claAdder(String, String, char)方法实现。
	 * 例：adder("0100", "0011", ‘0’, 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param c 最低位进位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		//扩展两个操作数到 length 长度
		String tmpO1 = operand1;
		String tmpO2 = operand2;
		while(tmpO1.length() < length){
			if(tmpO1.charAt(0) == '1'){
				tmpO1 = "1" + tmpO1;
			}else{
				tmpO1 = "0" + tmpO1;
			}
		}
		while(tmpO2.length() < length){
			if(tmpO2.charAt(0) == '1'){
				tmpO2 = "1" + tmpO2;
			}else{
				tmpO2 = "0" + tmpO2;
			}
		}
		//计算结果
		String result = "";
		char ci = c;
		int i = 0;
		do{
			String ts = claAdder(tmpO1.substring(length - i - 4, length - i), tmpO2.substring(length - i - 4, length - i), ci);
			result = ts.substring(1, 5) + result;
			ci = ts.charAt(0);
			i += 4;
		}while(i <= length - 4);
		while(result.length() < length){
			if(result.charAt(0) == '1'){
				result = "1" + result;
			}else{
				result = "0" + result;
			}
		}
		//判断是否溢出
		boolean isOverflow = false;
		if(result.charAt(0) != tmpO1.charAt(0) && result.charAt(0) != tmpO2.charAt(0) && tmpO1.charAt(0) == tmpO2.charAt(0)){
			isOverflow = true;
		}
		if(isOverflow){
			result = "1" + result;
		}else{
			result = "0" + result;
		}
		return result;
	}

	/**
	 * 整数加法，要求调用 adder 方法实现。
	 * 例：integerAddition("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		//直接调用,并把进位设为0
		return adder(operand1, operand2, '0', length);
	}

	/**
	 * 整数减法，要求调用 adder 方法实现。
	 * 例：integerSubtraction("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被减数
	 * @param operand2 二进制补码表示的减数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		//把第二个数取反,并把进位设为1
		return adder(operand1, negation(operand2), '1', length);
	}

	/**
	 * 整数乘法，使用Booth算法实现，可调用 adder 等方法。
	 * 例：integerMultiplication("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被乘数
	 * @param operand2 二进制补码表示的乘数
	 * @param length 存放操作数的寄存器的长度，为4的倍 数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		String result = "";
		//判断是否为0
		if(operand1.equals(allZeroWithLength(operand1.length())) || operand2.equals(allZeroWithLength(operand2.length()))){
			while (result.length() < length + 1){
				result = result + "0";
			}
			return result;
		}
		//扩展两个操作数到 length 长度
		String x1 = operand1;
		String y = operand2;
		while(x1.length() < length){
			if(x1.charAt(0) == '1'){
				x1 = "1" + x1;
			}else{
				x1 = "0" + x1;
			}
		}
		while(y.length() < length){
			if(y.charAt(0) == '1'){
				y = "1" + y;
			}else{
				y = "0" + y;
			}
		}
		String x2 = oneAdder(negation(x1)).substring(1, length + 1);
		y = y + "0";
		int i = y.length() - 1;
		String product = allZeroWithLength(length);
		//开始布思乘法
		while(i > 0){
			switch (y.charAt(i) - y.charAt(i - 1)){
				case 0:
					break;
				case 1:
					product = adder(x1, product.substring(0, length), '0', length).substring(1, length + 1) + product.substring(length, 2 * length - i);
					break;
				case -1:
					product = adder(x2, product.substring(0, length), '0', length).substring(1, length + 1) + product.substring(length, 2 * length - i);
					break;
			}
			//右移
			if(product.charAt(0) == '0'){
				product = "0" + product;
			}else{
				product = "1" + product;
			}
			i--;
		}
		//判断溢出
		boolean isOverflow = false;
		for (int j = 0; j < length; j++) {
			if(product.charAt(j) != product.charAt(length)){
				isOverflow = true;
				break;
			}
		}
		if(isOverflow){
			result = "1" + product.substring(length, 2 * length);
		}else{
			result = "0" + product.substring(length, 2 * length);
		}
		return result;
	}

	/**
	 * 整数的不恢复余数除法，可调用 adder 等方法实现。
	 * 例：integerDivision("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被除数
	 * @param operand2 二进制补码表示的除数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，最后length位为余数
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		//异常部分
		if(operand1.equals(allZeroWithLength(operand1.length())) && operand2.equals(allZeroWithLength(operand2.length()))){
			return "NaN";
		}else if(operand1.equals(allZeroWithLength(operand1.length())) && !operand2.equals(allZeroWithLength(operand2.length()))){
			return xorGate(operand1.charAt(0), operand2.charAt(0)) + allZeroWithLength(2 * length);
		}else if(!operand1.equals(allZeroWithLength(operand1.length())) && operand2.equals(allZeroWithLength(operand2.length()))){
			return (xorGate(operand1.charAt(0), operand2.charAt(0)) == '0') ? "+Inf" : "-Inf";
		}else{
			//继续处理
			//初始化商和余数
			String quotient = operand1;
			while(quotient.length() < length){
				if(quotient.charAt(0) == '1'){
					quotient = "1" + quotient;
				}else{
					quotient = "0" + quotient;
				}
			}
			String remainder;
			if(quotient.charAt(0) == '0'){
				remainder = allZeroWithLength(length);
			}else{
				remainder = allOneWithLength(length);
			}
			//开始进行除法
			int i = 0;
			char tmpChar = '\0';
			do{
				//非第一次
				if(i != 0){
					//余数左移
					remainder = remainder.substring(1, length) + quotient.charAt(0);
					//商左移
					quotient = quotient.substring(1, length) + tmpChar;
				}
				//判断余数和商是否异号?
				if(xorGate(operand2.charAt(0), remainder.charAt(0)) == '0'){
					//相同:加上除数
					remainder = adder(remainder, negation(operand2), '1', length).substring(1, length + 1);
				}else{
					//不同:减去除数
					remainder = adder(remainder, operand2, '0', length).substring(1, length + 1);
				}
				//给商上新位
				if(xorGate(remainder.charAt(0), operand2.charAt(0)) == '0'){
					//同号:上1
					tmpChar = '1';
				}else{
					//异号:上0
					tmpChar = '0';
				}
				i++;
			}while (i <= length);
			//修正商
			//先左移
			quotient = quotient.substring(1, length) + tmpChar;
			//若为负数,加1
			if(xorGate(operand1.charAt(0), operand2.charAt(0)) == '1'){
				quotient = oneAdder(quotient).substring(1, length + 1);
			}
			//再修正余数
			//若余数和被除数异号
			if(xorGate(operand1.charAt(0), remainder.charAt(0)) == '1'){
				if(xorGate(operand1.charAt(0), operand2.charAt(0)) == '1') {
					//若被除数和除数异号,减一个除数
					remainder = adder(remainder, negation(operand2), '1', length).substring(1, length + 1);
				}else{
					//若被除数和除数同号,加一个除数
					remainder = adder(remainder, operand2, '0', length).substring(1, length + 1);
				}
			}
			boolean isOverflow = false;
			if(quotient.charAt(0) != operand1.charAt(0) &&
					quotient.charAt(0) == xorGate(operand1.charAt(0), operand2.charAt(0))){
				isOverflow = true;
			}
			return (isOverflow ? "1" : "0") + quotient + remainder;
		}
	}

	/**
	 * 带符号整数加法
	 * 可以调用 adder 等方法,但不能直接将操作数转换为补码 后使用 integerAddition、integerSubtraction 来实现。
	 * 例：signedAddition("1100", "1011", 8)
	 * @param operand1 二进制原码表示的被加数，其中第1位为符号位
	 * @param operand2 二进制原码表示的加数，其中第1位为符号位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，需要将其长度扩展到length
	 * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，后length位是相加结果
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		//扩展两个操作数到 length 长度
		char sign1 = operand1.charAt(0);
		char sign2 = operand2.charAt(0);
		boolean isSameSign = (sign1 == sign2);
		operand1 = operand1.substring(1);
		operand2 = operand2.substring(1);
		while(operand1.length() < length){
			operand1 = "0" + operand1;
		}
		while(operand2.length() < length){
			operand2 = "0" + operand2;
		}
		//有相同符号位,做加法
		String result = "";
		boolean isOverflow = false;
		if(isSameSign){
			String tmp = "";
			char ci = '0';
			int i = 0;
			do{
				String ts = claAdder(operand1.substring(length - i - 4, length - i), operand2.substring(length - i - 4, length - i), ci);
				tmp = ts.substring(1, 5) + tmp;
				ci = ts.charAt(0);
				i += 4;
			}while(i <= length - 4);
			while(tmp.length() < length){
				if(tmp.charAt(0) == '1'){
					tmp = "1" + tmp;
				}else{
					tmp = "0" + tmp;
				}
			}
			//有进位,溢出
			if(ci == '1'){
				isOverflow = true;
			}
			if(isOverflow){
				result = "1";
			}else{
				result = "0";
			}
			result = result + sign1 + tmp;
		}else{
			String tmp = "";
			char ci = '1';
			int i = 0;
			operand2 = negation(operand2);
			do{
				String ts = claAdder(operand1.substring(length - i - 4, length - i), operand2.substring(length - i - 4, length - i), ci);
				tmp = ts.substring(1, 5) + tmp;
				ci = ts.charAt(0);
				i += 4;
			}while(i <= length - 4);
			while(tmp.length() < length){
				if(tmp.charAt(0) == '1'){
					tmp = "1" + tmp;
				}else{
					tmp = "0" + tmp;
				}
			}
			//若溢出,则说明正确
			if(ci == '1'){
				result = "0" + sign1 + tmp;
			}else{
				//否则计算补码
				tmp = oneAdder(negation(tmp)).substring(1);
				result = "0" + (sign2 == '0' ? '0' : '1') + tmp;
			}
		}
		return result;
	}

	/**
	 * 浮点数加法，可调用integerAddition(String, String, char, int)等方法实现。
	 * 例：floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被加数
	 * @param operand2 二进制表示的加数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		if(operand1.substring(1).equals(allZeroWithLength(operand1.length()-1))){
			return "0" + operand2;
		}else if(operand2.substring(1).equals(allZeroWithLength(operand2.length()-1))){
			return "0" + operand1;
		}else{
			//X,Y都不等于0
//			System.out.println("*****************************");
//			System.out.println(operand1);
//			System.out.println(operand2);
			char sign1 = operand1.charAt(0);
			char sign2 = operand2.charAt(0);
			String exp1 = operand1.substring(1, 1 + eLength);
			String exp2 = operand2.substring(1, 1 + eLength);
			String tail1 = "1" + operand1.substring(1 + eLength);
			String tail2 = "1" + operand2.substring(1 + eLength);
//			System.out.println("e1 " + exp1);
//			System.out.println("e2 " + exp2);
//			System.out.println("t1 " + tail1);
//			System.out.println("t2 " + tail2);
			//补全尾数到sL+gL 长度
			while(tail1.length() <= sLength + gLength){
				tail1 = tail1 + "0";
			}
			while(tail2.length() <= sLength + gLength){
				tail2 = tail2 + "0";
			}
//			System.out.println("t1 " + tail1);
//			System.out.println("t2 " + tail2);
			//判断指数是否相等
			if(!exp1.equals(exp2)){
				//deltaE: exp1 - exp2
				String deltaE = integerSubtraction(exp1, exp2, eLength).substring(1);
				//如果exp1 > exp2
				if(deltaE.charAt(0) == '0'){
					//增长 exp2
					int i = Integer.valueOf(integerTrueValue(deltaE));
					boolean significandIsZero = false;
					while(i > 0){
						exp2 = oneAdder(exp2).substring(1);
						tail2 = logRightShift(tail2, 1);
						if(tail2.equals(allZeroWithLength(tail2.length()))){
							significandIsZero = true;
						}
						if(significandIsZero){
							return "0" + operand1;
						}
						i--;
					}
				}else{
					//增长 exp1
					int i = Math.abs(Integer.valueOf(integerTrueValue(deltaE)));
					boolean significandIsZero = false;
					while(i > 0){
						exp1 = oneAdder(exp1).substring(1);
						tail1 = logRightShift(tail1, 1);
						if(tail1.equals(allZeroWithLength(tail1.length()))){
							significandIsZero = true;
						}
						if(significandIsZero){
							return "0" + operand2;
						}
						i--;
					}
				}
			}
			//此时指数已经相等
			int l = 2 + 4 * ((sLength + gLength + 1) / 4);
//			System.out.println(l);
//			System.out.println("e1 " + exp1);
//			System.out.println("e2 " + exp2);
//			System.out.println("t1 " + sign1 + tail1);
//			System.out.println("t2 " + sign2 + tail2);
			String significand = signedAddition(sign1 + tail1, sign2 + tail2, l+2);
			//有溢出,指数加1
			if(significand.charAt(0) == '1'){
				exp1 = oneAdder(exp1).substring(1);
				significand = logRightShift(significand.substring(1), significand.length() - 1);
			}
//			System.out.println(significand +" "+ significand.length());
			if(significand.substring(1).equals(allZeroWithLength(l+2))){
				return allZeroWithLength(2+eLength+sLength);
			}
			//是否上溢?
			//TODO
			boolean isOverFlow = false;
			//第一位:是否溢出?
			if (significand.charAt(0) == '1'){
				significand = logRightShift(significand, 1);
				exp1 = integerSubtraction(exp1, integerRepresentation("1",eLength), eLength).substring(1);
				if(exp1.charAt(0) == '1'){
					isOverFlow = true;
				}else{
					exp1 = exp1.substring(1);
				}
			}else{
				significand = significand.substring(1);
			}
//			System.out.println(significand.length());
			//第二位:significand 自带符号
			char sign = significand.charAt(0);
			significand = significand.substring(1);
			int deltaE = normalize(significand);
//			exp1 = integerSubtraction(exp1, integerRepresentation(String.valueOf(deltaE), eLength), eLength).substring(1);
//			System.out.println(deltaE);
//			System.out.println(exp1);
			significand = leftShift(significand, deltaE);
			//是否下溢?
			//TODO
//			System.out.println(significand);
			significand = significand.substring(0, sLength);
			return (isOverFlow ? "1" : "0") + sign + exp1 + significand;
		}
	}

	/**
	 * 浮点数减法，可调用floatAddition(String, String, int, int, int)方法实现。
	 * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被减数
	 * @param operand2 二进制表示的减数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		//直接取反第二个操作数的符号位然后调用floatAddition
		operand2 = notGate(operand2.charAt(0)) + operand2.substring(1);
		return floatAddition(operand1, operand2, eLength, sLength, gLength);
	}

	/**
	 * 浮点数乘法，可调用 integerMultiplication 等方法实现。
	 * 例：floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被乘数
	 * @param operand2 二进制表示的乘数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		//判断符号
		char sign = xorGate(operand1.charAt(0), operand2.charAt(0));
		//判断结果是否为零
		if (operand1.substring(1).equals(allZeroWithLength(operand1.length()-1)) ||
				operand2.substring(1).equals(allZeroWithLength(operand2.length()-1))) {
			return "0" + sign + allZeroWithLength(eLength + sLength);
		}
		//计算偏移量
		int bias = bias(eLength);
		String exp1 = operand1.substring(1, 1 + eLength);
		String exp2 = operand2.substring(1, 1 + eLength);
//		System.out.println("exp1 " + exp1);
//		System.out.println("exp2 " + exp2);
		//指数相加
		String exp = signedAddition("0" + exp1, "0" + exp2, 4 + 4 * ((eLength) / 4)).substring(2);
//		System.out.println("before -bias " + exp);
		//减去偏移量
		exp = signedAddition("0" + exp, "1" + integerRepresentation(String.valueOf(bias), exp.length()), 4 + 4 * ((eLength) / 4));
		//指数上溢
		if(exp.charAt(0) == '1'){
			return "overflow";
		}
//		System.out.println("after  -bias " + exp.substring(2));
		exp = exp.substring(exp.length() - eLength);
		//相乘尾数
		String num1 = "01" + operand1.substring(1 + eLength);
		String num2 = "01" + operand2.substring(1 + eLength);
//		System.out.println(num1);
//		System.out.println(num2);
		//ti 是 numi 的4的整数倍长度
		int t1 = 4 + 4 * (num1.length() / 4);
		int t2 = 4 + 4 * (num2.length() / 4);
		//最后布斯乘法要去掉多少个0
		int cancelZeroNum = 2;
		while(num1.length() < t1){
			num1 = "0" + num1;
			cancelZeroNum++;
		}
		while(num2.length() < t2){
			num2 = "0" + num2;
			cancelZeroNum++;
		}
		//用修正过的尾数做布斯乘法
		String tmpR = integerMultiplication(num1, num2, 2 * (sLength + 2 + (cancelZeroNum - 1) / 2)).substring(1 + cancelZeroNum);
//		System.out.println(tmpR);
		//规格化尾数
		int deltaExp = (normalize(tmpR));
		tmpR = tmpR.substring(deltaExp);
//		System.out.println(tmpR);
		//再给指数加/减偏移了多少
		//TODO
//		System.out.println(deltaExp);
		if(deltaExp == 1) {
			exp = integerAddition(exp, integerRepresentation(String.valueOf(deltaExp), eLength), eLength);
		}else {
			exp = integerAddition(exp, allZeroWithLength(eLength), eLength);
		}
//		System.out.println(exp);
		//判断是否上溢?
		boolean isOverFlow = exp.charAt(0) == '1';
//		System.out.println((isOverFlow ? "1" : "0") + sign + exp.substring(1) + tmpR.substring(0, sLength));
		return (isOverFlow ? "1" : "0") + sign + exp.substring(1) + tmpR.substring(0, sLength);
	}

	/**
	 * 浮点数除法，可调用 integerDivision 等方法实现。
	 * 例：floatDivision("00111110 111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被除数
	 * @param operand2 二进制表示的除数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		//结果是0
		if(operand1.substring(1, 1 + eLength).equals(allZeroWithLength(eLength))){
			if(operand1.substring(1 + eLength, operand1.length()).equals(allZeroWithLength(sLength))) {
				return allZeroWithLength(2 + eLength + sLength);
			}else{
				//结果是无穷大
				if(operand1.charAt(0) == operand2.charAt(0)){
					return "+Inf";
				}else{
					return "-Inf";
				}
			}
		}else{
			//正常情况
			//判断符号
			char sign = xorGate(operand1.charAt(0), operand2.charAt(0));
			//计算偏移量
			int bias = bias(eLength);
			String exp1 = operand1.substring(1, 1 + eLength);
			String exp2 = operand2.substring(1, 1 + eLength);
			//指数相减
			String exp = signedAddition("0" + exp1, "1" + exp2, 4 + 4 * ((eLength) / 4)).substring(2);
//			System.out.println("before -bias " + exp);
			//加上偏移量
			exp = signedAddition("0" + exp, "0" + integerRepresentation(String.valueOf(bias), exp.length()), 4 + 4 * ((eLength) / 4)).substring(2);
			exp = exp.substring(exp.length() - eLength);
//			System.out.println("after  -bias " + exp);
			String num1 = "1" + operand1.substring(1 + eLength) + allZeroWithLength(sLength);
			String num2 = "1" + operand2.substring(1 + eLength);
			int t1 = 4 + 4 * (num1.length() / 4);
			int t2 = 4 + 4 * (num2.length() / 4);
//			System.out.println("t1 "+ t1);
//			System.out.println("t2 "+ t2);
			while(num1.length() < t1){
				num1 = "0" + num1;
			}
			while(num2.length() < t2){
				num2 = "0" + num2;
			}
			//尾数相除
			String tmpR = integerDivision(num1, num2, t1 + t2).substring(1, 1 + t1 + t2);
//			System.out.println(tmpR.substring(tmpR.length() - sLength - 1));
			tmpR = tmpR.substring(normalize(tmpR)).substring(0, sLength);
			return "0" + sign + exp + tmpR;
		}
	}

	/*
	 * 以下为自己补充的辅助方法
	 */

	//与门
	private char andGate(char a, char b){
		if(a == '1' && b == '1'){
			return '1';
		}else{
			return '0';
		}
	}

	//或门
	private char orGate(char a, char b){
		if(a == '1' || b == '1'){
			return '1';
		}else{
			return '0';
		}
	}

	//非门
	private char notGate(char c){
		return c == '0' ? '1' : '0';
	}

	//异或门
	private char xorGate(char a, char b){
		if(a - b == 0){
			return '0';
		}else{
			return '1';
		}
	}
	
	//返回长为n的全0串
	private String allZeroWithLength(int n){
		String result = "";
		while(result.length() < n){
			result = result + "0";
		}
		return result;
	}

	//返回长为n的全1串
	private String allOneWithLength(int n){
		String result = "";
		while(result.length() < n){
			result = result + "1";
		}
		return result;
	}

	//计算偏移量
	private int bias(int eLength){
		return (int) Math.pow(2, eLength - 1) - 1;
	}

	//规格化一个数,返回值为需要在指数上加的数值(可正可负)
	private int normalize(String num){
		//TODO
		int i = 0;
		char c = '0';
		while(i < num.length()){
			if(num.charAt(i) == c){
				c = num.charAt(i);
				i++;
			}else{
				i++;
				break;
			}
		}
		return i;
	}
}
