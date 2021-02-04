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
        
    }
    
}
