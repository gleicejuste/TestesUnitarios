package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;

public class OrdemTest {

    public static int contador = 1;

    @Test
    public void inicio(){
        contador = 1;
    }

    @Test
    public void verifica(){
        Assert.assertEquals(1, contador);

    }


}
