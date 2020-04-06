package HeatToDatabase;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.jms.Connection;


public class MyLogger {

    /**
     * <p> sendLog </p> Method sends simple log message (String) to ActiveMQ on which Logstash is listening for kibana logs
     * @param logMessage - Sting message which is being send to ActiveMQ
     */
        public void sendLog(String logMessage) {
            try {

                // Create a ConnectionFactory
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://10.13.135.11:61616");

                // Create a Connection
                Connection connection = connectionFactory.createConnection();
                connection.start();

                // Create a Session
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // Create the destination (Topic or Queue)
                Destination destination = session.createQueue("web-worker");

                // Create a MessageProducer from the Session to the Topic or Queue
                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//              ObjectMessage message =  session.createObjectMessage();
                TextMessage message = session.createTextMessage(logMessage);

                // Tell the producer to send the message
//                System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
                producer.send(message);

                // Clean up
                session.close();
                connection.close();
            }
            catch (Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        }
    }
