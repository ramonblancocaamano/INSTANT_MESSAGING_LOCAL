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
        if (numPublishers > 1) {
            numPublishers--;
        } 
        return numPublishers;
    }

    @Override
    public void attachSubscriber(Subscriber subscriber) {
        subscriberSet.add(subscriber);
    }

    @Override
    public boolean detachSubscriber(Subscriber subscriber) {
        return subscriberSet.remove(subscriber);
    }

    @Override
    public void detachAllSubscribers() {
        subscriberSet.clear();
    }

    @Override
    public void publish(Message message) {
        topic = message.topic;
        Subscriber subscriber;

        for (int i = 0; i < subscriberSet.size(); i++) {
            subscriber = subscriberSet.get(i);
            
            subscriber.onMessage(message);
        }
    }
}
