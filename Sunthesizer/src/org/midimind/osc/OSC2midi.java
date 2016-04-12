package org.midimind.osc;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;
import com.illposed.osc.OSCPortOut;

import java.net.InetAddress;
import java.util.Date;


public class OSC2midi {

    private OSCPortIn oscIn;
    private OSCPortOut oscOut;
    int[] noteVector = new int[4];
    private float oldAlpha, oldBeta,oldDelta,oldGamma,oldTheta;
    public static void main(String ... args){
        OSC2midi mt = new OSC2midi();
        mt.init(5030);

    }
    public void init(int outport) {
        try {
            oscIn = new OSCPortIn(5001);
            oscOut = new OSCPortOut(InetAddress.getLocalHost(), outport);
        } catch (Exception e) {
            e.printStackTrace();
        }
        oscIn.addListener("/muse/elements/alpha_relative", new OSCListener() {
            @Override
            public void acceptMessage(Date date, OSCMessage oscMessage) {
                Object[] msg = oscMessage.getArguments();
                doOnSend(merge((Float) msg[0], (Float) msg[1], (Float) msg[2], (Float) msg[3]) * 50f, "alpha");

            }
        });
        oscIn.addListener("/muse/elements/beta_relative", new OSCListener() {
            @Override
            public void acceptMessage(Date date, OSCMessage oscMessage) {
                Object[] msg = oscMessage.getArguments();
                doOnSend(merge((Float) msg[0], (Float) msg[1], (Float) msg[2], (Float) msg[3]) * 50f, "beta");

            }
        });
        oscIn.addListener("/muse/elements/delta_relative", new OSCListener() {
            @Override
            public void acceptMessage(Date date, OSCMessage oscMessage) {
                Object[] msg = oscMessage.getArguments();
                doOnSend(merge((Float) msg[0], (Float) msg[1], (Float) msg[2], (Float) msg[3]) * 50f, "delta");
            }
        });
        oscIn.addListener("/muse/elements/gamma_relative", new OSCListener() {
            @Override
            public void acceptMessage(Date date, OSCMessage oscMessage) {
                Object[] msg = oscMessage.getArguments();
                doOnSend(merge((Float) msg[0], (Float) msg[1], (Float) msg[2], (Float) msg[3]) * 501f, "gamma");
            }
        });
        oscIn.addListener("/muse/elements/theta_relative", new OSCListener() {
            @Override
            public void acceptMessage(Date date, OSCMessage oscMessage) {
                Object[] msg = oscMessage.getArguments();
                doOnSend(merge((Float) msg[0], (Float) msg[1], (Float) msg[2], (Float) msg[3]) * 127f, "theta");

            }
        });
        oscIn.startListening();
    }
    public void noteOnOfSend() {
        if (null == oscOut) {
            System.out.println("No Connection");
        }
        Object[] args = new Object[4];
        args[0] = noteVector[0];
        args[1] = noteVector[1];
        args[2] = noteVector[2];
        args[3] = noteVector[3];
        OSCMessage msg = new OSCMessage("musicinmyhead/OnOff/", args);
        try {
            oscOut.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public int checkValuea(float old, float news){
        float tmp = Math.abs(old-news);
        //System.out.println(tmp);
        if(tmp > 1){
            oldAlpha = news;
            return 0;
        }
        return 1;
    }
    public int checkValueb(float old, float news){
        float tmp = Math.abs(old-news);
        //System.out.println(tmp);
        if(tmp > 1){
            oldBeta = news;
            return 1;
        }
        return 0;
    }
    public int checkValuec(float old, float news){
        float tmp = Math.abs(old-news);
        //System.out.println(tmp);
        if(tmp > 1){
            oldDelta = news;
            return 1;
        }
        return 0;
    }
    public int checkValued(float old, float news){
        float tmp = Math.abs(old-news);
        //System.out.println(tmp);
        if(tmp > 1){
            oldGamma = news;
            return 1;
        }
        return 0;
    }

    public void doOnSend(float number, String waveName){
        switch(waveName){
            case "alpha": noteVector[0] = checkValuea(oldAlpha,number);break;
            case "beta": noteVector[1] = checkValueb(oldBeta,number);break;
            case "delta": noteVector[2] = checkValuec(oldDelta,number);break;
            case "gamma": noteVector[3] = checkValued(oldGamma,number);break;
            default: break;
        }
        noteOnOfSend();

        if(null == oscOut){
            System.out.println("No Connection");
        }
        Object[] args = new Object[2];
        args[0] = number;
        args[1] = number;
        OSCMessage msg = new OSCMessage("musicinmyhead/"+waveName, args);
        try {
            oscOut.send(msg);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
