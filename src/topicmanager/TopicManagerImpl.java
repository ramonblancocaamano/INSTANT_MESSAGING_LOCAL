package topicmanager;

import util.Subscription_check;
import util.Topic;
import util.Topic_check;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import publisher.Publisher;
import publisher.PublisherImpl;
import subscriber.Subscriber;

/**
 * @Author: BLANCO CAAMANO, Ramon <ramonblancocaamano@gmail.com>
 */
public class TopicManagerImpl implements TopicManager {

    private Map<Topic, Publisher> topicMap;

    public TopicManagerImpl() {
        topicMap = new HashMap<Topic, Publisher>();
    }

    @Override
    public Publisher addPublisherToTopic(Topic topic) {
        Topic_check check;
        Publisher publisher;
        
        check = isTopic(topic);
        if (check.isOpen == false) {
            publisher = new PublisherImpl(topic);
            topicMap.put(topic, publisher);
            return publisher;
        } 
        publisher = topicMap.get(topic);
        publisher.incPublishers();
        return publisher;
    }

    @Override
    public void removePublisherFromTopic(Topic topic) {
        Topic_check check;
        Publisher publisher;
        int num;
        
        check = isTopic(topic);
        if (check.isOpen == false ){
            return;
        }
        publisher = topicMap.get(topic);
        num = publisher.decPublishers();
        
        if (num == 0) {            
            publisher.detachAllSubscribers();
            topicMap.remove(topic, publisher);
        }
    }

    @Override
    public Topic_check isTopic(Topic topic) {
        Topic_check check;
        
        check = new Topic_check(topic, topicMap.containsKey(topic));        
        return check;
    }

    @Override
    public List<Topic> topics() {
        List<Topic> topics;
        
        topics = new ArrayList<Topic>(topicMap.keySet());
        return topics;
    }

    @Override
    public Subscription_check subscribe(Topic topic, Subscriber subscriber) {
        Subscription_check check;
        Publisher publisher;
        boolean hasTopic;

        hasTopic = topicMap.containsKey(topic);
        if (hasTopic == false) {
            check = new Subscription_check(topic, Subscription_check.Result.NO_TOPIC);
            return check;
        }

        publisher = topicMap.get(topic);
        publisher.attachSubscriber(subscriber);
        check = new Subscription_check(topic, Subscription_check.Result.OKAY);
        return check;
    }

    @Override
    public Subscription_check unsubscribe(Topic topic, Subscriber subscriber) {
        Publisher publisher;
        Subscription_check check;
        boolean hasTopic;

        hasTopic = topicMap.containsKey(topic);
        if (hasTopic == false) {
            check = new Subscription_check(topic, Subscription_check.Result.NO_TOPIC);
            return check;
        }
        
        publisher = topicMap.get(topic);
        publisher.detachSubscriber(subscriber);
        check = new Subscription_check(topic, Subscription_check.Result.NO_SUBSCRIPTION);
        return check;
    }
}
