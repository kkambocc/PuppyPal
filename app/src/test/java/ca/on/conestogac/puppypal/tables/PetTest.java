package ca.on.conestogac.puppypal.tables;

import org.junit.Test;

import static org.junit.Assert.*;

public class PetTest {

    @Test
    public void breedValidation_inputValid() {
        String input = "Golden Retriever";
        boolean output;
        boolean expected = true;

        Pet pet = new Pet();

        output = pet.breedValidation(input);

        assertEquals(expected, output);
    }

    @Test
    public void breedValidation_inputEmpty() {
        String input = "";
        boolean output;
        boolean expected = false;

        Pet pet = new Pet();

        output = pet.breedValidation(input);

        assertEquals(expected, output);
    }

    @Test
    public void breedValidation_inputContainsNumbers() {
        String input = "Golden Retriever1";
        boolean output;
        boolean expected = false;

        Pet pet = new Pet();

        output = pet.breedValidation(input);

        assertEquals(expected, output);
    }

    @Test
    public void breedValidation_inputContainsSpecialCharacters() {
        String input = "Golden Retriever!";
        boolean output;
        boolean expected = false;

        Pet pet = new Pet();

        output = pet.breedValidation(input);

        assertEquals(expected, output);
    }

    @Test
    public void breedValidation_inputLengthGreaterThan20Characters() {
        String input = "Golden Retriever asdfg";
        boolean output;
        boolean expected = false;

        Pet pet = new Pet();

        output = pet.breedValidation(input);

        assertEquals(expected, output);
    }

    @Test
    public void weightValidation_inputValid() {
        String input = "100";
        boolean output;
        boolean expected = true;

        Pet pet = new Pet();

        output = pet.weightValidation(input);

        assertEquals(expected, output);
    }

    @Test
    public void weightValidation_inputEmpty() {
        String input = "";
        boolean output;
        boolean expected = false;

        Pet pet = new Pet();

        output = pet.weightValidation(input);

        assertEquals(expected, output);
    }

    @Test
    public void weightValidation_inputGreaterThan999() {
        String input = "1000";
        boolean output;
        boolean expected = false;

        Pet pet = new Pet();

        output = pet.weightValidation(input);

        assertEquals(expected, output);
    }

    @Test
    public void ageValidation_inputValid() {
        String input = "10";
        boolean output;
        boolean expected = true;

        Pet pet = new Pet();

        output = pet.ageValidation(input);

        assertEquals(expected, output);
    }

    @Test
    public void ageValidation_inputEmpty() {
        String input = "";
        boolean output;
        boolean expected = false;

        Pet pet = new Pet();

        output = pet.ageValidation(input);

        assertEquals(expected, output);
    }

    @Test
    public void ageValidation_inputLengthGreaterThan2Characters() {
        String input = "100";
        boolean output;
        boolean expected = false;

        Pet pet = new Pet();

        output = pet.ageValidation(input);

        assertEquals(expected, output);
    }

    @Test
    public void nameValidation_inputValid() {
        String input = "Pal";
        boolean output;
        boolean expected = true;

        Pet pet = new Pet();

        output = pet.nameValidation(input);

        assertEquals(expected, output);
    }

    @Test
    public void nameValidation_inputEmpty() {
        String input = "";
        boolean output;
        boolean expected = false;

        Pet pet = new Pet();

        output = pet.nameValidation(input);

        assertEquals(expected, output);
    }

    @Test
    public void nameValidation_inputContainsNumbers() {
        String input = "Pal1";
        boolean output;
        boolean expected = false;

        Pet pet = new Pet();

        output = pet.nameValidation(input);

        assertEquals(expected, output);
    }

    @Test
    public void nameValidation_inputContainsSpecialCharacters() {
        String input = "Pal!";
        boolean output;
        boolean expected = false;

        Pet pet = new Pet();

        output = pet.nameValidation(input);

        assertEquals(expected, output);
    }

    @Test
    public void nameValidation_inputLengthGreaterThan20Characters() {
        String input = "Palllllllllllllllllll";
        boolean output;
        boolean expected = false;

        Pet pet = new Pet();

        output = pet.nameValidation(input);

        assertEquals(expected, output);
    }
}