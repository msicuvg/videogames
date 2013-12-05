/* Made by TheDarkJoker94. 
*  Check http://thedarkjoker094.blogspot.com/ for more C# Tutorials 
*  and also SUBSCRIBE to my Youtube Channel http://www.youtube.com/user/TheDarkJoker094 
*  Thanks! */
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

namespace MyFirstGame
{
    /// <summary>
    /// This is the main type for your game
    /// </summary>
    public class Game1 : Microsoft.Xna.Framework.Game
    {
        GraphicsDeviceManager graphics;
        SpriteBatch spriteBatch;

        public Game1()
        {
            graphics = new GraphicsDeviceManager(this);
            Content.RootDirectory = "Content";
            // Set the window size
            graphics.PreferredBackBufferWidth = 800;
            graphics.PreferredBackBufferHeight = 600;

            // You can also set it to fullscreen
            //graphics.IsFullScreen = true;
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
        Texture2D mytexture; // A texture we can render (our sprite)
        Vector2 sPos = Vector2.Zero; // Set the coordinates where the sprite will be drawn (2-dimensional space) 
        Vector2 positionContador = new Vector2(50,50);
        int contador = 0;
        protected override void LoadContent()
        {
            // Create a new SpriteBatch, which can be used to draw textures.
            spriteBatch = new SpriteBatch(GraphicsDevice);
            mytexture = Content.Load<Texture2D>("ball"); // Load the sprite
            // TODO: use this.Content to load your game content here
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
        Vector2 sSpeed = Vector2.One; // This is something like a direction indicator
        bool b = false;
        KeyboardState k0;// = k1;//= null;// Keyboard.GetState();
        GamePadState g0;// = g1;//= null;// GamePad.GetState(PlayerIndex.One);
        
        protected override void Update(GameTime gameTime)
        {
            KeyboardState k1 = Keyboard.GetState();
          
            GamePadState g1 = GamePad.GetState(PlayerIndex.One);

         
            // Allows the game to exit
            if (Keyboard.GetState().IsKeyDown(Keys.Escape) || GamePad.GetState(PlayerIndex.One).Buttons.Back == ButtonState.Pressed) // Exit game when the ESC key is pressed
                this.Exit();
            else if ((g1.Buttons.A == ButtonState.Pressed) || (k0.IsKeyUp(Keys.Space) && k1.IsKeyDown(Keys.Space)))
            {
                b = !b;
                //contador++;
            }
            else if (g1.Buttons.B== ButtonState.Pressed)
            {
                contador = 0;
                g0 = g1;
               
            }
            if((g0.Buttons.B == ButtonState.Released && g1.Buttons.B == ButtonState.Pressed) || (k0.IsKeyUp(Keys.B) && k1.IsKeyDown(Keys.B)))
            {
                contador++;

            }
            k0 = k1;
            g0 = g1;
            if (!b)
            {
                sPos += sSpeed * (float)gameTime.ElapsedGameTime.TotalSeconds * 500; // Let's speed it up :)
                int MaxX = graphics.GraphicsDevice.Viewport.Width - mytexture.Width;
                int MinX = 0;
                int MaxY = graphics.GraphicsDevice.Viewport.Height - mytexture.Height;
                int MinY = 0;
                // Check if the sprite hits the sides of the window. If it does so then make it to move the opposite direction.
                if (sPos.X > MaxX)
                {
                    sSpeed.X *= -1;
                    sPos.X = MaxX;
                }
                else if (sPos.X < MinX)
                {
                    sSpeed.X *= -1;
                    sPos.X = MinX;
                }
                if (sPos.Y > MaxY)
                {
                    sSpeed.Y *= -1;
                    sPos.Y = MaxY;
                }
                else if (sPos.Y < MinY)
                {
                    sSpeed.Y *= -1;
                    sPos.Y = MinY;
                }  
                // TODO: Add your update logic here

            }
            else
            {
               
            }

            base.Update(gameTime);
        }

        /// <summary>
        /// This is called when the game should draw itself.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Draw(GameTime gameTime)
        {
            GraphicsDevice.Clear(Color.White); // You can change the background color here
            // Draw the sprite
                spriteBatch.Begin();
                spriteBatch.DrawString(Content.Load<SpriteFont>("Arial"), contador.ToString(), positionContador, Color.Black);
                spriteBatch.End();

                spriteBatch.Begin(SpriteSortMode.FrontToBack, BlendState.AlphaBlend);
                spriteBatch.Draw(mytexture, sPos, Color.White);
                spriteBatch.End();
            
           
            base.Draw(gameTime);
            // TODO: Add your drawing code here

        
        }
    }
}