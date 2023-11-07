import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Calcolatrice {
    private JPanel Calcolatrice;
    private JTextField txtDisplay;
    private JButton btnApertaTonda;
    private JButton btnChiusaTonda;
    private JButton btnCancella;
    private JButton btn7;
    private JButton btn5;
    private JButton btn8;
    private JButton btn9;
    private JButton btn4;
    private JButton btn6;
    private JButton btnAC;
    private JButton btnSomma;
    private JButton btnSottrazione;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btnMoltiplica;
    private JButton btn0;
    private JButton btnPunto;
    private JButton btnUguale;
    private JButton btnDivisione;
    private JButton btnNegativi;
    private JButton btnRPN;
    private JButton btnSpazio;

    private boolean RPNmode = false;

    public Calcolatrice() {
        btnCancella.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText("");
            }
        });
        btn0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText(txtDisplay.getText() + "0");
            }
        });
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText(txtDisplay.getText() + "1");
            }
        });
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText(txtDisplay.getText() + "2");
            }
        });
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText(txtDisplay.getText() + "3");
            }
        });
        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText(txtDisplay.getText() + "4");
            }
        });
        btn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText(txtDisplay.getText() + "5");
            }
        });
        btn6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText(txtDisplay.getText() + "6");
            }
        });
        btn7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText(txtDisplay.getText() + "7");
            }
        });
        btn8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText(txtDisplay.getText() + "8");
            }
        });
        btn9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText(txtDisplay.getText() + "9");
            }
        });
        btnPunto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText(txtDisplay.getText() + ".");
            }
        });
        btnSomma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText(txtDisplay.getText() + "+");
            }
        });
        btnSottrazione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText(txtDisplay.getText() + "-");
            }
        });
        btnMoltiplica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText(txtDisplay.getText() + "*");
            }
        });
        btnDivisione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText(txtDisplay.getText() + "/");
            }
        });
        btnApertaTonda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText(txtDisplay.getText() + "(");
            }
        });
        btnChiusaTonda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDisplay.setText(txtDisplay.getText() + ")");
            }
        });
        btnAC.addActionListener(new ActionListener() {  //cancella 1 cifra
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = txtDisplay.getText();
                if (!text.isEmpty()) {
                    txtDisplay.setText(text.substring(0, text.length() - 1));
                }

            }
        });
        btnNegativi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtDisplay.getText().contains(".")) {
                    double value = Double.parseDouble(txtDisplay.getText());
                    value = -1 * value;
                    txtDisplay.setText(String.valueOf(value));
                } else {
                    long val = Long.parseLong(txtDisplay.getText());
                    val = -1 * val;
                    txtDisplay.setText(String.valueOf(val));
                }
            }
        });
        btnRPN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RPNmode = true;
                btnRPN.setText("RPN: ON");
            }
        });
        btnUguale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (RPNmode == false) {
                    String str = infixToPostfix(txtDisplay.getText());
                    String ris = Double.toString(calcoloRPN(str));
                    txtDisplay.setText(ris);
                } else {
                    String ris = Double.toString(calcoloRPN(txtDisplay.getText()));
                    txtDisplay.setText(ris);
                }
            }
        });
    }

    public static String infixToPostfix(String infissa) { //converto da notazione infissa a rpn
        StringBuilder postfissa = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (char c : infissa.toCharArray()) {
            if (Character.isDigit(c) || Character.isLetter(c)) {
                postfissa.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfissa.append(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && hasPrecedenza(c) <= hasPrecedenza(stack.peek())) {
                    postfissa.append(stack.pop());
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            postfissa.append(stack.pop());
        }
        return postfissa.toString();
    }

    private static int hasPrecedenza(char operatore) {
        if (operatore == '+' || operatore == '-') {
            return 1;
        } else if (operatore == '*' || operatore == '/') {
            return 2;
        } else if (operatore == '^') {
            return 3;
        }
        return 0;
    }

    public static double calcoloRPN(String postfix) {   //calcolo risultato in notazione rpn
        Stack<Double> stack = new Stack<>();
        for (char c : postfix.toCharArray()) {
            if (Character.isDigit(c)) {
                stack.push(Double.parseDouble(String.valueOf(c)));
            } else if (isOperator(c)) {
                double operando2 = stack.pop();
                double operando1 = stack.pop();
                double ris = operazione(c, operando1, operando2);
                stack.push(ris);
            }
        }
        return stack.pop();
    }

    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static double operazione(char operatore, double a, double b) {
        switch (operatore) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Divisione per zero");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Operatore non valido: " + operatore);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calcolatrice");
        frame.setContentPane(new Calcolatrice().Calcolatrice);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
