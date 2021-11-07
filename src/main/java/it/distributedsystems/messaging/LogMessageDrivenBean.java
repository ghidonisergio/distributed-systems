package it.distributedsystems.messaging;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.ejb.ActivationConfigProperty;

import org.apache.log4j.Logger;

@MessageDriven(name = "SimpleMessageBean", activationConfig = {
		 @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		 @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/LogQueue"),
		 @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") }) 
public class LogMessageDrivenBean implements MessageListener{
	private static Logger logger = Logger.getLogger("SimpleMessageBean");
	
	public LogMessageDrivenBean() {}
	
	 
	@Override
	public void onMessage(Message message) {
		
		 
		 TextMessage msg = null;

		    try {
		        if (message instanceof TextMessage) {
		            msg = (TextMessage) message;
		            logger.info("MESSAGE BEAN: Message received: " +
		                msg.getText());
		        } else {
		            logger.warn("Message of wrong type: " +
		            		message.getClass().getName());
		        }
		    } catch (JMSException e) {
		        e.printStackTrace();
		       
		    } catch (Throwable te) {
		        te.printStackTrace();
		    }
		
	}
}
