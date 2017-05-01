precision mediump float;            // Set the default precision to medium. We don't need as high of a precision in the fragment shader.
uniform sampler2D u_textureUnit;
varying vec2 v_textureCoordinates;                // This is the color from the vertex shader interpolated across the triangle per fragment.
void main()                         // The entry point for our fragment shader.
{
   gl_FragColor = texture2D(u_textureUnit, v_textureCoordinates);          // Pass the color directly through the pipeline.
}