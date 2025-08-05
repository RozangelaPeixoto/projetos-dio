package Banco;

public class Conta {

    private double saldo;
    private double chequeEspecial;
    private final double valorChequeEspecial;
    private double taxaPendente;
    private static final double TAXA_CHEQUE_ESPECIAL = 0.2;

    public Conta(double saldo){
        this.saldo = saldo;
        chequeEspecial = calcularChequeEspecial(saldo);
        valorChequeEspecial = chequeEspecial;
        taxaPendente = 0.0;
    }

    private double calcularChequeEspecial(double saldo){ return saldo <= 500 ? 50.0 : saldo * 0.5; }

    private double consultarSaldo(){ return saldo; }

    private double consultarChequeEspecial(){ return chequeEspecial; }

    private double consultarTaxaPendente(){ return taxaPendente; }

    public String depositar(double deposito){
        if(estaUsandoChequeEspecial()){
            return gerenciarChequeEspecial(deposito);
        }else{
            saldo += deposito;
            if(temTaxaPendente() && saldo >= taxaPendente){
                return lidarComTaxaPendente();
            }
        }
        return mostrarInfoSaldo();
    }

    private String gerenciarChequeEspecial(double deposito){
        double valorUsadoChequeEspecial = valorUsadoDoChequeEspecial();
        double cobrancaChequeEspecial = calcularCobrancaChequeEspecial(valorUsadoChequeEspecial);
        if(depositoCobreTudo(deposito, cobrancaChequeEspecial, valorUsadoChequeEspecial)){
            lidarComDepositoCobreTudo(deposito, cobrancaChequeEspecial, valorUsadoChequeEspecial);
        }
        else{
            lidarComDepositoParical(deposito, cobrancaChequeEspecial, valorUsadoChequeEspecial);
        }
       return registrarInfoTaxaChequeEspecial(cobrancaChequeEspecial, taxaPendente) + "\n" +  mostrarInfoSaldo();
    }

    public String sacar(double valor){
        double saldoTotal = saldo + chequeEspecial;
        if(valor <= saldo) {
            saldo -= valor;
        } else if (valor <= saldoTotal) {
            double saqueEspecial = valor - saldo;
            saldo = 0;
            chequeEspecial -= saqueEspecial;
        }else{
            return "Saldo insuficiente!";
        }
        return mostrarInfoSaldo();
    }

    public String pagarBoleto(double valor){
        return sacar(valor);
    }

    private void lidarComDepositoCobreTudo(double deposito, double cobrancaTaxa, double valorUsado){
        deposito -= cobrancaTaxa + valorUsado;
        chequeEspecial += valorUsado;
        saldo += deposito;
    }

    private void lidarComDepositoParical(double deposito, double cobrancaTaxa, double valorUsado){
        if(deposito >= valorUsado){
            chequeEspecial += valorUsado;
            saldo += deposito - valorUsado;
        }else{
            chequeEspecial += deposito;
        }
        taxaPendente += cobrancaTaxa;
    }

    private String lidarComTaxaPendente(){
        String msg = "Foi cobrada uma taxa pendente de R$" + taxaPendente;
        saldo -= taxaPendente;
        taxaPendente = 0;
        return msg + "\n" + mostrarInfoSaldo();
    }

    private double calcularCobrancaChequeEspecial(double valorUsado){
        return valorUsado * TAXA_CHEQUE_ESPECIAL;
    }

    private boolean estaUsandoChequeEspecial(){
        return chequeEspecial < valorChequeEspecial;
    }

    private boolean depositoCobreTudo(double deposito, double cobrancaTaxa, double valorUsado){
        return deposito > cobrancaTaxa + valorUsado;
    }

    private boolean depositoCobreParcial(double deposito, double valorUsado){
        return deposito >= valorUsado;
    }

    private boolean temTaxaPendente(){
        return taxaPendente > 0;
    }

    private double valorUsadoDoChequeEspecial(){
        return valorChequeEspecial - chequeEspecial;
    }

    public String mostrarInfoSaldo() {
        return "Saldo: R$" +
                consultarSaldo() +
                " | " +
                mostrarInfoChequeEspecial();
    }
    public String mostrarInfoChequeEspecial() {
        StringBuilder sb = new StringBuilder();
        if(estaUsandoChequeEspecial()){
            sb.append("Cheque especial usado: R$")
                    .append(valorUsadoDoChequeEspecial())
                    .append(" | ");
        }
        sb.append("Cheque especial disponível: R$")
                .append(consultarChequeEspecial());

        return sb.toString();
    }

    private String registrarInfoTaxaChequeEspecial(double cobrancaTaxa, double taxaPendente){
        String msg = "Taxa do cheque especial de R$" + cobrancaTaxa;
        if(taxaPendente == 0) { return msg; }
        return msg += """
                    \nDevido a falta de saldo a taxa ficou pendente.
                    Quando houver saldo na conta ela será debitada.""";
    }

    public String mostrarInfoTaxaPendente() {
        double taxa = consultarTaxaPendente();
        return taxa > 0 ? "Você possui uma taxa pendente R$" + taxa : "Você não possui taxa pendente.";
    }
}

