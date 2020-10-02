package com.focusedpoint.weighttracker;

import com.focusedpoint.weighttracker.DataStructures.hashtable.HashTableFactory;
import com.focusedpoint.weighttracker.DataStructures.hashtable.HashTableSCFactory;
import com.focusedpoint.weighttracker.DataStructures.list.ArrayList;
import com.focusedpoint.weighttracker.DataStructures.map.Map;
import com.focusedpoint.weighttracker.Storage.Date;
import com.focusedpoint.weighttracker.Storage.FoodEntry;
import com.focusedpoint.weighttracker.Storage.WeightEntry;

import java.text.SimpleDateFormat;

public class User {
	
	//private fields
	private String username, password;				
	private int age;
	private ArrayList<WeightEntry> weights;		//Weights will be measured in pounds
	public Map<String, FoodEntry> ht;			//HashTable that contains a key which is the name of the food and a value that contains an object holding all the information related to the food.
	private int heightFT, heightIN;			//Height will be measured in feet & inches
	public enum sex {
		MALE, FEMALE
	}
	sex uSex;
	
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
	
	public String toString() {
		return "(" + username + ", " + password + ", " + uSex + ", " + age + " yrs, " 
				+ currentWeight() + " lb, " + heightFT + "'" + heightIN + "\")";
	}
}
