package it.distributedsystems.messaging;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

@Stateless
@Local
public class LogMessageClientImpl implements LogMessageClient {
	private static Logger logger = Logger.getLogger("LogMessageClientImpl");

	//NON usare static sulle risorse @Resource, occhio a non usare mappedName
	//e per la connectionFactory usa @Resource senza config aggiuntive
	//Puoi solo iniettare delle ConnectionFactory generiche, poi casti
	
	@Resource
	private  ConnectionFactory connectionFactory;

	//WATCH OUT!! se entry nel descrittore standalone-full.xml è
	//<jms-queue name="LogQueue" entries="jms/queue/LogQueue"/> nel subsystem
	//<subsystem xmlns="urn:jboss:domain:messaging-activemq:7.0">
	//il nome della coda è java:/jms/queue/LogQueue
	//cmq la queue nel mdb è chiamata jms/queue/LogQueue
	
	@Resource(name="java:/jms/queue/LogQueue")
	private  Queue queue;
	
	private QueueConnection connection;
	private QueueSession session;
	private QueueSender messageProducer;
	
	public LogMessageClientImpl() {
		
	

		
	}
	
	/*
	 * metodo invocato dopo che il bean è stato istanziato e le dipedenze iniettate
	 */
	@PostConstruct
	public void initMessagingConnection() {
		logger.info("LOG MESSAGE CLIENT ACTIVATED");
		 try {
			connection = ((QueueConnectionFactory)  connectionFactory).createQueueConnection();
			 session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			
			 messageProducer= session.createSender( queue);
		
			
				connection.start();
			} catch (JMSException e) {
				logger.error("error in log message client initialization");
				e.printStackTrace();
			}
	}
	
	
	
	/* (non-Javadoc)
	 * @see it.distributedsystems.notused.MessageClient#sendMessage()
	 */
	@Override
	public void sendMessage(String message) {
		
	
		
		try {
			TextMessage jmsmessage = session.createTextMessage();
			jmsmessage.setText(message);
		
		    messageProducer.send(jmsmessage);
		
		} catch (JMSException e) {
			logger.error("error in log message client send");
			e.printStackTrace();
		}
		
		
		
	}
}
