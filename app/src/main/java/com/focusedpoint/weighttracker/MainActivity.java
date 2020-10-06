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
import android.widget.Toast;

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
    ArrayList<Integer> Weights = new ArrayList<Integer>(31);
    GraphView Graph;
    ArrayList<Button> Buttons;

    double x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NumberField = findViewById(R.id.NumberField);
        Graph = (GraphView) findViewById(R.id.graph);
        Graph.setVisibility(View.INVISIBLE);
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
            SeriesGraph.appendData(new DataPoint(i+1,Weights.get(i)),false,31);
            SeriesGraph.setDrawDataPoints(true);
            }
            //adds the data stored in SeriesGraph to the GraphView so that it can draw the graph.
            //SeriesGraph needs more than 3 data points so that the graph can display correctly.
            Graph.addSeries(SeriesGraph);
            if(Weights.size()>=5){
            Graph.setVisibility(View.VISIBLE);}
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "To graph weights more than 5 are needed", Toast.LENGTH_LONG);
                toast.show();
            }
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
                Weights.add(Integer.parseInt(NumericalInput));
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
    public String ArrayListToString(ArrayList<Integer> list){
        String Temp ="";
        for(Integer s:list)
            Temp=Temp+","+s;//separates each element of the list by a comma.
        return Temp;
    }


}