public class Node<S> {
    S stateName;
    S firstLetter;

    public Node(S stateName, S firstLetter) {
        this.stateName = stateName;
        this.firstLetter = firstLetter;
    }
}
