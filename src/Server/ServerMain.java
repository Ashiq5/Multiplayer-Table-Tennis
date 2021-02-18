package Server;

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

public class ServerMain extends Application{
    public Circle ball=new Circle(8,Color.LAWNGREEN);
    public double x1,x2,y1,y2;
    public Ellipse c1,c2;
    public Rectangle r1,r2;
    public int f;                //  f for collision
    public double go=0;
    public int upward=0;
    public int bat_hit1=0,bat_hit2=0;
    public int serve=-1;
    public double dx1=0,dx2=0;
    public int fk;
    public int score1=0,score2=0;
    public int servelost=1;
    public double mx,rx,my,ry;     //x=server , y=client
    public int hit=999;
    AnimationTimer n;
    AudioClip sound;
    Text S,P;
    Stage stage;
    Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL resource= getClass().getResource("sport_table_tennis_ping_pong_bat_hit_ball_002.mp3");
        sound=new AudioClip(resource.toString());
        stage = primaryStage;
        showFrontPage();

    }
    public void showFrontPage() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("front page.fxml"));
        Parent root1 = loader.load();

        Controller controller = loader.getController();
        controller.getNew_game_page();
        controller.getinstruction_page();
        controller.getexit_page();
        controller.gethigh_score_page();
        controller.setMain(this);


        stage.setTitle("TABLE TENNIS");
        scene=new Scene(root1);
        stage.setScene(scene);
        stage.show();
    }
    public void showGamePage()
    {
        Image img = new Image("Server/unnamed.png",1000,650,false,true);
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
        stage.setTitle("Table Tennis-Server");
        stage.setScene(scene);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                c1.setCenterX(c1.getCenterX() - 8);
                r1.setX(r1.getX() - 8);
            }
            else if (event.getCode() == KeyCode.RIGHT) {
                c1.setCenterX(c1.getCenterX() + 8);
                r1.setX(r1.getX() + 8);
            }

            if (servelost == 1 && upward == 0) {

                if (event.getCode() == KeyCode.X) {
                    if (serve == -1) {
                        dx1=0;

                    }

                } else if (event.getCode() == KeyCode.Z) {
                    if (serve == -1) {
                        dx1-=50;

                    }

                }
                else if (event.getCode() == KeyCode.C) {
                    if (serve == -1) {
                        dx1+=50;

                    }


                }
                if(event.getCode()==KeyCode.SPACE && serve==-1)
                {
                    ball.setCenterX(c1.getCenterX());
                    ball.setCenterY(c1.getCenterY());
                    x1 = c1.getCenterX();
                    y1 = c1.getCenterY();
                    x2 = dx1+325 + (x1 - 213) * 374 / 612;
                    y2 = 268;
                    go = 1;
                    upward = 1;
                    dx1 = 0;
                    servelost = 0;
                    sound.play();
                }


            }
            if (event.getCode() == KeyCode.X && go == 2)       //direct hit
            {
                double y = ball.getCenterY();

                if (y > 468 && y < c1.getCenterY() + 10 && (f == 2 || f == 4))     // lower bat
                {

                    bat_hit1 = 1;

                }
            }
            if (event.getCode() == KeyCode.Z && go == 2)       //direct hit
            {
                dx1 -= 50;
                double y = ball.getCenterY();

                if (y > 468 && y < c1.getCenterY() + 10 && (f == 2 || f == 4))     // lower bat
                {

                    bat_hit1 = 1;

                }
            }
            if (event.getCode() == KeyCode.C && go == 2)       //direct hit
            {
                dx1 += 50;
                double y = ball.getCenterY();

                if (y > 468 && y < c1.getCenterY() + 10 && (f == 2 || f == 4))     // lower bat
                {

                    bat_hit1 = 1;

                }
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
        Thread t=new Thread(new Server(this));
        t.start();
        stage.show();
    }

    public void game_over(int f) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("game over page.fxml"));
        Parent root2=loader.load();
        Controller2 controller = loader.getController();
        controller.setMain(this);
        if(f==1)controller.text.setText("PLAYER 2 HAS WON");
        else controller.text.setText("PLAYER 1 HAS WON");
        stage.setTitle("TABLE TENNIS-SERVER");
        scene=new Scene(root2);
        stage.setScene(scene);
        stage.show();
        n.stop();
    }
    int update(long now)
    {
        //time+=0.015;
        mx=c1.getCenterX();
        rx=r1.getX();
        if(my!=0)c2.setCenterX(my);
        if(ry!=0)r2.setX(ry);


            if(hit==1 && serve==1 && servelost==1)
            {
                ball.setCenterX(c2.getCenterX());
                ball.setCenterY(c2.getCenterY());
                x1 = c2.getCenterX();
                y1 = c2.getCenterY();
                x2 = dx2+261 + (x1 - 338) * 514 / 346;
                y2 = 409;
                go = 1;
                upward = -1;
                servelost = 0;
                hit=0;
                dx2=0;
                sound.play();
            }

        if(now%10==0)
        {
            S.setText(String.format("PLAYER_1: %d",score2));
            P.setText(String.format("PLAYER 2: %d",score1));
            //hit=999;

        }
        if(servelost==1 && upward==0)
        {
            if(serve==1){
                ball.setCenterX(c2.getCenterX());
                ball.setCenterY(c2.getCenterY()+25);
            }
            else if(serve==-1){
                ball.setCenterX(c1.getCenterX());
                ball.setCenterY(c1.getCenterY()-25);
            }
        }

        if(upward==1) {

            if (go == 1 && ball.getCenterY() > 268)         //upward first drop
            {

                double y = ball.getCenterY();
                ball.setCenterY(y - 4);
                ball.setCenterX(x1 + ((x1 - x2) * (y - y1) / (y1 - y2)));
                if (ball.getCenterY() <= 268) {
                    go = 2;
                    x1 = ball.getCenterX();
                    y1 = ball.getCenterY();
                    x2 = 338 + (x1 - 325) * 346 / 374;
                    y2 = 242;
                    if(ball.getCenterX()<323 || ball.getCenterX()>702)
                    {
                        upward=0;
                        go=0;
                        serve=-1;
                        servelost=1;
                        score1++;
                        dx1=0;
                        if(winner()==1){
                            return 100;
                        }

                    }
                }
            }
            else if (go == 2) {                //upward 2nd drop
                double y = ball.getCenterY();
                ball.setCenterY(y - 3);
                ball.setCenterX(x1 + ((x1 - x2) * (y - y1) / (y1 - y2)));
                if(bat_hit2==1 && ball.getCenterY()<c2.getCenterY()+20)
                {

                    x1 = ball.getCenterX();
                    y1 = ball.getCenterY();
                    x2 = dx2 + 261 + (x1 - 338) * 514 / 346;
                    y2 = 409;
                    upward = -1;
                    go = 1;
                    bat_hit2=0;
                    dx2=0;
                    sound.play();
                }
                if(ball.getCenterY()<c2.getCenterY()+10)
                {
                    serve=1;                                       //  upper bat will serve
                    if(servelost==0){
                        score2++;
                        if(winner()==2){
                            return 200;
                        }
                    }
                    servelost=1;
                    dx2=0;
                    if(ball.getCenterY()<30)upward=0;
                }
            }
        }
        else if(upward==-1)
        {
            if (go == 1 && ball.getCenterY() < 409)         //downward first drop
            {

                double y = ball.getCenterY();
                ball.setCenterY(y + 4);
                ball.setCenterX(x1 + ((x1 - x2) * (y - y1) / (y1 - y2)));
                if (ball.getCenterY() >= 409) {
                    go = 2;
                    x1 = ball.getCenterX();
                    y1 = ball.getCenterY();
                    x2 = 213 + (x1 - 261) * 612 / 515;
                    y2 = 518;
                    if(ball.getCenterX()<260 || ball.getCenterX()>778)
                    {
                        upward=0;
                        go=0;
                        serve=1;
                        servelost=1;
                        score2++;
                        dx2=0;
                        if(winner()==2){
                            return 200;
                        }

                    }
                }
            }
            else if (go == 2) {                // 2nd drop
                double y = ball.getCenterY();
                ball.setCenterY(y + 3);
                ball.setCenterX(x1 + ((x1 - x2) * (y - y1) / (y1 - y2)));
                if(bat_hit1==1 && ball.getCenterY()>c1.getCenterY()-20)
                {
                    x1 = ball.getCenterX();
                    y1 = ball.getCenterY();
                    x2 = dx1 + 325 + (x1 - 213) * 374 / 612;
                    y2 = 268;
                    go = 1;
                    upward = 1;
                    dx1=0;
                    bat_hit1=0;
                    sound.play();
                }
                if(ball.getCenterY()>c1.getCenterY()+10)
                {
                    serve=-1;                //lower bat will serve
                    if(servelost==0){
                        score1++;
                        if(winner()==1){
                            return 100;
                        }
                    }
                    servelost=1;
                    dx1=0;
                    if(ball.getCenterY()>640)upward=0;

                }
            }
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
        if(score1>=15&&score1>score2+1 ){

            upward=0;
            servelost=1;
            return 1;
        }
        if(score2>=15&&score2>score1+1 ){

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
