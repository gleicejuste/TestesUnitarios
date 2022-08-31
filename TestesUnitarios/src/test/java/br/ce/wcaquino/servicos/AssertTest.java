package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Usuario;
import org.junit.Assert;
import org.junit.Test;

public class AssertTest {

    @Test
    public void test(){

        Assert.assertTrue(true);
        Assert.assertFalse(false);

        Assert.assertEquals(1, 1);
        Assert.assertEquals(0.51234, 0.512, 0.001);
        Assert.assertEquals(Math.PI, 3.14, 0.01);

        int i = 5;
        Integer i2 = 5;
        Assert.assertEquals(Integer.valueOf(i), i2);
        Assert.assertEquals(i, i2.intValue());

        Assert.assertEquals("bola", "bola");
        Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
        Assert.assertTrue("bola".startsWith("b"));

        Usuario u1 = new Usuario("Usuário 1");
        Usuario u2 = new Usuario("Usuário 1");
        Usuario u3 = u2;
        Usuario u4 = null;

        Assert.assertEquals(u1, u2);

        Assert.assertSame(u2, u3); //verifica se os dois objetos possuem a mesma instancia

        Assert.assertNull(u4);
    }


}
