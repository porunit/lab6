import executionmanager.NetworkHandler;
import org.apache.log4j.PropertyConfigurator;

public class UDPClient {
    public static void main(String[] args) {
        String log4jConfPath = "/home/studs/s372839/C/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        NetworkHandler.start();
    }
}