package sabal.minimalosmos;

/**
 * Created by Sergey on 10.04.2016.
 */
public interface ICanvasView {
    void drawCircle(SimpleCircle circle);

    void redraw();

    void showMessage(String text);
}
