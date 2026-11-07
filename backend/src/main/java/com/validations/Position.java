package com.validations;

public class Position {
    private int file, rank;

    public Position() {}
    public Position(int rank, int file) {
        this.rank = rank;
        this.file = file;
    }

    public int getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    public void setFile(int file) {
        this.file = file;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Position other)) {
            return false;
        }

        return rank == other.rank && file == other.file;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(rank, file);
    }

    @Override
    public String toString() {
        return "(Rank " + rank + ", " + "File " + file + ")";
    }
}
