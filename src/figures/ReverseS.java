package figures;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReverseS extends Figure {

    private static final Color COLOR = Color.CYAN;

    public ReverseS() {
        super(initPieces(), initPiecesNext());
    }

    private static java.util.List<Piece> initPieces() {
        java.util.List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(new Position(170, 100)));
        pieces.add(new Piece(new Position(170, 130)));
        pieces.add(new Piece(new Position(140, 100)));
        pieces.add(new Piece(new Position(200, 130)));

        return pieces;
    }

    private static java.util.List<Piece> initPiecesNext() {
        List<Piece> piecesNext = new ArrayList<>();
        piecesNext.add(new Piece(new Position(100, 24)));
        piecesNext.add(new Piece(new Position(100, 44)));
        piecesNext.add(new Piece(new Position(80, 24)));
        piecesNext.add(new Piece(new Position(120, 44)));

        return piecesNext;
    }

    @Override
    public Color getColor() {
        return COLOR;
    }

    @Override
    public void rotate() {
        this.pieces = this.getRotatedShape();
        if (this.shape == 1) {
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

        if (this.shape == 1) {
            int x = newPieces.get(1).position.x;
            int y = newPieces.get(1).position.y;
            newPieces.get(0).position.x = x + Piece.PEACE_SIZE;
            newPieces.get(0).position.y = y - (2 * Piece.PEACE_SIZE);
            newPieces.get(1).position.x = x + Piece.PEACE_SIZE;
            newPieces.get(1).position.y = y - Piece.PEACE_SIZE;
            newPieces.get(2).position.x = x;
            newPieces.get(2).position.y = y - Piece.PEACE_SIZE;
            newPieces.get(3).position.x = x;
            newPieces.get(3).position.y = y;
        } else if (this.shape == 2) {
            int x = newPieces.get(1).position.x;
            int y = newPieces.get(1).position.y;
            newPieces.get(0).position.x = x;
            newPieces.get(0).position.y = y + Piece.PEACE_SIZE;
            newPieces.get(1).position.x = x - Piece.PEACE_SIZE;
            newPieces.get(1).position.y = y + Piece.PEACE_SIZE;
            newPieces.get(2).position.x = x - (2 * Piece.PEACE_SIZE);
            newPieces.get(2).position.y = y;
            newPieces.get(3).position.x = x - Piece.PEACE_SIZE;
            newPieces.get(3).position.y = y;
        }

        return newPieces;
    }

    @Override
    public boolean canRotate(List<Piece> boardPieces) {
        List<Piece> rotatedPieces = this.getRotatedShape();
        if (rotatedPieces.stream().anyMatch(rotatedPiece -> boardPieces.stream()
                .anyMatch(boardPiece -> rotatedPiece.position.x == boardPiece.position.x
                        && rotatedPiece.position.y == boardPiece.position.y))) {
            return false;
        }

        if (rotatedPieces.stream().anyMatch(rotatedPiece -> rotatedPiece.position.x < 20 || rotatedPiece.position.x > 380)) {
            return false;
        }

        return true;
    }
}
