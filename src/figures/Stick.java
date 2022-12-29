package figures;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Stick extends Figure {
    private static final Color COLOR = Color.YELLOW;

    public Stick() {
        super(initPieces(), initPiecesNext());
    }

    private static List<Piece> initPieces() {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(new Position(170, 100)));
        pieces.add(new Piece(new Position(200, 100)));
        pieces.add(new Piece(new Position(230, 100)));
        pieces.add(new Piece(new Position(260, 100)));

        return pieces;
    }

    private static List<Piece> initPiecesNext() {
        List<Piece> piecesNext = new ArrayList<>();
        piecesNext.add(new Piece(new Position(70, 34)));
        piecesNext.add(new Piece(new Position(90, 34)));
        piecesNext.add(new Piece(new Position(110, 34)));
        piecesNext.add(new Piece(new Position(130, 34)));

        return piecesNext;
    }

    @Override
    public Color getColor() {
        return COLOR;
    }

    @Override
    public void rotate() {
        this.pieces = this.getRotatedShape();
        if (this.shape == 1){
            this.shape = 2;
        } else {
            this.shape = 1;
        }
    }

    private List<Piece> getRotatedShape() {
        List<Piece> newPieces = new ArrayList<>();
        for (Piece piece : this.pieces) {
            newPieces.add(new Piece(new Position(piece.position.x, piece.position.y)));
        }

        if (this.shape == 1){
            int x = newPieces.get(2).position.x;
            int y = newPieces.get(2).position.y -(3 * Piece.PEACE_SIZE);
            for (int i = 0; i < newPieces.size(); i++) {
                newPieces.get(i).position.x = x;
                newPieces.get(i).position.y = (i * Piece.PEACE_SIZE) + y;
            }
        } else {
            int x = newPieces.get(3).position.x -(2 * Piece.PEACE_SIZE);
            int y = newPieces.get(3).position.y;
            for (int i = 0; i < newPieces.size(); i++) {
                newPieces.get(i).position.x = (i * Piece.PEACE_SIZE) + x;
                newPieces.get(i).position.y = y;
            }
        }

        return newPieces;
    }

    @Override
    public boolean canRotate(List<Piece> boardPieces) {
        List<Piece> rotatedPieces = this.getRotatedShape();
        if (rotatedPieces.stream().anyMatch(rotatedPiece -> boardPieces.
                stream().anyMatch(boardPiece -> rotatedPiece.position.x == boardPiece.position.x
                        && rotatedPiece.position.y == boardPiece.position.y))){
            return false;
        }

        if (rotatedPieces.stream().anyMatch(rotatedPiece -> rotatedPiece.position.x < 20 || rotatedPiece.position.x > 380)){
            return false;
        }

        return true;
    }
}