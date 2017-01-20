package com.jikexueyuancrm.hadoop;

        import java.util.Properties;

        import kafka.javaapi.producer.Producer;
        import kafka.producer.KeyedMessage;
        import kafka.producer.ProducerConfig;
        import kafka.serializer.StringEncoder;

        import org.apache.flume.Channel;
        import org.apache.flume.Context;
        import org.apache.flume.Event;
        import org.apache.flume.EventDeliveryException;
        import org.apache.flume.Transaction;
        import org.apache.flume.conf.Configurable;
        import org.apache.flume.sink.AbstractSink;
        //自定义sink需要继承AbstractSink类实现Configurable接口
        public class TestKafkaSink extends AbstractSink implements Configurable {

            Producer<String, String> producer;
            String topic = "test111";
            
            @Override
            public Status process() throws EventDeliveryException {
                Status status = null;
                Channel channel = getChannel();
                Transaction transaction = channel.getTransaction();
                transaction.begin();
                try {
                    Event event = channel.take();
                    if (event==null) {
                        transaction.rollback();
                        status = Status.BACKOFF;
                        return status;
                    }
                    byte[] body = event.getBody();
                    final String msg = new String(body);
                    final KeyedMessage<String, String> message = new KeyedMessage<String, String>(topic , msg);
                    producer.send(message);
                    transaction.commit();
                    status = Status.READY;            
                } catch (Exception e) {
                    transaction.rollback();
                    status = Status.BACKOFF;
                } finally {
                    transaction.close();
                }
                
                return status;
            }

            @Override
            public void configure(Context arg0) {
                Properties prop = new Properties();
                prop.put("zookeeper.connect", "h5:2181,h6:2181,h7:2181");
                prop.put("metadata.broker.list", "h5:9092,h6:9092,h7:9092");
                prop.put("serializer.class", StringEncoder.class.getName());
                producer = new Producer<String, String>(new ProducerConfig(prop));
            }

        }