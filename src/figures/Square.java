package figures;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Square extends Figure {
    private static final Color COLOR = Color.PINK;

    public Square() {
        super(initPieces(), initPiecesNext());
    }

    private static java.util.List<Piece> initPieces() {
        java.util.List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(new Position(170, 100)));
        pieces.add(new Piece(new Position(170, 130)));
        pieces.add(new Piece(new Position(200, 100)));
        pieces.add(new Piece(new Position(200, 130)));

        return pieces;
    }

    private static java.util.List<Piece> initPiecesNext() {
        List<Piece> piecesNext = new ArrayList<>();
        piecesNext.add(new Piece(new Position(70, 24)));
        piecesNext.add(new Piece(new Position(70, 44)));
        piecesNext.add(new Piece(new Position(90, 24)));
        piecesNext.add(new Piece(new Position(90, 44)));

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
        return true;
    }
}
