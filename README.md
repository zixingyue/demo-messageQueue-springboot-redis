# demo-messageQueue-springboot-redis
springboot集成redis做消息队列测试demo
## 发布者和订阅者模式：
发布者发送消息到队列，每个订阅者都能收到一样的消息。
主要使用了redis提供的rightPopAndLeftPush获取队列数据，如果队列没有数据则阻塞等待，也就是监听。
## 生产者和消费者模式：
生产者将消息放入队列，多个消费者共同监听，谁先抢到资源，谁就从队列中取走消息去处理。注意，每个消息只能最多被一个消费者接收。
