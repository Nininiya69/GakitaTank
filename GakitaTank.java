package GakitaTank;

import robocode.*;
import java.awt.Color;
import robocode.util.Utils;

public class GakitaTank extends AdvancedRobot {
    int moveDirection = 1; // variável para armazenar a direção do movimento
    String targetName = ""; // variável para armazenar o nome do robô inimigo a ser mirado
    double gunAdjust; // variável para armazenar o ajuste do canhão

    public void run() {
        setColors(new Color(128,0,128), Color.black, Color.white); // define as cores do tanque (roxo, preto, branco)
        setAdjustGunForRobotTurn(true); // ajusta o canhão independentemente da rotação do tanque
        setAdjustRadarForGunTurn(true); // ajusta o radar independentemente da rotação do canhão

        while(true) {
            // verifica se existe um alvo definido
            if (!targetName.isEmpty()) {
                // obtém informações do alvo
                ScannedRobotEvent target = getTarget(targetName);

                // se o alvo for encontrado, ajusta o canhão para mirar nele
                if (target != null) {
                    gunAdjust = Utils.normalRelativeAngleDegrees(target.getBearing() + getHeading() - getGunHeading());
                    setTurnGunLeft(gunAdjust);
                    execute();

                    // atira no alvo
                    fire(1);
                }
                // se o alvo não for encontrado, remove o nome do alvo para procurar um novo alvo
                else {
                    targetName = "";
                }
            }
            // se não houver alvo definido, gira a mira no sentido anti-horário
            else {
                setTurnGunLeft(10);
                execute();
            }
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        // verifica se o robô escaneado é um inimigo
        if (e.isMyEnemy()) {
            targetName = e.getName();
        }
    }

    // método auxiliar para obter o objeto ScannedRobotEvent do alvo
    private ScannedRobotEvent getTarget(String name) {
        for (ScannedRobotEvent e : getScannedRobots()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
