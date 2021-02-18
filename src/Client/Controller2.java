package Client;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * Created by hp on 12/11/2015.
 */
public class Controller2 {

    public ImageView image;
    public Button play;
    public Text text;
    private ClientMain serverMain;

    public void play_again(ActionEvent event) throws Exception {
        serverMain.showFrontPage();
        serverMain.score1=0;
        serverMain.score2=0;
    }
    void setMain(ClientMain main){
        this.serverMain =main;
    }

}
