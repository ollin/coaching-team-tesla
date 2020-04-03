package ch.css.booleancalculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;



public class BooleanCalculatorTest {
    
    @ParameterizedTest
    @CsvSource({
        "T, T",
        "TR, TR",
        "TRU, TRU",
        "T AND FALSE, T",
        "TRUE AND F, F",
        
    })
    void excIfNotKnowsElement(final String text, final String fehlerFragment) {
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () ->  {
            new BooleanCalculator().evaluate(text);
        });
        
        assertThat(exc.getMessage()).contains("Can not read: " + fehlerFragment);        
    }

    @ParameterizedTest
    @CsvSource({
        "TRUE, true",
        "FALSE, false",
        // NOT
        "NOT FALSE, true",
        "NOT TRUE, false",
        // AND
        "FALSE AND FALSE, false",
        "FALSE AND TRUE, false",
        "TRUE AND FALSE, false",
        "TRUE AND TRUE, true",
        // OR
        "FALSE OR FALSE, false",
        "FALSE OR TRUE, true",
        "TRUE OR FALSE, true",
        "TRUE OR TRUE, true",
        // Kombis
        "TRUE OR TRUE OR TRUE AND FALSE, true",
        "TRUE OR FALSE AND NOT FALSE, true",
        "TRUE AND NOT NOT TRUE OR FALSE, true",
        // Klammer
        // TODO "( TRUE AND NOT NOT TRUE OR FALSE ), true",
        
    })
    public void evaluateBooleanFromString(String givenString, boolean expectedBoolean) {
        // given
        // when
        boolean actual = new BooleanCalculator().evaluate(givenString);
        // then
        assertThat(actual).isEqualTo(expectedBoolean);
    }

    @Test
    public void shouldSplitFalse() {
        //arrange
        String given = "FALSE";
        List<String> expectedElements = Arrays.asList("FALSE");
        //act
        List<String> actual = new BooleanCalculator().split(given);
        // assert
        assertThat(actual).isEqualTo(expectedElements);
    }

    @Test
    public void shouldSplitNotFalse() {
        // arrange
        String given = "NOT FALSE";
        List<String> expectedElements = Arrays.asList("NOT", "FALSE");
        // act
        List<String> actual = new BooleanCalculator().split(given);
        // assert
        assertThat(actual).isEqualTo(expectedElements);
    }
    
    @Test
    public void createTrueElement() {
        // arrange
        String given = "TRUE";
        // act
        Element actual = new BooleanCalculator().toElement(given);
        // assert
        assertThat(actual.getClass()).isEqualTo(Element.True.class);
    }
    
    @Test
    public void createFalseElement() {
        // arrange
        String given = "FALSE";
        // act
        Element actual = new BooleanCalculator().toElement(given);
        // assert
        assertThat(actual.getClass()).isEqualTo(Element.False.class);
    }
}

/**
 *
 * ________________________________________________________________________________________________________
 * Boolean Calculator:  Entwickle einen Boolean Calculator. Er bekommt einen String als Input und wertet diesen String zu einem boolean aus.
 *
 * 1.) einzelne Werte:  "TRUE" -> true "FALSE" -> false
 *
 * 2.) NOT Operator  "NOT TRUE" -> false
 *
 * 3.) AND Operator  "TRUE AND FALSE" -> false "TRUE AND TRUE" -> true
 *
 * 4.) OR Operator  "TRUE OR FALSE" -> true "FALSE OR FALSE" -> false
 *
 * 5.) Beliebige Anzahl von AND und OR Operatoren, NOT wird zuerst ausgewertet, dann AND und zuletzt OR  "TRUE OR TRUE OR TRUE AND FALSE" -> true "TRUE OR FALSE AND NOT FALSE" -> true 
 *
 * 6.) Klammern  "(TRUE OR TRUE OR TRUE) AND FALSE" -> false "NOT (TRUE AND TRUE)" -> false
 *
 * 7.) Drucke den abstrakten Syntax Baum aus, der die Berechnung repräsentiert
 * "(TRUE OR TRUE OR TRUE) AND FALSE" ->
 * AND
 * |_OR |
 * |_TRUE
 * ||_OR
 * | |_TRUE
 * | |_TRUE
 * |_FALSE
 *
 * "NOT ((TRUE OR TRUE) OR (TRUE AND FALSE))" ->
 *
 *  NOT
 * |_OR
 *  |_OR  
 *  |  |_TRUE 
 *  |  |_TRUE 
 *  |_AND 
 *     |_TRUE 
 *     |_FALSE 
 **/
