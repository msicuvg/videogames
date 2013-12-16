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
using System.Threading;
namespace AnimatedSprites
{
    /// <summary>
    /// This is the main type for your game
    /// </summary>
    public class Game1 : Microsoft.Xna.Framework.Game
    {
        int collisionRectOffsetBall = 5;
     
        SoundEffect soundEffect;
        SoundEffectInstance soundEffectUpInstance;
        bool up = false;
        SoundEffect soundEffectUp;
        
        GraphicsDeviceManager graphics;
        SpriteBatch sprite;
         // Texture stuff

        // Ball
        Texture2D textureBall;
        Point frameSizeBall = new Point(75, 75);
        Point currentFrameBall = new Point(0, 0);
        Point sheetSizeBall = new Point(6, 8);
        int timeSinceLastFrameBall = 0;
        const int millisecondsPerFrameBall = 25;
        //Vector2 positionBall = new Vector2(100, 100);
       
        Vector2 positionDrawBall = new Vector2(250, 350);


        //Animación
        Texture2D texture;
        Point frameSize = new Point(75, 75);
        Point currentFrame = new Point(0, 0);
        const int millisecondsPerFrameAnimation = 25;
        Vector2 positionDraw = new Vector2(50, 50);
        Point sheetSize = new Point(6, 8);
        const float speed = 15;

    

        // Framerate stuff
        int timeSinceLastFrame = 0;
        int millisecondsPerFrame = 50;

        Texture2D textureMundo;
        Texture2D textureUp;

        //Control de botones
        GamePadState gamepadLast;
        KeyboardState keyboardLast;

        Player mario;
        public Game1()
        {
            graphics = new GraphicsDeviceManager(this);
            graphics.IsFullScreen = false;
            graphics.PreferredBackBufferHeight = 600;
            graphics.PreferredBackBufferWidth = 1024;
            Content.RootDirectory = "Content";
    

        }

        /// <summary>
        /// Allows the game to perform any initialization it needs to before starting to run.
        /// This is where it can query for any required services and load any non-graphic
        /// related content.  Calling base.Initialize will enumerate through any components
        /// and initialize them as well.
        /// </summary>
        protected override void Initialize()
        {
            // TODO: Add your initialization logic here
            sprite = new SpriteBatch(GraphicsDevice);
            mario = new Player(this, sprite);
            base.Initialize();
        }

        /// <summary>
        /// LoadContent will be called once per game and is the place to load
        /// all of your content.
        /// </summary>
        protected override void LoadContent()
        {
            // Create a new SpriteBatch, which can be used to draw textures.
            //Cargar Player
            mario.LoadContent();
            texture = Content.Load<Texture2D>(@"images\threerings");
            textureMundo = Content.Load<Texture2D>(@"images\super_mario");
            textureBall = Content.Load<Texture2D>(@"images\skullball");
            textureUp = Content.Load<Texture2D>(@"images\up");
          
            soundEffect = Content.Load<SoundEffect>(@"sounds\01-overworld");
            soundEffectUp = Content.Load<SoundEffect>(@"sounds\smb_1-up");
            
            soundEffectUpInstance = soundEffectUp.CreateInstance();
            soundEffect.Play();
        }

        /// <summary>
        /// UnloadContent will be called once per game and is the place to unload
        /// all content.
        /// </summary>
        protected override void UnloadContent()
        {
            Console.WriteLine("Saliendo");
            // TODO: Unload any non ContentManager content here
        }
        /// <summary>
        /// Animación inicial
        /// </summary>
        /// <param name="gameTime"></param>
        private void Animation(GameTime gameTime)
        {

            ++currentFrame.X;
            if (currentFrame.X >= sheetSize.X)
            {
                currentFrame.X = 0;
                ++currentFrame.Y;
                if (currentFrame.Y >= sheetSize.Y)
                    currentFrame.Y = 0;
            }
        }
        /// <summary>
        /// Explosivo
        /// </summary>
        /// <param name="gameTime"></param>
        private void Ball(GameTime gameTime)
        {
            // ball animation and framerate stuff
            timeSinceLastFrameBall += gameTime.ElapsedGameTime.Milliseconds;
            if (timeSinceLastFrameBall > millisecondsPerFrameBall)
            {
                timeSinceLastFrameBall -= millisecondsPerFrameBall;
                // Advance to the next frame
                ++currentFrameBall.X;
                if (currentFrameBall.X >= sheetSizeBall.X)
                {
                    currentFrameBall.X = 0;
                    ++currentFrameBall.Y;
                    if (currentFrameBall.Y >= sheetSizeBall.Y)
                        currentFrameBall.Y = 0;
                }
            }
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
            //Update
            mario.Update(gameTime);
            timeSinceLastFrame += gameTime.ElapsedGameTime.Milliseconds;
            if (timeSinceLastFrame > millisecondsPerFrame)
            {
                timeSinceLastFrame -= millisecondsPerFrame;
                Animation(gameTime);
            }

            
            Ball(gameTime);
           /* if (Collide() && !up)
            {
                up = true;
                soundEffectUpInstance.Play();

               // Exit();
            }*/
            // Move threerings based on keyboard input
            KeyboardState keyboardState = Keyboard.GetState();

            if (keyboardState.IsKeyDown(Keys.Left) || GamePad.GetState(PlayerIndex.One).DPad.Left == ButtonState.Pressed || GamePad.GetState(PlayerIndex.One).IsButtonDown(Buttons.LeftThumbstickLeft))
            {
                //Caminando Derecha
                mario.CaminarDerecha();
            }
            else if (keyboardState.IsKeyDown(Keys.Right) || GamePad.GetState(PlayerIndex.One).DPad.Right == ButtonState.Pressed)
            {
                //Caminando izquierda
                mario.CaminarIzquierda();
            }
            else if (keyboardState.IsKeyDown(Keys.Up) || GamePad.GetState(PlayerIndex.One).DPad.Up == ButtonState.Pressed)
            {
               //CaminarArriba
                mario.CaminarArriba();
            }
            else if (keyboardState.IsKeyDown(Keys.Down) || GamePad.GetState(PlayerIndex.One).DPad.Down == ButtonState.Pressed)
            {
                //CaminarAbajo
                mario.CaminarAbajo();
            }
            else
            {
             //   marioCaminandoDerecha = null;
                //Mario detenido
                mario.MarioDetener();
            }
            GamePadState gamepadState = GamePad.GetState(PlayerIndex.One);
            if (gamepadState.Buttons.A == ButtonState.Pressed)
            {
                gamepadLast = gamepadState;
            }
            if (keyboardState.IsKeyDown(Keys.A))
            {
                if (!keyboardLast.IsKeyDown(Keys.A))
                {
                    Encender();
                    Console.WriteLine("Imprimir A");
                }
                keyboardLast = keyboardState;
            }

            if ((gamepadLast.Buttons.A == ButtonState.Released && gamepadState.Buttons.A == ButtonState.Pressed))
            {
                    GamePad.SetVibration(PlayerIndex.One, 1f, 1f);

                    Encender();
            }
            else
            {
                GamePad.SetVibration(PlayerIndex.One, 0, 0);
            }

            keyboardLast = keyboardState;
            gamepadLast = gamepadState;
           
            base.Update(gameTime);
        }

        private void MundoSprite()
        {
            sprite.Draw(textureMundo, Vector2.Zero,
                Color.White);
        }

        private void UpSprite()
        {
            sprite.Draw(textureUp, new Vector2(140,500),
                Color.White);
        }


        private void AnimationSprite()
        {
            sprite.Draw(texture, positionDraw,
                new Rectangle(currentFrame.X * frameSize.X,
                    currentFrame.Y * frameSize.Y,
                    frameSize.X,
                    frameSize.Y),
                Color.White, 0, Vector2.Zero,
                1, SpriteEffects.None, 0);
        }


        private void Encender()
        {
            ClienteUtils c = new ClienteUtils();
            Thread t = new Thread(delegate()
            {
                try
                {
                    //192,168,43,91
                  //  ClienteUtils.Send("192.168.1.177", 80, "5\n\n");
                   // ClienteUtils.Send(ip, 80, "5\n\n");
                    
                    Console.WriteLine("Enviando paquete....");
                }
                catch { }
            });
            t.Start();
        }

       
        private void BallSprite()
        {
            sprite.Draw(textureBall, positionDrawBall,
                new Rectangle(currentFrameBall.X * frameSizeBall.X,
                    currentFrameBall.Y * frameSizeBall.Y,
                    frameSizeBall.X,
                    frameSizeBall.Y),
                Color.White, 0, Vector2.Zero,
                1, SpriteEffects.None, 0);
        }

        /// <summary>
        /// This is called when the game should draw itself.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Draw(GameTime gameTime)
        {
            GraphicsDevice.Clear(Color.White);

            sprite.Begin(SpriteSortMode.Immediate, BlendState.AlphaBlend);
            MundoSprite();
            UpSprite();
            AnimationSprite();
            BallSprite();
            //Dibujar...Draw
            mario.Draw(gameTime);
            sprite.End();
            base.Draw(gameTime);
        }

       /* protected bool Collide()
        {
            Rectangle marioRect = new Rectangle(
                (int)positionDrawMario.X + collisionRectOffsetMarioCaminando,
                (int)positionDrawMario.Y + collisionRectOffsetMarioCaminando,
                frameSizeMarioCaminando.X - (collisionRectOffsetMarioCaminando * 2),
                frameSizeMarioCaminando.Y - (collisionRectOffsetMarioCaminando * 2));
            Rectangle ballRect = new Rectangle(
                (int)positionDrawBall.X + collisionRectOffsetBall,
                (int)positionDrawBall.Y + collisionRectOffsetBall,
                frameSizeBall.X - (collisionRectOffsetBall * 2),
                frameSizeBall.Y - (collisionRectOffsetBall * 2));

            return marioRect.Intersects(ballRect);

        }*/

      

    }
}
