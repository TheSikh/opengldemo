package com.gagan.opengl.demo1.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.gagan.opengl.demo1.R;
import com.gagan.opengl.demo1.graphics.shapes.Polygon;
import com.gagan.opengl.demo1.utils.LoggerConfig;
import com.gagan.opengl.demo1.utils.ShaderHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_CLAMP_TO_EDGE;
import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_WRAP_S;
import static android.opengl.GLES20.GL_TEXTURE_WRAP_T;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLUtils.texImage2D;
import static com.gagan.opengl.demo1.utils.RawResourceReader.readTextFileFromResource;

/**
 * Created by gagan on 4/28/17.
 */

public class SimpleRenderer implements GLSurfaceView.Renderer {

    private final Context context;
    private int program;

    private static final String U_MVP_MATRIX = "u_MVPMatrix";
    private static final String U_TEXTURE = "u_textureUnit";
    private static final String A_POSITION = "a_Position";
    private static final String A_TEXTURE_COORDINATES = "a_textureCoordinates";


    //    private int uTextureHandle;
//    private int uMVPMatrixHandle;
    private int aPositionHandle = -1;
    private int aTextureCoordinatesHandle = -1;
    private int textureId;

//    /**
//     * Store the view matrix. This can be thought of as our camera. This matrix transforms world space to eye space;
//     * it positions things relative to our eye.
//     */
//    private final float[] mViewMatrix = new float[16];
//    /**
//     * Store the model matrix. This matrix is used to move models from object space (where each model can be thought
//     * of being located at the center of the universe) to world space.
//     */
//    private final float[] mModelMatrix = new float[16];

//    /**
//     * Store the projection matrix. This is used to project the scene onto a 2D viewport.
//     */
//    private final float[] mProjectionMatrix = new float[16];

//    /**
//     * Allocate storage for the final combined matrix. This will be passed into the shader program.
//     */
//    private final float[] mMVPMatrix = new float[16];
//

    public SimpleRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {


//        // Position the eye behind the origin.
//        final float eyeX = 0.0f;
//        final float eyeY = 0.0f;
//        final float eyeZ = 1.5f;
//
//        // We are looking toward the distance
//        final float lookX = 0.0f;
//        final float lookY = 0.0f;
//        final float lookZ = -5.0f;
//
//        // Set our up vector. This is where our head would be pointing were we holding the camera.
//        final float upX = 0.0f;
//        final float upY = 1.0f;
//        final float upZ = 0.0f;
//
//        // Set the view matrix. This matrix can be said to represent the camera position.
//        // NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
//        // view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
//        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        setup();
//        // Create a new perspective projection matrix. The height will stay the same
//        // while the width will vary as per aspect ratio.
//        final float ratio = (float) width / height;
//        final float left = -ratio;
//        final float right = ratio;
//        final float bottom = -1.0f;
//        final float top = 1.0f;
//        final float near = 1.0f;
//        final float far = 10.0f;
//
//        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
//
//        // Calculate the projection and view transformation
//        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        float[] uScreen =
                {
                        2f / width, 0f, 0f, 0f,
                        0f, -2f / height, 0f, 0f,
                        0f, 0f, 0f, 0f,
                        -1f, 1f, 0f, 1f
                };

        // Now, let's set the value.
        FloatBuffer b = ByteBuffer.allocateDirect(uScreen.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        b.put(uScreen).position(0);

        int textureHandle = glGetUniformLocation(program, U_TEXTURE);
        int MVPMatrixHandle = glGetUniformLocation(program, U_MVP_MATRIX);
        GLES20.glUniformMatrix4fv(MVPMatrixHandle, b.limit() / uScreen.length, false, b);
        glUniform1i(textureHandle, 0);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glEnable(GLES20.GL_BLEND);

        // set the viewport and a fixed, white background
        GLES20.glViewport(0, 0, width, height);
    }

    private void setup() {
        String vertexShaderSource = readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = readTextFileFromResource(context, R.raw.simple_fragment_shader);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(program);
        }

        aPositionHandle = glGetAttribLocation(program, A_POSITION);
        aTextureCoordinatesHandle = glGetAttribLocation(program, A_TEXTURE_COORDINATES);

        glUseProgram(program);
        // Set the background clear color to gray.
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glEnable(GL_TEXTURE);
//        glEnable(GL_BLEND);
//        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnableVertexAttribArray(aPositionHandle);
        glEnableVertexAttribArray(aTextureCoordinatesHandle);

        //load the texture in to opengl
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.landscape_cropped);
        int[] texttureIds = new int[1];
        glGenTextures(1, texttureIds, 0);
        textureId = texttureIds[0];
        glBindTexture(GL_TEXTURE_2D, textureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
//        glActiveTexture(textureId);
        texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        if (bitmap != null)
            bitmap.recycle();


        if (LoggerConfig.ON) {
            Log.d("Renderer", glGetProgramInfoLog(program));
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);


        drawTriangle();
        drawSquare();
//        drawCircle();
//        drawPolygon();
        drawPolygonTest();
//        drawface();


    }


    private void drawTriangle() {
        // This triangle is red, green, and blue.
        final float[] verticesData = {
                // X, Y, Z
                75f, 500f,
                525f, 500f,
                300f, 100f,
        };
        // Initialize the buffers.
        FloatBuffer triangleData = ByteBuffer.allocateDirect(verticesData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(verticesData);
        triangleData.rewind();

        glVertexAttribPointer(aPositionHandle, 2, GLES20.GL_FLOAT, false,
                0, triangleData);


        final float[] textureData = {
                // X, Y, Z,
                // R, G, B, A
                0.075f, 0.500f,

                0.525f, 0.500f,

                0.300f, 0.100f,
        };
//        // Initialize the buffers.

        FloatBuffer triangleTextureData = ByteBuffer.allocateDirect(textureData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(textureData);
        triangleTextureData.rewind();

        glVertexAttribPointer(aTextureCoordinatesHandle, 2, GLES20.GL_FLOAT, false,
                0, triangleTextureData);
        // Pass in the color information

        glDrawArrays(GLES20.GL_TRIANGLES, 0, verticesData.length / 2);
    }

    private void drawSquare() {
//        50f, 500f,
//                500f, 500f,
//                275f, 100f,
        final float[] squareVerticesData = {
                // X, Y,
                600f, 100f,   // top left

                600f, 500f,   // bottom left

                1000f, 500f,   // bottom right

                1000f, 100f,  // top right
        };


// Initialize the buffers.
        FloatBuffer vertexData = ByteBuffer.allocateDirect(squareVerticesData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(squareVerticesData);
        vertexData.position(0);
        vertexData.position(0);
        glVertexAttribPointer(aPositionHandle, 2, GLES20.GL_FLOAT, false,
                0, vertexData);

        final float[] textureData = {
                // X, Y,
                0.0f, 0.0f,   // top left

                0.0f, 1.0f,   // bottom left

                1.0f, 1.0f,   // bottom right

                1.0f, 0.0f,  // top right
        };
//        // Initialize the buffers.

        FloatBuffer textureDataBuffer = ByteBuffer.allocateDirect(textureData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(textureData);
        textureDataBuffer.rewind();

        glVertexAttribPointer(aTextureCoordinatesHandle, 2, GLES20.GL_FLOAT, false,
                0, textureDataBuffer);

        final short drawOrder[] = {0, 1, 2, 0, 2, 3};

        // initialize byte buffer for the draw list
        ShortBuffer drawListBuffer = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer().put(drawOrder);
        drawListBuffer.position(0);


        // Pass in the color information
//        vertexData.position(mColorOffset);
//        glVertexAttribPointer(aColorHandle, mColorDataSize, GLES20.GL_FLOAT, false,
//                mStrideBytes, vertexData);
//
//        glEnableVertexAttribArray(aColorHandle);

        // Draw the square
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, 6,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

    }


    private void drawCircle() {
        // Circle variables
        int circlePoints = 30;
        float radius = 300.0f;
        float center_x = 550.0f;
        float center_y = 1000.0f;

        // Outer vertices of the circle i.e. excluding the center_x, center_y
        int circumferencePoints = circlePoints - 1;

        // Circle vertices and buffer variables
        int vertices = 0;
        float circleVertices[] = new float[circlePoints * 2];
        FloatBuffer vertexBuff; // 4 bytes per float

        // The initial buffer values
        circleVertices[vertices++] = center_x;
        circleVertices[vertices++] = center_y;

        // Set circle vertices values
        for (int i = 0; i < circumferencePoints; i++) {
            float percent = (i / (float) (circumferencePoints - 1));
            float radians = (float) (percent * 2 * Math.PI);


            // Vertex position
            float outer_x = (float) (center_x + radius * Math.cos(radians));
            float outer_y = (float) (center_y + radius * Math.sin(radians));

            circleVertices[vertices++] = outer_x;
            circleVertices[vertices++] = outer_y;
        }

        // Float buffer short has four bytes
        ByteBuffer vertexByteBuff = ByteBuffer
                .allocateDirect(circleVertices.length * 4);

        // Garbage collector won't throw this away
        vertexByteBuff.order(ByteOrder.nativeOrder());
        vertexBuff = vertexByteBuff.asFloatBuffer();
        vertexBuff.put(circleVertices);
        vertexBuff.position(0);
        glVertexAttribPointer(aPositionHandle, 2, GLES20.GL_FLOAT, false,
                0, vertexBuff);


        float textureRadius = 0.300f;
        float textureCenter_x = 0.550f;
        float textureCenter_y = 1.000f;

        // Outer vertices of the circle i.e. excluding the center_x, center_y
        int textureCircumferencePoints = circlePoints - 1;

        // Circle vertices and buffer variables
        int textureVertices = 0;
        float textureVircleVertices[] = new float[circlePoints * 2];
        FloatBuffer textureVertexBuff; // 4 bytes per float

        // The initial buffer values
        textureVircleVertices[textureVertices++] = textureCenter_x;
        textureVircleVertices[textureVertices++] = textureCenter_y;

        // Set circle vertices values
        for (int i = 0; i < textureCircumferencePoints; i++) {
            float percent = (i / (float) (textureCircumferencePoints - 1));
            float radians = (float) (percent * 2 * Math.PI);


            // Vertex position
            float outer_x = (float) (textureCenter_x + textureRadius * Math.cos(radians));
            float outer_y = (float) (textureCenter_y + textureRadius * Math.sin(radians));

            textureVircleVertices[textureVertices++] = outer_x;
            textureVircleVertices[textureVertices++] = outer_y;
        }

        // Float buffer short has four bytes
        ByteBuffer textureVertexByteBuff = ByteBuffer
                .allocateDirect(textureVircleVertices.length * 4);

        // Garbage collector won't throw this away
        textureVertexByteBuff.order(ByteOrder.nativeOrder());
        textureVertexBuff = textureVertexByteBuff.asFloatBuffer();
        textureVertexBuff.put(textureVircleVertices);
        textureVertexBuff.position(0);
        glVertexAttribPointer(aTextureCoordinatesHandle, 2, GLES20.GL_FLOAT, false,
                0, textureVertexBuff);

        // Draw circle as filled shape
        glDrawArrays(GL_TRIANGLE_FAN, 0, circlePoints);
    }

    private void drawPolygonTest() {
        Polygon polygon = new Polygon(
                550.0f,  //center_X
                1000.0f, //center_Y
                300.0f, //radius
                10 //number of sides+2
        );
        FloatBuffer vertexBuffer = polygon.getVertexBuffer();
        FloatBuffer textureBuffer = polygon.getTextureBuffer();
        vertexBuffer.position(0);
        textureBuffer.position(0);
        glVertexAttribPointer(aPositionHandle, 2, GLES20.GL_FLOAT, false,
                0, vertexBuffer);
        glVertexAttribPointer(aTextureCoordinatesHandle, 2, GLES20.GL_FLOAT, false,
                0, textureBuffer);
        glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, polygon.getSides());
    }

    private void drawPolygon() {
// Circle variables
        int circlePoints = 100;
        float radius = 300.0f;
        float center_x = 550.0f;
        float center_y = 1000.0f;

        // Outer vertices of the circle i.e. excluding the center_x, center_y
        int circumferencePoints = circlePoints - 1;

        // Circle vertices and buffer variables
        int vertices = 0;
        float circleVertices[] = new float[circlePoints * 2];
        FloatBuffer vertexBuff; // 4 bytes per float

        // The initial buffer values
        circleVertices[vertices++] = center_x;
        circleVertices[vertices++] = center_y;

        // Set circle vertices values
        for (int i = 0; i < circumferencePoints; i++) {
            float percent = (i / (float) (circumferencePoints - 1));
            float radians = (float) (percent * 2 * Math.PI);


            // Vertex position
            float outer_x = (float) (center_x + radius * Math.cos(radians));
            float outer_y = (float) (center_y + radius * Math.sin(radians));

            circleVertices[vertices++] = outer_x;
            circleVertices[vertices++] = outer_y;
        }

        // Float buffer short has four bytes
        ByteBuffer vertexByteBuff = ByteBuffer
                .allocateDirect(circleVertices.length * 4);

        // Garbage collector won't throw this away
        vertexByteBuff.order(ByteOrder.nativeOrder());
        vertexBuff = vertexByteBuff.asFloatBuffer();
        vertexBuff.put(circleVertices);
        vertexBuff.position(0);
        glVertexAttribPointer(aPositionHandle, 2, GLES20.GL_FLOAT, false,
                0, vertexBuff);


        float textureRadius = 0.300f;
        float textureCenter_x = 0.550f;
        float textureCenter_y = 1.000f;

        // Outer vertices of the circle i.e. excluding the center_x, center_y
        int textureCircumferencePoints = circlePoints - 1;

        // Circle vertices and buffer variables
        int textureVertices = 0;
        float textureVircleVertices[] = new float[circlePoints * 2];
        FloatBuffer textureVertexBuff; // 4 bytes per float

        // The initial buffer values
        textureVircleVertices[textureVertices++] = textureCenter_x;
        textureVircleVertices[textureVertices++] = textureCenter_y;

        // Set circle vertices values
        for (int i = 0; i < textureCircumferencePoints; i++) {
            float percent = (i / (float) (textureCircumferencePoints - 1));
            float radians = (float) (percent * 2 * Math.PI);


            // Vertex position
            float outer_x = (float) (textureCenter_x + textureRadius * Math.cos(radians));
            float outer_y = (float) (textureCenter_y + textureRadius * Math.sin(radians));

            textureVircleVertices[textureVertices++] = outer_x;
            textureVircleVertices[textureVertices++] = outer_y;
        }

        // Float buffer short has four bytes
        ByteBuffer textureVertexByteBuff = ByteBuffer
                .allocateDirect(textureVircleVertices.length * 4);

        // Garbage collector won't throw this away
        textureVertexByteBuff.order(ByteOrder.nativeOrder());
        textureVertexBuff = textureVertexByteBuff.asFloatBuffer();
        textureVertexBuff.put(textureVircleVertices);
        textureVertexBuff.position(0);
        glVertexAttribPointer(aTextureCoordinatesHandle, 2, GLES20.GL_FLOAT, false,
                0, textureVertexBuff);

        // Draw circle as filled shape
        glDrawArrays(GL_TRIANGLE_FAN, 0, circlePoints);
    }

    private void drawface() {

//
//        float[] facepoints = {
//                430.0f, 1749.0f,
//                438.0f, 1866.0f,
//                468.0f, 1987.0f,
//                522.0f, 2098.0f,
//                606.0f, 2195.0f,
//                697.0f, 2268.0f,
//                799.0f, 2333.0f,
//                908.0f, 2370.0f,
//                1007.0f, 2360.0f,
//                1084.0f, 2317.0f,
//                1130.0f, 2232.0f,
//                1164.0f, 2133.0f,
//                1196.0f, 2028.0f,
//                1229.0f, 1921.0f,
//                1245.0f, 1816.0f,
//                1239.0f, 1706.0f,
//                1211.0f, 1600.0f,
//                546.0f, 1661.0f,
//                593.0f, 1599.0f,
//                674.0f, 1576.0f,
//                761.0f, 1583.0f,
//                844.0f, 1611.0f,
//                990.0f, 1593.0f,
//                1035.0f, 1536.0f,
//                1093.0f, 1494.0f,
//                1152.0f, 1476.0f,
//                1186.0f, 1514.0f,
//                924.0f, 1689.0f,
//                945.0f, 1779.0f,
//                967.0f, 1870.0f,
//                989.0f, 1960.0f,
//                882.0f, 1990.0f,
//                933.0f, 2003.0f,
//                990.0f, 2015.0f,
//                1033.0f, 1983.0f,
//                1064.0f, 1944.0f,
//                635.0f, 1722.0f,
//                682.0f, 1682.0f,
//                745.0f, 1677.0f,
//                805.0f, 1721.0f,
//                747.0f, 1747.0f,
//                680.0f, 1751.0f,
//                991.0f, 1676.0f,
//                1031.0f, 1610.0f,
//                1092.0f, 1586.0f,
//                1135.0f, 1609.0f,
//                1110.0f, 1656.0f,
//                1049.0f, 1674.0f,
//                805.0f, 2139.0f,
//                876.0f, 2100.0f,
//                943.0f, 2077.0f,
//                989.0f, 2077.0f,
//                1021.0f, 2056.0f,
//                1066.0f, 2045.0f,
//                1118.0f, 2049.0f,
//                1083.0f, 2126.0f,
//                1045.0f, 2170.0f,
//                1007.0f, 2189.0f,
//                959.0f, 2199.0f,
//                888.0f, 2192.0f,
//                832.0f, 2136.0f,
//                947.0f, 2103.0f,
//                994.0f, 2098.0f,
//                1027.0f, 2081.0f,
//                1096.0f, 2062.0f,
//                1033.0f, 2115.0f,
//                997.0f, 2136.0f,
//                951.0f, 2143.0f
//        };

        float[] facepoints = {
                424.0f, 887.0f,
                540.0f, 887.0f,
                848.0f, 886.0f,
                562.0f, 688.0f,
                548.0f, 690.0f,
                547.0f, 690.0f,
        };

//
//        for (int i = 0; i < facepoints.length; i++) {
//            if (i % 2 == 0)
//                facepoints[i] = facepoints[i] - 500f;
//            else
//                facepoints[i] = facepoints[i] + 500f;
//
//        }

// Initialize the buffers.
        FloatBuffer vertexData = ByteBuffer.allocateDirect(facepoints.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(facepoints);
        vertexData.position(0);
        glVertexAttribPointer(aPositionHandle, 2, GLES20.GL_FLOAT, false,
                0, vertexData);

        glDrawArrays(GLES20.GL_POINTS, 0, facepoints.length / 2);
    }

}
