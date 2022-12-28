package figures;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class L extends Figure{

    private static final Color COLOR = Color.BLUE;

    public L() {
        super(initPieces(), initPiecesNext());
    }

    private static java.util.List<Piece> initPieces() {
        java.util.List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(new Position(170, 130)));
        pieces.add(new Piece(new Position(200, 130)));
        pieces.add(new Piece(new Position(230, 130)));
        pieces.add(new Piece(new Position(230, 100)));

        return pieces;
    }

    private static java.util.List<Piece> initPiecesNext() {
        List<Piece> piecesNext = new ArrayList<>();
        piecesNext.add(new Piece(new Position(70, 42)));
        piecesNext.add(new Piece(new Position(90, 42)));
        piecesNext.add(new Piece(new Position(110, 42)));
        piecesNext.add(new Piece(new Position(110, 22)));

        return piecesNext;
    }

    @Override
    public Color getColor() {
        return COLOR;
    }

    @Override
    public void rotate() {

    }

    @Override
    public boolean canRotate(List<Piece> pieces) {
        return false;
    }
}
