using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;          // Espacio de nombre necesario para usar Vector2
using Microsoft.Xna.Framework.Graphics; // Espacio de nombre necesario para usar Texture2D

namespace Prueba01
{
    class Figura
    {
        private Texture2D textura;   // Textura que se usará para representar al jugador.
        private Vector2 posicion;            // Contendrá las coordenadas de su posición.
        private float velocidadMov;  // Representa al valor de la velocidad de su desplazamiento.

        public void Inicializar(Texture2D textura, Vector2 posicion, float velocidadMov)
        {
            this.textura = textura;
            this.posicion = posicion;
            this.velocidadMov = velocidadMov;
        }

        public void Dibujar(SpriteBatch spriteBatch)
        {
            spriteBatch.Draw(
                            textura,
                            posicion,
                                null, 
                            Color.White,
                            0f,         //MathHelper.ToRadians(45)
                            Vector2.Zero,
                            0.5f,
                            SpriteEffects.None,
                            0f
                            );
        }

        public int GetAncho()
        {
            return textura.Width /2;   // Se retorna el valor (en pixeles) del ancho de la textura.
        }

        public int GetAlto()
        {
            return textura.Height /2; // Se retorna el valor (en pixeles) del alto de la textura.
        }

        public float GetVelocidad()
        {
            return velocidadMov; // Se retorna el valor de la velocidad establecida.
        }

        public float GetPosicionX()
        {
            return posicion.X;
        }

        public float GetPosicionY()
        {
            return posicion.Y;
        }

        public void SetPosicionX (float x)
        {
            posicion.X = x;
        }

        public void SetPosicionY(float y)
        {
            posicion.Y = y;
        }

        public void IncreasePosX(float valorX)
        {
            posicion.X += valorX;
        }

        public void IncreasePosY(float valorY)
        {
            posicion.Y += valorY;
        }

    }
}
