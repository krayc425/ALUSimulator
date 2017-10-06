import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kray on 16/5/21.
 */
public class ALUTest {

    private ALU alu;

    @Before
    public void setUp() throws Exception {
        alu = new ALU();
    }

    @Test
    public void testIntegerRepresentation() throws Exception {
        assertEquals("00001001",alu.integerRepresentation("9", 8));
        assertEquals("111111011",alu.integerRepresentation("-5", 9));
        assertEquals("1111011011",alu.integerRepresentation("-37", 10));
        assertEquals("000100", this.alu.integerRepresentation("4", 6));
        assertEquals("1100", this.alu.integerRepresentation("-4", 4));
        assertEquals("1010", this.alu.integerRepresentation("-6", 4));
        assertEquals("1111110000", this.alu.integerRepresentation("-16", 10));
    }

    //TODO
    @Test
    public void testFloatRepresentation() throws Exception {
        assertEquals("01000001001101100000000000000000",alu.floatRepresentation("11.375", 8, 23));
        assertEquals("11000001111010011",alu.floatRepresentation("-29.1875", 8, 8));
        assertEquals("00000000000000000",alu.floatRepresentation("0.0", 8, 8));
        assertEquals("000001100", this.alu.floatRepresentation("0.005859375", 4, 4));
        assertEquals("0011111010111100010000", this.alu.floatRepresentation("0.36768", 8, 13));
        assertEquals("01000010111101100111100010001010", this.alu.floatRepresentation("123.235435", 8, 23));
        assertEquals("11000001001101100000", this.alu.floatRepresentation("-11.375", 8, 11));

        assertEquals("000000001",alu.floatRepresentation("0.0009765625", 4, 4));
        assertEquals("000000010",alu.floatRepresentation("0.001953125",4,4));


    }

    @Test
    public void testIeee754() throws Exception {
        assertEquals("01000001001101100000000000000000",alu.ieee754("11.375", 32));
        assertEquals("01000010101111100000011000000000",alu.ieee754("95.01171875", 32));
        assertEquals("01000010101111111101001011110001",alu.ieee754("95.912", 32));
        assertEquals("01000001101110110111101110011110",alu.ieee754("23.43536",32));
        assertEquals("00111111000000000000000000000000",alu.ieee754("0.5",32));
    }

    @Test
    public void testIntegerTrueValue() throws Exception {
        assertEquals("9",alu.integerTrueValue("00001001"));
        assertEquals("-4",alu.integerTrueValue("1100"));
        assertEquals("-105",alu.integerTrueValue("10010111"));
        assertEquals("93",alu.integerTrueValue("01011101"));
        assertEquals("-1",alu.integerTrueValue("111111111"));
        assertEquals("31", alu.integerTrueValue("011111"));
        assertEquals("-1", alu.integerTrueValue("111111"));
        assertEquals("240", alu.integerTrueValue("000011110000"));
    }

    @Test
    public void testFloatTrueValue() throws Exception {
        assertEquals("NaN",alu.floatTrueValue("01111111101101100000", 8, 11));
        assertEquals("-Inf",alu.floatTrueValue("1111111110000000000", 8, 10));
        assertEquals("+Inf",alu.floatTrueValue("011111100", 6, 2));
        assertEquals("0",alu.floatTrueValue("100000", 2, 3));
        assertEquals("11.375",alu.floatTrueValue("01000001001101100000", 8, 11));
//        assertEquals("36.96",alu.floatTrueValue("01000010000100111101011100001000", 8, 23));
        assertEquals("-99.0625",alu.floatTrueValue("110000101100011000100000000000", 8, 21));
//        assertEquals("0.005859375", this.alu.floatTrueValue("000001100", 4, 4));
        assertEquals("11.375", this.alu.floatTrueValue("01000001001101100000000000000000", 8, 23));
        assertEquals("+Inf", this.alu.floatTrueValue("01111111100000000000000000000000", 8, 23));
        assertEquals("-Inf", this.alu.floatTrueValue("11111111100000000000000000000000", 8, 23));
        assertEquals("NaN", this.alu.floatTrueValue("11111111100000000000000100000000", 8, 23));
        assertEquals("62.0",this.alu.floatTrueValue("011001111",4,4));

//        assertEquals(floatTrueValue("000000111",4,4), this.alu.floatTrueValue("000000111",4,4));
        assertEquals(String.valueOf(Math.pow(2, -10)), this.alu.floatTrueValue("000000001",4,4));
//        alu.floatTrueValue("01000001001101100000", 8, 11);
//        alu.floatTrueValue("01000101111101100000", 8, 11);
//        alu.floatTrueValue("10111111101101100000", 8, 11);
//        alu.floatTrueValue("00111110001101100000", 8, 11);
//        alu.floatTrueValue("10111111110000000000000000000000", 8, 23);
//
//        alu.floatTrueValue("00000000000000000000", 8, 11);
//        alu.floatTrueValue("01111111100000000000", 8, 11);
//        alu.floatTrueValue("01111111111111000000", 8, 11);
//
//        alu.floatTrueValue("000010111", 4, 4);
//        alu.floatTrueValue("00000000000000000000000000000110", 8, 23);
    }

    @Test
    public void testNegation() throws Exception {
        assertEquals("11110110",alu.negation("00001001"));
        assertEquals("01011100",alu.negation("10100011"));
        assertEquals("00000000",alu.negation("11111111"));
        assertEquals("1",alu.negation("0"));
        assertEquals("00", this.alu.negation("11"));
        assertEquals("0101010101", this.alu.negation("1010101010"));
    }

    @Test
    public void testLeftShift() throws Exception {
        assertEquals("00011011",alu.leftShift("00011011", 0));
        assertEquals("00100100",alu.leftShift("00001001", 2));
        assertEquals("01000000",alu.leftShift("00100100", 4));
        assertEquals("00000000",alu.leftShift("01111101", 8));
        assertEquals("00100100", this.alu.leftShift("00001001", 2));
        assertEquals("000", this.alu.leftShift("000", 2));
        assertEquals("100", this.alu.leftShift("001", 2));
    }

    @Test
    public void testLogRightShift() throws Exception {
        assertEquals("00011011",alu.logRightShift("00011011", 0));
        assertEquals("00111101",alu.logRightShift("11110110", 2));
        assertEquals("00000010",alu.logRightShift("00100101", 4));
        assertEquals("00000000",alu.logRightShift("10010110", 8));
        assertEquals("000", this.alu.logRightShift("001", 2));
        assertEquals("00000010", this.alu.logRightShift("00001001", 2));
        assertEquals("00111101", this.alu.logRightShift("11110110", 2));
    }

    @Test
    public void testAriRightShift() throws Exception {
        assertEquals("00011011",alu.ariRightShift("00011011", 0));
        assertEquals("11111101",alu.ariRightShift("11110110", 2));
        assertEquals("00000010",alu.ariRightShift("00100101", 4));
        assertEquals("11111111",alu.ariRightShift("11110000", 8));
        assertEquals("00000010", this.alu.ariRightShift("00001001", 2));
        assertEquals("11111101", this.alu.ariRightShift("11110110", 2));
    }

    @Test
    public void testFullAdder() throws Exception {
        assertEquals("10",alu.fullAdder('1', '1', '0'));
        assertEquals("10",alu.fullAdder('0', '1', '1'));
        assertEquals("01",alu.fullAdder('0', '1', '0'));
        assertEquals("01",alu.fullAdder('1', '0', '0'));
        assertEquals("10", this.alu.fullAdder('1', '1', '0'));
        assertEquals("01", this.alu.fullAdder('1', '0', '0'));
        assertEquals("00", this.alu.fullAdder('0', '0', '0'));
        assertEquals("11", this.alu.fullAdder('1', '1', '1'));
    }

    @Test
    public void testClaAdder() throws Exception {
        assertEquals("01011",alu.claAdder("1001", "0001", '1'));
        assertEquals("10011",alu.claAdder("0110", "1101", '0'));
        assertEquals("11111",alu.claAdder("1111", "1111", '1'));
        assertEquals("01011", this.alu.claAdder("1001", "0001", '1'));
        assertEquals("10000", this.alu.claAdder("1111", "0001", '0'));
        assertEquals("01000", this.alu.claAdder("0111", "0001", '0'));
    }

    @Test
    public void testOneAdder() throws Exception {
        assertEquals("000001010",alu.oneAdder("00001001"));
        assertEquals("100000000",alu.oneAdder("11111111"));
        assertEquals("01101010",alu.oneAdder("1101001"));
        //TODO
//        assertEquals("11000", this.alu.oneAdder("0111"));
    }

    //TODO
    @Test
    public void testAdder() throws Exception {
        assertEquals("0000000000010",alu.adder("0001", "0001", '0', 12));
        assertEquals("000001000",alu.adder("0100", "0011", '1', 8));
        assertEquals("11000",alu.adder("0100", "0011", '1', 4));
        assertEquals("011111010",alu.adder("1111", "1011", '0', 8));
        assertEquals("000001101",alu.adder("0111", "0110", '0', 8));
        assertEquals("011110011",alu.adder("1001", "1010", '0', 8));
        assertEquals("10011",alu.adder("1001", "1010", '0', 4));
        assertEquals("000000111", this.alu.adder("0100", "0011", '0', 8));
        assertEquals("000001000", this.alu.adder("0100", "0011", '1', 8));
        assertEquals("11000", this.alu.adder("0100", "0011", '1', 4));
        assertEquals("00111", this.alu.adder("0100", "0011", '0', 4));
        assertEquals("01111", this.alu.adder("1100", "0011", '0', 4));
        assertEquals("000000111", this.alu.adder("0100", "0011", '0', 8));
        assertEquals("000001000", this.alu.adder("0100", "0011", '1', 8));
        assertEquals("11000", this.alu.adder("0100", "0011", '1', 4));
        assertEquals("00111", this.alu.adder("0100", "0011", '0', 4));
        assertEquals("01111", this.alu.adder("1100", "0011", '0', 4));
    }

    @Test
    public void testIntegerAddition() throws Exception {
        assertEquals("11011",alu.integerAddition("0100", "0111", 4));
        assertEquals("000000111", this.alu.integerAddition("0100", "0011", 8));
        assertEquals("00111", this.alu.integerAddition("0100", "0011", 4));
        assertEquals("11010", this.alu.integerAddition("0111", "0011", 4));
    }

    @Test
    public void testIntegerSubtraction() throws Exception {
        assertEquals("000000001",alu.integerSubtraction("0100", "0011", 8));
        assertEquals("011111111",alu.integerSubtraction("0011", "0100", 8));
        assertEquals("011111001",alu.integerSubtraction("0000", "0111", 8));
        assertEquals("10011",alu.integerSubtraction("1001", "0110", 4));
        assertEquals("000000001", this.alu.integerSubtraction("0100", "0011", 8));
        assertEquals("00001", this.alu.integerSubtraction("0100", "0011", 4));
        assertEquals("11000", this.alu.integerSubtraction("0100", "1100", 4));
        assertEquals("000001000", this.alu.integerSubtraction("0100", "1100", 8));
        assertEquals("111111000", this.alu.integerSubtraction("01111100", "10000100", 8));
    }

    @Test
    public void testIntegerMultiplication() throws Exception {
        assertEquals("00110",alu.integerMultiplication("0011", "0010", 4));
        assertEquals("11100",alu.integerMultiplication("0110", "0010", 4));
        assertEquals("000001100",alu.integerMultiplication("0110", "0010", 8));
        assertEquals("10001",alu.integerMultiplication("0111", "0111", 4));
        assertEquals("011110100", this.alu.integerMultiplication("1100", "0011", 8));
    }

    @Test
    public void testIntegerDivision() throws Exception {
        assertEquals("+Inf",alu.integerDivision("0011", "0000", 4));
        assertEquals("NaN",alu.integerDivision("0000", "0000", 4));
        assertEquals("00000000000000000", alu.integerDivision("0000", "0110", 8));
        assertEquals("011101111",alu.integerDivision("1001", "0011", 4));
        assertEquals("000010010",alu.integerDivision("0110", "0100", 4));
        assertEquals("00000000100000001", alu.integerDivision("0100", "0011", 8));
        assertEquals("011101111", this.alu.integerDivision("1001", "0011", 4));
        assertEquals("011011111", this.alu.integerDivision("1001", "0010", 4));
        assertEquals("000100000", this.alu.integerDivision("0110", "0011", 4));
        assertEquals("101111111", this.alu.integerDivision("1000", "1111", 4));
    }

    //TODO
    @Test
    public void testSignedAddition() throws Exception {
        assertEquals("0100000111", alu.signedAddition("1010", "1101", 8));
        assertEquals("010111", alu.signedAddition("1010", "1101", 4));
        assertEquals("000001", alu.signedAddition("1010", "0011", 4));
        assertEquals("000101", alu.signedAddition("0010", "0011", 4));
        assertEquals("001110", alu.signedAddition("0111", "0111", 4));
        assertEquals("110111", alu.signedAddition("11101", "11010", 4));
        assertEquals("010001", alu.signedAddition("0010", "1011", 4));
        assertEquals("0100000111", this.alu.signedAddition("1100", "1011", 8));
        assertEquals("0000010010", this.alu.signedAddition("01011", "0111", 8));
        assertEquals("0000000111", this.alu.signedAddition("0111", "0000", 8));
        assertEquals("010111", this.alu.signedAddition("1100", "1011", 4));
        assertEquals("000000", this.alu.signedAddition("01000", "11000", 4));
        assertEquals("000001", this.alu.signedAddition("0001", "1000", 4));
    }

    @Test
    public void testFloatAddition() throws Exception {
        assertEquals("0" + alu.ieee754("0.9375", 32), alu.floatAddition(alu.ieee754("0.5", 32), alu.ieee754("0.4375", 32), 8, 23, 4));
        assertEquals("0" + alu.ieee754("3.15", 32), alu.floatAddition(alu.ieee754("0.75", 32), alu.ieee754("2.4", 32), 8, 23, 4));
        assertEquals("0" + alu.ieee754("2.9", 32), alu.floatAddition(alu.ieee754("0.3", 32), alu.ieee754("2.6", 32), 8, 23, 4));
//        assertEquals("000111111101110000", this.alu.floatAddition("00111111010100000", "00111111001000000", 8, 8, 8));
//        assertEquals("0001110010", this.alu.floatAddition("001101010", "001010100", 4, 4, 8));
//        assertEquals("0000110000", this.alu.floatAddition("001101010", "101101000", 4, 4, 4));
//        assertEquals("0000100000", this.alu.floatAddition("001101001", "101101000", 4, 4, 4));
        assertEquals("0001011010", this.alu.floatAddition("001011001", "000010000", 4, 4, 4));
        assertEquals("0001011000", this.alu.floatAddition("001011001", "100010000", 4, 4, 4));
//        assertEquals("00001000000000", this.alu.floatAddition("001101001", "101101000", 4, 8, 8));
//        assertEquals("0000000000", this.alu.floatAddition("001101010", "101101010", 4, 4, 4));
//        assertEquals("101111111100000000", this.alu.floatAddition("01111111000100000", "01111111001000000", 8, 8, 8));

        assertEquals(floatAddition(alu.ieee754("0.3", 32), alu.ieee754("2.6", 32), 8, 23, 4),
                this.alu.floatAddition(ieee754("0.3", 32), alu.ieee754("2.6", 32), 8, 23, 4));


//        assertEquals("0"+alu.ieee754("6.7",32), alu.floatAddition(alu.ieee754("1.3",32), alu.ieee754("5.4",32), 8, 23, 4));
//        assertEquals("0"+alu.ieee754("3.0",32), alu.floatAddition(alu.ieee754("0.4",32), alu.ieee754("2.6",32), 8, 23, 4));

    }

    @Test
    public void testFloatSubtraction() throws Exception {
        assertEquals("0"+alu.ieee754("0.9375",32), alu.floatSubtraction(alu.ieee754("0.5",32), alu.ieee754("-0.4375",32), 8, 23, 4));
//        assertEquals("0"+alu.ieee754("3.6",32), alu.floatSubtraction(alu.ieee754("9.1",32), alu.ieee754("5.5",32), 8, 23, 4));
//        assertEquals("101111111100000000", this.alu.floatSubtraction("01111111000100000", "11111111001000000", 8, 8, 8));
    }

    @Test
    public void testFloatMultiplication() throws Exception {
        assertEquals("000111110011000000", alu.floatMultiplication("00111111000000000", "00111110111000000", 8, 8));
        assertEquals("0"+alu.ieee754("4.5",32),alu.floatMultiplication(alu.ieee754("1.5",32), alu.ieee754("3.0",32), 8 ,23));
        assertEquals("0"+alu.ieee754("2864.1872",32),alu.floatMultiplication(alu.ieee754("58.31",32), alu.ieee754("49.12",32), 8 ,23));
        assertEquals("0"+alu.ieee754("1045.408668",32),alu.floatMultiplication(alu.ieee754("64.002",32), alu.ieee754("16.334",32), 8 ,23));
        assertEquals("0"+alu.ieee754("0.35",32),alu.floatMultiplication(alu.ieee754("0.5",32), alu.ieee754("0.7",32), 8 ,23));
        assertEquals("1"+alu.ieee754("2.56",32),alu.floatMultiplication(alu.ieee754("1.6",32), alu.ieee754("1.6",32), 8 ,23));
        assertEquals("000111110011000000", this.alu.floatMultiplication("00111110111000000", "00111111000000000", 8, 8));
        assertEquals("0011101111", this.alu.floatMultiplication("011101111", "001110000", 4, 4));
        assertEquals("0100000000", this.alu.floatMultiplication("011101111", "100000000", 4, 4));
        assertEquals("001000001010100000101000111101010", this.alu.floatMultiplication(alu.ieee754("1.4", 32), alu.ieee754("9.3", 32), 8, 23));

//        assertEquals("0"+alu.ieee754("13.02",32),alu.floatMultiplication(alu.ieee754("1.4",32), alu.ieee754("9.3",32), 8 ,23));
//        assertEquals("1000000000", this.alu.floatMultiplication("001111111", "000000001", 4, 4));
//        assertEquals("0"+floatRepresentation("0.060546875", 4 ,4), this.alu.floatMultiplication("011001111", "000000001", 4, 4));

//        System.out.println(alu.floatTrueValue("011001111",4,4));
//        System.out.println(floatTrueValue("011001111",4,4));
//        System.out.println(alu.floatTrueValue("000000001",4,4));
//        System.out.println(floatTrueValue("000000001",4,4));
//        System.out.println(alu.floatTrueValue("000001111",4,4));
//        System.out.println(floatTrueValue("000001111",4,4));

        assertEquals(floatMultiplication(alu.ieee754("64.02",32), alu.ieee754("16.33",32), 8 ,23),
                alu.floatMultiplication(alu.ieee754("64.02",32), alu.ieee754("16.33",32), 8 ,23));
    }

    @Test
    public void testFloatDivision() throws Exception {
        assertEquals("0" + alu.ieee754("4.0", 32), alu.floatDivision(alu.ieee754("1.6", 32), alu.ieee754("0.4", 32), 8, 23));
        assertEquals("0" + alu.ieee754("1.4", 32), alu.floatDivision(alu.ieee754("3.78", 32), alu.ieee754("2.7", 32), 8, 23));
        assertEquals("0" + alu.ieee754("39.6", 32), alu.floatDivision(alu.ieee754("253.44", 32), alu.ieee754("6.4", 32), 8, 23));
        assertEquals("0" + alu.ieee754("1.0", 32), alu.floatDivision(alu.ieee754("1.0", 32), alu.ieee754("1.0", 32), 8, 23));

        assertEquals("000111111111000000", this.alu.floatDivision("00111111011000000", "00111111000000000", 8, 8));
        assertEquals("0001110000", this.alu.floatDivision("011101111", "011101111", 4, 4));
        //TODO
//        assertEquals("1111110000", this.alu.floatDivision("011101100", "101100000", 4, 4));
//        assertEquals("0001010101", this.alu.floatDivision("001110000", "010001000", 4, 4));
    }







    /**
     * Ä£ÄâALU½øÐÐÕûÊýºÍ¸¡µãÊýµÄËÄÔòÔËËã
     *
     * @author [151250216_×¯ÓîÖÝ]
     *
     */


        private char negate(char c) {
            if (c == '0') {
                return '1';
            } else {
                return '0';
            }
        }

        private char XOr(char a, char b) {
            char c = '0';
            if ((a == '0' && b == '0') || (a == '1' && b == '1')) {
                c = '0';
            } else if ((a == '0' && b == '1') || (a == '1' && b == '0')) {
                c = '1';
            }
            return c;
        }

        private char Or(char a, char b) {
            char c = '0';
            if (a == '0' && b == '0') {
                c = '0';
            } else if ((a == '0' && b == '1') || (a == '1' && b == '0') || (a == '1' && b == '1')) {
                c = '1';
            }
            return c;
        }

        private char And(char a, char b) {
            char c = '0';
            if ((a == '0' && b == '1') || (a == '1' && b == '0') || (a == '0' && b == '0')) {
                c = '0';
            } else if ((a == '1' && b == '1')) {
                c = '1';
            }
            return c;
        }

        private String negateInteger(String operand) {
            StringBuilder sb = new StringBuilder();
            sb.append(operand);
            boolean mark = false;
            for (int i = 0; i < sb.length(); i++) {
                if (mark) {
                    sb.setCharAt(sb.length() - i - 1, negate(sb.charAt(sb.length() - i - 1)));
                }
                if (sb.charAt(sb.length() - i - 1) != '0') {
                    mark = true;
                }
            }
            return sb.toString();
        }

        /**
         * Éú³ÉÊ®½øÖÆÕûÊýµÄ¶þ½øÖÆ²¹Âë±íÊ¾¡£<br/>
         * Àý£ºintegerRepresentation("9", 8)
         *
         * @param number
         *            Ê®½øÖÆÕûÊý¡£ÈôÎª¸ºÊý£»ÔòµÚÒ»Î»Îª¡°-¡±£»ÈôÎªÕýÊý»ò 0£¬ÔòÎÞ·ûºÅÎ»
         * @param length
         *            ¶þ½øÖÆ²¹Âë±íÊ¾µÄ³¤¶È
         * @return numberµÄ¶þ½øÖÆ²¹Âë±íÊ¾£¬³¤¶ÈÎªlength
         */
        public String integerRepresentation(String number, int length) {
            // TODO YOUR CODE HERE.
            int num = 0;
            StringBuilder sb = new StringBuilder();
            if (number.charAt(0) != '-') {
                num = Integer.parseInt(number);
                while (num > 0) {
                    sb.append(num % 2);
                    num = num / 2;
                }
                for (int i = sb.length(); i < length; i++) {
                    sb.append("0");
                }
                return sb.reverse().toString();
            } else {
                num = Integer.parseInt(number.substring(1));
                while (num > 0) {
                    sb.append(num % 2);
                    num = num / 2;
                }
                for (int i = sb.length(); i < length; i++)
                    sb.append("0");

                boolean mark = false;
                for (int i = 0; i < sb.length(); i++) {
                    if (mark) {
                        sb.setCharAt(i, negate(sb.charAt(i)));
                    }
                    if (sb.charAt(i) == '1') {
                        mark = true;
                    }
                }
                return sb.reverse().toString();
            }

        }

        /**
         * Éú³ÉÊ®½øÖÆ¸¡µãÊýµÄ¶þ½øÖÆ±íÊ¾¡£ ÐèÒª¿¼ÂÇ 0¡¢·´¹æ¸ñ»¯¡¢Õý¸ºÎÞÇî£¨¡°+Inf¡±ºÍ¡°-Inf¡±£©¡¢ NaNµÈÒòËØ£¬¾ßÌå½è¼ø IEEE 754¡£
         * ÉáÈë²ßÂÔÎªÏò0ÉáÈë¡£<br/>
         * Àý£ºfloatRepresentation("11.375", 8, 11)
         *
         * @param number
         *            Ê®½øÖÆ¸¡µãÊý£¬°üº¬Ð¡Êýµã¡£ÈôÎª¸ºÊý£»ÔòµÚÒ»Î»Îª¡°-¡±£»ÈôÎªÕýÊý»ò 0£¬ÔòÎÞ·ûºÅÎ»
         * @param eLength
         *            Ö¸ÊýµÄ³¤¶È£¬È¡Öµ´óÓÚµÈÓÚ 4
         * @param sLength
         *            Î²ÊýµÄ³¤¶È£¬È¡Öµ´óÓÚµÈÓÚ 4
         * @return numberµÄ¶þ½øÖÆ±íÊ¾£¬³¤¶ÈÎª 1+eLength+sLength¡£´Ó×óÏòÓÒ£¬ÒÀ´ÎÎª·ûºÅ¡¢Ö¸Êý£¨ÒÆÂë±íÊ¾£©¡¢Î²Êý£¨Ê×Î»Òþ²Ø£©
         */
        public String floatRepresentation(String number, int eLength, int sLength) {
            StringBuilder outcome = new StringBuilder();
            if (number.charAt(0) != '-') {
                outcome.append('0');
            } else {
                outcome.append('1');
                number = number.substring(1);
            }

            String significand = "";
            String numBeforeDot = number.substring(0, number.indexOf('.'));
            String numAfterDot = number.substring(number.indexOf('.') + 1);
            String numWithoutDot = numBeforeDot + numAfterDot;
            boolean mark = true;
            for (int i = 0; i < numWithoutDot.length(); i++) {
                if (numWithoutDot.charAt(i) != '0') {
                    mark = false;
                }
            }
            if (mark) {
                for (int i = 0; i < sLength + eLength; i++) {
                    outcome.append("0");
                }
                return outcome.toString();
            } else {
                int realNum0 = Integer.parseInt(numBeforeDot);
                StringBuilder sb1 = new StringBuilder();
                while (realNum0 > 0) {
                    sb1.append(realNum0 % 2);
                    realNum0 = realNum0 / 2;
                }
                int realNum1 = Integer.parseInt(numAfterDot);
                StringBuilder sb2 = new StringBuilder();
                while (realNum1 > 0) {
                    realNum1 = realNum1 * 2;
                    if (realNum1 >= (int) Math.pow(10, numAfterDot.length())) {
                        realNum1 = realNum1 - (int) Math.pow(10, numAfterDot.length());
                        sb2.append("1");
                    } else {
                        sb2.append("0");
                    }
                    if (sb2.length() > 2 * sLength) {
                        break;
                    }
                }
                if (sb1.length() != 0) {
                    significand = (sb1.reverse().toString() + sb2.toString()).substring(1);
                } else {
                    significand = (sb1.reverse().toString() + sb2.toString()).substring(sb2.indexOf("1") + 1);
                }
                if (significand.length() >= sLength) {
                    significand = significand.substring(0, sLength);
                } else {
                    for (int i = significand.length(); i < sLength; i++) {
                        significand = significand + "0";
                    }
                }
            }

            String exponent = "";
            int valueOfExponent = 0;
            if (number.charAt(0) != '0') {
                int count = 0;
                int num = Integer.parseInt(numBeforeDot);
                while (num > 0) {
                    count++;
                    num = num / 2;
                }
                valueOfExponent = count - 1 + (int) Math.pow(2, eLength - 1) - 1;
            } else {
                int count = 0;
                int num = Integer.parseInt(numAfterDot);
                if (num != 0) {
                    while (num < (int) Math.pow(10, numAfterDot.length())) {
                        count--;
                        num = num * 2;
                    }
                    valueOfExponent = count + (int) Math.pow(2, eLength - 1) - 1;
                } else {
                    valueOfExponent = 0;
                }
            }
            StringBuilder sb2 = new StringBuilder();
            while (valueOfExponent > 0) {
                sb2.append(valueOfExponent % 2);
                valueOfExponent = valueOfExponent / 2;
            }

            if (sb2.length() == 0) {
                for (int i = 0; i < eLength; i++) {
                    exponent = exponent + "0";
                }
                significand = "1" + significand.substring(0, significand.length() - 1);
            } else {
                exponent = sb2.reverse().toString();
                if (exponent.length() > eLength) {
                    exponent = "";
                    significand = "";
                    for (int i = 0; i < eLength; i++) {
                        exponent = exponent + "1";
                    }
                    for (int i = 0; i < sLength; i++) {
                        significand = significand + "0";
                    }
                } else {
                    for (int i = exponent.length(); i < eLength; i++) {
                        exponent = "0" + exponent;
                    }
                }
            }

            return outcome.append(exponent).append(significand).toString();
        }

        /**
         * Éú³ÉÊ®½øÖÆ¸¡µãÊýµÄIEEE 754±íÊ¾£¬ÒªÇóµ÷ÓÃ{@link #floatRepresentation(String, int, int)
         * floatRepresentation}ÊµÏÖ¡£<br/>
         * Àý£ºieee754("11.375", 32)
         *
         * @param number
         *            Ê®½øÖÆ¸¡µãÊý£¬°üº¬Ð¡Êýµã¡£ÈôÎª¸ºÊý£»ÔòµÚÒ»Î»Îª¡°-¡±£»ÈôÎªÕýÊý»ò 0£¬ÔòÎÞ·ûºÅÎ»
         * @param length
         *            ¶þ½øÖÆ±íÊ¾µÄ³¤¶È£¬Îª32»ò64
         * @return numberµÄIEEE 754±íÊ¾£¬³¤¶ÈÎªlength¡£´Ó×óÏòÓÒ£¬ÒÀ´ÎÎª·ûºÅ¡¢Ö¸Êý£¨ÒÆÂë±íÊ¾£©¡¢Î²Êý£¨Ê×Î»Òþ²Ø£©
         */
        public String ieee754(String number, int length) {
            if (length == 32) {
                return floatRepresentation(number, 8, 23);
            } else {
                return floatRepresentation(number, 11, 52);
            }
        }

        /**
         * ¼ÆËã¶þ½øÖÆ²¹Âë±íÊ¾µÄÕûÊýµÄÕæÖµ¡£<br/>
         * Àý£ºintegerTrueValue("00001001")
         *
         * @param operand
         *            ¶þ½øÖÆ²¹Âë±íÊ¾µÄ²Ù×÷Êý
         * @return operandµÄÕæÖµ¡£ÈôÎª¸ºÊý£»ÔòµÚÒ»Î»Îª¡°-¡±£»ÈôÎªÕýÊý»ò 0£¬ÔòÎÞ·ûºÅÎ»
         */
        public String integerTrueValue(String operand) {
            int value = 0;
            for (int i = 0; i < operand.length(); i++) {
                if (i == 0) {
                    value = value - (operand.charAt(i) - '0') * (int) Math.pow(2, operand.length() - 1);
                } else {
                    value = value + (operand.charAt(i) - '0') * (int) Math.pow(2, operand.length() - 1 - i);
                }
            }
            return value + "";
        }

        /**
         * ¼ÆËã¶þ½øÖÆÔ­Âë±íÊ¾µÄ¸¡µãÊýµÄÕæÖµ¡£<br/>
         * Àý£ºfloatTrueValue("01000001001101100000", 8, 11)
         *
         * @param operand
         *            ¶þ½øÖÆ±íÊ¾µÄ²Ù×÷Êý
         * @param eLength
         *            Ö¸ÊýµÄ³¤¶È£¬È¡Öµ´óÓÚµÈÓÚ 4
         * @param sLength
         *            Î²ÊýµÄ³¤¶È£¬È¡Öµ´óÓÚµÈÓÚ 4
         * @return operandµÄÕæÖµ¡£ÈôÎª¸ºÊý£»ÔòµÚÒ»Î»Îª¡°-¡±£»ÈôÎªÕýÊý»ò 0£¬ÔòÎÞ·ûºÅÎ»¡£Õý¸ºÎÞÇî·Ö±ð±íÊ¾Îª¡°+Inf¡±ºÍ¡°-Inf¡±£¬
         *         NaN±íÊ¾Îª¡°NaN¡±
         */
        public String floatTrueValue(String operand, int eLength, int sLength) {
            double value = 0;
            boolean mark0 = true;// e--all0
            boolean mark1 = true;// e--all1
            boolean mark2 = true;// s--all0
            for (int i = 0; i < eLength; i++) {
                if (operand.charAt(1 + i) != '0') {
                    mark0 = false;
                } else {
                    mark1 = false;
                }
            }
            for (int i = 0; i < sLength; i++) {
                if (operand.charAt(1 + eLength + i) != '0') {
                    mark2 = false;
                }
            }
            if (mark1 && mark2) {
                if (operand.charAt(0) == '0') {
                    return "Inf";
                } else {
                    return "-Inf";
                }
            }
            if (mark1 && (!mark2)) {
                return "NaN";
            }
            if (mark0 && mark2) {
                return "0";
            } else if (mark0 && !mark2) {
                for (int i = 0; i < sLength; i++) {
                    value = value + (operand.charAt(eLength + i) - '0')
                            * Math.pow(2, -1 * (int) Math.pow(2, eLength - 1) + 1 - i);
                }
                if (operand.charAt(0) == '0') {
                    return value + "";
                } else {
                    return -1 * value + "";
                }

            } else if (!mark0) {
                int e = 0;
                String string = operand.substring(1, eLength + 1);
                for (int i = 0; i < eLength; i++) {
                    e = e + (string.charAt(i) - '0') * (int) Math.pow(2, eLength - i - 1);
                }
                value = Math.pow(2, e - 1 * (int) Math.pow(2, eLength - 1) + 1);
                for (int i = 0; i < sLength; i++) {
                    value = value + (operand.charAt(1 + eLength + i) - '0')
                            * Math.pow(2, e - 1 * (int) Math.pow(2, eLength - 1) - i);
                }
                if (operand.charAt(0) == '0') {
                    return value + "";
                } else {
                    return -1 * value + "";
                }
            }
            return null;
        }

        /**
         * °´Î»È¡·´²Ù×÷¡£<br/>
         * Àý£ºnegation("00001001")
         *
         * @param operand
         *            ¶þ½øÖÆ±íÊ¾µÄ²Ù×÷Êý
         * @return operand°´Î»È¡·´µÄ½á¹û
         */
        public String negation(String operand) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < operand.length(); i++) {
                sb.append(negate(operand.charAt(i)));
            }
            return sb.toString();
        }

        /**
         * ×óÒÆ²Ù×÷¡£<br/>
         * Àý£ºleftShift("00001001", 2)
         *
         * @param operand
         *            ¶þ½øÖÆ±íÊ¾µÄ²Ù×÷Êý
         * @param n
         *            ×óÒÆµÄÎ»Êý
         * @return operand×óÒÆnÎ»µÄ½á¹û
         */
        public String leftShift(String operand, int n) {
            String outcome = operand;
            for (int i = 0; i < n; i++) {
                outcome = outcome.substring(1, outcome.length()) + "0";
            }
            return outcome;
        }

        /**
         * Âß¼­ÓÒÒÆ²Ù×÷¡£<br/>
         * Àý£ºlogRightShift("11110110", 2)
         *
         * @param operand
         *            ¶þ½øÖÆ±íÊ¾µÄ²Ù×÷Êý
         * @param n
         *            ÓÒÒÆµÄÎ»Êý
         * @return operandÂß¼­ÓÒÒÆnÎ»µÄ½á¹û
         */
        public String logRightShift(String operand, int n) {
            String outcome = operand;
            for (int i = 0; i < n; i++) {
                outcome = "0" + outcome.substring(0, outcome.length() - 1);
            }
            return outcome;
        }

        /**
         * ËãÊõÓÒÒÆ²Ù×÷¡£<br/>
         * Àý£ºlogRightShift("11110110", 2)
         *
         * @param operand
         *            ¶þ½øÖÆ±íÊ¾µÄ²Ù×÷Êý
         * @param n
         *            ÓÒÒÆµÄÎ»Êý
         * @return operandËãÊõÓÒÒÆnÎ»µÄ½á¹û
         */
        public String ariRightShift(String operand, int n) {
            String outcome = operand;
            char c = operand.charAt(0);
            for (int i = 0; i < n; i++) {
                outcome = c + outcome.substring(0, outcome.length() - 1);
            }
            return outcome;
        }

        /**
         * È«¼ÓÆ÷£¬¶ÔÁ½Î»ÒÔ¼°½øÎ»½øÐÐ¼Ó·¨ÔËËã¡£<br/>
         * Àý£ºfullAdder('1', '1', '0')
         *
         * @param x
         *            ±»¼ÓÊýµÄÄ³Ò»Î»£¬È¡0»ò1
         * @param y
         *            ¼ÓÊýµÄÄ³Ò»Î»£¬È¡0»ò1
         * @param c
         *            µÍÎ»¶Ôµ±Ç°Î»µÄ½øÎ»£¬È¡0»ò1
         * @return Ïà¼ÓµÄ½á¹û£¬ÓÃ³¤¶ÈÎª2µÄ×Ö·û´®±íÊ¾£¬µÚ1Î»±íÊ¾½øÎ»£¬µÚ2Î»±íÊ¾ºÍ
         */
        public String fullAdder(char x, char y, char c) {
            String outcome = "";
            if (((x == '1') && (y == '1')) || ((y == '1') && (c == '1')) || ((c == '1') && (x == '1'))) {
                outcome = "1";
            } else {
                outcome = "0";
            }

            return outcome + XOr(x, XOr(y, c));
        }

        /**
         * 4Î»ÏÈÐÐ½øÎ»¼Ó·¨Æ÷¡£ÒªÇó²ÉÓÃ{@link #fullAdder(char, char, char) fullAdder}À´ÊµÏÖ<br/>
         * Àý£ºclaAdder("1001", "0001", '1')
         *
         * @param operand1
         *            4Î»¶þ½øÖÆ±íÊ¾µÄ±»¼ÓÊý
         * @param operand2
         *            4Î»¶þ½øÖÆ±íÊ¾µÄ¼ÓÊý
         * @param c
         *            µÍÎ»¶Ôµ±Ç°Î»µÄ½øÎ»£¬È¡0»ò1
         * @return ³¤¶ÈÎª5µÄ×Ö·û´®±íÊ¾µÄ¼ÆËã½á¹û£¬ÆäÖÐµÚ1Î»ÊÇ×î¸ßÎ»½øÎ»£¬ºó4Î»ÊÇÏà¼Ó½á¹û£¬ÆäÖÐ½øÎ»²»¿ÉÒÔÓÉÑ­»·»ñµÃ
         */
        public String claAdder(String operand1, String operand2, char c) {
            String outcome = "";
            char[] p = new char[4];
            char[] g = new char[4];
            char[] ch = new char[5];
            for (int i = 0; i < 4; i++) {
                p[i] = Or(operand1.charAt(3 - i), operand2.charAt(3 - i));
                g[i] = And(operand1.charAt(3 - i), operand2.charAt(3 - i));
            }
            ch[0] = c;
            ch[1] = Or(g[0], And(p[0], c));
            ch[2] = Or(g[1], Or(And(p[1], g[0]), And(p[1], And(p[0], c))));
            ch[3] = Or(g[2], Or(And(p[2], g[1]), Or(And(p[2], And(p[1], g[0])), And(p[2], And(p[1], And(p[0], c))))));
            ch[4] = Or(g[3], Or(And(p[3], g[2]), Or(And(p[3], And(p[2], g[1])),
                    Or(And(p[3], And(p[2], And(p[1], g[0]))), And(p[3], And(p[2], And(p[1], And(p[0], c))))))));
            for (int i = 0; i <= 4; i++) {
                if (i < 4) {
                    outcome = fullAdder(operand1.charAt(3 - i), operand2.charAt(3 - i), ch[i]).charAt(1) + outcome;
                } else {
                    outcome = ch[4] + outcome;
                }
            }
            return outcome;
        }

        /**
         * ¼ÓÒ»Æ÷£¬ÊµÏÖ²Ù×÷Êý¼Ó1µÄÔËËã¡£ ÐèÒª²ÉÓÃÓëÃÅ¡¢»òÃÅ¡¢Òì»òÃÅµÈÄ£Äâ£¬ ²»¿ÉÒÔÖ±½Óµ÷ÓÃ
         * {@link #fullAdder(char, char, char) fullAdder}¡¢
         * {@link #claAdder(String, String, char) claAdder}¡¢
         * {@link #adder(String, String, char, int) adder}¡¢
         * {@link #integerAddition(String, String, int) integerAddition}·½·¨¡£<br/>
         * Àý£ºoneAdder("00001001")
         *
         * @param operand
         *            ¶þ½øÖÆ²¹Âë±íÊ¾µÄ²Ù×÷Êý
         * @return operand¼Ó1µÄ½á¹û£¬³¤¶ÈÎªoperandµÄ³¤¶È¼Ó1£¬ÆäÖÐµÚ1Î»Ö¸Ê¾ÊÇ·ñÒç³ö£¨Òç³öÎª1£¬·ñÔòÎª0£©£¬ÆäÓàÎ»ÎªÏà¼Ó½á¹û
         */
        public String oneAdder(String operand) {
            StringBuilder sb = new StringBuilder();
            char[] c = new char[operand.length() + 1];
            char[] s = new char[operand.length() + 1];
            c[0] = '1';
            for (int i = 0; i < operand.length(); i++) {
                s[i] = operand.charAt(operand.length() - i - 1);
                c[i + 1] = And(s[i], c[i]);
                s[i] = XOr(s[i], c[i]);
                sb.append(s[i]);
            }
            if ((s[operand.length() - 1] != operand.charAt(0)) && (s[operand.length() - 1] == '1')) {
                sb.append('1');
            } else {
                sb.append('0');
            }
            return sb.reverse().toString();
        }

        /**
         * ¼Ó·¨Æ÷£¬ÒªÇóµ÷ÓÃ{@link #claAdder(String, String, char)}·½·¨ÊµÏÖ¡£<br/>
         * Àý£ºadder("0100", "0011", ¡®0¡¯, 8)
         *
         * @param operand1
         *            ¶þ½øÖÆ²¹Âë±íÊ¾µÄ±»¼ÓÊý
         * @param operand2
         *            ¶þ½øÖÆ²¹Âë±íÊ¾µÄ¼ÓÊý
         * @param c
         *            ×îµÍÎ»½øÎ»
         * @param length
         *            ´æ·Å²Ù×÷ÊýµÄ¼Ä´æÆ÷µÄ³¤¶È£¬Îª4µÄ±¶Êý¡£length²»Ð¡ÓÚ²Ù×÷ÊýµÄ³¤¶È£¬µ±Ä³¸ö²Ù×÷ÊýµÄ³¤¶ÈÐ¡ÓÚlengthÊ±£¬
         *            ÐèÒªÔÚ¸ßÎ»²¹·ûºÅÎ»
         * @return ³¤¶ÈÎªlength+1µÄ×Ö·û´®±íÊ¾µÄ¼ÆËã½á¹û£¬ÆäÖÐµÚ1Î»Ö¸Ê¾ÊÇ·ñÒç³ö£¨Òç³öÎª1£¬·ñÔòÎª0£©£¬ºólengthÎ»ÊÇÏà¼Ó½á¹û
         */
        public String adder(String operand1, String operand2, char c, int length) {
            char firstChar = operand1.charAt(0);
            for (int i = operand1.length(); i < length; i++) {
                operand1 = firstChar + operand1;
            }
            firstChar = operand2.charAt(0);
            for (int i = operand2.length(); i < length; i++) {
                operand2 = firstChar + operand2;
            }

            StringBuilder sb = new StringBuilder();
            String[] s1 = new String[length / 4];
            String[] s2 = new String[length / 4];
            String[] s = new String[length / 4];
            char[] ch = new char[length / 4 + 1];
            ch[0] = c;
            for (int i = 0; i < length / 4; i++) {
                s1[i] = operand1.substring(length - 4 * (i + 1), length - 4 * i);
                s2[i] = operand2.substring(length - 4 * (i + 1), length - 4 * i);
                ch[i + 1] = claAdder(s1[i], s2[i], ch[i]).charAt(0);
                s[length / 4 - i - 1] = claAdder(s1[i], s2[i], ch[i]).substring(1);
            }
            if ((operand1.charAt(0) == operand2.charAt(0)) && (operand1.charAt(0) != s[0].charAt(0))) {
                sb.append('1');
            } else {
                sb.append('0');
            }
            for (int i = 0; i < length / 4; i++) {
                sb.append(s[i]);
            }
            return sb.toString();
        }

        /**
         * ÕûÊý¼Ó·¨£¬ÒªÇóµ÷ÓÃ{@link #adder(String, String, char, int) adder}·½·¨ÊµÏÖ¡£<br/>
         * Àý£ºintegerAddition("0100", "0011", 8)
         *
         * @param operand1
         *            ¶þ½øÖÆ²¹Âë±íÊ¾µÄ±»¼ÓÊý
         * @param operand2
         *            ¶þ½øÖÆ²¹Âë±íÊ¾µÄ¼ÓÊý
         * @param length
         *            ´æ·Å²Ù×÷ÊýµÄ¼Ä´æÆ÷µÄ³¤¶È£¬Îª4µÄ±¶Êý¡£length²»Ð¡ÓÚ²Ù×÷ÊýµÄ³¤¶È£¬µ±Ä³¸ö²Ù×÷ÊýµÄ³¤¶ÈÐ¡ÓÚlengthÊ±£¬
         *            ÐèÒªÔÚ¸ßÎ»²¹·ûºÅÎ»
         * @return ³¤¶ÈÎªlength+1µÄ×Ö·û´®±íÊ¾µÄ¼ÆËã½á¹û£¬ÆäÖÐµÚ1Î»Ö¸Ê¾ÊÇ·ñÒç³ö£¨Òç³öÎª1£¬·ñÔòÎª0£©£¬ºólengthÎ»ÊÇÏà¼Ó½á¹û
         */
        public String integerAddition(String operand1, String operand2, int length) {
            return adder(operand1, operand2, '0', length);
        }

        /**
         * ÕûÊý¼õ·¨£¬¿Éµ÷ÓÃ{@link #adder(String, String, char, int) adder}·½·¨ÊµÏÖ¡£<br/>
         * Àý£ºintegerSubtraction("0100", "0011", 8)
         *
         * @param operand1
         *            ¶þ½øÖÆ²¹Âë±íÊ¾µÄ±»¼õÊý
         * @param operand2
         *            ¶þ½øÖÆ²¹Âë±íÊ¾µÄ¼õÊý
         * @param length
         *            ´æ·Å²Ù×÷ÊýµÄ¼Ä´æÆ÷µÄ³¤¶È£¬Îª4µÄ±¶Êý¡£length²»Ð¡ÓÚ²Ù×÷ÊýµÄ³¤¶È£¬µ±Ä³¸ö²Ù×÷ÊýµÄ³¤¶ÈÐ¡ÓÚlengthÊ±£¬
         *            ÐèÒªÔÚ¸ßÎ»²¹·ûºÅÎ»
         * @return ³¤¶ÈÎªlength+1µÄ×Ö·û´®±íÊ¾µÄ¼ÆËã½á¹û£¬ÆäÖÐµÚ1Î»Ö¸Ê¾ÊÇ·ñÒç³ö£¨Òç³öÎª1£¬·ñÔòÎª0£©£¬ºólengthÎ»ÊÇÏà¼õ½á¹û
         */
        public String integerSubtraction(String operand1, String operand2, int length) {
            return adder(operand1, negateInteger(operand2), '0', length);
        }

        /**
         * ÕûÊý³Ë·¨£¬Ê¹ÓÃBoothËã·¨ÊµÏÖ£¬¿Éµ÷ÓÃ{@link #adder(String, String, char, int) adder}µÈ·½·¨¡£
         * <br/>
         * Àý£ºintegerMultiplication("0100", "0011", 8)
         *
         * @param operand1
         *            ¶þ½øÖÆ²¹Âë±íÊ¾µÄ±»³ËÊý
         * @param operand2
         *            ¶þ½øÖÆ²¹Âë±íÊ¾µÄ³ËÊý
         * @param length
         *            ´æ·Å²Ù×÷ÊýµÄ¼Ä´æÆ÷µÄ³¤¶È£¬Îª4µÄ±¶Êý¡£length²»Ð¡ÓÚ²Ù×÷ÊýµÄ³¤¶È£¬µ±Ä³¸ö²Ù×÷ÊýµÄ³¤¶ÈÐ¡ÓÚlengthÊ±£¬
         *            ÐèÒªÔÚ¸ßÎ»²¹·ûºÅÎ»
         * @return ³¤¶ÈÎªlength+1µÄ×Ö·û´®±íÊ¾µÄÏà³Ë½á¹û£¬ÆäÖÐµÚ1Î»Ö¸Ê¾ÊÇ·ñÒç³ö£¨Òç³öÎª1£¬·ñÔòÎª0£©£¬ºólengthÎ»ÊÇÏà³Ë½á¹û
         */
        public String integerMultiplication(String operand1, String operand2, int length) {
            char c = operand1.charAt(0);
            for (int i = operand1.length(); i < length; i++) {
                operand1 = c + operand1;
            }
            c = operand2.charAt(0);
            for (int i = operand2.length(); i < length; i++) {
                operand2 = c + operand2;
            }
            String string = operand2 + "0";
            for (int i = 0; i < length; i++) {
                string = "0" + string;
            }
            for (int i = 0; i < length; i++) {
                if ((string.charAt(string.length() - 1) - string.charAt(string.length() - 2)) == 1) {
                    string = adder(operand1, string.substring(0, length), '0', length).substring(1)
                            + string.substring(length);
                } else if ((string.charAt(string.length() - 1) - string.charAt(string.length() - 2)) == -1) {
                    string = adder(negateInteger(operand1), string.substring(0, length), '0', length).substring(1)
                            + string.substring(length);
                }

                string = string.charAt(0) + string.substring(0, string.length() - 1);
            }
            boolean mark = true;
            c = string.charAt(length);
            for (int i = 0; i < length; i++) {
                if (string.charAt(i) != c) {
                    mark = false;
                }
            }
            if (mark) {
                return "0" + string.substring(length, 2 * length);
            } else {
                return "1" + string.substring(length, 2 * length);
            }
        }

        /**
         * ÕûÊýµÄ²»»Ö¸´ÓàÊý³ý·¨£¬¿Éµ÷ÓÃ{@link #adder(String, String, char, int) adder}µÈ·½·¨ÊµÏÖ¡£<br/>
         * Àý£ºintegerDivision("0100", "0011", 8)
         *
         * @param operand1
         *            ¶þ½øÖÆ²¹Âë±íÊ¾µÄ±»³ýÊý
         * @param operand2
         *            ¶þ½øÖÆ²¹Âë±íÊ¾µÄ³ýÊý
         * @param length
         *            ´æ·Å²Ù×÷ÊýµÄ¼Ä´æÆ÷µÄ³¤¶È£¬Îª4µÄ±¶Êý¡£length²»Ð¡ÓÚ²Ù×÷ÊýµÄ³¤¶È£¬µ±Ä³¸ö²Ù×÷ÊýµÄ³¤¶ÈÐ¡ÓÚlengthÊ±£¬
         *            ÐèÒªÔÚ¸ßÎ»²¹·ûºÅÎ»
         * @return ³¤¶ÈÎª2*length+1µÄ×Ö·û´®±íÊ¾µÄÏà³ý½á¹û£¬ÆäÖÐµÚ1Î»Ö¸Ê¾ÊÇ·ñÒç³ö£¨Òç³öÎª1£¬·ñÔòÎª0£©£¬ÆäºólengthÎ»ÎªÉÌ£¬
         *         ×îºólengthÎ»ÎªÓàÊý
         */
        public String integerDivision(String operand1, String operand2, int length) {
            char c = operand2.charAt(0);
            for (int i = operand2.length(); i < length; i++) {
                operand2 = c + operand2;
            }
            c = operand1.charAt(0);
            for (int i = operand1.length(); i < length; i++) {
                operand1 = c + operand1;
            }
            String string = operand1;
            for (int i = 0; i < length; i++) {
                string = c + string;
            }
            if (operand1.charAt(0) == operand2.charAt(0)) {
                string = adder(string.substring(0, length), negateInteger(operand2), '0', length).substring(1)
                        + string.substring(length);
            } else {
                string = adder(string.substring(0, length), operand2, '0', length).substring(1) + string.substring(length);
            }
            if (string.charAt(0) == operand2.charAt(0)) {
                string = string + "1";
            } else {
                string = string + "0";
            }
            for (int i = 0; i < length; i++) {
                if (string.charAt(0) == operand2.charAt(0)) {
                    string = string.substring(1);
                    string = adder(string.substring(0, length), negateInteger(operand2), '0', length).substring(1)
                            + string.substring(length);
                } else {
                    string = string.substring(1);
                    string = adder(string.substring(0, length), operand2, '0', length).substring(1)
                            + string.substring(length);
                }
                if (string.charAt(0) == operand2.charAt(0)) {
                    string = string + "1";
                } else {
                    string = string + "0";
                }
            }

            String quotient = string.substring(length);
            quotient = quotient.substring(1);
            if (operand1.charAt(0) != operand2.charAt(0)) {
                quotient = oneAdder(quotient).substring(1);
            }
            String remainder = string.substring(0, length);
            if (remainder.charAt(0) != operand1.charAt(0)) {
                if (operand1.charAt(0) == operand2.charAt(0)) {
                    remainder = adder(remainder, operand2, '0', length).substring(1);
                } else {
                    remainder = adder(remainder, negateInteger(operand2), '0', length).substring(1);
                }
            }
            return "0" + quotient + remainder;
        }

        /**
         * ´ø·ûºÅÕûÊý¼Ó·¨£¬¿ÉÒÔµ÷ÓÃ{@link #adder(String, String, char, int) adder}µÈ·½·¨£¬
         * µ«²»ÄÜÖ±½Ó½«²Ù×÷Êý×ª»»Îª²¹ÂëºóÊ¹ÓÃ{@link #integerAddition(String, String, int)
         * integerAddition}¡¢ {@link #integerSubtraction(String, String, int)
         * integerSubtraction}À´ÊµÏÖ¡£<br/>
         * Àý£ºsignedAddition("1100", "1011", 8)
         *
         * @param operand1
         *            ¶þ½øÖÆÔ­Âë±íÊ¾µÄ±»¼ÓÊý£¬ÆäÖÐµÚ1Î»Îª·ûºÅÎ»
         * @param operand2
         *            ¶þ½øÖÆÔ­Âë±íÊ¾µÄ¼ÓÊý£¬ÆäÖÐµÚ1Î»Îª·ûºÅÎ»
         * @param length
         *            ´æ·Å²Ù×÷ÊýµÄ¼Ä´æÆ÷µÄ³¤¶È£¬Îª4µÄ±¶Êý¡£length²»Ð¡ÓÚ²Ù×÷ÊýµÄ³¤¶È£¨²»°üº¬·ûºÅ£©£¬µ±Ä³¸ö²Ù×÷ÊýµÄ³¤¶ÈÐ¡ÓÚlengthÊ±£¬
         *            ÐèÒª½«Æä³¤¶ÈÀ©Õ¹µ½length
         * @return ³¤¶ÈÎªlength+2µÄ×Ö·û´®±íÊ¾µÄ¼ÆËã½á¹û£¬ÆäÖÐµÚ1Î»Ö¸Ê¾ÊÇ·ñÒç³ö£¨Òç³öÎª1£¬·ñÔòÎª0£©£¬µÚ2Î»Îª·ûºÅÎ»£¬
         *         ºólengthÎ»ÊÇÏà¼Ó½á¹û
         */
        public String signedAddition(String operand1, String operand2, int length) {
            String outcome = "";
            int value1 = 0;
            int value2 = 0;
            for (int i = operand2.length(); i <= length; i++) {
                operand2 = operand2.charAt(0) + "0" + operand2.substring(1);
            }
            for (int i = operand1.length(); i <= length; i++) {
                operand1 = operand1.charAt(0) + "0" + operand1.substring(1);
            }
            for (int i = 1; i <= length; i++) {
                value1 = value1 + (operand1.charAt(i) - '0') * (int) Math.pow(2, length - i);
                value2 = value2 + (operand2.charAt(i) - '0') * (int) Math.pow(2, length - i);
            }
            if (operand1.charAt(0) != '0') {
                value1 = value1 * (-1);
            }
            if (operand2.charAt(0) != '0') {
                value2 = value2 * (-1);
            }
            int value = value1 + value2;
            if (value >= 0) {
                outcome = "0";
            } else {
                outcome = "1";
                value = value * (-1);
            }
            StringBuilder sb = new StringBuilder();
            while (value > 0) {
                sb.append(value % 2);
                value = value / 2;
            }
            if (sb.length() <= length) {
                for (int i = sb.length(); i < length; i++) {
                    sb.append("0");
                }
                outcome = "0" + outcome + sb.reverse().toString();
            } else {
                outcome = "1" + outcome + sb.reverse().substring(sb.length() - length, sb.length());
            }
            return outcome;
        }

        /**
         * ¸¡µãÊý¼Ó·¨£¬¿Éµ÷ÓÃ{@link #signedAddition(String, String, int) signedAddition}
         * µÈ·½·¨ÊµÏÖ¡£<br/>
         * Àý£ºfloatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
         *
         * @param operand1
         *            ¶þ½øÖÆ±íÊ¾µÄ±»¼ÓÊý
         * @param operand2
         *            ¶þ½øÖÆ±íÊ¾µÄ¼ÓÊý
         * @param eLength
         *            Ö¸ÊýµÄ³¤¶È£¬È¡Öµ´óÓÚµÈÓÚ 4
         * @param sLength
         *            Î²ÊýµÄ³¤¶È£¬È¡Öµ´óÓÚµÈÓÚ 4
         * @param gLength
         *            ±£»¤Î»µÄ³¤¶È
         * @return ³¤¶ÈÎª2+eLength+sLengthµÄ×Ö·û´®±íÊ¾µÄÏà¼Ó½á¹û£¬ÆäÖÐµÚ1Î»Ö¸Ê¾ÊÇ·ñÖ¸ÊýÉÏÒç£¨Òç³öÎª1£¬·ñÔòÎª0£©£¬
         *         ÆäÓàÎ»´Ó×óµ½ÓÒÒÀ´ÎÎª·ûºÅ¡¢Ö¸Êý£¨ÒÆÂë±íÊ¾£©¡¢Î²Êý£¨Ê×Î»Òþ²Ø£©¡£ÉáÈë²ßÂÔÎªÏò0ÉáÈë
         */
        public String floatAddition(String operand1, String operand2, int eLength, int sLength, int gLength) {
            boolean mark = true;
            for (int i = 1; i < operand2.length(); i++) {
                if (operand1.charAt(i) != '0') {
                    mark = false;
                }
            }
            if (mark) {
                return "0" + operand2;
            }
            mark = true;
            for (int i = 1; i < operand1.length(); i++) {
                if (operand2.charAt(i) != '0') {
                    mark = false;
                }
            }
            if (mark) {
                return "0" + operand1;
            }
            int exponent1 = 0;
            int exponent2 = 0;
            String exponentA = operand1.substring(1, eLength + 1);
            String exponentB = operand2.substring(1, eLength + 1);
            String significand1 = null;
            String significand2 = null;
            for (int i = 0; i < eLength; i++) {
                exponent1 = exponent1 + (operand1.charAt(i + 1) - '0') * (int) Math.pow(2, eLength - i - 1);
                exponent2 = exponent2 + (operand2.charAt(i + 1) - '0') * (int) Math.pow(2, eLength - i - 1);
            }
            if (exponent1 > 0) {
                significand1 = "1" + operand1.substring(1 + eLength);
            } else {
                significand1 = operand1.substring((operand1.substring(1).indexOf('1')));
            }
            if (exponent2 > 0) {
                significand2 = "1" + operand2.substring(1 + eLength);
            } else {
                significand2 = operand2.substring((operand2.substring(1).indexOf('1')));
            }
            for (int i = 0; i < gLength; i++) {
                significand1 = significand1 + "0";
                significand2 = significand2 + "0";
            }
            if (exponent1 >= exponent2) {
                while ((exponent1 > exponent2) && (significand2.length() > 0)) {
                    exponent2++;
                    significand2 = significand2.substring(0, significand2.length() - 1);
                }
                if (significand2.length() == 0) {
                    return "0" + operand1;
                }
            } else {
                while ((exponent2 > exponent1) && (significand1.length() > 0)) {
                    exponent1++;
                    significand1 = significand1.substring(0, significand1.length() - 1);
                }
                if (significand1.length() == 0) {
                    return "0" + operand2;
                }
            }
            int length1 = (significand1.length() / 4 + 1) * 4 - 1;
            int length2 = (significand2.length() / 4 + 1) * 4 - 1;
            int count = 0;
            String significand = "";
            if (length1 >= length2) {
                while (significand1.length() < length1) {
                    significand1 = "0" + significand1;
                    count++;
                }
                while (significand2.length() < length1) {
                    significand2 = "0" + significand2;
                }
                significand1 = operand1.charAt(0) + significand1;
                significand2 = operand2.charAt(0) + significand2;
                significand = signedAddition(significand1, significand2, length1 + 1).substring(1);
            } else {
                while (significand1.length() < length2) {
                    significand1 = "0" + significand1;
                }
                while (significand2.length() < length2) {
                    significand2 = "0" + significand2;
                    count++;
                }
                significand1 = operand1.charAt(0) + significand1;
                significand2 = operand2.charAt(0) + significand2;
                significand = signedAddition(significand1, significand2, length2 + 1).substring(1);
            }
            char sign = significand.charAt(0);
            significand = significand.substring(1);
            boolean significandIsZero = true;
            for (int i = 0; i < significand.length(); i++) {
                if (significand.charAt(i) != '0') {
                    significandIsZero = false;
                }
            }
            if (significandIsZero) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < eLength + sLength; i++) {
                    sb.append("0");
                }
                return "0" + sign + sb.toString();
            }
            int exponentValue = exponent1;
            String exponent = integerRepresentation(exponentValue + "", eLength);
            int num = significand.indexOf('1');
            significand = significand.substring(count);
            if (num > count) {
                if (num == count + 1) {
                    significand = significand.substring(1);
                    count++;
                    exponentValue++;
                } else {
                    significand = significand.substring(2);
                    count++;
                    count++;
                }
                while (num >= count) {
                    exponentValue--;
                    if (exponentValue == 0) {
                        if (significand.length() > sLength + gLength) {
                            significand = significand.substring(0, sLength + gLength);
                        } else {
                            for (int i = significand.length(); i < sLength + gLength; i++) {
                                significand = significand + "0";
                            }
                        }
                        return "1" + sign + integerRepresentation(exponentValue + "", eLength)
                                + significand.substring(0, sLength);
                    }
                    significand = significand.substring(1);
                    count++;
                }
            } else {
                exponentValue++;
                if (exponentValue >= (int) Math.pow(2, eLength) - 1) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < eLength; i++) {
                        sb.append("1");
                    }
                    for (int i = 0; i < sLength; i++) {
                        sb.append("0");
                    }
                    return "1" + sign + sb.toString();
                }
                significand = significand.substring(1);
            }
            if (significand.length() >= gLength) {
                significand = significand.substring(0, significand.length() - gLength);
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < eLength + sLength; i++) {
                    sb.append("0");
                }
                return "0" + sign + sb.toString();
            }
            if (significand.length() < sLength) {
                for (int i = significand.length(); i < sLength; i++) {
                    significand = significand + "0";
                }
            } else {
                significand = significand.substring(0, sLength);
            }
            return "0" + sign + integerRepresentation(exponentValue + "", eLength) + significand;
        }

        /**
         * ¸¡µãÊý¼õ·¨£¬¿Éµ÷ÓÃ{@link #floatAddition(String, String, int, int, int)
         * floatAddition}·½·¨ÊµÏÖ¡£<br/>
         * Àý£ºfloatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
         *
         * @param operand1
         *            ¶þ½øÖÆ±íÊ¾µÄ±»¼õÊý
         * @param operand2
         *            ¶þ½øÖÆ±íÊ¾µÄ¼õÊý
         * @param eLength
         *            Ö¸ÊýµÄ³¤¶È£¬È¡Öµ´óÓÚµÈÓÚ 4
         * @param sLength
         *            Î²ÊýµÄ³¤¶È£¬È¡Öµ´óÓÚµÈÓÚ 4
         * @param gLength
         *            ±£»¤Î»µÄ³¤¶È
         * @return ³¤¶ÈÎª2+eLength+sLengthµÄ×Ö·û´®±íÊ¾µÄÏà¼õ½á¹û£¬ÆäÖÐµÚ1Î»Ö¸Ê¾ÊÇ·ñÖ¸ÊýÉÏÒç£¨Òç³öÎª1£¬·ñÔòÎª0£©£¬
         *         ÆäÓàÎ»´Ó×óµ½ÓÒÒÀ´ÎÎª·ûºÅ¡¢Ö¸Êý£¨ÒÆÂë±íÊ¾£©¡¢Î²Êý£¨Ê×Î»Òþ²Ø£©¡£ÉáÈë²ßÂÔÎªÏò0ÉáÈë
         */
        public String floatSubtraction(String operand1, String operand2, int eLength, int sLength, int gLength) {
            return floatAddition(operand1, negate(operand2.charAt(0)) + operand2.substring(1), eLength, sLength, gLength);
        }

        /**
         * ¸¡µãÊý³Ë·¨£¬¿Éµ÷ÓÃ{@link #integerMultiplication(String, String, int)
         * integerMultiplication}µÈ·½·¨ÊµÏÖ¡£<br/>
         * Àý£ºfloatMultiplication("00111110111000000", "00111111000000000", 8, 8)
         *
         * @param operand1
         *            ¶þ½øÖÆ±íÊ¾µÄ±»³ËÊý
         * @param operand2
         *            ¶þ½øÖÆ±íÊ¾µÄ³ËÊý
         * @param eLength
         *            Ö¸ÊýµÄ³¤¶È£¬È¡Öµ´óÓÚµÈÓÚ 4
         * @param sLength
         *            Î²ÊýµÄ³¤¶È£¬È¡Öµ´óÓÚµÈÓÚ 4
         * @return ³¤¶ÈÎª2+eLength+sLengthµÄ×Ö·û´®±íÊ¾µÄÏà³Ë½á¹û,ÆäÖÐµÚ1Î»Ö¸Ê¾ÊÇ·ñÖ¸ÊýÉÏÒç£¨Òç³öÎª1£¬·ñÔòÎª0£©£¬
         *         ÆäÓàÎ»´Ó×óµ½ÓÒÒÀ´ÎÎª·ûºÅ¡¢Ö¸Êý£¨ÒÆÂë±íÊ¾£©¡¢Î²Êý£¨Ê×Î»Òþ²Ø£©¡£ÉáÈë²ßÂÔÎªÏò0ÉáÈë
         */
        public String floatMultiplication(String operand1, String operand2, int eLength, int sLength) {
            char sign = XOr(operand1.charAt(0), operand2.charAt(0));
            boolean mark = true;
            for (int i = 1; i <= eLength + sLength; i++) {
                if (operand1.charAt(i) != '0') {
                    mark = false;
                }
            }
            if (mark) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < eLength + sLength; i++) {
                    sb.append("0");
                }
                return "0" + sign + sb.toString();
            }
            mark = true;
            for (int i = 1; i <= eLength + sLength; i++) {
                if (operand2.charAt(i) != '0') {
                    mark = false;
                }
            }
            if (mark) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < eLength + sLength; i++) {
                    sb.append("0");
                }
                return "0" + sign + sb.toString();
            }
            int exponent1 = 0;
            int exponent2 = 0;
            String significand1 = null;
            String significand2 = null;
            for (int i = 0; i < eLength; i++) {
                exponent1 = exponent1 + (operand1.charAt(i + 1) - '0') * (int) Math.pow(2, eLength - i - 1);
                exponent2 = exponent2 + (operand2.charAt(i + 1) - '0') * (int) Math.pow(2, eLength - i - 1);
            }
            if (exponent1 > 0) {
                significand1 = "1" + operand1.substring(1 + eLength);
            } else {
                significand1 = "0" + operand1.substring(1 + eLength);
                exponent1--;
                int num = significand1.indexOf('1');
                for (int i = 0; i < num; i++) {
                    significand1 = significand1.substring(1) + "0";
                    exponent1--;
                }
            }
            if (exponent2 > 0) {
                significand2 = "1" + operand2.substring(1 + eLength);
            } else {
                significand2 = "0" + operand2.substring(1 + eLength);
                exponent2--;
                int num = significand2.indexOf('1');
                for (int i = 0; i < num; i++) {
                    significand2 = significand2.substring(1) + "0";
                    exponent2--;
                }
            }
            int exponentValue = exponent1 + exponent2 - (int) Math.pow(2, eLength - 1) + 1;
            int length1 = (significand1.length() / 4 + 1) * 4;
            int length2 = (significand2.length() / 4 + 1) * 4;
            int count = length1 - significand1.length() + length2 - significand2.length();
            while (significand1.length() < length1) {
                significand1 = "0" + significand1;
            }
            while (significand2.length() < length2) {
                significand2 = "0" + significand2;
            }
            String significand = integerMultiplication(significand1, significand2, length1 + length2).substring(1);
            int num = significand.indexOf('1');
            significand = significand.substring(count);
            if (significand.length() + count - num == 2 * sLength + 1) {
                significand = significand.substring(2);
            } else {
                exponentValue++;
                significand = significand.substring(1);
            }
            if (exponentValue <= 0) {
                significand = "1" + significand;
                int eV = exponentValue;
                for (int i = 0; i > eV; i--) {
                    if (i != 0) {
                        significand = "0" + significand;
                    }
                    exponentValue++;
                }
            }
            if (exponentValue >= Math.pow(2, eLength) - 1) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < eLength; i++) {
                    sb.append("1");
                }
                for (int i = 0; i < sLength; i++) {
                    sb.append("0");
                }
                return "1" + sign + sb.toString();
            } else if ((exponentValue == 0) && (!significand.substring(0, sLength).contains("1"))) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < eLength; i++) {
                    sb.append("0");
                }
                for (int i = 0; i < sLength; i++) {
                    sb.append("0");
                }
                return "1" + sign + sb.toString();
            }
            if (significand.length() < sLength) {
                for (int i = significand.length(); i < sLength; i++) {
                    significand = significand + "0";
                }
            } else {
                significand = significand.substring(0, sLength);
            }
            return "0" + sign + integerRepresentation(exponentValue + "", eLength) + significand;
        }

        /**
         * ¸¡µãÊý³ý·¨£¬¿Éµ÷ÓÃ{@link #integerDivision(String, String, int) integerDivision}
         * µÈ·½·¨ÊµÏÖ¡£<br/>
         * Àý£ºfloatDivision("00111110111000000", "00111111000000000", 8, 8)
         *
         * @param operand1
         *            ¶þ½øÖÆ±íÊ¾µÄ±»³ýÊý
         * @param operand2
         *            ¶þ½øÖÆ±íÊ¾µÄ³ýÊý
         * @param eLength
         *            Ö¸ÊýµÄ³¤¶È£¬È¡Öµ´óÓÚµÈÓÚ 4
         * @param sLength
         *            Î²ÊýµÄ³¤¶È£¬È¡Öµ´óÓÚµÈÓÚ 4
         * @return ³¤¶ÈÎª2+eLength+sLengthµÄ×Ö·û´®±íÊ¾µÄÏà³Ë½á¹û,ÆäÖÐµÚ1Î»Ö¸Ê¾ÊÇ·ñÖ¸ÊýÉÏÒç£¨Òç³öÎª1£¬·ñÔòÎª0£©£¬
         *         ÆäÓàÎ»´Ó×óµ½ÓÒÒÀ´ÎÎª·ûºÅ¡¢Ö¸Êý£¨ÒÆÂë±íÊ¾£©¡¢Î²Êý£¨Ê×Î»Òþ²Ø£©¡£ÉáÈë²ßÂÔÎªÏò0ÉáÈë
         */
        public String floatDivision(String operand1, String operand2, int eLength, int sLength) {
            char sign = XOr(operand1.charAt(0), operand2.charAt(0));
            boolean mark = true;
            for (int i = 1; i <= eLength + sLength; i++) {
                if (operand1.charAt(i) != '0') {
                    mark = false;
                }
            }
            if (mark) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < eLength + sLength; i++) {
                    sb.append("0");
                }
                return "0" + sign + sb.toString();
            }
            mark = true;
            for (int i = 1; i <= eLength + sLength; i++) {
                if (operand2.charAt(i) != '0') {
                    mark = false;
                }
            }
            if (mark) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < eLength + sLength; i++) {
                    sb.append("0");
                }
                return "0" + sign + sb.toString();
            }
            int exponent1 = 0;
            int exponent2 = 0;
            String significand1 = null;
            String significand2 = null;
            for (int i = 0; i < eLength; i++) {
                exponent1 = exponent1 + (operand1.charAt(i + 1) - '0') * (int) Math.pow(2, eLength - i - 1);
                exponent2 = exponent2 + (operand2.charAt(i + 1) - '0') * (int) Math.pow(2, eLength - i - 1);
            }
            if (exponent1 > 0) {
                significand1 = "1" + operand1.substring(1 + eLength);
            } else {
                significand1 = "0" + operand1.substring(1 + eLength);
                exponent1--;
                int num = significand1.indexOf('1');
                for (int i = 0; i < num; i++) {
                    significand1 = significand1.substring(1) + "0";
                    exponent1--;
                }
            }
            if (exponent2 > 0) {
                significand2 = "1" + operand2.substring(1 + eLength);
            } else {
                significand2 = "0" + operand2.substring(1 + eLength);
                exponent2--;
                int num = significand2.indexOf('1');
                for (int i = 0; i < num; i++) {
                    significand2 = significand2.substring(1) + "0";
                    exponent2--;
                }
            }
            int exponentValue = exponent1 - exponent2 + (int) Math.pow(2, eLength - 1) - 1;
            int length1 = (significand1.length() / 4 + 1) * 4;
            int length2 = (significand2.length() / 4 + 1) * 4;
            int count = length1 - significand1.length() + length2 - significand2.length();
            while (significand1.length() < length1) {
                significand1 = "0" + significand1;
            }
            while (significand2.length() < length2) {
                significand2 = "0" + significand2;
            }
            StringBuilder sb0 = new StringBuilder();
            for (int i = 0; i < sLength; i++) {
                sb0.append("0");
            }
            String significand = integerDivision(significand1 + sb0.toString(), significand2, length1 + length2)
                    .substring(1, 1 + length1 + length2);
            if (significand.charAt(0) == '1') {
                significand = negateInteger(significand);
            }
            int num = significand.indexOf('1');
            exponentValue = exponentValue + significand.length() - num -  sLength;
            significand = significand.substring(num + 1);

            if (exponentValue <= 0) {
                significand = "1" + significand;
                int eV = exponentValue;
                for (int i = 0; i > eV; i--) {
                    if (i != 0) {
                        significand = "0" + significand;
                    }
                    exponentValue++;
                }
            }
            if (exponentValue >= Math.pow(2, eLength) - 1) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < eLength; i++) {
                    sb.append("1");
                }
                for (int i = 0; i < sLength; i++) {
                    sb.append("0");
                }
                return "1" + sign + sb.toString();
            } else if ((exponentValue == 0) && (!significand.substring(0, sLength).contains("1"))) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < eLength; i++) {
                    sb.append("0");
                }
                for (int i = 0; i < sLength; i++) {
                    sb.append("0");
                }
                return "1" + sign + sb.toString();
            }
            if (significand.length() < sLength) {
                for (int i = significand.length(); i < sLength; i++) {
                    significand = significand + "0";
                }
            } else {
                significand = significand.substring(0, sLength);
            }
            return "0" + sign + integerRepresentation(exponentValue + "", eLength) + significand;
        }


}