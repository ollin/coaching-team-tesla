package ch.css.booleancalculator;

import java.util.Arrays;
import java.util.List;

public class BooleanCalculator {
    
    private static final String TRUE = "TRUE";
    private static final String FALSE = "FALSE";
    private static final String NOT = "NOT";
    private static final String AND = "AND";
    private static final String OR = "OR";
    private static final String S = " ";

    public boolean evaluate(String given) {

       
        List<String> elements = split(given);
        
        Element elementeBaum = erzeugeBaum(elements);
        
        return elementeBaum.asBoolean();
    }

    private Element erzeugeBaum(List<String> elements) {
        Element elementeBaum = null;
        if (elements.contains(OR)) {
            int indexOf = elements.indexOf(OR);
            
            Element leftElement = erzeugeBaum(elements.subList(0, indexOf));
            Element rightElement = erzeugeBaum(elements.subList(indexOf + 1, elements.size()));

            elementeBaum = new Element.OR(leftElement, rightElement);
        } else if (elements.contains(AND)) {
            int indexOf = elements.indexOf(AND);
            
            Element leftElement = erzeugeBaum(elements.subList(0, indexOf));
            Element rightElement = erzeugeBaum(elements.subList(indexOf + 1, elements.size()));

            elementeBaum = new Element.AND(leftElement, rightElement);
        } else         
        if (elements.contains(NOT)){
            int indexOf = elements.indexOf(NOT);

            Element rightElement = erzeugeBaum(elements.subList(indexOf + 1, elements.size()));

            elementeBaum = new Element.NOT(rightElement);
        } else 
        
        if (TRUE.equals(elements.get(0))) {
            elementeBaum = toElement(elements.get(0));
        } else 
        
        if (FALSE.equals(elements.get(0))) {
            elementeBaum = toElement(elements.get(0));
        } else {
            elementeBaum = toElement(elements.get(0));
        }
        return elementeBaum;
    }

    List<String> split(String given) {
        return Arrays.asList(given.split(S));
    }

    public Element toElement(String given) {
        if (FALSE.equals(given)) {
            return new Element.False();
        } else if (TRUE.equals(given)) {
            return new Element.True();
        };
        
        throw new IllegalArgumentException("Can not read: " + given);
    }
    
    
}


/* **** TPP ******
 ({} → nil) no code at all → code that employs nil
 (nil → constant)
 (constant → constant+) a simple constant to a more complex constant
 (constant → scalar) replacing a constant with a variable or an argument
 (statement → statements) adding more unconditional statements.
 (unconditional → if) splitting the execution path
 (scalar → array)
 (array → container)
 (statement → tail-recursion)
 (if → while)
 (statement → non-tail-recursion)
 (expression → function) replacing an expression with a function or algorithm
 (variable → assignment) replacing the value of a variable.
 (case) adding a case (or else) to an existing switch or if

*/