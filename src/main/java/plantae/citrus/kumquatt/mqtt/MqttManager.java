package plantae.citrus.kumquatt.mqtt;

import org.apache.commons.lang3.StringUtils;
import org.fusesource.mqtt.client.*;
import plantae.citrus.kumquatt.MqttTracer;
import plantae.citrus.kumquatt.mqtt.exception.NotConnectedException;

import java.util.concurrent.TimeUnit;

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
        if (connection.isConnected()){
            try {
                connection.publish(topic, message.getBytes(), QoS.AT_MOST_ONCE, false);
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        } else {
            throw new NotConnectedException();

        }
    }

    public boolean receiveOne(String topic, String payload) throws NotConnectedException {
        if (connection.isConnected()){
            try {

                Message message = connection.receive(1, TimeUnit.SECONDS);
                if (message != null) {
                    String rTopic = message.getTopic();
                    String rPayload = new String(message.getPayload());
                    message.ack();

                    System.out.print(String.format("You received topic(%s) message(%s) : ", rTopic, rPayload));
                    if (StringUtils.equals(topic, rTopic) && StringUtils.equals(payload, rPayload)) {
                        System.out.println("Good");
                        return true;
                    } else {
                        System.out.println("NG");
                        return false;
                    }
                } else {
                    System.out.println("Nothing to receive");
                    return false;
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        } else {
            throw new NotConnectedException();
        }
    }

    public boolean subscribe(String topic) throws NotConnectedException {
        if (connection.isConnected()){
            Topic[] topics = new Topic[1];
            topics[0] = new Topic(topic, QoS.AT_MOST_ONCE);
            try {
                connection.subscribe(topics);
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        } else {
            throw new NotConnectedException();
        }
    }

    // Exception Not connected Yet

    // connect
    // isConnected
    // publish
    // subscribe
    // getMessage

}
