import java.util.Random;
import java.util.Scanner;

class Main{

    MineCell[][] Map;

    public static void main(String[] args){
        System.out.println("Welcome to bad mine sweeper");
        System.out.print("Please enter your number of cols here: ");

        int Size = 10;

        MineCell[][] Map = new MineCell[Size][Size];


        Random rand = new Random();

        for (int Row = 0; Row < Map.length; Row++) {
            for (int Col = 0; Col < Map[Row].length; Col++) {
                Map[Row][Col] = new MineCell(rand.nextBoolean());
            }
        }

        

        


    }

    pri

}

class MineCell{

    //Shows Wethier its live ie clicking it will end the game
    Boolean IsActive;

    //is used to show weither it should be show on the map
    Boolean IsFound;

    MineCell(Boolean isActive){
        IsActive = isActive;
    }

}
