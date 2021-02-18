package Client;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

public class ClientMain extends Application  {
    public Circle ball=new Circle(8,Color.LAWNGREEN);
    public Ellipse c1,c2;
    public Rectangle r1,r2;
    public int f;                //  f for collision
    public int upward=0;
    public int bat_hit2=0;
    public double dx1=0,dx2=0;
    public int fk;
    public int score1=0,score2=0;
    public int servelost=1;
    public double mx,rx,my,ry;    //x=server, y=client
    public int hit=999;
    AnimationTimer n;
    Text S,P;
    Stage stage;
    Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        showFrontPage();

    }
    public void showFrontPage() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("front page1.fxml"));
        Parent root1 = loader.load();
        Controller clientController = loader.getController();
        clientController.getNew_game_page();
        clientController.getinstruction_page();
        clientController.getexit_page();
        clientController.gethigh_score_page();
        clientController.setMain(this);


        stage.setTitle("TABLE TENNIS");
        scene=new Scene(root1);
        stage.setScene(scene);
        stage.show();
    }
    public void showGamePage()
    {
        Image img = new Image("Client/unnamed.png",1000,650,false,true);
        ImageView imageView = new ImageView(img);

        c1=new Ellipse(670,530,25,25);      //lower bat
        c1.setFill(Color.INDIGO);
        r1=new Rectangle(645,545,10,30);
        r1.setFill(Color.BROWN);
        r1.setRotationAxis(Rotate.Z_AXIS);
        r1.setRotate(35);

        c2=new Ellipse(370,195,25,25);       //upper bat
        r2=new Rectangle(385,212,10,30);
        c2.setFill(Color.INDIANRED);
        r2.setFill(Color.BROWN);
        r2.setRotationAxis(Rotate.Z_AXIS);
        r2.setRotate(-30);

        this.S=new Text(15,30,"PLAYER_1: "+score2);
        S.setFont(Font.font("Comic Sans MS", 24));
        S.setFill(Color.AQUA);
        this.P=new Text(800,30,"PLAYER_2: "+score2);
        P.setFont(Font.font("Comic Sans MS", 24));
        P.setFill(Color.AQUA);
        Group root=new Group(imageView,ball,r1,c1,r2,c2,S,P);
        scene=new Scene(root,1000,650,true);
        stage.setTitle("Table Tennis- Client");
        stage.setScene(scene);
        scene.setOnKeyPressed(event -> {
            if ((event.getCode() == KeyCode.RIGHT)) {
                c2.setCenterX(c2.getCenterX() + 8);
                r2.setX(r2.getX() + 8);
            }
            else if (event.getCode() == KeyCode.LEFT) {
                c2.setCenterX(c2.getCenterX() - 8);
                r2.setX(r2.getX() - 8);
            }
            if (event.getCode() == KeyCode.X )       //direct hit
            {
                double y = ball.getCenterY();
                if (y < 270 && y > c2.getCenterY() + 10 && (f == 3 || f == 4))        //upper bat
                {
                    bat_hit2 = 1;
                }
                dx2=0;
            }
            if (event.getCode() == KeyCode.Z )       //direct hit
            {
                dx2 -= 50;
                double y = ball.getCenterY();
                if (y < 270 && y > c2.getCenterY() + 10 && (f == 3 || f == 4))        //upper bat
                {
                    bat_hit2 = 1;
                }

            }
            if (event.getCode() == KeyCode.C )       //direct hit
            {
                dx2 += 50;
                double y = ball.getCenterY();
                if (y < 270 && y > c2.getCenterY() + 10 && (f == 3 || f == 4))        //upper bat
                {
                    bat_hit2 = 1;
                }

            }
            if(event.getCode()==KeyCode.SPACE)
            {
                hit=1;
            }
        });
        n=new AnimationTimer(){
            @Override
            public void handle(long now) {
                fk = update(now);
                detect();
                if (fk == 100) {
                    try {
                        game_over(1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (fk == 200) {
                    try {
                        game_over(2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        n.start();
        new Thread(new Client(this)).start();
        stage.show();
    }

    public void game_over(int f) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("game over page1.fxml"));
        Parent root2=loader.load();
        Controller2 controller = loader.getController();
        controller.setMain(this);
        if(f==1)controller.text.setText("PLAYER 2 HAS WON");
        else controller.text.setText("PLAYER 1 HAS WON");
        stage.setTitle("TABLE TENNIS-CLIENT");
        scene=new Scene(root2);
        stage.setScene(scene);
        stage.show();
        n.stop();
    }
    int update(long now)
    {
        //time+=0.015;
        my=c2.getCenterX();
        ry=r2.getX();
        if(mx!=0)c1.setCenterX(mx);
        if(rx!=0) r1.setX(rx);

        if(now%10==0)
        {
            S.setText(String.format("PLAYER_1: %d",score2));
            P.setText(String.format("PLAYER 2: %d",score1));
            hit=0;
            bat_hit2=0;
            dx2=0;
        }

        return 0;
    }

    public int detect()
    {
        if(((ball.getCenterX()>=(c1.getCenterX()-c1.getRadiusX()))&&(ball.getCenterX()<=(c1.getCenterX()+c1.getRadiusX()))) && ((ball.getCenterX()>=(c2.getCenterX()-c2.getRadiusX()))&&(ball.getCenterX()<=(c2.getCenterX()+c2.getRadiusX())))){
            f=4;
            return 4;
        }

        if((ball.getCenterX()>=(c1.getCenterX()-c1.getRadiusX()))&&(ball.getCenterX()<=(c1.getCenterX()+c1.getRadiusX()))){
            f=2;
            return  f;
        }
        if((ball.getCenterX()>=(c2.getCenterX()-c2.getRadiusX()))&&(ball.getCenterX()<=(c2.getCenterX()+c2.getRadiusX()))) {
            f=3;
            return f;
        }
        f=0;
        return f;
    }
    public int winner()
    {
        if(score1>=5&&score1>score2+1){

            upward=0;
            servelost=1;
            return 1;
        }
        if(score2>=5&&score2>score1+1){

            upward=0;
            servelost=1;
            return 2;
        }
        return 0;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
