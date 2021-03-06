package pkg05.fractions;

/**
 *
 * @author Marketa.Milerova
 */
public class Fraction {

    private int citatel = 0;
    private int jmenovatel = 1;

    public Fraction(int citatel, int jmenovatel) {
        if(jmenovatel==0){
            throw new IllegalArgumentException("jmenovatel nesmí být nula!");
        }
        this.citatel = citatel;
        this.jmenovatel = jmenovatel;
        prepocet();
    }
    public int getCitatel() {
        return citatel;
    }

    public int getJmenovatel() {
        return jmenovatel;
    }
    @Override
    public String toString() {
        return citatel + "/" + jmenovatel;
    }

    private int nejDelitel() {
        int a = jmenovatel, b = citatel, c;
        while (b != 0) {
            c = b;
            b = a % b;
            a = c;
        }
        return a;
    }

    private void prepocet() {
        int delitel;
        delitel = nejDelitel();
        if (Math.abs(delitel) > 1) {
            jmenovatel = jmenovatel / delitel;
            citatel = citatel / delitel;
        }
        if(jmenovatel<0 && citatel>0){
            jmenovatel = jmenovatel*-1;
            citatel = citatel*-1;
        }
    }
}
