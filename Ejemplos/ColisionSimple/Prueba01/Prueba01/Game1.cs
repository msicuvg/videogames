using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Media;

namespace Prueba01
{
    /// <summary>
    /// This is the main type for your game
    /// </summary>
    public class Game1 : Microsoft.Xna.Framework.Game
    {
        GraphicsDeviceManager graphics;
        SpriteBatch spriteBatch;

        Figura jugador;                 // Instancia del objeto de clase Figura.
        float velocJugador;        // Valor que se le sumará o restará a la posición del objeto, por cada loop.
        KeyboardState estadoTeclado;    // Referencia que guardará el último estado del teclado, por cada loop.

        Figura jugador2;
        MouseState estadoMouse;         // Referencia que guardará el último estado del Mouse, por cada loop.

        Figura figNeutral;              // Referencia del tercer objeto para prueba de colisiones. Es inmóvil.
     
        SpriteFont font;                // Se guardará el tipo de fuente que se usará en los textos.
        bool huboColision;              // Indicará true cada vez que se detecte una colisión.
       
        public Game1()
        {
            graphics = new GraphicsDeviceManager(this);
            Content.RootDirectory = "Content";
           
            // this.graphics.IsFullScreen = true;
            
            //this.graphics.PreferredBackBufferWidth = 600;
            //this.graphics.PreferredBackBufferHeight = 800;
        }

        /// <summary>
        /// Allows the game to perform any initialization it needs to before starting to run.
        /// This is where it can query for any required services and load any non-graphic
        /// related content.  Calling base.Initialize will enumerate through any components
        /// and initialize them as well.
        /// </summary>
        protected override void Initialize()
        {
            jugador = new Figura();
            jugador2 = new Figura();
            figNeutral = new Figura();
            velocJugador = 5f;
            huboColision = false;

            base.Initialize();
        }

        /// <summary>
        /// LoadContent will be called once per game and is the place to load
        /// all of your content.
        /// </summary>
        protected override void LoadContent()
        {
            // Create a new SpriteBatch, which can be used to draw textures.
            spriteBatch = new SpriteBatch(GraphicsDevice);

            // Se establece la posición inicial del jugador (x,y)
            Vector2 posicionJugador = new Vector2(
                                                GraphicsDevice.Viewport.TitleSafeArea.X,
                                                GraphicsDevice.Viewport.TitleSafeArea.Center.Y
                                                );
            Vector2 posicionJugador2 = new Vector2(
                                                GraphicsDevice.Viewport.TitleSafeArea.Center.X,
                                                GraphicsDevice.Viewport.TitleSafeArea.Center.Y
                                                );

            Vector2 posicionNeutral = new Vector2(  GraphicsDevice.Viewport.TitleSafeArea.Right /2,
                                                    GraphicsDevice.Viewport.TitleSafeArea.Bottom / 2
                                                 );

            jugador.Inicializar(Content.Load<Texture2D>("Texturas/Azul"), posicionJugador, velocJugador);
            jugador2.Inicializar(Content.Load<Texture2D>("Texturas/Rojo"), posicionJugador2, velocJugador);
            figNeutral.Inicializar(Content.Load<Texture2D>("Texturas/Neutral"), posicionNeutral, velocJugador);

            font = Content.Load<SpriteFont>("SpriteFont1");
        }

        /// <summary>
        /// UnloadContent will be called once per game and is the place to unload
        /// all content.
        /// </summary>
        protected override void UnloadContent()
        {
            // TODO: Unload any non ContentManager content here
        }

        /// <summary>
        /// Allows the game to run logic such as updating the world,
        /// checking for collisions, gathering input, and playing audio.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Update(GameTime gameTime)
        {
            // Allows the game to exit
            if (GamePad.GetState(PlayerIndex.One).Buttons.Back == ButtonState.Pressed)
                this.Exit();

            estadoTeclado = Keyboard.GetState();
            estadoMouse = Mouse.GetState();
            MoverFigura(gameTime);
            huboColision = DetectarColision(gameTime);

            base.Update(gameTime);
        }

        /// <summary>
        /// Por cada loop de juego, este método se encargará de analizar el último
        /// estado del teclado. Mediante este análisis se podrá determinar qué tecla
        /// fue presionada la última vez y, gracias a esto, poder modificar la posición
        /// del objeto creado.
        /// </summary>
        private void MoverFigura(GameTime gameTime)
        {
            // Analizamos qué tecla fue presionada y luego modificamos su posición,
            // teniendo en cuenta la velocidad que indicamos.

            // ======== Mover Jugador 1 ====================================
            if (estadoTeclado.IsKeyDown(Keys.Left))
            { jugador.IncreasePosX(-jugador.GetVelocidad()); }

            if (estadoTeclado.IsKeyDown(Keys.Right))
            { jugador.IncreasePosX(jugador.GetVelocidad()); }

            if (estadoTeclado.IsKeyDown(Keys.Up))
            { jugador.IncreasePosY(-jugador.GetVelocidad()); }

            if (estadoTeclado.IsKeyDown(Keys.Down))
            { jugador.IncreasePosY(jugador.GetVelocidad()); ; }

            jugador.SetPosicionX(MathHelper.Clamp(jugador.GetPosicionX(), 0,
                                                GraphicsDevice.Viewport.Width - jugador.GetAncho()));
            jugador.SetPosicionY(MathHelper.Clamp(jugador.GetPosicionY(), 0,
                                                    GraphicsDevice.Viewport.Height - jugador.GetAlto()));


            // ======== Mover Jugador 2 ====================================
            jugador2.SetPosicionX(estadoMouse.X);
            jugador2.SetPosicionY(estadoMouse.Y);

            jugador2.SetPosicionX(MathHelper.Clamp(jugador2.GetPosicionX(), 0,
                                                GraphicsDevice.Viewport.Width - jugador2.GetAncho()));
            jugador2.SetPosicionY(MathHelper.Clamp(jugador2.GetPosicionY(), 0,
                                                    GraphicsDevice.Viewport.Height - jugador2.GetAlto()));
        }


        /// <summary>
        /// En cada loop del juego y cada vez que se mueva un objeto, inmediatamente se
        /// evaluará la posibilidad de que exista o no una colisión entre los objetos
        /// creados.
        /// </summary>
        /// <param name="gameTime"></param>
        /// <returns></returns>
        public bool DetectarColision(GameTime gameTime)
        {
            BoundingBox cuadroLimitacion;       // Un cuadro delimitador que se usará para detectar colisiones.
            BoundingSphere esferaLimitacion;   // Un círculo delimitador que se usará para detectar colisiones.
            BoundingBox neutralLimitacion;      // Un cuadro delimitador que se usará para detectar colisiones.

            Vector3 centroEsfera;

            cuadroLimitacion = new BoundingBox( new Vector3(jugador.GetPosicionX(),
                                                            jugador.GetPosicionY(),
                                                            0f)
                                               ,new Vector3(jugador.GetPosicionX() + jugador.GetAncho(),
                                                            jugador.GetPosicionY() + jugador.GetAlto(),
                                                            0f)
                                               );

            centroEsfera = new Vector3( jugador2.GetPosicionX() + jugador2.GetAncho() / 2,
                                        jugador2.GetPosicionY() + jugador2.GetAlto() / 2,
                                        0f);
            esferaLimitacion = new BoundingSphere(centroEsfera, jugador2.GetAncho() / 2);

            neutralLimitacion = new BoundingBox(new Vector3(figNeutral.GetPosicionX(),
                                                                figNeutral.GetPosicionY(),
                                                                0),
                                                 new Vector3(figNeutral.GetPosicionX() + figNeutral.GetAncho(),
                                                                figNeutral.GetPosicionY() + figNeutral.GetAlto(),
                                                                0)
                                                );

            if (cuadroLimitacion.Intersects(esferaLimitacion)
                || cuadroLimitacion.Intersects(neutralLimitacion)
                || neutralLimitacion.Intersects(esferaLimitacion))
            {
                return true;
            }
            else
                return false;
        }

        /// <summary>
        /// This is called when the game should draw itself.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Draw(GameTime gameTime)
        {
            string anchoPantalla = Convert.ToString(GraphicsDevice.Viewport.Width);
            string altoPantalla = Convert.ToString(GraphicsDevice.Viewport.Height);
            string textoColision;

            if (huboColision)
                textoColision = "Si";
            else
                textoColision = "No";
            
            GraphicsDevice.Clear(Color.CornflowerBlue);
            

            // ============ Comienza dibujado ===================
            spriteBatch.Begin();

            // Se dibuja el texto donde informa el valor de Ancho de la pantalla
            spriteBatch.DrawString(font,
                                "Ancho: " + anchoPantalla,
                                new Vector2(GraphicsDevice.Viewport.TitleSafeArea.X,
                                            GraphicsDevice.Viewport.TitleSafeArea.Y),
                                Color.White,
                                0f,
                                new Vector2(0f,0f),
                                0.8f,
                                SpriteEffects.None,0);

            // Se dibuja el texto donde informa el valor de Alto de la pantalla
            spriteBatch.DrawString(font,
                                    "Alto: " + altoPantalla,
                                    new Vector2(GraphicsDevice.Viewport.TitleSafeArea.X+20,
                                                GraphicsDevice.Viewport.TitleSafeArea.Y+50),
                                    Color.DarkBlue,
                                    MathHelper.ToRadians(90),
                                    new Vector2(0,0),
                                    0.8f,
                                    SpriteEffects.None,
                                    0f);


            // Se dibuja un texto donde indica las coordenadas (x,y) del jugador1
            spriteBatch.DrawString(font,
                        "Jugador 1: (" + jugador.GetPosicionX() + ", " + jugador.GetPosicionY() + ")",
                        new Vector2(GraphicsDevice.Viewport.TitleSafeArea.Center.X,
                                    GraphicsDevice.Viewport.TitleSafeArea.Y),
                        Color.Aquamarine,
                        0f,
                        new Vector2(0f, 0f),
                        0.8f,
                        SpriteEffects.None,
                        0f);

            // Se dibuja un texto donde indica las coordenadas (x,y) del jugador2
            spriteBatch.DrawString(font,
                        "Jugador 2: (" + jugador2.GetPosicionX() + ", " + jugador2.GetPosicionY() + ")",
                        new Vector2(GraphicsDevice.Viewport.TitleSafeArea.Center.X,
                                    GraphicsDevice.Viewport.TitleSafeArea.Y+25),
                        Color.Aquamarine,
                        0f,
                        new Vector2(0f, 0f),
                        0.8f,
                        SpriteEffects.None,
                        0f);

            // Se dibuja un texto que indica si hubo alguna colisión o no.
            spriteBatch.DrawString(font,
                        "Colision: " + textoColision,
                        new Vector2(GraphicsDevice.Viewport.TitleSafeArea.X,
                                    GraphicsDevice.Viewport.TitleSafeArea.Bottom - 25),
                        Color.BlueViolet,
                        0f,
                        new Vector2(0f, 0f),
                        0.8f,
                        SpriteEffects.None,
                        0f);

            // Se dibuja la figura del jugador
            jugador.Dibujar(spriteBatch);
            jugador2.Dibujar(spriteBatch);
            figNeutral.Dibujar(spriteBatch);
           
            spriteBatch.End();
            // ============ Termina dibujado ===================           
                 

            base.Draw(gameTime);
        }
    }
}
