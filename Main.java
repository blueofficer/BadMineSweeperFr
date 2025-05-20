import java.util.Random;
import java.util.Scanner;

class Main{

    static MineCell[][] Map;
    static Random rand = new Random(69420);
    static boolean FirstMove = false;

    public static void main(String[] args){

        Setup();

        while (true) {
            PrintMap();
            ChooseCell();
            CheckWin();
        }
        
    }

    public static void ChooseCell() {
    Scanner Userinput = new Scanner(System.in);
    char XCord;
    char YCord;

    while (true) {
        System.out.print("Please Enter the X cord: ");
        XCord = (char) (Userinput.next().toCharArray()[0] - 33);

        if (XCord < 0 || XCord >= Map.length) {
            System.out.println("X Cord IS Out of bounds");
            continue;
        }

        System.out.print("Please Enter the Y cord: ");
        YCord = (char) (Userinput.next().toCharArray()[0] - 33);

        if (YCord < 0 || YCord >= Map.length) {
            System.out.println("Y Cord IS Out of bounds");
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
        
        Scanner Userinput = new Scanner(System.in);

        while (true) {
            System.out.print("Please enter your number of cols here: ");

            try {
                Size = Integer.parseInt(Userinput.next());

                //end of chars that are visable
                if(Size > 93){
                    System.out.println("To Big Please try again");
                }else if(Size < 0){
                    System.out.println("To Small Please try again");
                }else{
                    break;
                }

            } catch (Exception e) {
                System.out.println("NaN");
            }
        }

        Map = new MineCell[Size][Size];

        for (int Row = 0; Row < Map.length; Row++) {
            for (int Col = 0; Col < Map[Row].length; Col++) {
                Map[Row][Col] = new MineCell(rand.nextBoolean());
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
                    System.out.print("|D");
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

    public static void CheckWin() {
        for (int Row = 0; Row < Map.length; Row++) {
            for (int Col = 0; Col < Map[Row].length; Col++) {
                if (!Map[Row][Col].IsFound && !Map[Row][Col].IsActive) {
                    return;
                }
            }
        }

        System.out.println();
        System.out.println("You Win :D");
        System.exit(0);
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
