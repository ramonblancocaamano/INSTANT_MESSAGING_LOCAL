package subscriber;

import util.Message;
import util.Subscription_close;

/**
 * @Author: BLANCO CAAMANO, Ramon <ramonblancocaamano@gmail.com>
 */
public interface Subscriber {

    void onMessage(Message message);

    void onClose(Subscription_close subs_close);
}
