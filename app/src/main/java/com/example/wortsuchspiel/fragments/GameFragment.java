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
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {

    TextView helloText;
    Button[] buttonList = new Button[16];
    String selectedButtonColor = "#6b9ac8";
    String selectedTextColor = "#042e58";
    String backgroundColor = "#dae9f8";
    String buttonColor = "#bad4ef";
    String playBackgroundColor = "#593e8d";
    String playTextColor = backgroundColor;
    int textSize = 25;
    String textColor  = "";
    String[] wordList = {"NAME", "MUND"};
    Button submitButton;
    TextView initialText, scoreBoard;
    int numGames = 1;
    int numPoints = 0;
    ArrayList<Button> selected = new ArrayList<>();
    int[] buttonIDs = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15, R.id.button16};
    ArrayList<String> foundWords = new ArrayList<>();
    private Context context;
    private MainActivity mActivity;

    public GameFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public int chooseWords() {
        // String bigString = "BALL TOUR TEAM TORE FELD RENN GOLF KICK LAUF ZIEL PUCK HOCH BOCK RUGY JUDO KORB BOXE RUDY SKAT KAMP MANN SPIEL WURF ZWEI SPORT HOKE LAUF TANZ SKI REN ZELT BOGE KORB KANU TAUE FANG ZIEL BAHN RUG KLET HAND BERT BOCK HOCS KICK LUEF TANZ BALL KORB BAHN SKI SPORT TORE ZIEL HÄNDE SPRUN KICK RENNE RUGY SCHW HOPP SPIR TAU SURFE KANU FANG HOCH LAUF BAHN";
        String bigString = "ABEND ABWEG ACHSEL ADELIG ANGELE ARBEIT ABRUF BÄRTE BÄNDE BILDER BINDER BLUMEN BREITE BRÜCHE BRÜDER DRUCKE FALTEN GEBÄUDE GEHEGE GEBETE GEMEDE GESETZ HÄNDER HOFFE HÜNDIN KINDER KONTEN KURVEN LÄNDER MÄNGEL MÄRZEN MAHLEN PFLANZ SÄCKEN SICHER SCHRIFT SCHLUSS SCHNITZ SONDER SÜSSE TÄGLICH TEILER WÄHLEN WENDEN WORTE ZÄHLEN ZÜHLER";
        String[] bigWordList = bigString.split(" ");

        int wordLengthCount = 0;
        int randomIndex = new Random().nextInt(bigWordList.length);

        for (int i = 0; i < 2; i++) {
            String currWord = bigWordList[randomIndex];
            while (arrayContains(currWord)) {
                randomIndex = new Random().nextInt(bigWordList.length);
                currWord = bigWordList[randomIndex];
            }

            wordLengthCount += currWord.length();

            wordList[i] = currWord;
        }

        return wordLengthCount;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        // Inflate the layout for this fragment
        return rootView;
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // helloText = view.findViewById(R.id.helloText);

        // tells the level
        String s = ((MainActivity) getActivity()).getLevel();
        int wordLengthCount = chooseWords();
        while (wordLengthCount > 16) {
            wordLengthCount = chooseWords();
        }


        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        initialText = view.findViewById(R.id.initialText);
        scoreBoard = view.findViewById(R.id.scoreBoard);
        submitButton = view.findViewById(R.id.submitButton);

        completeReset(view);
        onClickSubmitButton();
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
        submitButton.setTextColor(Color.parseColor(cols[4]));
        submitButton.setBackgroundColor(Color.parseColor(cols[5]));
        scoreBoard.setTextColor(Color.parseColor(cols[6]));
        scoreBoard.setBackgroundColor(Color.parseColor(cols[7]));
    }

    public void completeReset(View view) {
        updateScoreBoard();
        updateColors();

        initialText.setTextSize(16);
        initialText.setText("Words to Find: \n");
        for (int k = 0; k < wordList.length; k++) {
            initialText.append("\n" + wordList[k]);
        }
        initialText.append("\n");

        foundWords.clear();

        int count = 0;
        submitButton.setEnabled(true);
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
    }

    public void playAgain() {
        // switch to the play again fragment
        getParentFragmentManager().beginTransaction().replace(R.id.container, new PlayAgainFragment()).commit();
    }

    public void onClickSubmitButton() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedString = selectedToString();
                if (selected.size() == 0) Toast.makeText(view.getContext(), "You've selected nothing!" , Toast.LENGTH_SHORT).show();
                else if (arrayContains(selectedString) && !listContains(selectedString)) {
                    Toast.makeText(view.getContext(), "Congrats, you found " + selectedString + "! 20 points!", Toast.LENGTH_SHORT).show();
                    congratsMessage(selectedString, false);
                    foundWords.add(selectedString);
                } else if (listContains(selectedString)) Toast.makeText(view.getContext(), "You've already found the word " + selectedString + "!", Toast.LENGTH_SHORT).show();
                else Toast.makeText(view.getContext(), "Sorry, " + selectedString + " isn't correct.", Toast.LENGTH_SHORT).show();
                selected.clear();
                buttonList = resetButtons();

                if (foundWords.size() == wordList.length) congratsMessage(selectedString, true);
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
        scoreBoard.setText(scoreString);
    }

    public void congratsMessage(String selectedString, boolean complete) {
        numPoints += 20;
        updateScoreboard();
        if (!complete) {
            initialText.append("\nCongrats, you found: " + selectedString + ". 20 points gained!");
            buttonList = assignButtonLetters();
        } else {
            initialText.setTextSize(24);
            initialText.setText("Congrats, you found all the words!");
            numGames += 1;

            playAgain();
        }


    }

    public Button[] assignButtonLetters() {
        // String wordString = join(wordList, "");
        // create a new letter string that contains all the letters just once

        ArrayList<String> letterList = new ArrayList<String>();
        letterList.add(wordList[0]);
        for (int k = 1; k < wordList.length; k++) {
            if (!letterList.contains(wordList[k])) {
                letterList.add(wordList[k]);
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