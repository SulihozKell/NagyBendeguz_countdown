package com.example.nagybendeguz_countdown;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class HelloController {

    @FXML
    private TextField datumBevitel;
    @FXML
    private Button gombIndit;
    @FXML
    private Label hatralevoIdoKiir;
    private Timer idoTimer;
    private boolean gombNyomas;

    @FXML
    public void initialize() {
        gombNyomas = true;
    }

    @FXML
    public void inditTimer() {
        if (gombNyomas) {
            // HELYES DÁTUM PÉLDA -> 2021.12.01 23:00:00
            String idoString = datumBevitel.getText();
            try {
                LocalDateTime ido = LocalDateTime.parse(idoString, DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
                Period datum = Period.between(LocalDateTime.now().toLocalDate(), ido.toLocalDate());
                Duration datumIdo = Duration.between(LocalDateTime.now(), ido);
                if (!datum.isNegative() && !datumIdo.isNegative()) {
                    gombNyomas = false;
                    gombIndit.setText("-");
                    idoTimer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            Period datum = Period.between(LocalDateTime.now().toLocalDate(), ido.toLocalDate());
                            Duration datumIdo = Duration.between(LocalDateTime.now(), ido);
                            int ev = datum.getYears();
                            int honap = datum.getMonths();
                            int nap = datum.getDays();
                            int ora = datumIdo.toHoursPart();
                            int perc = datumIdo.toMinutesPart();
                            int masodperc = datumIdo.toSecondsPart();
                            Platform.runLater(() -> hatralevoIdoKiir.setText(String.format(
                                    "%d év %d hó %d nap %02d:%02d:%02d", ev, honap, nap, ora, perc, masodperc)));
                            if (ev == 0 && honap == 0 && nap == 0 && ora == 0 && perc == 0 && masodperc == 0) {
                                idoTimer.cancel();
                                Platform.runLater(()->vegeTimerFelugroAblak());
                                gombNyomas = true;
                                Platform.runLater(()->gombIndit.setText("Indít"));
                            }
                        }
                    };
                    idoTimer.schedule(timerTask, 1, 1);
                }
                else {
                    hatralevoIdoKiir.setText("Érvénytelen időpont!");
                }
            }
            catch (Exception e) {
                hatralevoIdoKiir.setText("Helytelen dátum!");
            }
        }
    }

    public void vegeTimerFelugroAblak() {
        Alert felugroAblak = new Alert(Alert.AlertType.NONE, "Lejárt az idő!", ButtonType.OK);
        felugroAblak.setTitle("Lejárt");
        felugroAblak.show();
    }
}