package ru.method.celebression.punchy.client.animation.data;

import ru.method.celebression.animation.Animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class AnimationExpression {
    private final Node root;

    private AnimationExpression(Node node) {
        this.root = node;
    }

    public static AnimationExpression compile(String string) {
        if (string == null) {
            return null;
        }
        String string2 = string.trim();
        if (string2.isEmpty()) {
            return null;
        }
        try {
            Parser parser = new Parser(string2);
            Node node = parser.parse();
            return node == null ? null : new AnimationExpression(node);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public double evaluate(double d) {
        return this.root.eval(d);
    }

    public static AnimationExpression negated(AnimationExpression animationExpression) {
        return animationExpression == null ? null : new AnimationExpression(d -> -animationExpression.evaluate(d));
    }

    static interface Node {
        public double eval(double var1);
    }

    static final class Parser {
        private final String src;
        private int pos;

        Parser(String string) {
            this.src = string;
        }

        Node parse() {
            Node node = this.parseExpression();
            this.skipWs();
            return this.pos == this.src.length() ? node : null;
        }

        private Node parseExpression() {
            Node node = this.parseTerm();
            while (true) {
                this.skipWs();
                if (this.match('+')) {
                    node = new BinaryNode('+', node, this.parseTerm());
                    continue;
                }
                if (!this.match('-')) {
                    return node;
                }
                node = new BinaryNode('-', node, this.parseTerm());
            }
        }

        private Node parseTerm() {
            Node node = this.parseUnary();
            while (true) {
                this.skipWs();
                if (this.match('*')) {
                    node = new BinaryNode('*', node, this.parseUnary());
                    continue;
                }
                if (!this.match('/')) {
                    return node;
                }
                node = new BinaryNode('/', node, this.parseUnary());
            }
        }

        private Node parseUnary() {
            this.skipWs();
            if (this.match('+')) {
                return new UnaryNode('+', this.parseUnary());
            }
            return this.match('-') ? new UnaryNode('-', this.parseUnary()) : this.parsePrimary();
        }

        private Node parsePrimary() {
            this.skipWs();
            if (this.match('(')) {
                Node node = this.parseExpression();
                this.expect(')');
                return node == null ? new ConstantNode(0.0) : node;
            }
            if (this.peekNumberStart()) {
                return new ConstantNode(this.parseNumber());
            }
            String string = this.parseIdentifier();
            if (string != null && !string.isEmpty()) {
                String string2 = this.normalizeIdentifier(string);
                this.skipWs();
                if (this.match('(')) {
                    ArrayList<Node> arrayList = new ArrayList<Node>();
                    this.skipWs();
                    if (!this.peek(')')) {
                        while (true) {
                            arrayList.add(this.parseExpression());
                            this.skipWs();
                            if (!this.match(',')) continue;
                        }
                    }
                    this.expect(')');
                    return new FunctionNode(string2, arrayList);
                }
                if ("query.anim_time".equals(string2) || "query.animtime".equals(string2)) {
                    return VariableNode.ANIM_TIME;
                }
                if ("pi".equals(string2) || "math.pi".equals(string2)) {
                    return new ConstantNode(Math.PI);
                }
                return !"e".equals(string2) && !"math.e".equals(string2) ? new ConstantNode(0.0) : new ConstantNode(Math.E);
            }
            return new ConstantNode(0.0);
        }

        private String normalizeIdentifier(String string) {
            String string2;
            String string3 = string.trim().toLowerCase(Locale.ROOT);
            if (string3.startsWith("math.") && !(string2 = string3.substring("math.".length())).isBlank()) {
                return string2;
            }
            return string3;
        }

        private boolean peekNumberStart() {
            if (this.pos >= this.src.length()) {
                return false;
            }
            char c = this.src.charAt(this.pos);
            return Character.isDigit(c) || c == '.';
        }

        private double parseNumber() {
            int n = this.pos;
            while (this.pos < this.src.length()) {
                char c = this.src.charAt(this.pos);
                if (!Character.isDigit(c) && c != '.') {
                    if (c != 'e' && c != 'E' || this.pos + 1 >= this.src.length()) break;
                    ++this.pos;
                    if (this.src.charAt(this.pos) != '+' && this.src.charAt(this.pos) != '-') continue;
                    ++this.pos;
                    continue;
                }
                ++this.pos;
            }
            try {
                return Double.parseDouble(this.src.substring(n, this.pos));
            }
            catch (Exception exception) {
                return 0.0;
            }
        }

        private String parseIdentifier() {
            char c;
            this.skipWs();
            if (this.pos >= this.src.length()) {
                return null;
            }
            int n = this.pos;
            char c2 = this.src.charAt(this.pos);
            if (!Character.isLetter(c2) && c2 != '_') {
                return null;
            }
            ++this.pos;
            while (this.pos < this.src.length() && (Character.isLetterOrDigit(c = this.src.charAt(this.pos)) || c == '_' || c == '.')) {
                ++this.pos;
            }
            return this.src.substring(n, this.pos);
        }

        private void skipWs() {
            while (this.pos < this.src.length() && Character.isWhitespace(this.src.charAt(this.pos))) {
                ++this.pos;
            }
        }

        private boolean match(char c) {
            if (this.peek(c)) {
                ++this.pos;
                return true;
            }
            return false;
        }

        private boolean peek(char c) {
            this.skipWs();
            return this.pos < this.src.length() && this.src.charAt(this.pos) == c;
        }

        private void expect(char c) {
            this.skipWs();
            if (this.pos < this.src.length() && this.src.charAt(this.pos) == c) {
                ++this.pos;
            }
        }
    }

    static enum VariableNode implements Node
    {
        ANIM_TIME;


        @Override
        public double eval(double d) {
            return d;
        }
    }

    record UnaryNode(char op, Node operand) implements Node
    {
        @Override
        public double eval(double d) {
            double d2 = this.operand.eval(d);
            return this.op == '-' ? -d2 : d2;
        }
    }

    record FunctionNode(String name, List<Node> args) implements Node
    {
        @Override
        public double eval(double d) {
            ArrayList<Double> arrayList = new ArrayList<Double>(this.args.size());
            for (Node argument : this.args) {
                arrayList.add(argument.eval(d));
            }
            return switch (this.name) {
                case "sin" -> {
                    if (arrayList.isEmpty()) {
                        yield 0.0;
                    }
                    yield Math.sin(Math.toRadians((Double)arrayList.get(0)));
                }
                case "cos" -> {
                    if (arrayList.isEmpty()) {
                        yield 0.0;
                    }
                    yield Math.cos(Math.toRadians((Double)arrayList.get(0)));
                }
                case "tan" -> {
                    if (arrayList.isEmpty()) {
                        yield 0.0;
                    }
                    yield Math.tan(Math.toRadians((Double)arrayList.get(0)));
                }
                case "abs" -> {
                    if (arrayList.isEmpty()) {
                        yield 0.0;
                    }
                    yield Math.abs((Double)arrayList.get(0));
                }
                case "sqrt" -> {
                    if (arrayList.isEmpty()) {
                        yield 0.0;
                    }
                    yield Math.sqrt(Math.max(0.0, (Double)arrayList.get(0)));
                }
                case "floor" -> {
                    if (arrayList.isEmpty()) {
                        yield 0.0;
                    }
                    yield Math.floor((Double)arrayList.get(0));
                }
                case "ceil" -> {
                    if (arrayList.isEmpty()) {
                        yield 0.0;
                    }
                    yield Math.ceil((Double)arrayList.get(0));
                }
                case "round" -> {
                    if (arrayList.isEmpty()) {
                        yield 0.0;
                    }
                    yield Math.rint((Double)arrayList.get(0));
                }
                case "min" -> {
                    if (arrayList.isEmpty()) {
                        yield 0.0;
                    }
                    double minimum = (Double)arrayList.get(0);
                    for (int index = 1; index < arrayList.size(); ++index) {
                        minimum = Math.min(minimum, (Double)arrayList.get(index));
                    }
                    yield minimum;
                }
                case "max" -> {
                    if (arrayList.isEmpty()) {
                        yield 0.0;
                    }
                    double maximum = (Double)arrayList.get(0);
                    for (int index = 1; index < arrayList.size(); ++index) {
                        maximum = Math.max(maximum, (Double)arrayList.get(index));
                    }
                    yield maximum;
                }
                case "clamp" -> {
                    if (arrayList.size() < 3) {
                        if (arrayList.isEmpty()) {
                            yield 0.0;
                        }
                        yield (Double)arrayList.get(0);
                    }
                    yield Math.max((Double)arrayList.get(1), Math.min((Double)arrayList.get(2), (Double)arrayList.get(0)));
                }
                default -> 0.0;
            };
        }
    }

    record ConstantNode(double value) implements Node
    {
        @Override
        public double eval(double d) {
            return this.value;
        }
    }

    record BinaryNode(char op, Node left, Node right) implements Node
    {
        @Override
        public double eval(double d) {
            double d2 = this.left.eval(d);
            double d3 = this.right.eval(d);
            return switch (this.op) {
                case '*' -> d2 * d3;
                case '+' -> d2 + d3;
                default -> 0.0;
                case '-' -> d2 - d3;
                case '/' -> d3 == 0.0 ? 0.0 : d2 / d3;
            };
        }
    }
}
