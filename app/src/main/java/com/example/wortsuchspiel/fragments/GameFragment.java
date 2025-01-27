package com.example.wortsuchspiel.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wortsuchspiel.MainActivity;
import com.example.wortsuchspiel.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameFragment extends Fragment {

    Button[] buttonListMedOrHard = new Button[25];
    Button[] buttonListEasy = new Button[16];
    Button[] buttonList;
    String selectedButtonColor = "#6b9ac8";
    String selectedTextColor = "#042e58";
    String buttonColor = "#bad4ef";
    int textSize = 20;
    String textColor  = "";

    String level;

    int currPosInList = 0;

    Button backButton, nextButton;

    ArrayList<String> wordList = new ArrayList<>();
    // String[] wordList = {"NAME", "MUND", "HALLO", "ICHI"};
    TextView initialText, scoreBoard, showLetters;
    int numGames = 1;
    int numPoints = 0;
    ArrayList<Button> selected = new ArrayList<>();
    ArrayList<String> foundWords = new ArrayList<>();
    private Context context;
    private MainActivity mActivity;

    int lettersUpperBound, numWords;
    boolean shiftWords = true;

    int[] buttonIDsEasy = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15, R.id.button16};
    int[] buttonIDsMedAndHard = {R.id.button1_1, R.id.button1_2, R.id.button1_3, R.id.button1_4, R.id.button1_5, R.id.button1_6, R.id.button1_7, R.id.button1_8, R.id.button1_9, R.id.button1_10, R.id.button1_11, R.id.button1_12, R.id.button1_13, R.id.button1_14, R.id.button1_15, R.id.button1_16, R.id.button1_17, R.id.button1_18, R.id.button1_19, R.id.button1_20, R.id.button1_21, R.id.button1_22, R.id.button1_23, R.id.button1_24, R.id.button1_25};

    int[] buttonIDs;

    public GameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);

        // initialize the elements
        initialText = view.findViewById(R.id.initialText);
        scoreBoard = view.findViewById(R.id.scoreBoard);
        showLetters = view.findViewById(R.id.showLetters);
        backButton = view.findViewById(R.id.backButton);
        nextButton = view.findViewById(R.id.nextButton);

        // tells the level
        level = ((MainActivity) getActivity()).getLevel();
        if (level.equals("Easy")) easyLevel();
        else if (level.equals("Medium")) mediumLevel();
        else hardLevel();


        int wordLengthCount = chooseWords();
        while (wordLengthCount > lettersUpperBound) {
            wordLengthCount = chooseWords();
        }

        completeReset(view);
        wordButtons();
    }

    public void completeReset(View view) {
        updateScoreBoard();
        updateColors();

        initialText.setTextSize(26);
        initialText.setText(wordList.get(currPosInList));

        foundWords.clear();

        int count = 0;
        // submitButton.setEnabled(true);
        for (int buttonID : buttonIDs) {
            Button button = view.findViewById(buttonID);
            button.setEnabled(true);
            button.setTextSize(textSize);
            button.setTextColor(mActivity.getColor(R.color.textcolor));
            button.setOnClickListener(view1 -> {
                if (selected.contains(button)) {
                    selected.remove(button);
                    button.setBackgroundColor(Color.parseColor(buttonColor));
                    button.setTextColor(Color.parseColor(textColor));
                } else {
                    if (selected.size() < 4) {
                        selected.add(button);
                        button.setBackgroundColor(Color.parseColor(selectedButtonColor));
                        button.setTextColor(Color.parseColor(selectedTextColor));
                    }
                }
                showLetters.setText(fillInBlanks(selectedToString()));

                if (arrayContains(selectedToString()) && !listContains(selectedToString())) {
                    confirmOrDenyWords();
                }
            });
            buttonList[count] = button;
            count++;
        }

        assignButtonLetters();
    }

    public String fillInBlanks(String s) {
        String returnString = s;
        for (int i = 0; i < 4 - s.length(); i++) {
            returnString += "_" + " ";
        }
        return returnString;
    }

    public int chooseWords() {
        String bigString = "SURF SIEG TOR TOUR TEAM TORE FELD RENN GOLF LAUF ZIEL PUCK JUDO BOXE RUDY SKAT KAMP MANN WURF ZWEI HOKE REN ZELT BOGE KORB TAUE KLET HAND BERT BOCK HOCS KICK LUEF TANZ BALL SKI RUGY HOPP SPIR TAU KANU FANG HOCH BAHN";
        String[] bigWordList = bigString.split(" ");

        int wordLengthCount = 0;
        int randomIndex = new Random().nextInt(bigWordList.length);

        for (int i = 0; i < numWords; i++) {
            String currWord = bigWordList[randomIndex];
            while (arrayContains(currWord)) {
                randomIndex = new Random().nextInt(bigWordList.length);
                currWord = bigWordList[randomIndex];
            }

            wordLengthCount += currWord.length();

            wordList.add(currWord);
        }

        return wordLengthCount;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity){
            mActivity = (MainActivity) context;
            this.context = context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void wordButtons() {
        final Button button = nextButton;
        final Button button2 = backButton;

        button.setOnClickListener(view1 -> {
            if (currPosInList > wordList.size() - 1) currPosInList = wordList.size() - 1;
            else if (currPosInList < wordList.size() - 1) currPosInList++;
            initialText.setText(wordList.get(currPosInList));

            if (currPosInList >= wordList.size() - 1) {
                button2.setEnabled(true);
                button.setEnabled(false);
                // button.setBackgroundColor(Color.parseColor());
            } else {
                button.setEnabled(true);
            }
        });

        button2.setOnClickListener(view1 -> {
            if (currPosInList >= 1) currPosInList--;
            initialText.setText(wordList.get(currPosInList));

            if (currPosInList == 0) {
                button.setEnabled(true);
                button2.setEnabled(false);
            } else {
                button2.setEnabled(true);
            }
        });

        if (wordList.size() == 1) {
            nextButton.setEnabled(false);
            backButton.setEnabled(false);
        }
    }

    public void updateScoreBoard() {
        String scoreString = "Points: " + numPoints;
        scoreBoard.setText(scoreString);
    }

    public void updateColors() {
        String[] cols1 = {"#042e58", "#6b9ac8", "#bad4ef", "#042e58", "#042e58", "#bad4ef", "#042e58", "#bad4ef"};
        String[] cols2 = {"#bad4ef", "#6b9ac8", "#042e58", "#042e58", "#bad4ef", "#042e58", "#bad4ef", "#042e58"};
        String[] cols = (numGames % 2 == 0) ? cols1 : cols2;

        buttonColor = cols[0]; // navy blue
        selectedButtonColor = cols[1]; // light blue
        textColor = cols[2];
        selectedTextColor = cols[3]; // navy blue
        scoreBoard.setTextColor(Color.parseColor(cols[6]));
        scoreBoard.setBackgroundColor(Color.parseColor(cols[7]));
    }

    public void playAgain() {
        // switch to the play again fragment
        getParentFragmentManager().beginTransaction().replace(R.id.container, new PlayAgainFragment()).commit();
    }

    public void confirmOrDenyWords() {
        String selectedString = selectedToString();
        showLetters.setText("Super gemacht!");
        if (arrayContains(selectedString) && !listContains(selectedString)) {
            foundWords.add(selectedString);
            if (wordList.get(currPosInList).equals(selectedString)) {
                if (currPosInList == 0 && wordList.size() > 1) {
                    currPosInList++;
                    initialText.setText(wordList.get(currPosInList));
                    currPosInList--;
                } else if (currPosInList == wordList.size() - 1 && wordList.size() > 1) {
                    currPosInList--;
                    initialText.setText(wordList.get(currPosInList));
                } else {
                    currPosInList = 0;
                    initialText.setText(wordList.get(currPosInList));
                }
            }

            congratsMessage(false);
        }
        selected.clear();
        resetButtons();

        if (foundWords.size() == numWords) congratsMessage(true);
    }

    public void resetButtons() {
        for (Button b : buttonList) {
            b.setBackgroundColor(Color.parseColor(buttonColor));
            b.setTextColor(Color.parseColor(textColor));
        }
    }

    public void updateScoreboard() {
        String scoreString = "Points: " + numPoints;
        scoreBoard.setText(scoreString);
    }

    public void congratsMessage(boolean complete) {
        numPoints += 20;
        updateScoreboard();
        if (!complete) {
            if (shiftWords) assignButtonLetters();
        } else {
            initialText.setTextSize(26);
            numGames += 1;

            playAgain();
        }
    }
    public void assignButtonLetters() {
        ArrayList<String> letterList = new ArrayList<String>();
        letterList.add(wordList.get(0));
        for (int k = 1; k < wordList.size(); k++) {
            if (!letterList.contains(wordList.get(k))) {
                letterList.add(wordList.get(k));
            }
        }

        String wordString = joinArrayList(letterList, "");

        int currLength = wordString.length();

        for (int i = 0; i < buttonList.length - currLength; i++) {
            wordString += randomLetter();
        }

        wordString = shuffler(wordString);

        for (int j = 0; j < wordString.length(); j++) {
            buttonList[j].setText(wordString.substring(j, j + 1));
        }


        resetButtons();
    }

    public String shuffler(String string) {
        List<Character> characters = new ArrayList<>();
        for (char c : string.toCharArray())
            characters.add(c);
        Collections.shuffle(characters);

        StringBuilder shuffledString = new StringBuilder();
        for (char c : characters)
            shuffledString.append(c);

        return shuffledString.toString();
    }

    public String joinArrayList(ArrayList<String> arrList, String joinCharacter) {
        String str = "";
        for (int i = 0; i < arrList.size(); i++) {
            str = str + arrList.get(i) + joinCharacter;
        }
        return str;
    }

    public char randomLetter() {
        int firstUppercaseIndex = (int) 'A'; // for uppercase

        Random r = new Random();
        int letterIndex = r.nextInt(26); // random number between 0 and 26
        return (char) (firstUppercaseIndex + letterIndex);
    }

    public String selectedToString() {
        String str = "";
        for (Button b : selected) {
            str += b.getText();
        }
        return str;
    }

    public boolean arrayContains(String word) {
        for (String w : wordList)
            if (word.equals(w)) return true;
        return false;
    }

    public boolean listContains(String selectedString) {
        for (String w : foundWords) {
            if (selectedString.equals(w)) return true;
        }
        return false;
    }

    public void easyLevel() {
        buttonList = buttonListEasy;
        buttonIDs = buttonIDsEasy;
        numWords = 2;
        lettersUpperBound = 14;
    }

    public void mediumLevel() {
        buttonList = buttonListMedOrHard;
        buttonIDs = buttonIDsMedAndHard;
        numWords = 3;
        lettersUpperBound = 22;
    }

    public void hardLevel() {
        buttonList = buttonListMedOrHard;
        buttonIDs = buttonIDsMedAndHard;
        numWords = 3;
        lettersUpperBound = 22;
    }
}