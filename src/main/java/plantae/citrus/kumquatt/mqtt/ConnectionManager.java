package plantae.citrus.kumquatt.mqtt;

import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import plantae.citrus.kumquatt.MqttTracer;
import plantae.citrus.kumquatt.mqtt.exception.NotConnectedException;

public class ConnectionManager {
    private volatile static ConnectionManager instance = null;
    private MQTT mqttclient = null;

    private ConnectionManager() {
        mqttclient = new MQTT();
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            synchronized (ConnectionManager.class) {
                if (instance == null) {
                    instance = new ConnectionManager();
                }
            }
        }

        return instance;
    }

    public boolean isConnected() {
        return mqttclient.blockingConnection().isConnected();
    }

    public boolean connect(String host, int port) {
        try {
            mqttclient.setHost(host, port);
            mqttclient.setVersion("3.1.1");
            mqttclient.setConnectAttemptsMax(1);
            mqttclient.setTracer(new MqttTracer());
            mqttclient.blockingConnection().connect();
        } catch (Exception e) {
            System.out.println("...");
            System.out.println(e.getMessage());
            System.out.println("...");
            return false;
        }

        return true;
    }

    public boolean publish(String topic, String message) throws NotConnectedException {
        if (mqttclient.blockingConnection().isConnected()) {
            try {
                mqttclient.blockingConnection().publish(topic, message.getBytes(), QoS.AT_LEAST_ONCE, false);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        } else {
            throw new NotConnectedException();
        }
        return true;
    }

    // Exception Not connected Yet

    // connect
    // isConnected
    // publish
    // subscribe
    // getMessage

}
