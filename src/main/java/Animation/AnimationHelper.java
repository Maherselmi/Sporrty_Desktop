package Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.util.Duration;

public class AnimationHelper {
    public static void addButtonShadowEffect(Button button) {
        // Créer un effet d'ombre portée personnalisé
        DropShadow shadow = createShadowEffect();

        // Ajouter l'effet d'ombre portée lorsque le curseur est sur le bouton
        button.setOnMouseEntered(event -> button.setEffect(shadow));

        // Retirer l'effet d'ombre portée lorsque le curseur quitte le bouton
        button.setOnMouseExited(event -> button.setEffect(null));
    }

    private static DropShadow createShadowEffect() {
        DropShadow shadow = new DropShadow();
        shadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.5)); // Couleur de l'ombre
        shadow.setRadius(3); // Rayon de l'ombre
        shadow.setSpread(0.2); // Étalement de l'ombre
        shadow.setOffsetX(2); // Décalage horizontal de l'ombre
        shadow.setOffsetY(2); // Décalage vertical de l'ombre
        return shadow;
    }




    // Méthode pour ajouter une animation de transition d'échelle à un bouton


    // Méthode pour ajouter une animation de transition d'opacité à un nœud
    public static void addNodeFadeAnimation(Node node) {
        // Créer une transition d'opacité
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(100), node);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.7);

        // Définir les événements de souris pour démarrer et arrêter la transition
        node.setOnMouseEntered(event -> fadeTransition.play());
        node.setOnMouseExited(event -> fadeTransition.stop());
    }

    // Méthode pour ajouter une animation de transition d'opacité à une ligne de tableau
    public static void addTableViewFadeAnimation(TableView<?> tableView) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), tableView);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    public static void addTableRowFadeAnimation(TableView<?> tableView) {
        for (Node n : tableView.lookupAll("TableRow")) {
            if (n instanceof javafx.scene.control.TableRow) {
                ((javafx.scene.control.TableRow) n).setOpacity(0);
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), n);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();
            }
        }
    }
    // Méthode pour ajouter une animation de transition de fondu enchaîné à un nœud
    public static void addFadeTransition(Node node) {
        // Créer une transition de fondu enchaîné
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), node);
        fadeTransition.setFromValue(0.0); // Opacité de départ (invisible)
        fadeTransition.setToValue(1.0); // Opacité finale (complètement visible)
        fadeTransition.play(); // Démarrer la transition
    }
    // Méthode pour ajouter une animation de transition de déplacement à toute l'interface

}
