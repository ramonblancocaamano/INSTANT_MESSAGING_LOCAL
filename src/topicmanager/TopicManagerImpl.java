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
        Publisher publisher = new PublisherImpl(topic);
        topicMap.put(topic, publisher);        
        return publisher;
    }

    @Override
    public void removePublisherFromTopic(Topic topic) {
        Publisher publisher = new PublisherImpl(topic);
        topicMap.remove(topic, publisher);
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
        
        if(topicMap.containsKey(topic) == false ) {
           check = new Subscription_check(topic, Subscription_check.Result.NO_TOPIC);
           return check;
        }
      
       publisher = topicMap.get(topic);
        if( publisher == null) {
            check = new Subscription_check(topic, Subscription_check.Result.NO_SUBSCRIPTION);
            return check;
        }
        
        publisher.attachSubscriber(subscriber);
        check = new Subscription_check(topic, Subscription_check.Result.OKAY);
        return check;

    }

    @Override
    public Subscription_check unsubscribe(Topic topic, Subscriber subscriber) {
        Publisher publisher;
        Subscription_check check;        

        if (topicMap.containsKey(topic) == false) {
            check = new Subscription_check(topic, Subscription_check.Result.NO_TOPIC);
            return check;
        }
        
        publisher = topicMap.get(topic);
        if( publisher == null) {
            check = new Subscription_check(topic, Subscription_check.Result.NO_SUBSCRIPTION);
            return check;
        }
        
        publisher.detachSubscriber(subscriber);
        check = new Subscription_check(topic, Subscription_check.Result.OKAY);
        return check;
    }
}
