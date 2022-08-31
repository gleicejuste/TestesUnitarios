package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.matchers.MatchersProprios;
import br.ce.wcaquino.utils.DataUtils;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LocacaoServiceTest {

    public LocacaoService service;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup(){
        service = new LocacaoService();
    }


    @Test
    public void deveAlugarFilme1() {

        //cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(new Filme ("Filme 1", 10, 5.0));

        //ação
        Locacao locacao;

        try {
            locacao = service.alugarFilme(usuario, filmes);

            //verificação
            assertThat(locacao.getValor(), is(5.0));
            assertThat(locacao.getValor(), is(equalTo(5.0)));
            assertThat(locacao.getValor(), is(not(6.0)));
            assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
            assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));

        } catch (Exception e) { }
    }

    @Test
    public void deveAlugarFilme() throws Exception {

        Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        //cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(new Filme ("Filme 1", 2, 5.0));

        //ação
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificação
        error.checkThat(locacao.getValor(), is(5.0));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));

        error.checkThat(locacao.getDataLocacao(), MatchersProprios.ehHoje());
        error.checkThat(locacao.getDataRetorno(), MatchersProprios.ehHojeComDiferencaDias(1));

    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void naoDeveAlugarFilmesSemEstoque() throws Exception {               //forma elegante

        //cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(new Filme ("Filme 1", 0, 5.0));

        //ação
        service.alugarFilme(usuario, filmes);
    }

    @Test
    public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException{ //forma robusta

        //cenário
        List<Filme> filmes = Arrays.asList(new Filme ("Filme 1", 1, 5.0));

        //ação
        try {
            service.alugarFilme(null, filmes);
            Assert.fail();
        } catch (LocadoraException e) {
            Assert.assertThat(e.getMessage(), is("Usuário vazio"));
        }
    }

    @Test
    public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException { //forma nova
        //cenário
        Usuario usuario = umUsuario().agora();

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        //ação
        service.alugarFilme(usuario, null);
    }

    @Test
    public void deveDevolverFilmeNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {

        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        //cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(
                new Filme ("Filme 1", 2, 4.0));

        //acao
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificacao
        //Boolean ehSegunda = DataUtils.verificarDiaSemana(locacao.getDataLocacao(), Calendar.MONDAY);
        //Assert.assertTrue(ehSegunda);

        //MATCHERS PRÓPRIOS
        //assertThat(locacao.getDataRetorno(), new DiaSemana(Calendar.MONDAY));
        //assertThat(locacao.getDataRetorno(), MatchersProprios.caiEm(Calendar.MONDAY));
        assertThat(locacao.getDataRetorno(), MatchersProprios.caiNumaSegundaFeira());
    }
}
