package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculadoraTest {

    private CalculadoraService calc;

    @Before
    public void setup(){
        calc = new CalculadoraService();
    }

    @Test
    public void deveSomarDoisValores(){
        //cenario
        int a = 5;
        int b = 3;

        //acao
        int result = calc.somar(a, b);

        //verificacao
        Assert.assertEquals(8, result);

    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException{
        //cenario
        int a = 6;
        int b = 3;

        //acao
        int result = calc.dividir(a, b);

        //verificacao
        Assert.assertEquals(2, result);

    }

    @Test(expected = NaoPodeDividirPorZeroException.class)
    public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException{
        //cenario
        int a = 10;
        int b = 0;

        //acao
        int result = calc.dividir(a, b);


        //verificacao
        Assert.assertEquals(2, result);

    }
}
