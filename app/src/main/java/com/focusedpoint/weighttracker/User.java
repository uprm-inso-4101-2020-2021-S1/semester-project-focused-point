package com.focusedpoint.weighttracker;

import android.content.Context;
import android.util.Log;

import com.focusedpoint.weighttracker.DataStructures.hashtable.HashTableFactory;
import com.focusedpoint.weighttracker.DataStructures.hashtable.HashTableSCFactory;
import com.focusedpoint.weighttracker.DataStructures.list.ArrayList;
import com.focusedpoint.weighttracker.DataStructures.map.Map;
import com.focusedpoint.weighttracker.Storage.Date;
import com.focusedpoint.weighttracker.Storage.FoodEntry;
import com.focusedpoint.weighttracker.Storage.WeightEntry;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;

public class User {
	
	//private fields
	private String username, password;				
	private int age;
	private ArrayList<WeightEntry> weights;		//Weights will be measured in pounds
	public static Map<String, FoodEntry> ht;			//HashTable that contains a key which is the name of the food and a value that contains an object holding all the information related to the food.
	private int heightFT, heightIN;			//Height will be measured in feet & inches
	public enum sex {
		MALE, FEMALE
	}
	sex uSex;
	Integer VisitorCode;
	
	public User(String un, String pw, sex uSex, int age, int weight, int heightFT, int heightIN) {
		if (weight < 1) {
			throw new IllegalArgumentException("The weight must be at least 1");
		}
		if (age < 3) {
			throw new IllegalArgumentException("Users age must be at least 3");
		}
		this.username = un;	
		this.password = pw;	
		this.uSex = uSex;
		this.age = age;
		this.weights = new ArrayList<WeightEntry>(10);
		this.weights.add(new WeightEntry(weight,Date.currentDate()));
		HashTableFactory<String, FoodEntry> factory = new HashTableSCFactory<String, FoodEntry>();
		this.ht = factory.getInstance(10);
		this.heightFT = heightFT;
		this.heightIN = heightIN;
		Random R = new Random();
		this.setVisitorCode(1000+R.nextInt(1000));
	}
	
	public int currentWeight() {
		return this.weights.last().getWeight();
	}


	
	public int weightAt(int n) { return this.weights.get(n-1).getWeight();}
	
	public void setWeight(int w) {
		if (w < 1) {
			throw new IllegalArgumentException("Weight must be at least 1");
		}
		this.weights.add(new WeightEntry(w, Date.currentDate()));
	}
	public void setWeight(int n,String date) {
		if (n < 1) {
			throw new IllegalArgumentException("Weight must be at least 1");
		}
		this.weights.add(new WeightEntry(n, date));
	}
	
	//Calculates BMI.
	public double BMI() {
		return (currentWeight()*703)/(Math.pow(heightInInches(), 2));
	}
	
	//BMI classification.
	public String classifyBMI() {
		if (BMI() <= 18.5) {
			return "UNDERWEIGHT";
		} else if (BMI() <= 24.9) {
			return "NORMAL";
		} else if (BMI() <= 29.9) {
			return "OVERWEIGHT";
		} else if (BMI() <= 35) {
			return "OBESE CLASS I";
		} else if (BMI() <= 40) {
			return "OBESE CLASS II";
		} else {
			return "OBESE CLASS III";
		}
	}
	
	//Body Fat Percentage (BMI Method)
	public double BFP () {
		if (this.age > 19) {
			if (this.uSex.equals(sex.MALE)) {
				return ((1.20*BMI())+(0.23*this.age))-16.2;
			} else {
				return ((1.20*BMI())+(0.23*this.age))-5.2;
			}
		}
		if (this.uSex.equals(sex.MALE)) {
			return ((1.51*BMI())-(0.7*this.age))-2.2;
		} else {
		return (1.51*BMI())-(0.7*(this.age))-1.4;
		}
	}
	
	//Transfers the heightFT to inches and adds the remaining inches from heightIN. 
	public int heightInInches() {
		return (heightFT*12)+heightIN;
	}

	//adds the meal to ht but it does not store when it was eaten.
	public void addMeal(String food, double cal) {
		if(!ht.containsKey(food))
			this.ht.put(food, new FoodEntry(cal));
	}
	//adds the meal to ht but it does not store when it was eaten.
	public void addMeal(String food, double cal,ArrayList<String> TimesEaten) {
		if(!ht.containsKey(food))
			this.ht.put(food, new FoodEntry(cal,TimesEaten));
	}
	
	//getters & setters
	public String getUsername() { 
		return username;
	}
	
	public void setUsername(String un) {
		this.username = un;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String pw) {
		this.password = pw;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int a) {
		this.age = a; 
	}
	
	//getters & setters for height
	public int getHeightFT() {
		return heightFT;
	}
	
	public void setHeightFT(int ft) {
		this.heightFT = ft;
	}
	
	public int getHeightIN() {
		 return heightIN;
	}
	
	public void setHeightIN(int in) {
		this.heightIN = in;
	}

	public sex getSex(){
		return  uSex;
	}

	public void setSex(sex sex){
		  uSex=sex;
	}

	public ArrayList<WeightEntry> getWeights(){
		return weights;
	}

	public void setWeights(ArrayList<WeightEntry> list){
		this.weights=list;
	}

	public String toString() throws NullPointerException{
		return "(" + username + ", " + password + ", " + uSex + ", " + age + " yrs, "
				+ currentWeight() + " lb, " + heightFT + "'" + heightIN + "\")"+"\nWeights with Dates:\n"+getWeights().toString()+"Foods with Calories:\n"+ht.toString();
	}
	public String UserStatustoString() throws NullPointerException{
		return "Username: "+username+"\nSex: " + uSex + "\nAge: " + age + " yrs\nCurrent Weight: "	+ currentWeight() + " lb\n Height: " + heightFT + "'" + heightIN +"\nWeights with Dates:\n"+getWeights().toString()+"Foods with Calories:\n"+ht.toString();
	}
	public void clear(){
		username="";
		password = "";
		age=0;
		this.weights = new ArrayList<WeightEntry>(10);
		HashTableFactory<String, FoodEntry> factory = new HashTableSCFactory<String, FoodEntry>();
		this.ht = factory.getInstance(10);
		this.heightFT = 0;
		this.heightIN = 0;
	}

	public Integer getVisitorCode() {
		return VisitorCode;
	}

	public void setVisitorCode(Integer visitorCode) {
		VisitorCode = visitorCode;
	}
}
