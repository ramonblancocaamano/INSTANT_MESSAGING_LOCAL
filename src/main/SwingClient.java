package main;

import java.util.List;
import util.Message;
import util.Subscription_check;
import util.Topic;
import subscriber.SubscriberImpl;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import publisher.Publisher;
import subscriber.Subscriber;
import topicmanager.TopicManager;

/**
 * @Author: BLANCO CAAMANO, Ramon <ramonblancocaamano@gmail.com>
 */
public class SwingClient {

    TopicManager topicManager;
    public Map<Topic, Subscriber> my_subscriptions;
    Publisher publisher;
    Topic publisherTopic;
    SubscriberImpl suscriber;

    JFrame frame;
    JTextArea topic_list_TextArea;
    public JTextArea messages_TextArea;
    public JTextArea my_subscriptions_TextArea;
    JTextArea publisher_TextArea;
    JTextField argument_TextField;

    public SwingClient(TopicManager topicManager) {
        this.topicManager = topicManager;
        my_subscriptions = new HashMap<Topic, Subscriber>();
        publisher = null;
        publisherTopic = null;
        suscriber = new SubscriberImpl(this);
    }

    public void createAndShowGUI() {

        frame = new JFrame("Publisher/Subscriber demo");
        frame.setSize(300, 300);
        frame.addWindowListener(new CloseWindowHandler());

        topic_list_TextArea = new JTextArea(5, 10);
        messages_TextArea = new JTextArea(10, 20);
        my_subscriptions_TextArea = new JTextArea(5, 10);
        publisher_TextArea = new JTextArea(1, 10);
        argument_TextField = new JTextField(20);

        JButton show_topics_button = new JButton("show Topics");
        JButton new_publisher_button = new JButton("new Publisher");
        JButton new_subscriber_button = new JButton("new Subscriber");
        JButton to_unsubscribe_button = new JButton("to unsubscribe");
        JButton to_post_an_event_button = new JButton("post an event");
        JButton to_close_the_app = new JButton("close app.");

        show_topics_button.addActionListener(new showTopicsHandler());
        new_publisher_button.addActionListener(new newPublisherHandler());
        new_subscriber_button.addActionListener(new newSubscriberHandler());
        to_unsubscribe_button.addActionListener(new UnsubscribeHandler());
        to_post_an_event_button.addActionListener(new postEventHandler());
        to_close_the_app.addActionListener(new CloseAppHandler());

        JPanel buttonsPannel = new JPanel(new FlowLayout());
        buttonsPannel.add(show_topics_button);
        buttonsPannel.add(new_publisher_button);
        buttonsPannel.add(new_subscriber_button);
        buttonsPannel.add(to_unsubscribe_button);
        buttonsPannel.add(to_post_an_event_button);
        buttonsPannel.add(to_close_the_app);

        JPanel argumentP = new JPanel(new FlowLayout());
        argumentP.add(new JLabel("Write content to set a new_publisher / new_subscriber / unsubscribe / post_event:"));
        argumentP.add(argument_TextField);

        JPanel topicsP = new JPanel();
        topicsP.setLayout(new BoxLayout(topicsP, BoxLayout.PAGE_AXIS));
        topicsP.add(new JLabel("Topics:"));
        topicsP.add(topic_list_TextArea);
        topicsP.add(new JScrollPane(topic_list_TextArea));
        topicsP.add(new JLabel("My Subscriptions:"));
        topicsP.add(my_subscriptions_TextArea);
        topicsP.add(new JScrollPane(my_subscriptions_TextArea));
        topicsP.add(new JLabel("I'm Publisher of topic:"));
        topicsP.add(publisher_TextArea);
        topicsP.add(new JScrollPane(publisher_TextArea));

        JPanel messagesPanel = new JPanel();
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.PAGE_AXIS));
        messagesPanel.add(new JLabel("Messages:"));
        messagesPanel.add(messages_TextArea);
        messagesPanel.add(new JScrollPane(messages_TextArea));

        Container mainPanel = frame.getContentPane();
        mainPanel.add(buttonsPannel, BorderLayout.PAGE_START);
        mainPanel.add(messagesPanel, BorderLayout.CENTER);
        mainPanel.add(argumentP, BorderLayout.PAGE_END);
        mainPanel.add(topicsP, BorderLayout.LINE_START);

        frame.pack();
        frame.setVisible(true);
    }

    class showTopicsHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Topic> topics;
            String text = "";
            
            topics = topicManager.topics();           
            for (int i = 0; i < topics.size(); i++) {
                text = text + topics.get(i).name +'\n';
            }
            
            topic_list_TextArea.setText(text);
        }
    }

    class newPublisherHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {   
            String text;
            
            text = argument_TextField.getText();
            if (text.equals("")) {
                return;
            }
            
            publisherTopic = new Topic(text);
            publisher = topicManager.addPublisherToTopic(publisherTopic);            
            publisher_TextArea.setText(publisherTopic.name);            
        }
    }

    class newSubscriberHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Topic> topics;
            Subscription_check check;
            String text;
            Topic topic;
            
            
            text = argument_TextField.getText();
            topic = new Topic(text);
            check = topicManager.subscribe(topic, suscriber);
            
            if (check.result == Subscription_check.Result.OKAY) {
                my_subscriptions.put(topic,suscriber);
            }
            
            text = "";
            topics =  new ArrayList<Topic>(my_subscriptions.keySet());       
            for (int i = 0; i < topics.size(); i++) {
                text = text + topics.get(i).name +'\n';
            }
            my_subscriptions_TextArea.setText(text);
        }
    }

    class UnsubscribeHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Topic> topics;
            Subscription_check check;
            String text;
            Topic topic;
            
            text = argument_TextField.getText();
            topic = new Topic(text);
            check = topicManager.unsubscribe(topic, suscriber);
            if (check.result == Subscription_check.Result.OKAY) {
                my_subscriptions.remove(topic,suscriber);
            }
            
            text = "";
            topics =  new ArrayList<Topic>(my_subscriptions.keySet());       
            for (int i = 0; i < topics.size(); i++) {
                text = text + topics.get(i).name +'\n';
            }
            my_subscriptions_TextArea.setText(text);
        }
    }

    class postEventHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Topic> topics;
            String text;
            Message message;
            
            text = argument_TextField.getText();
            message = new Message(publisherTopic, text);
            publisher.publish(message);
        }
    }

    class CloseAppHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("all users closed");
            System.exit(0);
        }
    }

    class CloseWindowHandler implements WindowListener {

        @Override
        public void windowDeactivated(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowClosed(WindowEvent e) {
        }

        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {

            //...
            System.out.println("one user closed");
        }
    }
}
