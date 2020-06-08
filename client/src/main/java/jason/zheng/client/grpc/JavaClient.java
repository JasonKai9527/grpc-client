package jason.zheng.client.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jason.zheng.helloworld.GreeterGrpc;
import jason.zheng.helloworld.HelloReply;
import jason.zheng.helloworld.HelloRequest;
import jason.zheng.helloworld.HelloWorldProto;

import java.util.concurrent.TimeUnit;

public class JavaClient {
    private final ManagedChannel channel;

    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public JavaClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    private JavaClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public String sayHello(String name) {
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply reply = blockingStub.sayHello(request);
        return reply.getMessage();
    }
}
