package Client;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Created by hp on 12/10/2015.
 */
public class Controller {

    public Button new_game_page;

    public Button getNew_game_page() {
        new_game_page.setRotate(-15);
        new_game_page.setOpacity(0.75);
        return new_game_page;
    }

    public Button high_score_page;
    public Button instruction_page;
    public Button exit_page;
    public ClientMain main;
    public ImageView image1;
    public Button gethigh_score_page() {
        high_score_page.setRotate(15);
        high_score_page.setOpacity(0.75);
        return high_score_page;
    }
    public Button getinstruction_page() {
        instruction_page.setRotate(-15);
        instruction_page.setOpacity(0.75);
        return instruction_page;
    }
    public Button getexit_page() {
        exit_page.setRotate(-15);
        exit_page.setOpacity(0.7);
        return exit_page;
    }
    public void NewGamePage(ActionEvent event) {
        main.showGamePage();
    }

    public void HighScorePage(ActionEvent event) {

    }

    public void InstructionPage(ActionEvent event) {

    }

    public void ExitPage(ActionEvent event) {
        main.stage.close();
    }
    void  setMain(ClientMain main){
        this.main=main;
    }

}

