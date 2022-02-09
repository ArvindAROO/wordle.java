// Java Program to Print Colored Text in Console

// import necessary packages
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
 

class Colorize{
    public static void print(String s, String[] colorset){

        final String ANSI_RESET = "\u001B[0m";
        

        // print each letter with its corresponding color
        for(int i=0; i<s.length(); i++){
            System.out.print(colorset[i] + s.charAt(i) + ANSI_RESET);
        }
        System.out.println();
    }
}

class check{
    public static String[] test(String actualWord, String guess){
        final String GREEN = "\u001B[42m";
        final String YELLOW = "\u001B[43m";
        final String RED = "\u001B[41m";
        final String GREY = ""; //passing nothing will make the text grey
        
        String[] sol = {"", "", "", "", ""};
        String[] score = {"", "", "", "", ""};
        // for each letter in guess
        // if it is in correcte dposition in actual word, put green
        // if it is in wrong position, put yellow
        // if its not in actual word, put grey
        for(int i=0; i<actualWord.length(); i++){
            if(actualWord.charAt(i) == guess.charAt(i)){
                sol[i] = GREEN;
                score[i] = "2";
            }
            else if(actualWord.indexOf(guess.charAt(i)) != -1){
                // actualWord.contains(guess.charAt(i))){
                sol[i] = YELLOW;
                score[i] = "1";
            }
            else{
                sol[i] = RED;
                score[i] = "0";
            }
        }
        
        Colorize.print(guess, sol);
        return score;
    }

}

class load{
    public static Object[] loadData()
        throws IOException
    {
        // list that holds strings of a file
        List<String> reader = new ArrayList<String>();
        
        // for some reason, java needs absolute file path, so find current path, and append relative path
        String filePath = new File("").getAbsolutePath();

        //read files
        reader = Files.readAllLines(Paths.get(filePath + "/data/allowed_words.txt"));
        String[] allowedWords = reader.toArray(new String[0]);
        System.out.println("Number of allowed words: " + allowedWords.length);

        reader = Files.readAllLines(Paths.get(filePath + "/data/possible_words.txt"));
        String[] possibleWords = reader.toArray(new String[0]);
        System.out.println("Number of possible words: " + possibleWords.length);

        // for (String eachString : allowedWords) {
        //     System.out.println(eachString);
        // }
        // for (String eachString : possibleWords) {
        //     System.out.println(eachString);
        // }
        return new Object[]{allowedWords, possibleWords};

    }
}

class play{
    final String GREEN = "\u001B[42m";
    final String RED = "\u001B[41m";
    final String ANSI_RESET = "\u001B[0m";
        
    private String[] allowed_words;
    private String[] possible_words;
    private ArrayList<String[]> result;
    
    public void load() throws IOException{
        Object[] data = load.loadData();
        this.allowed_words = (String[]) data[0];
        this.possible_words = (String[]) data[1];
        
    }

    public void results () {
        String MISS = "⬛", MISPLACED = "🟨", EXACT= "🟩";
            
        HashMap <String, String> map = new HashMap<String, String>();
        map.put("0", MISS);
        map.put("1", MISPLACED);
        map.put("2", EXACT);

        for(int i=0; i<this.result.size(); i++){
            // System.out.println("Score: " + Arrays.toString(this.result.get(i)));
            String[] score = this.result.get(i);
            for(int j=0; j<score.length; j++){
                System.out.print(map.get(score[j]));
            }
            System.out.println();


        }
    }

    public void game() {
        this.result = new ArrayList<String[]>();

        //randomly choose a word from possible words
        int num = (int) (Math.random() * this.possible_words.length);
        String word = this.possible_words[num];
        for(int attempts = 1; attempts < 7; ){
            //take a guess
            System.out.print("Enter a guess: ");
            String guess = System.console().readLine();
            //check if guess in allowed words
            if(Arrays.asList(this.allowed_words).contains(guess)){
                String[] score = check.test(word, guess);
                this.result.add(score);
                attempts++;

                if (guess.equals(word)){
                    System.out.println(GREEN + "You win!" + ANSI_RESET);
                    results();
                    return;
                }
                
            }
            else{
                System.out.println("Not in allowed words");
            }
            System.out.println();
            
        }
        System.out.println("You lose!");
        System.out.println(RED + "The word was: " + word + ANSI_RESET);
        results();
    }

}

class wordle {
    
    final static String GREEN = "\u001B[42m";
    final static String YELLOW = "\u001B[43m";
    final static String RED = "\u001B[41m";
    final static String GREY = ""; //passing nothing will make the text grey
    

    public static void main(String[] args) throws IOException
    {
        // test to check the colors
        // int[] score = check.test("hello", "world");
        // for(int i=0; i<score.length; i++){
        //     System.out.print(score[i] + " ");
        // }
        // System.out.println();

        //call the game function
        play p = new play();
        p.load();
        p.game();

    }
}