import java.util.Random;
import java.util.Scanner;

class Main{


    //Mine cell map contains all cells
    static MineCell[][] Map;
    //random
    static Random rand = new Random(69420);
    static boolean FirstMove = true;
    static Scanner Userinput = new Scanner(System.in);
    static int Difcaulty = 0;


    //entry fo the program
    public static void main(String[] args){
        while (true) {
            Setup();
            
            while (!HasWon()) {
                PrintMap();
                ChooseCell();
            }

            PrintMap();

            PlayAgain();
        }
       
    }

    public static void PlayAgain() {
        System.out.println("Would you like to play again?");

        String Answer = Userinput.next();

        if (Answer.toLowerCase().contains("no")) {
            System.out.println("See you next time");
            System.exit(0);
        }else{
            System.out.println("Playing again");
        }
    }

    public static void ChooseCell() {
    
    char XCord;
    char YCord;

    while (true) {
        System.out.print("Please Enter the X cord: ");
        XCord = (char) (Userinput.next().toCharArray()[0] - 33);

        if (XCord < 0 || XCord >= Map.length) {
            System.out.println("X Cord IS Out of bounds plese try again");
            continue;
        }

        System.out.print("Please Enter the Y cord: ");
        YCord = (char) (Userinput.next().toCharArray()[0] - 33);

        if (YCord < 0 || YCord >= Map.length) {
            System.out.println("Y Cord IS Out of bounds please try again");
            continue;
        }

        break;
    }

    if (FirstMove) {
        // Prevent a mine on first move
        Map[YCord][XCord].IsActive = false;

        WaveCell(YCord, XCord);  // Note: row = Y, col = X
        FirstMove = false;
    } else {
        if (Map[YCord][XCord].IsActive) {
            System.out.println("'Game Over'");
            System.exit(0);
        } else {
            WaveCell(YCord, XCord);
        }
    }
}


    public static void WaveCell(int row, int col) {
    // Bounds check
    if (row < 0 || col < 0 || row >= Map.length || col >= Map.length) return;

    MineCell cell = Map[row][col];

    if (cell.IsFound || cell.IsActive) return; // Already revealed or mine

    cell.IsFound = true;

    int nearby = GetNeibours(row, col);

    if (nearby == 0) {
        // Recursively reveal neighbors
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr != 0 || dc != 0) {
                    WaveCell(row + dr, col + dc);
                }
            }
        }
    }
}


    public static void Setup() {
        System.out.println("Welcome to bad mine sweeper");

        int Size = 0;
        
        

        while (true) {
            System.out.print("Please enter the map size here (Min of 1 max of 93): ");

            try {
                Size = Integer.parseInt(Userinput.next());

                //end of chars that are visable
                if(Size > 93){
                    System.out.println("To Big Please try again");
                }else if(Size < 1){
                    System.out.println("To Small Please try again");
                }else{
                    break;
                }

            } catch (Exception e) {
                System.out.println("Errror unable to determen the number");
            }
        }

        while (true) {
            System.out.print("Please enter the difculty (Min of 0 (no mines) max of 5 (50/50)): ");

            try {
                Difcaulty = Integer.parseInt(Userinput.next());

                //end of chars that are visable
                if(Difcaulty > 5){
                    System.out.println("To Big Please try again");
                }else if(Size < 0){
                    System.out.println("To Small Please try again");
                }else{
                    break;
                }

            } catch (Exception e) {
                System.out.println("Errror unable to determen the number");
            }
        }




        Map = new MineCell[Size][Size];

        for (int Row = 0; Row < Map.length; Row++) {
            for (int Col = 0; Col < Map[Row].length; Col++) {

                if(rand.nextInt(5) < Difcaulty){
                    Map[Row][Col] = new MineCell(true);
                }else{
                    Map[Row][Col] = new MineCell(false);
                }

            }
        }
    }

    public static void PrintMapDebug() {
        //Gets it onto a new line
        System.out.println();

        //This is to relign the picker key 
        System.out.print("| ");

        for (int i = 0; i < Map.length; i++) {
            System.out.print("|" + (char) (33 + i));
        }

        //Starts a new Line and finishes the sides
        System.out.println("|");

        for (int Row = 0; Row < Map.length; Row++) {
            System.out.print("|" + (char) (33 + Row));
            for (int Col = 0; Col < Map[Row].length; Col++) {
                if(Map[Row][Col].IsActive){
                    System.out.print("|A");
                }else if (Map[Row][Col].IsFound) {
                    System.out.print("|F");
                }else{
                    System.out.print("|D");
                }
            }
            //Starts a new Line and finishes the sides
            System.out.println("|");
        }
    }

    public static void PrintMap() {
        //Gets it onto a new line
        System.out.println();

        //This is to relign the picker key 
        System.out.print("| ");

        for (int i = 0; i < Map.length; i++) {
            System.out.print("|" + (char) (33 + i));
        }

        //Starts a new Line and finishes the sides
        System.out.println("|");

        for (int Row = 0; Row < Map.length; Row++) {
            System.out.print("|" + (char) (33 + Row));
            for (int Col = 0; Col < Map[Row].length; Col++) {
                if (Map[Row][Col].IsFound) {
                    System.out.print("|" + GetNeibours(Row, Col));
                }else{
                    System.out.print("| ");
                }
            }
            //Starts a new Line and finishes the sides
            System.out.println("|");
        }
    }

    public static int GetNeibours(int Row,int Col) {
        int Nebours = 0;
        try {
            if(Map[Row - 1][Col - 1].IsActive){
                Nebours++;
            }
        } catch (Exception e) {
            //Not importent just to prevent crashing with outta bounds
        }

        try {
            if(Map[Row - 1][Col].IsActive){
                Nebours++;
            }
        } catch (Exception e) {
            //Not importent just to prevent crashing with outta bounds
        }

        try {
            if(Map[Row - 1][Col + 1].IsActive){
                Nebours++;
            }
        } catch (Exception e) {
            //Not importent just to prevent crashing with outta bounds
        }

        try {
            if(Map[Row][Col - 1].IsActive){
                Nebours++;
            }
        } catch (Exception e) {
            //Not importent just to prevent crashing with outta bounds
        }

        try {
            if(Map[Row][Col + 1].IsActive){
                Nebours++;
            }
        } catch (Exception e) {
            //Not importent just to prevent crashing with outta bounds
        }

        try {
            if(Map[Row + 1][Col + 1].IsActive){
                Nebours++;
            }
        } catch (Exception e) {
            //Not importent just to prevent crashing with outta bounds
        }

        try {
            if(Map[Row + 1][Col].IsActive){
                Nebours++;
            }
        } catch (Exception e) {
            //Not importent just to prevent crashing with outta bounds
        }

        try {
            if(Map[Row + 1][Col - 1].IsActive){
                Nebours++;
            }
        } catch (Exception e) {
            //Not importent just to prevent crashing with outta bounds
        }



        return Nebours;
    }

    public static boolean HasWon() {
        for (int Row = 0; Row < Map.length; Row++) {
            for (int Col = 0; Col < Map[Row].length; Col++) {
                if (!Map[Row][Col].IsFound && !Map[Row][Col].IsActive) {
                    return false;
                }
            }
        }

        System.out.println();
        System.out.println("You Win :D");
        return true;
    }

}

class MineCell{

    //Shows Wethier its live and choosing it will end the game
    Boolean IsActive;

    //is used to show weither it should be show on the map
    Boolean IsFound;

    MineCell(Boolean isActive){
        IsActive = isActive;
        IsFound = false;
    }

}
