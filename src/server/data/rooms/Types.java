package server.data.rooms;

public enum Types {
    Queen(0.8), King(0.85), SINGLE(0.5), DOUBLE(0.45), TRIPLE(0.3), QUAD(0.1);

    private double tax;

    Types(double tax) {
        this.tax = tax;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }
}
