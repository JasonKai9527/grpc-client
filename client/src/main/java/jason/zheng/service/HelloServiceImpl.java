package jason.zheng.service;

import jason.zheng.client.grpc.JavaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements IHelloService{
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);
    @Value("${gRPC.host}")
    private String host;
    @Value("${gRPC.port}")
    private int port;

    /**
     * say hello
     *
     * @param name
     * @return String
     */
    @Override
    public String sayHello(String name) {
        JavaClient client = new JavaClient(host,port);
        String reply = client.sayHello(name);
        try {
            client.shutdown();
        } catch (InterruptedException e) {
            logger.error("channel shutdown exception: error={}",e.getMessage());
        }
        return reply;
    }
}
