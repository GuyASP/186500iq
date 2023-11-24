package GameArea;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class Areacontrol {
    private String Mode;


    @FXML
    private Label uppertext,scoreLabel, timeLabel,endLabel,gameendlabel,titlelabel;
    //AllButton
    @FXML
    private Button clear, Next,Home,reBut;
    @FXML
    private Button num1, num2 , num3 , num4; //num
    @FXML
    private Button plus, minus, div, time, root, power, fac, root2, sigma, product; //opertaion
    private Button b1, b2; //number button which user click it
    //AllButton
    private int stage = 1;
    private int score = 0;

    private int[] allNum;

    //Operator
    private boolean haveOp = false;
    public Button oper;
    //Operator

    //fromanotherclass
    private Calculator cal;
    private Checker chk;
    private NumberGenerator numgen = new NumberGenerator();
    private Timer mistertime = new Timer();
    //fromanotherclass


    public void setMode(String m){
        Mode = m;
        startGame();
    }
    public void startGame(){
        if(Mode.equals("Speedrun")){
            Button[] arrBut = {num1, num2, num3, num4,reBut,clear};
            mistertime.setData(timeLabel, uppertext, endLabel,scoreLabel,gameendlabel,titlelabel, arrBut);
            mistertime.starttimer();
        }
        clear.setDisable(false);
        allNum = numgen.getNum(stage);
        scoreLabel.setText(Integer.toString(score));
        setDefault();
    }
    @FXML
    protected void stage_to_home() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FirstPage_view.fxml"));

        Stage window = (Stage)Home.getScene().getWindow();
        window.setScene(new Scene(root,800,700));
    }
    public void onClick2NumOp(ActionEvent event) {
        if(b1 != null) {
            oper = (Button) event.getSource();
            haveOp = true;
        }
    }

    public void onClick1NumOp(ActionEvent event){
        oper = (Button) event.getSource();
        if(b1 != null) {
            cal = new Calculator(b1.getText(), oper);
            sendCal();
        }
        b1 = null;
    }

    public void onClickNum(ActionEvent event){
        Button button = (Button) event.getSource();
        if(b1 !=null){
            b1.setStyle("-fx-background-color: #C9A1FF;");
        }
        button.setStyle("-fx-background-color: #AEE4FF;");
        if (haveOp && button != b1){
            b2 = button;
            if(oper.getId().equals("sigma") || oper.getId().equals("product")){
                b2.setStyle("-fx-background-color: #C9A1FF;");
                if(Integer.parseInt(b1.getText()) < Integer.parseInt(b2.getText())){
                    cal = new Calculator(b1.getText(), oper, b2.getText());
                    sendCal();
                }
            }
            else{
                cal = new Calculator(b1.getText(), oper, b2.getText());
                sendCal();
            }
            haveOp = false;
        }
        else{
            b1 = button;
        }

    }
    public void sendCal(){ //for 2 num evaluate
        oper = null;
        if(cal.AnsisInteger()) {
            String num = cal.getAns();
            afterCal(num);
        }
        else{
            b1.setStyle("-fx-background-color: #C9A1FF;");
            if(b2 != null){
                b2.setStyle("-fx-background-color: #C9A1FF;");
            }
        }
    }
    private void afterCal(String n){
        Button[] arr = {plus, minus, div, time, root, power, fac, root2, sigma, product};

        if(b2 != null){
            b2.setText(n);
            b2.setStyle("-fx-background-color: #C9A1FF;");
            b1.setVisible(false);
        }
        else{
            b1.setText(n);
            b1.setStyle("-fx-background-color: #C9A1FF;");
        }
        b1 = null; b2 = null;
        if(chk.Win(Integer.parseInt(n))){
            for(int i = 0;i<10;i++){
                arr[i].setDisable(true);
            }
            Next.setDisable(false);
            clear.setDisable(true);
            score++;
            scoreLabel.setText(Integer.toString(score));
        }
    }

    public void clear(){
        Next.setDisable(true);
        setDefault();
    }

    public void next(){
        Button[] arr = {plus, minus, div, time, root, power, fac, root2, sigma, product};
        for(int i = 0;i<10;i++){
            arr[i].setDisable(false);
        }
        stage++;
        allNum = numgen.getNum(stage);
        clear.setDisable(false);
        Next.setDisable(true);
        setDefault();
    }
    public void retry(){
        score=0;
        mistertime = new Timer();
        numgen = new NumberGenerator();
        gameendlabel.setText("");
        endLabel.setText("-----------------------");
        reBut.setVisible(false);
        startGame();
    }
    public void setDefault(){
        Button[] arrNum = {num1, num2, num3, num4};
        chk = new Checker(arrNum, allNum[0]);
        uppertext.setText(Integer.toString(allNum[0]));
        for(int i = 0;i<4;i++){
            arrNum[i].setDisable(false);
            arrNum[i].setVisible(true); //setVisible
            arrNum[i].setText(Integer.toString(allNum[i+1])); //setNum
            arrNum[i].setStyle("-fx-background-color: #C9A1FF;"); //setColor
        }
        haveOp = false;
    }


}
class Calculator{
    private Button opper ;
    private int num1,num2, ans;
    private Double specialAns;

    public Calculator(String n1, Button op){
        num1 = Integer.parseInt(n1);
        opper = op;
        think();
    }
    public Calculator(String n1, Button op, String n2){
        num1 = Integer.parseInt(n1);
        opper = op;
        num2 = Integer.parseInt(n2);
        think();
    }

    private void think(){
        switch (opper.getId()) {
            case "plus" -> ans = (num1 + num2);
            case "minus" -> ans = (num1 - num2);
            case "time" -> ans = (num1 * num2);
            case "div" -> specialAns = (num1 / (num2*1.0));
            case "root" -> specialAns = (Math.pow(num2, 1.0 / num1));
            case "root2" -> specialAns = (Math.sqrt(num1 * 1.0));
            case "power" -> ans = (int) (Math.pow(num1, num2));
            case "fac" -> ans = fracto(num1);
            case "sigma" -> ans = sigma(num1, num2);
            case "product" -> ans = product(num1, num2);
            default -> ans = 0 ;
        };
    }

    private int fracto(int n){
        int fac = 1;
        for(int i = 2;i<=n;i++)
            fac*=i;
        return fac;
    }

    private int sigma(int n1, int n2){
        int sum = 0;
        for(int i = n1;i<=n2;i++){
            sum+=i;
        }
        return sum;
    }

    private int product(int n1, int n2){
        ans = n1;
        for(int i = n1+1;i<=n2;i++){
            ans*=i;
        }
        return ans;
    }

    public boolean AnsisInteger(){
        if(specialAns != null)
            return specialAns == specialAns.intValue();
        return true;
    }
    public String getAns(){
        if(ans == 0 && specialAns != null){
            ans = (specialAns.intValue());
        }
        return Integer.toString(ans);}
}

class Checker{
    private Button button_num1,button_num2,button_num3,button_num4;
    private int btn_visible1,btn_visible2,btn_visible3,btn_visible4; //มีค่าคือ 1 และ 0 เท่านั้น
    private int targetAns, currentAns;
    public Checker(Button[] arr,int n){
        button_num1 = arr[0];
        button_num2 = arr[1];
        button_num3 = arr[2];
        button_num4 = arr[3];
        targetAns = n;
    }

    public boolean Win(int n){
        currentAns = n;
        if(!button_num1.isVisible())
            btn_visible1 = 0;
        else
            btn_visible1 = 1;
        if(!button_num2.isVisible())
            btn_visible2 = 0;
        else
            btn_visible2 = 1;
        if(!button_num3.isVisible())
            btn_visible3 = 0;
        else
            btn_visible3 = 1;
        if(!button_num4.isVisible())
            btn_visible4 = 0;
        else
            btn_visible4 = 1;
        return (btn_visible1+btn_visible2+btn_visible3+btn_visible4) == 1 && (currentAns == targetAns);
    }
}
