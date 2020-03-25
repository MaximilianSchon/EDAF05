import java.util.Optional;

class Man extends Person {
    private int[] priority;
    private Optional<Integer> wife;
    private int currentWifeIndex = -1;
    private int number;
    private String name;

    public Man(String name, int number) {
        super(name, number);
    }


    @Override
    public void loadPriority(int[] priority) {
        this.priority = priority;
    }

    public int getNextWife() {
        currentWifeIndex++;
        return priority[currentWifeIndex];
    }

    public void setWife(int wife) {
        this.wife = Optional.of(wife);
    }

    public Integer getWife() {
        return wife.orElse(NOT_AVAILABLE);
    }
}
