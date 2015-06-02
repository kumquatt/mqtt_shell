package plantae.citrus.kumquatt.mqtt;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import plantae.citrus.kumquatt.MqttTracer;
import plantae.citrus.kumquatt.mqtt.exception.NotConnectedException;

public class MqttManager {
    private volatile static MqttManager instance = null;
    private BlockingConnection connection = null;

    private MqttManager() {
    }

    public static MqttManager getInstance() {
        if (instance == null) {
            synchronized (MqttManager.class) {
                if (instance == null) {
                    instance = new MqttManager();
                }
            }
        }

        return instance;
    }

    public boolean isConnected() {
        if (connection != null) {
            return connection.isConnected();
        } else {
            return false;
        }
    }

    public boolean connect(String host, int port) {
        if (connection == null) {
            MQTT mqttclient = new MQTT();
            try {
                mqttclient.setHost(host, port);
                mqttclient.setVersion("3.1.1");
                mqttclient.setConnectAttemptsMax(1);
                mqttclient.setTracer(new MqttTracer());
                connection = mqttclient.blockingConnection();
                connection.connect();
            } catch (Exception e) {
                System.out.println("...");
                System.out.println(e.getMessage());
                System.out.println("...");
                return false;
            }

            return true;
        } else {
            return connection.isConnected();
        }

    }

    public boolean connect(String host, int port, String clientId) {
        if (connection == null) {
            MQTT mqttclient = new MQTT();
            try {
                mqttclient.setHost(host, port);
                mqttclient.setClientId(clientId);
                mqttclient.setVersion("3.1.1");
                mqttclient.setConnectAttemptsMax(1);
                mqttclient.setTracer(new MqttTracer());
                connection = mqttclient.blockingConnection();
                connection.connect();
            } catch (Exception e) {
                System.out.println("...");
                System.out.println(e.getMessage());
                System.out.println("...");
                return false;
            }

            return true;
        } else {
            return connection.isConnected();
        }
    }

    public boolean disconnect() {
        try {
            System.out.println(connection.isConnected());
            connection.disconnect();
            System.out.println(connection.isConnected());
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean publish(String topic, String message) throws NotConnectedException {
//        if (mqttclient.blockingConnection().isConnected()) {
//            try {
//                mqttclient.blockingConnection().publish(topic, message.getBytes(), QoS.AT_LEAST_ONCE, false);
//
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//                return false;
//            }
//        } else {
//            throw new NotConnectedException();
//        }
        return true;
    }

    // Exception Not connected Yet

    // connect
    // isConnected
    // publish
    // subscribe
    // getMessage

}
