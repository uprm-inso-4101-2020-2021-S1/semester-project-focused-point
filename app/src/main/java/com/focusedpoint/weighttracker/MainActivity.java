package com.focusedpoint.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Used to store data from the graph.
    LineGraphSeries<DataPoint> SeriesGraph;
    EditText NumberField;
    Button SubmitButton,GraphButton;
    //List that contains all the weights of a person.
    // (Planing to make each index of the list represent a day of the month.)
    ArrayList<Double> Weights = new ArrayList<Double>(31);
    GraphView Graph;
    ArrayList<Button> Buttons;

    double x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NumberField = findViewById(R.id.NumberField);
        Graph = (GraphView) findViewById(R.id.graph);
        enableSubmitButton();
        enableGraphButton();
    }

    private void enableGraphButton() {
        GraphButton = (Button) findViewById(R.id.GraphButton);
        //sets the behavior of the graph button
        GraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //creates a graph object that stores the data of graph view.
            SeriesGraph = new LineGraphSeries<DataPoint>();
            for(int i=0;i<Weights.size();i++){
            //adds a new point to the graph object
            SeriesGraph.appendData(new DataPoint(i+1,Weights.get(i)),true,Weights.size());
            }
            //adds the data stored in SeriesGraph to the GraphView so that it can draw the graph.
            Graph.addSeries(SeriesGraph);
            }
        });
    }

    private void enableSubmitButton() {
        SubmitButton = (Button) findViewById(R.id.SubmitButton);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInputToList();
            }});
    }

    //This function adds elements to the list titled weights.
    // The elements it adds is the number written inside the text box named "NumberField".
    public void addInputToList(){
        if(NumberField.getText()==null)
            return;
        else {
            //gets the text inside NumberField and removes all the whitespace inside it
            String NumericalInput = NumberField.getText().toString().trim();
            //checks if the text can be converted to a Double.
            // If so, it adds it to weights and sets the text inside NumberField to empty.
            try {
                Weights.add(Double.parseDouble(NumericalInput));
                NumberField.setText("");
                String debug = ArrayListToString(Weights);
                Log.println(Log.INFO,"debug","The weights are:"+debug);
            }
            //if an error occurs, then the text inside NumberField is not a valid weight
            catch (NumberFormatException e){
            NumberField.setText("");
            Log.println(Log.INFO,"debug","Not a valid weight");
            }
        }
    }
    //Turns an arrayList of doubles into a single string
    public String ArrayListToString(ArrayList<Double> list){
        String Temp ="";
        for(Double s:list)
            Temp=Temp+","+s;//separates each element of the list by a comma.
        return Temp;
    }

//    private void enableNumberField() {
//        NumberField = findViewById(R.id.NumberField);
//        NumberField.setOnKeyListener(new View.OnKeyListener(){
//
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if(event.getAction()==KeyEvent.ACTION_DOWN && isNumeric(keyCode)){
//                    Input = Input+=keyCode;
//                    NumberField.setText(Input);
//                    return true;
//                }
//                return false;
//            }});
//    }

//    //function used to find out if the given keycode is numeric
//    public boolean isNumeric(int KeyCode){
//        //If the keycode provided is the same as any number from 0-9
//        if(KeyCode==KeyEvent.KEYCODE_0|KeyCode==KeyEvent.KEYCODE_1
//                |KeyCode==KeyEvent.KEYCODE_2|KeyCode==KeyEvent.KEYCODE_3
//                |KeyCode==KeyEvent.KEYCODE_4|KeyCode==KeyEvent.KEYCODE_5
//                |KeyCode==KeyEvent.KEYCODE_6|KeyCode==KeyEvent.KEYCODE_7
//                |KeyCode==KeyEvent.KEYCODE_8|KeyCode==KeyEvent.KEYCODE_9){
//            return true;
//        }
//        else{
//            //if not, then it is not a number
//            return false;}
//    }


}