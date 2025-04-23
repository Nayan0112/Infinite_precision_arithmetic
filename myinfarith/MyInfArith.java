package myinfarith;
import arbitraryarithmetic.*;

public class MyInfArith {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java MyInfArith <int/float> <add/sub/mul/div> <num1> <num2>");
            return;
        }

        String type = args[0];
        String op = args[1];
        String n1 = args[2];
        String n2 = args[3];

        try {
            if (type.equals("int")) {
                AInteger a = new AInteger(n1);
                AInteger b = new AInteger(n2);
                switch (op) {
                    case "add": System.out.println(a.add(b).getValue()); break;
                    case "sub": System.out.println(a.subtract(b).getValue()); break;
                    case "mul": System.out.println(a.multiply(b).getValue()); break;
                    case "div": System.out.println(a.divide(b).getValue()); break;
                    default: System.out.println("Unknown operation.");
                }
            } else if (type.equals("float")) {
                AFloat a = new AFloat(n1);
                AFloat b = new AFloat(n2);
                switch (op) {
                    case "add": System.out.println(a.add(b).getValue()); break;
                    case "sub": System.out.println(a.subtract(b).getValue()); break;
                    case "mul": System.out.println(a.multiply(b).getValue()); break;
                    case "div": System.out.println(a.divide(b).getValue()); break;
                    default: System.out.println("Unknown operation.");
                }
            } else {
                System.out.println("Invalid type: must be int or float.");
            }
        } catch (ArithmeticException e) {
            System.out.println("Division by zero error");
        }
    }
}
