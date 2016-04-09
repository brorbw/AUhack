public class HelloWorld{

     public static void main(String []args){
         float[] p1 = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f}; //input array
         float[] out = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f,};
        for(int n=0; n<5; n++){
            for(int k=0;k<5;k++){
                out[n*5+k]=p1[n];
                
            }
        
            
        }