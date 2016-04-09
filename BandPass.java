public class HelloWorld{

     public static void main(String []args){
         float[] p1 = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f}; //input array
         float[] out = new float[4];
         float cutoff=0.5f;
         float Q=0.99f;
         float lowfreq=100f;
         float highfreq=3000f;
        for(int i=0; i<5;i++)
        {
            out[i] = BandPassFilter(p1[i], cutoff, Q, lowfreq, highfreq);
            System.out.println("BPF is" + i + out[i]);
            
        }
        
         
        
     }
    private static float[] xh = new float[3]; 
    
    public static float BandPassFilter(float input, float cutoff, float Q, float lowfreq, float highfreq) {
        float out, K, b0, b1, b2, a1, a2, Fs;
        float pi = (float) Math.PI;
        
            K = (float) Math.tan(pi*(lowfreq+((highfreq-lowfreq)*(cutoff/2+0.5)))/48000); // hard code Fs (the sampling frequency)
            b0= K/((K*K)*Q+K+Q);
            b1= 0;
            b2= -K/((K*K)*Q+K+Q);
            a1= (2*Q*(K*K-1))/((K*K)*Q+K+Q);
            a2= (K*K)*Q-K+Q/((K*K)*Q+K+Q);
            xh[0] = input-a1*xh[1]-a2*xh[2];
            out = b0*xh[0]+b1*xh[1]+b2*xh[2];
            xh[2]=xh[1];
            xh[1]=xh[0];
        return out;
    }
     

     
     
}