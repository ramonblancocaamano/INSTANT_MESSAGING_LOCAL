package util;

/**
 * @Author: BLANCO CAAMANO, Ramon <ramonblancocaamano@gmail.com>
 */
public class Message {

    public Topic topic;
    public String content;

    public Message(Topic topic, String content) {
        this.topic = topic;
        this.content = content;
    }

}
