package Banco;

public class Conta {

    private double saldo;
    private double chequeEspecial;
    private final double valorChequeEspecial;
    private double taxaPendente;

    public Conta(double saldo){
        this.saldo = saldo;
        chequeEspecial = calcularChequeEspecial(saldo);
        valorChequeEspecial = chequeEspecial;
        taxaPendente = 0.0;
    }

    private double calcularChequeEspecial(double saldo){
        return saldo <= 500 ? 50.0 : saldo * 0.5;
    }

    public double consultarSaldo(){
        return saldo;
    }

    public double consultarChequeEspecial(){
        return chequeEspecial;
    }

    public double consultarTaxaPendente(){
        return taxaPendente;
    }

    public void depositar(double deposito){
        if(estaUsandoChequeEspecial()){
            gerenciarChequeEspecial(deposito);
        }else{
            saldo += deposito;
            if(temTaxaPendente() && saldo >= taxaPendente){
                System.out.println("Foi cobrada uma taxa pendente de R$" + taxaPendente);
                saldo -= taxaPendente;
                taxaPendente = 0;
            }
        }
    }

    public void gerenciarChequeEspecial(double deposito){
        double valorUsadoChequeEspecial = valorUsadoDoChequeEspecial();
        double taxaChequeEspecial = 0.2;
        double cobrancaChequeEspecial = valorUsadoChequeEspecial * taxaChequeEspecial;
        //case: depósito cobre cheque especial e taxa
        if(deposito >= cobrancaChequeEspecial + valorUsadoChequeEspecial){
            deposito -= cobrancaChequeEspecial;
            deposito -= valorUsadoChequeEspecial;
            chequeEspecial += valorUsadoChequeEspecial;
            saldo += deposito;
        }
        //case: depósito cobre taxa, mas não cobre o cheque especial
        else if(deposito >= cobrancaChequeEspecial){
            deposito -= cobrancaChequeEspecial;
            chequeEspecial += deposito;
        }
        //case: depósito não cobre a taxa, mas o cliente ainda tem saldo do cheque especial
        else if(deposito < cobrancaChequeEspecial && deposito + chequeEspecial >= cobrancaChequeEspecial){
            chequeEspecial += deposito;
            chequeEspecial -= cobrancaChequeEspecial;
        }
        //case: depósito não cobre a taxa e não tem saldo no cheque especial
        else{
            chequeEspecial += deposito;
            taxaPendente += cobrancaChequeEspecial;
        }
    }

    public void sacar(double valor){
        double saldoTotal = saldo + chequeEspecial;
        if(valor <= saldo) {
            saldo -= valor;
        } else if (valor <= saldoTotal) {
            double saqueEspecial = valor - saldo;
            saldo = 0;
            chequeEspecial -= saqueEspecial;
        }else{
            System.out.println("Saldo insuficiente!");
        }
    }

    public void pagarBoleto(double valor){
        sacar(valor);
    }

    public boolean estaUsandoChequeEspecial(){
        return chequeEspecial < valorChequeEspecial;
    }

    public double valorUsadoDoChequeEspecial(){
        return valorChequeEspecial - chequeEspecial;
    }

    public boolean temTaxaPendente(){
        return taxaPendente > 0;
    }

    public String mostrarInfoSaldo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Saldo: R$")
                .append(consultarSaldo())
                .append(" | ");
        sb.append(mostrarInfoChequeEspecial());
        return sb.toString();
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
}

