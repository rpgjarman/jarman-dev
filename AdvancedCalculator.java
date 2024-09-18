/*  THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING OTHERS
    Robert Jarman */
import java.io.*;
import java.util.*;

public class AdvancedCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Hello, welcome to your AI advanced calculator");
        System.out.println("Do you want to do a math operation or unit conversion?");
        String conditional = sc.nextLine();

        if (conditional.contains("math operation")) {
            System.out.println("Enter the first number: ");
            double num1 = sc.nextDouble();
            
            System.out.println("Enter the second number: ");
            double num2 = sc.nextDouble();
        
            System.out.println("Select an operation: addition, subtraction, multiplication, division, division-remainder");
            String operator = sc.next();
        
            double result = 0.0;
        
            if (operator.contains("addition")) {
               result = num1 + num2;
            } else if (operator.contains("subtraction")) {
                result = num1 - num2;
            } else if (operator.contains("multiplication")) {
                result = num1 * num2;
            } else if (operator.contains("division")) {
                result = num1 / num2;
            } else if (operator.contains("division-remainder")) {
                result = num1 % num2;
            } else {
                System.out.println("Sorry, I didn't get that. Invalid operator! Could you type that again?");
            }

            System.out.println("Total: " + result);
        } else if (conditional.contains("unit conversion")) {
            System.out.println("What do you want to convert (from imperial to metric): Fahrenheit to Celsius, Miles to Kilometers, Feet to Meters, Pounds to Kilograms");
            String txt = sc.nextLine();

            System.out.println("Enter the number: ");
            double num3 = sc.nextDouble();

            double convert = 0.0;
            
            if (txt.contains("Fahrenheit to Celsius")) {
                convert = (num3 - 32) * 5/9;
            } else if (txt.contains("Miles to Kilometers")) {
                convert = num3 * 1.609344;
            } else if (txt.contains("Feet to Meters")) {
                convert = num3 * 0.304;
            } else if (txt.contains("Pounds to Kilograms")) {
                convert = num3 * 0.45359237;
            }

            System.out.println("Total: " + convert);
        }

        sc.close();
    }
}