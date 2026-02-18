package edu.touro.las.mcon364.func_prog.exercises;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.lang.Math.random;

/**
 * Functional Interface Practice
 *
 * In this assignment you will:
 *  - Create and return different functional interfaces
 *  - Apply them
 *  - Practice chaining where appropriate
 *
 * IMPORTANT:
 *  - Use lambdas
 *  - Do NOT use anonymous classes
 */
public class FunctionalInterfaceExercises {

    // =========================================================
    // PART 1 — SUPPLIERS
    // =========================================================

    /**
     * 1) Create a Supplier that returns the current year.
     *
     * Hint:
     * You can get the current date using:
     *     LocalDate.now()
     *
     * Then extract the year using:
     *     getYear()
     *
     * Example (not the solution):
     *
     */
    public static Supplier<Integer> currentYearSupplier() {
        //Supplier is a lambda
        Supplier<Integer> supplier = () -> LocalDate.now().getYear();
        return supplier;
    }

    /**
     * 2) Create a Supplier that generates a random number
     * between 1 and 100.
     */
    public static Supplier<Integer> randomScoreSupplier() {
        //Math.random() expects no params - it returns a decimal btwn 0 and 1. so we multiply
        // by 100 and then add one, so we are within the range if 1 and 100.
        Supplier<Integer> supplier= () -> (int) (Math.random() * 100) + 1;
        return supplier;
    }

    // =========================================================
    // PART 2 — PREDICATES
    // =========================================================

    /**
     * 3) Create a Predicate that checks whether
     * a string is all uppercase.
     */
    public static Predicate<String> isAllUpperCase() {
        //Predicate is a lambda- obvs accepts params bc its testing them
        // we compare the string 's' to its uppercase version.
        // If they are equal, it means the string was already all uppercase.
        Predicate<String> predicate = (s) -> s.equals(s.toUpperCase());
        return predicate;
    }

    /**
     * 4) Create a Predicate that checks whether
     * a number is positive AND divisible by 5.
     *
     * Hint: consider chaining.
     */
    public static Predicate<Integer> positiveAndDivisibleByFive() {
        //predicate runs test() so returns true or false
        Predicate<Integer> predicate = (s) -> s % 5 == 0 && s>0;
        return predicate;
    }

    // =========================================================
    // PART 3 — FUNCTIONS
    // =========================================================

    /**
     * 5) Create a Function that converts
     * a temperature in Celsius to Fahrenheit.
     *
     * Formula: F = C * 9/5 + 32
     */
    public static Function<Double, Double> celsiusToFahrenheit() {
        // functions accept params and return result
        Function<Double, Double> function = (s) -> s * 1.8 + 32;
        return function;
    }

    /**
     * 6) Create a Function that takes a String
     * and returns the number of vowels in it.
     *
     * Bonus: Make it case-insensitive.
     */
    public static Function<String, Integer> countVowels() {
        Function<String, Integer> function = (s) -> {
            int count = 0;
            // I used upper and lower to handle case-insensitivity
            String vowels = "aeiouAEIOU";

            // the indexOf(char c) method searches a String for a specific character:
            // if it finds the character, it returns its position (index) (0, 1, 2...).
            // if it cannot find the character, it returns -1.
            // so in our case- we first get the current character and then we use the indexOf
            // method to check if the character is in the string of vowels
            for (int i = 0; i < s.length(); i++) {
                // Check if the character at index i is in our vowels string
                if (vowels.indexOf(s.charAt(i)) != -1) {
                    count++;
                }
            }
            return count;  // we are returning count in the lambda
        };
        return function;
    }

    // =========================================================
    // PART 4 — CONSUMERS
    // =========================================================

    /**
     * 7) Create a Consumer that prints a value
     * surrounded by "***"
     *
     * Example output:
     * *** Hello ***
     */
    public static Consumer<String> starPrinter() {
        Consumer <String>consumer = (value) -> System.out.println("*** "+value+" ***");
        return consumer;
    }

    /**
     * 8) Create a Consumer that prints the square
     * of an integer.
     */
    public static Consumer<Integer> printSquare() {
        Consumer<Integer> consumer = (value) -> System.out.println(value*value);
        return consumer;
    }

    // =========================================================
    // PART 5 — APPLYING FUNCTIONAL INTERFACES
    // =========================================================

    /**
     * 9) Apply:
     *  - A Predicate
     *  - A Function
     *  - A Consumer
     *
     * Process the list as follows:
     *  - Keep only strings longer than 3 characters
     *  - Convert them to lowercase
     *  - Print them
     */
    public static void processStrings(List<String> values) {
        // the two types inside the < > are : the first is the input type and the second is
        // the return type
        // first we need to test(), then we meed to take that value in and return,
        // and then do something with that value. In regards to the Function below.
        //Predicate only has one type inside the braces which refers to the input-
        // we dont need to specify a type for the output bc its obvious. Its boolean

        //we always put the method name before the specific method we need! like longerThan3.test()
        //Predicate- our test --> will use .test()
        // Predicate
        Predicate<String> longerThan3= s -> s.length()>3;

        //The Function - receives input and returns --> will use .apply()
        Function<String, String> to_Lower_Case=  s -> s.toLowerCase();

        //The consumer - receives, but no return --> will use .accept()
        Consumer<String> printString= s -> System.out.println(s);

        for(String value:values){
            if(longerThan3.test(value)){
                String result = to_Lower_Case.apply(value);
                printString.accept(result);
            }
        }
    }

    /**
     * 10) Apply:
     *  - A Supplier
     *  - A Predicate
     *  - A Consumer
     *
     * Generate 5 random scores.
     * Print only those above 70.
     */
    public static void generateAndFilterScores() {
        // supplier only returns-  will generate the random number --> use .get()
        Supplier<Integer> generateRandomNumber= () -> (int) (Math.random() * 100) + 1;
        //Predicate will perform test. Is the number higher than 70? --> use test()
        Predicate<Integer> isAbove70= x -> x>70;
        //Consumer will print the results --> use .accept()
        Consumer<Integer> printer= (x) -> System.out.println(x);

        for(int i=0;i<5;i++){
            // get the value
            int score = generateRandomNumber.get();
            // test the value
            if (isAbove70.test(score)) {
                // print the value
                printer.accept(score);
            }

        }
    }
}
