package rest;


public class Pair<A, B> {
    public final A fst;
    public final B snd;

    Pair(A var1, B var2) {
        this.fst = var1;
        this.snd = var2;
    }

    public static <A, B> Pair<A,B> of(A var0, B var1) {
        return new Pair<>(var0, var1);
    }
}
