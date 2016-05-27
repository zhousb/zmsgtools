package com.ai.msg;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class KafkaProducer {
	
	public static final String KAFKA_TOPIC = "kafka.topic";
	public static final String KAFKA_BROKER_LIST = "kafka.broker.list";
	private Producer<String, String> producer = null;
	private String kafka_topic = "";
	
	
	public KafkaProducer(String param){
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject)jsonParser.parse(param);
		kafka_topic = jsonObject.get(KAFKA_TOPIC).getAsString();
		String brokers = jsonObject.get(KAFKA_BROKER_LIST).getAsString();
		
		Properties props = new Properties();
		props.put("metadata.broker.list", brokers);
        props.put("serializer.class", "kafka.serializer.StringEncoder"); 
        props.put("request.required.acks", "1");
        producer = new Producer<String, String>(new ProducerConfig(props));
	}
	
	public void sendMessage(String message){
		KeyedMessage<String, String> data = new KeyedMessage<String, String>(kafka_topic, message);
		producer.send(data);
	}
	
	public void close(){
		if(producer != null){
			producer.close();
		}
	}
	
}
