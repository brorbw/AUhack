/**
 * Created by brorbw on 10/04/16.
 */
import org.junit.Before;
import org.junit.Test;
import org.midimind.osc.OSCsender;
import static junit.framework.Assert.assertEquals;

public class OscSenderTest {
    @Before
    public void setUp() {

    }

    @Test
    public void getPortTest(){
        OSCsender o = new OSCsender(5000);
        assertEquals("Port number :", 5000,o.getPort());
    }
    @Test
    public void setPortTest(){
        OSCsender o = new OSCsender(5000);
        assertEquals("Port number :", 5000,o.getPort());
        o.setPort(4000);
        assertEquals("Port number :", 4000, o.getPort());
    }
}
