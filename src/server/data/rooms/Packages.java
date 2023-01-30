package server.data.rooms;

public enum Packages {
    SUITES(0.5), STANDARD(0.45), STUDIO(0.3), ONLY_BED(0.1), COMPLEMENT(0);

    private double tax;

    Packages(double tax) {
        this.tax = tax;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }
}
