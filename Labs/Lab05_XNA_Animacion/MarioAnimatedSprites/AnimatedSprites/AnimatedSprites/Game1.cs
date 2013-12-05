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

namespace AnimatedSprites
{
    /// <summary>
    /// This is the main type for your game
    /// </summary>
    public class Game1 : Microsoft.Xna.Framework.Game
    {
        SoundEffect soundEffect;
        GraphicsDeviceManager graphics;
        SpriteBatch sprite;
        SpriteBatch spriteBall;
        SpriteBatch spriteMarioCaminando;
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

        // Mario
        Texture2D textureMarioCaminando;
        Point currentFrameMarioCaminando = new Point(0, 0);
        Point frameSizeMarioCaminando = new Point(17, 32);
        int timeSinceLastFrameMarioCaminando = 0;
        const int millisecondsPerFrameMarioCaminando = 30;
        Point sheetSizeMarioCaminando = new Point(3, 1);
        Vector2 positionDrawMarioCaminando = new Vector2(100, 150);
        const float speedMarioCaminando = 5;
     
        // Framerate stuff
        int timeSinceLastFrame = 0;
        int millisecondsPerFrame = 50;

        public Game1()
        {
            graphics = new GraphicsDeviceManager(this);
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

            base.Initialize();
        }

        /// <summary>
        /// LoadContent will be called once per game and is the place to load
        /// all of your content.
        /// </summary>
        protected override void LoadContent()
        {
            // Create a new SpriteBatch, which can be used to draw textures.
            sprite = new SpriteBatch(GraphicsDevice);
            spriteMarioCaminando = new SpriteBatch(GraphicsDevice);
            spriteBall = new SpriteBatch(GraphicsDevice);
            texture = Content.Load<Texture2D>(@"images\threerings");
            textureMarioCaminando = Content.Load<Texture2D>(@"images\marioSprite1");
            textureBall = Content.Load<Texture2D>(@"images\skullball");
            soundEffect = Content.Load<SoundEffect>(@"sounds\01-overworld");
            soundEffect.Play();
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


        private Vector2 ValidarObjeto(Vector2 obj, Point frameSize)
        {
            // If threerings is off the screen, move it back into the game window
            if (obj.X < 0)
                obj.X = 0;
            if (obj.Y < 0)
                obj.Y = 0;
#if WINDOWS
            if (obj.X > Window.ClientBounds.Width - frameSize.X)
                obj.X = Window.ClientBounds.Width - frameSize.X;
            if (obj.Y > Window.ClientBounds.Height - frameSize.Y)
                obj.Y = Window.ClientBounds.Height - frameSize.Y;
#else
                if (obj.X >  (graphics.GraphicsDevice.Viewport.Width-20) - frameSize.X)
                    obj.X = (graphics.GraphicsDevice.Viewport.Width-20) - frameSize.X;
                if (obj.Y > (graphics.GraphicsDevice.Viewport.Height-20) - frameSize.Y)
                    obj.Y = (graphics.GraphicsDevice.Viewport.Height-20) - frameSize.Y;
   
#endif
            return obj;
        }

        /// <summary>
        /// MarioNormal....
        /// </summary>
        /// <param name="gameTime"></param>
        private void MarioCaminando(GameTime gameTime)
        {
            timeSinceLastFrameMarioCaminando += gameTime.ElapsedGameTime.Milliseconds;
            if (timeSinceLastFrameMarioCaminando > millisecondsPerFrameMarioCaminando)
            {
                timeSinceLastFrameMarioCaminando -= millisecondsPerFrameMarioCaminando;

                ++currentFrameMarioCaminando.X;
                if (currentFrameMarioCaminando.X >= sheetSizeMarioCaminando.X)
                {
                    currentFrameMarioCaminando.X = 0;
                    ++currentFrameMarioCaminando.Y;
                    if (currentFrameMarioCaminando.Y >= sheetSizeMarioCaminando.Y)
                        currentFrameMarioCaminando.Y = 0;
                }
            }



            positionDrawMarioCaminando = ValidarObjeto(positionDrawMarioCaminando, frameSizeMarioCaminando);
        }

        /// <summary>
        /// Allows the game to run logic such as updating the world,
        /// checking for collisions, gathering input, and playing audio.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Update(GameTime gameTime)
        {
            // Allows the game to exit
            if (GamePad.GetState(PlayerIndex.One).Buttons.Back == ButtonState.Pressed || Keyboard.GetState().IsKeyDown(Keys.Space))
                this.Exit();

            timeSinceLastFrame += gameTime.ElapsedGameTime.Milliseconds;
            if (timeSinceLastFrame > millisecondsPerFrame)
            {
                timeSinceLastFrame -= millisecondsPerFrame;
                Animation(gameTime);
                MarioCaminando(gameTime);
            }

            Ball(gameTime);
            // Move threerings based on keyboard input
            KeyboardState keyboardState = Keyboard.GetState();
            if (keyboardState.IsKeyDown(Keys.Left) || GamePad.GetState(PlayerIndex.One).DPad.Left == ButtonState.Pressed
                || GamePad.GetState(PlayerIndex.One).IsButtonDown(Buttons.LeftThumbstickLeft))
                positionDrawMarioCaminando.X -= speedMarioCaminando;
            if (keyboardState.IsKeyDown(Keys.Right) || GamePad.GetState(PlayerIndex.One).DPad.Right == ButtonState.Pressed)
                positionDrawMarioCaminando.X += speedMarioCaminando;
            if (keyboardState.IsKeyDown(Keys.Up) || GamePad.GetState(PlayerIndex.One).DPad.Up == ButtonState.Pressed)
                positionDrawMarioCaminando.Y -= speedMarioCaminando;
            if (keyboardState.IsKeyDown(Keys.Down) || GamePad.GetState(PlayerIndex.One).DPad.Down == ButtonState.Pressed)
                positionDrawMarioCaminando.Y += speedMarioCaminando;

            GamePadState gamepadState = GamePad.GetState(PlayerIndex.One);

            if (gamepadState.Buttons.A == ButtonState.Pressed || keyboardState.IsKeyDown(Keys.A))
            {
                GamePad.SetVibration(PlayerIndex.One, 1f, 1f);
            }
            else
            {
                GamePad.SetVibration(PlayerIndex.One, 0, 0);
            }

            
            base.Update(gameTime);
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


        private void MarioCaminandoSprite()
        {
            sprite.Draw(textureMarioCaminando, positionDrawMarioCaminando,
                new Rectangle(currentFrameMarioCaminando.X * frameSizeMarioCaminando.X,
                    currentFrameMarioCaminando.Y * frameSizeMarioCaminando.Y,
                    frameSizeMarioCaminando.X,
                    frameSizeMarioCaminando.Y),
                Color.White, 0, Vector2.Zero,
               2, SpriteEffects.None, 0);
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
            sprite.Begin(SpriteSortMode.FrontToBack, BlendState.AlphaBlend);
            AnimationSprite();
            BallSprite();
            MarioCaminandoSprite();
            sprite.End();
            base.Draw(gameTime);
        }

     

    }
}
