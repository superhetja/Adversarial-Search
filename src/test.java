import java.util.ArrayList;

import javax.swing.Action;

public class test {

    public static void main(String  args[]){
        int height = 5;
        int width = 5;
        for (int x = 1; x <= width; x++) {
            for(int y=1; y <= 2; y++ ){
                System.out.println("White: " + x + " " + y);
                System.out.println("Black: " + x + " " + (height-y+1));
                
            }

        }
        ArrayList<Action> a = new ArrayList<Action>();
        boolean k = a.iterator().hasNext();
        System.out.println(k);
        
    }
    
}
