package br.grupointegrado.SpaceInvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;




/**
 * Created by Fagner on 03/08/2015.
 */
public class TelaJogo extends TelaBase {

    private OrthographicCamera camera;
    private SpriteBatch  batch;
    private Stage palco;
    private BitmapFont fonte;
    private Label lbPontuacao;
    private Image jogador;
    private Texture texturaJogador;
    private Texture texturaJogadorDireita;
    private Texture texturaJogadorEsquerda;
    private boolean indoDireita;
    private boolean indoEsquerda;

    /**
     * Construtor padr�o da tela de Jogo
     * @param game Refer�ncia para a classe principal
     */

    public TelaJogo(MainGame game) {
        super(game);
    }

    /**
     * Chamado quando a tela � exibida
     */
    @Override
    public void show() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        palco = new Stage(new FillViewport(camera.viewportWidth, camera.viewportHeight, camera));

        initFonte();
        initInformacoes();
        initJogador();

    }

    private void initJogador() {
        texturaJogador = new Texture("sprites/player.png");
        texturaJogadorDireita = new Texture("sprites/player-right.png");
        texturaJogadorEsquerda = new Texture("sprites/player-left.png");

        jogador = new Image(texturaJogador);
        float x = camera.viewportWidth / 2 - jogador.getWidth() /2;
        float y = 15;

        jogador.setPosition(x, y);
        palco.addActor(jogador);
    }

    private void initInformacoes() {
        Label.LabelStyle lbEstilo = new  Label.LabelStyle();
        lbEstilo.font= fonte;
        lbEstilo.fontColor = Color.WHITE;

        lbPontuacao = new Label("O pontos", lbEstilo);
        palco.addActor(lbPontuacao);


    }

    private void initFonte() {
        fonte = new BitmapFont();
    }

    /**
     * Chamado a todo quadro de atualiza��o do jogo (FPS)
     * @param delta Tempo entre um quadro e outro (em segundo)
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.15f, .15f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        lbPontuacao.setPosition(10, camera.viewportHeight - 20);
        capturaTeclas();
        atualizarJogador(delta);
        palco.act(delta);
        palco.draw();



    }

    /**
     * Atualiza a posi��o do jogador
     * @param delta
     */
    private void atualizarJogador(float delta) {
        float velocidade = 200; //velocidade de movimento do jogador

        if(indoDireita){
            if(jogador.getX() < camera.viewportWidth - jogador.getWidth()) {
                float x = jogador.getX() + velocidade * delta;
                float y = jogador.getY();
                jogador.setPosition(x, y);
            }
        }
        if(indoEsquerda){
            if(jogador.getX() > 0){
            float x = jogador.getX() - velocidade * delta;
            float y = jogador.getY();
            jogador.setPosition(x ,y);
            }
        }

        if (indoDireita){
            //trocar imagem direita
            jogador.setDrawable(new SpriteDrawable(new Sprite(texturaJogadorDireita)));
        }else if (indoEsquerda){
            //trocar imagem esquerda
            jogador.setDrawable(new SpriteDrawable(new Sprite(texturaJogadorEsquerda)));
        }else{
            //trocar imagem centro
            jogador.setDrawable(new SpriteDrawable(new Sprite(texturaJogador)));
        }
    }

    /**
     * Verifica se as teclas est�o pressionadas
     */

    private void capturaTeclas() {

        indoDireita = false;
        indoEsquerda = false;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            indoEsquerda = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            indoDireita = true;
        }
    }

    /**
     * � chamado sempre que h� uma altera��o no tamanho da tela
     * @param width novo valor de largura da tela
     * @param height novo valor de altura da tela
     */
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();

    }

    /**
     * � chamado sempre que o jogo for minimizado
     */
    @Override
    public void pause() {

    }

    /**
     * � chamado sempre que o jogo voltar para o primeiro plano
     */
    @Override
    public void resume() {

    }

    /**
     * � chamado quando a nossa tela � destruida
     */
    @Override
    public void dispose() {

        batch.dispose();
        palco.dispose();
        fonte.dispose();
        texturaJogador.dispose();
        texturaJogadorEsquerda.dispose();
        texturaJogadorDireita.dispose();

    }
}
