public class Hw3 {

  // The linked list classes are from the lecture slides on neli's ccsu website

  // node class
  static class Node {

    private char data;
    private Node next;

    public Node() {
      this('#', null);
    }

    public Node(char d) {
      data = d;
    }

    public Node(char d, Node n) {
      data = d;
      next = n;
    }

    public Node(int d, Node n) {
      data = 0;
      next = n;
    }

    public void setData(char newData) {
      data = newData;
    }

    public void setData(int newData) {
      data = (char) newData;
    }

    public void setNext(Node newNext) {
      next = newNext;
    }

    public char getData() {
      return data;
    }

    public Node getNext() {
      return next;
    }

    public void displayNode() {
      System.out.print(data);
    }
  }

  // Linked list Stack ADT from slides
  public static class LLStackADT {

    private Node top;
    private int size;


    LLStackADT() {
      top = null;
      size = 0;
    }

    public boolean empty() {
      return (top == null);
    }

    public void push(char c) {
      Node newNode = new Node();
      newNode.setData(c);
      newNode.setNext(top);
      size++;
      top = newNode;
    }

    public void push(int c) {
      Node newNode = new Node();
      newNode.setData(c);
      newNode.setNext(top);
      size++;
      top = newNode;
    }

    public char pop() {
      char i;
      i = top.getData();
      top = top.getNext();
      size--;
      return i;
    }

    public int size() {
      return size;
    }

    public char onTop() {
      char i = pop();
      push(i);
      return i;
    }
  }

  // Linked List queue ADT
  public static class LLQueueADT {

    private int size;
    private Node front;
    private Node rear;

    public LLQueueADT() {
      size = 0;
      front = null;
      rear = null;
    }

    public boolean empty() {
      return (size == 0);
    }

    public void enqueue(char c) {
      Node newNodeQ = new Node();
      newNodeQ.setData(c);
      newNodeQ.setNext(null);
      if (this.empty())
        front = newNodeQ;
      else
        rear.setNext(newNodeQ);
      rear = newNodeQ;
      size++;
    }

    public char dequeue() {
      char i;
      i = front.getData();
      front = front.getNext();
      size--;
      if (this.empty())
        rear = null;
      return i;
    }

    public char front() {
      return front.getData();
    }

    public int size() {
      return size;
    }
  }

  // this parses expressions that are fed
  static class parseExpression {

    private static LLQueueADT inFixQ;
    private static LLQueueADT postFixQ;
    private static LLStackADT opStack;
    private static LLStackADT valueStack;

    public static int stackPriority(char char1) {
      switch (char1) {
        case ')':
        case '#':
          return 0;
        case '+':
        case '-':
          return 2;
        case '/':
        case '*':
          return 3;
        case '(':
          return 1;
      }
      return -1;
    }

    // this method receives an expression infix and converts to postfix
    public static void infixToPostFix(String expression) {
      inFixQ = new LLQueueADT();
      postFixQ = new LLQueueADT();
      opStack = new LLStackADT();
      opStack.push('#');

      for (int i = 0; i < expression.length(); i++) {
        if (expression.charAt(i) != ' ')
          inFixQ.enqueue(expression.charAt(i));
      }
      while (!inFixQ.empty()) {
        int num = inFixQ.dequeue();
        char character = (char) num;
        if (character == '(')
          opStack.push(character);

        else if (Character.isLetterOrDigit(character)) {
          postFixQ.enqueue(character);
        } else if (character == ')') {
          while (!opStack.empty() && ((char) opStack.onTop()) != '(') {

            postFixQ.enqueue(opStack.pop());
          }
          if (opStack.onTop() == '(') {
            opStack.pop();
            postFixQ.enqueue(opStack.pop());
          }
        } else {

          int priority1 = stackPriority(character);
          int priority2 = stackPriority(opStack.onTop());
          while (priority1 < priority2) {
            postFixQ.enqueue(opStack.pop());
            priority2 = stackPriority(opStack.onTop());
          }
          opStack.push(character);
        }
      }
      while (opStack.onTop() != '#') {
        postFixQ.enqueue(opStack.pop());
      }
    }

    static boolean isOpStack(char char2) {
      if (char2 == '+' || char2 == '-' || char2 == '*' || char2 == '/') {
        return true;
      }
      return false;
    }

    // this method takes the postfix expression from the above method and evaluates it for the
    // postfix evaluation which is part 2 of the assignment
    static int evaluatePostfix(String exp) {
      valueStack = new LLStackADT();

      int size = exp.length();
      for (int i = 0; i < size; i++) {
        if (isOpStack(exp.charAt(i))) {

          int op1 = valueStack.pop();
          int op2 = valueStack.pop();
          switch (exp.charAt(i)) {
            case '+':
              valueStack.push(op2 + op1);
              break;
            case '-':
              valueStack.push(op2 - op1);
              break;
            case '*':
              valueStack.push(op2 * op1);
              break;
            case '/':
              valueStack.push(op2 / op1);
              break;
          }

        } else {

          int op = exp.charAt(i) - '0';

          valueStack.push(op);
        }

      }
      return (valueStack.pop());
    }

  }

  public static void main(String[] args) {

    String infixExpression = "8*1+(6/2)";
    System.out.println("Your expression(infix): " + infixExpression);
    System.out.print("Your expression(postfix): ");
    parseExpression.infixToPostFix(infixExpression);

    while (!parseExpression.postFixQ.empty()) {
      System.out.print(parseExpression.postFixQ.dequeue() + " ");

    }
    System.out.println();

    System.out.print("Evaluation: ");
    System.out.println(parseExpression.evaluatePostfix("81*62/+"));

  }

}
