package main;

import topicmanager.TopicManager;
import topicmanager.TopicManagerImpl;

/**
 * @Author: BLANCO CAAMANO, Ramon <ramonblancocaamano@gmail.com>
 */
public class The_system {

    public static void main(String[] args) {
        final TopicManager topicManager = new TopicManagerImpl();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SwingClient client = new SwingClient(topicManager);
                client.createAndShowGUI();
            }
        });
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SwingClient client = new SwingClient(topicManager);
                client.createAndShowGUI();
            }
        });
    }
}
