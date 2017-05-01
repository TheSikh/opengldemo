uniform mat4 u_MVPMatrix;           // A constant representing the combined model/view/projection matrix.

attribute vec2 a_Position;          // Per-vertex position information we will pass in.
attribute vec2 a_textureCoordinates;             // Per-vertex color information we will pass in.

varying vec2 v_textureCoordinates;               // This will be passed into the fragment shader.

void main()                         // The entry point for our vertex shader.
{
//   v_Color = a_Color;               // Pass the color through to the fragment shader. It will be interpolated across the triangle.
   gl_Position = u_MVPMatrix        // gl_Position is a special variable used to store the final position.
               * vec4(a_Position, 0.0, 1.0);        // Multiply the vertex by the matrix to get the final point in normalized screen coordinates.
   gl_PointSize = 10.0;
   v_textureCoordinates = a_textureCoordinates;
}
