package org.midimind.osc;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by brorbw on 10/04/16.
 */
public class OSCsender {
    private int port;
    private OSCPortOut oscPortOut;
    public OSCsender(int portnumber){
        this.port = portnumber;
    }
    public int getPort(){
        return port;
    }
    public void setPort(int port){
        this.port = port;
    }
    public boolean start() {
        try {
            oscPortOut = new OSCPortOut(InetAddress.getLocalHost(), port);
        } catch (UnknownHostException e) {
            e.toString();
            return false;
        } catch (SocketException e) {
            e.toString();
            return false;
        }
        return true;
    }

    public void write(String path,Object[] args){
        if(null == oscPortOut){
            System.out.println("No Connection");
        }
        OSCMessage msg = new OSCMessage("MIDImind/"+path, args);
        try{
            oscPortOut.send(msg);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
