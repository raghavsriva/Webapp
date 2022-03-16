package com.nagarro.AdvanceJava;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;



public class App {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc=new Scanner(System.in);
        String color,size,gender;
        int choiceCode=0;
        System.out.println("******Welcome to our T-Shirt Store****** ");
        System.out.print("Enter Colour Of Your Preferred T-shirt : ");
        color=sc.nextLine().toUpperCase();
        
        System.out.print("Enter Required Size  : ");
        size=sc.nextLine().toUpperCase();
        
        //Conditional statements to check if input is acceptable or not
        if(! (size.equalsIgnoreCase("S") || size.equalsIgnoreCase("M") || size.equalsIgnoreCase("L") ||size.equalsIgnoreCase("XL")))
        {
        	System.out.println("Wrong input");
        	System.exit(0);
        }
        
        System.out.print("Enter Gender/Type - Male(M) , Female(F) , Unisex(U)   : ");
        gender=sc.nextLine().toUpperCase();
        
        //Conditional statements to check if input is acceptable or not
        if(!(gender.equalsIgnoreCase("f") || gender.equalsIgnoreCase("m")||gender.equalsIgnoreCase("u")))
        {
        	System.out.println("Wrong input");
        	System.exit(0);
        }
        
        System.out.print("Enter Output Preference :\n1. Sort by Price  \n" +"2. Sort by Rating \n3. Sort by Price and Rating. \nEnter Preference Choice Code : ");
        
        try 
        {
        	choiceCode=sc.nextInt();
        }
        catch(InputMismatchException e)
        {
        	System.err.println("Exception Occured : "+e);
        	System.err.println("**Enter Integer Value**");
        	System.exit(0);
        }
        
        DataController fc=new DataController();
        fc.start();
        try {
        	 fc.searchData("src\\TshirtInformation\\Nike.csv",color,size,gender);
             fc.searchData("src\\TshirtInformation\\Puma.csv",color,size,gender);
             fc.searchData("src\\TshirtInformation\\Adidas.csv",color,size,gender);
        	}
       catch(FileNotFoundException e)
        {
    	   System.err.println("File Does Not Exist");
    	   System.exit(0);
        }
        fc.updateView(choiceCode);
        sc.close();
    }
}

class DataController extends Thread {
	public void run()
	{
	}
    ArrayList<Model> list=new ArrayList<Model>();
    ArrayList<String> arr;
    DataView view=new DataView();

    public void searchData(String filename, String color, String size, String gender) throws FileNotFoundException
    {
        Scanner sc = new Scanner(new File(filename));
        while(sc.hasNext()) {
            String line = sc.nextLine().toUpperCase().toString();
            if (!line.isEmpty()) {
                StringTokenizer token = new StringTokenizer(line, "|");
                arr = new ArrayList<String>(line.length());
                while (token.hasMoreTokens()) {
                    arr.add(token.nextToken());
                }
                
                
                if (arr.get(2).equals(color) && arr.get(4).equals(size) && arr.get(3).equals(gender)) {
                    Model model = new Model(arr.get(1),arr.get(2), arr.get(4), arr.get(3), arr.get(5), arr.get(6));
                    list.add(model);
                }
            }
        }
        sc.close();
    }

    public void updateView(int choiceCode)throws FileNotFoundException
    {
        if(choiceCode==1)//Sort By Price
        {
           Collections.sort(list, new Comparator<Model>() {
              public int compare(Model o1, Model o2) {
                  return o2.getPrice().compareTo(o1.getPrice()) ;
                  
               }
           });
        }
        else if(choiceCode==2)//Sort By Rating
        {
            Collections.sort(list, new Comparator<Model>() {
                public int compare(Model o1, Model o2) {
                	return o2.getRating().compareTo(o1.getRating()) ;
               }
            });
        }
        else if(choiceCode==3)//both
        {
        	 Collections.sort(list, new Comparator<Model>() {
                 public int compare(Model o1, Model o2) {
                	 return o1.getRating().compareTo(o2.getRating()) & o2.getPrice().compareTo(o1.getPrice());
                }
             });
        }
        else
        {
            System.out.println("Wrong Choice.");
            return;
        }
        view.viewTshirts(list);

    }
}

class Model {
	private String name;
    private String color;
    private String size;
    private String gender;
    private String price;
    private String rating;

    public Model(){}

    public Model(String name,String color, String size, String gender, String price, String rating) {
    	this.name=name;
        this.size = size;
        this.color = color;
        this.gender = gender;
        this.price = price;
        this.rating = rating;
    }
    
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name=name;
    }

    public String getSize()
    {
        return size;
    }
    public void setSize(String size)
    {
        this.size=size;
    }

    public String getColor()
    {
        return color;
    }
    public void setColor(String color)
    {
        this.color=color;
    }

    public String getGender()
    {
        return gender;
    }
    public void setGender(String gender)
    {
        this.gender=gender;
    }
    public String getPrice()
    {
        return price;
    }
    public void setPrice(String price)
    {
        this.price=price;
    }

    public String getRating()
    {
        return rating;
    }
    public void setRating(String rating)
    {
        this.rating=rating;
    }

}

class DataView {

    public void viewTshirts(ArrayList<Model> list)
    {
    	
    	 if(list.isEmpty())
         {
             System.out.println("Tshirt Not Available.");
             System.exit(0);
         }
        System.out.println("\n \tTSHIRT INFORMATION");
        System.out.println("--------------------------------------------------");
        System.out.println("NAME | SIZE | COLOUR | GENDER | PRICE | RATING |");
        System.out.println("--------------------------------------------------");
        for(Model f:list)
        {
            System.out.print(f.getName()+" | ");
            System.out.print(f.getSize()+" | ");
            System.out.print(f.getColor()+" | ");
            System.out.print(f.getGender()+" | ");
            System.out.print(f.getPrice()+" | ");
            System.out.println(f.getRating()+" |");
        }
       
    }
}
