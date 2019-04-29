package publisher;

import util.Subscription_close;
import util.Message;
import util.Topic;
import java.util.ArrayList;
import java.util.List;
import subscriber.Subscriber;

/**
 * @Author: BLANCO CAAMANO, Ramon <ramonblancocaamano@gmail.com>
 */
public class PublisherImpl implements Publisher {

    private List<Subscriber> subscriberSet;
    private int numPublishers;
    private Topic topic;

    public PublisherImpl(Topic topic) {
        subscriberSet = new ArrayList<Subscriber>();
        numPublishers = 1;
        this.topic = topic;
    }

    @Override
    public void incPublishers() {
        numPublishers++;
    }

    @Override
    public int decPublishers() {
        numPublishers--;
        return numPublishers;
    }

    @Override
    public void attachSubscriber(Subscriber subscriber) {      
        subscriberSet.add(subscriber);
    }

    @Override
    public boolean detachSubscriber(Subscriber subscriber) {        
        Subscription_close close;
        
        close = new Subscription_close(topic, Subscription_close.Cause.SUBSCRIBER);
        subscriber.onClose(close);
        return subscriberSet.remove(subscriber);
    }

    @Override
    public void detachAllSubscribers() {
        Subscription_close close;

        close = new Subscription_close(topic, Subscription_close.Cause.PUBLISHER);
        for (int i = 0; i < subscriberSet.size(); i++) {
            subscriberSet.get(i).onClose(close);
        }
        subscriberSet.clear();
    }

    @Override
    public void publish(Message message) {
        topic = message.topic;

        for (int i = 0; i < subscriberSet.size(); i++) {
            subscriberSet.get(i).onMessage(message);
        }
    }
}
