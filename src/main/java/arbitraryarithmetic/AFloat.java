package arbitraryarithmetic;
import arbitraryarithmetic.AInteger;

public class AFloat{
    Boolean isNegative;
    String integer;
    String fractional;

    //class constructor default
    public AFloat(){
        this.isNegative = false;
        this.integer = "0";
        this.fractional = "0";
    }

    //class constructor when an string is passed
    public AFloat(String s) {
        //checks for any invalid type input 
        int countSign = 0;
        int countDec = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (i == 0 && (c == '+' || c == '-')) {
                countSign++;
                continue;
            }
            if (c == '+' || c == '-') {
                throw new IllegalArgumentException("Invalid Input");
            }
            if (c == '.') {
                countDec++;
                if (countDec > 1) throw new IllegalArgumentException("Invalid Input");
                continue;
            }
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException("Invalid Input");
            }
        }
        
        //assigns vlaues 
        if (s.charAt(0) == '-') {
            this.isNegative = true;
            s = s.substring(1);
        } else {
            this.isNegative = false;
        }
    
        if (s.contains(".")) {
            String[] parts = s.split("\\.", -1);
            this.integer = parts.length > 1 ? parts[0] : "0";
            this.fractional = parts.length > 1 && !parts[1].isEmpty() ? parts[1] : "0";
        } else {
            this.integer = s;
            this.fractional = "0";
        }
        this.integer = truncate(this.integer);
        this.fractional = trim(this.fractional);

    }

    //parses AInteger 
    public static AFloat parse(String s){
        return new AFloat(s);
    }

    //method to extract the value from the class in a formatted manner
    public String getValue(){
        this.integer = truncate(this.integer);
        this.fractional = trim(fractional);
        if(this.fractional.length() > 30) this.fractional = this.fractional.substring(0,30);
        if(this.integer.equals("0") && this.fractional.equals("0")) { this.isNegative = false; }
        return ((this.isNegative ? "-" : "") + this.integer + "." + this.fractional);
    }

    //truncates leading zeros
    public String truncate(String str) {
        while (str.length() > 1 && str.charAt(0) == '0') {
            str = str.substring(1);
        }
        return str;
    }

    //trims the trailing zeros
    public String trim(String str) {
        int i = str.length() - 1;

        if (str.isEmpty() || str.replace("0", "").isEmpty()) {
            return "0"; 
        }

        while (i >= 0 && str.charAt(i) == '0') {
            i--;
        }
        if (i < 0) {
            return "0";
        }
        return str.substring(0, i + 1);
    }
    
    //function to compare values of the string 
    public Boolean compareString(String s1, String s2){

        s1 = truncate(s1);
        s2 = truncate(s2);

        int len1 = s1.length();
        int len2 = s2.length();
    
        if(len1 == len2){
            int i = 0;
            while(i < len1 && s1.charAt(i) == s2.charAt(i)){
                i++;
            }
            if (i == len1) return true;  
            return s1.charAt(i) > s2.charAt(i); 
        }else if(len1 < len2){
            return false;
        }else{
            return true;
        }
    }

    //provides padding for fraction parts
    public String[] equalizeFractionLengths(String f1, String f2) {
        int len1 = f1.length();
        int len2 = f2.length();
    
        if (len1 < len2) {
            f1 += "0".repeat(len2 - len1);
        } else if (len2 < len1) {
            f2 += "0".repeat(len1 - len2);
        }
    
        return new String[]{f1, f2};
    }    

    //method to add 2 strings 
    public String addStrings(String num1, String num2) {
        String result = "";
        int len1 = num1.length();
        int len2 = num2.length();

        int carry = 0;
        int i = len1 - 1, j = len2 - 1;

        while (i >= 0 || j >= 0 || carry != 0) {
            int digit1 = i >= 0 ? num1.charAt(i) - '0' : 0;
            int digit2 = j >= 0 ? num2.charAt(j) - '0' : 0;
            int sum = digit1 + digit2 + carry;
            result = String.valueOf(sum % 10) + result;
            carry = sum / 10;
            i--;
            j--;
        }
        
        return result;
    }

    //method to add two strings 
    public String subtractStrings(String num1, String num2) {
        String result = "";
        int len1 = num1.length();
        int len2 = num2.length();
        int borrow = 0;
        int i = len1 - 1, j = len2 - 1;

        while (i >= 0 || j >= 0) {
            int digit1 = i >= 0 ? num1.charAt(i) - '0' : 0;
            int digit2 = j >= 0 ? num2.charAt(j) - '0' : 0;
            int diff = digit1 - digit2 - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            result = String.valueOf(diff) + result ;
            i--;
            j--;
        }

        return result;
    }

    //method to assign add or substract in form of strings 
    public AFloat add(AFloat other){
        if(this.isNegative == other.isNegative){
            String[] f = equalizeFractionLengths(this.fractional, other.fractional);
            int len = f[0].length();
            String num1 = this.integer + f[0];
            String num2 = other.integer + f[1];
            String sum = addStrings(num1, num2);
            String intg = sum.substring(0, sum.length() - len);
            String frac = sum.substring(sum.length() - len, sum.length());
            return new AFloat((this.isNegative ? "-" : "") + intg + "." + frac);
        }else{
            AFloat temp = new AFloat(other.integer + "." + other.fractional);
            temp.isNegative = !other.isNegative;
            return this.subtract(temp);
        }
    }


    //mrthod to assign add or substract in form of strings 
    public AFloat subtract(AFloat other){
        if(this.isNegative == other.isNegative){
            String[] f = equalizeFractionLengths(this.fractional, other.fractional);
            int len = f[0].length();
            String num1 = this.integer + f[0];
            String num2 = other.integer + f[1];
            if(compareString(num1, num2)){
                String diff = subtractStrings(num1, num2);
                String intg = diff.substring(0, diff.length() - len);
                String frac = diff.substring(diff.length() - len, diff.length());
                return new AFloat((this.isNegative ? "-" : "") + intg + "." + frac);
            }else{
                String diff = subtractStrings(num2, num1);
                String intg = diff.substring(0, diff.length() - len);
                String frac = diff.substring(diff.length() - len, diff.length());
                return new AFloat((this.isNegative ? "" : "-") + intg + "." + frac);
            }
        }else{
            AFloat temp = new AFloat(other.integer + "." + other.fractional);
            temp.isNegative = !other.isNegative;
            return this.add(temp);
        }
    }

    // method to multiply two numbers
    public AFloat multiply(AFloat other){

        AFloat temp = new AFloat();

        String num1 = truncate(this.integer) + trim(this.fractional);
        String num2 = truncate(other.integer) + trim(other.fractional);

          if (num1.equals("00") || num2.equals("00")) {
            temp.integer = "0";
            temp.fractional = "0";
            temp.isNegative = false;
            return temp;
        }

        if(this.isNegative == other.isNegative){
            temp.isNegative = false;
        }else{
            temp.isNegative = true;
        }

        int len1 = num1.length();
        int len2 = num2.length();
        int[] result = new int[len1 + len2];
    
    
        for (int i = len1 - 1; i >= 0; i--) {
            for (int j = len2 - 1; j >= 0; j--) {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int sum = mul + result[i + j + 1];
    
                result[i + j + 1] = sum % 10;
                result[i + j] += sum / 10;
            }
        }

        String acc = "";

        boolean check = true;

        for(int digit : result){
            if (digit == 0 && check) continue;
            check = false;
            acc = acc + digit;
        }

        while (acc.length() <= this.fractional.length() + other.fractional.length()) {
            acc = "0" + acc;
        }

        int index = acc.length() - (this.fractional).length() - (other.fractional).length();
        temp.integer = acc.substring(0, index);
        temp.fractional = acc.substring(index, acc.length());

        return temp;
    }

    //method tp divide 2 numbers
    public AFloat divide(AFloat other) {
        if (this.integer.equals("0") && this.fractional.equals("0")) {
            return new AFloat("0");
        }
        if (other.integer.equals("0") && other.fractional.equals("0")) {
            throw new ArithmeticException("Division by zero");
        }
    
        String num1 = this.integer + this.fractional;
        String num2 = other.integer + other.fractional;
    
        num1 += "0".repeat(1000);
    
        AInteger numerator = new AInteger(num1);
        AInteger denominator = new AInteger(num2);
        AInteger remainder = new AInteger("0");
    
        String quo = "";

        String digits = numerator.getValue();
        for (int j = 0; j < digits.length(); j++) {
            remainder = new AInteger(remainder.getValue() + digits.charAt(j));
    
            AInteger i = new AInteger("0");
            AInteger acc = new AInteger("0");
    
            while (remainder.compareString(remainder.getValue(), acc.getValue())) {
                i = i.add(new AInteger("1"));
                acc = denominator.multiply(i);
            }
    
            i = i.subtract(new AInteger("1"));
            acc = denominator.multiply(i);
            remainder = remainder.subtract(acc);
    
            quo += i.getValue();
        }
    
        int totalDecimalPlaces = this.fractional.length() + 1000 - other.fractional.length();
    
        while (quo.length() <= totalDecimalPlaces) {
            quo = "0" + quo;
        }
    
        String intg = quo.substring(0, quo.length() - totalDecimalPlaces);
        String frac = quo.substring(quo.length() - totalDecimalPlaces);

        AFloat result = new AFloat();
        result.integer = result.truncate(intg);
        result.fractional = result.trim(frac);
        result.isNegative = (this.isNegative != other.isNegative);
    
        return result;
    }
    
    //RAW TESTING IGNORE ###############################################################################################################################

    // public static void main(String[] args) {
    //     System.out.println(new AFloat("9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999.999999999").add(new AFloat("0.000000000000000000000000000000000000000000000000000000000000000000000001")).getValue()); // Large + tiny
    //     System.out.println(new AFloat("1000000000000000").multiply(new AFloat("0.000000000001")).getValue());     // Test rounding
        
        
    // }
    
}