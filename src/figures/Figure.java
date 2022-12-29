package figures;

import java.awt.*;
import java.util.List;

public abstract class Figure {
    public List<Piece> pieces;

    public List<Piece> piecesNext;

    public int shape = 1;

    public Figure(List<Piece> pieces, List<Piece> piecesNext) {
        this.pieces = pieces;
        this.piecesNext = piecesNext;
    }

    public abstract Color getColor();

    public void draw(Graphics g) {
        for (Piece piece : pieces) {
            g.setColor(this.getColor());
            g.fillRoundRect(piece.position.x, piece.position.y, Piece.PEACE_SIZE, Piece.PEACE_SIZE, Piece.ARC, Piece.ARC);
            g.setColor(Color.BLACK);
            g.drawRoundRect(piece.position.x, piece.position.y, Piece.PEACE_SIZE, Piece.PEACE_SIZE, Piece.ARC, Piece.ARC);
        }
    }

    public void drawNext(Graphics g) {
        for (Piece piece : piecesNext) {
            g.setColor(this.getColor());
            g.fillRoundRect(piece.position.x, piece.position.y, Piece.PEACE_SIZE_SMALL, Piece.PEACE_SIZE_SMALL, Piece.ARC, Piece.ARC);
            g.setColor(Color.BLACK);
            g.drawRoundRect(piece.position.x, piece.position.y, Piece.PEACE_SIZE_SMALL, Piece.PEACE_SIZE_SMALL, Piece.ARC, Piece.ARC);
        }
    }

    public boolean canMove(Direction direction, List<Piece> boardPieces) {
        switch (direction) {
            case DOWN:
                if (this.pieces.stream().anyMatch(piece -> piece.position.y == 610)) {
                    return false;
                }

                if (this.pieces.stream().anyMatch(piece ->
                        boardPieces.stream().anyMatch(boardPiece -> piece.position.y + Piece.PEACE_SIZE == boardPiece.position.y
                                && piece.position.x == boardPiece.position.x))) {
                    return false;
                }

                return true;
            case LEFT:
                if (this.pieces.stream().anyMatch(piece -> piece.position.x == 20)) {
                    return false;
                }
                if (this.pieces.stream().anyMatch(piece ->
                        boardPieces.stream().anyMatch(boardPiece -> piece.position.x - Piece.PEACE_SIZE == boardPiece.position.x
                                && piece.position.y == boardPiece.position.y))) {
                    return false;
                }
                return true;
            case RIGHT:
                if (this.pieces.stream().anyMatch(piece -> piece.position.x == 380)) {
                    return false;
                }
                if (this.pieces.stream().anyMatch(piece ->
                        boardPieces.stream().anyMatch(boardPiece -> piece.position.x + Piece.PEACE_SIZE == boardPiece.position.x
                                && piece.position.y == boardPiece.position.y))) {
                    return false;
                }

                return true;
            default:
                return false;
        }
    }

    public void move(Direction direction) {
        switch (direction) {
            case DOWN:
                for (Piece piece : this.pieces) {
                    piece.position.y += Piece.PEACE_SIZE;
                }

                break;
            case LEFT:
                for (Piece piece : this.pieces) {
                    piece.position.x -= Piece.PEACE_SIZE;
                }

                break;
            case RIGHT:
                for (Piece piece : this.pieces) {
                    piece.position.x += Piece.PEACE_SIZE;
                }

                break;
            default:
                break;
        }
    }

    public abstract void rotate();

    public abstract boolean canRotate(List<Piece> pieces);
}
