public class HelloWorld{

    public static float Merge(float p1, float p2, float p3, float p4) {
        float out;
        out = p1/4+p2/4+p3/4+p4/4;
        return out;
    }


     public static void main(String []args){
         float[] p1 = {1.1f, 2.9f, 3.4f, 3.5f}; //input array
         float[] p2 = {1.9f, 2.9f, 3.4f, 3.5f};
         float[] p3 = {1.9f, 2.9f, 3.4f, 3.5f};
         float[] p4 = {1.9f, 2.9f, 3.4f, 3.5f};
         float[] out = new float[4];
        
        for(int i=0; i<4;i++)
        {
            out[i] = Merge(p1[i],p2[i],p3[i],p4[i]);
            System.out.println("average is" + out[i]);
            
        }
        
     }
}