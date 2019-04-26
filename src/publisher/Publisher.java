package publisher;

import util.Message;
import subscriber.Subscriber;

/**
 * @Author: BLANCO CAAMANO, Ramon <ramonblancocaamano@gmail.com>
 */
public interface Publisher {

    void incPublishers();

    int decPublishers();

    void attachSubscriber(Subscriber subscriber);

    boolean detachSubscriber(Subscriber subscriber);

    void detachAllSubscribers();

    void publish(Message message);
}
