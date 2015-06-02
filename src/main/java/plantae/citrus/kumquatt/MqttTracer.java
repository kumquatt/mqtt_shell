package plantae.citrus.kumquatt;

import org.fusesource.mqtt.client.Tracer;
import org.fusesource.mqtt.codec.MQTTFrame;

public class MqttTracer extends Tracer {
    @Override
    public void debug(String message, Object... args) {
        System.out.println(message);
    }

    @Override
    public void onReceive(MQTTFrame frame) {
        System.out.println(frame.header());
    }

    @Override
    public void onSend(MQTTFrame frame) {
        System.out.println(frame.header());
    }
}
