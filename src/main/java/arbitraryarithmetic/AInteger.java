package arbitraryarithmetic;

public class AInteger {

    public String value;
    public Boolean isNegative;

    //default constructor 
    public AInteger(){
        this.isNegative = false;
        this.value = "0";
    }

    //constrctor when string is an input
    public AInteger(String s){
        //Invalid input handling
        int countSign = 0;

        for(int i = 0; i < s.length(); i++){
            if(countSign > 1) throw new IllegalArgumentException("Invalid Input");
            if((s.charAt(0) == '+' || s.charAt(0) == '-')) { countSign ++; continue;}
            if(i != 0 && (s.charAt(i) == '+' || s.charAt(i) == '-')) { throw new IllegalArgumentException("Invalid Input");}
            if(!Character.isDigit(s.charAt(i))) throw new IllegalArgumentException("Invalid Input");
        }

        //assigning signs and values
        if (s.charAt(0) == '-') {
            this.isNegative = true;
            this.value = s.substring(1);
        } else {
            this.isNegative = false;
            this.value = s;
        }
    }
    //take another AInterger as input *testing*
    public AInteger(AInteger other) {
        this.value = other.value;
        this.isNegative = other.isNegative;
    }

    //parses AInteger
    public static AInteger parse(String s) {
        return new AInteger(s);
    }

    //truncates leading zeros
    public String truncate(String str) {
        while (str.length() > 1 && str.charAt(0) == '0') {
            str = str.substring(1);
        }
        return str;
    }

    //compares 2 strings values
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
    
    //method to get value in a formatted form
    public String getValue(){
        if(truncate(this.value).equals("0")) { this.isNegative = false; }
        return ((this.isNegative ? "-" : "") + truncate(this.value));
    }

    //method to add/substract checking the sign
    public AInteger add(AInteger other){
        if(this.isNegative == other.isNegative){
            String result = addStrings(this.value, other.value);
            return new AInteger((this.isNegative ? "-" : "") + result);
        }else{
            AInteger temp = new AInteger(other.value);
            temp.isNegative = !other.isNegative;
            return this.subtract(temp);
        }
    }

    //method to add/subtract checking the sign
    public AInteger subtract(AInteger other) {
        if (this.isNegative == other.isNegative) {

            if(compareString(this.value, other.value)){
                String result = subtractStrings(this.value, other.value);
                return new AInteger((this.isNegative ? "-" : "") + result);
            }else{
                String result = subtractStrings(other.value, this.value);
                return new AInteger((this.isNegative ? "" : "-") + result);
            }
 
        } else {
            AInteger temp = new AInteger(other.value);
            temp.isNegative = !other.isNegative;
            return this.add(temp);
        }
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

    //method to subtract 2 strings 
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

    //method to multiply 2 strings 
    public AInteger multiply( AInteger other){

        AInteger temp = new AInteger();

        String num1 = this.value;
        String num2 = other.value;

          if (num1.equals("0") || num2.equals("0")) {
            temp.value = "0";
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

        temp.value = "";

        boolean check = true;

        for(int digit : result){
            if (digit == 0 && check) continue;
            check = false;
            temp.value = temp.value + digit;
        }

        return temp;

    }

    //method to divide 2 strings 
    public AInteger divide(AInteger other){

        if(this.value.equals("0")){
            return new AInteger("0");
        }
        if(other.value.equals("0")){
            throw new ArithmeticException("Division by zero");
        }
    
        AInteger temp = new AInteger();

        if(this.isNegative != other.isNegative){
            temp.isNegative = true;
        }

        other.isNegative = false;
    
        String num1 = this.value;
        //String num2 = other.value;
        String result = "";
        AInteger remainder = new AInteger("0");
        //int size = num2.length();

        for (int j = 0; j < num1.length(); j++) {
            remainder = new AInteger(remainder.getValue() + num1.charAt(j)); 
    
            AInteger i = new AInteger("0");
            AInteger acc = new AInteger("0");
    
            while (compareString(remainder.getValue(), acc.getValue())) {
                i = i.add(new AInteger("1"));
                acc = other.multiply(i);
            }
    
            i = i.subtract(new AInteger("1"));
            acc = other.multiply(i);
            remainder = remainder.subtract(acc);
    
            result += i.getValue();
        }
    
        other.isNegative = true;
    
        temp.value = temp.truncate(result);
        return temp;
    }
    


    // //testing RAW
    // public static void main(String[] args) {
    //     // Create AInteger instances
    //     AInteger num1 = new AInteger("6969696966969");
    //     AInteger num2 = new AInteger("69");

    //     // Add two large numbers
    //     AInteger sum = num1.add(num2);
    //     System.out.println("Sum: " + sum.getValue());

    //     // Subtract two large numbers
    //     AInteger diff = num1.subtract(num2);
    //     System.out.println("Difference: " + diff.getValue());

    //     //Multiply two large numbers
    //     AInteger prod = num1.multiply(num2);
    //     System.out.println("Product: " + prod.getValue());

    //     //Divide two large numbers
    //     AInteger quo = num1.divide(num2);
    //     System.out.println("Quotent: " + quo.getValue());
    // }


}