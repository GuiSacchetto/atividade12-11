public class Funcionario {
    private String nome;
    private double salarioMensal;
    private final boolean pj;

    public Funcionario(String nome, double salarioMensal) {
        this(nome, salarioMensal, false);
    }

    protected Funcionario(String nome, double salarioMensal, boolean pj) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome deve ser preenchido.");
        }

        if (!Double.isFinite(salarioMensal) || salarioMensal < 0) {
            throw new IllegalArgumentException("O salário deve ser válido e não pode ser negativo.");
        }

        this.nome = nome;
        this.salarioMensal = salarioMensal;
        this.pj = pj;
    }

    public String getNome() {
        return nome;
    }

    public double getSalarioMensal() {
        return salarioMensal;
    }

    public boolean isPJ() {
        return pj;
    }

    protected double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    public double calcularBruto() {
        return salarioMensal;
    }

    public double calcularINSS() {
        if (isPJ()) {
            return 0.0;
        }

        double b = calcularBruto();

        if (b > 8475.55) {
            b = 8475.55;
        }

        double inss = 0.0;

        if (b > 4354.27) {
            inss += (b - 4354.27) * 0.14;
            b = 4354.27;
        }

        if (b > 2902.84) {
            inss += (b - 2902.84) * 0.12;
            b = 2902.84;
        }

        if (b > 1621.00) {
            inss += (b - 1621.00) * 0.09;
            b = 1621.00;
        }

        inss += b * 0.075;

        return arredondar(inss);
    }

    public double calcularIRRF() {
        if (isPJ()) {
            return 0.0;
        }

        double bruto = calcularBruto();

        if (bruto <= 5000.00) {
            return 0.0;
        }

        double inss = calcularINSS();
        double deducao = 607.20;

        if (inss > deducao) {
            deducao = inss;
        }

        double base = bruto - deducao;

        if (base < 0) {
            base = 0;
        }

        double imposto = 0.0;

        if (base <= 2428.80) {
            imposto = 0.0;
        } else if (base <= 2826.65) {
            imposto = base * 0.075 - 182.16;
        } else if (base <= 3751.05) {
            imposto = base * 0.15 - 394.16;
        } else if (base <= 4664.68) {
            imposto = base * 0.225 - 675.49;
        } else {
            imposto = base * 0.275 - 908.73;
        }

        if (imposto < 0) {
            imposto = 0;
        }

        imposto = arredondar(imposto);

        double reducao = 0.0;

        if (bruto <= 7350.00) {
            reducao = 978.62 - 0.133145 * bruto;

            if (reducao < 0) {
                reducao = 0;
            }

            reducao = arredondar(reducao);
        }

        double irFinal = imposto - reducao;

        if (irFinal < 0) {
            irFinal = 0;
        }

        return arredondar(irFinal);
    }

    public double calcularFGTS() {
        if (isPJ()) {
            return 0.0;
        }

        return arredondar(calcularBruto() * 0.08);
    }

    public double calcularMEI() {
        if (isPJ()) {
            return 86.05;
        }

        return 0.0;
    }

    public double calcularLiquido() {
        double liquido = calcularBruto()
                - calcularINSS()
                - calcularIRRF()
                - calcularMEI();

        return arredondar(liquido);
    }
}
    

