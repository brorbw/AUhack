import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;
import com.illposed.osc.OSCPortOut;
import com.illposed.osc.test.OSCBundleTest;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by brorbw on 09/04/16.
 */
public class OSCTranslater {

    private OSCPortIn oscIn;
    private OSCPortOut oscOut;
    private AudioFormat af = new AudioFormat(44100,8,1,true,false); //new AudioFormat((float)44100, 8, 1, true, false);
    SourceDataLine sdl;
    private byte[] buffer;
    private float alpha, beta, delta, gamma, theta;
    private float toSumAlpha, toSumBeta, toSumtheta;
    private double time = 1;
    private float out;
    private boolean isInterrupted;
    public static void main(String ... args){
        OSCTranslater mt = new OSCTranslater(8);
        mt.init(5001);

    }
    public OSCTranslater(int buffersize){
        buffer = new byte[buffersize];
    }
    public void init(int outport){
        try{
            oscIn = new OSCPortIn(5020);
            //oscOut = new OSCPortOut(InetAddress.getLocalHost(),outport);
            sdl = AudioSystem.getSourceDataLine(af);
            sdl.open();
            sdl.start();
        } catch (Exception e){
            e.printStackTrace();
        }
        oscIn.addListener("/muse/elements/alpha_relative", new OSCListener() {
            @Override
            public void acceptMessage(Date date, OSCMessage oscMessage) {
                Object[] msg = oscMessage.getArguments();
                    /*alpha = merge((Float)msg[0],(Float)msg[1],(Float)msg[2],(Float)msg[3]);
                    float x = 25*alpha;
                    float reg = (float) 0.45281f*(x*x)+4.1387f*x+131.04f;
                    double angle = time / ((float) 44100 / reg) * 2.0 * Math.PI;
                    toSumAlpha = (byte) (Math.sin(angle) * 100);*/

            }
        });
        oscIn.addListener("/muse/elements/beta_relative", new OSCListener() {
            @Override
            public void acceptMessage(Date date, OSCMessage oscMessage) {
                Object[] msg = oscMessage.getArguments();
                    beta = merge((Float)msg[0],(Float)msg[1],(Float)msg[2],(Float)msg[3]);
                    //float x = 25*beta;
                    //float reg = (float) 1.8113f*(x*x)+16.553f*x+524.19f;
                    double angle = time / ((float) 44100 / (beta*3000)) * 2.0 * Math.PI;
                    toSumBeta = (float) (Math.sin(angle) * 100);

            }
        });
        oscIn.addListener("/muse/elements/delta_relative", new OSCListener() {
            @Override
            public void acceptMessage(Date date, OSCMessage oscMessage) {
                Object[] msg = oscMessage.getArguments();
                    delta = merge((Float)msg[0],(Float)msg[1],(Float)msg[2],(Float)msg[3]);
            }
        });
        oscIn.addListener("/muse/elements/gamma_relative", new OSCListener() {
            @Override
            public void acceptMessage(Date date, OSCMessage oscMessage) {
                Object[] msg = oscMessage.getArguments();
                    gamma = merge((Float)msg[0],(Float)msg[1],(Float)msg[2],(Float)msg[3]);
            }
        });
        oscIn.addListener("/muse/elements/theta_relative", new OSCListener() {
            @Override
            public void acceptMessage(Date date, OSCMessage oscMessage) {
                Object[] msg = oscMessage.getArguments();
                /*theta = merge((Float) msg[0], (Float) msg[1], (Float) msg[2], (Float) msg[3]);
                float x = 25*theta;
                float reg = (float) 0.11318f*(x*x)+1.0352f*x+32.759f;
                double angle = time / ((float) 44100 / reg) * 2.0 * Math.PI;
                toSumtheta = (byte) (Math.sin(angle) * 100);*/
            }
        });
        oscIn.startListening();
        Thread output = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!isInterrupted) {
                    buffer[0] = (byte) toSumBeta; //(toSumAlpha/3+toSumBeta/3+toSumtheta/3);
                    //System.out.println(buffer[0]);
                    sdl.write(buffer, 0,1);
                    time++;
                }
            }
        });
        output.start();
    }

    public float merge(float p1, float p2, float p3, float p4) {
        if(!Float.isNaN(p1)){
            p1 = 0F;
        } else if(!Float.isNaN(p2)){
            p2 = 0F;
        } else if (!Float.isNaN(p3)){
            p3 = 0F;
        } else if (!Float.isNaN(p4)){
            p4 = 0F;
        }
        float out;
        //out = (p1+p2+p3+p4)/4;
        out = (p2+p3)/2;
        return out;
    }

    /*
    public void doOnSend(float number, String waveName){
        if(null == oscOut){
            System.out.println("No Connection");
        }
        Object[] args = new Object[3];
        args[0] = "MusicInMyHead";
        args[1] = ("number");
        args[2] = new Float(number);
        OSCMessage msg = new OSCMessage(waveName, args);
        oscOut.send();
    }
    */
}
