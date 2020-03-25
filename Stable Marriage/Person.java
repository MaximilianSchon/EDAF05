abstract class Person {
    private String name;
    private int number;
    public static int NOT_AVAILABLE = -1;
    public Person(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    abstract void loadPriority(int[] priority);

    @Override
    public String toString() {
        return name;
    }
}
