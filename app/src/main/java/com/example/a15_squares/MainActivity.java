package com.example.a15_squares;
/*
Luis Perez
February 20, 2023
 */
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.widget.GridLayout;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Arrays;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private int[] buttons = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0} ;
    //Create an array of "buttons" to display on the screen
    private GridLayout gridLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        shuffleNumbers();
        updateGridLayout();


        Button resetButton = findViewById(R.id.ResetButton);
        resetButton.setOnClickListener(view -> {
            shuffleNumbers();
            updateGridLayout();

        })
        ;}
    private void shuffleNumbers() {
        //This method iterates through all the buttons and shuffles randomly
        buttons[buttons.length - 1] = 0;
        Random random = new Random();
        for (int i = buttons.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = buttons[i];
            buttons[i] = buttons[j];
            buttons[j] = temp;
        }
    }


    private void updateGridLayout(){
        gridLayout.removeAllViews();
        for(int i = 0; i < buttons.length; i++){
            TextView textView = new TextView(this);

            textView.setText(String.valueOf(buttons[i]));//displays the "buttons", im not sure why it kept displaying the 0
            textView.setGravity(Gravity.CENTER);//this Code is to adjust the way the text on the buttons are displayed
            textView.setTextSize(35);
            textView.setPadding(15,15,15,15);
            textView.setBackgroundResource(R.drawable.ic_launcher_background);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView clickedView = (TextView) view;
                    int emptyIndex = getEmptyIndex();
                    int clickedIndex = gridLayout.indexOfChild(clickedView);
                    if (canMove(clickedIndex, emptyIndex)) {//this on click method allows object to move when index that is clicked is empty
                        swapCells(clickedIndex, emptyIndex);
                        updateGridLayout();
                        if (isSolved()) {
                            gridLayout.setBackgroundColor(Color.BLUE);// when the puzzle is solved background changes to blue.
                        }
                    }
                }
            });
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();//parameters for GridLayout
            params.columnSpec = GridLayout.spec(i % 4, 1f);
            params.rowSpec = GridLayout.spec(i / 4, 1f);
            gridLayout.addView(textView, params);
        }
    }
    private boolean canMove(int clickedIndex, int emptyIndex) {
        //This method determines if object is allowed to move to spot
        //this also returns only index's that are existent....
        int rowDiff = Math.abs(clickedIndex / 4 - emptyIndex / 4);
        int colDiff = Math.abs(clickedIndex % 4 - emptyIndex % 4);
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
    }


    private boolean isSolved() {
        for (int i = 0; i < buttons.length - 1; i++) {
            if (buttons[i] != i + 1) {
                return false;
            }
        }
        return true;
    }

    private int getEmptyIndex() {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    private void swapCells(int clickedIndex, int emptyIndex) {
        //temp is used as a place holder for the empty cell
        int temp = buttons[clickedIndex];
        buttons[clickedIndex] = buttons[emptyIndex];
        buttons[emptyIndex] = temp;
    }



}