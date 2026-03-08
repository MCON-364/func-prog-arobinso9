package edu.touro.las.mcon364.func_prog.homework;

import java.util.*;
import java.util.function.*;

/**
 * ============================================================
 *  Smart Data Processing Engine
 * ============================================================
 *
 * Goal:
 *  - Build a reusable functional pipeline engine
 *  - Use Consumer, Supplier, Predicate, Function
 *  - Use Optional
 *  - Use chaining
 *  - Use switch expression
 *
 * Estimated time: ~2 hours
 *
 * Rules:
 *  - Use lambdas (no anonymous classes)
 *  - Use Optional where requested
 *  - Use functional interface chaining where appropriate
 */
public class SmartDataEngine {

    // ============================================================
    // PART 1 — GENERIC PIPELINE ENGINE
    // ============================================================

    /**
     * TODO:
     * Implement a generic pipeline.
     *
     * Behavior:
     *  1. Filter using Predicate -> .test()
     *  2. Transform using Function -> .apply()
     *  3. Pass result to Consumer -> .accept()
     */
    // all inputs are type T, outputs are type R
    public static <T, R> void pipeline(
            List<T> input,
            Predicate<T> filter,
            Function<T, R> mapper,
            Consumer<R> consumer
    ) {
        for(T item : input) {
            if(filter.test(item)) {
                R transformed = mapper.apply(item);
                consumer.accept(transformed);
            }
        }

    }

    // ============================================================
    // PART 2 — OPTIONAL PROCESSING
    // ============================================================

    /**
     * Implement a safe divide method.
     *
     * - If denominator is 0 → return Optional.empty()
     * - Otherwise return Optional.of(result)
     */
    public static Optional<Double> safeDivide(double a, double b) {
        if(b==0)
            return Optional.empty();
        double result= a/b;
        return Optional.of(result);
    }

    /**
     * Use Optional chaining:
     *
     *  - Divide two numbers using safeDivide(...)
     *  - If a value is present, multiply the result by 10
     *  - If empty, return -1.0
     *
     * Reminder:
     *  - Optional.map(...) transforms the value ONLY if it is present.
     *  - If the Optional is empty, map() does nothing and the empty
     *    Optional continues down the chain.
     *  - Use orElse(...) to provide a default value when empty.
     */
    public static double processDivision(double a, double b) {
        return safeDivide(a,b).map(result->result*10).orElse(-1.0);
        // if the safeDivide return an Optional with an obj inside, then .map will
        //transform the value with the lambda. If there is no value inside, .map won't do anything.
        // So- regardless of if there is a value inside the Optional, we continue down the
        // chain, and hit the orElse block. The orElse block provides a defualt value
        // when the Optional was empty.
    }

    // ============================================================
    // PART 3 — DATA TYPE ROUTER (Pattern Matching Switch)
    // ============================================================

    /**
     * Use switch expression with pattern matching.
     *
     * Behavior:
     *  - If Integer → return square
     *  - If String → return uppercase
     *  - If Double → return rounded value
     *  - Otherwise → return "Unsupported"
     *
     * Must use switch expression (arrow syntax).
     */
    public static Object transformObject(Object input) {

        // Example structure (not solution):
        // Java checks if input is an instance of the class (example - Integer)
        // If the test passes, Java automatically creates a local variable ("i" in this case)
        // and assigns the value of input to it, already cast to the correct type.

         return switch (input) {
             case Integer i -> i*i;
             case String s  -> s.toUpperCase();
             case Double d  -> Math.round(d);
             default -> "Unsupported";
         };
    }

    // ============================================================
    // PART 4 — FUNCTION CHAINING
    // ============================================================

    /**
     * Create and return a Function<String, Integer>
     * that performs the following transformations in order:
     *
     *   1. Trim leading and trailing whitespace
     *   2. Convert the string to lowercase
     *   3. Return the length of the final string
     *
     * Guidance:
     *   - You should NOT write one large lambda block.
     *   - Instead, create smaller Function variables
     *     and combine them using function chaining.
     *
     *   - Use:
     *         andThen(...)  → left function runs first
     *         compose(...)  → right function runs first
     *
     *   - Think carefully about types:
     *       trim:        String → String
     *       toLowerCase: String → String
     *       length:      String → Integer
     *
     *   - The final returned function must be:
     *       Function<String, Integer>
     *
     * This exercise reinforces how data flows through a functional pipeline.
     */

    public static Function<String, Integer> buildStringLengthPipeline() {
        // we are not passing in an actual string bc this method doesn't run the logic,
        // rather- it builds the logic. Its the Function. So now when we call
        // buildStringLengthPipeline.applu("Hello"); --> the Function will be called
        // and will run. We will then refer to the String "Hello" as "s" in our case-
        // as I did with the lambdas.


        // Trim leading and trailing whitespace
        Function<String, String> trimmer = (s) -> s.trim();
        // Convert the string to lowercase
        Function<String, String> toLower = (s) -> s.toLowerCase();
        // Return the length of the final string
        Function <String, Integer> length = (s) -> s.length();

        // Pipeline1 and Pipeline2 return the exact same result
        // Pipeline using andThen L -> R
        Function <String, Integer> pipeLine1 = trimmer.andThen(toLower).andThen(length);
        // Pipeline using compose R -> L
        Function<String, Integer> pipeLine2 = length.compose(toLower).compose(trimmer);
        return pipeLine1;
    }

    // ============================================================
    // PART 5 — MINI APPLICATION
    // ============================================================

   /**
     * Implement this method using ALL four functional interfaces:
     *
     *  - Supplier  → generate random integers
     *  - Predicate → filter numbers > 50
     *  - Function  → convert Integer → "Score: X"
     *  - Consumer  → print the final result
     *
     * Required Behavior:
     *  1. Generate 10 random integers between 1 and 100.
     *     (Call supplier.get() multiple times.)
     *  2. Keep only numbers greater than 50.
     *  3. Convert each remaining number into a formatted string.
     *  4. Print each formatted string.
     *
     * Important Guidance:
     *  - Do NOT use Streams.
     *  - Do NOT hardcode logic directly inside the loop.
     *  - First define the functional interfaces.
     *  - Then generate a List<Integer>.
     *  - Then pass everything into your pipeline() method.
     *
     * Think in terms of behavior injection:
     *  - The pipeline should NOT know how random numbers are created.
     *  - The pipeline should NOT know how formatting works.
     *
     * It should only orchestrate the behavior passed to it.
     *
     * This method integrates everything you learned in this project.
     */

    public static void runScoreProcessor() {
        // 1- Supplier  → generate random integers- Generate 10 random integers between 1 and 100.
        // (Call supplier.get() multiple times.)
        // Supplier -> .get()
        // random.nextInt(100) gives us ints from 0 thru 99. so we do +1
        // for a specific range that does not start at 0 or 1, we can use this formula:
        // random.nextInt(max - min + 1) + min -> just playing with the offset:)
        Random random = new Random();
        Supplier<Integer> generateRandIntegers = () -> random.nextInt(100)+1;

        // 2- Predicate → filter numbers > 50
        // Predicate -> .test()
        Predicate <Integer> filterInt = number -> number > 50;

        // 3- Function  → convert Integer → "Score: X" - Convert each remaining number into a formatted string.
        // Function -> .apply()
        Function<Integer, String> convertor= number -> "Score: "+number;

        // 4- Consumer  → print the final result
        // Consumer -> .accept()
        Consumer<String> printer = (formatted_number) -> System.out.println(formatted_number);

        //get the list of random numbers
        List <Integer> list_of_integers = new ArrayList<>();
        for(int i=0;i<10;i++) {
            int number= generateRandIntegers.get();
            list_of_integers.add(number);
        }
        pipeline(list_of_integers, filterInt, convertor, printer);
    }

}
