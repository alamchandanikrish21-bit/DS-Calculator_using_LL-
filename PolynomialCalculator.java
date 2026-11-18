import java.util.Scanner;

// Each node represents one term: coefficient * x^exponent.
class Node {
    int coeff;
    int exp;
    Node next;

    Node(int coeff, int exp) {
        this.coeff = coeff;
        this.exp = exp;
        this.next = null;
    }
}

// Polynomial implemented using a singly linked list.
class Polynomial {
    private Node head;

    // Insert a term in descending order of exponent.
    // If the exponent already exists, merge the coefficients.
    // Remove the node if the merged coefficient becomes zero.
    public void insert(int coeff, int exp) {
        if (coeff == 0) {
            return;
        }

        Node prev = null;
        Node cur = head;

        while (cur != null && cur.exp > exp) {
            prev = cur;
            cur = cur.next;
        }

        if (cur != null && cur.exp == exp) {
            cur.coeff += coeff;

            if (cur.coeff == 0) {
                if (prev == null) {
                    head = cur.next;
                } else {
                    prev.next = cur.next;
                }
            }
            return;
        }

        Node newNode = new Node(coeff, exp);
        newNode.next = cur;

        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
    }

    // Addition: P + Q
    public Polynomial add(Polynomial other) {
        Polynomial result = new Polynomial();

        for (Node cur = this.head; cur != null; cur = cur.next) {
            result.insert(cur.coeff, cur.exp);
        }

        for (Node cur = other.head; cur != null; cur = cur.next) {
            result.insert(cur.coeff, cur.exp);
        }

        return result;
    }

    // Subtraction: P - Q
    public Polynomial subtract(Polynomial other) {
        Polynomial result = new Polynomial();

        for (Node cur = this.head; cur != null; cur = cur.next) {
            result.insert(cur.coeff, cur.exp);
        }

        for (Node cur = other.head; cur != null; cur = cur.next) {
            result.insert(-cur.coeff, cur.exp);
        }

        return result;
    }

    // Multiplication: P * Q
    public Polynomial multiply(Polynomial other) {
        Polynomial result = new Polynomial();

        for (Node a = this.head; a != null; a = a.next) {
            for (Node b = other.head; b != null; b = b.next) {
                result.insert(a.coeff * b.coeff, a.exp + b.exp);
            }
        }

        return result;
    }

    // Differentiation of a polynomial.
    public Polynomial differentiate() {
        Polynomial result = new Polynomial();

        for (Node cur = this.head; cur != null; cur = cur.next) {
            if (cur.exp != 0) {
                result.insert(cur.coeff * cur.exp, cur.exp - 1);
            }
        }

        return result;
    }

    // Evaluate the polynomial for a given value of x.
    public double evaluate(double x) {
        double sum = 0.0;

        for (Node cur = this.head; cur != null; cur = cur.next) {
            sum += cur.coeff * Math.pow(x, cur.exp);
        }

        return sum;
    }

    // Display the polynomial in standard mathematical form.
    @Override
    public String toString() {
        if (head == null) {
            return "0";
        }

        StringBuilder result = new StringBuilder();

        for (Node cur = head; cur != null; cur = cur.next) {
            int coeff = cur.coeff;
            int exp = cur.exp;

            if (result.length() > 0) {
                result.append(coeff >= 0 ? " + " : " - ");
            } else if (coeff < 0) {
                result.append("-");
            }

            int absCoeff = Math.abs(coeff);

            if (exp == 0 || absCoeff != 1) {
                result.append(absCoeff);
            }

            if (exp > 0) {
                result.append("x");
                if (exp > 1) {
                    result.append("^").append(exp);
                }
            }
        }

        return result.toString();
    }
}

// Main class: menu and user interaction.
public class PolynomialCalculator {
    private static final Scanner sc = new Scanner(System.in);

    // Read an integer safely.
    private static int readInt(String message) {
        while (true) {
            System.out.print(message);

            if (sc.hasNextInt()) {
                return sc.nextInt();
            }

            System.out.println("Invalid input. Please enter an integer.");
            sc.next();
        }
    }

    // Read a polynomial from the user.
    private static Polynomial readPolynomial(String name) {
        Polynomial p = new Polynomial();

        System.out.println("\nEnter " + name + ":");

        int terms;
        do {
            terms = readInt("How many terms? ");
            if (terms < 0) {
                System.out.println("Number of terms cannot be negative.");
            }
        } while (terms < 0);

        for (int i = 1; i <= terms; i++) {
            System.out.println("Term " + i + ":");

            int coeff = readInt("  Enter coefficient: ");

            int exp;
            do {
                exp = readInt("  Enter exponent: ");
                if (exp < 0) {
                    System.out.println("Exponent cannot be negative.");
                }
            } while (exp < 0);

            p.insert(coeff, exp);
        }

        System.out.println(name + " = " + p);
        return p;
    }

    private static void displayMenu() {
        System.out.println("\n========== POLYNOMIAL CALCULATOR ==========");
        System.out.println("1. Addition");
        System.out.println("2. Subtraction");
        System.out.println("3. Multiplication");
        System.out.println("4. Differentiation");
        System.out.println("5. Evaluation");
        System.out.println("6. Exit");
        System.out.println("===========================================");
    }

    public static void main(String[] args) {
        System.out.println("POLYNOMIAL CALCULATOR USING SINGLY LINKED LIST");

        int choice;

        do {
            displayMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: {
                    Polynomial p1 = readPolynomial("First Polynomial");
                    Polynomial p2 = readPolynomial("Second Polynomial");
                    Polynomial sum = p1.add(p2);

                    System.out.println("\nFirst Polynomial  : " + p1);
                    System.out.println("Second Polynomial : " + p2);
                    System.out.println("Sum               : " + sum);
                    break;
                }

                case 2: {
                    Polynomial p1 = readPolynomial("First Polynomial");
                    Polynomial p2 = readPolynomial("Second Polynomial");
                    Polynomial difference = p1.subtract(p2);

                    System.out.println("\nFirst Polynomial  : " + p1);
                    System.out.println("Second Polynomial : " + p2);
                    System.out.println("Difference        : " + difference);
                    break;
                }

                case 3: {
                    Polynomial p1 = readPolynomial("First Polynomial");
                    Polynomial p2 = readPolynomial("Second Polynomial");
                    Polynomial product = p1.multiply(p2);

                    System.out.println("\nFirst Polynomial  : " + p1);
                    System.out.println("Second Polynomial : " + p2);
                    System.out.println("Product           : " + product);
                    break;
                }

                case 4: {
                    Polynomial p = readPolynomial("Polynomial");
                    Polynomial derivative = p.differentiate();

                    System.out.println("\nPolynomial        : " + p);
                    System.out.println("Differentiation   : " + derivative);
                    break;
                }

                case 5: {
                    Polynomial p = readPolynomial("Polynomial");
                    double x = readInt("Enter the value of x: ");

                    System.out.println("\nPolynomial        : " + p);
                    System.out.println("Value at x = " + x + " : " + p.evaluate(x));
                    break;
                }

                case 6:
                    System.out.println("Thank you for using Polynomial Calculator!");
                    break;

                default:
                    System.out.println("Invalid choice. Please select a number from 1 to 6.");
            }

        } while (choice != 6);

        sc.close();
    }
}