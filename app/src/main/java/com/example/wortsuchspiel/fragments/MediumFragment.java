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
import android.widget.Toast;

import com.example.wortsuchspiel.MainActivity;
import com.example.wortsuchspiel.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MediumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MediumFragment extends Fragment {

    Button[] buttonList = new Button[25];
    String selectedButtonColor = "#6b9ac8";
    String selectedTextColor = "#042e58";
    String backgroundColor = "#dae9f8";
    String buttonColor = "#bad4ef";
    String playBackgroundColor = "#593e8d";
    String playTextColor = backgroundColor;
    int textSize = 20;
    String textColor  = "";

    ArrayList<String> wordList = new ArrayList<>();
    // String[] wordList = {"NAME", "MUND", "HALLO", "ICHI"};
    Button submitButtonMed;
    TextView initialTextMed, scoreBoardMed;
    int numGames = 1;
    int numPoints = 0;
    ArrayList<Button> selected = new ArrayList<>();
    int[] buttonIDs = {R.id.button17, R.id.button18, R.id.button19, R.id.button20, R.id.button21, R.id.button22, R.id.button23, R.id.button24, R.id.button25, R.id.button26, R.id.button27, R.id.button28, R.id.button29, R.id.button30, R.id.button31, R.id.button32, R.id.button33, R.id.button34, R.id.button35, R.id.button36, R.id.button37, R.id.button38, R.id.button39, R.id.button40, R.id.button41};
    ArrayList<String> foundWords = new ArrayList<>();
    private Context context;
    private MainActivity mActivity;

    int lettersUpperBound;
    int numWords;
    boolean shiftWords = true;

    public MediumFragment() {
        // Required empty public constructor
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);

        // initialize the elements
        initialTextMed = view.findViewById(R.id.initialTextmed);
        scoreBoardMed = view.findViewById(R.id.scoreBoardmed);
        submitButtonMed = view.findViewById(R.id.submitButtonmed);

        // the user needs to find 4 words
        numWords = 4;

        // the max number of letters is 13
        lettersUpperBound = 22;

        // the words do switch around
        shiftWords = false;

        int wordLengthCount = chooseWords();
        while (wordLengthCount > lettersUpperBound)
            wordLengthCount = chooseWords();

        completeReset(view);
        onClickSubmitButton();

    }

    public static MediumFragment newInstance() {
        MediumFragment fragment = new MediumFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medium, container, false);
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



    public void updateScoreBoard() {
        String scoreString = "Points: " + numPoints;
        scoreBoardMed.setText(scoreString);
    }

    public void updateColors() {

        String[] cols1 = {"#042e58", "#6b9ac8", "#bad4ef", "#042e58", "#042e58", "#bad4ef", "#042e58", "#bad4ef"};
        String[] cols2 = {"#bad4ef", "#6b9ac8", "#042e58", "#042e58", "#bad4ef", "#042e58", "#bad4ef", "#042e58"};
        String[] cols = (numGames % 2 == 0) ? cols1 : cols2;

        buttonColor = cols[0]; // navy blue
        selectedButtonColor = cols[1]; // light blue
        textColor = cols[2];
        selectedTextColor = cols[3]; // navy blue
        submitButtonMed.setTextColor(Color.parseColor(cols[4]));
        submitButtonMed.setBackgroundColor(Color.parseColor(cols[5]));
        scoreBoardMed.setTextColor(Color.parseColor(cols[6]));
        scoreBoardMed.setBackgroundColor(Color.parseColor(cols[7]));
    }

    public void completeReset(View view) {
        updateScoreBoard();
        updateColors();

        initialTextMed.setTextSize(26);
        initialTextMed.setText("Words to Find: \n");
        for (int k = 0; k < wordList.size(); k++) {
            initialTextMed.append("\n" + wordList.get(k));
        }
        initialTextMed.append("\n");

        foundWords.clear();

        int count = 0;
        submitButtonMed.setEnabled(true);
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
                    button.setBackgroundColor(Color.parseColor(selectedButtonColor));
                    button.setTextColor(Color.parseColor(selectedTextColor));
                    selected.add(button);
                }
            });
            buttonList[count] = button;
            count++;
        }

        buttonList = assignButtonLetters();
        // assignLetters();

    }

    public void playAgain() {
        // switch to the play again fragment
        getParentFragmentManager().beginTransaction().replace(R.id.container, new PlayAgainFragment()).commit();
    }

    public void onClickSubmitButton() {
        submitButtonMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedString = selectedToString();
                if (selected.size() == 0) Toast.makeText(view.getContext(), "You've selected nothing!" , Toast.LENGTH_SHORT).show();
                else if (arrayContains(selectedString) && !listContains(selectedString)) {
                    Toast.makeText(view.getContext(), "Congrats, you found " + selectedString + "! 20 points!", Toast.LENGTH_SHORT).show();
                    foundWords.add(selectedString);
                    congratsMessage(selectedString, false);
                } else if (listContains(selectedString)) Toast.makeText(view.getContext(), "You've already found the word " + selectedString + "!", Toast.LENGTH_SHORT).show();
                else Toast.makeText(view.getContext(), "Sorry, " + selectedString + " isn't correct.", Toast.LENGTH_SHORT).show();
                selected.clear();
                // resets the button colors
                buttonList = resetButtons();

                if (foundWords.size() == wordList.size()) congratsMessage(selectedString, true);
            }
        });
    }

    public Button[] resetButtons() {
        for (Button b : buttonList) {
            b.setBackgroundColor(Color.parseColor(buttonColor));
            b.setTextColor(Color.parseColor(textColor));
        }

        return buttonList;
    }

    public void updateScoreboard() {
        String scoreString = "Points: " + numPoints;
        scoreBoardMed.setText(scoreString);
    }

    public void congratsMessage(String selectedString, boolean complete) {
        numPoints += 20;
        updateScoreboard();
        if (!complete) {
            // set text to the words that weren't found yet
            String notFound = "";
            for (String s : wordList) {
                if (!listContains(s)) notFound += "\n" + s;
            }
            // initialText.setText("\nCongrats, you found: " + selectedString + ". 20 points gained!");
            initialTextMed.setText("Words to Find: \n" + notFound);
            if (shiftWords) buttonList = assignButtonLetters();

        } else {
            initialTextMed.setTextSize(26);
            numGames += 1;

            playAgain();
        }
    }

    public Button[] assignButtonLetters() {
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

        buttonList = resetButtons();

        return buttonList;
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


    public String join(String[] arr, String joinCharacter) {
        String str = "";
        for (int i = 0; i < arr.length; i++) {
            str = str + arr[i] + joinCharacter;
        }

        return str;
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
}