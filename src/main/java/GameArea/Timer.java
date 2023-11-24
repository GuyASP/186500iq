package GameArea;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Timer {
      private int i = 100;
      private CurrentTime time = new CurrentTime(i);

      private Label showtime, text, overLabel, score, gameend, titlela;
      private Button[] btn;
      public void setData(Label timetext, Label testtext, Label End, Label s, Label ge, Label title,Button[] btn){
            showtime = timetext;
            text = testtext;
            overLabel = End;
            this.btn = btn;
            score = s;
            gameend = ge;
            titlela = title;

      }
      public void starttimer(){
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            e -> {
                            time.oneSecondPassed();
                            showtime.setText("time: "+time.getCurrentTime());
                            if((time.getCurrentTime()).equals("0")) {
                                  GameOver();
                              }
                            }));

            timeline.setCycleCount(i);
            timeline.play();
      }

      public void GameOver(){

            for(int i = 0;i<4;i++){
                  btn[i].setVisible(false);
            }
            btn[4].setVisible(true);
            titlela.setText("Time's out");
            text.setText("Your score is");
            gameend.setText(score.getText());
            overLabel.setText("Thanks for playing");
            btn[5].setDisable(true);
      }
}

class CurrentTime {
      private int timeRemaining;

      public CurrentTime(int initialTime) {
            this.timeRemaining = initialTime;
      }

      public void oneSecondPassed() {
            this.timeRemaining--;
      }

      public String getCurrentTime() {
            return String.valueOf(timeRemaining);
      }
}
