class Woman extends Person {


    private int[] priority;
    private int husband = NOT_AVAILABLE;


    Woman(String name, int num) {
        super(name, num);
    }

    @Override
    public void loadPriority(int[] priority) {
        this.priority = new int[priority.length];
        for (int i = 0; i < priority.length; i++)
            this.priority[priority[i]+1] = i;
    }

    public boolean isSingle() {
        return husband == NOT_AVAILABLE;
    }

    public Integer setHusband(Integer husband) {
        int oldHusband = this.husband;
        this.husband = husband;
        return oldHusband;
    }

    public boolean prefers(int m) {
        return priority[m] < priority[husband];
    }
}
