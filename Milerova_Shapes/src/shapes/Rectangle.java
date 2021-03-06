package shapes;

/**
 *
 * @author Marketa.Milerova
 */
public class Rectangle extends Shape{
    private double a;
    private double b;

    public Rectangle(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" a = %.2f, b = %.2f", a,b);
    }
    
    @Override
    public double area(){
        return a*b;
    }

    @Override
    public double perimeter() {
        return 2*(a+b);
    }
}
